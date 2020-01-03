package com.suifeng.xposedwork.hookclassloader;

import android.util.Log;

import com.suifeng.xposedwork.hookmodule.AbstractClassLoaderModule;
import com.suifeng.xposedwork.hookmodule.HookMethodData;
import com.suifeng.xposedwork.hookmodule.HookType;

import java.io.File;

import de.robv.android.xposed.XC_MethodHook;

public class Hook_BaseDexClassLoader extends AbstractClassLoaderModule {
    public Hook_BaseDexClassLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected void init() {
        className = "dalvik.system.BaseDexClassLoader";
        hookDatas.add(new HookMethodData("", HookType.HOOK_NORMAL_INIT,
                String.class, File.class, String.class, ClassLoader.class,
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
                }));
    }


}

