package com.nativelibs4java.mono.bridj;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
/**
 * <i>native declaration : /Library/Frameworks/Mono.framework/Headers/mono-2.0/mono/metadata/mono-debug.h:77</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("mono") 
public class MonoDebugMethodJitInfo extends StructObject {
	public MonoDebugMethodJitInfo() {
		super();
	}
	public MonoDebugMethodJitInfo(Pointer pointer) {
		super(pointer);
	}
	/// C type : const mono_byte*
	@Field(0) 
	public Pointer<Byte > code_start() {
		return this.io.getPointerField(this, 0);
	}
	/// C type : const mono_byte*
	@Field(0) 
	public MonoDebugMethodJitInfo code_start(Pointer<Byte > code_start) {
		this.io.setPointerField(this, 0, code_start);
		return this;
	}
	@Field(1) 
	public int code_size() {
		return this.io.getIntField(this, 1);
	}
	@Field(1) 
	public MonoDebugMethodJitInfo code_size(int code_size) {
		this.io.setIntField(this, 1, code_size);
		return this;
	}
	@Field(2) 
	public int prologue_end() {
		return this.io.getIntField(this, 2);
	}
	@Field(2) 
	public MonoDebugMethodJitInfo prologue_end(int prologue_end) {
		this.io.setIntField(this, 2, prologue_end);
		return this;
	}
	@Field(3) 
	public int epilogue_begin() {
		return this.io.getIntField(this, 3);
	}
	@Field(3) 
	public MonoDebugMethodJitInfo epilogue_begin(int epilogue_begin) {
		this.io.setIntField(this, 3, epilogue_begin);
		return this;
	}
	/// C type : const mono_byte*
	@Field(4) 
	public Pointer<Byte > wrapper_addr() {
		return this.io.getPointerField(this, 4);
	}
	/// C type : const mono_byte*
	@Field(4) 
	public MonoDebugMethodJitInfo wrapper_addr(Pointer<Byte > wrapper_addr) {
		this.io.setPointerField(this, 4, wrapper_addr);
		return this;
	}
	@Field(5) 
	public int num_line_numbers() {
		return this.io.getIntField(this, 5);
	}
	@Field(5) 
	public MonoDebugMethodJitInfo num_line_numbers(int num_line_numbers) {
		this.io.setIntField(this, 5, num_line_numbers);
		return this;
	}
	/// C type : MonoDebugLineNumberEntry*
	@Field(6) 
	public Pointer<MonoDebugLineNumberEntry > line_numbers() {
		return this.io.getPointerField(this, 6);
	}
	/// C type : MonoDebugLineNumberEntry*
	@Field(6) 
	public MonoDebugMethodJitInfo line_numbers(Pointer<MonoDebugLineNumberEntry > line_numbers) {
		this.io.setPointerField(this, 6, line_numbers);
		return this;
	}
	@Field(7) 
	public int num_params() {
		return this.io.getIntField(this, 7);
	}
	@Field(7) 
	public MonoDebugMethodJitInfo num_params(int num_params) {
		this.io.setIntField(this, 7, num_params);
		return this;
	}
	/// C type : MonoDebugVarInfo*
	@Field(8) 
	public Pointer<MonoDebugVarInfo > this_var() {
		return this.io.getPointerField(this, 8);
	}
	/// C type : MonoDebugVarInfo*
	@Field(8) 
	public MonoDebugMethodJitInfo this_var(Pointer<MonoDebugVarInfo > this_var) {
		this.io.setPointerField(this, 8, this_var);
		return this;
	}
	/// C type : MonoDebugVarInfo*
	@Field(9) 
	public Pointer<MonoDebugVarInfo > params() {
		return this.io.getPointerField(this, 9);
	}
	/// C type : MonoDebugVarInfo*
	@Field(9) 
	public MonoDebugMethodJitInfo params(Pointer<MonoDebugVarInfo > params) {
		this.io.setPointerField(this, 9, params);
		return this;
	}
	@Field(10) 
	public int num_locals() {
		return this.io.getIntField(this, 10);
	}
	@Field(10) 
	public MonoDebugMethodJitInfo num_locals(int num_locals) {
		this.io.setIntField(this, 10, num_locals);
		return this;
	}
	/// C type : MonoDebugVarInfo*
	@Field(11) 
	public Pointer<MonoDebugVarInfo > locals() {
		return this.io.getPointerField(this, 11);
	}
	/// C type : MonoDebugVarInfo*
	@Field(11) 
	public MonoDebugMethodJitInfo locals(Pointer<MonoDebugVarInfo > locals) {
		this.io.setPointerField(this, 11, locals);
		return this;
	}
}
