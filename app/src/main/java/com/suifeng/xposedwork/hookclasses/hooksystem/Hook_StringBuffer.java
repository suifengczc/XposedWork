package com.suifeng.xposedwork.hookclasses.hooksystem;

import com.suifeng.xposedwork.hookmodule.BaseHookModule;
import com.suifeng.xposedwork.hookmodule.HookHelper;
import com.suifeng.xposedwork.util.Logger;
import com.suifeng.xposedwork.util.NativeUtils;

import de.robv.android.xposed.XC_MethodHook;

/**
 * @author suifengczc
 * @date 2020/3/13
 */
public class Hook_StringBuffer extends BaseHookModule {

    public Hook_StringBuffer(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected void init() {
        className = StringBuffer.class.getName();
        hookDatas.add(HookHelper.hookMethod(
                "append",
                String.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        String thisStr = param.thisObject.toString();
                        String concatStr = (String) param.args[0];
                        Logger.logi(NativeUtils.concatString("hook StringBuffer.append(String):", thisStr, " --> ", concatStr));
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
