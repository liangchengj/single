/**
 * @author Liangcheng Juves
 * Created at 2020/6/9 20:51
 */
#include <jni.h>
#include <stdlib.h>
#include <string.h>
#include <android/log.h>

jclass findclz(JNIEnv *env, char const *clz_name);

char const *jstr_cstr(JNIEnv *env, jstring jstr);

jstring jobj_jstr(JNIEnv *env, jstring jstr);

const jint TOAST_LEN_LONG = 1;
const jint TOAST_LEN_SHORT = 0;

void toast_show_msg(JNIEnv *env, jobject ctx, jobject msg, jint duration);

void toast_show_res(JNIEnv *env, jobject ctx, jint resid, jint duration);

#define LOG_FMT "%s"
#define VLOG(__TAG__, ...) __android_log_print(ANDROID_LOG_VERBOSE,__TAG__,LOG_FMT,__VA_ARGS__)
#define DLOG(__TAG__, ...) __android_log_print(ANDROID_LOG_DEBUG,__TAG__,LOG_FMT,__VA_ARGS__)
#define ILOG(__TAG__, ...) __android_log_print(ANDROID_LOG_INFO,__TAG__,LOG_FMT,__VA_ARGS__)
#define WLOG(__TAG__, ...) __android_log_print(ANDROID_LOG_WARN,__TAG__,LOG_FMT,__VA_ARGS__)
#define ELOG(__TAG__, ...) __android_log_print(ANDROID_LOG_ERROR,__TAG__,LOG_FMT,__VA_ARGS__)


inline jclass findclz(JNIEnv *env, char const *clz_name) {
    return (*env)->FindClass(env, clz_name);
}


// For C++.
// inline char const *jstr_cstr(JNIEnv *env, jstring jstr) {
//     return (*env)->GetStringChars(env, jstr, JNI_FALSE);
// }

inline char const *jstr_cstr(JNIEnv *env, jstring jstr) {
    char *cs = NULL;
    /* (*env)->FindClass(env, "java/lang/String") */
    jclass jclz_str = findclz(env, "java/lang/String");
    jstring jstr_enc = (*env)->NewStringUTF(env, "utf-8");
    jmethodID jmid_gbts = (*env)->GetMethodID(env, jclz_str, "getBytes", "(Ljava/lang/String;)[B");
    jbyteArray jbts_arr = (jbyteArray) (*env)->CallObjectMethod(env, jstr, jmid_gbts, jstr_enc);
    jsize arrlen = (*env)->GetArrayLength(env, jbts_arr);
    jbyte *jbt = (*env)->GetByteArrayElements(env, jbts_arr, JNI_FALSE);
    if (arrlen > 0) {
        cs = (char *) malloc(arrlen + 1);
        memcpy(cs, jbt, arrlen);
        cs[arrlen] = 0;
    }
    (*env)->ReleaseByteArrayElements(env, jbts_arr, jbt, 0);
    return cs;
}

inline jstring jobj_jstr(JNIEnv *env, jstring jstr) {
    jclass jclz = (*env)->GetObjectClass(env, jstr);
    jmethodID jmid_tostr = (*env)->GetMethodID(env, jclz, "toString", "()Ljava/lang/String;");
    return (jstring) (*env)->CallObjectMethod(env, jstr, jmid_tostr);
}


void toast_show_msg(JNIEnv *env, jobject ctx, jobject msg, jint duration) {
    /* (*env)->FindClass(env, "android/widget/Toast") */
    jclass jclz_toast = findclz(env, "android/widget/Toast");
    jmethodID jmid_mktxt = (*env)->GetStaticMethodID(env, jclz_toast, "makeText",
                                                     "(Landroid/content/Context;Ljava/lang/CharSequence;I)Landroid/widget/Toast;");
    jobject jobj_toast = (*env)->CallStaticObjectMethod(env, jclz_toast, jmid_mktxt,
                                                        ctx, jobj_jstr(env, msg), duration);
    jmethodID jmid_show = (*env)->GetMethodID(env, jclz_toast, "show", "()V");
    (*env)->CallVoidMethod(env, jobj_toast, jmid_show);
}


void toast_show_res(JNIEnv *env, jobject ctx, jint resid, jint duration) {
    /* (*env)->FindClass(env, "android/widget/Toast") */
    jclass jclz_toast = findclz(env, "android/widget/Toast");
    jmethodID jmid_mktxt = (*env)->GetStaticMethodID(env, jclz_toast, "makeText",
                                                     "(Landroid/content/Context;II)Landroid/widget/Toast;");
    jobject jobj_toast = (*env)->CallStaticObjectMethod(env, jclz_toast, jmid_mktxt,
                                                        ctx, resid, duration);
    jmethodID jmid_show = (*env)->GetMethodID(env, jclz_toast, "show", "()V");
    (*env)->CallVoidMethod(env, jobj_toast, jmid_show);
}

JNIEXPORT void JNICALL
Java_com_meyoustu_amuse_App_verboseLog(JNIEnv *env, jclass clazz, jboolean if_debug, jstring tag,
                                       jobject msg) {
    if (if_debug == JNI_TRUE) {
        VLOG(jstr_cstr(env, tag), jstr_cstr(env, jobj_jstr(env, msg)));
    }
}

JNIEXPORT void JNICALL
Java_com_meyoustu_amuse_App_debugLog(JNIEnv *env, jclass clazz, jboolean if_debug, jstring tag,
                                     jobject msg) {
    if (if_debug == JNI_TRUE) {
        DLOG(jstr_cstr(env, tag), jstr_cstr(env, jobj_jstr(env, msg)));
    }
}

JNIEXPORT void JNICALL
Java_com_meyoustu_amuse_App_infoLog(JNIEnv *env, jclass clazz, jboolean if_debug, jstring tag,
                                    jobject msg) {
    if (if_debug == JNI_TRUE) {
        ILOG(jstr_cstr(env, tag), jstr_cstr(env, jobj_jstr(env, msg)));
    }
}

JNIEXPORT void JNICALL
Java_com_meyoustu_amuse_App_warnLog(JNIEnv *env, jclass clazz, jboolean if_debug, jstring tag,
                                    jobject msg) {
    if (if_debug == JNI_TRUE) {
        WLOG(jstr_cstr(env, tag), jstr_cstr(env, jobj_jstr(env, msg)));
    }
}

JNIEXPORT void JNICALL
Java_com_meyoustu_amuse_App_errorLog(JNIEnv *env, jclass clazz, jboolean if_debug, jstring tag,
                                     jobject msg) {
    if (if_debug == JNI_TRUE) {
        ELOG(jstr_cstr(env, tag), jstr_cstr(env, jobj_jstr(env, msg)));
    }
}

JNIEXPORT void JNICALL
Java_com_meyoustu_amuse_util_Toast_toastMsg(JNIEnv *env, jclass clazz,
                                            jobject ctx, jobject msg, jint duration) {
    toast_show_msg(env, ctx, msg, duration);
}

JNIEXPORT void JNICALL
Java_com_meyoustu_amuse_util_Toast_toastRes(JNIEnv *env, jclass clazz,
                                            jobject ctx, jint id, jint duration) {
    toast_show_res(env, ctx, id, duration);
}

