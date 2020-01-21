package com.suifeng.xposedwork.hookclasses.hooklauncher;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.suifeng.xposedwork.hookmodule.BaseHookModule;
import com.suifeng.xposedwork.hookmodule.HookMethodData;
import com.suifeng.xposedwork.hookmodule.HookType;
import com.suifeng.xposedwork.util.Logger;
import com.suifeng.xposedwork.util.Reflector;
import com.suifeng.xposedwork.util.Utils;

import de.robv.android.xposed.XC_MethodHook;

/**
 * Hook Actiity 相关方法
 *
 * @author suifengczc
 * @date 2020/1/5
 */
public class Hook_Activity extends BaseHookModule {

    public Hook_Activity(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected void init() {
        className = "android.app.Activity";
        hookDatas.add(new HookMethodData("startActivity", HookType.HOOK_NORMAL_METHOD,
                Intent.class, Bundle.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Object mParent = Reflector.on(className).field("mParent").get(param.thisObject);
                        Logger.logi("hook activity startActiviety : before \n" + Utils.concatParams(param)
                                + "\n this = " + param.thisObject
                                + "\n mParent = " + mParent);
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                }));
    }
}
