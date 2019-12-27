package com.suifeng.xposedwork.hookmodule;

public abstract class HookBasicData {

    public static final int HOOK_NORMAL_METHOD = 0; //普通hook方法
    public static final int HOOK_NORMAL_INIT = 1; //普通hook构造函数
    public static final int HOOK_ALL_METHOD = 3; //hook所有方法
    public static final int HOOK_ALL_INIT = 4; //hook所有构造函数
    public static final int HOOK_GET_STATIC_FIELD = 5;//hook类的static变量

    public int hookType;
}
