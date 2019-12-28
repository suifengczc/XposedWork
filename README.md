# 项目介绍
本项目时为方便逆向分析，基于Xposed框架实现的hook模块

- 为了方便代码的实时生效实现了免重启，[参考项目](https://github.com/shuihuadx/XposedHook)
- 为了避免在一个类中重复写太多hook代码，又频繁只是修改，导致Hook入口类冗余杂乱，所以使用了HookModule类来定义每一个需要Hook的类和具体Hook操作

    - ClassLoaderModule继承自HookModule用来 hook ClassLoader相关api，用来实现hook动态加载的插件中的类

    - PluginClassModule继承自HookModule， hook 插件中的类
    
- 为方便管理需要Hook的应用，在assets下添加了hook_package.json文件，其中用json格式添加需要hook的app，可以在不重启设备的情况下动态修改需要hook的app

# 使用介绍
1. 在assets下的hook_package.json下添加需要hook的包名，格式如下：
``` json
{
  "hook_package": ["com.google.android.googlequicksearchbox"]
}
```

2. 编写继承自HookModule的具体Hook逻辑类

3. 在HookStack的setHookClasses方法中添加具体Hook类，需要注意的是如果是Hook的当前的app下的类就添加到hookClassList。如果是Hook app动态加载的包中的类，就添加到hookPluginClassList。
``` java
    private void setHookClasses(ClassLoader classLoader) {
        //hook当前包中的类
        hookClassList.addHookModule(new Hook_PathClassLoader(classLoader));

        //hook 动态加载的插件中的类
        hookPluginClassList.add(Hook_ct.class);
    }
```

4. 在修改了xposed模块代码后需要重启被hook的apk才能使新代码生效，但不需要重启设备

未完待续，keep coding...