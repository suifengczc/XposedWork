package com.suifeng.xposedwork.hookclasses.hooksystem;

import android.util.Base64;

import com.suifeng.xposedwork.hookmodule.BaseHookModule;
import com.suifeng.xposedwork.hookmodule.HookHelper;
import com.suifeng.xposedwork.util.Logger;
import com.suifeng.xposedwork.util.Utils;

import de.robv.android.xposed.XC_MethodHook;

/**
 * @author suifengczc
 * @date 2020/3/22
 */
public class Hook_Base64 extends BaseHookModule {
    public Hook_Base64(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected void init() {
        className = Base64.class.getName();

        //hook Base64.encodeToString(byte[],int)
        hookDatas.add(HookHelper.hookMethod(
                "encodeToString",
                byte[].class,
                int.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Logger.logi("hook Base64.encodeToString(byte[],int) before\n " + Utils.concatParams(param));
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Logger.logi("hook Base64.encodeToString(byte[],int) after\n " + Utils.concatParams(param));
                        super.afterHookedMethod(param);
                    }
                }

        ));
    }
}
