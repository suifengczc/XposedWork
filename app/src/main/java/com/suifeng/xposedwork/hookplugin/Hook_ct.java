package com.suifeng.xposedwork.hookplugin;

import android.content.SharedPreferences;
import android.os.Environment;
import android.util.Log;

import com.suifeng.xposedwork.hookmodule.AbstractPluginClassModule;
import com.suifeng.xposedwork.hookmodule.HookMethodData;
import com.suifeng.xposedwork.hookmodule.HookType;
import com.suifeng.xposedwork.util.Reflector;

import java.io.File;
import java.io.FileWriter;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Vector;

import de.robv.android.xposed.XC_MethodHook;

public class Hook_ct extends AbstractPluginClassModule {

    /**
     * @param classLoader 加载插件的classloader
     */
    public Hook_ct(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected void init() {
        className = "ct";
        hookDatas.add(new HookMethodData("a", HookType.HOOK_NORMAL_METHOD,
                loadClass("com.google.ads.afma.proto2api.d"),
                String.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Class xClz = loadClass("com.google.android.gms.ads.internal.client.x");
                        Method dMethod = xClz.getMethod("d");
                        Object n = dMethod.invoke(null);
                        Log.i(TAG, "hook ct a : before  n = " + n + " class = " + n.getClass().toString());
                        Reflector nRef = Reflector.on("com.google.android.gms.ads.internal.config.n", false, classLoader);
                        Object c = nRef.field("c").get(n);
                        Object d = nRef.field("d").get(n);
                        Object e = nRef.field("e").get(n);
                        Object f = nRef.field("f").get(n);
                        Object g = nRef.field("g").get(n);
                        Object h = nRef.field("h").get(n);
                        Log.i(TAG, "hook ct a : before  c = " + c + " class = " + c.getClass().toString());
                        Log.i(TAG, "hook ct a : before  d = " + d + " class = " + d.getClass().toString());
                        Log.i(TAG, "hook ct a : before  e = " + e + " class = " + e.getClass().toString());
                        Log.i(TAG, "hook ct a : before  f = " + f + " class = " + f.getClass().toString());
                        Log.i(TAG, "hook ct a : before  g = " + g + " class = " + g.getClass().toString());
                        Log.i(TAG, "hook ct a : before  h = " + h + " class = " + h.getClass().toString());
                        Log.i(TAG, "hook ct a : before  h contains gads:gestures:bs:enabled = " + h.toString().contains("gads:gestures:bs:enabled"));
                        Reflector pRef = Reflector.on("com.google.android.gms.ads.internal.config.p", false, classLoader);
                        Object bw = pRef.field("bw").get();
                        Reflector cRef = Reflector.on("com.google.android.gms.ads.internal.config.c", false, classLoader);
                        Object a = cRef.field("a").get(bw);
                        Log.i(TAG, "hook ct a : before  c.a = " + a + " class = " + a.getClass().toString());
                        SharedPreferences sp = (SharedPreferences) e;
                        boolean contains = sp.contains("gads:gestures:bs:enabled");
                        if (contains) {
                            Log.i(TAG, "hook ct a : before sp contains = " + sp.getBoolean("gads:gestures:bs:enabled", false));
                        } else {
                            Log.i(TAG, "hook ct a : before sp not contains = ");
                        }
                        File storageDir = Environment.getExternalStorageDirectory();
                        File file = new File(storageDir, "outctbefore.txt");
                        if (file.exists()) {
                            file.delete();
                        }
                        file.createNewFile();
                        FileWriter fileWriter = new FileWriter(file, true);
                        fileWriter.write("hook ct a : before  c = " + c + " class = " + c.getClass().toString() + "\n");
                        fileWriter.write("hook ct a : before  d = " + d + " class = " + d.getClass().toString() + "\n");
                        fileWriter.write("hook ct a : before  e = " + e + " class = " + e.getClass().toString() + "\n");
                        fileWriter.write("hook ct a : before  f = " + f + " class = " + f.getClass().toString() + "\n");
                        fileWriter.write("hook ct a : before  g = " + g + " class = " + g.getClass().toString() + "\n");
                        fileWriter.write("hook ct a : before  h = " + h + " class = " + h.getClass().toString() + "\n");
                        fileWriter.flush();
                        fileWriter.close();
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Class xClz = loadClass("com.google.android.gms.ads.internal.client.x");
                        Method dMethod = xClz.getMethod("d");
                        Object n = dMethod.invoke(null);
                        Log.i(TAG, "hook ct a : after  n = " + n + " class = " + n.getClass().toString());
                        Reflector nRef = Reflector.on("com.google.android.gms.ads.internal.config.n", false, classLoader);
                        Object c = nRef.field("c").get(n);
                        Object d = nRef.field("d").get(n);
                        Object e = nRef.field("e").get(n);
                        Object f = nRef.field("f").get(n);
                        Object g = nRef.field("g").get(n);
                        Object h = nRef.field("h").get(n);
                        Log.i(TAG, "hook ct a : after  c = " + c + " class = " + c.getClass().toString());
                        Log.i(TAG, "hook ct a : after  d = " + d + " class = " + d.getClass().toString());
                        Log.i(TAG, "hook ct a : after  e = " + e + " class = " + e.getClass().toString());
                        Log.i(TAG, "hook ct a : after  f = " + f + " class = " + f.getClass().toString());
                        Log.i(TAG, "hook ct a : after  g = " + g + " class = " + g.getClass().toString());
                        Log.i(TAG, "hook ct a : after  h = " + h + " class = " + h.getClass().toString());
                        Log.i(TAG, "hook ct a : after  h contains gads:gestures:bs:enabled = " + h.toString().contains("gads:gestures:bs:enabled"));
                        Reflector pRef = Reflector.on("com.google.android.gms.ads.internal.config.p", false, classLoader);
                        Object bw = pRef.field("bw").get();
                        Reflector cRef = Reflector.on("com.google.android.gms.ads.internal.config.c", false, classLoader);
                        Object a = cRef.field("a").get(bw);
                        Log.i(TAG, "hook ct a : after  c.a = " + a + " class = " + a.getClass().toString());
                        SharedPreferences sp = (SharedPreferences) e;
                        boolean contains = sp.contains("gads:gestures:bs:enabled");
                        if (contains) {
                            Log.i(TAG, "hook ct a : after  sp contains = " + sp.getBoolean("gads:gestures:bs:enabled", false));
                        } else {
                            Log.i(TAG, "hook ct a : after  sp contains = ");
                        }
                        File storageDir = Environment.getExternalStorageDirectory();
                        File file = new File(storageDir, "outctafter.txt");
                        if (file.exists()) {
                            file.delete();
                        }
                        file.createNewFile();
                        FileWriter fileWriter = new FileWriter(file, true);
                        fileWriter.write("hook ct a : after  c = " + c + " class = " + c.getClass().toString() + "\n");
                        fileWriter.write("hook ct a : after  d = " + d + " class = " + d.getClass().toString() + "\n");
                        fileWriter.write("hook ct a : after  e = " + e + " class = " + e.getClass().toString() + "\n");
                        fileWriter.write("hook ct a : after  f = " + f + " class = " + f.getClass().toString() + "\n");
                        fileWriter.write("hook ct a : after  g = " + g + " class = " + g.getClass().toString() + "\n");
                        fileWriter.write("hook ct a : after  h = " + h + " class = " + h.getClass().toString() + "\n");
                        fileWriter.flush();
                        fileWriter.close();
                        super.afterHookedMethod(param);
                    }
                }));
    }

    /**
     * ct.b(byte[] bArr)
     */
    private Vector b(byte[] bArr) {
        int length;
        if (bArr == null || (length = bArr.length) <= 0) {
            return null;
        }
        int i = (length + 254) / 255;
        Vector vector = new Vector();
        int i2 = 0;
        while (i2 < i) {
            int i3 = i2 * 255;
            try {
                int length2 = bArr.length;
                if (length2 - i3 > 255) {
                    length2 = i3 + 255;
                }
                vector.add(Arrays.copyOfRange(bArr, i3, length2));
                i2++;
            } catch (IndexOutOfBoundsException e2) {
                return null;
            }
        }
        return vector;
    }

}
