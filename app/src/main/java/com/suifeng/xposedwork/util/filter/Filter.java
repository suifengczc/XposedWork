package com.suifeng.xposedwork.util.filter;

/**
 * 条件过滤器
 * @author suifengczc
 * @date 2020/1/22
 */
public interface Filter<T> {

    /**
     * 判断check是否符合筛选条件
     * @param check 被判断条件
     * @return true 符合筛选条件
     */
    public boolean filter(T check);
}
