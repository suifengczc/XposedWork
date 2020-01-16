package com.suifeng.xposedwork.hookclasses.hookclassloader;

import com.suifeng.xposedwork.hookmodule.AbstractClassLoaderModule;
import com.suifeng.xposedwork.hookmodule.HookMethodData;
import com.suifeng.xposedwork.hookmodule.HookType;

import de.robv.android.xposed.XC_MethodHook;

public class Hook_DexClassLoader extends AbstractClassLoaderModule {

    public Hook_DexClassLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected void init() {
        className = "dalvik.system.DexClassLoader";
        hookDatas.add(new HookMethodData("", HookType.HOOK_NORMAL_INIT,
                String.class, String.class, String.class, ClassLoader.class,
                new XC_MethodHook() {
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
