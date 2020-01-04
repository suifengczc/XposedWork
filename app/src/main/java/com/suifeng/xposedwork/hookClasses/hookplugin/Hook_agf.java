package com.suifeng.xposedwork.hookClasses.hookplugin;

import android.util.Log;

import com.suifeng.xposedwork.hookmodule.AbstractPluginClassModule;
import com.suifeng.xposedwork.hookmodule.HookMethodData;
import com.suifeng.xposedwork.hookmodule.HookType;
import com.suifeng.xposedwork.util.Utils;

import de.robv.android.xposed.XC_MethodHook;

public class Hook_agf extends AbstractPluginClassModule {

    public Hook_agf(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected void init() {
        className = "agf";
        hookDatas.add(new HookMethodData("a", HookType.HOOK_NORMAL_METHOD,
                loadClass("afz"),
                loadClass("agi"),
                loadClass("afp"),
                loadClass("ahl"),
                loadClass("ael"),
                loadClass("afw"),
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
                }));

    }


}
