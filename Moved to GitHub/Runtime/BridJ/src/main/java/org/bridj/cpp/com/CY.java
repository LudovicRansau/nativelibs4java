package org.bridj.cpp.com;
import org.bridj.CRuntime;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
import org.bridj.ann.Runtime;
/**
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Runtime(CRuntime.class) 
public class CY extends StructObject {
	public CY() {
		super();
	}
	public CY(Pointer pointer) {
		super(pointer);
	}
	@Field(0) 
	public long int64() {
		return this.io.getLongField(this, 0);
	}
	@Field(0) 
	public CY int64(long int64) {
		this.io.setLongField(this, 0, int64);
		return this;
	}
	public final long int64_$eq(long int64) {
		int64(int64);
		return int64;
	}
}

