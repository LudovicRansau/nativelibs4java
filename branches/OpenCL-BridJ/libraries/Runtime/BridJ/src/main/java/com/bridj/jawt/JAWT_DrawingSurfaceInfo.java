package com.bridj.jawt;
import com.bridj.Pointer;
import com.bridj.StructObject;
import com.bridj.ann.Field;
import com.bridj.ann.Library;
/**
 * <i>native declaration : jawt.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("jawt") 
public class JAWT_DrawingSurfaceInfo extends StructObject {
	public JAWT_DrawingSurfaceInfo() {
		super();
	}
	public JAWT_DrawingSurfaceInfo(Pointer pointer) {
		super(pointer);
	}
	/**
	 * Pointer to the platform-specific information.  This can be safely<br>
	 * cast to a JAWT_Win32DrawingSurfaceInfo on Windows or a<br>
	 * JAWT_X11DrawingSurfaceInfo on Solaris.  See jawt_md.h for details.<br>
	 * C type : void*
	 */
	@Field(0) 
	public native Pointer platformInfo();
	/**
	 * Pointer to the platform-specific information.  This can be safely<br>
	 * cast to a JAWT_Win32DrawingSurfaceInfo on Windows or a<br>
	 * JAWT_X11DrawingSurfaceInfo on Solaris.  See jawt_md.h for details.<br>
	 * C type : void*
	 */
	@Field(0) 
	public native JAWT_DrawingSurfaceInfo platformInfo(Pointer platformInfo);
	/**
	 * Cached pointer to the underlying drawing surface<br>
	 * C type : jawt_DrawingSurface*
	 */
	@Field(1) 
	public native Pointer ds();
	/**
	 * Cached pointer to the underlying drawing surface<br>
	 * C type : jawt_DrawingSurface*
	 */
	@Field(1) 
	public native JAWT_DrawingSurfaceInfo ds(Pointer ds);
	/**
	 * Bounding rectangle of the drawing surface<br>
	 * C type : JAWT_Rectangle
	 */
	@Field(2) 
	public native com.bridj.jawt.JAWT_Rectangle bounds();
	/**
	 * Bounding rectangle of the drawing surface<br>
	 * C type : JAWT_Rectangle
	 */
	@Field(2) 
	public native JAWT_DrawingSurfaceInfo bounds(com.bridj.jawt.JAWT_Rectangle bounds);
	/// Number of rectangles in the clip
	@Field(3) 
	public native int clipSize();
	/// Number of rectangles in the clip
	@Field(3) 
	public native JAWT_DrawingSurfaceInfo clipSize(int clipSize);
	/**
	 * Clip rectangle array<br>
	 * C type : JAWT_Rectangle*
	 */
	@Field(4) 
	public native com.bridj.jawt.JAWT_Rectangle clip();
	/**
	 * Clip rectangle array<br>
	 * C type : JAWT_Rectangle*
	 */
	@Field(4) 
	public native JAWT_DrawingSurfaceInfo clip(com.bridj.jawt.JAWT_Rectangle clip);
}