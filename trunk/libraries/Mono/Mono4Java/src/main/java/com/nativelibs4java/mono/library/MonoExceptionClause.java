package com.nativelibs4java.mono.library;
/**
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a>, <a href="http://rococoa.dev.java.net/">Rococoa</a>, or <a href="http://jna.dev.java.net/">JNA</a>.
 */
public class MonoExceptionClause extends com.ochafik.lang.jnaerator.runtime.Structure<MonoExceptionClause, MonoExceptionClause.ByValue, MonoExceptionClause.ByReference> {
	public int flags;
	public int try_offset;
	public int try_len;
	public int handler_offset;
	public int handler_len;
	/// C type : data_union
	public data_union data;
	public static class data_union extends com.ochafik.lang.jnaerator.runtime.Union<data_union, data_union.ByValue, data_union.ByReference> {
		public int filter_offset;
		/// C type : MonoClass*
		public com.nativelibs4java.mono.library.MonoLibrary.MonoClass catch_class;
		public data_union() {
			super();
		}
		public data_union(int filter_offset) {
			super();
			this.filter_offset = filter_offset;
			setType(java.lang.Integer.TYPE);
		}
		/// @param catch_class C type : MonoClass*
		public data_union(com.nativelibs4java.mono.library.MonoLibrary.MonoClass catch_class) {
			super();
			this.catch_class = catch_class;
			setType(com.nativelibs4java.mono.library.MonoLibrary.MonoClass.class);
		}
		protected ByReference newByReference() { return new ByReference(); }
		protected ByValue newByValue() { return new ByValue(); }
		protected data_union newInstance() { return new data_union(); }
		public static data_union[] newArray(int arrayLength) {
			return com.ochafik.lang.jnaerator.runtime.Union.newArray(data_union.class, arrayLength);
		}
		public static class ByReference extends data_union implements com.sun.jna.Structure.ByReference {}
		public static class ByValue extends data_union implements com.sun.jna.Structure.ByValue {}
	}
	public MonoExceptionClause() {
		super();
	}
	/// @param data C type : data_union
	public MonoExceptionClause(int flags, int try_offset, int try_len, int handler_offset, int handler_len, data_union data) {
		super();
		this.flags = flags;
		this.try_offset = try_offset;
		this.try_len = try_len;
		this.handler_offset = handler_offset;
		this.handler_len = handler_len;
		this.data = data;
	}
	protected ByReference newByReference() { return new ByReference(); }
	protected ByValue newByValue() { return new ByValue(); }
	protected MonoExceptionClause newInstance() { return new MonoExceptionClause(); }
	public static MonoExceptionClause[] newArray(int arrayLength) {
		return com.ochafik.lang.jnaerator.runtime.Structure.newArray(MonoExceptionClause.class, arrayLength);
	}
	public static class ByReference extends MonoExceptionClause implements com.sun.jna.Structure.ByReference {}
	public static class ByValue extends MonoExceptionClause implements com.sun.jna.Structure.ByValue {}
}