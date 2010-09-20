package com.nativelibs4java.ffmpeg.avcodec;
import static com.nativelibs4java.ffmpeg.avcodec.AvcodecLibrary.*;
import static com.nativelibs4java.ffmpeg.avformat.AvformatLibrary.*;
import static com.nativelibs4java.ffmpeg.avutil.AvutilLibrary.*;
import static com.nativelibs4java.ffmpeg.swscale.SwscaleLibrary.*;
import org.bridj.Callback;
import org.bridj.Pointer;
import org.bridj.StructObject;
import org.bridj.ValuedEnum;
import org.bridj.ann.Field;
import org.bridj.ann.Library;
/**
 * <i>native declaration : libavcodec/avcodec.h</i><br>
 * This file was autogenerated by <a href="http://jnaerator.googlecode.com/">JNAerator</a>,<br>
 * a tool written by <a href="http://ochafik.free.fr/">Olivier Chafik</a> that <a href="http://code.google.com/p/jnaerator/wiki/CreditsAndLicense">uses a few opensource projects.</a>.<br>
 * For help, please visit <a href="http://nativelibs4java.googlecode.com/">NativeLibs4Java</a> or <a href="http://bridj.googlecode.com/">BridJ</a> .
 */
@Library("avcodec") 
public class AVCodec extends StructObject {
	public AVCodec() {
		super();
	}
	public AVCodec(Pointer pointer) {
		super(pointer);
	}
	/**
	 * Name of the codec implementation.<br>
	 * The name is globally unique among encoders and among decoders (but an<br>
	 * encoder and a decoder can share the same name).<br>
	 * This is the primary way to find a codec from the user perspective.<br>
	 * C type : const char*
	 */
	@Field(0) 
	public Pointer<java.lang.Byte > name() {
		return this.io.getPointerField(this, 0);
	}
	/**
	 * Name of the codec implementation.<br>
	 * The name is globally unique among encoders and among decoders (but an<br>
	 * encoder and a decoder can share the same name).<br>
	 * This is the primary way to find a codec from the user perspective.<br>
	 * C type : const char*
	 */
	@Field(0) 
	public AVCodec name(Pointer<java.lang.Byte > name) {
		this.io.setPointerField(this, 0, name);
		return this;
	}
	/// C type : const char*
	public final Pointer<java.lang.Byte > name_$eq(Pointer<java.lang.Byte > name) {
		name(name);
		return name;
	}
	/// C type : AVMediaType
	@Field(1) 
	public ValuedEnum<AVMediaType > type() {
		return this.io.getEnumField(this, 1);
	}
	/// C type : AVMediaType
	@Field(1) 
	public AVCodec type(ValuedEnum<AVMediaType > type) {
		this.io.setEnumField(this, 1, type);
		return this;
	}
	/// C type : AVMediaType
	public final ValuedEnum<AVMediaType > type_$eq(ValuedEnum<AVMediaType > type) {
		type(type);
		return type;
	}
	/// C type : CodecID
	@Field(2) 
	public ValuedEnum<CodecID > id() {
		return this.io.getEnumField(this, 2);
	}
	/// C type : CodecID
	@Field(2) 
	public AVCodec id(ValuedEnum<CodecID > id) {
		this.io.setEnumField(this, 2, id);
		return this;
	}
	/// C type : CodecID
	public final ValuedEnum<CodecID > id_$eq(ValuedEnum<CodecID > id) {
		id(id);
		return id;
	}
	@Field(3) 
	public int priv_data_size() {
		return this.io.getIntField(this, 3);
	}
	@Field(3) 
	public AVCodec priv_data_size(int priv_data_size) {
		this.io.setIntField(this, 3, priv_data_size);
		return this;
	}
	public final int priv_data_size_$eq(int priv_data_size) {
		priv_data_size(priv_data_size);
		return priv_data_size;
	}
	/// C type : init_callback
	@Field(4) 
	public Pointer<com.nativelibs4java.ffmpeg.avfilter.AVFilter.init_callback > init() {
		return this.io.getPointerField(this, 4);
	}
	/// C type : init_callback
	@Field(4) 
	public AVCodec init(Pointer<com.nativelibs4java.ffmpeg.avfilter.AVFilter.init_callback > init) {
		this.io.setPointerField(this, 4, init);
		return this;
	}
	/// C type : init_callback
	public final Pointer<com.nativelibs4java.ffmpeg.avfilter.AVFilter.init_callback > init_$eq(Pointer<com.nativelibs4java.ffmpeg.avfilter.AVFilter.init_callback > init) {
		init(init);
		return init;
	}
	/// C type : encode_callback
	@Field(5) 
	public Pointer<AVCodec.encode_callback > encode() {
		return this.io.getPointerField(this, 5);
	}
	/// C type : encode_callback
	@Field(5) 
	public AVCodec encode(Pointer<AVCodec.encode_callback > encode) {
		this.io.setPointerField(this, 5, encode);
		return this;
	}
	/// C type : encode_callback
	public final Pointer<AVCodec.encode_callback > encode_$eq(Pointer<AVCodec.encode_callback > encode) {
		encode(encode);
		return encode;
	}
	/// C type : close_callback
	@Field(6) 
	public Pointer<com.nativelibs4java.ffmpeg.avcodec.AVBitStreamFilter.close_callback > close() {
		return this.io.getPointerField(this, 6);
	}
	/// C type : close_callback
	@Field(6) 
	public AVCodec close(Pointer<com.nativelibs4java.ffmpeg.avcodec.AVBitStreamFilter.close_callback > close) {
		this.io.setPointerField(this, 6, close);
		return this;
	}
	/// C type : close_callback
	public final Pointer<com.nativelibs4java.ffmpeg.avcodec.AVBitStreamFilter.close_callback > close_$eq(Pointer<com.nativelibs4java.ffmpeg.avcodec.AVBitStreamFilter.close_callback > close) {
		close(close);
		return close;
	}
	/// C type : decode_callback
	@Field(7) 
	public Pointer<AVCodec.decode_callback > decode() {
		return this.io.getPointerField(this, 7);
	}
	/// C type : decode_callback
	@Field(7) 
	public AVCodec decode(Pointer<AVCodec.decode_callback > decode) {
		this.io.setPointerField(this, 7, decode);
		return this;
	}
	/// C type : decode_callback
	public final Pointer<AVCodec.decode_callback > decode_$eq(Pointer<AVCodec.decode_callback > decode) {
		decode(decode);
		return decode;
	}
	/**
	 * Codec capabilities.<br>
	 * see CODEC_CAP_*
	 */
	@Field(8) 
	public int capabilities() {
		return this.io.getIntField(this, 8);
	}
	/**
	 * Codec capabilities.<br>
	 * see CODEC_CAP_*
	 */
	@Field(8) 
	public AVCodec capabilities(int capabilities) {
		this.io.setIntField(this, 8, capabilities);
		return this;
	}
	public final int capabilities_$eq(int capabilities) {
		capabilities(capabilities);
		return capabilities;
	}
	/// C type : AVCodec*
	@Field(9) 
	public Pointer<AVCodec > next() {
		return this.io.getPointerField(this, 9);
	}
	/// C type : AVCodec*
	@Field(9) 
	public AVCodec next(Pointer<AVCodec > next) {
		this.io.setPointerField(this, 9, next);
		return this;
	}
	/// C type : AVCodec*
	public final Pointer<AVCodec > next_$eq(Pointer<AVCodec > next) {
		next(next);
		return next;
	}
	/**
	 * Flush buffers.<br>
	 * Will be called when seeking<br>
	 * C type : flush_callback
	 */
	@Field(10) 
	public Pointer<AVCodec.flush_callback > flush() {
		return this.io.getPointerField(this, 10);
	}
	/**
	 * Flush buffers.<br>
	 * Will be called when seeking<br>
	 * C type : flush_callback
	 */
	@Field(10) 
	public AVCodec flush(Pointer<AVCodec.flush_callback > flush) {
		this.io.setPointerField(this, 10, flush);
		return this;
	}
	/// C type : flush_callback
	public final Pointer<AVCodec.flush_callback > flush_$eq(Pointer<AVCodec.flush_callback > flush) {
		flush(flush);
		return flush;
	}
	/**
	 * < array of supported framerates, or NULL if any, array is terminated by {0,0}<br>
	 * C type : const AVRational*
	 */
	@Field(11) 
	public Pointer<? > supported_framerates() {
		return this.io.getPointerField(this, 11);
	}
	/**
	 * < array of supported framerates, or NULL if any, array is terminated by {0,0}<br>
	 * C type : const AVRational*
	 */
	@Field(11) 
	public AVCodec supported_framerates(Pointer<? > supported_framerates) {
		this.io.setPointerField(this, 11, supported_framerates);
		return this;
	}
	/// C type : const AVRational*
	public final Pointer<? > supported_framerates_$eq(Pointer<? > supported_framerates) {
		supported_framerates(supported_framerates);
		return supported_framerates;
	}
	/**
	 * < array of supported pixel formats, or NULL if unknown, array is terminated by -1<br>
	 * C type : PixelFormat*
	 */
	@Field(12) 
	public Pointer<ValuedEnum<PixelFormat > > pix_fmts() {
		return this.io.getPointerField(this, 12);
	}
	/**
	 * < array of supported pixel formats, or NULL if unknown, array is terminated by -1<br>
	 * C type : PixelFormat*
	 */
	@Field(12) 
	public AVCodec pix_fmts(Pointer<ValuedEnum<PixelFormat > > pix_fmts) {
		this.io.setPointerField(this, 12, pix_fmts);
		return this;
	}
	/// C type : PixelFormat*
	public final Pointer<ValuedEnum<PixelFormat > > pix_fmts_$eq(Pointer<ValuedEnum<PixelFormat > > pix_fmts) {
		pix_fmts(pix_fmts);
		return pix_fmts;
	}
	/**
	 * Descriptive name for the codec, meant to be more human readable than name.<br>
	 * You should use the NULL_IF_CONFIG_SMALL() macro to define it.<br>
	 * C type : const char*
	 */
	@Field(13) 
	public Pointer<java.lang.Byte > long_name() {
		return this.io.getPointerField(this, 13);
	}
	/**
	 * Descriptive name for the codec, meant to be more human readable than name.<br>
	 * You should use the NULL_IF_CONFIG_SMALL() macro to define it.<br>
	 * C type : const char*
	 */
	@Field(13) 
	public AVCodec long_name(Pointer<java.lang.Byte > long_name) {
		this.io.setPointerField(this, 13, long_name);
		return this;
	}
	/// C type : const char*
	public final Pointer<java.lang.Byte > long_name_$eq(Pointer<java.lang.Byte > long_name) {
		long_name(long_name);
		return long_name;
	}
	/**
	 * < array of supported audio samplerates, or NULL if unknown, array is terminated by 0<br>
	 * C type : const int*
	 */
	@Field(14) 
	public Pointer<java.lang.Integer > supported_samplerates() {
		return this.io.getPointerField(this, 14);
	}
	/**
	 * < array of supported audio samplerates, or NULL if unknown, array is terminated by 0<br>
	 * C type : const int*
	 */
	@Field(14) 
	public AVCodec supported_samplerates(Pointer<java.lang.Integer > supported_samplerates) {
		this.io.setPointerField(this, 14, supported_samplerates);
		return this;
	}
	/// C type : const int*
	public final Pointer<java.lang.Integer > supported_samplerates_$eq(Pointer<java.lang.Integer > supported_samplerates) {
		supported_samplerates(supported_samplerates);
		return supported_samplerates;
	}
	/**
	 * < array of supported sample formats, or NULL if unknown, array is terminated by -1<br>
	 * C type : SampleFormat*
	 */
	@Field(15) 
	public Pointer<ValuedEnum<SampleFormat > > sample_fmts() {
		return this.io.getPointerField(this, 15);
	}
	/**
	 * < array of supported sample formats, or NULL if unknown, array is terminated by -1<br>
	 * C type : SampleFormat*
	 */
	@Field(15) 
	public AVCodec sample_fmts(Pointer<ValuedEnum<SampleFormat > > sample_fmts) {
		this.io.setPointerField(this, 15, sample_fmts);
		return this;
	}
	/// C type : SampleFormat*
	public final Pointer<ValuedEnum<SampleFormat > > sample_fmts_$eq(Pointer<ValuedEnum<SampleFormat > > sample_fmts) {
		sample_fmts(sample_fmts);
		return sample_fmts;
	}
	/**
	 * < array of support channel layouts, or NULL if unknown. array is terminated by 0<br>
	 * C type : const int64_t*
	 */
	@Field(16) 
	public Pointer<java.lang.Long > channel_layouts() {
		return this.io.getPointerField(this, 16);
	}
	/**
	 * < array of support channel layouts, or NULL if unknown. array is terminated by 0<br>
	 * C type : const int64_t*
	 */
	@Field(16) 
	public AVCodec channel_layouts(Pointer<java.lang.Long > channel_layouts) {
		this.io.setPointerField(this, 16, channel_layouts);
		return this;
	}
	/// C type : const int64_t*
	public final Pointer<java.lang.Long > channel_layouts_$eq(Pointer<java.lang.Long > channel_layouts) {
		channel_layouts(channel_layouts);
		return channel_layouts;
	}
	/// <i>native declaration : libavcodec/avcodec.h:2669</i>
	public static abstract class init_callback extends Callback<init_callback > {
		public abstract int apply(Pointer<com.nativelibs4java.ffmpeg.avcodec.AVCodecContext > AVCodecContextPtr1);
	};
	/// <i>native declaration : libavcodec/avcodec.h:2670</i>
	public static abstract class encode_callback extends Callback<encode_callback > {
		public abstract int apply(Pointer<com.nativelibs4java.ffmpeg.avcodec.AVCodecContext > AVCodecContextPtr1, Pointer<java.lang.Byte > buf, int buf_size, Pointer<? > data);
	};
	/// <i>native declaration : libavcodec/avcodec.h:2671</i>
	public static abstract class close_callback extends Callback<close_callback > {
		public abstract int apply(Pointer<com.nativelibs4java.ffmpeg.avcodec.AVCodecContext > AVCodecContextPtr1);
	};
	/// <i>native declaration : libavcodec/avcodec.h:2672</i>
	public static abstract class decode_callback extends Callback<decode_callback > {
		public abstract int apply(Pointer<com.nativelibs4java.ffmpeg.avcodec.AVCodecContext > AVCodecContextPtr1, Pointer<? > outdata, Pointer<java.lang.Integer > outdata_size, Pointer<com.nativelibs4java.ffmpeg.avcodec.AVPacket > avpkt);
	};
	/// <i>native declaration : libavcodec/avcodec.h:2683</i>
	public static abstract class flush_callback extends Callback<flush_callback > {
		public abstract void apply(Pointer<com.nativelibs4java.ffmpeg.avcodec.AVCodecContext > AVCodecContextPtr1);
	};
}
