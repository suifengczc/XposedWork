package com.suifeng.xposedwork.hookmodule;

import de.robv.android.xposed.XC_MethodHook;

/**
 * Hook时的hook相关参数对象
 *
 * @author suifengczc
 */
public class HookMethodData extends HookData {

    /**
     * XC_MethodHook
     */
//    XC_MethodHook methodHook;
    /**
     * 组合了方法传参类型和XC_MethodHook
     */
    Object[] hookVariableParams;

    /**
     * @param hookTarget     hook 的方法名
     * @param hookType       hook type {@link HookType}
     * @param variableParams 方法传参拼接XC_MethodHook
     */
    public HookMethodData(String hookTarget, HookType hookType, Object... variableParams) {
        this.hookTarget = hookTarget;
        this.hookType = hookType;
        if (variableParams.length == 0 || !(variableParams[variableParams.length - 1] instanceof XC_MethodHook)) {
            throw new IllegalArgumentException("no callback defined");
        }
//        this.methodHook = ((XC_MethodHook) variableParams[variableParams.length - 1]);
        this.hookVariableParams = variableParams;
    }

    public XC_MethodHook getXcMethodHook() {
        return (XC_MethodHook) hookVariableParams[hookVariableParams.length - 1];
    }

}
