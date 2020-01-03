package com.suifeng.xposedwork.hookmodule;

public abstract class HookData {
    /**
     * 要hook的目标，可以是方法名或者参数名
     */
    String hookTarget;
    /**
     * hook type {@link HookType}
     */
    HookType hookType;
}
