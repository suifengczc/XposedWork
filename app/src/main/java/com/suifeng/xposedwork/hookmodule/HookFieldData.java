package com.suifeng.xposedwork.hookmodule;

/**
 * Hook static field
 *
 * @author suifengczc
 */
public class HookFieldData extends HookData {

    public HookFieldData(String hookTarget, HookType hookType) {
        this.hookTarget = hookTarget;
        this.hookType = hookType;
    }
}
