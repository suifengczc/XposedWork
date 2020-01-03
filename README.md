# 项目介绍
本项目是为方便逆向分析，基于Xposed框架实现的hook模块

- 为了方便代码的实时生效实现了免重启，[参考项目](https://github.com/shuihuadx/XposedHook)
- 为了避免在一个类中重复写太多hook代码，又频繁只是修改，导致Hook入口类冗余杂乱，所以使用了HookModule类来定义每一个需要Hook的类和具体Hook操作

    - ClassLoaderModule继承自HookModule用来 hook ClassLoader相关api，用来实现hook动态加载的插件中的类

    - PluginClassModule继承自HookModule， hook 插件中的类
    
- 为方便管理需要Hook的应用，在assets下添加了hook_package.json文件，其中用json格式添加需要hook的app，可以在不重启设备的情况下动态修改需要hook的app

# 主要功能类

## XposedMain
Hook功能的外部入口，由Xposed调用。通过动态加载的方式实现了免重启，基本不对这个类的代码做修改，因为当这个类的代码变化无法通过动态加载的方式实现即时生效。
如果确实需要对这个类做修改的，修改完成后需要重启设备以使新代码生效。

## HookStack
Hook逻辑的唯一入口，在HookStack的`setHookClasses()`方法中设置了需要Hook的类，基本上在使用过程中只需要修改`setHookClasses()`中的代码动态修改需要Hook的类

## BaseHookModule
Hook逻辑的抽象类，里面包含了`className`(被Hook的类全限定类名)，`hookDatas`(Hook数据类)，`classLoader`(构造函数传入的加载Hook类的ClassLoader)。
一个HookModule可以包含多个HookData，表示对当前类的多个方法或参数Hook。

## HookData
一个HookData代表一个具体Hook逻辑的数据类，包含了例如被hook的方法名或参数名，HookType。

## HookMethodData
hook method时创建的hook相关的参数

## HookFieldData
hook field时创建的hook相关的参数

## AbstractClassLoaderModule
继承自HookModule，当Hook ClassLoader相关的类时使用。

## AbstractPluginClassModule
继承自HookModule，当Hook 动态加载的插件中的类时使用。

## Reflector
反射工具类，借用自滴滴的VirtualApk项目

# 使用介绍
1. 在assets下的hook_package.json下添加需要hook的包名，格式如下：
``` json
{
  "hook_package": ["com.google.android.googlequicksearchbox"]
}
```

2. 编写继承自HookModule的具体Hook逻辑类
``` java
public class HookTest extends BaseHookModule {
    
    public HookTest(ClassLoader classLoader) {
        super(classLoader);
    }

    @Override
    protected void init() {
        className = "com.suifeng.test";
        hookDatas.add(new HookMethodData("testMethod", HookType.HOOK_NORMAL_METHOD,
                int.class, String.class, boolean.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        //do something
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        //do something
                        super.afterHookedMethod(param);
                    }
                }));
    }
}

```

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