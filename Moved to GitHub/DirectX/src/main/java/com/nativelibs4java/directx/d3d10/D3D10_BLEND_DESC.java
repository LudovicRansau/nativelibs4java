package com.nativelibs4java.directx.d3d10;
import com.nativelibs4java.directx.d3d10.D3d10Library.D3D10_BLEND;
import com.nativelibs4java.directx.d3d10.D3d10Library.D3D10_BLEND_OP;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ValuedEnum;
import org.bridj.ann.Array;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
/**
 * <i>native declaration : d3d10.h:255</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("d3d10") 
public class D3D10_BLEND_DESC extends StructObject {
	public D3D10_BLEND_DESC() {
		super();
	}
	public D3D10_BLEND_DESC(Pointer pointer) {
		super(pointer);
	}
	@Field(0) 
	public int AlphaToCoverageEnable() {
		return this.io.getIntField(this, 0);
	}
	@Field(0) 
	public D3D10_BLEND_DESC AlphaToCoverageEnable(int AlphaToCoverageEnable) {
		this.io.setIntField(this, 0, AlphaToCoverageEnable);
		return this;
	}
	/// C type : BOOL[8]
	@Array({8}) 
	@Field(1) 
	public Pointer<Integer > BlendEnable() {
		return this.io.getPointerField(this, 1);
	}
	/// C type : D3D10_BLEND
	@Field(2) 
	public ValuedEnum<D3D10_BLEND > SrcBlend() {
		return this.io.getEnumField(this, 2);
	}
	/// C type : D3D10_BLEND
	@Field(2) 
	public D3D10_BLEND_DESC SrcBlend(ValuedEnum<D3D10_BLEND > SrcBlend) {
		this.io.setEnumField(this, 2, SrcBlend);
		return this;
	}
	/// C type : D3D10_BLEND
	@Field(3) 
	public ValuedEnum<D3D10_BLEND > DestBlend() {
		return this.io.getEnumField(this, 3);
	}
	/// C type : D3D10_BLEND
	@Field(3) 
	public D3D10_BLEND_DESC DestBlend(ValuedEnum<D3D10_BLEND > DestBlend) {
		this.io.setEnumField(this, 3, DestBlend);
		return this;
	}
	/// C type : D3D10_BLEND_OP
	@Field(4) 
	public ValuedEnum<D3D10_BLEND_OP > BlendOp() {
		return this.io.getEnumField(this, 4);
	}
	/// C type : D3D10_BLEND_OP
	@Field(4) 
	public D3D10_BLEND_DESC BlendOp(ValuedEnum<D3D10_BLEND_OP > BlendOp) {
		this.io.setEnumField(this, 4, BlendOp);
		return this;
	}
	/// C type : D3D10_BLEND
	@Field(5) 
	public ValuedEnum<D3D10_BLEND > SrcBlendAlpha() {
		return this.io.getEnumField(this, 5);
	}
	/// C type : D3D10_BLEND
	@Field(5) 
	public D3D10_BLEND_DESC SrcBlendAlpha(ValuedEnum<D3D10_BLEND > SrcBlendAlpha) {
		this.io.setEnumField(this, 5, SrcBlendAlpha);
		return this;
	}
	/// C type : D3D10_BLEND
	@Field(6) 
	public ValuedEnum<D3D10_BLEND > DestBlendAlpha() {
		return this.io.getEnumField(this, 6);
	}
	/// C type : D3D10_BLEND
	@Field(6) 
	public D3D10_BLEND_DESC DestBlendAlpha(ValuedEnum<D3D10_BLEND > DestBlendAlpha) {
		this.io.setEnumField(this, 6, DestBlendAlpha);
		return this;
	}
	/// C type : D3D10_BLEND_OP
	@Field(7) 
	public ValuedEnum<D3D10_BLEND_OP > BlendOpAlpha() {
		return this.io.getEnumField(this, 7);
	}
	/// C type : D3D10_BLEND_OP
	@Field(7) 
	public D3D10_BLEND_DESC BlendOpAlpha(ValuedEnum<D3D10_BLEND_OP > BlendOpAlpha) {
		this.io.setEnumField(this, 7, BlendOpAlpha);
		return this;
	}
	/// C type : UINT8[8]
	@Array({8}) 
	@Field(8) 
	public Pointer<Byte > RenderTargetWriteMask() {
		return this.io.getPointerField(this, 8);
	}
}
