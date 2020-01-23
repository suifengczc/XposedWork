package com.suifeng.xposedwork.hookclasses.hooklauncher;


import android.content.ClipData;
import android.content.Intent;
import android.util.Log;

import com.suifeng.xposedwork.hookmodule.BaseHookModule;
import com.suifeng.xposedwork.hookmodule.HookMethodData;
import com.suifeng.xposedwork.hookmodule.HookType;
import com.suifeng.xposedwork.util.Logger;
import com.suifeng.xposedwork.util.filter.PackageNameFilter;

import de.robv.android.xposed.XC_MethodHook;

/**
 * @author suifengczc
 * @date 2020/1/12
 */
public class Hook_Intent extends BaseHookModule {

    /**
     * @param classLoader 这里传入的是当前的classloader
     */
    public Hook_Intent(ClassLoader classLoader, PackageNameFilter filter) {
        super(classLoader, filter);
    }

    @Override
    protected void init() {
        className = "android.content.Intent";
        hookDatas.add(new HookMethodData("migrateExtraStreamToClipData", HookType.HOOK_NORMAL_METHOD,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Intent intent = (Intent) param.thisObject;
                        ClipData clipData = intent.getClipData();
                        if (clipData != null) {
                            Logger.logi("hook Intent migrateExtraStreamToClipData: before " + clipData.toString());
                        } else {
                            Logger.logi("hook Intent migrateExtraStreamToClipData: before clipdata is null ");
                        }
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Intent intent = (Intent) param.thisObject;
                        ClipData clipData = intent.getClipData();
                        if (clipData != null) {
                            Logger.logi("hook Intent migrateExtraStreamToClipData: after " + clipData.toString());
                        } else {
                            Logger.logi("hook Intent migrateExtraStreamToClipData: after clipdata is null ");
                        }
                        super.afterHookedMethod(param);
                    }
                }));
    }
}
