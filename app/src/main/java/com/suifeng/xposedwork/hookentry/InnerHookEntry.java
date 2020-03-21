package com.suifeng.xposedwork.hookentry;

import com.suifeng.xposedwork.hookclasses.hookplugin.Hook_clzInPlugin;
import com.suifeng.xposedwork.hookclasses.hooksystem.Hook_String;
import com.suifeng.xposedwork.hookmodule.BaseHookModule;
import com.suifeng.xposedwork.hookmodule.HookHelper;
import com.suifeng.xposedwork.util.NativeUtils;
import com.suifeng.xposedwork.util.Utils;
import com.suifeng.xposedwork.util.exception.ModuleApkPathException;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

/**
 * Xposed 内部hook入口，实际处理hook的位置
 *
 * @author suifengczc
 */
public class InnerHookEntry implements IXposedHookLoadPackage {
    private static InnerHookEntry INSTANCE = null;

    /**
     * 需要hook的包名list
     */
    private List<String> hookPackageName;
    /**
     * 需要hook的本地类list
     */
    private List<BaseHookModule> hookClassList;
    /**
     * 需要hook的插件中的类list
     */
    private List<Class> hookPluginClassList;

    public static InnerHookEntry getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new InnerHookEntry();
        }
        return INSTANCE;
    }

    private InnerHookEntry() {
        //因为是动态加载的xposed模块，所以在内部入口加载lib，如果在HookMain中加载lib会导致找不到对应native方法
        NativeUtils.loadLibrary();
        //从hook_package.json中获取需要hook的包名
        try {
            hookPackageName = Utils.getHookPackageList();
        } catch (ModuleApkPathException e) {
            e.printStackTrace();
        }

        //plugin hook
        hookPluginClassList = new ArrayList<>();

        //normal hook
        hookClassList = new ArrayList<>();
    }


    public List<Class> getHookPluginClassList() {
        return hookPluginClassList;
    }

    /**
     * 设置需要hook的类的位置
     *
     * @param classLoader 当前的ClassLoader
     */
    private void setHookClasses(ClassLoader classLoader) {
        //hook当前包中的类
        hookClassList.add(new Hook_String(classLoader));

        //hook 动态加载的插件中的类
        hookPluginClassList.add(Hook_clzInPlugin.class);
    }

    /**
     * hook方法执行入口
     *
     * @param classLoader
     */
    private void hookNormalClass(ClassLoader classLoader) {
        setHookClasses(classLoader);
        HookHelper.dealHook(classLoader, hookClassList);
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
