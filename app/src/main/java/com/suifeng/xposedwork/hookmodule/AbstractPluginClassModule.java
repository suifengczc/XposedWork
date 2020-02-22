package com.suifeng.xposedwork.hookmodule;

import com.suifeng.xposedwork.util.filter.PackageNameFilter;

/**
 * HOOK 动态加载的插件中的类模板
 *
 * @author suifengczc
 */
public abstract class AbstractPluginClassModule extends BaseHookModule {

    /**
     * @param classLoader 这里传入的是当前的classloader
     */
    public AbstractPluginClassModule(ClassLoader classLoader) {
        super(classLoader);
    }

    /**
     * @param classLoader 这里传入的是当前的classloader
     * @param filter      包名筛选，指定HookModule对特定的包生效，为空时对所有包生效
     */
    public AbstractPluginClassModule(ClassLoader classLoader, PackageNameFilter filter) {
        super(classLoader, filter);
    }


    /**
     * 在init方法中设置className和hookDatas的值
     */
    @Override
    protected abstract void init();

}
