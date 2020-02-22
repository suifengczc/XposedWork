package com.suifeng.xposedwork.util.filter;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 判断包名是否符合筛选条件，支持报包名列表和包名正则两种形式筛选
 *
 * @author suifengczc
 * @date 2020/1/22
 */
public class PackageNameFilter implements Filter<String> {

    /**
     * 允许的包名列表，可以存包名或符合的包名正则
     */
    private List<Object> allowedPackageName = new ArrayList<>();

    /**
     * @param allowedPackageName 允许的包名列表
     */
    public PackageNameFilter(List<Object> allowedPackageName) {
        this.allowedPackageName = allowedPackageName;
    }

    /**
     * @param allowedPackageName 允许的包名
     */
    public PackageNameFilter(String allowedPackageName) {
        this.allowedPackageName.add(allowedPackageName);
    }

    /**
     * @param allowedPattern 允许的包名正则
     */
    public PackageNameFilter(Pattern allowedPattern) {
        this.allowedPackageName.add(allowedPattern);
    }

    /**
     * 添加筛选条件
     *
     * @param condition 添加的条件
     * @return
     */
    public PackageNameFilter add(Object condition) {
        if (condition instanceof String || condition instanceof Pattern) {
            this.allowedPackageName.add(condition);
            return this;
        }
        throw new RuntimeException("condition not allowed");
    }

    @Override
    public boolean filter(String checkPackageName) {
        if (allowedPackageName != null && !allowedPackageName.isEmpty()) {
            for (Object obj : allowedPackageName) {
                if (obj instanceof String) {
                    if (((String) obj).equals(checkPackageName)) {
                        return true;
                    }
                } else if (obj instanceof Pattern) {
                    if (((Pattern) obj).matcher(checkPackageName).find()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
