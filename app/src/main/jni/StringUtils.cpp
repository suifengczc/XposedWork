//
// Created by suifengczc on 2020/2/22.
//

#include <jni.h>
#include <string.h>
#include <android/log.h>
#include <malloc.h>

#define TAG "HookDemo"
#define LOGI(...) __android_log_print(ANDROID_LOG_INFO,TAG ,__VA_ARGS__) // 定义LOGI类型

extern "C"
JNIEXPORT jstring JNICALL
concatString(JNIEnv *env, jclass clz, jobjectArray strArray) {
    jsize strArrLength = env->GetArrayLength(strArray);
    size_t size = 0;
    const char *s;
    for (int i = 0; i < strArrLength; i++) {
        s = env->GetStringUTFChars((jstring) env->GetObjectArrayElement(strArray, i), JNI_FALSE);
        size = size + strlen(s);
    }
    size++;
    char *result = (char *) malloc(size);

    for (int i = 0; i < strArrLength; i++) {
        jstring s = (jstring) env->GetObjectArrayElement(strArray, i);
        const char *c = env->GetStringUTFChars(s, JNI_FALSE);
        if (i == 0) {
            strcpy(result, c);
        } else {
            strcat(result, c);
        }
        //一定要及时释放内存
        env->DeleteLocalRef(s);
        env->DeleteLocalRef((jobject) c);
    }

    jstring js = env->NewStringUTF(result);
    env->ReleaseStringUTFChars(js, result);
    return js;
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_test_a_NativeTest_getString(JNIEnv *env, jclass clazz) {
    return env->NewStringUTF("create by jni -> getString");
}


static const char *className = "com/suifeng/xposedwork/util/NativeUtils";

static JNINativeMethod registerMethods[] = {
        {"concatString", "([Ljava/lang/String;)Ljava/lang/String;", (jstring *) concatString},
};

static int jniRegisterNativeMethods(JNIEnv *env, const char *className,
                                    const JNINativeMethod *gMethods, int numMethods) {
    jclass clazz;

    LOGI("JNI", "Registering %s natives\n", className);
    clazz = (env)->FindClass(className);
    if (clazz == NULL) {
        LOGI("Native registration unable to find class '%s'\n", className);
        return -1;
    }

    int result = 0;
    if ((env)->RegisterNatives(clazz, registerMethods, numMethods) < 0) {
        LOGI("RegisterNatives failed for '%s'\n", className);
        result = -1;
    }

    (env)->DeleteLocalRef(clazz);
    return result;
}

jint JNI_OnLoad(JavaVM *vm, void *reserved) {
    LOGI("enter jni_onload");

    JNIEnv *env = NULL;
    jint result = -1;

    if (vm->GetEnv((void **) &env, JNI_VERSION_1_4) != JNI_OK) {
        return result;
    }

    jniRegisterNativeMethods(env, className, registerMethods,
                             sizeof(registerMethods) / sizeof(JNINativeMethod));

    return JNI_VERSION_1_4;
}