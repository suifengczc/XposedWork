package com.suifeng.xposedwork.util;

/**
 * 需要native配合的工具类
 *
 * @author suifengczc
 * @date 2020/2/22
 */
public class NativeUtils {

//    static {
//        System.loadLibrary("nativesupport");
//    }

    public static void loadLibrary() {
        try {
            System.load(Utils.getLibPath("nativesupport"));
        } catch (Exception e) {
            Utils.printThrowable(e);
        }
    }

    public static native String concatString(String... str);

}
