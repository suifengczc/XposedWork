package com.suifeng.xposedwork.hookplugin;

import android.util.Log;

import com.suifeng.xposedwork.hookmodule.HookData;
import com.suifeng.xposedwork.hookmodule.PluginClassModule;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

import de.robv.android.xposed.XC_MethodHook;

public class Hook_agm extends PluginClassModule {


    public Hook_agm(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected void init() {
        className = "agm";
        hookDatas = new ArrayList<>();
        //agm.a(Class class)
        hookDatas.add(new HookData("a", new Class[]{Class.class}, new XC_MethodHook() {
            @Override
            protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                Log.i(TAG, "hook agm: before class = " + param.args[0].toString());
                super.beforeHookedMethod(param);
            }

            @Override
            protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                Log.i(TAG, "hook agm: after ");
                Object thisObject = param.thisObject;
                Field c = thisObject.getClass().getDeclaredField("c");
                c.setAccessible(true);
                ConcurrentMap concurrentMap = (ConcurrentMap) c.get(thisObject);
                Set<Map.Entry> set = concurrentMap.entrySet();
                Iterator<Map.Entry> iterator = set.iterator();
                while (iterator.hasNext()) {
                    Map.Entry next = iterator.next();
                    Class key = (Class) next.getKey();
                    Object value = next.getValue();
                    Log.i(TAG, "hook agm after key = " + key + " value = " + value);
                }

                ClassLoader classLoader = thisObject.getClass().getClassLoader();
                Class<?> aClass = null;
                try {
                    aClass = loadClass("com.google.protobuf.DescriptorMessageInfoFactory");
                } catch (ClassNotFoundException e) {
                    Log.i(TAG, "hook agm after: load factory not found");
                }
                if (aClass == null) {
                    Log.i(TAG, "hook agm after: load factory == null");
                } else {
                    Log.i(TAG, "hook agm after: load factory not null " + aClass);
                }
                super.afterHookedMethod(param);
            }
        }));
    }
}
