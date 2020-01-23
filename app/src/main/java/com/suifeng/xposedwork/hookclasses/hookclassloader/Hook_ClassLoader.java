package com.suifeng.xposedwork.hookclasses.hookclassloader;

import com.suifeng.xposedwork.hookmodule.AbstractClassLoaderModule;
import com.suifeng.xposedwork.hookmodule.HookMethodData;
import com.suifeng.xposedwork.hookmodule.HookType;
import com.suifeng.xposedwork.util.filter.PackageNameFilter;

import de.robv.android.xposed.XC_MethodHook;

public class Hook_ClassLoader extends AbstractClassLoaderModule {

    public Hook_ClassLoader(ClassLoader classLoader, PackageNameFilter filter) {
        super(classLoader, filter);
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
