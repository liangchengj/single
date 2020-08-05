/**
 * Created at 2020/7/22 11:28.
 *
 * @author Liangcheng Juves
 */
#ifndef _JNIVK_H
#define _JNIVK_H

#ifdef __cplusplus
extern "C"
{
#endif

/**
 * jboolean     Z
 * jbyte        B
 * jchar        C
 * jshort       S
 * jint         I
 * jlong        J
 * jfloat       F
 * jdouble      D
 * jobject      L
 */
#include <jni.h>

typedef char const *cstr;

// 'long' is always 32 bit on windows so this matches what jdk expects
// typedef long jint;
// typedef __int64 jlong;
// typedef signed char jbyte;

typedef jboolean /* unsigned char */ jbool;
// typedef unsigned short jchar;
// typedef short jshort;
// typedef float jfloat;
// typedef double jdouble;

// typedef jint jsize;

typedef jobject /* struct _jobject * */ jobj;
typedef jclass /* jobject */ jclz;
// typedef jthrowable /* jobject */ jthrowable;
typedef jstring /* jobject */ jstr;
typedef jarray /* jobject */ jarr;
typedef jbooleanArray /* jarray */ jboolArr;
typedef jbyteArray /* jarray */ jbyteArr;
typedef jcharArray /* jarray */ jcharArr;
typedef jshortArray /* jarray */ jshortArr;
typedef jintArray /* jarray */ jintArr;
typedef jlongArray /* jarray */ jlongArr;
typedef jfloatArray /* jarray */ jfloatArr;
typedef jdoubleArray /* jarray */ jdoubleArr;
typedef jobjectArray /* jarray */ jobjArr;

typedef jvalue /* union jvalue {
        jboolean z;
        jbyte b;
        jchar c;
        jshort s;
        jint i;
        jlong j;
        jfloat f;
        jdouble d;
        jobject l;
    } */
jval;

typedef jfieldID /* struct _jfieldID * */ jfID;
typedef jmethodID /* struct _jmethodID * */ jmID;

/* Return values from jobjectRefType */
typedef jobjectRefType /* enum _jobjectType
    {
        JNIInvalidRefType = 0,
        JNILocalRefType = 1,
        JNIGlobalRefType = 2,
        JNIWeakGlobalRefType = 3
    }  */
jobjRefT;

#ifndef true
#define true JNI_TRUE
#endif

#ifndef false
#define false JNI_FALSE
#endif

typedef JNIEnv /* const struct JNINativeInterface_ * */ JEnv;
typedef JavaVM /* const struct JNIInvokeInterface_ * */ JVM;

typedef jvalue const *jvalcon;

typedef JavaVMOption /* struct JavaVMOption
    {
        char *optionString;
        void *extraInfo;
    } */
JVMOption;

typedef JavaVMInitArgs /* struct JavaVMInitArgs
    {
        jint version;
        jint nOptions;
        JavaVMOption *options;
        jboolean ignoreUnrecognized;
    } */
JVMInitArgs;

typedef JavaVMAttachArgs /* struct JavaVMAttachArgs
    {
        jint version;
        char *name;
        jobject group;
    } */
JVMAttachArgs;

jint GetJNIVersion(JEnv *env);

jclz DefineClass(JEnv *env, cstr name, jobj loader, const jbyte *buf,
                 jsize len);
jclz FindClass(JEnv *env, cstr name);

jmID FromReflectedMethod(JEnv *env, jobj method);
jfID FromReflectedField(JEnv *env, jobj field);

jobj ToReflectedMethod(JEnv *env, jclz cls, jmID methodID, jbool isStatic);

jclz GetSuperclass(JEnv *env, jclz sub);
jbool IsAssignableFrom(JEnv *env, jclz sub, jclz sup);

jobj ToReflectedField(JEnv *env, jclz cls, jfID fieldID, jbool isStatic);

jint Throw(JEnv *env, jthrowable obj);
jint ThrowNew(JEnv *env, jclz clazz, cstr msg);
jthrowable ExceptionOccurred(JEnv *env);
void ExceptionDescribe(JEnv *env);
void ExceptionClear(JEnv *env);
void FatalError(JEnv *env, cstr msg);

jint PushLocalFrame(JEnv *env, jint capacity);
jobj PopLocalFrame(JEnv *env, jobj result);

jobj NewGlobalRef(JEnv *env, jobj lobj);
void DeleteGlobalRef(JEnv *env, jobj gref);
void DeleteLocalRef(JEnv *env, jobj obj);
jbool IsSameObject(JEnv *env, jobj obj1, jobj obj2);
jobj NewLocalRef(JEnv *env, jobj ref);
jint EnsureLocalCapacity(JEnv *env, jint capacity);

jobj AllocObject(JEnv *env, jclz clazz);
jobj NewObject(JEnv *env, jclz clazz, jmID methodID, ...);
jobj NewObjectV(JEnv *env, jclz clazz, jmID methodID, va_list args);
jobj NewObjectA(JEnv *env, jclz clazz, jmID methodID, jvalcon args);

jclz GetObjectClass(JEnv *env, jobj obj);
jbool IsInstanceOf(JEnv *env, jobj obj, jclz clazz);

jmID GetMethodID(JEnv *env, jclz clazz, cstr name, cstr sig);

jobj CallObjectMethod(JEnv *env, jobj obj, jmID methodID, ...);
jobj CallObjectMethodV(JEnv *env, jobj obj, jmID methodID, va_list args);
jobj CallObjectMethodA(JEnv *env, jobj obj, jmID methodID, jvalcon args);

jbool CallBooleanMethod(JEnv *env, jobj obj, jmID methodID, ...);
jbool CallBooleanMethodV(JEnv *env, jobj obj, jmID methodID, va_list args);
jbool CallBooleanMethodA(JEnv *env, jobj obj, jmID methodID, jvalcon args);

jbyte CallByteMethod(JEnv *env, jobj obj, jmID methodID, ...);
jbyte CallByteMethodV(JEnv *env, jobj obj, jmID methodID, va_list args);
jbyte CallByteMethodA(JEnv *env, jobj obj, jmID methodID, jvalcon args);

jchar CallCharMethod(JEnv *env, jobj obj, jmID methodID, ...);
jchar CallCharMethodV(JEnv *env, jobj obj, jmID methodID, va_list args);
jchar CallCharMethodA(JEnv *env, jobj obj, jmID methodID, jvalcon args);

jshort CallShortMethod(JEnv *env, jobj obj, jmID methodID, ...);
jshort CallShortMethodV(JEnv *env, jobj obj, jmID methodID, va_list args);
jshort CallShortMethodA(JEnv *env, jobj obj, jmID methodID, jvalcon args);

jint CallIntMethod(JEnv *env, jobj obj, jmID methodID, ...);
jint CallIntMethodV(JEnv *env, jobj obj, jmID methodID, va_list args);
jint CallIntMethodA(JEnv *env, jobj obj, jmID methodID, jvalcon args);

jlong CallLongMethod(JEnv *env, jobj obj, jmID methodID, ...);
jlong CallLongMethodV(JEnv *env, jobj obj, jmID methodID, va_list args);
jlong CallLongMethodA(JEnv *env, jobj obj, jmID methodID, jvalcon args);

jfloat CallFloatMethod(JEnv *env, jobj obj, jmID methodID, ...);
jfloat CallFloatMethodV(JEnv *env, jobj obj, jmID methodID, va_list args);
jfloat CallFloatMethodA(JEnv *env, jobj obj, jmID methodID, jvalcon args);

jdouble CallDoubleMethod(JEnv *env, jobj obj, jmID methodID, ...);
jdouble CallDoubleMethodV(JEnv *env, jobj obj, jmID methodID, va_list args);
jdouble CallDoubleMethodA(JEnv *env, jobj obj, jmID methodID, jvalcon args);

void CallVoidMethod(JEnv *env, jobj obj, jmID methodID, ...);
void CallVoidMethodV(JEnv *env, jobj obj, jmID methodID, va_list args);
void CallVoidMethodA(JEnv *env, jobj obj, jmID methodID, jvalcon args);

jobj CallNonvirtualObjectMethod(JEnv *env, jobj obj, jclz clazz, jmID methodID, ...);
jobj CallNonvirtualObjectMethodV(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                 va_list args);
jobj CallNonvirtualObjectMethodA(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                 jvalcon args);

jbool CallNonvirtualBooleanMethod(JEnv *env, jobj obj, jclz clazz, jmID methodID, ...);
jboolean CallNonvirtualBooleanMethodV(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                      va_list args);
jboolean CallNonvirtualBooleanMethodA(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                      jvalcon args);

jbyte CallNonvirtualByteMethod(JEnv *env, jobj obj, jclz clazz, jmID methodID, ...);
jbyte CallNonvirtualByteMethodV(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                va_list args);
jbyte CallNonvirtualByteMethodA(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                jvalcon args);

jchar CallNonvirtualCharMethod(JEnv *env, jobj obj, jclz clazz, jmID methodID, ...);
jchar CallNonvirtualCharMethodV(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                va_list args);
jchar CallNonvirtualCharMethodA(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                jvalcon args);

jshort CallNonvirtualShortMethod(JEnv *env, jobj obj, jclz clazz, jmID methodID, ...);
jshort CallNonvirtualShortMethodV(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                  va_list args);
jshort CallNonvirtualShortMethodA(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                  jvalcon args);

jint CallNonvirtualIntMethod(JEnv *env, jobj obj, jclz clazz, jmID methodID, ...);
jint CallNonvirtualIntMethodV(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                              va_list args);
jint CallNonvirtualIntMethodA(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                              jvalcon args);

jlong CallNonvirtualLongMethod(JEnv *env, jobj obj, jclz clazz, jmID methodID, ...);
jlong CallNonvirtualLongMethodV(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                va_list args);
jlong CallNonvirtualLongMethodA(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                jvalcon args);

jfloat CallNonvirtualFloatMethod(JEnv *env, jobj obj, jclz clazz, jmID methodID, ...);
jfloat CallNonvirtualFloatMethodV(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                  va_list args);
jfloat CallNonvirtualFloatMethodA(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                  jvalcon args);

jdouble CallNonvirtualDoubleMethod(JEnv *env, jobj obj, jclz clazz, jmID methodID, ...);
jdouble CallNonvirtualDoubleMethodV(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                    va_list args);
jdouble CallNonvirtualDoubleMethodA(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                    jvalcon args);

void CallNonvirtualVoidMethod(JEnv *env, jobj obj, jclz clazz, jmID methodID, ...);
void CallNonvirtualVoidMethodV(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                               va_list args);
void CallNonvirtualVoidMethodA(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                               jvalcon args);

jfID GetFieldID(JEnv *env, jclz clazz, cstr name, cstr sig);

jobj GetObjectField(JEnv *env, jobj obj, jfID fieldID);
jbool GetBooleanField(JEnv *env, jobj obj, jfID fieldID);
jbyte GetByteField(JEnv *env, jobj obj, jfID fieldID);
jchar GetCharField(JEnv *env, jobj obj, jfID fieldID);
jshort GetShortField(JEnv *env, jobj obj, jfID fieldID);
jint GetIntField(JEnv *env, jobj obj, jfID fieldID);
jlong GetLongField(JEnv *env, jobj obj, jfID fieldID);
jfloat GetFloatField(JEnv *env, jobj obj, jfID fieldID);
jdouble GetDoubleField(JEnv *env, jobj obj, jfID fieldID);

void SetObjectField(JEnv *env, jobj obj, jfID fieldID, jobj val);
void SetBooleanField(JEnv *env, jobj obj, jfID fieldID, jbool val);
void SetByteField(JEnv *env, jobj obj, jfID fieldID, jbyte val);
void SetCharField(JEnv *env, jobj obj, jfID fieldID, jchar val);
void SetShortField(JEnv *env, jobj obj, jfID fieldID, jshort val);
void SetIntField(JEnv *env, jobj obj, jfID fieldID, jint val);
void SetLongField(JEnv *env, jobj obj, jfID fieldID, jlong val);
void SetFloatField(JEnv *env, jobj obj, jfID fieldID, jfloat val);
void SetDoubleField(JEnv *env, jobj obj, jfID fieldID, jdouble val);

jmID GetStaticMethodID(JEnv *env, jclz clazz, cstr name, cstr sig);

jobj CallStaticObjectMethod(JEnv *env, jclz clazz, jmID methodID, ...);
jobj CallStaticObjectMethodV(JEnv *env, jclz clazz, jmID methodID, va_list args);
jobj CallStaticObjectMethodA(JEnv *env, jclz clazz, jmID methodID, jvalcon args);

jbool CallStaticBooleanMethod(JEnv *env, jclz clazz, jmID methodID, ...);
jbool CallStaticBooleanMethodV(JEnv *env, jclz clazz, jmID methodID, va_list args);
jbool CallStaticBooleanMethodA(JEnv *env, jclz clazz, jmID methodID, jvalcon args);

jbyte CallStaticByteMethod(JEnv *env, jclz clazz, jmID methodID, ...);
jbyte CallStaticByteMethodV(JEnv *env, jclz clazz, jmID methodID, va_list args);
jbyte CallStaticByteMethodA(JEnv *env, jclz clazz, jmID methodID, jvalcon args);

jchar CallStaticCharMethod(JEnv *env, jclz clazz, jmID methodID, ...);
jchar CallStaticCharMethodV(JEnv *env, jclz clazz, jmID methodID, va_list args);
jchar CallStaticCharMethodA(JEnv *env, jclz clazz, jmID methodID, jvalcon args);

jshort CallStaticShortMethod(JEnv *env, jclz clazz, jmID methodID, ...);
jshort CallStaticShortMethodV(JEnv *env, jclz clazz, jmID methodID, va_list args);
jshort CallStaticShortMethodA(JEnv *env, jclz clazz, jmID methodID, jvalcon args);

jint CallStaticIntMethod(JEnv *env, jclz clazz, jmID methodID, ...);
jint CallStaticIntMethodV(JEnv *env, jclz clazz, jmID methodID, va_list args);
jint CallStaticIntMethodA(JEnv *env, jclz clazz, jmID methodID, jvalcon args);

jlong CallStaticLongMethod(JEnv *env, jclz clazz, jmID methodID, ...);
jlong CallStaticLongMethodV(JEnv *env, jclz clazz, jmID methodID, va_list args);
jlong CallStaticLongMethodA(JEnv *env, jclz clazz, jmID methodID, jvalcon args);

jfloat CallStaticFloatMethod(JEnv *env, jclz clazz, jmID methodID, ...);
jfloat CallStaticFloatMethodV(JEnv *env, jclz clazz, jmID methodID, va_list args);
jfloat CallStaticFloatMethodA(JEnv *env, jclz clazz, jmID methodID, jvalcon args);

jdouble CallStaticDoubleMethod(JEnv *env, jclz clazz, jmID methodID, ...);
jdouble CallStaticDoubleMethodV(JEnv *env, jclz clazz, jmID methodID, va_list args);
jdouble CallStaticDoubleMethodA(JEnv *env, jclz clazz, jmID methodID, jvalcon args);

void CallStaticVoidMethod(JEnv *env, jclz cls, jmID methodID, ...);
void CallStaticVoidMethodV(JEnv *env, jclz cls, jmID methodID, va_list args);
void CallStaticVoidMethodA(JEnv *env, jclz cls, jmID methodID, jvalcon args);

jfID GetStaticFieldID(JEnv *env, jclz clazz, cstr name, cstr sig);
jobj GetStaticObjectField(JEnv *env, jclz clazz, jfID fieldID);
jbool GetStaticBooleanField(JEnv *env, jclz clazz, jfID fieldID);
jbyte GetStaticByteField(JEnv *env, jclz clazz, jfID fieldID);
jchar GetStaticCharField(JEnv *env, jclz clazz, jfID fieldID);
jshort GetStaticShortField(JEnv *env, jclz clazz, jfID fieldID);
jint GetStaticIntField(JEnv *env, jclz clazz, jfID fieldID);
jlong GetStaticLongField(JEnv *env, jclz clazz, jfID fieldID);
jfloat GetStaticFloatField(JEnv *env, jclz clazz, jfID fieldID);
jdouble GetStaticDoubleField(JEnv *env, jclz clazz, jfID fieldID);

void SetStaticObjectField(JEnv *env, jclz clazz, jfID fieldID, jobj value);
void SetStaticBooleanField(JEnv *env, jclz clazz, jfID fieldID, jbool value);
void SetStaticByteField(JEnv *env, jclz clazz, jfID fieldID, jbyte value);
void SetStaticCharField(JEnv *env, jclz clazz, jfID fieldID, jchar value);
void SetStaticShortField(JEnv *env, jclz clazz, jfID fieldID, jshort value);
void SetStaticIntField(JEnv *env, jclz clazz, jfID fieldID, jint value);
void SetStaticLongField(JEnv *env, jclz clazz, jfID fieldID, jlong value);
void SetStaticFloatField(JEnv *env, jclz clazz, jfID fieldID, jfloat value);
void SetStaticDoubleField(JEnv *env, jclz clazz, jfID fieldID, jdouble value);

jstr NewString(JEnv *env, const jchar *unicode, jsize len);
jsize GetStringLength(JEnv *env, jstr str);
const jchar *GetStringChars(JEnv *env, jstr str, jbool *isCopy);
void ReleaseStringChars(JEnv *env, jstr str, const jchar *chars);

jstr NewStringUTF(JEnv *env, cstr utf);
jsize GetStringUTFLength(JEnv *env, jstr str);
cstr GetStringUTFChars(JEnv *env, jstr str, jbool *isCopy);
void ReleaseStringUTFChars(JEnv *env, jstr str, cstr chars);

jsize GetArrayLength(JEnv *env, jarr array);

jobjArr NewObjectArray(JEnv *env, jsize len, jclz clazz, jobj init);
jobj GetObjectArrayElement(JEnv *env, jobjArr array, jsize index);
void SetObjectArrayElement(JEnv *env, jobjArr array, jsize index, jobj val);

jboolArr NewBooleanArray(JEnv *env, jsize len);
jbyteArr NewByteArray(JEnv *env, jsize len);
jcharArr NewCharArray(JEnv *env, jsize len);
jshortArr NewShortArray(JEnv *env, jsize len);
jintArr NewIntArray(JEnv *env, jsize len);
jlongArr NewLongArray(JEnv *env, jsize len);
jfloatArr NewFloatArray(JEnv *env, jsize len);
jdoubleArr NewDoubleArray(JEnv *env, jsize len);

jbool *GetBooleanArrayElements(JEnv *env, jboolArr array, jbool *isCopy);
jbyte *GetByteArrayElements(JEnv *env, jbyteArr array, jbool *isCopy);
jchar *GetCharArrayElements(JEnv *env, jcharArr array, jbool *isCopy);
jshort *GetShortArrayElements(JEnv *env, jshortArr array, jbool *isCopy);
jint *GetIntArrayElements(JEnv *env, jintArr array, jbool *isCopy);
jlong *GetLongArrayElements(JEnv *env, jlongArr array, jbool *isCopy);
jfloat *GetFloatArrayElements(JEnv *env, jfloatArr array, jbool *isCopy);
jdouble *GetDoubleArrayElements(JEnv *env, jdoubleArr array, jbool *isCopy);

void ReleaseBooleanArrayElements(JEnv *env, jboolArr array, jbool *elems, jint mode);
void ReleaseByteArrayElements(JEnv *env, jbyteArr array, jbyte *elems, jint mode);
void ReleaseCharArrayElements(JEnv *env, jcharArr array, jchar *elems, jint mode);
void ReleaseShortArrayElements(JEnv *env, jshortArr array, jshort *elems, jint mode);
void ReleaseIntArrayElements(JEnv *env, jintArr array, jint *elems, jint mode);
void ReleaseLongArrayElements(JEnv *env, jlongArr array, jlong *elems, jint mode);
void ReleaseFloatArrayElements(JEnv *env, jfloatArr array, jfloat *elems, jint mode);
void ReleaseDoubleArrayElements(JEnv *env, jdoubleArr array, jdouble *elems, jint mode);

void GetBooleanArrayRegion(JEnv *env, jboolArr array, jsize start, jsize l, jbool *buf);
void GetByteArrayRegion(JEnv *env, jbyteArr array, jsize start, jsize len, jbyte *buf);
void GetCharArrayRegion(JEnv *env, jcharArr array, jsize start, jsize len, jchar *buf);
void GetShortArrayRegion(JEnv *env, jshortArr array, jsize start, jsize len, jshort *buf);
void GetIntArrayRegion(JEnv *env, jintArr array, jsize start, jsize len, jint *buf);
void GetLongArrayRegion(JEnv *env, jlongArr array, jsize start, jsize len, jlong *buf);
void GetFloatArrayRegion(JEnv *env, jfloatArr array, jsize start, jsize len, jfloat *buf);
void GetDoubleArrayRegion(JEnv *env, jdoubleArr array, jsize start, jsize len, jdouble *buf);

void SetBooleanArrayRegion(JEnv *env, jboolArr array, jsize start, jsize l, const jbool *buf);
void SetByteArrayRegion(JEnv *env, jbyteArr array, jsize start, jsize len, const jbyte *buf);
void SetCharArrayRegion(JEnv *env, jcharArr array, jsize start, jsize len, const jchar *buf);
void SetShortArrayRegion(JEnv *env, jshortArr array, jsize start, jsize len, const jshort *buf);
void SetIntArrayRegion(JEnv *env, jintArr array, jsize start, jsize len, const jint *buf);
void SetLongArrayRegion(JEnv *env, jlongArr array, jsize start, jsize len, const jlong *buf);
void SetFloatArrayRegion(JEnv *env, jfloatArr array, jsize start, jsize len, const jfloat *buf);
void SetDoubleArrayRegion(JEnv *env, jdoubleArr array, jsize start, jsize len, const jdouble *buf);

jint RegisterNatives(JEnv *env, jclz clazz, const JNINativeMethod *methods,
                     jint nMethods);
jint UnregisterNatives(JEnv *env, jclz clazz);

jint MonitorEnter(JEnv *env, jobj obj);
jint MonitorExit(JEnv *env, jobj obj);

jint GetJavaVM(JEnv *env, JVM **vm);

void GetStringRegion(JEnv *env, jstr str, jsize start, jsize len, jchar *buf);
void GetStringUTFRegion(JEnv *env, jstr str, jsize start, jsize len, char *buf);

void *GetPrimitiveArrayCritical(JEnv *env, jarr array, jbool *isCopy);
void ReleasePrimitiveArrayCritical(JEnv *env, jarr array, void *carray, jint mode);

const jchar *GetStringCritical(JEnv *env, jstr string, jbool *isCopy);
void ReleaseStringCritical(JEnv *env, jstr string, const jchar *cstring);

jweak NewWeakGlobalRef(JEnv *env, jobj obj);
void DeleteWeakGlobalRef(JEnv *env, jweak ref);

jbool ExceptionCheck(JEnv *env);

jobj NewDirectByteBuffer(JEnv *env, void *address, jlong capacity);
void *GetDirectBufferAddress(JEnv *env, jobj buf);
jlong GetDirectBufferCapacity(JEnv *env, jobj buf);

/* New JNI 1.6 Features */

jobjRefT GetObjectRefType(JEnv *env, jobj obj);

/* Module Features */

jobj GetModule(JEnv *env, jclz clazz);

/* End VM-specific. */

jint DestroyJavaVM(JVM *vm);

jint AttachCurrentThread(JVM *vm, void **penv, void *args);

jint DetachCurrentThread(JVM *vm);

jint GetEnv(JVM *vm, void **penv, jint version);

jint AttachCurrentThreadAsDaemon(JVM *vm, void **penv, void *args);

/* End jni.h. */

/* Begin implement. */

jint GetJNIVersion(JEnv *env)
{
#ifndef __cplusplus
    return (*env)->GetVersion(env);
#else
    return env->GetVersion();
#endif
}

jclz DefineClass(JEnv *env, cstr name, jobj loader, const jbyte *buf,
                 jsize len)
{
#ifndef __cplusplus
    return (*env)->DefineClass(env, name, loader, buf, len);
#else
    return env->DefineClass(name, loader, buf, len);
#endif
}
jclz FindClass(JEnv *env, cstr name)
{
#ifndef __cplusplus
    return (*env)->FindClass(env, name);
#else
    return env->FindClass(name);
#endif
}

jmID FromReflectedMethod(JEnv *env, jobj method)
{
#ifndef __cplusplus
    return (*env)->FromReflectedMethod(env, method);
#else
    return env->FromReflectedMethod(method);
#endif
}
jfID FromReflectedField(JEnv *env, jobj field)
{
#ifndef __cplusplus
    return (*env)->FromReflectedField(env, field);
#else
    return env->FromReflectedField(field);
#endif
}

jobj ToReflectedMethod(JEnv *env, jclz cls, jmID methodID, jbool isStatic)
{
#ifndef __cplusplus
    return (*env)->ToReflectedMethod(env, cls, methodID, isStatic);
#else
    return env->ToReflectedMethod(cls, methodID, isStatic);
#endif
}

jclz GetSuperclass(JEnv *env, jclz sub)
{
#ifndef __cplusplus
    return (*env)->GetSuperclass(env, sub);
#else
    return env->GetSuperclass(sub);
#endif
}
jbool IsAssignableFrom(JEnv *env, jclz sub, jclz sup)
{
#ifndef __cplusplus
    return (*env)->IsAssignableFrom(env, sub, sup);
#else
    return env->IsAssignableFrom(sub, sup);
#endif
}

jobj ToReflectedField(JEnv *env, jclz cls, jfID fieldID, jbool isStatic)
{
#ifndef __cplusplus
    return (*env)->ToReflectedField(env, cls, fieldID, isStatic);
#else
    return env->ToReflectedField(cls, fieldID, isStatic);
#endif
}

jint Throw(JEnv *env, jthrowable obj)
{
#ifndef __cplusplus
    return (*env)->Throw(env, obj);
#else
    return env->Throw(obj);
#endif
}
jint ThrowNew(JEnv *env, jclz clazz, cstr msg)
{
#ifndef __cplusplus
    return (*env)->ThrowNew(env, clazz, msg);
#else
    return env->ThrowNew(clazz, msg);
#endif
}
jthrowable ExceptionOccurred(JEnv *env)
{
#ifndef __cplusplus
    return (*env)->ExceptionOccurred(env);
#else
    return env->ExceptionOccurred();
#endif
}
void ExceptionDescribe(JEnv *env)
{
#ifndef __cplusplus
    (*env)->ExceptionDescribe(env);
#else
    return env->ExceptionDescribe();
#endif
}
void ExceptionClear(JEnv *env)
{
#ifndef __cplusplus
    (*env)->ExceptionClear(env);
#else
    env->ExceptionClear();
#endif
}
void FatalError(JEnv *env, cstr msg)
{
#ifndef __cplusplus
    (*env)->FatalError(env, msg);
#else
    env->FatalError(msg);
#endif
}

jint PushLocalFrame(JEnv *env, jint capacity)
{
#ifndef __cplusplus
    return (*env)->PushLocalFrame(env, capacity);
#else
    return env->PushLocalFrame(capacity);
#endif
}
jobj PopLocalFrame(JEnv *env, jobj result)
{
#ifndef __cplusplus
    return (*env)->PopLocalFrame(env, result);
#else
    return env->PopLocalFrame(result);
#endif
}

jobj NewGlobalRef(JEnv *env, jobj lobj)
{
#ifndef __cplusplus
    return (*env)->NewGlobalRef(env, lobj);
#else
    return env->NewGlobalRef(lobj);
#endif
}
void DeleteGlobalRef(JEnv *env, jobj gref)
{
#ifndef __cplusplus
    (*env)->DeleteGlobalRef(env, gref);
#else
    return env->DeleteGlobalRef(gref);
#endif
}
void DeleteLocalRef(JEnv *env, jobj obj)
{
#ifndef __cplusplus
    (*env)->DeleteLocalRef(env, obj);
#else
    return env->DeleteLocalRef(obj);
#endif
}
jbool IsSameObject(JEnv *env, jobj obj1, jobj obj2)
{
#ifndef __cplusplus
    return (*env)->IsSameObject(env, obj1, obj2);
#else
    return env->IsSameObject(obj1, obj2);
#endif
}
jobj NewLocalRef(JEnv *env, jobj ref)
{
#ifndef __cplusplus
    return (*env)->NewLocalRef(env, ref);
#else
    return env->NewLocalRef(ref);
#endif
}
jint EnsureLocalCapacity(JEnv *env, jint capacity)
{
#ifndef __cplusplus
    return (*env)->EnsureLocalCapacity(env, capacity);
#else
    return env->EnsureLocalCapacity(capacity);
#endif
}

jobj AllocObject(JEnv *env, jclz clazz)
{
#ifndef __cplusplus
    return (*env)->AllocObject(env, clazz);
#else
    return env->AllocObject(clazz);
#endif
}
jobj NewObject(JEnv *env, jclz clazz, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->NewObject(env, clazz, methodID, args);
#else
    return env->NewObject(clazz, methodID, args);
#endif
    va_end(args);
}
jobj NewObjectV(JEnv *env, jclz clazz, jmID methodID, va_list args)
{
#ifndef __cplusplus
    return (*env)->NewObjectV(env, clazz, methodID, args);
#else
    return env->NewObjectV(clazz, methodID, args);
#endif
}
jobj NewObjectA(JEnv *env, jclz clazz, jmID methodID, jvalcon args)
{
#ifndef __cplusplus
    return (*env)->NewObjectA(env, clazz, methodID, args);
#else
    return env->NewObjectA(clazz, methodID, args);
#endif
}

jclz GetObjectClass(JEnv *env, jobj obj)
{
#ifndef __cplusplus
    return (*env)->GetObjectClass(env, obj);
#else
    return env->GetObjectClass(obj);
#endif
}
jbool IsInstanceOf(JEnv *env, jobj obj, jclz clazz)
{
#ifndef __cplusplus
    return (*env)->IsInstanceOf(env, obj, clazz);
#else
    return env->IsInstanceOf(obj, clazz);
#endif
}

jmID GetMethodID(JEnv *env, jclz clazz, cstr name, cstr sig)
{
#ifndef __cplusplus
    return (*env)->GetMethodID(env, clazz, name, sig);
#else
    return env->GetMethodID(clazz, name, sig);
#endif
}

jobj CallObjectMethod(JEnv *env, jobj obj, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallObjectMethod(env, obj, methodID, args);
#else
    return env->CallObjectMethod(obj, methodID, args);
#endif
    va_end(args);
}
jobj CallObjectMethodV(JEnv *env, jobj obj, jmID methodID, va_list args)
{
#ifndef __cplusplus
    return (*env)->CallObjectMethodV(env, obj, methodID, args);
#else
    return env->CallObjectMethodV(obj, methodID, args);
#endif
}
jobj CallObjectMethodA(JEnv *env, jobj obj, jmID methodID, jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallObjectMethodA(env, obj, methodID, args);
#else
    return env->CallObjectMethodA(obj, methodID, args);
#endif
}

jbool CallBooleanMethod(JEnv *env, jobj obj, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallBooleanMethod(env, obj, methodID, args);
#else
    return env->CallBooleanMethod(obj, methodID, args);
#endif
    va_end(args);
}
jbool CallBooleanMethodV(JEnv *env, jobj obj, jmID methodID, va_list args)
{
#ifndef __cplusplus
    return (*env)->CallBooleanMethodV(env, obj, methodID, args);
#else
    return env->CallBooleanMethodV(obj, methodID, args);
#endif
}
jbool CallBooleanMethodA(JEnv *env, jobj obj, jmID methodID, jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallBooleanMethodA(env, obj, methodID, args);
#else
    return env->CallBooleanMethodA(obj, methodID, args);
#endif
}

jbyte CallByteMethod(JEnv *env, jobj obj, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallByteMethod(env, obj, methodID, args);
#else
    return env->CallByteMethod(obj, methodID, args);
#endif
    va_end(args);
}
jbyte CallByteMethodV(JEnv *env, jobj obj, jmID methodID, va_list args)
{
#ifndef __cplusplus
    return (*env)->CallByteMethodV(env, obj, methodID, args);
#else
    return env->CallByteMethodV(obj, methodID, args);
#endif
}
jbyte CallByteMethodA(JEnv *env, jobj obj, jmID methodID, jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallByteMethodA(env, obj, methodID, args);
#else
    return env->CallByteMethodA(obj, methodID, args);
#endif
}

jchar CallCharMethod(JEnv *env, jobj obj, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallCharMethod(env, obj, methodID, args);
#else
    return env->CallCharMethod(obj, methodID, args);
#endif
    va_end(args);
}
jchar CallCharMethodV(JEnv *env, jobj obj, jmID methodID, va_list args)
{
#ifndef __cplusplus
    return (*env)->CallCharMethodV(env, obj, methodID, args);
#else
    return env->CallCharMethodV(obj, methodID, args);
#endif
}
jchar CallCharMethodA(JEnv *env, jobj obj, jmID methodID, jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallCharMethodA(env, obj, methodID, args);
#else
    return env->CallCharMethodA(obj, methodID, args);
#endif
}

jshort CallShortMethod(JEnv *env, jobj obj, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallShortMethod(env, obj, methodID, args);
#else
    return env->CallShortMethod(obj, methodID, args);
#endif
    va_end(args);
}
jshort CallShortMethodV(JEnv *env, jobj obj, jmID methodID, va_list args)
{
#ifndef __cplusplus
    return (*env)->CallShortMethodV(env, obj, methodID, args);
#else
    return env->CallShortMethodV(obj, methodID, args);
#endif
}
jshort CallShortMethodA(JEnv *env, jobj obj, jmID methodID, jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallShortMethodA(env, obj, methodID, args);
#else
    return env->CallShortMethodA(obj, methodID, args);
#endif
}

jint CallIntMethod(JEnv *env, jobj obj, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallIntMethod(env, obj, methodID, args);
#else
    return env->CallIntMethod(obj, methodID, args);
#endif
    va_end(args);
}
jint CallIntMethodV(JEnv *env, jobj obj, jmID methodID, va_list args)
{
#ifndef __cplusplus
    return (*env)->CallIntMethodV(env, obj, methodID, args);
#else
    return env->CallIntMethodV(obj, methodID, args);
#endif
}
jint CallIntMethodA(JEnv *env, jobj obj, jmID methodID, jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallIntMethodA(env, obj, methodID, args);
#else
    return env->CallIntMethodA(obj, methodID, args);
#endif
}

jlong CallLongMethod(JEnv *env, jobj obj, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallLongMethod(env, obj, methodID, args);
#else
    return env->CallLongMethod(obj, methodID, args);
#endif
    va_end(args);
}
jlong CallLongMethodV(JEnv *env, jobj obj, jmID methodID, va_list args)
{
#ifndef __cplusplus
    return (*env)->CallLongMethodV(env, obj, methodID, args);
#else
    return env->CallLongMethodV(obj, methodID, args);
#endif
}
jlong CallLongMethodA(JEnv *env, jobj obj, jmID methodID, jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallLongMethodA(env, obj, methodID, args);
#else
    return env->CallLongMethodA(obj, methodID, args);
#endif
}

jfloat CallFloatMethod(JEnv *env, jobj obj, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallFloatMethod(env, obj, methodID, args);
#else
    return env->CallFloatMethod(obj, methodID, args);
#endif
    va_end(args);
}
jfloat CallFloatMethodV(JEnv *env, jobj obj, jmID methodID, va_list args)
{
#ifndef __cplusplus
    return (*env)->CallFloatMethodV(env, obj, methodID, args);
#else
    return env->CallFloatMethodV(obj, methodID, args);
#endif
}
jfloat CallFloatMethodA(JEnv *env, jobj obj, jmID methodID, jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallFloatMethodA(env, obj, methodID, args);
#else
    return env->CallFloatMethodA(obj, methodID, args);
#endif
}

jdouble CallDoubleMethod(JEnv *env, jobj obj, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallDoubleMethod(env, obj, methodID, args);
#else
    return env->CallDoubleMethod(obj, methodID, args);
#endif
    va_end(args);
}
jdouble CallDoubleMethodV(JEnv *env, jobj obj, jmID methodID, va_list args)
{
#ifndef __cplusplus
    return (*env)->CallDoubleMethodV(env, obj, methodID, args);
#else
    return env->CallDoubleMethodV(obj, methodID, args);
#endif
}
jdouble CallDoubleMethodA(JEnv *env, jobj obj, jmID methodID, jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallDoubleMethodA(env, obj, methodID, args);
#else
    return env->CallDoubleMethodA(obj, methodID, args);
#endif
}

void CallVoidMethod(JEnv *env, jobj obj, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    (*env)->CallVoidMethod(env, obj, methodID, args);
#else
    env->CallVoidMethod(obj, methodID, args);
#endif
    va_end(args);
}
void CallVoidMethodV(JEnv *env, jobj obj, jmID methodID, va_list args)
{
#ifndef __cplusplus
    (*env)->CallVoidMethodV(env, obj, methodID, args);
#else
    env->CallVoidMethodV(obj, methodID, args);
#endif
}
void CallVoidMethodA(JEnv *env, jobj obj, jmID methodID, jvalcon args)
{
#ifndef __cplusplus
    (*env)->CallVoidMethodA(env, obj, methodID, args);
#else
    env->CallVoidMethodA(obj, methodID, args);
#endif
}

jobj CallNonvirtualObjectMethod(JEnv *env, jobj obj, jclz clazz, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallNonvirtualObjectMethod(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualObjectMethod(obj, clazz, methodID, args);
#endif
    va_end(args);
}
jobj CallNonvirtualObjectMethodV(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                 va_list args)
{
#ifndef __cplusplus
    return (*env)->CallNonvirtualObjectMethodV(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualObjectMethodV(obj, clazz, methodID, args);
#endif
}
jobj CallNonvirtualObjectMethodA(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                 jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallNonvirtualObjectMethodA(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualObjectMethodA(obj, clazz, methodID, args);
#endif
}

jbool CallNonvirtualBooleanMethod(JEnv *env, jobj obj, jclz clazz, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallNonvirtualBooleanMethod(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualBooleanMethod(obj, clazz, methodID, args);
#endif
    va_end(args);
}
jboolean CallNonvirtualBooleanMethodV(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                      va_list args)
{
#ifndef __cplusplus
    return (*env)->CallNonvirtualBooleanMethodV(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualBooleanMethodV(obj, clazz, methodID, args);
#endif
}
jboolean CallNonvirtualBooleanMethodA(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                      jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallNonvirtualBooleanMethodA(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualBooleanMethodA(obj, clazz, methodID, args);
#endif
}

jbyte CallNonvirtualByteMethod(JEnv *env, jobj obj, jclz clazz, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallNonvirtualByteMethod(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualByteMethod(obj, clazz, methodID, args);
#endif
    va_end(args);
}
jbyte CallNonvirtualByteMethodV(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                va_list args)
{
#ifndef __cplusplus
    return (*env)->CallNonvirtualByteMethodV(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualByteMethodV(obj, clazz, methodID, args);
#endif
}
jbyte CallNonvirtualByteMethodA(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallNonvirtualByteMethodA(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualByteMethodA(obj, clazz, methodID, args);
#endif
}

jchar CallNonvirtualCharMethod(JEnv *env, jobj obj, jclz clazz, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallNonvirtualCharMethod(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualCharMethod(obj, clazz, methodID, args);
#endif
    va_end(args);
}
jchar CallNonvirtualCharMethodV(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                va_list args)
{
#ifndef __cplusplus
    return (*env)->CallNonvirtualCharMethodV(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualCharMethodV(obj, clazz, methodID, args);
#endif
}
jchar CallNonvirtualCharMethodA(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallNonvirtualCharMethodA(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualCharMethodA(obj, clazz, methodID, args);
#endif
}

jshort CallNonvirtualShortMethod(JEnv *env, jobj obj, jclz clazz, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallNonvirtualShortMethod(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualShortMethod(obj, clazz, methodID, args);
#endif
    va_end(args);
}
jshort CallNonvirtualShortMethodV(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                  va_list args)
{
#ifndef __cplusplus
    return (*env)->CallNonvirtualShortMethodV(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualShortMethodV(obj, clazz, methodID, args);
#endif
}
jshort CallNonvirtualShortMethodA(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                  jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallNonvirtualShortMethodA(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualShortMethodA(obj, clazz, methodID, args);
#endif
}

jint CallNonvirtualIntMethod(JEnv *env, jobj obj, jclz clazz, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallNonvirtualIntMethod(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualIntMethod(obj, clazz, methodID, args);
#endif
    va_end(args);
}
jint CallNonvirtualIntMethodV(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                              va_list args)
{
#ifndef __cplusplus
    return (*env)->CallNonvirtualIntMethodV(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualIntMethodV(obj, clazz, methodID, args);
#endif
}
jint CallNonvirtualIntMethodA(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                              jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallNonvirtualIntMethodA(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualIntMethodA(obj, clazz, methodID, args);
#endif
}

jlong CallNonvirtualLongMethod(JEnv *env, jobj obj, jclz clazz, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallNonvirtualLongMethod(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualLongMethod(obj, clazz, methodID, args);
#endif
    va_end(args);
}
jlong CallNonvirtualLongMethodV(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                va_list args)
{
#ifndef __cplusplus
    return (*env)->CallNonvirtualLongMethodV(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualLongMethodV(obj, clazz, methodID, args);
#endif
}
jlong CallNonvirtualLongMethodA(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallNonvirtualLongMethodA(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualLongMethodA(obj, clazz, methodID, args);
#endif
}

jfloat CallNonvirtualFloatMethod(JEnv *env, jobj obj, jclz clazz, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallNonvirtualFloatMethod(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualFloatMethod(obj, clazz, methodID, args);
#endif
    va_end(args);
}
jfloat CallNonvirtualFloatMethodV(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                  va_list args)
{
#ifndef __cplusplus
    return (*env)->CallNonvirtualFloatMethodV(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualFloatMethodV(obj, clazz, methodID, args);
#endif
}
jfloat CallNonvirtualFloatMethodA(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                  jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallNonvirtualFloatMethodA(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualFloatMethodA(obj, clazz, methodID, args);
#endif
}

jdouble CallNonvirtualDoubleMethod(JEnv *env, jobj obj, jclz clazz, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallNonvirtualDoubleMethod(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualDoubleMethod(obj, clazz, methodID, args);
#endif
    va_end(args);
}
jdouble CallNonvirtualDoubleMethodV(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                    va_list args)
{
#ifndef __cplusplus
    return (*env)->CallNonvirtualDoubleMethodV(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualDoubleMethodV(obj, clazz, methodID, args);
#endif
}
jdouble CallNonvirtualDoubleMethodA(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                                    jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallNonvirtualDoubleMethodA(env, obj, clazz, methodID, args);
#else
    return env->CallNonvirtualDoubleMethodA(obj, clazz, methodID, args);
#endif
}

void CallNonvirtualVoidMethod(JEnv *env, jobj obj, jclz clazz, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    (*env)->CallNonvirtualVoidMethod(env, obj, clazz, methodID, args);
#else
    env->CallNonvirtualVoidMethod(obj, clazz, methodID, args);
#endif
    va_end(args);
}
void CallNonvirtualVoidMethodV(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                               va_list args)
{
#ifndef __cplusplus
    (*env)->CallNonvirtualVoidMethodV(env, obj, clazz, methodID, args);
#else
    env->CallNonvirtualVoidMethodV(obj, clazz, methodID, args);
#endif
}
void CallNonvirtualVoidMethodA(JEnv *env, jobj obj, jclz clazz, jmID methodID,
                               jvalcon args)
{
#ifndef __cplusplus
    (*env)->CallNonvirtualVoidMethodA(env, obj, clazz, methodID, args);
#else
    env->CallNonvirtualVoidMethodA(obj, clazz, methodID, args);
#endif
}

jfID GetFieldID(JEnv *env, jclz clazz, cstr name, cstr sig)
{
#ifndef __cplusplus
    return (*env)->GetFieldID(env, clazz, name, sig);
#else
    return env->GetFieldID(clazz, name, sig);
#endif
}

jobj GetObjectField(JEnv *env, jobj obj, jfID fieldID)
{
#ifndef __cplusplus
    return (*env)->GetObjectField(env, obj, fieldID);
#else
    return env->GetObjectField(obj, fieldID);
#endif
}
jbool GetBooleanField(JEnv *env, jobj obj, jfID fieldID)
{
#ifndef __cplusplus
    return (*env)->GetBooleanField(env, obj, fieldID);
#else
    return env->GetBooleanField(obj, fieldID);
#endif
}
jbyte GetByteField(JEnv *env, jobj obj, jfID fieldID)
{
#ifndef __cplusplus
    return (*env)->GetByteField(env, obj, fieldID);
#else
    return env->GetByteField(obj, fieldID);
#endif
}
jchar GetCharField(JEnv *env, jobj obj, jfID fieldID)
{
#ifndef __cplusplus
    return (*env)->GetCharField(env, obj, fieldID);
#else
    return env->GetCharField(obj, fieldID);
#endif
}
jshort GetShortField(JEnv *env, jobj obj, jfID fieldID)
{
#ifndef __cplusplus
    return (*env)->GetShortField(env, obj, fieldID);
#else
    return env->GetShortField(obj, fieldID);
#endif
}
jint GetIntField(JEnv *env, jobj obj, jfID fieldID)
{
#ifndef __cplusplus
    return (*env)->GetIntField(env, obj, fieldID);
#else
    return env->GetIntField(obj, fieldID);
#endif
}
jlong GetLongField(JEnv *env, jobj obj, jfID fieldID)
{
#ifndef __cplusplus
    return (*env)->GetLongField(env, obj, fieldID);
#else
    return env->GetLongField(obj, fieldID);
#endif
}
jfloat GetFloatField(JEnv *env, jobj obj, jfID fieldID)
{
#ifndef __cplusplus
    return (*env)->GetFloatField(env, obj, fieldID);
#else
    return env->GetFloatField(obj, fieldID);
#endif
}
jdouble GetDoubleField(JEnv *env, jobj obj, jfID fieldID)
{
#ifndef __cplusplus
    return (*env)->GetDoubleField(env, obj, fieldID);
#else
    return env->GetDoubleField(obj, fieldID);
#endif
}

void SetObjectField(JEnv *env, jobj obj, jfID fieldID, jobj val)
{
#ifndef __cplusplus
    (*env)->SetObjectField(env, obj, fieldID, val);
#else
    env->SetObjectField(obj, fieldID, val);
#endif
}
void SetBooleanField(JEnv *env, jobj obj, jfID fieldID, jbool val)
{
#ifndef __cplusplus
    (*env)->SetBooleanField(env, obj, fieldID, val);
#else
    env->SetBooleanField(obj, fieldID, val);
#endif
}
void SetByteField(JEnv *env, jobj obj, jfID fieldID, jbyte val)
{
#ifndef __cplusplus
    (*env)->SetByteField(env, obj, fieldID, val);
#else
    env->SetByteField(obj, fieldID, val);
#endif
}
void SetCharField(JEnv *env, jobj obj, jfID fieldID, jchar val)
{
#ifndef __cplusplus
    (*env)->SetCharField(env, obj, fieldID, val);
#else
    env->SetCharField(obj, fieldID, val);
#endif
}
void SetShortField(JEnv *env, jobj obj, jfID fieldID, jshort val)
{
#ifndef __cplusplus
    (*env)->SetShortField(env, obj, fieldID, val);
#else
    env->SetShortField(obj, fieldID, val);
#endif
}
void SetIntField(JEnv *env, jobj obj, jfID fieldID, jint val)
{
#ifndef __cplusplus
    (*env)->SetIntField(env, obj, fieldID, val);
#else
    env->SetIntField(obj, fieldID, val);
#endif
}
void SetLongField(JEnv *env, jobj obj, jfID fieldID, jlong val)
{
#ifndef __cplusplus
    (*env)->SetLongField(env, obj, fieldID, val);
#else
    env->SetLongField(obj, fieldID, val);
#endif
}
void SetFloatField(JEnv *env, jobj obj, jfID fieldID, jfloat val)
{
#ifndef __cplusplus
    (*env)->SetFloatField(env, obj, fieldID, val);
#else
    env->SetFloatField(obj, fieldID, val);
#endif
}
void SetDoubleField(JEnv *env, jobj obj, jfID fieldID, jdouble val)
{
#ifndef __cplusplus
    (*env)->SetDoubleField(env, obj, fieldID, val);
#else
    env->SetDoubleField(obj, fieldID, val);
#endif
}

jmID GetStaticMethodID(JEnv *env, jclz clazz, cstr name, cstr sig)
{
#ifndef __cplusplus
    return (*env)->GetStaticMethodID(env, clazz, name, sig);
#else
    return env->GetStaticMethodID(clazz, name, sig);
#endif
}

jobj CallStaticObjectMethod(JEnv *env, jclz clazz, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallStaticObjectMethod(env, clazz, methodID, args);
#else
    return env->CallStaticObjectMethod(clazz, methodID, args);
#endif
    va_end(args);
}
jobj CallStaticObjectMethodV(JEnv *env, jclz clazz, jmID methodID, va_list args)
{
#ifndef __cplusplus
    return (*env)->CallStaticObjectMethodV(env, clazz, methodID, args);
#else
    return env->CallStaticObjectMethodV(clazz, methodID, args);
#endif
}
jobj CallStaticObjectMethodA(JEnv *env, jclz clazz, jmID methodID, jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallStaticObjectMethodA(env, clazz, methodID, args);
#else
    return env->CallStaticObjectMethodA(clazz, methodID, args);
#endif
}

jbool CallStaticBooleanMethod(JEnv *env, jclz clazz, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallStaticBooleanMethod(env, clazz, methodID, args);
#else
    return env->CallStaticBooleanMethod(clazz, methodID, args);
#endif
    va_end(args);
}
jbool CallStaticBooleanMethodV(JEnv *env, jclz clazz, jmID methodID, va_list args)
{
#ifndef __cplusplus
    return (*env)->CallStaticBooleanMethodV(env, clazz, methodID, args);
#else
    return env->CallStaticBooleanMethodV(clazz, methodID, args);
#endif
}
jbool CallStaticBooleanMethodA(JEnv *env, jclz clazz, jmID methodID, jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallStaticBooleanMethodA(env, clazz, methodID, args);
#else
    return env->CallStaticBooleanMethodA(clazz, methodID, args);
#endif
}

jbyte CallStaticByteMethod(JEnv *env, jclz clazz, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallStaticByteMethod(env, clazz, methodID, args);
#else
    return env->CallStaticByteMethod(clazz, methodID, args);
#endif
    va_end(args);
}
jbyte CallStaticByteMethodV(JEnv *env, jclz clazz, jmID methodID, va_list args)
{
#ifndef __cplusplus
    return (*env)->CallStaticByteMethodV(env, clazz, methodID, args);
#else
    return env->CallStaticByteMethodV(clazz, methodID, args);
#endif
}
jbyte CallStaticByteMethodA(JEnv *env, jclz clazz, jmID methodID, jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallStaticByteMethodA(env, clazz, methodID, args);
#else
    return env->CallStaticByteMethodA(clazz, methodID, args);
#endif
}

jchar CallStaticCharMethod(JEnv *env, jclz clazz, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallStaticCharMethod(env, clazz, methodID, args);
#else
    return env->CallStaticCharMethod(clazz, methodID, args);
#endif
    va_end(args);
}
jchar CallStaticCharMethodV(JEnv *env, jclz clazz, jmID methodID, va_list args)
{
#ifndef __cplusplus
    return (*env)->CallStaticCharMethodV(env, clazz, methodID, args);
#else
    return env->CallStaticCharMethodV(clazz, methodID, args);
#endif
}
jchar CallStaticCharMethodA(JEnv *env, jclz clazz, jmID methodID, jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallStaticCharMethodA(env, clazz, methodID, args);
#else
    return env->CallStaticCharMethodA(clazz, methodID, args);
#endif
}

jshort CallStaticShortMethod(JEnv *env, jclz clazz, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallStaticShortMethod(env, clazz, methodID, args);
#else
    return env->CallStaticShortMethod(clazz, methodID, args);
#endif
    va_end(args);
}
jshort CallStaticShortMethodV(JEnv *env, jclz clazz, jmID methodID, va_list args)
{
#ifndef __cplusplus
    return (*env)->CallStaticShortMethodV(env, clazz, methodID, args);
#else
    return env->CallStaticShortMethodV(clazz, methodID, args);
#endif
}
jshort CallStaticShortMethodA(JEnv *env, jclz clazz, jmID methodID, jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallStaticShortMethodA(env, clazz, methodID, args);
#else
    return env->CallStaticShortMethodA(clazz, methodID, args);
#endif
}

jint CallStaticIntMethod(JEnv *env, jclz clazz, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallStaticIntMethod(env, clazz, methodID, args);
#else
    return env->CallStaticIntMethod(clazz, methodID, args);
#endif
    va_end(args);
}
jint CallStaticIntMethodV(JEnv *env, jclz clazz, jmID methodID, va_list args)
{
#ifndef __cplusplus
    return (*env)->CallStaticIntMethodV(env, clazz, methodID, args);
#else
    return env->CallStaticIntMethodV(clazz, methodID, args);
#endif
}
jint CallStaticIntMethodA(JEnv *env, jclz clazz, jmID methodID, jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallStaticIntMethodA(env, clazz, methodID, args);
#else
    return env->CallStaticIntMethodA(clazz, methodID, args);
#endif
}

jlong CallStaticLongMethod(JEnv *env, jclz clazz, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallStaticLongMethod(env, clazz, methodID, args);
#else
    return env->CallStaticLongMethod(clazz, methodID, args);
#endif
    va_end(args);
}
jlong CallStaticLongMethodV(JEnv *env, jclz clazz, jmID methodID, va_list args)
{
#ifndef __cplusplus
    return (*env)->CallStaticLongMethodV(env, clazz, methodID, args);
#else
    return env->CallStaticLongMethodV(clazz, methodID, args);
#endif
}
jlong CallStaticLongMethodA(JEnv *env, jclz clazz, jmID methodID, jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallStaticLongMethodA(env, clazz, methodID, args);
#else
    return env->CallStaticLongMethodA(clazz, methodID, args);
#endif
}

jfloat CallStaticFloatMethod(JEnv *env, jclz clazz, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallStaticFloatMethod(env, clazz, methodID, args);
#else
    return env->CallStaticFloatMethod(clazz, methodID, args);
#endif
    va_end(args);
}
jfloat CallStaticFloatMethodV(JEnv *env, jclz clazz, jmID methodID, va_list args)
{
#ifndef __cplusplus
    return (*env)->CallStaticFloatMethodV(env, clazz, methodID, args);
#else
    return env->CallStaticFloatMethodV(clazz, methodID, args);
#endif
}
jfloat CallStaticFloatMethodA(JEnv *env, jclz clazz, jmID methodID, jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallStaticFloatMethodA(env, clazz, methodID, args);
#else
    return env->CallStaticFloatMethodA(clazz, methodID, args);
#endif
}

jdouble CallStaticDoubleMethod(JEnv *env, jclz clazz, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    return (*env)->CallStaticDoubleMethod(env, clazz, methodID, args);
#else
    return env->CallStaticDoubleMethod(clazz, methodID, args);
#endif
    va_end(args);
}
jdouble CallStaticDoubleMethodV(JEnv *env, jclz clazz, jmID methodID, va_list args)
{
#ifndef __cplusplus
    return (*env)->CallStaticDoubleMethodV(env, clazz, methodID, args);
#else
    return env->CallStaticDoubleMethodV(clazz, methodID, args);
#endif
}
jdouble CallStaticDoubleMethodA(JEnv *env, jclz clazz, jmID methodID, jvalcon args)
{
#ifndef __cplusplus
    return (*env)->CallStaticDoubleMethodA(env, clazz, methodID, args);
#else
    return env->CallStaticDoubleMethodA(clazz, methodID, args);
#endif
}

void CallStaticVoidMethod(JEnv *env, jclz cls, jmID methodID, ...)
{
    va_list args;
    va_start(args, methodID);
#ifndef __cplusplus
    (*env)->CallStaticVoidMethod(env, cls, methodID, args);
#else
    env->CallStaticVoidMethod(cls, methodID, args);
#endif
    va_end(args);
}
void CallStaticVoidMethodV(JEnv *env, jclz cls, jmID methodID, va_list args)
{
#ifndef __cplusplus
    (*env)->CallStaticVoidMethodV(env, cls, methodID, args);
#else
    env->CallStaticVoidMethodV(cls, methodID, args);
#endif
}
void CallStaticVoidMethodA(JEnv *env, jclz cls, jmID methodID, jvalcon args)
{
#ifndef __cplusplus
    (*env)->CallStaticVoidMethodA(env, cls, methodID, args);
#else
    env->CallStaticVoidMethodA(cls, methodID, args);
#endif
}

jfID GetStaticFieldID(JEnv *env, jclz clazz, cstr name, cstr sig)
{
#ifndef __cplusplus
    return (*env)->GetStaticFieldID(env, clazz, name, sig);
#else
    return env->GetStaticFieldID(clazz, name, sig);
#endif
}
jobj GetStaticObjectField(JEnv *env, jclz clazz, jfID fieldID)
{
#ifndef __cplusplus
    return (*env)->GetStaticObjectField(env, clazz, fieldID);
#else
    return env->GetStaticObjectField(clazz, fieldID);
#endif
}
jbool GetStaticBooleanField(JEnv *env, jclz clazz, jfID fieldID)
{
#ifndef __cplusplus
    return (*env)->GetStaticBooleanField(env, clazz, fieldID);
#else
    return env->GetStaticBooleanField(clazz, fieldID);
#endif
}
jbyte GetStaticByteField(JEnv *env, jclz clazz, jfID fieldID)
{
#ifndef __cplusplus
    return (*env)->GetStaticByteField(env, clazz, fieldID);
#else
    return env->GetStaticByteField(clazz, fieldID);
#endif
}
jchar GetStaticCharField(JEnv *env, jclz clazz, jfID fieldID)
{
#ifndef __cplusplus
    return (*env)->GetStaticCharField(env, clazz, fieldID);
#else
    return env->GetStaticCharField(clazz, fieldID);
#endif
}
jshort GetStaticShortField(JEnv *env, jclz clazz, jfID fieldID)
{
#ifndef __cplusplus
    return (*env)->GetStaticShortField(env, clazz, fieldID);
#else
    return env->GetStaticShortField(clazz, fieldID);
#endif
}
jint GetStaticIntField(JEnv *env, jclz clazz, jfID fieldID)
{
#ifndef __cplusplus
    return (*env)->GetStaticIntField(env, clazz, fieldID);
#else
    return env->GetStaticIntField(clazz, fieldID);
#endif
}
jlong GetStaticLongField(JEnv *env, jclz clazz, jfID fieldID)
{
#ifndef __cplusplus
    return (*env)->GetStaticLongField(env, clazz, fieldID);
#else
    return env->GetStaticLongField(clazz, fieldID);
#endif
}
jfloat GetStaticFloatField(JEnv *env, jclz clazz, jfID fieldID)
{
#ifndef __cplusplus
    return (*env)->GetStaticFloatField(env, clazz, fieldID);
#else
    return env->GetStaticFloatField(clazz, fieldID);
#endif
}
jdouble GetStaticDoubleField(JEnv *env, jclz clazz, jfID fieldID)
{
#ifndef __cplusplus
    return (*env)->GetStaticDoubleField(env, clazz, fieldID);
#else
    return env->GetStaticDoubleField(clazz, fieldID);
#endif
}

void SetStaticObjectField(JEnv *env, jclz clazz, jfID fieldID, jobj value)
{
#ifndef __cplusplus
    (*env)->SetStaticObjectField(env, clazz, fieldID, value);
#else
    env->SetStaticObjectField(clazz, fieldID, value);
#endif
}
void SetStaticBooleanField(JEnv *env, jclz clazz, jfID fieldID, jbool value)
{
#ifndef __cplusplus
    (*env)->SetStaticBooleanField(env, clazz, fieldID, value);
#else
    env->SetStaticBooleanField(clazz, fieldID, value);
#endif
}
void SetStaticByteField(JEnv *env, jclz clazz, jfID fieldID, jbyte value)
{
#ifndef __cplusplus
    (*env)->SetStaticByteField(env, clazz, fieldID, value);
#else
    env->SetStaticByteField(clazz, fieldID, value);
#endif
}
void SetStaticCharField(JEnv *env, jclz clazz, jfID fieldID, jchar value)
{
#ifndef __cplusplus
    (*env)->SetStaticCharField(env, clazz, fieldID, value);
#else
    env->SetStaticCharField(clazz, fieldID, value);
#endif
}
void SetStaticShortField(JEnv *env, jclz clazz, jfID fieldID, jshort value)
{
#ifndef __cplusplus
    (*env)->SetStaticShortField(env, clazz, fieldID, value);
#else
    env->SetStaticShortField(clazz, fieldID, value);
#endif
}
void SetStaticIntField(JEnv *env, jclz clazz, jfID fieldID, jint value)
{
#ifndef __cplusplus
    (*env)->SetStaticIntField(env, clazz, fieldID, value);
#else
    env->SetStaticIntField(clazz, fieldID, value);
#endif
}
void SetStaticLongField(JEnv *env, jclz clazz, jfID fieldID, jlong value)
{
#ifndef __cplusplus
    (*env)->SetStaticLongField(env, clazz, fieldID, value);
#else
    env->SetStaticLongField(clazz, fieldID, value);
#endif
}
void SetStaticFloatField(JEnv *env, jclz clazz, jfID fieldID, jfloat value)
{
#ifndef __cplusplus
    (*env)->SetStaticFloatField(env, clazz, fieldID, value);
#else
    env->SetStaticFloatField(clazz, fieldID, value);
#endif
}
void SetStaticDoubleField(JEnv *env, jclz clazz, jfID fieldID, jdouble value)
{
#ifndef __cplusplus
    (*env)->SetStaticDoubleField(env, clazz, fieldID, value);
#else
    env->SetStaticDoubleField(clazz, fieldID, value);
#endif
}

jstr NewString(JEnv *env, const jchar *unicode, jsize len)
{
#ifndef __cplusplus
    return (*env)->NewString(env, unicode, len);
#else
    return env->NewString(unicode, len);
#endif
}
jsize GetStringLength(JEnv *env, jstr str)
{
#ifndef __cplusplus
    return (*env)->GetStringLength(env, str);
#else
    return env->GetStringLength(str);
#endif
}
const jchar *GetStringChars(JEnv *env, jstr str, jbool *isCopy)
{
#ifndef __cplusplus
    return (*env)->GetStringChars(env, str, isCopy);
#else
    return env->GetStringChars(str, isCopy);
#endif
}
void ReleaseStringChars(JEnv *env, jstr str, const jchar *chars)
{
#ifndef __cplusplus
    (*env)->ReleaseStringChars(env, str, chars);
#else
    env->ReleaseStringChars(str, chars);
#endif
}

jstr NewStringUTF(JEnv *env, cstr utf)
{
#ifndef __cplusplus
    return (*env)->NewStringUTF(env, utf);
#else
    return env->NewStringUTF(utf);
#endif
}
jsize GetStringUTFLength(JEnv *env, jstr str)
{
#ifndef __cplusplus
    return (*env)->GetStringUTFLength(env, str);
#else
    return env->GetStringUTFLength(str);
#endif
}
cstr GetStringUTFChars(JEnv *env, jstr str, jbool *isCopy)
{
#ifndef __cplusplus
    return (*env)->GetStringUTFChars(env, str, isCopy);
#else
    return env->GetStringUTFChars(str, isCopy);
#endif
}
void ReleaseStringUTFChars(JEnv *env, jstr str, cstr chars)
{
#ifndef __cplusplus
    (*env)->ReleaseStringUTFChars(env, str, chars);
#else
    env->ReleaseStringUTFChars(str, chars);
#endif
}

jsize GetArrayLength(JEnv *env, jarr array)
{
#ifndef __cplusplus
    return (*env)->GetArrayLength(env, array);
#else
    return env->GetArrayLength(array);
#endif
}

jobjArr NewObjectArray(JEnv *env, jsize len, jclz clazz, jobj init)
{
#ifndef __cplusplus
    return (*env)->NewObjectArray(env, len, clazz, init);
#else
    return env->NewObjectArray(len, clazz, init);
#endif
}
jobj GetObjectArrayElement(JEnv *env, jobjArr array, jsize index)
{
#ifndef __cplusplus
    return (*env)->GetObjectArrayElement(env, array, index);
#else
    return env->GetObjectArrayElement(array, index);
#endif
}
void SetObjectArrayElement(JEnv *env, jobjArr array, jsize index, jobj val)
{
#ifndef __cplusplus
    (*env)->SetObjectArrayElement(env, array, index, val);
#else
    env->SetObjectArrayElement(array, index, val);
#endif
}

jboolArr NewBooleanArray(JEnv *env, jsize len)
{
#ifndef __cplusplus
    return (*env)->NewBooleanArray(env, len);
#else
    return env->NewBooleanArray(len);
#endif
}
jbyteArr NewByteArray(JEnv *env, jsize len)
{
#ifndef __cplusplus
    return (*env)->NewByteArray(env, len);
#else
    return env->NewByteArray(len);
#endif
}
jcharArr NewCharArray(JEnv *env, jsize len)
{
#ifndef __cplusplus
    return (*env)->NewCharArray(env, len);
#else
    return env->NewCharArray(len);
#endif
}
jshortArr NewShortArray(JEnv *env, jsize len)
{
#ifndef __cplusplus
    return (*env)->NewShortArray(env, len);
#else
    return env->NewShortArray(len);
#endif
}
jintArr NewIntArray(JEnv *env, jsize len)
{
#ifndef __cplusplus
    return (*env)->NewIntArray(env, len);
#else
    return env->NewIntArray(len);
#endif
}
jlongArr NewLongArray(JEnv *env, jsize len)
{
#ifndef __cplusplus
    return (*env)->NewLongArray(env, len);
#else
    return env->NewLongArray(len);
#endif
}
jfloatArr NewFloatArray(JEnv *env, jsize len)
{
#ifndef __cplusplus
    return (*env)->NewFloatArray(env, len);
#else
    return env->NewFloatArray(len);
#endif
}
jdoubleArr NewDoubleArray(JEnv *env, jsize len)
{
#ifndef __cplusplus
    return (*env)->NewDoubleArray(env, len);
#else
    return env->NewDoubleArray(len);
#endif
}

jbool *GetBooleanArrayElements(JEnv *env, jboolArr array, jbool *isCopy)
{
#ifndef __cplusplus
    return (*env)->GetBooleanArrayElements(env, array, isCopy);
#else
    return env->GetBooleanArrayElements(array, isCopy);
#endif
}
jbyte *GetByteArrayElements(JEnv *env, jbyteArr array, jbool *isCopy)
{
#ifndef __cplusplus
    return (*env)->GetByteArrayElements(env, array, isCopy);
#else
    return env->GetByteArrayElements(array, isCopy);
#endif
}
jchar *GetCharArrayElements(JEnv *env, jcharArr array, jbool *isCopy)
{
#ifndef __cplusplus
    return (*env)->GetCharArrayElements(env, array, isCopy);
#else
    return env->GetCharArrayElements(array, isCopy);
#endif
}
jshort *GetShortArrayElements(JEnv *env, jshortArr array, jbool *isCopy)
{
#ifndef __cplusplus
    return (*env)->GetShortArrayElements(env, array, isCopy);
#else
    return env->GetShortArrayElements(array, isCopy);
#endif
}
jint *GetIntArrayElements(JEnv *env, jintArr array, jbool *isCopy)
{
#ifndef __cplusplus
    return (*env)->GetIntArrayElements(env, array, isCopy);
#else
    return env->GetIntArrayElements(array, isCopy);
#endif
}
jlong *GetLongArrayElements(JEnv *env, jlongArr array, jbool *isCopy)
{
#ifndef __cplusplus
    return (*env)->GetLongArrayElements(env, array, isCopy);
#else
    return env->GetLongArrayElements(array, isCopy);
#endif
}
jfloat *GetFloatArrayElements(JEnv *env, jfloatArr array, jbool *isCopy)
{
#ifndef __cplusplus
    return (*env)->GetFloatArrayElements(env, array, isCopy);
#else
    return env->GetFloatArrayElements(array, isCopy);
#endif
}
jdouble *GetDoubleArrayElements(JEnv *env, jdoubleArr array, jbool *isCopy)
{
#ifndef __cplusplus
    return (*env)->GetDoubleArrayElements(env, array, isCopy);
#else
    return env->GetDoubleArrayElements(array, isCopy);
#endif
}

void ReleaseBooleanArrayElements(JEnv *env, jboolArr array, jbool *elems, jint mode)
{
#ifndef __cplusplus
    (*env)->ReleaseBooleanArrayElements(env, array, elems, mode);
#else
    env->ReleaseBooleanArrayElements(array, elems, mode);
#endif
}
void ReleaseByteArrayElements(JEnv *env, jbyteArr array, jbyte *elems, jint mode)
{
#ifndef __cplusplus
    (*env)->ReleaseByteArrayElements(env, array, elems, mode);
#else
    env->ReleaseByteArrayElements(array, elems, mode);
#endif
}
void ReleaseCharArrayElements(JEnv *env, jcharArr array, jchar *elems, jint mode)
{
#ifndef __cplusplus
    (*env)->ReleaseCharArrayElements(env, array, elems, mode);
#else
    env->ReleaseCharArrayElements(array, elems, mode);
#endif
}
void ReleaseShortArrayElements(JEnv *env, jshortArr array, jshort *elems, jint mode)
{
#ifndef __cplusplus
    (*env)->ReleaseShortArrayElements(env, array, elems, mode);
#else
    env->ReleaseShortArrayElements(array, elems, mode);
#endif
}
void ReleaseIntArrayElements(JEnv *env, jintArr array, jint *elems, jint mode)
{
#ifndef __cplusplus
    (*env)->ReleaseIntArrayElements(env, array, elems, mode);
#else
    env->ReleaseIntArrayElements(array, elems, mode);
#endif
}
void ReleaseLongArrayElements(JEnv *env, jlongArr array, jlong *elems, jint mode)
{
#ifndef __cplusplus
    (*env)->ReleaseLongArrayElements(env, array, elems, mode);
#else
    env->ReleaseLongArrayElements(array, elems, mode);
#endif
}
void ReleaseFloatArrayElements(JEnv *env, jfloatArr array, jfloat *elems, jint mode)
{
#ifndef __cplusplus
    (*env)->ReleaseFloatArrayElements(env, array, elems, mode);
#else
    env->ReleaseFloatArrayElements(array, elems, mode);
#endif
}
void ReleaseDoubleArrayElements(JEnv *env, jdoubleArr array, jdouble *elems, jint mode)
{
#ifndef __cplusplus
    (*env)->ReleaseDoubleArrayElements(env, array, elems, mode);
#else
    env->ReleaseDoubleArrayElements(array, elems, mode);
#endif
}

void GetBooleanArrayRegion(JEnv *env, jboolArr array, jsize start, jsize l, jbool *buf)
{
#ifndef __cplusplus
    (*env)->GetBooleanArrayRegion(env, array, start, l, buf);
#else
    env->GetBooleanArrayRegion(array, start, l, buf);
#endif
}
void GetByteArrayRegion(JEnv *env, jbyteArr array, jsize start, jsize len, jbyte *buf)
{
#ifndef __cplusplus
    (*env)->GetByteArrayRegion(env, array, start, len, buf);
#else
    env->GetByteArrayRegion(array, start, len, buf);
#endif
}
void GetCharArrayRegion(JEnv *env, jcharArr array, jsize start, jsize len, jchar *buf)
{
#ifndef __cplusplus
    (*env)->GetCharArrayRegion(env, array, start, len, buf);
#else
    env->GetCharArrayRegion(array, start, len, buf);
#endif
}
void GetShortArrayRegion(JEnv *env, jshortArr array, jsize start, jsize len, jshort *buf)
{
#ifndef __cplusplus
    (*env)->GetShortArrayRegion(env, array, start, len, buf);
#else
    env->GetShortArrayRegion(array, start, len, buf);
#endif
}
void GetIntArrayRegion(JEnv *env, jintArr array, jsize start, jsize len, jint *buf)
{
#ifndef __cplusplus
    (*env)->GetIntArrayRegion(env, array, start, len, buf);
#else
    env->GetIntArrayRegion(array, start, len, buf);
#endif
}
void GetLongArrayRegion(JEnv *env, jlongArr array, jsize start, jsize len, jlong *buf)
{
#ifndef __cplusplus
    (*env)->GetLongArrayRegion(env, array, start, len, buf);
#else
    env->GetLongArrayRegion(array, start, len, buf);
#endif
}
void GetFloatArrayRegion(JEnv *env, jfloatArr array, jsize start, jsize len, jfloat *buf)
{
#ifndef __cplusplus
    (*env)->GetFloatArrayRegion(env, array, start, len, buf);
#else
    env->GetFloatArrayRegion(array, start, len, buf);
#endif
}
void GetDoubleArrayRegion(JEnv *env, jdoubleArr array, jsize start, jsize len, jdouble *buf)
{
#ifndef __cplusplus
    (*env)->GetDoubleArrayRegion(env, array, start, len, buf);
#else
    env->GetDoubleArrayRegion(array, start, len, buf);
#endif
}

void SetBooleanArrayRegion(JEnv *env, jboolArr array, jsize start, jsize l, const jbool *buf)
{
#ifndef __cplusplus
    (*env)->SetBooleanArrayRegion(env, array, start, l, buf);
#else
    env->SetBooleanArrayRegion(array, start, l, buf);
#endif
}
void SetByteArrayRegion(JEnv *env, jbyteArr array, jsize start, jsize len, const jbyte *buf)
{
#ifndef __cplusplus
    (*env)->SetByteArrayRegion(env, array, start, len, buf);
#else
    env->SetByteArrayRegion(array, start, len, buf);
#endif
}
void SetCharArrayRegion(JEnv *env, jcharArr array, jsize start, jsize len, const jchar *buf)
{
#ifndef __cplusplus
    (*env)->SetCharArrayRegion(env, array, start, len, buf);
#else
    env->SetCharArrayRegion(array, start, len, buf);
#endif
}
void SetShortArrayRegion(JEnv *env, jshortArr array, jsize start, jsize len, const jshort *buf)
{
#ifndef __cplusplus
    (*env)->SetShortArrayRegion(env, array, start, len, buf);
#else
    env->SetShortArrayRegion(array, start, len, buf);
#endif
}
void SetIntArrayRegion(JEnv *env, jintArr array, jsize start, jsize len, const jint *buf)
{
#ifndef __cplusplus
    (*env)->SetIntArrayRegion(env, array, start, len, buf);
#else
    env->SetIntArrayRegion(array, start, len, buf);
#endif
}
void SetLongArrayRegion(JEnv *env, jlongArr array, jsize start, jsize len, const jlong *buf)
{
#ifndef __cplusplus
    (*env)->SetLongArrayRegion(env, array, start, len, buf);
#else
    env->SetLongArrayRegion(array, start, len, buf);
#endif
}
void SetFloatArrayRegion(JEnv *env, jfloatArr array, jsize start, jsize len, const jfloat *buf)
{
#ifndef __cplusplus
    (*env)->SetFloatArrayRegion(env, array, start, len, buf);
#else
    env->SetFloatArrayRegion(array, start, len, buf);
#endif
}
void SetDoubleArrayRegion(JEnv *env, jdoubleArr array, jsize start, jsize len, const jdouble *buf)
{
#ifndef __cplusplus
    (*env)->SetDoubleArrayRegion(env, array, start, len, buf);
#else
    env->SetDoubleArrayRegion(array, start, len, buf);
#endif
}

jint RegisterNatives(JEnv *env, jclz clazz, const JNINativeMethod *methods,
                     jint nMethods)
{
#ifndef __cplusplus
    return (*env)->RegisterNatives(env, clazz, methods, nMethods);
#else
    return env->RegisterNatives(clazz, methods, nMethods);
#endif
}
jint UnregisterNatives(JEnv *env, jclz clazz)
{
#ifndef __cplusplus
    return (*env)->UnregisterNatives(env, clazz);
#else
    return env->UnregisterNatives(clazz);
#endif
}

jint MonitorEnter(JEnv *env, jobj obj)
{
#ifndef __cplusplus
    return (*env)->MonitorEnter(env, obj);
#else
    return env->MonitorEnter(obj);
#endif
}
jint MonitorExit(JEnv *env, jobj obj)
{
#ifndef __cplusplus
    return (*env)->MonitorExit(env, obj);
#else
    return env->MonitorExit(obj);
#endif
}

jint GetJavaVM(JEnv *env, JVM **vm)
{
#ifndef __cplusplus
    return (*env)->GetJavaVM(env, vm);
#else
    return env->GetJavaVM(vm);
#endif
}

void GetStringRegion(JEnv *env, jstr str, jsize start, jsize len, jchar *buf)
{
#ifndef __cplusplus
    (*env)->GetStringRegion(env, str, start, len, buf);
#else
    env->GetStringRegion(str, start, len, buf);
#endif
}
void GetStringUTFRegion(JEnv *env, jstr str, jsize start, jsize len, char *buf)
{
#ifndef __cplusplus
    (*env)->GetStringUTFRegion(env, str, start, len, buf);
#else
    env->GetStringUTFRegion(str, start, len, buf);
#endif
}

void *GetPrimitiveArrayCritical(JEnv *env, jarr array, jbool *isCopy)
{
#ifndef __cplusplus
    return (*env)->GetPrimitiveArrayCritical(env, array, isCopy);
#else
    return env->GetPrimitiveArrayCritical(array, isCopy);
#endif
}
void ReleasePrimitiveArrayCritical(JEnv *env, jarr array, void *carray, jint mode)
{
#ifndef __cplusplus
    (*env)->ReleasePrimitiveArrayCritical(env, array, carray, mode);
#else
    env->ReleasePrimitiveArrayCritical(array, carray, mode);
#endif
}

const jchar *GetStringCritical(JEnv *env, jstr string, jbool *isCopy)
{
#ifndef __cplusplus
    return (*env)->GetStringCritical(env, string, isCopy);
#else
    return env->GetStringCritical(string, isCopy);
#endif
}
void ReleaseStringCritical(JEnv *env, jstr string, const jchar *cstring)
{
#ifndef __cplusplus
    (*env)->ReleaseStringCritical(env, string, cstring);
#else
    env->ReleaseStringCritical(string, cstring);
#endif
}

jweak NewWeakGlobalRef(JEnv *env, jobj obj)
{
#ifndef __cplusplus
    return (*env)->NewWeakGlobalRef(env, obj);
#else
    return env->NewWeakGlobalRef(obj);
#endif
}
void DeleteWeakGlobalRef(JEnv *env, jweak ref)
{
#ifndef __cplusplus
    (*env)->DeleteWeakGlobalRef(env, ref);
#else
    env->DeleteWeakGlobalRef(ref);
#endif
}

jbool ExceptionCheck(JEnv *env)
{
#ifndef __cplusplus
    return (*env)->ExceptionCheck(env);
#else
    return env->ExceptionCheck();
#endif
}

jobj NewDirectByteBuffer(JEnv *env, void *address, jlong capacity)
{
#ifndef __cplusplus
    return (*env)->NewDirectByteBuffer(env, address, capacity);
#else
    return env->NewDirectByteBuffer(address, capacity);
#endif
}
void *GetDirectBufferAddress(JEnv *env, jobj buf)
{
#ifndef __cplusplus
    return (*env)->GetDirectBufferAddress(env, buf);
#else
    return env->GetDirectBufferAddress(buf);
#endif
}
jlong GetDirectBufferCapacity(JEnv *env, jobj buf)
{
#ifndef __cplusplus
    return (*env)->GetDirectBufferCapacity(env, buf);
#else
    return env->GetDirectBufferCapacity(buf);
#endif
}

/* New JNI 1.6 Features */

jobjRefT GetObjectRefType(JEnv *env, jobj obj)
{
#ifndef __cplusplus
    return (*env)->GetObjectRefType(env, obj);
#else
    return env->GetObjectRefType(obj);
#endif
}

/* Module Features */

jobj GetModule(JEnv *env, jclz clazz)
{
#ifndef __cplusplus
    return (*env)->GetModule(env, clazz);
#else
    return env->GetModule(clazz);
#endif
}

/* End VM-specific. */

jint DestroyJavaVM(JVM *vm)
{
#ifndef __cplusplus
    return (*vm)->DestroyJavaVM(vm);
#else
    return vm->DestroyJavaVM();
#endif
}

jint AttachCurrentThread(JVM *vm, void **penv, void *args)
{
#ifndef __cplusplus
    return (*vm)->AttachCurrentThread(vm, penv, args);
#else
    return vm->AttachCurrentThread(penv, args);
#endif
}

jint DetachCurrentThread(JVM *vm)
{
#ifndef __cplusplus
    return (*vm)->DetachCurrentThread(vm);
#else
    return vm->DetachCurrentThread();
#endif
}

jint GetEnv(JVM *vm, void **penv, jint version)
{
#ifndef __cplusplus
    return (*vm)->GetEnv(vm, penv, version);
#else
    return vm->GetEnv(penv, version);
#endif
}

jint AttachCurrentThreadAsDaemon(JVM *vm, void **penv, void *args)
{
#ifndef __cplusplus
    return (*vm)->AttachCurrentThreadAsDaemon(vm, penv, args);
#else
    return vm->AttachCurrentThreadAsDaemon(penv, args);
#endif
}

#ifdef __cplusplus
}
#endif

#endif /* _JNIVK_H */