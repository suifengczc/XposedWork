package com.suifeng.xposedwork.hookclasses.hookplugin;

import com.suifeng.xposedwork.hookmodule.BaseHookModule;
import com.suifeng.xposedwork.hookmodule.HookHelper;
import com.suifeng.xposedwork.util.Logger;
import com.suifeng.xposedwork.util.Utils;
import com.suifeng.xposedwork.util.filter.PackageNameFilter;

import de.robv.android.xposed.XC_MethodHook;


/**
 * hook 动态加载的插件中的类示例
 *
 * @author suifengczc
 */
public class Hook_clzInPlugin extends BaseHookModule {

    public Hook_clzInPlugin(ClassLoader classLoader, PackageNameFilter filter) {
        super(classLoader, filter);
    }

    @Override
    protected void init() {
        className = "com.plugin.clzInPlugin";

        //hook 插件中的类的方法
        hookDatas.add(HookHelper.hookMethod("a",
                loadClass("com.plugin.clzInPlugin1"),
                loadClass("com.plugin.clzInPlugin2"),
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Logger.logi("hook clzInPlugin a : before \n" + Utils.concatParams(param));
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Logger.logi("hook clzInPlugin a : after " + Utils.concatResult(param));
                        super.afterHookedMethod(param);
                    }
                })
        );

        //hook 插件中的类的构造方法
        hookDatas.add(HookHelper.hookInit(
                int.class,
                String.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Logger.logi("");
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                }
        ));

    }
}
