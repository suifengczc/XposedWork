package com.suifeng.xposedwork.hookmodule;


/**
 * hook type
 * @author suifengczc
 */
public enum HookType {
    /**
     * hook target method in the target class
     */
    HOOK_NORMAL_METHOD,
    /**
     * hook target constructor in the target class
     */
    HOOK_NORMAL_INIT,

    /**
     * hook all the methods of the same name in the target class
     */
    HOOK_ALL_METHOD,

    /**
     * hook all the constructor in the target class
     */
    HOOK_ALL_INIT,

    /**
     * hook static field in the target class and get it's value
     */
    HOOK_GET_STATIC_FIELD,

    /**
     * hook target method in the target class and replace code
     */
    HOOK_REPLACE_METHOD,

    /**
     * hook all methods of the same name in the target class and replace code
     */
    HOOK_REPLACE_ALL_METHOD;


}
