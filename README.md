# 项目介绍
本项目是为方便逆向分析，基于Xposed框架实现的hook模块

- 为了方便代码的实时生效实现了免重启，[参考项目](https://github.com/shuihuadx/XposedHook)
- 为了避免在一个类中重复写太多hook代码，又频繁修改，导致Hook入口类冗余杂乱，所以使用了BaseHookModule类来保存被hook类的具体hook逻辑

    - AbstractClassLoaderModule继承自BaseHookModule用来 hook ClassLoader相关api，用来实现hook动态加载的插件中的类。提供了getPluginHookList(ClassLoader)方法，借助这个方法可以hook由ClassLoader动态加载的dex包中的类。
    
- 为方便管理需要Hook的应用，在assets下添加了hook_package.json文件，其中用json格式添加需要hook的app包名，可以在不重启设备的情况下动态修改需要hook的app

- 添加了包名过滤机制，在创建BaseHookModule时传入PackageNameFilter，只有当hook到指定的包时才生效。支持多包名和正则。

# 使用介绍

1. 在assets下的hook_package.json下添加需要hook的包名，格式如下：
``` json
{
  "hook_package": ["com.test.apk1","com.test.apk2"]
}
```

2. 编写继承自HookModule的具体Hook逻辑类如下：
也可以参考
[Hook_PathClassLoader.java](app/src/main/java/com/suifeng/xposedwork/hookclasses/hookclassloader/Hook_PathClassLoader.java)
[Hook_Activity.java](app/src/main/java/com/suifeng/xposedwork/hookclasses/hooklauncher/Hook_Activity.java)
[Hook_clzInPlugin.java](app/src/main/java/com/suifeng/xposedwork/hookclasses/hookplugin/Hook_clzInPlugin.java)
[Hook_Base64.java](app/src/main/java/com/suifeng/xposedwork/hookclasses/hooksystem/Hook_Base64.java)
[Hook_String.java](app/src/main/java/com/suifeng/xposedwork/hookclasses/hooksystem/Hook_String.java)

``` java
public class HookTest extends BaseHookModule {
    
    public HookTest(ClassLoader classLoader) {
        super(classLoader);
    }
    
    public HookTest(ClassLoader classLoader, PackageNameFilter filter) {
        super(classLoader, filter);
    }

    @Override
    protected void init() {
        className = "com.test.clz1";
        hookDatas.add(HookHelper.hookMethod("testMethod",
                int.class, String.class, long.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        //dosomething
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        //dosomething
                        super.afterHookedMethod(param);
                    }
                }));
    }
}

```

3. 如果被Hook的类是动态加载进来的类需要先找到加载这个类的classLoader的具体类型。例如是PathClassLoader的话，需要hook PathClassLoader的构造方法，并在afterHookedMethod()中调用hookPluginClasses()方法。
代码如下：
``` java
public class Hook_PathClassLoader extends AbstractClassLoaderModule {
    //......
    @Override
    protected void init() {
        className = PathClassLoader.class.getName();
        hookDatas.add(HookHelper.hookInit(
                String.class, ClassLoader.class,
                new XC_MethodHook() {
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        Logger.logi("hook PathClassLoader2 :before path = " + param.args[0].toString());
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        String path = param.args[0].toString();
                        Logger.logi("hook PathClassLoader2 :after " + " path = " + path + "\n" + param.thisObject.toString());
                        hookPluginClasses(param);
                        super.afterHookedMethod(param);
                    }
                }));        
    }
    //......
}
```

4. 在HookStack的setHookClasses方法中添加具体Hook类，需要注意的是如果是Hook的当前的app下的类就添加到hookClassList。如果是Hook app动态加载的包中的类，就添加到hookPluginClassList。

``` java
public class InnerHookEntry implements IXposedHookLoadPackage {
    //......
    private void setHookClasses(ClassLoader classLoader) {
        //hook当前包中的类
        hookClassList.addHookModule(new Hook_PathClassLoader(classLoader));

        //hook 动态加载的插件中的类
        hookPluginClassList.add(Hook_clzInPlugin.class);
    }
    //......
}
```

5. 在修改了xposed模块代码后需要重启被hook的apk才能使新代码生效，但不需要重启设备

# 主要功能类

## OuterHookEntry
Hook功能的外部入口，由Xposed调用。通过动态加载的方式实现了免重启，基本不对这个类的代码做修改，因为当这个类的代码变化无法通过动态加载的方式实现即时生效。
如果确实需要对这个类做修改的，修改完成后需要重启设备以使新代码生效。

## InnerHookEntry
Hook逻辑的唯一入口，在InnerHookEntry的`setHookClasses()`方法中设置了需要Hook的类，基本上在使用过程中只需要修改`setHookClasses()`中的代码动态修改需要Hook的类

## BaseHookModule
Hook逻辑的抽象类，里面包含了`className`(被Hook的类全限定类名)，`hookDatas`(Hook数据类)，`classLoader`(构造函数传入的加载Hook类的ClassLoader)。
一个HookModule可以包含多个HookData，表示对当前类的多个方法或参数Hook。
可以传入PackageNameFilter指定当前HookModule在哪个包下生效

## HookData
一个HookData代表一个具体Hook逻辑的数据类，包含了例如被hook的方法名或参数名，HookType。

## AbstractClassLoaderModule
继承自BaseHookModule，当Hook ClassLoader相关的类时使用。AbstractClassLoaderModule继承自BaseHookModule用来 hook ClassLoader相关api，用来实现hook动态加载的插件中的类。提供了getPluginHookList(ClassLoader)方法，借助这个方法可以hook由ClassLoader动态加载的dex包中的类。

## Reflector
反射工具类，借用自滴滴的[VirtualApk项目](https://github.com/didi/VirtualAPK/blob/master/CoreLibrary/src/main/java/com/didi/virtualapk/utils/Reflector.java)

## HookHelper
Hook帮助类
- 包含hookInit(),hookMethod()等方法，对应具体hook操作。
- dealHook()方法处理BaseHookModule中的所有HookData

# 缺点
- 在HookData中需要手写`className = "com.suifeng.test";`不方便
- 在HookData中需要用`hookDatas.add(HookHelper.hookMethod(xxxxxx))`方式添加需要hook的逻辑，应该有更好的实现方式

未完待续，keep coding...