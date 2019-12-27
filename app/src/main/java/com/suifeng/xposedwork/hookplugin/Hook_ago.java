package com.suifeng.xposedwork.hookplugin;

import android.util.Log;

import com.suifeng.xposedwork.hookmodule.HookBasicData;
import com.suifeng.xposedwork.hookmodule.HookData;
import com.suifeng.xposedwork.hookmodule.PluginClassModule;
import com.suifeng.xposedwork.util.Utils;

import de.robv.android.xposed.XC_MethodHook;

public class Hook_ago extends PluginClassModule {

    public Hook_ago(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected void init() {
        try {
            className = "ago";
            hookDatas.add(new HookData("",
                    null, new XC_MethodHook() {
                @Override
                protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                    StringBuilder sb = new StringBuilder();
                    Object[] args = param.args;
                    if (args != null && args.length > 0) {
                        for (int i = 0; i < args.length; i++) {
                            Object arg = args[i];
                            if (arg != null) {
                                sb.append(arg.getClass().toString());
                                sb.append(" --> ");
                                if (i != 0) {
                                    sb.append(arg.toString());
                                }
                                sb.append("\n");
                                if (i == 2) {
                                    sb.append(Utils.concatArrays(((Object[]) arg)));
                                }
                            } else {
                                sb.append("this arg is null \n");
                            }
                        }
                    }
                    String s = init_in_ago(((String) param.args[1]), ((Object[]) param.args[2]));
                    Log.i(TAG, "hook ago init: before  " + sb.toString() + "\n result = " + s);
                    super.beforeHookedMethod(param);
                }

                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Log.i(TAG, "hook ago init: after ");
                    super.afterHookedMethod(param);
                }
            }, HookBasicData.HOOK_ALL_INIT));
        } catch (Exception e) {
            Log.e(TAG, "Hook_ago: when hookAllMethods cant found class agc");
        }
    }

    private String b;
    private Object[] c;
    private int d;

    public String init_in_ago(String str, Object[] objArr) {
        StringBuilder sb = new StringBuilder();
        this.b = str;
        this.c = objArr;
        sb.append("str = ").append(str).append("\n");
        sb.append("objArr = ").append(Utils.concatArrays(objArr)).append("\n");
        char charAt = str.charAt(0);
        if (charAt >= 55296) {
            char c2 = (char) (charAt & 8191);
            int i = 13;
            int i2 = 1;
            while (true) {
                int i3 = i2 + 1;
                char charAt2 = str.charAt(i2);
                if (charAt2 >= 55296) {
                    c2 |= (charAt2 & 8191) << i;
                    i += 13;
                    i2 = i3;
                } else {
                    this.d = c2 | (charAt2 << i);
                    break;
                }
            }
        } else {
            this.d = charAt;
        }
        sb.append("d = ").append(d);
        return sb.toString();
    }

}
