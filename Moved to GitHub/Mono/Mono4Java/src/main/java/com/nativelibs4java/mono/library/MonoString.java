package com.nativelibs4java.mono.library;
/**
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a>, <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class MonoString extends com.ochafik.lang.jnaerator.runtime.Structure<MonoString, MonoString.ByValue, MonoString.ByReference> {
	/// C type : MonoObject
	public com.nativelibs4java.mono.library.MonoObject object;
	public int length;
	/// C type : gunichar2[1]
	public short[] chars = new short[(1)];
	public MonoString() {
		super();
	}
	/**
	 * @param object C type : MonoObject<br>
	 * @param chars C type : gunichar2[1]
	 */
	public MonoString(com.nativelibs4java.mono.library.MonoObject object, int length, short chars[]) {
		super();
		this.object = object;
		this.length = length;
		if (chars.length != this.chars.length) 
			throw new java.lang.IllegalArgumentException("Wrong array size !");
		this.chars = chars;
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected MonoString newInstance() { return new MonoString(); }
	public static MonoString[] newArray(int arrayLength) {
		return com.ochafik.lang.jnaerator.runtime.Structure.newArray(MonoString.class, arrayLength);
	}
	public static class ByReference extends MonoString implements com.sun.jna.Structure.ByReference {}
	public static class ByValue extends MonoString implements com.sun.jna.Structure.ByValue {}
}
