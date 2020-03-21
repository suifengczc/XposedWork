package com.suifeng.xposedwork.util;

import android.os.Build;
import android.view.Display;


import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 在不同系统版本下获取ContextImpl实例的帮助类
 * 兼容API level 20-29
 *
 * @author suifengczc
 * @date 2020/3/12
 */
class ContextImplBuilder {
    public static final ContextImplBuilder BUILDER = new ContextImplBuilder();
    private static Map<Integer, ContextImplParam> contextImplParamMap = new HashMap<>();

    private Class<?> contextImplClz;
    private Class<?> actThreadClz;
    private Class<?> loadedApkClz;
    private Object loadedApk;

    private ContextImplBuilder() {
        try {
            contextImplClz = Class.forName("android.app.ContextImpl");
            actThreadClz = Class.forName("android.app.ActivityThread");
            loadedApkClz = Class.forName("android.app.LoadedApk");
            Field sCurrentActivityThreadField = actThreadClz.getDeclaredField("sCurrentActivityThread");
            sCurrentActivityThreadField.setAccessible(true);
            Object mainThread = sCurrentActivityThreadField.get(null);

            Constructor<?> loadedapkConstructor = loadedApkClz.getDeclaredConstructor(actThreadClz);
            loadedapkConstructor.setAccessible(true);
            loadedApk = loadedapkConstructor.newInstance(mainThread);
            contextImplParamMap.put(20,
                    new ContextImplParam(
                            new Class[]{
                                    contextImplClz,
                                    actThreadClz,
                                    loadedApkClz,
                                    android.os.IBinder.class,
                                    android.os.UserHandle.class,
                                    boolean.class,
                                    Display.class,
                                    android.content.res.Configuration.class
                            },
                            new Object[]{
                                    null,
                                    mainThread,
                                    loadedApk,
                                    null,
                                    null,
                                    false,
                                    null,
                                    null
                            }
                    )
            );
            contextImplParamMap.put(23,
                    new ContextImplParam(new Class[]{
                            contextImplClz,
                            actThreadClz,
                            loadedApkClz,
                            android.os.IBinder.class,
                            android.os.UserHandle.class,
                            boolean.class,
                            Display.class,
                            android.content.res.Configuration.class,
                            int.class
                    },
                            new Object[]{
                                    null,
                                    mainThread,
                                    loadedApk,
                                    null,
                                    null,
                                    false,
                                    null,
                                    null,
                                    Display.INVALID_DISPLAY
                            }
                    )
            );
            contextImplParamMap.put(24,
                    new ContextImplParam(
                            new Class[]{
                                    contextImplClz,
                                    actThreadClz,
                                    loadedApkClz,
                                    android.os.IBinder.class,
                                    android.os.UserHandle.class,
                                    int.class,
                                    Display.class,
                                    android.content.res.Configuration.class,
                                    int.class
                            },
                            new Object[]{
                                    null,
                                    mainThread,
                                    loadedApk,
                                    null,
                                    null,
                                    0,
                                    null,
                                    null,
                                    Display.INVALID_DISPLAY
                            }
                    )
            );
            contextImplParamMap.put(26,
                    new ContextImplParam(
                            new Class[]{
                                    contextImplClz,
                                    actThreadClz,
                                    loadedApkClz,
                                    String.class,
                                    android.os.IBinder.class,
                                    android.os.UserHandle.class,
                                    int.class,
                                    ClassLoader.class
                            },
                            new Object[]{
                                    null,
                                    mainThread,
                                    loadedApk,
                                    null,
                                    null,
                                    null,
                                    0,
                                    null
                            }
                    )
            );
            contextImplParamMap.put(29,
                    new ContextImplParam(
                            new Class[]{
                                    contextImplClz,
                                    actThreadClz,
                                    loadedApkClz,
                                    String.class,
                                    android.os.IBinder.class,
                                    android.os.UserHandle.class,
                                    int.class,
                                    ClassLoader.class,
                                    String.class
                            },
                            new Object[]{
                                    null,
                                    mainThread,
                                    loadedApk,
                                    null,
                                    null,
                                    null,
                                    0,
                                    null,
                                    null
                            }
                    )
            );

        } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InstantiationException | InvocationTargetException e) {
            Utils.printThrowable(e);
        }
    }


    /**
     * ContextImpl相关数据类
     */
    private class ContextImplParam {
        /**
         * ContextImpl构造函数的传参类型数组
         */
        Class[] paramType;
        /**
         * ContextImpl构造函数的实参数组
         */
        Object[] paramValue;

        ContextImplParam(Class[] paramType, Object[] paramValue) {
            this.paramType = paramType;
            this.paramValue = paramValue;
        }
    }

    /**
     * 根据sdk版本获取对应的ContextImplParam
     * 支持API level 20-29
     *
     * @param sdkVersion sdkversion
     * @return
     */
    public static ContextImplParam getParamBySdkInt(int sdkVersion) {
        switch (sdkVersion) {
            case 20:
            case 21:
            case 22:
                return contextImplParamMap.get(20);
            case 23:
                return contextImplParamMap.get(23);
            case 24:
            case 25:
                return contextImplParamMap.get(24);
            case 26:
            case 27:
            case 28:
                return contextImplParamMap.get(26);
            case 29:
                return contextImplParamMap.get(29);
            default:
                throw new UnsupportedOperationException("ContextImplUtill getParamBySDKInt unsupport sdk version = " + sdkVersion);
        }
    }

    /**
     * 反射生成对应版本的ContextImpl
     *
     * @return
     */
    public Object getContextImpl() {
        Object ctxtImpl = null;
        try {
            ContextImplParam contextImplParam = getParamBySdkInt(Build.VERSION.SDK_INT);
            Constructor ctxtImplConstructor = contextImplClz.getDeclaredConstructor(contextImplParam.paramType);
            ctxtImplConstructor.setAccessible(true);
            ctxtImpl = ctxtImplConstructor.newInstance(contextImplParam.paramValue);
            if (Build.VERSION.SDK_INT == 27) {
                //api27需要给ContextImpl设置mResources
                Field mResourcesField = contextImplClz.getDeclaredField("mResources");
                mResourcesField.setAccessible(true);
                Method getResourcesMethod = loadedApkClz.getDeclaredMethod("getResources");
                getResourcesMethod.setAccessible(true);
                Object resource = getResourcesMethod.invoke(loadedApk);
                mResourcesField.set(ctxtImpl, resource);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchFieldException e) {
            Utils.printThrowable(e);
        }
        return ctxtImpl;
    }

}
