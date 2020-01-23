package com.suifeng.xposedwork.hookClasses.hooklauncher;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.suifeng.xposedwork.hookmodule.BaseHookModule;
import com.suifeng.xposedwork.hookmodule.HookMethodData;
import com.suifeng.xposedwork.hookmodule.HookType;
import com.suifeng.xposedwork.util.Logger;
import com.suifeng.xposedwork.util.Reflector;
import com.suifeng.xposedwork.util.Utils;
import com.suifeng.xposedwork.util.filter.PackageNameFilter;

import java.util.List;

import de.robv.android.xposed.XC_MethodHook;

/**
 * @author suifengczc
 * @date 2020/1/5
 */
public class Hook_Instrumentation extends BaseHookModule {

    public Hook_Instrumentation(ClassLoader classLoader, PackageNameFilter filter) {
        super(classLoader, filter);
    }

    @Override
    protected void init() {
        className = "android.app.Instrumentation";
        hookDatas.add(new HookMethodData("execStartActivity", HookType.HOOK_NORMAL_METHOD,
                Context.class,
                IBinder.class,
                IBinder.class,
                Activity.class,
                Intent.class,
                int.class,
                Bundle.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Logger.logi("hook Instrumentation execStartActivity 7 : before \n" + Utils.concatParams(param));
                        Object onProvideReferrer = Reflector.on(Activity.class).method("onProvideReferrer").callByCaller(param.args[3]);
                        if (onProvideReferrer != null) {
                            Logger.logi("hook Instrumentation execStartActivity 7 : before == " + onProvideReferrer);
                        } else {
                            Logger.logi("hook Instrumentation execStartActivity 7 : before is null");
                        }

                        Object mActivityMonitors = Reflector.on(Instrumentation.class).field("mActivityMonitors").get(param.thisObject);
                        Reflector activityMonitorRef = Reflector.on(Instrumentation.ActivityMonitor.class);
                        if (mActivityMonitors != null) {
                            List<Instrumentation.ActivityMonitor> mActivityMonitorsList = (List<Instrumentation.ActivityMonitor>) mActivityMonitors;
                            for (Instrumentation.ActivityMonitor activityMonitor : mActivityMonitorsList) {
                                Object match = activityMonitorRef.method("match", Context.class, Activity.class, Intent.class)
                                        .callByCaller(activityMonitor, ((Context) param.args[0]), ((Activity) param.args[3]), ((Intent) param.args[4]));
                                Object isBlocking = activityMonitorRef.method("isBlocking").callByCaller(activityMonitor);
                                Object getResult = activityMonitorRef.method("getResult").callByCaller(activityMonitor);
                                Logger.logi("hook Instrumentation execStartActivity 7 : before \n"
                                        + "match =  " + match
                                        + "isBlocking = " + isBlocking
                                        + "getResult = " + getResult
                                );
                            }
                        } else {
                            Logger.logi("hook Instrumentation execStartActivity 7 : before mActivityMonitors is null");
                        }
                        Intent intent = (Intent) param.args[4];
                        if (intent != null) {
                            ClipData clipData = intent.getClipData();
                            if (clipData != null) {
                                Logger.logi("hook Instrumentation execStartActivity 7 : before \n "
                                        + clipData.toString());
                            }
                        }
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Intent intent = (Intent) param.args[4];
                        if (intent != null) {
                            ClipData clipData = intent.getClipData();
                            if (clipData != null) {
                                Logger.logi("hook Instrumentation execStartActivity 7 : aftre \n "
                                        + clipData.toString());
                            }
                        }
                        super.afterHookedMethod(param);
                    }
                }));
    }
}
