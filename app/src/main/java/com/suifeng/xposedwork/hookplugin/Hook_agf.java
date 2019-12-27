package com.suifeng.xposedwork.hookplugin;

import android.util.Log;

import com.suifeng.xposedwork.hookmodule.HookBasicData;
import com.suifeng.xposedwork.hookmodule.HookData;
import com.suifeng.xposedwork.hookmodule.PluginClassModule;
import com.suifeng.xposedwork.util.Utils;

import de.robv.android.xposed.XC_MethodHook;

public class Hook_agf extends PluginClassModule {

    public Hook_agf(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected void init() {
        className = "agf";
        try {
            hookDatas.add(new HookData("a",
                    new Class[]{
                            loadClass("afz"),
                            loadClass("agi"),
                            loadClass("afp"),
                            loadClass("ahl"),
                            loadClass("ael"),
                            loadClass("afw")
                    },
                    new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            Log.i(TAG, "hook agf a : before \n" + Utils.concatParams(param));
                            super.beforeHookedMethod(param);
                        }

                        @Override
                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                            Log.i(TAG, "hook agf a : after " + Utils.concatResult(param));
                            super.afterHookedMethod(param);
                        }
                    }, HookBasicData.HOOK_NORMAL_METHOD));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }


}
