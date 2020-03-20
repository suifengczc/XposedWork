package com.suifeng.xposedwork.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.text.TextUtils;

import com.suifeng.xposedwork.hookentry.OuterHookEntry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import de.robv.android.xposed.XC_MethodHook;

public class Utils {

    /**
     * 拼接堆栈信息
     *
     * @param stackTrace 堆栈数组
     * @return 堆栈数组拼接字符串
     */
    public static String concatStackTrace(StackTraceElement[] stackTrace) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : stackTrace) {
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }


    /**
     * 获取当前线程调用堆栈
     *
     * @return 当前堆栈字符串
     */
    public static String getStackTrace() {
        return concatStackTrace(Thread.currentThread().getStackTrace());
    }

    /**
     * 拼接被hook方法的执行结果
     *
     * @param params afterHookedMethod回调方法中传过来的param
     * @return 结果拼接字符串
     */
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
                sb.append("[this object is null]");
            }
        }
        return sb.append("\n").toString();
    }

    /**
     * 拼接被hook方法中传入的所有参数
     *
     * @param params beforeHookedMethod或afterHookedMethod回调方法中传过来的param
     * @return 参数拼接字符串
     */
    public static String concatParams(XC_MethodHook.MethodHookParam params) {
        StringBuilder sb = new StringBuilder();
        Object[] args = params.args;
        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                Object arg = args[i];
                if (arg != null) {
                    sb.append(arg.getClass().toString());
                    sb.append(" --> ");
                    sb.append(getObjectString(arg));
                    sb.append("\n");
                } else {
                    sb.append("[this object is null]\n");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 拼接double数组
     *
     * @param doubleArr
     * @return
     */
    public static String concatArrays(double[] doubleArr) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (double i : doubleArr) {
            sb.append(i);
            sb.append(", ");
        }
        sb.append("]");
        return sb.toString().replace(", ]", "]");
    }

    /**
     * 拼接float数组
     *
     * @param floatArr
     * @return
     */
    public static String concatArrays(float[] floatArr) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (float i : floatArr) {
            sb.append(i);
            sb.append(", ");
        }
        sb.append("]");
        return sb.toString().replace(", ]", "]");
    }

    /**
     * 拼接short数组
     *
     * @param shortArr
     * @return
     */
    public static String concatArrays(short[] shortArr) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (short i : shortArr) {
            sb.append(i);
            sb.append(", ");
        }
        sb.append("]");
        return sb.toString().replace(", ]", "]");
    }

    /**
     * 拼接boolean数组
     *
     * @param boolArr
     * @return
     */
    public static String concatArrays(boolean[] boolArr) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (boolean i : boolArr) {
            sb.append(i);
            sb.append(", ");
        }
        sb.append("]");
        return sb.toString().replace(", ]", "]");
    }

    /**
     * 拼接char数组
     *
     * @param charArr
     * @return
     */
    public static String concatArrays(char[] charArr) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (char i : charArr) {
            sb.append(i);
            sb.append(", ");
        }
        sb.append("]");
        return sb.toString().replace(", ]", "]");
    }

    /**
     * 拼接long数组
     *
     * @param longArr
     * @return
     */
    public static String concatArrays(long[] longArr) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (long i : longArr) {
            sb.append(i);
            sb.append(", ");
        }
        sb.append("]");
        return sb.toString().replace(", ]", "]");
    }

    /**
     * 拼接int数组
     *
     * @param intArr
     * @return
     */
    public static String concatArrays(int[] intArr) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i : intArr) {
            sb.append(i);
            sb.append(", ");
        }
        sb.append("]");
        return sb.toString().replace(", ]", "]");
    }

    /**
     * 拼接byte数组
     *
     * @param byteArr
     * @return
     */
    public static String concatArrays(byte[] byteArr) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i : byteArr) {
            sb.append(i);
            sb.append(", ");
        }
        sb.append("]");
        return sb.toString().replace(", ]", "]");
    }

    /**
     * 拼接Object数组
     *
     * @param objArr
     * @return
     */
    public static String concatArrays(Object[] objArr) {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Object obj : objArr) {
            if (obj != null) {
                sb.append(obj);
            } else {
                sb.append("[this object is null]");
            }
        }
        sb.append("]");
        return sb.toString().replace(", ]", "]");
    }

    /**
     * 获取需要hook的包名
     *
     * @return 包名list
     */
    public static List<String> getHookPackageList() {
        String config = getStringFromAssets("hook_package.json");
        List<String> packageList = new ArrayList<>();
        if (!TextUtils.isEmpty(config)) {
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(config);
            } catch (JSONException e) {
                Utils.printThrowable(e);
            }
            JSONArray hookPackage = jsonObject.optJSONArray("hook_package");
            for (int i = 0; i < hookPackage.length(); i++) {
                String s = hookPackage.optString(i);
                packageList.add(s);
            }
        }
        return packageList;
    }

    public static String getLibPath(String libName) {
        String modulePath = getModulePath();
        if (!TextUtils.isEmpty(modulePath)) {
            return modulePath + File.separator + "lib" + File.separator + "arm" + File.separator + "lib" + libName + ".so";
        }
        return libName;
    }

    public static String moduleApkPath = "";

    /**
     * 适配API level 20-29
     * 使用反射构造ContextImpl，调用getPackageCodePath方法获取本包路径
     * 例如/data/app/com.suifeng.xposedwork-1/base.apk
     * 或者/data/app/com.suifeng.xposedwork-8hwtoqh8oantk8aq==/base.apk
     *
     * @return apk包路径
     */
    public static String getModuleApkPath() {
        if (!TextUtils.isEmpty(moduleApkPath)) {
            return moduleApkPath;
        }
        String sourceDir = "";
        try {
            Class<?> contextImplClz = Class.forName("android.app.ContextImpl");
            Object ctxtImpl = ContextImplUtil.getContextImpl();
            Method createPackageContextMethod = contextImplClz.getDeclaredMethod("createPackageContext", String.class, int.class);
            createPackageContextMethod.setAccessible(true);
            Context context = (Context) createPackageContextMethod.invoke(ctxtImpl, OuterHookEntry.MODULE_PACKAGE_NAME, Context.CONTEXT_INCLUDE_CODE | Context.CONTEXT_IGNORE_SECURITY);
            sourceDir = context.getPackageCodePath();
        } catch (ClassNotFoundException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            Utils.printThrowable(e);
        }
        moduleApkPath = sourceDir;
        return sourceDir;
    }

    /**
     * 获取当前模块的实际包名路径
     * 例如/data/app/com.suifeng.xposedwork-1
     *
     * @return 模块路径
     */
    public static String getModulePath() {
        String moduleApkPath = getModuleApkPath();
        return moduleApkPath.substring(0, moduleApkPath.lastIndexOf("/"));
    }

    /**
     * 从assets下读取文件内容
     *
     * @param basePath base.apk路径
     * @param filePath 文件在assets下的路径
     * @return 读到的文件InputStream
     */
    public static InputStream getInputStreamFromAssets(String basePath, String filePath) {
        if (!TextUtils.isEmpty(basePath)) {
            try {
                ZipFile apkFile = new ZipFile(basePath);
                ZipEntry fileEntry = apkFile.getEntry("assets/" + filePath);
                return apkFile.getInputStream(fileEntry);
            } catch (IOException e) {
                Utils.printThrowable(e);
            }
        }
        return null;
    }

    /**
     * 从assets下读取文件内容返回byte[]
     *
     * @param basePath base.apk路径
     * @param filePath 文件在assets下的路径
     * @return 读到的byte[]
     */
    public static byte[] getBytesFromAssets(String basePath, String filePath) {
        InputStream inputStream = getInputStreamFromAssets(basePath, filePath);
        byte[] buf = null;
        try {
            buf = new byte[inputStream.available()];
            inputStream.read(buf);
        } catch (IOException e) {
            Utils.printThrowable(e);
        }
        return buf;
    }

    /**
     * 从模块的assets下获取指定路径下文件内容
     *
     * @param filePath 要读取的assets下的文件路径，路径中不包括assets
     * @return string
     */
    private static String getStringFromAssets(String filePath) {
        String str = "";
        String packagePath = getModuleApkPath();
        InputStream inputStream = getInputStreamFromAssets(packagePath, filePath);
        if (inputStream != null) {
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuilder sb = new StringBuilder();
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                str = sb.toString();
            } catch (IOException e) {
                Utils.printThrowable(e);
            }
        }
        return str;
    }

    /**
     * 打印object中所有field的值
     *
     * @param object object
     * @return object所有属性值组合后的String
     */
    public static String getObjectFields(Object object) {
        Class<?> clz = object.getClass();
        StringBuilder sb = new StringBuilder();
        Field[] declaredFields = clz.getDeclaredFields();
        Field[] fields = clz.getFields();
        Object[] allFields = arrayJoin(declaredFields, fields);
        for (Object o : allFields) {
            Field field = (Field) o;
            field.setAccessible(true);
            sb.append(field.getName());
            sb.append(" = ");
            try {
                Object obj = field.get(object);
                if (obj == null) {
                    sb.append("[this object is null]");
                } else {
                    sb.append(getObjectString(obj));
                }
            } catch (IllegalAccessException e) {
                Utils.printThrowable(e);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public static Object[] arrayJoin(Object[] array1, Object[] array2) {
        Object[] join = new Object[array1.length + array2.length];
        System.arraycopy(array1, 0, join, 0, array1.length);
        System.arraycopy(array2, 0, join, array1.length, array2.length);
        return join;
    }

    /**
     * 拼接Object的类型和toString
     *
     * @param object
     * @return
     */
    public static String getObjectInfo(Object object) {
        if (object == null) {
            return "[this object is null]";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(getObjectHashCode(object));
            sb.append(" --> ");
            sb.append(getObjectString(object));
            return sb.toString();
        }
    }

    /**
     * 打印object的toString()信息，如果是一维数组则拼接数组。
     *
     * @param obj
     * @return
     */
    public static String getObjectString(Object obj) {
        StringBuilder sb = new StringBuilder();
        if (obj instanceof byte[]) {
            sb.append(concatArrays((byte[]) obj));
        } else if (obj instanceof char[]) {
            sb.append(concatArrays((char[]) obj));
        } else if (obj instanceof double[]) {
            sb.append(concatArrays((double[]) obj));
        } else if (obj instanceof float[]) {
            sb.append(concatArrays((float[]) obj));
        } else if (obj instanceof int[]) {
            sb.append(concatArrays((int[]) obj));
        } else if (obj instanceof long[]) {
            sb.append(concatArrays((long[]) obj));
        } else if (obj instanceof short[]) {
            sb.append(concatArrays((short[]) obj));
        } else if (obj instanceof boolean[]) {
            sb.append(concatArrays((boolean[]) obj));
        } else if (obj instanceof Object[]) {
            sb.append(concatArrays((Object[]) obj));
        } else {
            sb.append(obj.toString());
        }
        return sb.toString();
    }

    /**
     * 拼接object的类名和hashcode
     *
     * @param obj
     * @return
     */
    public static String getObjectHashCode(Object obj) {
        return (obj.getClass().getName() + "@" + Integer.toHexString(obj.hashCode()));
    }

    /**
     * 用Logger打印错误信息方便在Android studio的logcat中查看
     *
     * @param throwable
     */
    public static void printThrowable(Throwable throwable) {
        StringBuilder sb = formatThrowable(throwable);
        Logger.loge(sb.toString());
    }

    /**
     * 格式化Throwable的输出格式
     *
     * @param throwable
     * @return StringBuilder
     */
    private static StringBuilder formatThrowable(Throwable throwable) {
        StringBuilder sb = new StringBuilder();
        sb.append(throwable.toString());
        sb.append("\n");
        sb.append(concatStackTrace(throwable.getStackTrace()));
        Throwable[] suppressed = throwable.getSuppressed();
        for (Throwable tw : suppressed) {
            sb.append(formatThrowable(tw));
        }
        Throwable cause = throwable.getCause();
        if (cause != null) {
            sb.append(formatThrowable(cause));
        }
        return sb;
    }
}
