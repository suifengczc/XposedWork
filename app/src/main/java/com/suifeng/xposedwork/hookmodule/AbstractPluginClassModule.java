package com.suifeng.xposedwork.hookmodule;

/**
 * HOOK 动态加载的插件中的类模板
 *
 * @author suifengczc
 */
public abstract class AbstractPluginClassModule extends BaseHookModule {

    /**
     * @param classLoader 加载插件的classloader
     */
    public AbstractPluginClassModule(ClassLoader classLoader) {
        super(classLoader);
    }

    /**
     * 在init方法中设置className和hookDatas的值
     */
    @Override
    protected abstract void init();

    /**
     * hook 插件中的类时可能需要用到插件中的其他类
     *
     * @param cls
     * @return 需要加载的Class
     * @throws ClassNotFoundException
     */
    protected Class loadClass(String cls) {
        try {
            return classLoader.loadClass(cls);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
