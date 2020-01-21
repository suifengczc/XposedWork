package com.suifeng.xposedwork.hookmodule;

import java.util.ArrayList;
import java.util.List;

/**
 * HOOK模板类
 * @author suifengczc
 */
public abstract class BaseHookModule {
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


    /**
     * @param classLoader 这里传入的是当前的classloader
     */
    public BaseHookModule(ClassLoader classLoader) {
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
