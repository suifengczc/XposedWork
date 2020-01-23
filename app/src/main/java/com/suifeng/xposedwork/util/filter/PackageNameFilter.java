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

    private List<String> allowedPackageName;
    private Pattern allowedPattern;

    /**
     * @param allowedPackageName 允许的包名列表
     */
    public PackageNameFilter(List<String> allowedPackageName) {
        this.allowedPackageName = allowedPackageName;
    }

    /**
     * @param allowedPackageName 允许的包名
     */
    public PackageNameFilter(String allowedPackageName) {
        this.allowedPackageName = new ArrayList<>();
        this.allowedPackageName.add(allowedPackageName);
    }

    /**
     * @param allowedPattern 允许的包名正则
     */
    public PackageNameFilter(Pattern allowedPattern) {
        this.allowedPattern = allowedPattern;
    }

    @Override
    public boolean filter(String checkPackageName) {
        if (allowedPackageName == null && allowedPattern == null) {
            //无筛选条件，默认通过
            return true;
        } else if (allowedPackageName != null && !allowedPackageName.isEmpty()) {
            return allowedPackageName.contains(checkPackageName);
        } else if (allowedPattern != null) {
            return allowedPattern.matcher(checkPackageName).find();
        }
        return false;
    }
}
