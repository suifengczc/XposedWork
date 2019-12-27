package com.suifeng.xposedwork.hookclassloader;

import com.suifeng.xposedwork.hookmodule.ClassLoaderModule;
import com.suifeng.xposedwork.hookmodule.HookBasicData;
import com.suifeng.xposedwork.hookmodule.HookData;

import de.robv.android.xposed.XC_MethodHook;

public class Hook_DexClassLoader extends ClassLoaderModule {

    public Hook_DexClassLoader(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected void init() {
        className = "dalvik.system.DexClassLoader";
        hookDatas.add(new HookData("", new Class[]{String.class, String.class, String.class, ClassLoader.class},
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        hookPluginClasses(param);
                        super.afterHookedMethod(param);
                    }
                }, HookBasicData.HOOK_NORMAL_INIT));
    }

//    /**
//     * hook DexClassLoader 构造方法
//     */
//    private HookData DexClassLoader_Init = new HookData("", new Class[]{String.class, String.class, String.class},
//            new XC_MethodHook() {
//                @Override
//                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                    super.beforeHookedMethod(param);
//                }
//
//                @Override
//                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                    if (param.hasThrowable()) {
//                        return;
//                    }
//                    final Class<?> result = (Class<?>) param.getResult();
//                    ClassLoader loader = result.getClassLoader();
//                    String clsName = result.getName();
//                    HookList hookPluginClassList = HookStack.getHookPluginClassList();
//                    Iterator<Map.Entry<String, HookModule>> iterator = hookPluginClassList.getHookModules().entrySet().iterator();
//                    while (iterator.hasNext()) {
//                        Map.Entry<String, HookModule> next = iterator.next();
//                        String clzName = next.getKey();
//                        HookModule hookModule = next.getValue();
//                        if (hookModule != null) {
//                            List<HookData> hookDatas = hookModule.getHookDatas();
//                            for (HookData methodData : hookDatas) {
//                                if (methodData.hookType == HookData.HOOK_NORMAL_METHOD) {
//                                    XposedHelpers.findAndHookMethod(clzName,
//                                            loader,
//                                            methodData.methodName,
//                                            methodData.methodParams,
//                                            methodData.methodHook);
//                                } else if (methodData.hookType == HookData.HOOK_NORMAL_INIT) {
//                                    XposedHelpers.findAndHookConstructor(clzName,
//                                            loader,
//                                            methodData.methodParams,
//                                            methodData.methodHook);
//                                }
//                            }
//                        }
//                    }
//                    super.afterHookedMethod(param);
//                }
//            }, HookData.HOOK_NORMAL_INIT);
}
