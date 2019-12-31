package com.suifeng.xposedwork.hookmodule;

import de.robv.android.xposed.XC_MethodHook;

/**
 *
 */
public class HookData extends HookBasicData {
    /**
     * 默认当作方法名，当hook参数时，当作参数名来用
     */
    String methodName;

    /**
     * 方法传参的类型Class数组
     */
    private Class[] methodParams;
    /**
     * XC_MethodHook
     */
    XC_MethodHook methodHook;
    Object[] hookVariableParams;//组合了方法传参类型和XC_MethodHook

    public HookData(String methodName, Class[] methodParams, XC_MethodHook methodHook) {
        this(methodName, methodParams, methodHook, HookBasicData.HOOK_NORMAL_METHOD);
    }

    public HookData(String methodName, Class[] methodParams, XC_MethodHook methodHook, int hookType) {
        this.methodName = methodName;
        this.methodHook = methodHook;
        this.hookType = hookType;
        if (methodParams != null) {
            //组合方法参数和XC_MethodHook
            this.methodParams = methodParams;
            int length = this.methodParams.length + 1;
            hookVariableParams = new Object[length];
            System.arraycopy(this.methodParams, 0, hookVariableParams, 0, length - 1);
            hookVariableParams[length - 1] = this.methodHook;
        } else {
            hookVariableParams = new Object[1];
            hookVariableParams[0] = this.methodHook;
        }

    }

}
