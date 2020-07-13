/**
 * Created at 2020/6/9 20:51.
 *
 * @author Liangcheng Juves
 */

#ifdef __cplusplus
extern "C"
{
#endif

#include <jni.h>
#include <stdlib.h>
#include <string.h>
#include <android/log.h>

    jclass fdjclz(JNIEnv *env, char const *name);

    jclass jclazz(JNIEnv *env);

    char const *jstr_cstr(JNIEnv *env, jstring jstr);

    jstring cstr_jstr(JNIEnv *env, char const *cs);

    jclass jclz_fromjobj(JNIEnv *env, jobject jobj);

    jobject jclz_callobjmtd(JNIEnv *env, jclass jclz, char const *name, char const *sig, ...);

    jobject jobj_callobjmtd(JNIEnv *env, jobject jobj, char const *name, char const *sig, ...);

    jstring jclz_name(JNIEnv *env, jclass jclz);

    jstring jclz_simp_name(JNIEnv *env, jclass jclz);

    jstring jobj_tostr(JNIEnv *env, jstring jstr);

    const jint TOAST_LEN_LONG = 1;
    const jint TOAST_LEN_SHORT = 0;

    void toast_show(JNIEnv *env, jobject ctx, jobject jobj, jint duration);

    // void toast_show_msg(JNIEnv *env, jobject ctx, jobject msg, jint duration);

    // void toast_show_res(JNIEnv *env, jobject ctx, jint resid, jint duration);

#define VLOG(__TAG__, fmt, ...) __android_log_print(ANDROID_LOG_VERBOSE, __TAG__, fmt, __VA_ARGS__)
#define DLOG(__TAG__, fmt, ...) __android_log_print(ANDROID_LOG_DEBUG, __TAG__, fmt, __VA_ARGS__)
#define ILOG(__TAG__, fmt, ...) __android_log_print(ANDROID_LOG_INFO, __TAG__, fmt, __VA_ARGS__)
#define WLOG(__TAG__, fmt, ...) __android_log_print(ANDROID_LOG_WARN, __TAG__, fmt, __VA_ARGS__)
#define ELOG(__TAG__, fmt, ...) __android_log_print(ANDROID_LOG_ERROR, __TAG__, fmt, __VA_ARGS__)

#define DEF_LOG_FMT "%s"

#define VLOG(__TAG__, ...) __android_log_print(ANDROID_LOG_VERBOSE, __TAG__, DEF_LOG_FMT, __VA_ARGS__)
#define DLOG(__TAG__, ...) __android_log_print(ANDROID_LOG_DEBUG, __TAG__, DEF_LOG_FMT, __VA_ARGS__)
#define ILOG(__TAG__, ...) __android_log_print(ANDROID_LOG_INFO, __TAG__, DEF_LOG_FMT, __VA_ARGS__)
#define WLOG(__TAG__, ...) __android_log_print(ANDROID_LOG_WARN, __TAG__, DEF_LOG_FMT, __VA_ARGS__)
#define ELOG(__TAG__, ...) __android_log_print(ANDROID_LOG_ERROR, __TAG__, DEF_LOG_FMT, __VA_ARGS__)

    inline jclass fdjclz(JNIEnv *env, char const *name)
    {
        return (*env)->FindClass(env, name);
    }

    inline jclass jclazz(JNIEnv *env)
    {
        return fdjclz(env, "java/lang/Class");
    }

    inline char const *jstr_cstr(JNIEnv *env, jstring jstr)
    {
        return (*env)->GetStringUTFChars(env, jstr, JNI_FALSE);
        // char *cs = NULL;
        // /* (*env)->FindClass(env, "java/lang/String") */
        // jclass jclz_String = fdjclz(env, "java/lang/String");
        // jstring jstr_Utf8 = (*env)->NewStringUTF(env, "utf-8");
        // jmethodID jmid_getBytes = (*env)->GetMethodID(env, jclz_String, "getBytes", "(Ljava/lang/String;)[B");
        // jbyteArray jba_bytes = (jbyteArray)(*env)->CallObjectMethod(env, jstr, jmid_getBytes, jstr_Utf8);
        // jsize arrlen = (*env)->GetArrayLength(env, jba_bytes);
        // jbyte *jbt = (*env)->GetByteArrayElements(env, jba_bytes, JNI_FALSE);
        // if (arrlen > 0)
        // {
        //     cs = (char *)malloc(arrlen + 1);
        //     memcpy(cs, jbt, arrlen);
        //     cs[arrlen] = 0;
        // }
        // (*env)->ReleaseByteArrayElements(env, jba_bytes, jbt, 0);
        // return cs;
    }

    inline jstring cstr_jstr(JNIEnv *env, char const *cs)
    {
        return (*env)->NewStringUTF(env, cs);
    }

    inline jclass jclz_fromjobj(JNIEnv *env, jobject jobj)
    {
        return (*env)->GetObjectClass(env, jobj);
    }

    inline jobject jclz_callobjmtd(JNIEnv *env, jclass jclz, char const *name, char const *sig, ...)
    {
        va_list ap;
        va_start(ap, sig);
        return (*env)->CallObjectMethod(env, jclz,
                                        (*env)->GetMethodID(env, jclazz(env), name,
                                                            sig),
                                        ap);
        va_end(ap);
    }

    inline jobject jclz_callsticmtd(JNIEnv *env, jclass jclz, char const *name, char const *sig, ...)
    {
        va_list ap;
        va_start(ap, sig);
        return (*env)->CallStaticObjectMethod(env, jclz,
                                              (*env)->GetStaticMethodID(env, jclazz(env), name, sig),
                                              ap);
        va_end(ap);
    }

    inline jobject jclz_callctor(JNIEnv *env, jclass jclz, char const *sig, ...)
    {
        va_list ap;
        va_start(ap, sig);
        return jclz_callobjmtd(env, jclz, "<init>", sig, ap);
        va_end(ap);
    }

    inline jobject jobj_callobjmtd(JNIEnv *env, jobject jobj, char const *name, char const *sig, ...)
    {
        va_list ap;
        va_start(ap, sig);
        return (*env)->CallObjectMethod(env, jobj,
                                        (*env)->GetMethodID(env, jclz_fromjobj(env, jobj), name,
                                                            sig),
                                        ap);
        va_end(ap);
    }

    inline jstring jclz_name(JNIEnv *env, jclass jclz)
    {
        return (jstring)jclz_callobjmtd(env, jclz, "getName", "()Ljava/lang/String;");
    }

    inline jstring jclz_simp_name(JNIEnv *env, jclass jclz)
    {
        return (jstring)jclz_callobjmtd(env, jclz, "getSimpleName", "()Ljava/lang/String;");
    }

    inline jstring jobj_tostr(JNIEnv *env, jstring jstr)
    {
        return (jstring)jobj_callobjmtd(env, jstr, "toString", "()Ljava/lang/String;");
    }

    inline jobject jbyte_(JNIEnv *env, jbyte jbt)
    {
        return jclz_callctor(env, fdjclz(env, "java/lang/Byte"), "(B)V", jbt);
    }

    inline jobject jshort_(JNIEnv *env, jshort jsh)
    {
        return jclz_callctor(env, fdjclz(env, "java/lang/Short"), "(S)V", jsh);
    }

    inline jobject jint_(JNIEnv *env, jint ji)
    {
        return jclz_callctor(env, fdjclz(env, "java/lang/Integer"), "(I)V", ji);
    }

    inline jobject jlong_(JNIEnv *env, jlong jl)
    {
        return jclz_callctor(env, fdjclz(env, "java/lang/Long"), "(J)V", jl);
    }

    inline jobject jfloat_(JNIEnv *env, jfloat jf)
    {
        return jclz_callctor(env, fdjclz(env, "java/lang/Float"), "(F)V", jf);
    }

    inline jobject jdouble_(JNIEnv *env, jdouble jd)
    {
        return jclz_callctor(env, fdjclz(env, "java/lang/Double"), "(D)V", jd);
    }

    inline jobject jboolean_(JNIEnv *env, jboolean jbool)
    {
        return jclz_callctor(env, fdjclz(env, "java/lang/Boolean"), "(Z)V", jbool);
    }

    inline jobject jchar_(JNIEnv *env, jchar jch)
    {
        return jclz_callctor(env, fdjclz(env, "java/lang/Character"), "(C)V", jch);
    }

    inline jobject boxing(JNIEnv *env, char const *wrapper_name, ...)
    {
        char const *clzn_begin = "java/lang/";
        char *clzn = (char *)malloc(strlen(clzn_begin) + strlen(wrapper_name));
        strcpy(clzn, clzn_begin);
        strcat(clzn, wrapper_name);
        va_list ap;
        va_start(ap, wrapper_name);
        return jclz_callsticmtd(env, fdjclz(env, clzn), "valueOf", ap);
        va_end(ap);
    }

    inline jint jint_unboxing(JNIEnv *env, jobject jobj)
    {
    }

    inline void toast_show(JNIEnv *env, jobject ctx, jobject jobj, jint duration)
    {
        char *param_sign = jclz_name(env, jclz_fromjobj(env, jobj)) == "java.lang.Integer" ? "I"
                                                                                           : "Ljava/lang/CharSequence;";

        char const *sign_begin = "(Landroid/content/Context;";
        char const *sign_end = "I)Landroid/widget/Toast;";
        char *sign = (char *)malloc(strlen(sign_begin) + strlen(param_sign) + strlen(sign_end));
        strcpy(sign, sign_begin);
        strcat(sign, param_sign);
        strcat(sign, sign_end);
        /* (*env)->FindClass(env, "android/widget/Toast") */
        jclass jclz_Toast = fdjclz(env, "android/widget/Toast");
        jmethodID jmid_makeText = (*env)->GetStaticMethodID(env, jclz_Toast, "makeText", sign);
        jobject jobj_Toast = (*env)->CallStaticObjectMethod(env, jclz_Toast, jmid_makeText,
                                                            ctx,
                                                            param_sign == "I" ? (jint)jobj
                                                                              : jobj_tostr(env, jobj),
                                                            duration);
        jmethodID jmid_show = (*env)->GetMethodID(env, jclz_Toast, "show", "()V");
        (*env)->CallVoidMethod(env, jobj_Toast, jmid_show);
    }

    JNIEXPORT void JNICALL
    Java_com_meyoustu_amuse_App_verboseLog(JNIEnv *env, jobject thiz, jboolean if_debug, jstring tag,
                                           jobject msg)
    {
        if (if_debug == JNI_TRUE)
        {
            VLOG(jstr_cstr(env, tag), jstr_cstr(env, jobj_tostr(env, msg)));
        }
    }

    JNIEXPORT void JNICALL
    Java_com_meyoustu_amuse_App_debugLog(JNIEnv *env, jobject thiz, jboolean if_debug, jstring tag,
                                         jobject msg)
    {
        if (if_debug == JNI_TRUE)
        {
            DLOG(jstr_cstr(env, tag), jstr_cstr(env, jobj_tostr(env, msg)));
        }
    }

    JNIEXPORT void JNICALL
    Java_com_meyoustu_amuse_App_infoLog(JNIEnv *env, jobject thiz, jboolean if_debug, jstring tag,
                                        jobject msg)
    {
        if (if_debug == JNI_TRUE)
        {
            ILOG(jstr_cstr(env, tag), jstr_cstr(env, jobj_tostr(env, msg)));
        }
    }

    JNIEXPORT void JNICALL
    Java_com_meyoustu_amuse_App_warnLog(JNIEnv *env, jobject thiz, jboolean if_debug, jstring tag,
                                        jobject msg)
    {
        if (if_debug == JNI_TRUE)
        {
            WLOG(jstr_cstr(env, tag), jstr_cstr(env, jobj_tostr(env, msg)));
        }
    }

    JNIEXPORT void JNICALL
    Java_com_meyoustu_amuse_App_errorLog(JNIEnv *env, jobject thiz, jboolean if_debug, jstring tag,
                                         jobject msg)
    {
        if (if_debug == JNI_TRUE)
        {
            ELOG(jstr_cstr(env, tag), jstr_cstr(env, jobj_tostr(env, msg)));
        }
    }

    JNIEXPORT void JNICALL
    Java_com_meyoustu_amuse_util_Toast_toastMsg(JNIEnv *env, jobject thiz,
                                                jobject ctx, jobject msg, jint duration)
    {
        toast_show(env, ctx, msg, duration);
    }

    JNIEXPORT void JNICALL
    Java_com_meyoustu_amuse_util_Toast_toastRes(JNIEnv *env, jobject thiz,
                                                jobject ctx, jint id, jint duration)
    {
        toast_show(env, ctx, id, duration);
    }

#ifdef __cplusplus
}
#endif