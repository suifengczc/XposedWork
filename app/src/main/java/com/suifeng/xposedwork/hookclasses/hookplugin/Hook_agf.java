package com.suifeng.xposedwork.hookclasses.hookplugin;

import com.suifeng.xposedwork.hookmodule.AbstractPluginClassModule;
import com.suifeng.xposedwork.hookmodule.HookMethodData;
import com.suifeng.xposedwork.hookmodule.HookType;
import com.suifeng.xposedwork.util.Logger;
import com.suifeng.xposedwork.util.Utils;
import com.suifeng.xposedwork.util.filter.PackageNameFilter;

import de.robv.android.xposed.XC_MethodHook;

public class Hook_agf extends AbstractPluginClassModule {

    public Hook_agf(ClassLoader classLoader, PackageNameFilter filter) {
        super(classLoader, filter);
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
                        Logger.logi("hook agf a : before \n" + Utils.concatParams(param));
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Logger.logi("hook agf a : after " + Utils.concatResult(param));
                        super.afterHookedMethod(param);
                    }
                })
        );

    }


}
