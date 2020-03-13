package com.suifeng.xposedwork.hookentry;

import android.app.Application;
import android.content.Context;

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
 * xposed模块的外部入口，实现免重启
 *
 * @author suifengczc
 */
public class OuterHookEntry implements IXposedHookLoadPackage, IXposedHookZygoteInit {
    private StartupParam startupparam;

    /**
     * 当前Xposed模块的包名,方便寻找apk文件
     */
    public final static String MODULE_PACKAGE_NAME = "com.suifeng.xposedwork";
    /**
     * 实际hook逻辑处理类
     */
    private final String handleHookClass = InnerHookEntry.class.getName();
    /**
     * 实际hook逻辑处理类的入口方法
     */
    private final String handleHookMethod = "handleLoadPackage";

    @Override
    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) throws Throwable {
        try {
            List<String> packageList = Utils.getHookPackageList();
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
                        Class<?> cls = getInnerClass(handleHookClass);
                        Object instance = cls.newInstance();
                        cls.getDeclaredMethod(handleHookMethod, loadPackageParam.getClass()).invoke(instance, loadPackageParam);
                    }
                });
            }
        } catch (Throwable e) {
            Utils.printThrowable(e);
        }

    }


    /**
     * 获取内部入口的Class
     *
     * @param handleHookClass
     * @return
     * @throws Throwable
     */
    private Class<?> getInnerClass(String handleHookClass) throws Throwable {
        File apkFile = new File(Utils.getModuleApkPath());
        if (apkFile == null) {
            throw new RuntimeException("寻找模块apk失败");
        }
        //加载指定的hook逻辑处理类，并调用它的handleHook方法
        PathClassLoader pathClassLoader = new PathClassLoader(apkFile.getAbsolutePath(), ClassLoader.getSystemClassLoader());
        return pathClassLoader.loadClass(handleHookClass);
    }


    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        startupparam = startupParam;
    }


}

