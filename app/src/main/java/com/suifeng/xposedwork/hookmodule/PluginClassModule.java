package com.suifeng.xposedwork.hookmodule;

/**
 * HOOK 动态加载的插件中的类模板
 */
public abstract class PluginClassModule extends HookModule {

    /**
     * @param classLoader  加载插件的classloader
     */
    public PluginClassModule(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected abstract void init();

    /**
     * hook 插件中的类时可能需要用到插件中的其他类
     * @param cls
     * @return  需要加载的Class
     * @throws ClassNotFoundException
     */
    protected Class loadClass(String cls) throws ClassNotFoundException {
        return classLoader.loadClass(cls);
    }
}
