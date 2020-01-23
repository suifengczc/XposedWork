package com.suifeng.xposedwork.hookclasses.hooklauncher;

import com.suifeng.xposedwork.hookmodule.BaseHookModule;
import com.suifeng.xposedwork.hookmodule.HookHelper;
import com.suifeng.xposedwork.util.Logger;
import com.suifeng.xposedwork.util.Utils;
import com.suifeng.xposedwork.util.filter.PackageNameFilter;

import de.robv.android.xposed.XC_MethodHook;

/**
 * @author suifengczc
 * @date 2020/1/18
 */
public class Hook_ActivityManagerNative extends BaseHookModule {

    /**
     * @param classLoader 这里传入的是当前的classloader
     */
    public Hook_ActivityManagerNative(ClassLoader classLoader, PackageNameFilter filter) {
        super(classLoader, filter);
    }

    @Override
    protected void init() {
        className = "android.app.ActivityManagerNative";
        hookDatas.add(HookHelper.hookMethod("getDefault", new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Logger.logi("hook ActivityManagerNative getDefault \n" + Utils.concatResult(param));
                        super.afterHookedMethod(param);
                    }
                })
        );

    }
}
