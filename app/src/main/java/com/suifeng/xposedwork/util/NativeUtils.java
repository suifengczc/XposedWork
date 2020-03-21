package com.suifeng.xposedwork.util;

/**
 * 需要native配合的工具类
 *
 * @author suifengczc
 * @date 2020/2/22
 */
public class NativeUtils {

    private NativeUtils() {
    }

    public static void loadLibrary() {
        try {
            System.load(Utils.getLibPath("nativesupport"));
        } catch (Exception e) {
            Utils.printThrowable(e);
        }
    }

    /**
     * 由jni完成String拼接，避免java层hook了String的拼接又有java代码调用了String的拼接导致crash
     *
     * @param str
     * @return
     */
    public static native String concatString(String... str);

}
