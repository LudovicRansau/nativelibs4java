/*
 * JavaCL - Java API and utilities for OpenCL
 * http://javacl.googlecode.com/
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
package com.nativelibs4java.opencl;


import com.nativelibs4java.opencl.library.OpenGLContextUtils;
import com.nativelibs4java.util.EnumValue;
import com.nativelibs4java.util.EnumValues;
import static com.nativelibs4java.opencl.library.OpenCLLibrary.*;

import org.bridj.*;
import static org.bridj.Pointer.*;

import java.nio.ByteOrder;
import java.util.*;
import static com.nativelibs4java.opencl.JavaCL.*;
import static com.nativelibs4java.opencl.CLException.*;

/**
 * OpenCL implementation entry point.
 * @see JavaCL#listPlatforms() 
 * @author Olivier Chafik
 */
public class CLPlatform extends CLAbstractEntity<cl_platform_id> {

    CLPlatform(cl_platform_id platform) {
        super(platform, true);
    }
    private static CLInfoGetter<cl_platform_id> infos = new CLInfoGetter<cl_platform_id>() {

        @Override
        protected int getInfo(cl_platform_id entity, int infoTypeEnum, long size, Pointer out, Pointer<SizeT> sizeOut) {
            return CL.clGetPlatformInfo(entity, infoTypeEnum, size, out, sizeOut);
        }
    };

    @Override
    public String toString() {
        return getName() + " {vendor: " + getVendor() + ", version: " + getVersion() + ", profile: " + getProfile() + ", extensions: " + Arrays.toString(getExtensions()) + "}";
    }

    @Override
    protected void clear() {
    }

    /**
     * Lists all the devices of the platform
     * @param onlyAvailable if true, only returns devices that are available
     * @see CLPlatform#listDevices(java.util.EnumSet, boolean)
     */
    public CLDevice[] listAllDevices(boolean onlyAvailable) {
        return listDevices(EnumSet.allOf(CLDevice.Type.class), onlyAvailable);
    }

    /**
     * Lists all the GPU devices of the platform
     * @param onlyAvailable if true, only returns GPU devices that are available
     * @see CLPlatform#listDevices(java.util.EnumSet, boolean)
     */
    public CLDevice[] listGPUDevices(boolean onlyAvailable) {
        try {
            return listDevices(EnumSet.of(CLDevice.Type.GPU), onlyAvailable);
        } catch (CLException ex) {
            if (ex.getCode() == CL_DEVICE_NOT_FOUND) {
                return new CLDevice[0];
            }
            throw new RuntimeException("Unexpected OpenCL error", ex);
        }
    }

    /**
     * Lists all the CPU devices of the platform
     * @param onlyAvailable if true, only returns CPU devices that are available
     * @see CLPlatform#listDevices(java.util.EnumSet, boolean)
     */
    public CLDevice[] listCPUDevices(boolean onlyAvailable) {
        try {
            return listDevices(EnumSet.of(CLDevice.Type.CPU), onlyAvailable);
        } catch (CLException ex) {
            if (ex.getCode() == CL_DEVICE_NOT_FOUND) {
                return new CLDevice[0];
            }
            throw new RuntimeException("Unexpected OpenCL error", ex);
        }
    }

    private CLDevice[] getDevices(Pointer<cl_device_id> ids, boolean onlyAvailable) {
        int nDevs = (int)ids.getValidElements();
        CLDevice[] devices;
        if (onlyAvailable) {
            List<CLDevice> list = new ArrayList<CLDevice>(nDevs);
            for (int i = 0; i < nDevs; i++) {
                CLDevice device = new CLDevice(this, ids.get(i));
                if (device.isAvailable()) {
                    list.add(device);
                }
            }
            devices = list.toArray(new CLDevice[list.size()]);
        } else {
            devices = new CLDevice[nDevs];
            for (int i = 0; i < nDevs; i++) {
                devices[i] = new CLDevice(this, ids.get(i));
            }
        }
        return devices;
    }

    static long[] getContextProps(Map<ContextProperties, Object> contextProperties) {
        if (contextProperties == null)
            return null;
        final long[] properties = new long[contextProperties.size() * 2 + 2];
        int iProp = 0;
        for (Map.Entry<ContextProperties, Object> e : contextProperties.entrySet()) {
            //if (!(v instanceof Number)) throw new IllegalArgumentException("Invalid context property value for '" + e.getKey() + ": " + v);
            properties[iProp++] = e.getKey().value();
            Object v = e.getValue();
            if (v instanceof Number)
                properties[iProp++] = ((Number)v).longValue();
            else if (v instanceof Pointer)
                properties[iProp++] = ((Pointer)v).getPeer();
            else
                throw new IllegalArgumentException("Cannot convert value " + v + " to a context property value !");
        }
        properties[iProp] = 0;
        return properties;
    }

    public enum DeviceEvaluationStrategy {

        BiggestMaxComputeUnits,
        BiggestMaxComputeUnitsWithNativeEndianness,
        BestDoubleSupportThenBiggestMaxComputeUnits
    }

	static CLDevice getBestDevice(DeviceEvaluationStrategy eval, Iterable<CLDevice> devices) {

        CLDevice bestDevice = null;
        for (CLDevice device : devices) {
            if (bestDevice == null) {
                bestDevice = device;
            } else {
                switch (eval) {
                    case BiggestMaxComputeUnitsWithNativeEndianness:
                        if (bestDevice.getKernelsDefaultByteOrder() != ByteOrder.nativeOrder() && device.getKernelsDefaultByteOrder() == ByteOrder.nativeOrder()) {
                            bestDevice = device;
                            break;
                        }
                    case BiggestMaxComputeUnits:
                        if (bestDevice.getMaxComputeUnits() < device.getMaxComputeUnits()) {
                            bestDevice = device;
                        }
                        break;
                    case BestDoubleSupportThenBiggestMaxComputeUnits:
                        if (device.isDoubleSupported() && !bestDevice.isDoubleSupported())
                            bestDevice = device;
                        break;
                }
            }
        }
        return bestDevice;
    }

    public CLDevice getBestDevice() {
        return getBestDevice(DeviceEvaluationStrategy.BiggestMaxComputeUnits, Arrays.asList(listGPUDevices(true)));
    }

    /** Bit values for CL_CONTEXT_PROPERTIES */
    public enum ContextProperties implements com.nativelibs4java.util.ValuedEnum {
    	//D3D10Device(CL_CONTEXT_D3D10_DEVICE_KHR), 
    	GLContext(CL_GL_CONTEXT_KHR),
		EGLDisplay(CL_EGL_DISPLAY_KHR),
		GLXDisplay(CL_GLX_DISPLAY_KHR),
		WGLHDC(CL_WGL_HDC_KHR),
        Platform(CL_CONTEXT_PLATFORM),
        CGLShareGroupApple(2684354),//CL_CONTEXT_PROPERTY_USE_CGL_SHAREGROUP_APPLE),
		CGLShareGroup(CL_CGL_SHAREGROUP_KHR);

		ContextProperties(long value) { this.value = value; }
		long value;
		@Override
		public long value() { return value; }
		
        public static long getValue(EnumSet<ContextProperties> set) {
            return EnumValues.getValue(set);
        }

        public static EnumSet<ContextProperties> getEnumSet(long v) {
            return EnumValues.getEnumSet(v, ContextProperties.class);
        }
    }

    public CLContext createContextFromCurrentGL() {
        return createGLCompatibleContext(listAllDevices(true));
    }

    static Map<ContextProperties, Object> getGLContextProperties(CLPlatform platform) {
        Map<ContextProperties, Object> out = new LinkedHashMap<ContextProperties, Object>();

        if (JNI.isMacOSX()) {
            Pointer<?> context = OpenGLContextUtils.CGLGetCurrentContext();
            Pointer<?> shareGroup = OpenGLContextUtils.CGLGetShareGroup(context);
            out.put(ContextProperties.GLContext, context.getPeer());
            out.put(ContextProperties.CGLShareGroup, shareGroup.getPeer());
        } else if (JNI.isWindows()) {
            Pointer<?> context = OpenGLContextUtils.wglGetCurrentContext();
            Pointer<?> dc = OpenGLContextUtils.wglGetCurrentDC();
            out.put(ContextProperties.GLContext, context.getPeer());
            out.put(ContextProperties.WGLHDC, dc.getPeer());
        } else if (JNI.isUnix()) {
            Pointer<?> context = OpenGLContextUtils.glXGetCurrentContext();
            Pointer<?> dc = OpenGLContextUtils.glXGetCurrentDisplay();
            out.put(ContextProperties.GLContext, context.getPeer());
            out.put(ContextProperties.GLXDisplay, dc.getPeer());
        } else
            throw new UnsupportedOperationException("Current GL context retrieval not implemented on this platform !");
        
        //out.put(ContextProperties.Platform, platform.getEntity().getPointer());
        
        return out;
    }
    @Deprecated
    public CLContext createGLCompatibleContext(CLDevice... devices) {
        for (CLDevice device : devices)
            if (!device.isGLSharingSupported())
                throw new UnsupportedOperationException("Device " + device + " does not support CL/GL sharing.");
        
        return createContext(getGLContextProperties(this), devices);
    }

    /**
     * Creates an OpenCL context formed of the provided devices.<br/>
     * It is generally not a good idea to create a context with more than one device,
     * because much data is shared between all the devices in the same context.
     * @param devices devices that are to form the new context
     * @return new OpenCL context
     */
    public CLContext createContext(Map<ContextProperties, Object> contextProperties, CLDevice... devices) {
        int nDevs = devices.length;
        if (nDevs == 0) {
            throw new IllegalArgumentException("Cannot create a context with no associated device !");
        }
        Pointer<cl_device_id> ids = allocateTypedPointers(cl_device_id.class, nDevs);
        for (int i = 0; i < nDevs; i++) {
            ids.set(i, devices[i].getEntity());
        }

        Pointer<Integer> errRef = allocateInt();

        long[] props = getContextProps(contextProperties);
        Pointer<SizeT> propsRef = props == null ? null : pointerToSizeTs(props);
        cl_context context = CL.clCreateContext(propsRef, nDevs, ids, null, null, errRef);
        error(errRef.get());
        return new CLContext(this, ids, context);
    }

    /**
     * List all the devices of the specified types, with only the ones declared as available if onlyAvailable is true.
     */
    @SuppressWarnings("deprecation")
    public CLDevice[] listDevices(EnumSet<CLDevice.Type> types, boolean onlyAvailable) {
        int flags = (int) CLDevice.Type.getValue(types);

        Pointer<Integer> pCount = allocateInt();
        error(CL.clGetDeviceIDs(getEntity(), flags, 0, null, pCount));

        int nDevs = pCount.get();
        if (nDevs <= 0) {
            return new CLDevice[0];
        }

        Pointer<cl_device_id> ids = allocateTypedPointers(cl_device_id.class, nDevs);

        error(CL.clGetDeviceIDs(getEntity(), flags, nDevs, ids, pCount));
        return getDevices(ids, onlyAvailable);
    }

    /*
    public CLDevice[] listGLDevices(long openglContextId, boolean onlyAvailable) {
        
        Pointer<Integer> errRef = allocateInt();
        long[] props = getContextProps(getGLContextProperties());
        Memory propsMem = toNSArray(props);
        Pointer<SizeT> propsRef = allocateSizeT();
        propsRef.setPointer(propsMem);
        
        Pointer<SizeT> pCount = allocateSizeT();
        error(CL.clGetGLContextInfoKHR(propsRef, CL_DEVICES_FOR_GL_CONTEXT_KHR, 0, (Pointer) null, pCount));

        int nDevs = pCount.getValue().intValue();
        if (nDevs == 0)
            return new CLDevice[0];
        Memory idsMem = new Memory(nDevs * Pointer.SIZE);
        error(CL.clGetGLContextInfoKHR(propsRef, CL_DEVICES_FOR_GL_CONTEXT_KHR, nDevs, idsMem, pCount));
        cl_device_id[] ids = new cl_device_id[nDevs];
        for (int i = 0; i < nDevs; i++)
            ids[i] = new cl_device_id(idsMem.getPointer(i * Pointer.SIZE));
        return getDevices(ids, onlyAvailable);
    }*/

    /**
     * OpenCL profile string. Returns the profile name supported by the implementation. The profile name returned can be one of the following strings:
     * <ul>
     * <li>FULL_PROFILE if the implementation supports the OpenCL specification (functionality defined as part of the core specification and does not require any extensions to be supported).</li>
     * <li>EMBEDDED_PROFILE if the implementation supports the OpenCL embedded profile. The embedded profile is defined to be a subset for each version of OpenCL. The embedded profile for OpenCL 1.0 is described in section 10.</li>
     * </ul>
     */
    @InfoName("CL_PLATFORM_PROFILE")
    public String getProfile() {
        return infos.getString(getEntity(), CL_PLATFORM_PROFILE);
    }

    /**
    OpenCL version string. Returns the OpenCL version supported by the implementation. This version string has the following format:
    OpenCL<space><major_version.min or_version><space><platform- specific information>
    Last Revision Date: 5/16/09	Page 30
    The major_version.minor_version value returned will be 1.0.
     */
    @InfoName("CL_PLATFORM_VERSION")
    public String getVersion() {
        return infos.getString(getEntity(), CL_PLATFORM_VERSION);
    }

    /**
     * Platform name string.
     */
    @InfoName("CL_PLATFORM_NAME")
    public String getName() {
        return infos.getString(getEntity(), CL_PLATFORM_NAME);
    }

    /**
     * Platform vendor string.
     */
    @InfoName("CL_PLATFORM_VENDOR")
    public String getVendor() {
        return infos.getString(getEntity(), CL_PLATFORM_VENDOR);
    }

    /**
     * Returns a list of extension names <br/>
     * Extensions defined here must be supported by all devices associated with this platform.
     */
    @InfoName("CL_PLATFORM_EXTENSIONS")
    public String[] getExtensions() {
        if (extensions == null) {
            extensions = infos.getString(getEntity(), CL_PLATFORM_EXTENSIONS).split("\\s+");
        }
        return extensions;
    }

    private String[] extensions;

    boolean hasExtension(String name) {
        name = name.trim();
        for (String x : getExtensions()) {
            if (name.equals(x.trim())) {
                return true;
            }
        }
        return false;
    }

    @InfoName("cl_nv_device_attribute_query")
    public boolean isNVDeviceAttributeQuerySupported() {
        return hasExtension("cl_nv_device_attribute_query");
    }

    @InfoName("cl_nv_compiler_options")
    public boolean isNVCompilerOptionsSupported() {
        return hasExtension("cl_nv_compiler_options");
    }

    @InfoName("cl_khr_byte_addressable_store")
    public boolean isByteAddressableStoreSupported() {
        return hasExtension("cl_khr_byte_addressable_store");
    }

    @InfoName("cl_khr_gl_sharing")
    public boolean isGLSharingSupported() {
        return hasExtension("cl_khr_gl_sharing") || hasExtension("cl_APPLE_gl_sharing");
    }

}