package com.suifeng.xposedwork.hookmodule;

/**
 * Hook static field
 *
 * @author suifengczc
 */
public class HookFieldData extends HookData {

    FieldGetCallback callback;

    /**
     * @param hookTarget hook的static field名称
     * @param hookType   Hook类型
     * @param callback   get field时传入回调接口，可传空
     */
    public HookFieldData(String hookTarget, HookType hookType, FieldGetCallback callback) {
        super(hookTarget, hookType);
        this.callback = callback;
    }

    /**
     * 获取到static field后回调接口
     */
    public interface FieldGetCallback {
        /**
         * 获取到static field值后回调方法
         * @param value 获取到的static值
         */
        void getFieldValue(Object value);
    }
}
