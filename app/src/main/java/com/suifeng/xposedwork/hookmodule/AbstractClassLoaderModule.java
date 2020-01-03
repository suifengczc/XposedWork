package com.suifeng.xposedwork.hookmodule;

import com.suifeng.xposedwork.hook.HookStack;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import de.robv.android.xposed.XC_MethodHook;

/**
 * Hook ClassLoader相关类时使用的模板
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
     * 在init方法中设置className和hookDatas的值
     */
    @Override
    protected abstract void init();

    /**
     * 各种ClassLoader构造方法调用完成后调用
     * hook 动态加载的插件中的类
     * @param param 各种ClassLoader构造方法的MethodHookParam
     */
    protected void hookPluginClasses(XC_MethodHook.MethodHookParam param) {
        if (param.hasThrowable()) {
            return;
        }
        ClassLoader loader = (ClassLoader) param.thisObject;
        if (loader == null) {
            return;
        }
        HookList hookPluginClassList = getPluginHookList(loader);
        Map<String, BaseHookModule> hookModules = hookPluginClassList.getHookModules();
        HookHelper.dealHook(loader, hookModules);
    }

    /**
     * 从HookStack获取插件中的hook类列表
     *
     * @return HookList
     */
    private HookList getPluginHookList(ClassLoader loader) {
        List<Class> hookPluginClassList = HookStack.getHookPluginClassList();
        HookList hookList = new HookList();
        for (Class aClass : hookPluginClassList) {
            try {
                //获取hook类的HookModule的构造方法反射创建实例
                Constructor constructor = aClass.getConstructor(ClassLoader.class);
                BaseHookModule hookModule = (BaseHookModule) constructor.newInstance(loader);
                hookList.addHookModule(hookModule);
            } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return hookList;
    }
}
