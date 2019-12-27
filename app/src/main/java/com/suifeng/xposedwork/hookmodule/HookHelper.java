package com.suifeng.xposedwork.hookmodule;

import android.util.Log;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class HookHelper {
    private static final String TAG = "HookDemo";

    /**
     * 读取HookModule的HookData处理具体的hook方式
     * @param classLoader
     * @param hookModules
     */
    public static void dealHook(ClassLoader classLoader, Map<String, HookModule> hookModules) {
        Iterator<Map.Entry<String, HookModule>> iterator = hookModules.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, HookModule> next = iterator.next();
            String clzName = next.getKey();
            HookModule hookModule = next.getValue();
            if (hookModule != null) {
//                hookModule.setClassLoader(classLoader);
                List<HookData> hookDatas = hookModule.getHookDatas();
                for (HookData hookData : hookDatas) {
                    if (hookData.hookType == HookBasicData.HOOK_NORMAL_METHOD) {
                        XposedHelpers.findAndHookMethod(clzName,
                                classLoader,
                                hookData.methodName,
                                hookData.hookVariableParams);
                    } else if (hookData.hookType == HookBasicData.HOOK_NORMAL_INIT) {
                        XposedHelpers.findAndHookConstructor(clzName,
                                classLoader,
                                hookData.hookVariableParams);
                    } else if (hookData.hookType == HookBasicData.HOOK_ALL_INIT) {
                        try {
                            XposedBridge.hookAllConstructors(classLoader.loadClass(clzName), hookData.methodHook);
                        } catch (ClassNotFoundException e) {
                            Log.e(TAG, "dealHook: when hookNormalClass cant found class " + clzName);
                        }
                    } else if (hookData.hookType == HookBasicData.HOOK_ALL_METHOD) {
                        try {
                            XposedBridge.hookAllMethods(classLoader.loadClass(clzName), hookData.methodName, hookData.methodHook);
                        } catch (ClassNotFoundException e) {
                            Log.e(TAG, "dealHook: when hookNormalClass cant found class " + clzName);
                        }
                    } else if (hookData.hookType == HookBasicData.HOOK_GET_STATIC_FIELD) {
                        try {
                            Object staticObjectField = XposedHelpers.getStaticObjectField(classLoader.loadClass(clzName), hookData.methodName);
                            Log.i(TAG, "dealHook: hook " + clzName + " --> " + hookData.methodName + " == " + staticObjectField);
                        } catch (ClassNotFoundException e) {
                            Log.e(TAG, "dealHook: when hookNormalClass cant found class " + clzName);
                        }
                    }
                }
            }
        }
    }
}
