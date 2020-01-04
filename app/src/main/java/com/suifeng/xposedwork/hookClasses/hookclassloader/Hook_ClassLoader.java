package com.suifeng.xposedwork.hookClasses.hookclassloader;

import com.suifeng.xposedwork.hookmodule.AbstractClassLoaderModule;
import com.suifeng.xposedwork.hookmodule.HookMethodData;
import com.suifeng.xposedwork.hookmodule.HookType;

import de.robv.android.xposed.XC_MethodHook;

public class Hook_ClassLoader extends AbstractClassLoaderModule {

    public Hook_ClassLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected void init() {
        className = "java.lang.ClassLoader";
        hookDatas.add(new HookMethodData("loadClass", HookType.HOOK_NORMAL_METHOD,
                String.class, new XC_MethodHook() {
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
