package com.suifeng.xposedwork.hookclasses.hooklauncher;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.suifeng.xposedwork.hookmodule.BaseHookModule;
import com.suifeng.xposedwork.hookmodule.HookMethodData;
import com.suifeng.xposedwork.hookmodule.HookType;
import com.suifeng.xposedwork.util.Logger;
import com.suifeng.xposedwork.util.Reflector;
import com.suifeng.xposedwork.util.Utils;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedHelpers;

/**
 * Hook Launcher相关方法
 * @author suifengczc
 * @date 2020/1/4
 */
public class Hook_Launcher extends BaseHookModule {

    public Hook_Launcher(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected void init() {
        className = "com.android.launcher3.Launcher";
        hookDatas.add(new HookMethodData("onClick", HookType.HOOK_NORMAL_METHOD,
                View.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Logger.logi( "hook Launcher onClick : before \n" + Utils.concatParams(param));
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                }));
        hookDatas.add(new HookMethodData("startActivity", HookType.HOOK_NORMAL_METHOD,
                View.class, Intent.class, Object.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Logger.logi( "hook Launcher startActivity : before \n" + Utils.concatParams(param)
                                + "\n intent Extras = " + ((Intent) param.args[1]).getExtras().toString());
                        Reflector launcherRef = Reflector.on(param.thisObject.getClass());
                        Object mParent = launcherRef.field("mParent").get(param.thisObject);
                        if (mParent != null) {
                            Logger.logi( "hook Launcher startActivity : before \n" + " mparent = " + mParent);
                        } else {
                            Logger.logi( "hook Launcher startActivity : before \n" + " mparent is null ");
                        }
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Logger.logi( "hook Launcher startActivity : after \n " + Utils.concatParams(param));
                        super.afterHookedMethod(param);
                    }
                }));
    }
}
