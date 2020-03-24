package com.suifeng.xposedwork.hookmodule;

import com.suifeng.xposedwork.hookentry.InnerHookEntry;
import com.suifeng.xposedwork.util.Utils;
import com.suifeng.xposedwork.util.exception.ModuleApkPathException;
import com.suifeng.xposedwork.util.filter.PackageNameFilter;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.robv.android.xposed.XC_MethodHook;

/**
 * Hook ClassLoader相关类时使用的模板
 *
 * @author suifengczc
 */
public abstract class AbstractClassLoaderModule extends BaseHookModule {

    /**
     * @param classLoader 这里传入的是当前的classloader
     */
    public AbstractClassLoaderModule(ClassLoader classLoader) {
        super(classLoader);
    }

    /**
     * @param classLoader 这里传入的是当前的classloader
     * @param filter      包名筛选，指定HookModule对特定的包生效，为空时对所有包生效
     */
    public AbstractClassLoaderModule(ClassLoader classLoader, PackageNameFilter filter) {
        super(classLoader, filter);
    }

    /**
     * 在init方法中设置className和hookDatas的值
     */
    @Override
    protected abstract void init();

    /**
     * 各种ClassLoader构造方法调用完成后调用
     * hook 动态加载的插件中的类
     *
     * @param param 各种ClassLoader构造方法的MethodHookParam
     */
    protected void hookPluginClasses(XC_MethodHook.MethodHookParam param) throws IOException, ModuleApkPathException {
        if (param.hasThrowable()) {
            return;
        }
        ClassLoader loader = (ClassLoader) param.thisObject;
        if (loader == null) {
            return;
        }
        List<BaseHookModule> hookPluginClassList = getPluginHookList(loader);
        HookHelper.dealHook(loader, hookPluginClassList);
    }

    /**
     * 从HookStack获取插件中的hook类列表
     *
     * @return HookList
     */
    private List getPluginHookList(ClassLoader loader) throws IOException, ModuleApkPathException {
        List<Class> hookPluginClassList = InnerHookEntry.getInstance().getHookPluginClassList();
        List<BaseHookModule> hookList = new ArrayList<>();
        for (Class aClass : hookPluginClassList) {
            try {
                //获取hook类的HookModule的构造方法反射创建实例
                Constructor constructor = aClass.getConstructor(ClassLoader.class);
                BaseHookModule hookModule = (BaseHookModule) constructor.newInstance(loader);
                hookList.add(hookModule);
            } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                Utils.printThrowable(e);
            }
        }
        return hookList;
    }
}
