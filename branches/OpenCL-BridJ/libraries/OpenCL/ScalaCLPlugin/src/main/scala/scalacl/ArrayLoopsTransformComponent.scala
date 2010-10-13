/*
 * ScalaCL - putting Scala on the GPU with JavaCL / OpenCL
 * http://scalacl.googlecode.com/
 *
 * Copyright (c) 2009-2010, Olivier Chafik (http://ochafik.free.fr/)
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of Olivier Chafik nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY OLIVIER CHAFIK AND CONTRIBUTORS ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE REGENTS AND CONTRIBUTORS BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package scalacl

import scala.tools.nsc.Global

import scala.tools.nsc.plugins.PluginComponent
import scala.tools.nsc.transform.{Transform, TypingTransformers}

object LoopsTransformComponent {
  val runsAfter = List[String](
    "namer"
    //, OpsFuserTransformComponent.phaseName, Seq2ArrayTransformComponent.phaseName
  )
  val runsBefore = List[String]("refchecks")
  val phaseName = "loopstransform"
}


/**
 * Transforms the following constructs into their equivalent while loops :
 * - Array[T].foreach(x => body)
 * - Array[T].map(x => body)
 * - Array[T].reduceLeft((x, y) => body) / reduceRight
 * - Array[T].foldLeft((x, y) => body) / foldRight
 * - Array[T].scanLeft((x, y) => body) / scanRight
 */
class LoopsTransformComponent(val global: Global, val fileAndLineOptimizationFilter: ScalaCLPlugin.FileAndLineOptimizationFilter)
extends PluginComponent
   with Transform
   with TypingTransformers
   with MiscMatchers
   with TreeBuilders
   with WithOptimizationFilter
{
  import global._
  import global.definitions._
  import scala.tools.nsc.symtab.Flags._
  import typer.{typed}    // methods to type trees

  override val runsAfter = LoopsTransformComponent.runsAfter
  override val runsBefore = LoopsTransformComponent.runsBefore
  override val phaseName = LoopsTransformComponent.phaseName

  /// describes the outside of the loop (before - statements - and after - final return value)
  class LoopOutersEnv(
    val aIdentGen: IdentGen,
    val nIdentGen: IdentGen
  )
  /// describes the outside of the loop (before - statements - and after - final return value). Includes payload that will be visible from the inside
  class LoopOuters[Payload](
    val statements: List[Tree],
    val finalReturnValue: Tree,
    val payload: Payload
  )
  /// describes the environment available to the inside of the loop
  class LoopInnersEnv[Payload](
    val aIdentGen: IdentGen,
    val nIdentGen: IdentGen,
    val iIdentGen: IdentGen,
    val itemIdentGen: IdentGen,
    val payload: Payload
  )

  def newTransformer(unit: CompilationUnit) = new TypingTransformer(unit) {
  
    abstract sealed class ColTree {
      val supportsRightVariants: Boolean
      def foreach[Payload](
        tree: Tree,
        array: Tree,
        componentType: Symbol,
        reverseOrder: Boolean,
        skipFirst: Boolean,
        outerStatements: LoopOutersEnv => LoopOuters[Payload],
        innerStatements: LoopInnersEnv[Payload] => List[Tree]
      ): Tree
    }
    object ColTree {
      def unapply(tree: Tree): Option[(ColTree, Type, Tree, Symbol)] = tree match {
        case ArrayTree(array, componentType) =>
          Some((ArrayCol, appliedType(ArrayClass.tpe, List(componentType.tpe)), array, componentType))
        case ListTree(componentType) =>
          Some((ListCol, appliedType(ListClass.tpe, List(componentType.tpe)), tree, componentType))
      }
    }

    case object ArrayCol extends ColTree {
      override val supportsRightVariants = true
      override def foreach[Payload](
        tree: Tree,
        collection: Tree,
        componentType: Symbol,
        reverseOrder: Boolean,
        skipFirst: Boolean,
        outerStatements: LoopOutersEnv => LoopOuters[Payload],
        innerStatements: LoopInnersEnv[Payload] => List[Tree]
      ): Tree = {
        val pos = tree.pos
        val (aIdentGen, aSym, aDef) = newVariable(unit, "array$", currentOwner, pos, false, collection)
        val (nIdentGen, _, nDef) = newVariable(unit, "n$", currentOwner, pos, false, Select(aIdentGen(), nme.length).setSymbol(getMember(aSym, nme.length)).setType(IntClass.tpe))
        val (iIdentGen, _, iDef) = newVariable(unit, "i$", currentOwner, pos, true,
          if (reverseOrder) {
            if (skipFirst)
              intSub(nIdentGen(), newInt(1))
            else
              nIdentGen()
          } else {
            if (skipFirst)
              newInt(1)
            else
              newInt(0)
          }
        )
        val (itemIdentGen, itemSym, itemDef) = newVariable(unit, "item$", currentOwner, pos, false, newApply(tree.pos, aIdentGen(), iIdentGen()))
        val loopOuters = outerStatements(new LoopOutersEnv(aIdentGen, nIdentGen))
        val loopInners = new LoopInnersEnv[Payload](aIdentGen, nIdentGen, iIdentGen, itemIdentGen, loopOuters.payload)
        typed {
          treeCopy.Block(
            tree,
            List(
              aDef,
              nDef,
              iDef
            ) ++
            loopOuters.statements ++
            List(
              whileLoop(
                currentOwner,
                unit,
                tree,
                (
                  if (reverseOrder) // while (i > 0) { i--; statements }
                    binOp(
                      iIdentGen(),
                      IntClass.tpe.member(nme.GT),
                      newInt(0)
                    )
                  else // while (i < n) { statements; i++ }
                    binOp(
                      iIdentGen(),
                      IntClass.tpe.member(nme.LT),
                      nIdentGen()
                    )
                ),
                typed {
                  val itemAndInnerStats =
                    List(itemDef) ++
                    innerStatements(loopInners)

                  if (reverseOrder)
                    Block(
                      List(decrementIntVar(iIdentGen, newInt(1))) ++
                      itemAndInnerStats,
                      newUnit
                    )
                  else
                    Block(
                      itemAndInnerStats,
                      incrementIntVar(iIdentGen, newInt(1))
                    )
                }
              )
            ),
            if (loopOuters.finalReturnValue == null)
              newUnit
            else
              loopOuters.finalReturnValue
          )
        }
      }
    }
    case object ListCol extends ColTree {
      override val supportsRightVariants = false
      override def foreach[Payload](
        tree: Tree,
        collection: Tree,
        componentType: Symbol,
        reverseOrder: Boolean,
        skipFirst: Boolean,
        outerStatements: LoopOutersEnv => LoopOuters[Payload],
        innerStatements: LoopInnersEnv[Payload] => List[Tree]
      ): Tree = {
        assert(!reverseOrder)
        val pos = tree.pos
        val colTpe = collection.tpe
        val (aIdentGen, aSym, aDef) = newVariable(unit, "list$", currentOwner, pos, true, collection)
        val (itemIdentGen, itemSym, itemDef) = newVariable(unit, "item$", currentOwner, pos, false, typed { 
            Select(aIdentGen(), headName).setSymbol(colTpe.member(headName)).setType(componentType.tpe)
        })
        val loopOuters = outerStatements(new LoopOutersEnv(aIdentGen, null))
        val loopInners = new LoopInnersEnv[Payload](aIdentGen, null, null, itemIdentGen, loopOuters.payload)
        typed {
          treeCopy.Block(
            tree,
            List(
              aDef
            ) ++
            loopOuters.statements ++
            List(
              whileLoop(
                currentOwner,
                unit,
                tree,
                boolNot(Select(aIdentGen(), isEmptyName).setSymbol(colTpe.member(isEmptyName)).setType(BooleanClass.tpe)),
                typed {
                  val itemAndInnerStats =
                    List(itemDef) ++
                    innerStatements(loopInners)

                  Block(
                    itemAndInnerStats,
                    Assign(
                      aIdentGen(),
                      Select(aIdentGen(), tailName).setSymbol(colTpe.member(tailName)).setType(colTpe)
                    ).setType(UnitClass.tpe)
                  )
                }
              )
            ),
            if (loopOuters.finalReturnValue == null)
              newUnit
            else
              loopOuters.finalReturnValue
          )
        }
      }
    }
    def newArray(arrayType: Type, length: => Tree) =
      typed {
        Apply(
          localTyper.typedOperator {
            Select(
              New(TypeTree(arrayType)),
              arrayType.typeSymbol.primaryConstructor
            )
          },
          List(length)
        )
      }

    override def transform(tree: Tree): Tree = {
      if (!shouldOptimize(tree))
        super.transform(tree)
      else
        try {
          tree match {
            case ArrayTabulate(componentType, lengths @ (firstLength :: otherLengths), f @ Func(params, body)) =>
              val tpe = body.tpe
              val returnType = if (tpe.isInstanceOf[ConstantType]) 
                tpe.widen
              else
                tpe
              
              val lengthDefs = lengths.map(length => newVariable(unit, "n$", currentOwner, tree.pos, false, length.setType(IntClass.tpe)))
              
              msg(unit, tree.pos, "transformed Array.tabulate[" + returnType + "] into equivalent while loop") {
                def replaceTabulates(lengthDefs: List[(TreeGen, Symbol, ValDef)], defineLengths: Boolean, params: List[ValDef], mappings: Map[Symbol, TreeGen]): (Tree, Type) = {
              
                  val param = params.head
                  val pos = tree.pos
                  val (nIdentGen, _, nDef) = lengthDefs.head
                  val (iIdentGen, _, iDef) = newVariable(unit, "i$", currentOwner, pos, true, newInt(0))
                  
                  val newMappings = mappings + (param.symbol -> iIdentGen)
                  val (newBody, bodyType) = if (lengthDefs.tail == Nil)
                      (
                          replaceOccurrences(
                            body,
                            newMappings,
                            Map(f.symbol -> currentOwner),
                            unit
                          ),
                          returnType
                      )
                  else
                      replaceTabulates(
                        lengthDefs.tail,
                        false,
                        params.tail,
                        newMappings
                      )
                  
                  newBody.setType(bodyType)
                  
                  val mappedArrayTpe = appliedType(ArrayClass.tpe, List(bodyType))
                  val (mIdentGen, _, mDef) = newVariable(unit, "m$", currentOwner, tree.pos, false, newArray(mappedArrayTpe, nIdentGen()))
                  (
                    super.transform {
                      typed {
                        treeCopy.Block(
                          tree,
                          (
                            if (defineLengths) 
                                lengthDefs.map(_._3) 
                            else 
                                Nil
                          ) ++
                          List(
                            mDef,
                            iDef,
                            whileLoop(
                              currentOwner,
                              unit,
                              tree,
                              binOp(
                                iIdentGen(),
                                IntClass.tpe.member(nme.LT),
                                nIdentGen()
                              ),
                              //typed {
                                Block(
                                  newUpdate(
                                    tree.pos,
                                    mIdentGen(),
                                    iIdentGen(),
                                    newBody
                                  ),
                                  incrementIntVar(iIdentGen, newInt(1))
                                )
                              //}
                            )
                          ),
                          mIdentGen()
                        )
                      }
                    },
                    mappedArrayTpe
                  )
                }
                replaceTabulates(lengthDefs, true, params, Map())._1
              }
            case ArrayMap(array, componentType, mappedComponentType, mappedArrayType, f) =>
              f match {
                case Func(List(param), body) =>
                  msg(unit, tree.pos, "transformed Array.map into equivalent while loop.") {
                    array.tpe = appliedType(ArrayClass.tpe, List(componentType.tpe))
                    typed {
                      super.transform(
                        ArrayCol.foreach[IdentGen](
                          tree,
                          array,
                          componentType,
                          false,
                          false,
                          env => {
                            //val mappedArrayTpe = appliedType(ArrayClass.tpe, List(mappedComponentType.tpe))
                            val (mIdentGen, _, mDef) = newVariable(
                              unit,
                              "m$",
                              currentOwner,
                              tree.pos,
                              false,
                              newArray(
                                //mappedArrayTpe,
                                mappedArrayType.tpe,
                                env.nIdentGen()
                              )
                            )
                            new LoopOuters(List(mDef), mIdentGen(), payload = mIdentGen)
                          },
                          env => {
                            val mIdentGen = env.payload
                            List(
                              newUpdate(
                                tree.pos,
                                mIdentGen(),
                                env.iIdentGen(),
                                replaceOccurrences(
                                  body,
                                  Map(param.symbol -> env.itemIdentGen),
                                  Map(f.symbol -> currentOwner),
                                  unit
                                )
                              )
                            )
                          }
                        )
                      )
                    }
                  }
                case _ =>
                  super.transform(tree)
              }
            case Foreach(collection, f @ Func(List(param), body)) =>
              collection match {
                case ColTree(colType, tpe, array, componentType) =>
                  msg(unit, tree.pos, "transformed " + tpe + ".foreach into equivalent while loop.") {
                    array.tpe = tpe
                    typed {
                      super.transform(
                        colType.foreach[Unit](
                          tree,
                          array,
                          componentType,
                          false,
                          false,
                          env => new LoopOuters(Nil, null, payload = ()), // no extra outer statement
                          env => List(
                            replaceOccurrences(
                              body,
                              Map(param.symbol -> env.itemIdentGen),
                              Map(f.symbol -> currentOwner),
                              unit
                            )
                          )
                        )
                      )
                    }
                  }
                case _ =>
                  super.transform(tree)
              }
              
            case TraversalOp(op, collection, resultType, f @ Func(List(leftParam, rightParam), body), isLeft, initialValue) =>
              val accParam = if (isLeft) leftParam else rightParam
              val newParam = if (isLeft) rightParam else leftParam
              collection match {
                case ColTree(colType, tpe, array, componentType) =>
                  if (isLeft || colType.supportsRightVariants)
                    msg(unit, tree.pos, "transformed " + tpe + "." + op + (if (isLeft) "Left" else "Right") + " into equivalent while loop.") {
                      array.tpe = tpe
                      super.transform(
                        op match {
                          case Reduce | Fold =>
                            val isReduce = op == Reduce
                            colType.foreach[IdentGen](
                              tree,
                              array,
                              componentType,
                              !isLeft,
                              isReduce,
                              env => {
                                assert((initialValue == null) == isReduce) // no initial value for reduce only
                                val (totIdentGen, _, totDef) = newVariable(unit, "tot$", currentOwner, tree.pos, true,
                                  if (initialValue == null)
                                    newApply(
                                      tree.pos,
                                      env.aIdentGen(),
                                      if (isLeft)
                                        newInt(0)
                                      else
                                        intAdd(env.nIdentGen(), newInt(-1))
                                    )
                                  else
                                    initialValue
                                )
                                new LoopOuters(List(totDef), totIdentGen(), payload = totIdentGen)
                              },
                              env => {
                                val totIdentGen = env.payload
                                List(
                                  Assign(
                                    totIdentGen(),
                                    replaceOccurrences(
                                      body,
                                      Map(
                                        accParam.symbol -> totIdentGen,
                                        newParam.symbol -> env.itemIdentGen
                                      ),
                                      Map(f.symbol -> currentOwner),
                                      unit
                                    )
                                  ).setType(UnitClass.tpe)
                                )
                              }
                            )
                          case Scan =>
                            val mappedArrayTpe = appliedType(ArrayClass.tpe, List(resultType.tpe))
                            colType.foreach[(IdentGen, IdentGen, Symbol)](
                              tree,
                              array,
                              componentType,
                              !isLeft,
                              false,
                              env => {
                                val (mIdentGen, _, mDef) = newVariable(unit, "m$", currentOwner, tree.pos, false, newArray(mappedArrayTpe, intAdd(env.nIdentGen(), newInt(1))))
                                val (totIdentGen, totSym, totDef) = newVariable(unit, "tot$", currentOwner, tree.pos, true, initialValue)//.setType(IntClass.tpe))
                                new LoopOuters(
                                  List(
                                    totDef,
                                    mDef,
                                    newUpdate(tree.pos, mIdentGen(), newInt(0), totIdentGen())
                                  ),
                                  mIdentGen(),
                                  payload = (mIdentGen, totIdentGen, totSym)
                                )
                              },
                              env => {
                                val (mIdentGen, totIdentGen, totSym) = env.payload
                                List(
                                  Assign(
                                    totIdentGen(),
                                    replaceOccurrences(
                                      body,
                                      Map(
                                        accParam.symbol -> totIdentGen,
                                        newParam.symbol -> env.itemIdentGen
                                      ),
                                      Map(f.symbol -> currentOwner),
                                      unit
                                    )
                                  ).setType(UnitClass.tpe),
                                  newUpdate(
                                    tree.pos,
                                    mIdentGen(),
                                    if (isLeft)
                                      intAdd(env.iIdentGen(), newInt(1))
                                    else
                                      intSub(env.nIdentGen(), env.iIdentGen()),
                                    totIdentGen())
                                )
                              }
                            )
                        }
                      )
                    }
                  else
                    super.transform(tree)
                case _ =>
                  super.transform(tree)
              }
            case _ =>
              super.transform(tree)
          }
        } catch {
          case ex =>
            //if (ScalaCLPlugin.trace)
            //  ex.printStackTrace
            super.transform(tree)
        }
    }
  }
}