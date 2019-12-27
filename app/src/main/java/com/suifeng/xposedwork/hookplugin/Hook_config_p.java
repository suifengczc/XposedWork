package com.suifeng.xposedwork.hookplugin;

import com.suifeng.xposedwork.hookmodule.HookBasicData;
import com.suifeng.xposedwork.hookmodule.HookData;
import com.suifeng.xposedwork.hookmodule.PluginClassModule;

public class Hook_config_p extends PluginClassModule {
    /**
     * @param classLoader 加载插件的classloader
     */
    public Hook_config_p(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected void init() {
        className = "com.google.android.gms.ads.internal.config.p";
        hookDatas.add(new HookData("bw", null, null, HookBasicData.HOOK_GET_STATIC_FIELD));
    }
}
