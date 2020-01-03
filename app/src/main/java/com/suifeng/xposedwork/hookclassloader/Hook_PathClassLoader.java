package com.suifeng.xposedwork.hookclassloader;

import android.util.Log;

import com.suifeng.xposedwork.hookmodule.AbstractClassLoaderModule;
import com.suifeng.xposedwork.hookmodule.HookMethodData;
import com.suifeng.xposedwork.hookmodule.HookType;

import de.robv.android.xposed.XC_MethodHook;

public class Hook_PathClassLoader extends AbstractClassLoaderModule {

    public Hook_PathClassLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected void init() {
        className = "dalvik.system.PathClassLoader";
        hookDatas.add(new HookMethodData("", HookType.HOOK_NORMAL_INIT,
                String.class, String.class, ClassLoader.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Log.i(TAG, "hook PathClassLoader3 :before path = " + param.args[0].toString());
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        String path = param.args[0].toString();
                        Log.i(TAG, "hook PathClassLoader3 :after " + " path = " + path + "\n" + param.thisObject.toString());
                        if (path.contains("dl-AdsFdrDynamite")) {
                            hookPluginClasses(param);
                        }
                        super.afterHookedMethod(param);
                    }
                }));
        hookDatas.add(new HookMethodData("", HookType.HOOK_NORMAL_INIT,
                String.class, ClassLoader.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Log.i(TAG, "hook PathClassLoader2 :before path = " + param.args[0].toString());
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        String path = param.args[0].toString();
                        Log.i(TAG, "hook PathClassLoader2 :after " + " path = " + path + "\n" + param.thisObject.toString());
                        if (path.contains("dl-AdsFdrDynamite")) {
                            hookPluginClasses(param);
                        }
                        super.afterHookedMethod(param);
                    }
                }));
    }
}
