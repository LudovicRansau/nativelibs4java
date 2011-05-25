package com.nativelibs4java.mono.bridj;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
/**
 * <i>native declaration : /Library/Frameworks/Mono.framework/Headers/mono-2.0/mono/metadata/mono-debug.h:98</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("mono") 
public class MonoDebugSourceLocation extends StructObject {
	public MonoDebugSourceLocation() {
		super();
	}
	public MonoDebugSourceLocation(Pointer pointer) {
		super(pointer);
	}
	/// C type : char*
	@Field(0) 
	public Pointer<Byte > source_file() {
		return this.io.getPointerField(this, 0);
	}
	/// C type : char*
	@Field(0) 
	public MonoDebugSourceLocation source_file(Pointer<Byte > source_file) {
		this.io.setPointerField(this, 0, source_file);
		return this;
	}
	@Field(1) 
	public int row() {
		return this.io.getIntField(this, 1);
	}
	@Field(1) 
	public MonoDebugSourceLocation row(int row) {
		this.io.setIntField(this, 1, row);
		return this;
	}
	@Field(2) 
	public int column() {
		return this.io.getIntField(this, 2);
	}
	@Field(2) 
	public MonoDebugSourceLocation column(int column) {
		this.io.setIntField(this, 2, column);
		return this;
	}
	@Field(3) 
	public int il_offset() {
		return this.io.getIntField(this, 3);
	}
	@Field(3) 
	public MonoDebugSourceLocation il_offset(int il_offset) {
		this.io.setIntField(this, 3, il_offset);
		return this;
	}
}