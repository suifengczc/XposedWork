package com.suifeng.xposedwork.util;

import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import de.robv.android.xposed.XC_MethodHook;

public class Utils {

    static int count = 0;

    public static String concatStackTrace(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : stackTrace) {
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }

    public static void printLog(String tag, String packageName, String pre, XC_MethodHook.MethodHookParam params, String stacktrace) {
        StringBuilder sb = new StringBuilder();
        Object[] args = params.args;
        if (args != null && args.length > 0) {
            for (Object arg : args) {
                if (arg != null) {
                    sb.append(arg.getClass().toString()).append(" --> ").append(arg.toString()).append("\n");
                } else {
                    sb.append("this arg is null \n");
                }
            }
        }

        Object result = params.getResult();
        if (result != null) {
            if (result.getClass().toString().contains("[B")) {
                boolean flag = true;
                byte[] bytes = (byte[]) result;
                sb.append("result = ");
                sb.append(result.toString());
                sb.append(" = [");
                for (int i = 0; i < bytes.length; i++) {
                    byte aByte = bytes[i];
                    sb.append(aByte);
                    if (i < bytes.length - 1) {
                        sb.append(",");
                    }
                }
                sb.append("]");
                sb.append("\n");
                String s = Base64.encodeToString(bytes, !flag ? 2 : 11);
                sb.append("encode bytes = ").append(s);
            } else {
                sb.append("\n result = ").append(result.toString()).append("\n class = ").append(result.getClass().toString());
            }
        }
        sb.append("\n");
        Log.i(tag, count + "  in " + packageName + "\n" + pre + ": \n" + sb.toString());
        if (!TextUtils.isEmpty(stacktrace)) {
            Log.i(tag, count + "  in " + packageName + " \n" + stacktrace);
        }
        count++;
    }

    public static String concatResult(XC_MethodHook.MethodHookParam params) {
        StringBuilder sb = new StringBuilder();
        sb.append("result = ");
        Throwable throwable = params.getThrowable();
        if (throwable != null) {
            sb.append(throwable.toString());
        } else {
            Object result = params.getResult();
            if (result != null) {
                if (result.getClass().toString().startsWith("[")) {
                    Object[] objArr = (Object[]) result;
                    sb.append(concatArrays(objArr));
                } else {
                    sb.append(result.toString());
                }
            } else {
                sb.append("result is null ");
            }
        }
        return sb.append("\n").toString();
    }

    public static String concatParams(XC_MethodHook.MethodHookParam params) {
        StringBuilder sb = new StringBuilder();
        Object[] args = params.args;
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if (arg != null) {
                    sb.append(arg.getClass().toString());
                    sb.append(" --> ");
                    sb.append(arg.toString());
                    sb.append("\n");
                } else {
                    sb.append("this arg is null \n");
                }
            }
        }
        return sb.toString();
    }

    public static String concatArrays(Object[] objArr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < objArr.length; i++) {
            sb.append(objArr[i]);
            if (i < objArr.length - 1) {
                sb.append(", ");
            }
        }
        return sb.append("\n").toString();
    }
}
