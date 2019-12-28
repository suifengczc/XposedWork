package com.suifeng.xposedwork.hookclassloader;

import com.suifeng.xposedwork.hookmodule.ClassLoaderModule;
import com.suifeng.xposedwork.hookmodule.HookBasicData;
import com.suifeng.xposedwork.hookmodule.HookData;

import de.robv.android.xposed.XC_MethodHook;

public class Hook_DexClassLoader extends ClassLoaderModule {

    public Hook_DexClassLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected void init() {
        className = "dalvik.system.DexClassLoader";
        hookDatas.add(new HookData("", new Class[]{String.class, String.class, String.class, ClassLoader.class},
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
                }, HookBasicData.HOOK_NORMAL_INIT));
    }
}
