package com.suifeng.xposedwork.hookplugin;

import android.util.Log;

import com.suifeng.xposedwork.hookmodule.HookData;
import com.suifeng.xposedwork.hookmodule.PluginClassModule;

import de.robv.android.xposed.XC_MethodHook;

public class Hook_client_x extends PluginClassModule {
    /**
     * @param classLoader 加载插件的classloader
     */
    public Hook_client_x(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected void init() {
        className = "com.google.android.gms.ads.internal.client.x";
        hookDatas.add(new HookData("d", new Class[]{}, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Log.i(TAG, "hook x d: aftre " + param.getResult().toString());
                super.afterHookedMethod(param);
            }
        }, HookData.HOOK_NORMAL_METHOD));
    }
}
