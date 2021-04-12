/**
 * Created at 2020/7/22 11:28.
 *
 * @author Liangcheng Juves
 */
#ifndef _JNIX_H
#define _JNIX_H

#include <jni.h>

#ifdef __cplusplus
extern "C"
{
#endif /* __cplusplus */

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

    // Replace type regex
    // (?<!(typedef )|(\*[^(*,)]*)|(\/\/.*))jboolean(?!Array)

    typedef char const *cstr;

    /* Primitive types that match up with Java equivalents. */
    typedef jboolean jbool; /* unsigned 8 bits */

    /* Reference types. */
    typedef jobject jobj;
    typedef jclass jcls;
    typedef jstring jstr;
    typedef jarray jarr;
    typedef jobjectArray jobjArr;
    typedef jbooleanArray jboolArr;
    typedef jbyteArray jbyteArr;
    typedef jcharArray jcharArr;
    typedef jshortArray jshortArr;
    typedef jintArray jintArr;
    typedef jlongArray jlongArr;
    typedef jfloatArray jfloatArr;
    typedef jdoubleArray jdoubleArr;
    typedef jthrowable jtr;

    typedef jfieldID jfID;  /* field IDs */
    typedef jmethodID jmID; /* method IDs */

    typedef jvalue jval;

    typedef jobjectRefType jobjRefT;

    typedef JavaVM JVM;

#ifdef __cplusplus
    namespace JNIX
    {
#endif /* __cplusplus */

        jint GetVersion();

        jcls DefineClass(cstr, jobj, const jbyte *,
                         jsize);
        jcls FindClass(cstr);

        jmID FromReflectedMethod(jobj);
        jfID FromReflectedField(jobj);
        /* spec doesn't show jboolean parameter */
        jobj ToReflectedMethod(jcls, jmID, jbool);

        jcls GetSuperclass(jcls);
        jbool IsAssignableFrom(jcls, jcls);

        /* spec doesn't show jboolean parameter */
        jobj ToReflectedField(jcls, jfID, jbool);

        jint Throw(jtr);
        jint ThrowNew(jcls, cstr);
        jtr ExceptionOccurred();
        void ExceptionDescribe();
        void ExceptionClear();
        void FatalError(cstr);

        jint PushLocalFrame(jint);
        jobj PopLocalFrame(jobj);

        jobj NewGlobalRef(jobj);
        void DeleteGlobalRef(jobj);
        void DeleteLocalRef(jobj);
        jbool IsSameObject(jobj, jobj);

        jobj NewLocalRef(jobj);
        jint EnsureLocalCapacity(jint);

        jobj AllocObject(jcls);
        jobj NewObject(jcls, jmID, ...);
        jobj NewObjectV(jcls, jmID, va_list);
        jobj NewObjectA(jcls, jmID, const jval *);

        jcls GetObjectClass(jobj);
        jbool IsInstanceOf(jobj, jcls);
        jmID GetMethodID(jcls, cstr, cstr);

        jobj CallObjectMethod(jobj, jmID, ...);
        jobj CallObjectMethodV(jobj, jmID, va_list);
        jobj CallObjectMethodA(jobj, jmID, const jval *);
        jbool CallBooleanMethod(jobj, jmID, ...);
        jbool CallBooleanMethodV(jobj, jmID, va_list);
        jbool CallBooleanMethodA(jobj, jmID, const jval *);
        jbyte CallByteMethod(jobj, jmID, ...);
        jbyte CallByteMethodV(jobj, jmID, va_list);
        jbyte CallByteMethodA(jobj, jmID, const jval *);
        jchar CallCharMethod(jobj, jmID, ...);
        jchar CallCharMethodV(jobj, jmID, va_list);
        jchar CallCharMethodA(jobj, jmID, const jval *);
        jshort CallShortMethod(jobj, jmID, ...);
        jshort CallShortMethodV(jobj, jmID, va_list);
        jshort CallShortMethodA(jobj, jmID, const jval *);
        jint CallIntMethod(jobj, jmID, ...);
        jint CallIntMethodV(jobj, jmID, va_list);
        jint CallIntMethodA(jobj, jmID, const jval *);
        jlong CallLongMethod(jobj, jmID, ...);
        jlong CallLongMethodV(jobj, jmID, va_list);
        jlong CallLongMethodA(jobj, jmID, const jval *);
        jfloat CallFloatMethod(jobj, jmID, ...);
        jfloat CallFloatMethodV(jobj, jmID, va_list);
        jfloat CallFloatMethodA(jobj, jmID, const jval *);
        jdouble CallDoubleMethod(jobj, jmID, ...);
        jdouble CallDoubleMethodV(jobj, jmID, va_list);
        jdouble CallDoubleMethodA(jobj, jmID, const jval *);
        void CallVoidMethod(jobj, jmID, ...);
        void CallVoidMethodV(jobj, jmID, va_list);
        void CallVoidMethodA(jobj, jmID, const jval *);

        jobj CallNonvirtualObjectMethod(jobj, jcls,
                                        jmID, ...);
        jobj CallNonvirtualObjectMethodV(jobj, jcls,
                                         jmID, va_list);
        jobj CallNonvirtualObjectMethodA(jobj, jcls,
                                         jmID, const jval *);
        jbool CallNonvirtualBooleanMethod(jobj, jcls,
                                          jmID, ...);
        jbool CallNonvirtualBooleanMethodV(jobj, jcls,
                                           jmID, va_list);
        jbool CallNonvirtualBooleanMethodA(jobj, jcls,
                                           jmID, const jval *);
        jbyte CallNonvirtualByteMethod(jobj, jcls,
                                       jmID, ...);
        jbyte CallNonvirtualByteMethodV(jobj, jcls,
                                        jmID, va_list);
        jbyte CallNonvirtualByteMethodA(jobj, jcls,
                                        jmID, const jval *);
        jchar CallNonvirtualCharMethod(jobj, jcls,
                                       jmID, ...);
        jchar CallNonvirtualCharMethodV(jobj, jcls,
                                        jmID, va_list);
        jchar CallNonvirtualCharMethodA(jobj, jcls,
                                        jmID, const jval *);
        jshort CallNonvirtualShortMethod(jobj, jcls,
                                         jmID, ...);
        jshort CallNonvirtualShortMethodV(jobj, jcls,
                                          jmID, va_list);
        jshort CallNonvirtualShortMethodA(jobj, jcls,
                                          jmID, const jval *);
        jint CallNonvirtualIntMethod(jobj, jcls,
                                     jmID, ...);
        jint CallNonvirtualIntMethodV(jobj, jcls,
                                      jmID, va_list);
        jint CallNonvirtualIntMethodA(jobj, jcls,
                                      jmID, const jval *);
        jlong CallNonvirtualLongMethod(jobj, jcls,
                                       jmID, ...);
        jlong CallNonvirtualLongMethodV(jobj, jcls,
                                        jmID, va_list);
        jlong CallNonvirtualLongMethodA(jobj, jcls,
                                        jmID, const jval *);
        jfloat CallNonvirtualFloatMethod(jobj, jcls,
                                         jmID, ...);
        jfloat CallNonvirtualFloatMethodV(jobj, jcls,
                                          jmID, va_list);
        jfloat CallNonvirtualFloatMethodA(jobj, jcls,
                                          jmID, const jval *);
        jdouble CallNonvirtualDoubleMethod(jobj, jcls,
                                           jmID, ...);
        jdouble CallNonvirtualDoubleMethodV(jobj, jcls,
                                            jmID, va_list);
        jdouble CallNonvirtualDoubleMethodA(jobj, jcls,
                                            jmID, const jval *);
        void CallNonvirtualVoidMethod(jobj, jcls,
                                      jmID, ...);
        void CallNonvirtualVoidMethodV(jobj, jcls,
                                       jmID, va_list);
        void CallNonvirtualVoidMethodA(jobj, jcls,
                                       jmID, const jval *);

        jfID GetFieldID(jcls, cstr, cstr);

        jobj GetObjectField(jobj, jfID);
        jbool GetBooleanField(jobj, jfID);
        jbyte GetByteField(jobj, jfID);
        jchar GetCharField(jobj, jfID);
        jshort GetShortField(jobj, jfID);
        jint GetIntField(jobj, jfID);
        jlong GetLongField(jobj, jfID);
        jfloat GetFloatField(jobj, jfID);
        jdouble GetDoubleField(jobj, jfID);

        void SetObjectField(jobj, jfID, jobj);
        void SetBooleanField(jobj, jfID, jbool);
        void SetByteField(jobj, jfID, jbyte);
        void SetCharField(jobj, jfID, jchar);
        void SetShortField(jobj, jfID, jshort);
        void SetIntField(jobj, jfID, jint);
        void SetLongField(jobj, jfID, jlong);
        void SetFloatField(jobj, jfID, jfloat);
        void SetDoubleField(jobj, jfID, jdouble);

        jmID GetStaticMethodID(jcls, cstr, cstr);

        jobj CallStaticObjectMethod(jcls, jmID, ...);
        jobj CallStaticObjectMethodV(jcls, jmID, va_list);
        jobj CallStaticObjectMethodA(jcls, jmID, const jval *);
        jbool CallStaticBooleanMethod(jcls, jmID, ...);
        jbool CallStaticBooleanMethodV(jcls, jmID,
                                       va_list);
        jbool CallStaticBooleanMethodA(jcls, jmID, const jval *);
        jbyte CallStaticByteMethod(jcls, jmID, ...);
        jbyte CallStaticByteMethodV(jcls, jmID, va_list);
        jbyte CallStaticByteMethodA(jcls, jmID, const jval *);
        jchar CallStaticCharMethod(jcls, jmID, ...);
        jchar CallStaticCharMethodV(jcls, jmID, va_list);
        jchar CallStaticCharMethodA(jcls, jmID, const jval *);
        jshort CallStaticShortMethod(jcls, jmID, ...);
        jshort CallStaticShortMethodV(jcls, jmID, va_list);
        jshort CallStaticShortMethodA(jcls, jmID, const jval *);
        jint CallStaticIntMethod(jcls, jmID, ...);
        jint CallStaticIntMethodV(jcls, jmID, va_list);
        jint CallStaticIntMethodA(jcls, jmID, const jval *);
        jlong CallStaticLongMethod(jcls, jmID, ...);
        jlong CallStaticLongMethodV(jcls, jmID, va_list);
        jlong CallStaticLongMethodA(jcls, jmID, const jval *);
        jfloat CallStaticFloatMethod(jcls, jmID, ...);
        jfloat CallStaticFloatMethodV(jcls, jmID, va_list);
        jfloat CallStaticFloatMethodA(jcls, jmID, const jval *);
        jdouble CallStaticDoubleMethod(jcls, jmID, ...);
        jdouble CallStaticDoubleMethodV(jcls, jmID, va_list);
        jdouble CallStaticDoubleMethodA(jcls, jmID, const jval *);
        void CallStaticVoidMethod(jcls, jmID, ...);
        void CallStaticVoidMethodV(jcls, jmID, va_list);
        void CallStaticVoidMethodA(jcls, jmID, const jval *);

        jfID GetStaticFieldID(jcls, cstr,
                              cstr);

        jobj GetStaticObjectField(jcls, jfID);
        jbool GetStaticBooleanField(jcls, jfID);
        jbyte GetStaticByteField(jcls, jfID);
        jchar GetStaticCharField(jcls, jfID);
        jshort GetStaticShortField(jcls, jfID);
        jint GetStaticIntField(jcls, jfID);
        jlong GetStaticLongField(jcls, jfID);
        jfloat GetStaticFloatField(jcls, jfID);
        jdouble GetStaticDoubleField(jcls, jfID);

        void SetStaticObjectField(jcls, jfID, jobj);
        void SetStaticBooleanField(jcls, jfID, jbool);
        void SetStaticByteField(jcls, jfID, jbyte);
        void SetStaticCharField(jcls, jfID, jchar);
        void SetStaticShortField(jcls, jfID, jshort);
        void SetStaticIntField(jcls, jfID, jint);
        void SetStaticLongField(jcls, jfID, jlong);
        void SetStaticFloatField(jcls, jfID, jfloat);
        void SetStaticDoubleField(jcls, jfID, jdouble);

        jstr NewString(const jchar *, jsize);
        jsize GetStringLength(jstr);
        const jchar *GetStringChars(jstr, jbool *);
        void ReleaseStringChars(jstr, const jchar *);
        jstr NewStringUTF(cstr);
        jsize GetStringUTFLength(jstr);
        /* JNI spec says this returns const jbyte*, but that's inconsistent */
        cstr GetStringUTFChars(jstr, jbool *);
        void ReleaseStringUTFChars(jstr, cstr);
        jsize GetArrayLength(jarr);
        jobjArr NewObjectArray(jsize, jcls, jobj);
        jobj GetObjectArrayElement(jobjArr, jsize);
        void SetObjectArrayElement(jobjArr, jsize, jobj);

        jboolArr NewBooleanArray(jsize);
        jbyteArr NewByteArray(jsize);
        jcharArr NewCharArray(jsize);
        jshortArr NewShortArray(jsize);
        jintArr NewIntArray(jsize);
        jlongArr NewLongArray(jsize);
        jfloatArr NewFloatArray(jsize);
        jdoubleArr NewDoubleArray(jsize);

        jbool *GetBooleanArrayElements(jboolArr, jbool *);
        jbyte *GetByteArrayElements(jbyteArr, jbool *);
        jchar *GetCharArrayElements(jcharArr, jbool *);
        jshort *GetShortArrayElements(jshortArr, jbool *);
        jint *GetIntArrayElements(jintArr, jbool *);
        jlong *GetLongArrayElements(jlongArr, jbool *);
        jfloat *GetFloatArrayElements(jfloatArr, jbool *);
        jdouble *GetDoubleArrayElements(jdoubleArr, jbool *);

        void ReleaseBooleanArrayElements(jboolArr,
                                         jbool *, jint);
        void ReleaseByteArrayElements(jbyteArr,
                                      jbyte *, jint);
        void ReleaseCharArrayElements(jcharArr,
                                      jchar *, jint);
        void ReleaseShortArrayElements(jshortArr,
                                       jshort *, jint);
        void ReleaseIntArrayElements(jintArr,
                                     jint *, jint);
        void ReleaseLongArrayElements(jlongArr,
                                      jlong *, jint);
        void ReleaseFloatArrayElements(jfloatArr,
                                       jfloat *, jint);
        void ReleaseDoubleArrayElements(jdoubleArr,
                                        jdouble *, jint);

        void GetBooleanArrayRegion(jboolArr,
                                   jsize, jsize, jbool *);
        void GetByteArrayRegion(jbyteArr,
                                jsize, jsize, jbyte *);
        void GetCharArrayRegion(jcharArr,
                                jsize, jsize, jchar *);
        void GetShortArrayRegion(jshortArr,
                                 jsize, jsize, jshort *);
        void GetIntArrayRegion(jintArr,
                               jsize, jsize, jint *);
        void GetLongArrayRegion(jlongArr,
                                jsize, jsize, jlong *);
        void GetFloatArrayRegion(jfloatArr,
                                 jsize, jsize, jfloat *);
        void GetDoubleArrayRegion(jdoubleArr,
                                  jsize, jsize, jdouble *);

        /* spec shows these without const; some jni.h do, some don't */
        void SetBooleanArrayRegion(jboolArr,
                                   jsize, jsize, const jbool *);
        void SetByteArrayRegion(jbyteArr,
                                jsize, jsize, const jbyte *);
        void SetCharArrayRegion(jcharArr,
                                jsize, jsize, const jchar *);
        void SetShortArrayRegion(jshortArr,
                                 jsize, jsize, const jshort *);
        void SetIntArrayRegion(jintArr,
                               jsize, jsize, const jint *);
        void SetLongArrayRegion(jlongArr,
                                jsize, jsize, const jlong *);
        void SetFloatArrayRegion(jfloatArr,
                                 jsize, jsize, const jfloat *);
        void SetDoubleArrayRegion(jdoubleArr,
                                  jsize, jsize, const jdouble *);

        jint RegisterNatives(jcls, const JNINativeMethod *,
                             jint);
        jint UnregisterNatives(jcls);
        jint MonitorEnter(jobj);
        jint MonitorExit(jobj);
        jint GetJVM(JVM **);

        void GetStringRegion(jstr, jsize, jsize, jchar *);
        void GetStringUTFRegion(jstr, jsize, jsize, char *);

        void *GetPrimitiveArrayCritical(jarr, jbool *);
        void ReleasePrimitiveArrayCritical(jarr, void *, jint);

        const jchar *GetStringCritical(jstr, jbool *);
        void ReleaseStringCritical(jstr, const jchar *);

        jweak NewWeakGlobalRef(jobj);
        void DeleteWeakGlobalRef(jweak);

        jbool ExceptionCheck();

        jobj NewDirectByteBuffer(void *, jlong);
        void *GetDirectBufferAddress(jobj);
        jlong GetDirectBufferCapacity(jobj);

        /* added in JNI 1.6 */
        jobjRefT GetObjectRefType(jobj);

        typedef JavaVMAttachArgs JVMAttachArgs;
        typedef JavaVMInitArgs JVMInitArgs;

#ifdef JNI_VERSION_1_8
#ifdef _JNI_IMPLEMENTATION_
#define _JNI_IMPORT_OR_EXPORT_ JNIEXPORT
#else
#define _JNI_IMPORT_OR_EXPORT_ JNIIMPORT
#endif /* _JNI_IMPLEMENTATION_ */

        /*
     * VM initialization functions.
     *
     * Note these are the only symbols exported for JNI by the VM.
     */

        _JNI_IMPORT_OR_EXPORT_ jint JNICALL
        JNI_GetDefaultJVMInitArgs(void *);

        _JNI_IMPORT_OR_EXPORT_ jint JNICALL
        JNI_CreateJVM(JVM **, JNIEnv **, void *);

        _JNI_IMPORT_OR_EXPORT_ jint JNICALL
        JNI_GetCreatedJVMs(JVM **, jsize, jsize *);

#endif /* JNI_VERSION_1_8 */

#ifndef __cplusplus
        void JNIX_SetJNIEnv(JNIEnv *);
#else
void SetJNIEnv(JNIEnv *);
#endif /* __cplusplus */

#ifdef __cplusplus
    }  /* namespace JNIX */
#endif /* __cplusplus */

#ifdef __cplusplus
    static
#endif /* __cplusplus */
        JNIEXPORT JNIEnv *_jenv;

#ifdef __cplusplus
} /* extern "C" */
#endif /* __cplusplus */

#endif /* _JNIX_H */
