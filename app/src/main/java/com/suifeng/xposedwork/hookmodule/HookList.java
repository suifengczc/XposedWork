package com.suifeng.xposedwork.hookmodule;

import java.util.HashMap;
import java.util.Map;

/**
 * 要hook的类列表
 * @author suifengczc
 */
public class HookList {
    public HookList() {
        this.hookModuleMap = new HashMap<>();
    }

    /**
     * HookModule的Map，以hook的类名为key
     */
    private Map<String, BaseHookModule> hookModuleMap;

    public void addHookModule(BaseHookModule module) {
        if (module == null) {
            return;
        }
        String className = module.className;
        hookModuleMap.put(className, module);
    }

    public Map<String, BaseHookModule> getHookModules() {
        return hookModuleMap;
    }


    /**
     * 判断hook列表是否为空
     *
     * @return true，空
     */
    public boolean isEmpty() {
        return hookModuleMap == null || hookModuleMap.size() == 0;
    }
}
