package com.suifeng.xposedwork.hook;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;

import com.suifeng.xposedwork.util.Utils;

import java.io.File;
import java.util.List;

import dalvik.system.PathClassLoader;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * xposed模块的入口，实现免重启
 */
public class XposedMain implements IXposedHookLoadPackage, IXposedHookZygoteInit {
    private StartupParam startupparam;

    /**
     * 当前Xposed模块的包名,方便寻找apk文件
     */
    private final static String MODULE_PACKAGE_NAME = "com.suifeng.xposedwork";
    /**
     * 实际hook逻辑处理类
     */
    private final String handleHookClass = HookStack.class.getName();
    /**
     * 实际hook逻辑处理类的入口方法
     */
    private final String handleHookMethod = "handleLoadPackage";

    private final String initMethod = "initZygote";


    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        List<String> packageList = Utils.getHookPackage();
        if (packageList.size() == 0) {
            return;
        }
        //将loadPackageParam的classloader替换为宿主程序Application的classloader,解决宿主程序存在多个.dex文件时,有时候ClassNotFound的问题
        if (packageList.contains(loadPackageParam.packageName)) {
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Context context = (Context) param.args[0];
                    loadPackageParam.classLoader = context.getClassLoader();
                    Class<?> cls = getApkClass(context, MODULE_PACKAGE_NAME, handleHookClass);
                    Object instance = cls.newInstance();
                    try {
                        //暂时无用，HookStack没有添加initZygote方法
                        cls.getDeclaredMethod(initMethod, startupparam.getClass()).invoke(instance, startupparam);
                    } catch (NoSuchMethodException e) {
                        // 找不到initZygote方法
                    }
                    cls.getDeclaredMethod(handleHookMethod, loadPackageParam.getClass()).invoke(instance, loadPackageParam);
                }
            });
        }

    }


    private Class<?> getApkClass(Context context, String modulePackageName, String handleHookClass) throws Throwable {
        File apkFile = findApkFile(context, modulePackageName);
        if (apkFile == null) {
            throw new RuntimeException("寻找模块apk失败");
        }
        //加载指定的hook逻辑处理类，并调用它的handleHook方法
        PathClassLoader pathClassLoader = new PathClassLoader(apkFile.getAbsolutePath(), ClassLoader.getSystemClassLoader());
//        Class<?> cls = Class.forName(handleHookClass, true, pathClassLoader);
        return pathClassLoader.loadClass(handleHookClass);
    }


    /**
     * 根据包名构建目标Context,并调用getPackageCodePath()来定位apk
     */
    private File findApkFile(Context context, String modulePackageName) {
        if (context == null) {
            return null;
        }
        try {
            Context moudleContext = context.createPackageContext(modulePackageName, Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
            String apkPath = moudleContext.getPackageCodePath();
            return new File(apkPath);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;

    }


    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {

        startupparam = startupParam;
    }


}

