package com.suifeng.xposedwork.hookentry;

import com.suifeng.xposedwork.hookclasses.hookclassloader.Hook_PathClassLoader;
import com.suifeng.xposedwork.hookclasses.hookplugin.Hook_agf;
import com.suifeng.xposedwork.hookmodule.BaseHookModule;
import com.suifeng.xposedwork.hookmodule.HookHelper;
import com.suifeng.xposedwork.hookmodule.HookList;
import com.suifeng.xposedwork.util.NativeUtils;
import com.suifeng.xposedwork.util.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Xposed 内部hook入口，实际处理hook的位置
 * @author suifengczc
 */
public class InnerHookEntry implements IXposedHookLoadPackage {

    /**
     * 需要hook的包名list
     */
    private static List<String> hookPackageName;
    /**
     * 需要hook的本地类list
     */
    private static HookList hookClassList;
    /**
     * 需要hook的插件中的类list
     */
    private static List<Class> hookPluginClassList;

    public InnerHookEntry() {
        //因为是动态加载的xposed模块，所以在内部入口加载lib，如果在HookMain中加载lib会导致找不到对应native方法
        NativeUtils.loadLibrary();
        //从hook_package.json中获取需要hook的包名
        hookPackageName = Utils.getHookPackageList();

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
//        hookClassList.addHookModule(new Hook_String(classLoader));
//        hookClassList.addHookModule(new Hook_Launcher(classLoader,null));
//        hookClassList.addHookModule(new Hook_Activity(classLoader));
//        hookClassList.addHookModule(new Hook_Instrumentation(classLoader));
//        hookClassList.addHookModule(new Hook_Intent(classLoader));
//        hookClassList.addHookModule(new Hook_ActivityManagerNative(classLoader, null));

        //hook 动态加载的插件中的类
//        hookPluginClassList.add(Hook_agf.class);
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
