package com.suifeng.xposedwork.hookmodule;

import android.util.Log;

import java.util.List;
import java.util.Map;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * Hook 辅助类
 *
 * @author suifengczc
 */
public class HookHelper {
    private static final String TAG = "HookDemo";

    /**
     * 读取HookModule的HookData处理具体的hook方式
     *
     * @param classLoader 加载被hook类的ClassLoader
     * @param hookModules 需要hook的模块
     */
    public static void dealHook(ClassLoader classLoader, Map<String, BaseHookModule> hookModules) {
        for (Map.Entry<String, BaseHookModule> next : hookModules.entrySet()) {
            String clzName = next.getKey();
            BaseHookModule hookModule = next.getValue();
            if (hookModule != null) {
                List<HookData> hookDatas = hookModule.getHookDatas();
                for (HookData hookData : hookDatas) {
                    if (hookData.hookType == HookType.HOOK_NORMAL_METHOD
                            || hookData.hookType == HookType.HOOK_REPLACE_METHOD) {
                        HookMethodData hookMethodData = (HookMethodData) hookData;
                        XposedHelpers.findAndHookMethod(clzName,
                                classLoader,
                                hookMethodData.hookTarget,
                                hookMethodData.hookVariableParams);
                    } else if (hookData.hookType == HookType.HOOK_NORMAL_INIT
                            || hookData.hookType == HookType.HOOK_REPLACE_INIT) {
                        HookMethodData hookMethodData = (HookMethodData) hookData;
                        XposedHelpers.findAndHookConstructor(clzName,
                                classLoader,
                                hookMethodData.hookVariableParams);
                    } else if (hookData.hookType == HookType.HOOK_ALL_INIT
                            || hookData.hookType == HookType.HOOK_REPLACE_ALL_INIT) {
                        HookMethodData hookMethodData = (HookMethodData) hookData;
                        try {
                            XposedBridge.hookAllConstructors(classLoader.loadClass(clzName), hookMethodData.getXcMethodHook());
                        } catch (ClassNotFoundException e) {
                            Log.e(TAG, "dealHook: when hookNormalClass cant found class " + clzName);
                        }
                    } else if (hookData.hookType == HookType.HOOK_ALL_METHOD
                            || hookData.hookType == HookType.HOOK_REPLACE_ALL_METHOD) {
                        HookMethodData hookMethodData = (HookMethodData) hookData;
                        try {
                            XposedBridge.hookAllMethods(classLoader.loadClass(clzName), hookMethodData.hookTarget, hookMethodData.getXcMethodHook());
                        } catch (ClassNotFoundException e) {
                            Log.e(TAG, "dealHook: when hookNormalClass cant found class " + clzName);
                        }
                    } else if (hookData.hookType == HookType.HOOK_GET_STATIC_FIELD) {
                        try {
                            Object staticObjectField = XposedHelpers.getStaticObjectField(classLoader.loadClass(clzName), hookData.hookTarget);
                            Log.i(TAG, "dealHook: hook " + clzName + " --> " + hookData.hookTarget + " == " + staticObjectField);
                        } catch (ClassNotFoundException e) {
                            Log.e(TAG, "dealHook: when hookNormalClass cant found class " + clzName);
                        }
                    }
                }
            }
        }
    }
}
