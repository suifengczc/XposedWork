package com.suifeng.xposedwork.hookclasses.hooksystem;

import com.suifeng.xposedwork.hookmodule.BaseHookModule;
import com.suifeng.xposedwork.hookmodule.HookHelper;
import com.suifeng.xposedwork.util.Logger;
import com.suifeng.xposedwork.util.NativeUtils;
import com.suifeng.xposedwork.util.filter.PackageNameFilter;

import de.robv.android.xposed.XC_MethodHook;

/**
 * hook 系统类示例
 *
 * @author suifengczc
 * @date 2020/2/23
 */
public class Hook_String extends BaseHookModule {

    public Hook_String(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected void init() {
        className = String.class.getName();

        //String.concat(String)
        hookDatas.add(HookHelper.hookMethod("concat",
                String.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        String thisStr = param.thisObject.toString();
                        String concatStr = (String) param.args[0];
                        Logger.logi(NativeUtils.concatString("hook String.concat(String):", thisStr, " --> ", concatStr));
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                })
        );
    }
}
