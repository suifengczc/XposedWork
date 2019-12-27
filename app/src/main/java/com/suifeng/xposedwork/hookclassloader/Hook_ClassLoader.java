package com.suifeng.xposedwork.hookclassloader;

import com.suifeng.xposedwork.hookmodule.ClassLoaderModule;
import com.suifeng.xposedwork.hookmodule.HookData;

import de.robv.android.xposed.XC_MethodHook;

public class Hook_ClassLoader extends ClassLoaderModule {

    public Hook_ClassLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected void init() {
        className = "java.lang.ClassLoader";
        hookDatas.add(new HookData("loadClass", new Class[]{String.class}, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                hookPluginClasses(param);
                super.afterHookedMethod(param);
            }
        }));
    }
}
