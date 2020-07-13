/**
 * Created at 2020/7/14 07:14.
 * 
 * In order to use JNI perfectly.
 * 
 * @author Liangcheng Juves
 */
#ifdef __cplusplus
extern "C"
{
#endif

#ifndef tcha_h
#define tcha_h
#endif

//#include <jni.h>
#include "../../../../../../AppData/Local/Android/Sdk/ndk/21.0.6113669/toolchains/llvm/prebuilt/windows-x86_64/sysroot/usr/include/jni.h"

JNIEnv *__JNIEnv__;

void __jnienv(JNIEnv *env, jobject thiz);
jclass jnigoc(jobject jobj);

void __jnienv(JNIEnv *env, jobject thiz) {
    __JNIEnv__ = env;
}

#ifdef __cplusplus
}
#endif