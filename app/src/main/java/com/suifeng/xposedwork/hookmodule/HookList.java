package com.suifeng.xposedwork.hookmodule;

import java.util.HashMap;
import java.util.Map;

/**
 * 要hook的类列表
 */
public class HookList {
    public HookList() {
        this.hookModuleMap = new HashMap<>();
    }

    /**
     * HookModule的Map，以hook的类名为key
     */
    private Map<String, HookModule> hookModuleMap;

    public void addHookModule(HookModule module) {
        if (module == null) {
            return;
        }
        String className = module.className;
        hookModuleMap.put(className, module);
    }

    public Map<String, HookModule> getHookModules() {
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
