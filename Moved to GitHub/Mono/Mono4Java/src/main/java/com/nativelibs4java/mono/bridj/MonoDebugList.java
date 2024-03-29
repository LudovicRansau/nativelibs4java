package com.nativelibs4java.mono.bridj;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
/**
 * NOTE:<br>
 * We intentionally do not use GList here since the debugger needs to know about<br>
 * the layout of the fields.<br>
 * <i>native declaration : /Library/Frameworks/Mono.framework/Headers/mono-2.0/mono/metadata/mono-debug.h:46</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("mono") 
public class MonoDebugList extends StructObject {
	public MonoDebugList() {
		super();
	}
	public MonoDebugList(Pointer pointer) {
		super(pointer);
	}
	/// C type : MonoDebugList*
	@Field(0) 
	public Pointer<MonoDebugList > next() {
		return this.io.getPointerField(this, 0);
	}
	/// C type : MonoDebugList*
	@Field(0) 
	public MonoDebugList next(Pointer<MonoDebugList > next) {
		this.io.setPointerField(this, 0, next);
		return this;
	}
	/// C type : const void*
	@Field(1) 
	public Pointer<? > data() {
		return this.io.getPointerField(this, 1);
	}
	/// C type : const void*
	@Field(1) 
	public MonoDebugList data(Pointer<? > data) {
		this.io.setPointerField(this, 1, data);
		return this;
	}
}
