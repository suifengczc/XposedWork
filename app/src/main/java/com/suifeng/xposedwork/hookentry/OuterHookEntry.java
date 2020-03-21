package com.suifeng.xposedwork.hookentry;

import android.app.Application;
import android.content.Context;

import com.suifeng.xposedwork.util.Utils;
import com.suifeng.xposedwork.util.exception.ModuleApkNotFoundException;
import com.suifeng.xposedwork.util.exception.ModuleApkPathException;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import dalvik.system.PathClassLoader;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * xposed模块的外部入口，实现免重启
 *
 * @author suifengczc
 */
public class OuterHookEntry implements IXposedHookLoadPackage {

    /**
     * 当前Xposed模块的包名,方便寻找apk文件
     */
    public static final String MODULE_PACKAGE_NAME = "com.suifeng.xposedwork";
    /**
     * 实际hook逻辑处理类
     */
    private final String handleHookClass = InnerHookEntry.class.getName();
    /**
     * 实际hook逻辑处理类的入口方法
     */
    private static final String handleHookMethod = "handleLoadPackage";

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {

        List<String> packageList = null;
        try {
            packageList = Utils.getHookPackageList();
        } catch (ModuleApkPathException e) {
            Utils.printThrowable(e);
        }
        if (packageList != null && packageList.isEmpty()) {
            return;
        }
        //将loadPackageParam的classloader替换为宿主程序Application的classloader,解决宿主程序存在多个.dex文件时,有时候ClassNotFound的问题
        if (packageList.contains(loadPackageParam.packageName)) {
            XposedHelpers.findAndHookMethod(Application.class, "attach", Context.class, new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    try {
                        Context context = (Context) param.args[0];
                        loadPackageParam.classLoader = context.getClassLoader();
                        Class<?> cls = getInnerClass(handleHookClass);
                        Method getInstanceMethod = cls.getDeclaredMethod("getInstance");
                        Object instance = getInstanceMethod.invoke(null);
                        cls.getDeclaredMethod(handleHookMethod, loadPackageParam.getClass()).invoke(instance, loadPackageParam);
                    } catch (ModuleApkNotFoundException | IllegalAccessException | ClassNotFoundException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
                        Utils.printThrowable(e);
                    }
                }
            });
        }


    }


    /**
     * 获取内部入口的Class
     *
     * @param innerEntryClassName 内部入口类名
     * @return
     * @throws ModuleApkNotFoundException 获取Xposed模块实际路径失败
     * @throws ClassNotFoundException
     */
    private Class<?> getInnerClass(String innerEntryClassName) throws ModuleApkNotFoundException, ClassNotFoundException, ModuleApkPathException {
        File apkFile = new File(Utils.getModuleApkPath());
        if (!apkFile.exists()) {
            throw new ModuleApkNotFoundException("寻找模块apk失败");
        }
        //加载指定的hook逻辑处理类，并调用它的handleHook方法
        PathClassLoader pathClassLoader = new PathClassLoader(apkFile.getAbsolutePath(), ClassLoader.getSystemClassLoader());
        return pathClassLoader.loadClass(innerEntryClassName);
    }


}

