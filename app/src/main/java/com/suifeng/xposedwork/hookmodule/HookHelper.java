package com.suifeng.xposedwork.hookmodule;

import android.app.AndroidAppHelper;

import com.suifeng.xposedwork.util.Logger;
import com.suifeng.xposedwork.util.Utils;

import java.util.List;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

/**
 * Hook 辅助类
 *
 * @author suifengczc
 */
public class HookHelper {

    private HookHelper() {
    }

    /**
     * 读取HookModule的HookData处理具体的hook方式
     *
     * @param classLoader 加载被hook类的ClassLoader
     * @param hookModules 需要hook的模块
     */
    public static void dealHook(ClassLoader classLoader, List<BaseHookModule> hookModules) {
        for (BaseHookModule hookModule : hookModules) {
            String clzName = hookModule.className;
            String packageName = AndroidAppHelper.currentPackageName();
            if (hookModule.checkPackageName(packageName)) {
                List<HookData> hookDatas = hookModule.getHookDatas();
                for (HookData hookData : hookDatas) {
                    handleHookData(clzName, hookData, classLoader);
                }
            }
        }
    }

    private static void handleHookData(String clzName, HookData hookData, ClassLoader classLoader) {
        switch (hookData.hookType) {
            case HOOK_NORMAL_METHOD:
            case HOOK_REPLACE_METHOD: {
                HookMethodData hookMethodData = (HookMethodData) hookData;
                XposedHelpers.findAndHookMethod(clzName,
                        classLoader,
                        hookMethodData.hookTarget,
                        hookMethodData.hookVariableParams);
                break;
            }
            case HOOK_NORMAL_INIT:
            case HOOK_REPLACE_INIT: {
                HookMethodData hookMethodData = (HookMethodData) hookData;
                XposedHelpers.findAndHookConstructor(clzName,
                        classLoader,
                        hookMethodData.hookVariableParams);
                break;
            }
            case HOOK_ALL_INIT:
            case HOOK_REPLACE_ALL_INIT: {
                HookMethodData hookMethodData = (HookMethodData) hookData;
                try {
                    XposedBridge.hookAllConstructors(classLoader.loadClass(clzName), hookMethodData.getXcMethodHook());
                } catch (ClassNotFoundException e) {
                    Logger.loge("dealHook: when hookNormalClass cant found class " + clzName);
                }
                break;
            }
            case HOOK_ALL_METHOD:
            case HOOK_REPLACE_ALL_METHOD: {
                HookMethodData hookMethodData = (HookMethodData) hookData;
                try {
                    XposedBridge.hookAllMethods(classLoader.loadClass(clzName), hookMethodData.hookTarget, hookMethodData.getXcMethodHook());
                } catch (ClassNotFoundException e) {
                    Logger.loge("dealHook: when hookNormalClass cant found class " + clzName);
                }
                break;
            }
            case HOOK_GET_STATIC_FIELD:
                Object staticObjectField;
                HookFieldData hookFieldData = (HookFieldData) hookData;
                try {
                    staticObjectField = XposedHelpers.findField(classLoader.loadClass(clzName), hookData.hookTarget).get(null);
                } catch (ClassNotFoundException | IllegalAccessException e) {
                    if (hookFieldData.callback != null) {
                        hookFieldData.callback.done(Utils.getThrowableInfo(e));
                    } else {
                        Utils.printThrowable(e);
                    }
                    break;
                }
                if (hookFieldData.callback != null) {
                    hookFieldData.callback.done(staticObjectField);
                } else {
                    Logger.loge("dealHook: hook " + clzName + " get " + hookFieldData.hookTarget + " == " + staticObjectField);
                }
                break;
            case HOOK_SET_STATIC_FIELD:
                HookFieldData fieldData = (HookFieldData) hookData;
                try {
                    XposedHelpers.findField(classLoader.loadClass(clzName), fieldData.hookTarget).set(null, fieldData.valueForSet);
                } catch (IllegalAccessException | ClassNotFoundException e) {
                    if (fieldData.callback != null) {
                        fieldData.callback.done(Utils.getThrowableInfo(e));
                    } else {
                        Utils.printThrowable(e);
                    }
                    break;
                }
                if (fieldData.callback != null) {
                    fieldData.callback.done(null);
                } else {
                    Logger.logi("dealHook: hook " + clzName + " set " + fieldData.hookTarget + " to " + fieldData.valueForSet);
                }
                break;
            default:
                break;
        }
    }

    /**
     * hook target method
     *
     * @param methodName     methodName
     * @param variableParams method 形参类型和XC_MethodHook，XC_MethodHook在最后，不可少
     * @return HookMethodData
     */
    public static HookMethodData hookMethod(String methodName, Object... variableParams) {
        return new HookMethodData(methodName, HookType.HOOK_NORMAL_METHOD, variableParams);
    }

    /**
     * hook 所有同名方法，不需要传递形参类型
     *
     * @param methodName methodName
     * @param methodHook XC_MethodHook
     * @return HookMethodData
     */
    public static HookMethodData hookAllMethod(String methodName, XC_MethodHook methodHook) {
        return new HookMethodData(methodName, HookType.HOOK_ALL_METHOD, methodHook);
    }

    /**
     * hook 指定构造函数
     *
     * @param variableParams 构造函数形参类型和XC_MethodHook，XC_MethodHook在最后，不可少
     * @return HookMethodData
     */
    public static HookMethodData hookInit(Object... variableParams) {
        return new HookMethodData("", HookType.HOOK_NORMAL_INIT, variableParams);
    }

    /**
     * hook 所有构造函数
     *
     * @param methodHook XC_MethodHook
     * @return HookMethodData
     */
    public static HookMethodData hookAllInit(XC_MethodHook methodHook) {
        return new HookMethodData("", HookType.HOOK_ALL_INIT, methodHook);
    }

    /**
     * hook and replace method，执行自己的代码
     *
     * @param methodName     methodName
     * @param variableParams
     * @return
     */
    public static HookMethodData hookAndReplaceMethod(String methodName, Object... variableParams) {
        return new HookMethodData(methodName, HookType.HOOK_REPLACE_METHOD, variableParams);
    }

    /**
     * hook and replace all method
     *
     * @param methodName methodName
     * @param methodHook method 形参类型和XC_MethodHook，XC_MethodHook在最后，不可少
     * @return HookMethodData
     */
    public static HookMethodData hookAndReplaceAllMethod(String methodName, XC_MethodHook methodHook) {
        return new HookMethodData(methodName, HookType.HOOK_REPLACE_ALL_METHOD, methodHook);
    }

    /**
     * hook and replace constructor
     *
     * @param variableParams 构造函数形参类型和XC_MethodHook，XC_MethodHook在最后，不可少
     * @return HookMethodData
     */
    public static HookMethodData hookAndReplaceInit(Object... variableParams) {
        return new HookMethodData("", HookType.HOOK_REPLACE_INIT, variableParams);
    }

    /**
     * hook and replace all constructor
     *
     * @param methodHook XC_MethodHook
     * @return HookMethodData
     */
    public static HookMethodData hookAndReplaceAllInit(XC_MethodHook methodHook) {
        return new HookMethodData("", HookType.HOOK_REPLACE_ALL_INIT, methodHook);
    }

    /**
     * hook and get static field value
     *
     * @param fieldName field name
     * @param callback  获取到static field值之后的回调，可空
     * @return HookFieldData
     */
    public static HookFieldData hookAndGetStaticField(String fieldName, HookFieldData.FieldCallback callback) {
        return new HookFieldData(fieldName, HookType.HOOK_GET_STATIC_FIELD, callback);
    }

    /**
     * hook and set a new value to static field
     *
     * @param fieldName
     * @return
     */
    public static HookFieldData hookAndSetStaticField(String fieldName) {
        return new HookFieldData(fieldName, HookType.HOOK_SET_STATIC_FIELD, null);
    }

}
