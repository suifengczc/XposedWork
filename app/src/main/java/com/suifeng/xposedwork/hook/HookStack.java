package com.suifeng.xposedwork.hook;

import com.suifeng.xposedwork.hookclassloader.Hook_PathClassLoader;
import com.suifeng.xposedwork.hookmodule.BaseHookModule;
import com.suifeng.xposedwork.hookmodule.HookHelper;
import com.suifeng.xposedwork.hookmodule.HookList;
import com.suifeng.xposedwork.hookplugin.Hook_ct;
import com.suifeng.xposedwork.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookStack implements IXposedHookLoadPackage {
    private static final String TAG = "HookDemo";

    private static List<String> hookPackageName;
    private static HookList hookClassList;
    private static List<Class> hookPluginClassList;

    public HookStack() {
        //从hook_package.json中获取需要hook的包名
        hookPackageName = Utils.getHookPackage();

        //plugin hook
        hookPluginClassList = new ArrayList<>();

        //normal hook
        hookClassList = new HookList();
    }


    public static List<Class> getHookPluginClassList() {
        return hookPluginClassList;
    }

    /**
     * 设置需要hook的类的位置
     *
     * @param classLoader 当前的ClassLoader
     */
    private void setHookClasses(ClassLoader classLoader) {
        //hook当前包中的类
        hookClassList.addHookModule(new Hook_PathClassLoader(classLoader));

        //hook 动态加载的插件中的类
        hookPluginClassList.add(Hook_ct.class);
    }

    /**
     * hook方法执行入口
     *
     * @param classLoader
     */
    private void hookNormalClass(ClassLoader classLoader) {
        setHookClasses(classLoader);
        Map<String, BaseHookModule> hookModules = hookClassList.getHookModules();
        HookHelper.dealHook(classLoader, hookModules);
    }


    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        if (!hookPackageName.contains(lpparam.packageName)) {
            return;
        }
        ClassLoader classLoader = lpparam.classLoader;
        hookNormalClass(classLoader);
    }

}
