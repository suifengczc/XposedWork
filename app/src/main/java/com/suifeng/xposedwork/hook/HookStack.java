package com.suifeng.xposedwork.hook;

import android.util.Log;

import com.suifeng.xposedwork.hookmodule.HookHelper;
import com.suifeng.xposedwork.hookmodule.HookList;
import com.suifeng.xposedwork.hookmodule.HookModule;
import com.suifeng.xposedwork.hookclassloader.Hook_PathClassLoader;
import com.suifeng.xposedwork.hookplugin.Hook_agf;
import com.suifeng.xposedwork.hookplugin.Hook_client_x;
import com.suifeng.xposedwork.hookplugin.Hook_config_p;
import com.suifeng.xposedwork.util.Utils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class HookStack implements IXposedHookLoadPackage {
    private static final String TAG = "HookDemo";

    private static Object object = new Object();

    private static final List<String> hookPackageName = new ArrayList<>();
    private static HookList hookClassList;
    private static List<Class> hookPluginClassList;

    public HookStack() {
        hookPackageName.add("com.guuidea.admoddemo");
//        hookPackageName.add("com.google.android.gms");

        //plugin hook
        hookPluginClassList = new ArrayList<>();
        hookPluginClassList.add(Hook_config_p.class);
        hookPluginClassList.add(Hook_client_x.class);

        //normal hook
        hookClassList = new HookList();

    }


    public static List<Class> getHookPluginClassList() {
        return hookPluginClassList;
    }

    private void hookNormalClass(ClassLoader classLoader) {
        hookClassList.addHookModule(new Hook_PathClassLoader(classLoader));
        Map<String, HookModule> hookModules = hookClassList.getHookModules();
        HookHelper.dealHook(classLoader, hookModules);
    }


    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {

        if (!hookPackageName.contains(lpparam.packageName)) {
            return;
        }

        ClassLoader classLoader = lpparam.classLoader;
        hookNormalClass(classLoader);

//        hookMethodLast("startViaZygote");

//        final String packageName = lpparam.packageName;
//        ClassLoader classLoader = this.getClass().getClassLoader();

//        hookClassloader_loadClass("com.google.android.gms.ads.nonagon.signals.i", i_init,String.class);
//        hookClassloader_loadClass("uw", uw_init);
//        XposedHelpers.findAndHookMethod(android.os.Process.class,)

//        XposedBridge.hookAllMethods(android.os.Process.class, "startViaZygote", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                Object[] args = param.args;
//                Integer f = (Integer) args[5];
//                f |= 1;
//                args[5] = f;
//                super.beforeHookedMethod(param);
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//            }
//        });

//        XposedBridge.hookAllConstructors(URL.class, new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                super.beforeHookedMethod(param);
//                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//                String s = Utils.concatStackTrace(stackTrace);
//                Utils.printLog(TAG, packageName, "hook url init", param, s);
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//            }
//        });

//        XposedBridge.hookAllConstructors(String.class, new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//                String s = Utils.concatStackTrace(stackTrace);
//                Utils.printLog(TAG, packageName, "hook string init", param, s);
//                super.beforeHookedMethod(param);
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//            }
//        });

//        XposedBridge.hookAllMethods(StringBuilder.class, "append", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//                String s = Utils.concatStackTrace(stackTrace);
//                Utils.printLog(TAG, packageName, "hook StringBuilder append " + param.thisObject.toString(), param, s);
//                super.beforeHookedMethod(param);
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//            }
//        });
//
//        XposedBridge.hookAllMethods(StringBuffer.class, "append", new XC_MethodHook() {
//            @Override
//            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//                String s = Utils.concatStackTrace(stackTrace);
//                Utils.printLog(TAG, packageName, "hook StringBuffer append " + param.thisObject.toString(), param, s);
//                super.beforeHookedMethod(param);
//            }
//
//            @Override
//            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                super.afterHookedMethod(param);
//            }
//        });


//        XposedHelpers.findAndHookMethod(StringBuilder.class,
//                "append",
//                String.class,
//                new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        boolean cpgE = param.thisObject.toString().contains("CpgE");
////                        if (cpgE) {
//                            Utils.printLog(TAG, packageName, "hook StringBuilder append " + cpgE, param,
//                                    Utils.concatStackTrace(Thread.currentThread().getStackTrace()));
////                        }
//                        super.beforeHookedMethod(param);
//                    }
//
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        super.afterHookedMethod(param);
//                    }
//                });
//
//        XposedHelpers.findAndHookMethod(StringBuffer.class,
//                "append",
//                String.class,
//                new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        boolean cpgE = param.thisObject.toString().contains("CpgE");
////                        if (cpgE) {
//                            Utils.printLog(TAG, packageName, "hook StringBuffer append " + cpgE, param,
//                                    Utils.concatStackTrace(Thread.currentThread().getStackTrace()));
////                        }
//                        super.beforeHookedMethod(param);
//                    }
//
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        super.afterHookedMethod(param);
//                    }
//                });


//        XposedHelpers.findAndHookMethod("java.lang.StringBuilder", lpparam.classLoader,
//                "append", String.class, new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        boolean cpgE = param.thisObject.toString().contains("CpgE");
//                        if (true) {
//                            Utils.printLog(TAG, packageName, "hook StringBuilder append", param,
//                                    Utils.concatStackTrace(Thread.currentThread().getStackTrace()));
//                        }
//                        super.beforeHookedMethod(param);
//                    }
//
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        super.afterHookedMethod(param);
//                    }
//                });
//        XposedHelpers.findAndHookMethod("java.lang.StringBuffer", lpparam.classLoader,
//                "append", String.class, new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        boolean cpgE = param.thisObject.toString().contains("CpgE");
//                        if (true) {
//                            Utils.printLog(TAG, packageName, "hook StringBuffer append", param,
//                                    Utils.concatStackTrace(Thread.currentThread().getStackTrace()));
//                        }
//                        super.beforeHookedMethod(param);
//                    }
//
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        super.afterHookedMethod(param);
//                    }
//                });
    }

    void hookGMS_i_init(XC_LoadPackage.LoadPackageParam lpparam) {
        XposedHelpers.findAndHookConstructor("com.google.android.gms.ads.nonagon.signals.i",
                lpparam.classLoader,
                String.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Object[] args = param.args;
                        for (Object arg : args) {
                            Log.i(TAG, "hook gms i init : str =  " + arg.toString());
                        }
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        super.afterHookedMethod(param);
                    }
                });
    }

    void hookClassloader_loadClass(final String hookClass, final XC_MethodHook methodHook) {
//        XposedHelpers.findAndHookMethod(Base64.class, "encodeToString",
//                byte[].class,
//                int.class,
//                new XC_MethodHook() {
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        Utils.printLog(TAG,"","hook base64 encodeToString : ",param,Utils.concatStackTrace(Thread.currentThread().getStackTrace()));
//                        super.afterHookedMethod(param);
//                    }
//                });
//        XposedHelpers.findAndHookMethod(Base64.class, "encodeToString",
//                byte[].class,
//                int.class,
//                int.class,
//                int.class,
//                new XC_MethodHook() {
//                    @Override
//                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                        Utils.printLog(TAG,"","hook base64 encodeToString : ",param,Utils.concatStackTrace(Thread.currentThread().getStackTrace()));
//                        super.afterHookedMethod(param);
//                    }
//                });
//        Method[] methods = Vector.class.getMethods();
//        for (Method method : methods) {
//            Log.i(TAG, "Vector all methods named =  " + method);
//        }
//        XposedHelpers.findAndHookMethod(java.util.AbstractList.class,
//                "iterator",
//                new XC_MethodHook() {
//                    @Override
//                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                        Utils.printLog(TAG, "this class = " +param.thisObject.getClass().toString(), "hook Vector iterator ", param, Utils.concatStackTrace(Thread.currentThread().getStackTrace()));
//                        super.beforeHookedMethod(param);
//                    }
//                });
        XposedHelpers.findAndHookMethod(ClassLoader.class,
                "loadClass",
                String.class,
                new XC_MethodHook() {
                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        if (param.hasThrowable()) {
                            return;
                        }
                        final Class<?> result = (Class<?>) param.getResult();
                        ClassLoader classLoader = result.getClassLoader();
                        String clsName = result.getName();
                        if ("ago".equals(clsName)) {
                            XposedBridge.hookAllConstructors(result, new XC_MethodHook() {
                                @Override
                                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                                    Log.i(TAG, "hook ago init: before ");
                                    super.beforeHookedMethod(param);
                                }

                                @Override
                                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                                    Log.i(TAG, "hook ago init: after ");
                                    super.afterHookedMethod(param);
                                }
                            });
//                            XposedHelpers.findAndHookConstructor(result,
//                                    classLoader.loadClass("agc"),
//                                    String.class,
//                                    Object[].class,
//                                    new XC_MethodHook() {
//                                        @Override
//                                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                                            Log.i(TAG, "hook ago init: before ");
//                                            super.beforeHookedMethod(param);
//                                        }
//
//                                        @Override
//                                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                            Log.i(TAG, "hook ago init: after ");
//                                            super.afterHookedMethod(param);
//                                        }
//                                    }
//                            );
                        }
//                        if (hookClass.equals(clsName)) {
//                            XposedHelpers.findAndHookConstructor(result,
//                                    classLoader.loadClass("wb"),
//                                    Object.class,
//                                    methodHook);
//                        }
//                        if ("com.google.android.gms.ads.nonagon.signals.i".equals(clsName)) {
//                            XposedHelpers.findAndHookConstructor(result,
//                                    String.class,
//                                    i_init);
//                        }
//                        if ("vw".equals(clsName)) {
//                            XposedHelpers.findAndHookMethod(result,
//                                    "a",
//                                    Future.class,
//                                    new XC_MethodHook() {
//                                        @Override
//                                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                                            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
//                                            Utils.printLog(TAG, "", "hook vw a :", param, Utils.concatStackTrace(stackTrace));
//                                            super.beforeHookedMethod(param);
//                                        }
//                                    });
//                        }

//                        if ("com.google.android.gms.ads.internal.ah".equals(clsName)) {
//                            XposedHelpers.findAndHookMethod(result,
//                                    "a",
//                                    Context.class,
//                                    new XC_MethodHook() {
//                                        @Override
//                                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                                            Utils.printLog(TAG, "", "hook ah a before:", param, "");
//                                            super.beforeHookedMethod(param);
//                                        }
//
//                                        @Override
//                                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                            Object thisObject = param.thisObject;
//                                            Field b = thisObject.getClass().getDeclaredField("b");
//                                            b.setAccessible(true);
//                                            Object bField = b.get(thisObject);
//                                            Method get = bField.getClass().getMethod("get");
//                                            Object invoke = get.invoke(bField);
//                                            String name = invoke.getClass().getName();
//                                            Log.i(TAG, "hook ah a after: b.get class = " + name);
//                                            Object result1 = param.getResult();
//                                            if (result1 != null) {
//                                                Log.i(TAG, "hook ah a after: " + result1.toString());
//                                            }
//                                            super.afterHookedMethod(param);
//                                        }
//                                    });
//                        }

//                        if ("dn".equals(clsName)) {
//                            Method[] methods = result.getMethods();
//                            for (Method method : methods) {
//                                Log.e(TAG, "all method in dn named  = " + method.toString());
//                            }
//                            XposedHelpers.findAndHookMethod(result,
//                                    "a",
//                                    android.content.Context.class,
//                                    java.lang.String.class,
//                                    int.class,
//                                    android.view.View.class,
//                                    android.app.Activity.class,
//                                    byte[].class,
//                                    new XC_MethodHook() {
//                                        @Override
//                                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                                            Utils.printLog(TAG, "", "hook dn a before ", param, "");
//                                            super.beforeHookedMethod(param);
//                                        }
//
//                                        @Override
//                                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                            Utils.printLog(TAG, "", "hook dn a after ", param, "");
//                                            super.afterHookedMethod(param);
//                                        }
//                                    });
//                        }

//                        if ("ct".equals(clsName)) {
//                            XposedBridge.hookAllMethods(result,
//                                    "a",
//                                    new XC_MethodHook() {
//                                        @Override
//                                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                                            Utils.printLog(TAG, "", "hook ct a before ", param, Utils.concatStackTrace(Thread.currentThread().getStackTrace()));
//                                            super.beforeHookedMethod(param);
//                                        }
//
//                                        @Override
//                                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                            Utils.printLog(TAG, "", "hook ct a after ", param, Utils.concatStackTrace(Thread.currentThread().getStackTrace()));
//                                            super.afterHookedMethod(param);
//                                        }
//                                    });
//                        }

//                        if ("adc".equals(clsName)) {
//                            XposedHelpers.findAndHookMethod(result,
//                                    "b",
//                                    new XC_MethodHook() {
//                                        @Override
//                                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                            Utils.printLog(TAG, "", "hook adc b after ", param, Utils.concatStackTrace(Thread.currentThread().getStackTrace()));
//                                            super.afterHookedMethod(param);
//                                        }
//                                    });
//                        }

//                        if ("cq".equals(clsName)) {
//                            XposedHelpers.findAndHookMethod(result,
//                                    "a",
//                                    Class.forName("[B"),
//                                    boolean.class,
//                                    new XC_MethodHook() {
//                                        @Override
//                                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                                            Utils.printLog(TAG, "", "hook cq a before ", param, Utils.concatStackTrace(Thread.currentThread().getStackTrace()));
//                                            super.beforeHookedMethod(param);
//                                        }
//
//                                        @Override
//                                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                            Utils.printLog(TAG, "", "hook cq a after ", param, Utils.concatStackTrace(Thread.currentThread().getStackTrace()));
//                                            super.afterHookedMethod(param);
//                                        }
//                                    });
//                        }

//                        if ("ct".equals(clsName)) {
//                            Method[] methods = result.getMethods();
//                            for (Method method : methods) {
//                                Log.e(TAG, "all method in ct named  = " + method.toString());
//                            }
//                            XposedHelpers.findAndHookMethod(result,
//                                    "a",
//                                    classLoader.loadClass("com.google.ads.afma.proto2api.d"),
//                                    String.class,
//                                    new XC_MethodHook() {
//                                        @Override
//                                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                                            Utils.printLog(TAG, "", "hook ct a before ", param, "");
//                                            super.beforeHookedMethod(param);
//                                        }
//
//                                        @Override
//                                        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
//                                            Utils.printLog(TAG, "", "hook ct a after ", param, "");
//                                            super.afterHookedMethod(param);
//                                        }
//                                    });
//                        }

//                        if ("dy".equals(clsName)) {
//                            XposedBridge.hookAllConstructors(result, new XC_MethodHook() {
//                                @Override
//                                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
//                                    Utils.printLog(TAG, "", "hook dy init ", param, Utils.concatStackTrace(Thread.currentThread().getStackTrace()));
//                                    super.beforeHookedMethod(param);
//                                }
//
//
//                            });
////                            XposedHelpers.findAndHookConstructor(result,
////                                    classLoader.loadClass("dm"),
////                                    new XC_MethodHook() {
////                                        @Override
////                                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
////                                            Utils.printLog(TAG, "", "hook dy init ", param, Utils.concatStackTrace(Thread.currentThread().getStackTrace()));
////                                            super.beforeHookedMethod(param);
////                                        }
////                                    });
//                        }
                        super.afterHookedMethod(param);
                    }
                });
    }

    XC_MethodHook i_init = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            Utils.printLog(TAG, "", "hook i init:  str = ", param, Utils.concatStackTrace(stackTrace));
            super.beforeHookedMethod(param);
        }

        @Override
        protected void afterHookedMethod(MethodHookParam param) throws Throwable {
            super.afterHookedMethod(param);
        }
    };

    XC_MethodHook uw_init = new XC_MethodHook() {
        @Override
        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
            StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
            Utils.printLog(TAG, "", "hook uw init: ", param, Utils.concatStackTrace(stackTrace));
            super.beforeHookedMethod(param);
        }
    };

    void printStack() {

    }


    void hookMethodLast(String name) {
        hookMethodLast(android.os.Process.class, name);
    }

    void hookMethodLast(Class<?> clz, String name) {
        try {
            int max = -1;
            Method hookMethod = null;

            for (Method method : clz.getDeclaredMethods()) {
                if (method.getName().equals(name)) {
                    int l = method.getParameterTypes().length;
                    if (l > max) {
                        hookMethod = method;
                        max = l;
                    }
                }
            }

            if (hookMethod != null) {
                Class<?>[] classes = hookMethod.getParameterTypes();

                StringBuilder sb = new StringBuilder(64);
                sb.append(hookMethod.getName()).append('(');
                for (Class<?> cls : classes) {
                    sb.append(cls.getName()).append(", ");
                }

                hookMethod(hookMethod);
            } else {
            }
        } catch (Exception e) {
            XposedBridge.log(e);
        }
    }

    void hookMethod(Method m) {
        try {
            XposedBridge.hookMethod(m, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    boolean equals = "startViaZygote".equals(param.method.getName());
                    if (equals) {
                        Object[] args = param.args;
                        Integer f = (Integer) args[5];
                        f |= 1;
                        args[5] = f;
                    }
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    super.afterHookedMethod(param);
                }
            });
        } catch (Exception e) {
            XposedBridge.log(e);
        }
    }
}
