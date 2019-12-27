package com.suifeng.xposedwork.hookclassloader;

import android.util.Log;

import com.suifeng.xposedwork.hookmodule.ClassLoaderModule;
import com.suifeng.xposedwork.hookmodule.HookBasicData;
import com.suifeng.xposedwork.hookmodule.HookData;

import java.io.File;

import de.robv.android.xposed.XC_MethodHook;

public class Hook_BaseDexClassLoader extends ClassLoaderModule {
    public Hook_BaseDexClassLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected void init() {
        className = "dalvik.system.BaseDexClassLoader";
        hookDatas.add(new HookData("",
                new Class[]{String.class, File.class, String.class, ClassLoader.class},
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Log.i(TAG, "hook BaseDexClassLoader: before");
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Log.i(TAG, "hook BaseDexClassLoader: after");
                        hookPluginClasses(param);
                        super.afterHookedMethod(param);
                    }
                }, HookBasicData.HOOK_NORMAL_INIT));
    }


}

