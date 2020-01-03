package com.suifeng.xposedwork.util;

import android.os.Environment;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import de.robv.android.xposed.XC_MethodHook;

public class Utils {

    public static String concatStackTrace(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : stackTrace) {
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
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
                    sb.append(result.getClass().toString()).append(" --> ").append(result.toString());
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

    /**
     * 获取需要hook的包名
     *
     * @return 包名list
     */
    public static List<String> getHookPackage() {
        String config = getConfigFromAssets();
        List<String> packageList = new ArrayList<>();
        if (!TextUtils.isEmpty(config)) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(config);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONArray hookPackage = jsonObject.optJSONArray("hook_package");
            for (int i = 0; i < hookPackage.length(); i++) {
                String s = hookPackage.optString(i);
                packageList.add(s);
            }
        }
        return packageList;
    }

    /**
     * 从模块的assets下获取hook_package.json文件中的配置
     *
     * @return json字符串
     */
    private static String getConfigFromAssets() {
        String config = "";
        //因为刚开始的时候还没有context，所以直接读取模块在/data/app下的base.apk，从压缩包中读取文件
        String dataPath = Environment.getDataDirectory().getAbsolutePath();
        String appPath = dataPath + File.separator + "app";
        String packagePath = "";
        for (int i = 1; i < 3; i++) {
            File f = new File(appPath + File.separator + "com.suifeng.xposedwork-" + i);
            if (f.exists()) {
                packagePath = f.getAbsolutePath();
                break;
            }
        }
        if (!TextUtils.isEmpty(packagePath)) {
            String basePath = packagePath + File.separator + "base.apk";
            try {
                ZipFile zipFile = new ZipFile(basePath);
                ZipEntry ze = zipFile.getEntry("assets/hook_package.json");
                InputStream inputStream = zipFile.getInputStream(ze);
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                config = sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return config;
    }

    /**
     * 打印object中所有field的值
     *
     * @param clz object的class
     * @param object 实例
     * @return object所有属性值组合后的String
     */
    public static String printObject(Class<?> clz, Object object) {
        StringBuilder sb = new StringBuilder();
        Field[] declaredFields = clz.getDeclaredFields();
        for (Field field : declaredFields) {
            field.setAccessible(true);
            sb.append(field.getName());
            sb.append(" = ");
            try {
                Object obj = field.get(object);
                if (obj == null) {
                    sb.append("null");
                } else {
                    sb.append(obj);
                    sb.append(" --> ");
                    sb.append(obj.getClass().toString());
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
