package com.suifeng.xposedwork.hookmodule;

import com.suifeng.xposedwork.util.filter.PackageNameFilter;

import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;

/**
 * HOOK模板类
 *
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
     * 包名筛选，HookModule针对指定的包名生效
     */
    protected PackageNameFilter filter;

    /**
     * @param classLoader 这里传入的是当前的classloader
     */
    public BaseHookModule(ClassLoader classLoader) {
        this(classLoader, null);
    }

    /**
     * @param classLoader 这里传入的是当前的classloader
     * @param filter 包名筛选，指定HookModule对特定的包生效，为空时对所有包生效
     */
    public BaseHookModule(ClassLoader classLoader, PackageNameFilter filter) {
        hookDatas = new ArrayList<>();
        this.classLoader = classLoader;
        this.filter = filter;
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

    /**
     * 判断当前hook到的包名是否符合当前HookModule的目标包名
     * @param packageName Xposed当前hook到的包名
     * @return true表示对当前包执行hook
     */
    public boolean checkPackageName(String packageName) {
        return filter == null || filter.filter(packageName);
    }

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
