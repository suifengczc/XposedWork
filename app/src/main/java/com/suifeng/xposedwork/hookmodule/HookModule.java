package com.suifeng.xposedwork.hookmodule;

import java.util.ArrayList;
import java.util.List;

/**
 * HOOK模板类
 */
public abstract class HookModule {
    protected static final String TAG = "HookDemo";

    /**
     * 被hook的类名
     */
    protected String className;

    /**
     * HookData列表
     */
    protected List<HookData> hookDatas;


    /**
     * 被hook类所属的ClassLoader
     */
    protected ClassLoader classLoader;


    public HookModule(ClassLoader classLoader) {
        hookDatas = new ArrayList<>();
        this.classLoader = classLoader;
        init();
    }


    /**
     * 在init方法中设置className和hookDatas的值
     */
    protected abstract void init();

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    public String getClassName() {
        return className;
    }

    public List<HookData> getHookDatas() {
        return hookDatas;
    }

}
