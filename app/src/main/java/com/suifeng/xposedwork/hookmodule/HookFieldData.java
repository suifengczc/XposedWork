package com.suifeng.xposedwork.hookmodule;

/**
 * Hook static field
 *
 * @author suifengczc
 */
public class HookFieldData extends HookData {

    FieldCallback callback;
    Object valueForSet;

    /**
     * @param hookTarget hook的static field名称
     * @param hookType   Hook类型
     * @param callback   get field时传入回调接口，可传空
     */
    public HookFieldData(String hookTarget, HookType hookType, FieldCallback callback) {
        super(hookTarget, hookType);
        this.callback = callback;
    }

    /**
     * @param hookTarget  hook的static field名称
     * @param hookType    Hook类型
     * @param valueForSet 设置给目标变量的值
     * @param callback    设置值结束后回调，可为空
     */
    public HookFieldData(String hookTarget, HookType hookType, Object valueForSet, FieldCallback callback) {
        super(hookTarget, hookType);
        this.valueForSet = valueForSet;
        this.callback = callback;
    }

    /**
     * 获取到static field后回调接口
     */
    public interface FieldCallback {
        /**
         * 对变量get或set完成后回调
         *
         * @param value 如果是get操作则返回get到的值，如果是set则传空
         */
        void done(Object value);
    }
}
