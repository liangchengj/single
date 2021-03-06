/**
 * Created at 2021/4/9 13:10.
 *
 * @author Liangcheng Juves
 */

#include "jnix.h"

// JNIEXPORT jint
// JNI_OnLoad(JVM *vm, void *reserved)
// {
// #ifdef __cplusplus
//     if (vm->GetEnv(reinterpret_cast<void **>(&_jenv), JNI_VERSION_1_6) != JNI_OK)
//     {
//         goto err;
//     }
// #else
//     if ((*vm)->GetEnv(vm, (void **)&_jenv, JNI_VERSION_1_6) != JNI_OK)
//     {
//         goto err;
//     }
// #endif /* __cplusplus */
// err:
//     return JNI_ERR;
// }

#ifdef __cplusplus
namespace JNIX
{
#endif /* __cplusplus */

#ifndef __cplusplus
    void JNIX_SetJNIEnv(JNIEnv *env)
    {
        _jenv = &(*env);
    }
#else
void SetJNIEnv(JNIEnv *env)
{
    _jenv = env;
}
#endif /* __cplusplus */

    jint GetVersion()
    {
#ifndef __cplusplus
        return (*_jenv)->GetVersion(_jenv);
#else
    return _jenv->GetVersion();
#endif /* __cplusplus */
    }
    jcls DefineClass(cstr name, jobj loader, const jbyte *buf,
                     jsize bufLen)
    {
#ifndef __cplusplus
        return (*_jenv)->DefineClass(_jenv, name, loader, buf, bufLen);
#else
    return _jenv->DefineClass(name, loader, buf, bufLen);
#endif /* __cplusplus */
    }
    jcls FindClass(cstr name)
    {
#ifndef __cplusplus
        return (*_jenv)->FindClass(_jenv, name);
#else
    return _jenv->FindClass(name);
#endif /* __cplusplus */
    }

    jmID FromReflectedMethod(jobj method)
    {
#ifndef __cplusplus
        return (*_jenv)->FromReflectedMethod(_jenv, method);
#else
    return _jenv->FromReflectedMethod(method);
#endif /* __cplusplus */
    }
    jfID FromReflectedField(jobj field)
    {
#ifndef __cplusplus
        return (*_jenv)->FromReflectedField(_jenv, field);
#else
    return _jenv->FromReflectedField(field);
#endif /* __cplusplus */
    }
    /* spec doesn't show jboolean parameter */
    jobj ToReflectedMethod(jcls cls, jmID methodID, jbool isStatic)
    {
#ifndef __cplusplus
        return (*_jenv)->ToReflectedMethod(_jenv, cls, methodID, isStatic);
#else
    return _jenv->ToReflectedMethod(cls, methodID, isStatic);
#endif /* __cplusplus */
    }

    jcls GetSuperclass(jcls clazz)
    {
#ifndef __cplusplus
        return (*_jenv)->GetSuperclass(_jenv, clazz);
#else
    return _jenv->GetSuperclass(clazz);
#endif /* __cplusplus */
    }
    jbool IsAssignableFrom(jcls clazz1, jcls clazz2)
    {
#ifndef __cplusplus
        return (*_jenv)->IsAssignableFrom(_jenv, clazz1, clazz2);
#else
    return _jenv->IsAssignableFrom(clazz1, clazz2);
#endif /* __cplusplus */
    }

    /* spec doesn't show jboolean parameter */
    jobj ToReflectedField(jcls cls, jfID fieldID, jbool isStatic)
    {
#ifndef __cplusplus
        return (*_jenv)->ToReflectedField(_jenv, cls, fieldID, isStatic);
#else
    return _jenv->ToReflectedField(cls, fieldID, isStatic);
#endif /* __cplusplus */
    }

    jint Throw(jtr obj)
    {
#ifndef __cplusplus
        return (*_jenv)->Throw(_jenv, obj);
#else
    return _jenv->Throw(obj);
#endif /* __cplusplus */
    }
    jint ThrowNew(jcls clazz, cstr message)
    {
#ifndef __cplusplus
        return (*_jenv)->ThrowNew(_jenv, clazz, message);
#else
    return _jenv->ThrowNew(clazz, message);
#endif /* __cplusplus */
    }
    jtr ExceptionOccurred()
    {
#ifndef __cplusplus
        return (*_jenv)->ExceptionOccurred(_jenv);
#else
    return _jenv->ExceptionOccurred();
#endif /* __cplusplus */
    }
    void ExceptionDescribe()
    {
#ifndef __cplusplus
        (*_jenv)->ExceptionDescribe(_jenv);
#else
    _jenv->ExceptionDescribe();
#endif /* __cplusplus */
    }
    void ExceptionClear()
    {
#ifndef __cplusplus
        (*_jenv)->ExceptionClear(_jenv);
#else
    _jenv->ExceptionClear();
#endif /* __cplusplus */
    }
    void FatalError(cstr msg)
    {
#ifndef __cplusplus
        (*_jenv)->FatalError(_jenv, msg);
#else
    _jenv->FatalError(msg);
#endif /* __cplusplus */
    }

    jint PushLocalFrame(jint capacity)
    {
#ifndef __cplusplus
        return (*_jenv)->PushLocalFrame(_jenv, capacity);
#else
    return _jenv->PushLocalFrame(capacity);
#endif /* __cplusplus */
    }
    jobj PopLocalFrame(jobj result)
    {
#ifndef __cplusplus
        return (*_jenv)->PopLocalFrame(_jenv, result);
#else
    return _jenv->PopLocalFrame(result);
#endif /* __cplusplus */
    }

    jobj NewGlobalRef(jobj obj)
    {
#ifndef __cplusplus
        return (*_jenv)->NewGlobalRef(_jenv, obj);
#else
    return _jenv->NewGlobalRef(obj);
#endif /* __cplusplus */
    }
    void DeleteGlobalRef(jobj globalRef)
    {
#ifndef __cplusplus
        (*_jenv)->DeleteGlobalRef(_jenv, globalRef);
#else
    _jenv->DeleteGlobalRef(globalRef);
#endif /* __cplusplus */
    }
    void DeleteLocalRef(jobj localRef)
    {
#ifndef __cplusplus
        (*_jenv)->DeleteLocalRef(_jenv, localRef);
#else
    _jenv->DeleteLocalRef(localRef);
#endif /* __cplusplus */
    }
    jbool IsSameObject(jobj ref1, jobj ref2)
    {
#ifndef __cplusplus
        return (*_jenv)->IsSameObject(_jenv, ref1, ref2);
#else
    return _jenv->IsSameObject(ref1, ref2);
#endif /* __cplusplus */
    }

    jobj NewLocalRef(jobj ref)
    {
#ifndef __cplusplus
        return (*_jenv)->NewLocalRef(_jenv, ref);
#else
    return _jenv->NewLocalRef(ref);
#endif /* __cplusplus */
    }
    jint EnsureLocalCapacity(jint capacity)
    {
#ifndef __cplusplus
        return (*_jenv)->EnsureLocalCapacity(_jenv, capacity);
#else
    return _jenv->EnsureLocalCapacity(capacity);
#endif /* __cplusplus */
    }

    jobj AllocObject(jcls clazz)
    {
#ifndef __cplusplus
        return (*_jenv)->AllocObject(_jenv, clazz);
#else
    return _jenv->AllocObject(clazz);
#endif /* __cplusplus */
    }
    jobj NewObject(jcls clazz, jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->NewObject(_jenv, clazz, methodID, args);
#else
    return _jenv->NewObject(clazz, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jobj NewObjectV(jcls clazz, jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->NewObjectV(_jenv, clazz, methodID, args);
#else
    return _jenv->NewObjectV(clazz, methodID, args);
#endif /* __cplusplus */
    }
    jobj NewObjectA(jcls clazz, jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->NewObjectA(_jenv, clazz, methodID, args);
#else
    return _jenv->NewObjectA(clazz, methodID, args);
#endif /* __cplusplus */
    }

    jcls GetObjectClass(jobj obj)
    {
#ifndef __cplusplus
        return (*_jenv)->GetObjectClass(_jenv, obj);
#else
    return _jenv->GetObjectClass(obj);
#endif /* __cplusplus */
    }
    jbool IsInstanceOf(jobj obj, jcls clazz)
    {
#ifndef __cplusplus
        return (*_jenv)->IsInstanceOf(_jenv, obj, clazz);
#else
    return _jenv->IsInstanceOf(obj, clazz);
#endif /* __cplusplus */
    }
    jmID GetMethodID(jcls clazz, cstr name, cstr sig)
    {
#ifndef __cplusplus
        return (*_jenv)->GetMethodID(_jenv, clazz, name, sig);
#else
    return _jenv->GetMethodID(clazz, name, sig);
#endif /* __cplusplus */
    }

    jobj CallObjectMethod(jobj obj, jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallObjectMethod(_jenv, obj, methodID, args);
#else
    return _jenv->CallObjectMethod(obj, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jobj CallObjectMethodV(jobj obj, jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallObjectMethodV(_jenv, obj, methodID, args);
#else
    return _jenv->CallObjectMethodV(obj, methodID, args);
#endif /* __cplusplus */
    }
    jobj CallObjectMethodA(jobj obj, jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallObjectMethodA(_jenv, obj, methodID, args);
#else
    return _jenv->CallObjectMethodA(obj, methodID, args);
#endif /* __cplusplus */
    }
    jbool CallBooleanMethod(jobj obj, jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallBooleanMethod(_jenv, obj, methodID, args);
#else
    return _jenv->CallBooleanMethod(obj, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jbool CallBooleanMethodV(jobj obj, jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallBooleanMethodV(_jenv, obj, methodID, args);
#else
    return _jenv->CallBooleanMethodV(obj, methodID, args);
#endif /* __cplusplus */
    }
    jbool CallBooleanMethodA(jobj obj, jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallBooleanMethodA(_jenv, obj, methodID, args);
#else
    return _jenv->CallBooleanMethodA(obj, methodID, args);
#endif /* __cplusplus */
    }
    jbyte CallByteMethod(jobj obj, jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallByteMethod(_jenv, obj, methodID, args);
#else
    return _jenv->CallByteMethod(obj, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jbyte CallByteMethodV(jobj obj, jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallByteMethodV(_jenv, obj, methodID, args);
#else
    return _jenv->CallByteMethodV(obj, methodID, args);
#endif /* __cplusplus */
    }
    jbyte CallByteMethodA(jobj obj, jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallByteMethodA(_jenv, obj, methodID, args);
#else
    return _jenv->CallByteMethodA(obj, methodID, args);
#endif /* __cplusplus */
    }
    jchar CallCharMethod(jobj obj, jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallCharMethod(_jenv, obj, methodID, args);
#else
    return _jenv->CallCharMethod(obj, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jchar CallCharMethodV(jobj obj, jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallCharMethodV(_jenv, obj, methodID, args);
#else
    return _jenv->CallCharMethodV(obj, methodID, args);
#endif /* __cplusplus */
    }
    jchar CallCharMethodA(jobj obj, jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallCharMethodA(_jenv, obj, methodID, args);
#else
    return _jenv->CallCharMethodA(obj, methodID, args);
#endif /* __cplusplus */
    }
    jshort CallShortMethod(jobj obj, jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallShortMethod(_jenv, obj, methodID, args);
#else
    return _jenv->CallShortMethod(obj, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jshort CallShortMethodV(jobj obj, jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallShortMethodV(_jenv, obj, methodID, args);
#else
    return _jenv->CallShortMethodV(obj, methodID, args);
#endif /* __cplusplus */
    }
    jshort CallShortMethodA(jobj obj, jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallShortMethodA(_jenv, obj, methodID, args);
#else
    return _jenv->CallShortMethodA(obj, methodID, args);
#endif /* __cplusplus */
    }
    jint CallIntMethod(jobj obj, jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallIntMethod(_jenv, obj, methodID, args);
#else
    return _jenv->CallIntMethod(obj, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jint CallIntMethodV(jobj obj, jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallIntMethodV(_jenv, obj, methodID, args);
#else
    return _jenv->CallIntMethodV(obj, methodID, args);
#endif /* __cplusplus */
    }
    jint CallIntMethodA(jobj obj, jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallIntMethodA(_jenv, obj, methodID, args);
#else
    return _jenv->CallIntMethodA(obj, methodID, args);
#endif /* __cplusplus */
    }
    jlong CallLongMethod(jobj obj, jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallLongMethod(_jenv, obj, methodID, args);
#else
    return _jenv->CallLongMethod(obj, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jlong CallLongMethodV(jobj obj, jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallLongMethodV(_jenv, obj, methodID, args);
#else
    return _jenv->CallLongMethodV(obj, methodID, args);
#endif /* __cplusplus */
    }
    jlong CallLongMethodA(jobj obj, jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallLongMethodA(_jenv, obj, methodID, args);
#else
    return _jenv->CallLongMethodA(obj, methodID, args);
#endif /* __cplusplus */
    }
    jfloat CallFloatMethod(jobj obj, jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallFloatMethod(_jenv, obj, methodID, args);
#else
    return _jenv->CallFloatMethod(obj, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jfloat CallFloatMethodV(jobj obj, jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallFloatMethodV(_jenv, obj, methodID, args);
#else
    return _jenv->CallFloatMethodV(obj, methodID, args);
#endif /* __cplusplus */
    }
    jfloat CallFloatMethodA(jobj obj, jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallFloatMethodA(_jenv, obj, methodID, args);
#else
    return _jenv->CallFloatMethodA(obj, methodID, args);
#endif /* __cplusplus */
    }
    jdouble CallDoubleMethod(jobj obj, jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallDoubleMethod(_jenv, obj, methodID, args);
#else
    return _jenv->CallDoubleMethod(obj, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jdouble CallDoubleMethodV(jobj obj, jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallDoubleMethodV(_jenv, obj, methodID, args);
#else
    return _jenv->CallDoubleMethodV(obj, methodID, args);
#endif /* __cplusplus */
    }
    jdouble CallDoubleMethodA(jobj obj, jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallDoubleMethodA(_jenv, obj, methodID, args);
#else
    return _jenv->CallDoubleMethodA(obj, methodID, args);
#endif /* __cplusplus */
    }
    void CallVoidMethod(jobj obj, jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        (*_jenv)->CallVoidMethod(_jenv, obj, methodID, args);
#else
    _jenv->CallVoidMethod(obj, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    void CallVoidMethodV(jobj obj, jmID methodID, va_list args)
    {
#ifndef __cplusplus
        (*_jenv)->CallVoidMethodV(_jenv, obj, methodID, args);
#else
    _jenv->CallVoidMethodV(obj, methodID, args);
#endif /* __cplusplus */
    }
    void CallVoidMethodA(jobj obj, jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        (*_jenv)->CallVoidMethodA(_jenv, obj, methodID, args);
#else
    _jenv->CallVoidMethodA(obj, methodID, args);
#endif /* __cplusplus */
    }

    jobj CallNonvirtualObjectMethod(jobj obj, jcls clazz,
                                    jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualObjectMethod(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualObjectMethod(obj, clazz, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jobj CallNonvirtualObjectMethodV(jobj obj, jcls clazz,
                                     jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualObjectMethodV(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualObjectMethodV(obj, clazz, methodID, args);
#endif /* __cplusplus */
    }
    jobj CallNonvirtualObjectMethodA(jobj obj, jcls clazz,
                                     jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualObjectMethodA(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualObjectMethodA(obj, clazz, methodID, args);
#endif /* __cplusplus */
    }
    jbool CallNonvirtualBooleanMethod(jobj obj, jcls clazz,
                                      jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualBooleanMethod(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualBooleanMethod(obj, clazz, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jbool CallNonvirtualBooleanMethodV(jobj obj, jcls clazz,
                                       jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualBooleanMethodV(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualBooleanMethodV(obj, clazz, methodID, args);
#endif /* __cplusplus */
    }
    jbool CallNonvirtualBooleanMethodA(jobj obj, jcls clazz,
                                       jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualBooleanMethodA(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualBooleanMethodA(obj, clazz, methodID, args);
#endif /* __cplusplus */
    }
    jbyte CallNonvirtualByteMethod(jobj obj, jcls clazz,
                                   jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualByteMethod(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualByteMethod(obj, clazz, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jbyte CallNonvirtualByteMethodV(jobj obj, jcls clazz,
                                    jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualByteMethodV(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualByteMethodV(obj, clazz, methodID, args);
#endif /* __cplusplus */
    }
    jbyte CallNonvirtualByteMethodA(jobj obj, jcls clazz,
                                    jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualByteMethodA(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualByteMethodA(obj, clazz, methodID, args);
#endif /* __cplusplus */
    }
    jchar CallNonvirtualCharMethod(jobj obj, jcls clazz,
                                   jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualCharMethod(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualCharMethod(obj, clazz, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jchar CallNonvirtualCharMethodV(jobj obj, jcls clazz,
                                    jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualCharMethodV(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualCharMethodV(obj, clazz, methodID, args);
#endif /* __cplusplus */
    }
    jchar CallNonvirtualCharMethodA(jobj obj, jcls clazz,
                                    jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualCharMethodA(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualCharMethodA(obj, clazz, methodID, args);
#endif /* __cplusplus */
    }
    jshort CallNonvirtualShortMethod(jobj obj, jcls clazz,
                                     jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualShortMethod(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualShortMethod(obj, clazz, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jshort CallNonvirtualShortMethodV(jobj obj, jcls clazz,
                                      jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualShortMethodV(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualShortMethodV(obj, clazz, methodID, args);
#endif /* __cplusplus */
    }
    jshort CallNonvirtualShortMethodA(jobj obj, jcls clazz,
                                      jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualShortMethodA(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualShortMethodA(obj, clazz, methodID, args);
#endif /* __cplusplus */
    }
    jint CallNonvirtualIntMethod(jobj obj, jcls clazz,
                                 jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualIntMethod(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualIntMethod(obj, clazz, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jint CallNonvirtualIntMethodV(jobj obj, jcls clazz,
                                  jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualIntMethodV(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualIntMethodV(obj, clazz, methodID, args);
#endif /* __cplusplus */
    }
    jint CallNonvirtualIntMethodA(jobj obj, jcls clazz,
                                  jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualIntMethodA(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualIntMethodA(obj, clazz, methodID, args);
#endif /* __cplusplus */
    }
    jlong CallNonvirtualLongMethod(jobj obj, jcls clazz,
                                   jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualLongMethod(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualLongMethod(obj, clazz, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jlong CallNonvirtualLongMethodV(jobj obj, jcls clazz,
                                    jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualLongMethodV(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualLongMethodV(obj, clazz, methodID, args);
#endif /* __cplusplus */
    }
    jlong CallNonvirtualLongMethodA(jobj obj, jcls clazz,
                                    jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualLongMethodA(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualLongMethodA(obj, clazz, methodID, args);
#endif /* __cplusplus */
    }
    jfloat CallNonvirtualFloatMethod(jobj obj, jcls clazz,
                                     jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualFloatMethod(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualFloatMethod(obj, clazz, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jfloat CallNonvirtualFloatMethodV(jobj obj, jcls clazz,
                                      jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualFloatMethodV(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualFloatMethodV(obj, clazz, methodID, args);
#endif /* __cplusplus */
    }
    jfloat CallNonvirtualFloatMethodA(jobj obj, jcls clazz,
                                      jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualFloatMethodA(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualFloatMethodA(obj, clazz, methodID, args);
#endif /* __cplusplus */
    }
    jdouble CallNonvirtualDoubleMethod(jobj obj, jcls clazz,
                                       jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualDoubleMethod(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualDoubleMethod(obj, clazz, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jdouble CallNonvirtualDoubleMethodV(jobj obj, jcls clazz,
                                        jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualDoubleMethodV(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualDoubleMethodV(obj, clazz, methodID, args);
#endif /* __cplusplus */
    }
    jdouble CallNonvirtualDoubleMethodA(jobj obj, jcls clazz,
                                        jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallNonvirtualDoubleMethodA(_jenv, obj, clazz, methodID, args);
#else
    return _jenv->CallNonvirtualDoubleMethodA(obj, clazz, methodID, args);
#endif /* __cplusplus */
    }
    void CallNonvirtualVoidMethod(jobj obj, jcls clazz,
                                  jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        (*_jenv)->CallNonvirtualVoidMethod(_jenv, obj, clazz, methodID, args);
#else
    _jenv->CallNonvirtualVoidMethod(obj, clazz, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    void CallNonvirtualVoidMethodV(jobj obj, jcls clazz,
                                   jmID methodID, va_list args)
    {
#ifndef __cplusplus
        (*_jenv)->CallNonvirtualVoidMethodV(_jenv, obj, clazz, methodID, args);
#else
    _jenv->CallNonvirtualVoidMethodV(obj, clazz, methodID, args);
#endif /* __cplusplus */
    }
    void CallNonvirtualVoidMethodA(jobj obj, jcls clazz,
                                   jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        (*_jenv)->CallNonvirtualVoidMethodA(_jenv, obj, clazz, methodID, args);
#else
    _jenv->CallNonvirtualVoidMethodA(obj, clazz, methodID, args);
#endif /* __cplusplus */
    }

    jfID GetFieldID(jcls clazz, cstr name, cstr sig)
    {
#ifndef __cplusplus
        return (*_jenv)->GetFieldID(_jenv, clazz, name, sig);
#else
    return _jenv->GetFieldID(clazz, name, sig);
#endif /* __cplusplus */
    }

    jobj GetObjectField(jobj obj, jfID fieldID)
    {
#ifndef __cplusplus
        return (*_jenv)->GetObjectField(_jenv, obj, fieldID);
#else
    return _jenv->GetObjectField(obj, fieldID);
#endif /* __cplusplus */
    }
    jbool GetBooleanField(jobj obj, jfID fieldID)
    {
#ifndef __cplusplus
        return (*_jenv)->GetBooleanField(_jenv, obj, fieldID);
#else
    return _jenv->GetBooleanField(obj, fieldID);
#endif /* __cplusplus */
    }
    jbyte GetByteField(jobj obj, jfID fieldID)
    {
#ifndef __cplusplus
        return (*_jenv)->GetByteField(_jenv, obj, fieldID);
#else
    return _jenv->GetByteField(obj, fieldID);
#endif /* __cplusplus */
    }
    jchar GetCharField(jobj obj, jfID fieldID)
    {
#ifndef __cplusplus
        return (*_jenv)->GetCharField(_jenv, obj, fieldID);
#else
    return _jenv->GetCharField(obj, fieldID);
#endif /* __cplusplus */
    }
    jshort GetShortField(jobj obj, jfID fieldID)
    {
#ifndef __cplusplus
        return (*_jenv)->GetShortField(_jenv, obj, fieldID);
#else
    return _jenv->GetShortField(obj, fieldID);
#endif /* __cplusplus */
    }
    jint GetIntField(jobj obj, jfID fieldID)
    {
#ifndef __cplusplus
        return (*_jenv)->GetIntField(_jenv, obj, fieldID);
#else
    return _jenv->GetIntField(obj, fieldID);
#endif /* __cplusplus */
    }
    jlong GetLongField(jobj obj, jfID fieldID)
    {
#ifndef __cplusplus
        return (*_jenv)->GetLongField(_jenv, obj, fieldID);
#else
    return _jenv->GetLongField(obj, fieldID);
#endif /* __cplusplus */
    }
    jfloat GetFloatField(jobj obj, jfID fieldID)
    {
#ifndef __cplusplus
        return (*_jenv)->GetFloatField(_jenv, obj, fieldID);
#else
    return _jenv->GetFloatField(obj, fieldID);
#endif /* __cplusplus */
    }
    jdouble GetDoubleField(jobj obj, jfID fieldID)
    {
#ifndef __cplusplus
        return (*_jenv)->GetDoubleField(_jenv, obj, fieldID);
#else
    return _jenv->GetDoubleField(obj, fieldID);
#endif /* __cplusplus */
    }

    void SetObjectField(jobj obj, jfID fieldID, jobj value)
    {
#ifndef __cplusplus
        (*_jenv)->SetObjectField(_jenv, obj, fieldID, value);
#else
    _jenv->SetObjectField(obj, fieldID, value);
#endif /* __cplusplus */
    }
    void SetBooleanField(jobj obj, jfID fieldID, jbool value)
    {
#ifndef __cplusplus
        (*_jenv)->SetBooleanField(_jenv, obj, fieldID, value);
#else
    _jenv->SetBooleanField(obj, fieldID, value);
#endif /* __cplusplus */
    }
    void SetByteField(jobj obj, jfID fieldID, jbyte value)
    {
#ifndef __cplusplus
        (*_jenv)->SetByteField(_jenv, obj, fieldID, value);
#else
    _jenv->SetByteField(obj, fieldID, value);
#endif /* __cplusplus */
    }
    void SetCharField(jobj obj, jfID fieldID, jchar value)
    {
#ifndef __cplusplus
        (*_jenv)->SetCharField(_jenv, obj, fieldID, value);
#else
    _jenv->SetCharField(obj, fieldID, value);
#endif /* __cplusplus */
    }
    void SetShortField(jobj obj, jfID fieldID, jshort value)
    {
#ifndef __cplusplus
        (*_jenv)->SetShortField(_jenv, obj, fieldID, value);
#else
    _jenv->SetShortField(obj, fieldID, value);
#endif /* __cplusplus */
    }
    void SetIntField(jobj obj, jfID fieldID, jint value)
    {
#ifndef __cplusplus
        (*_jenv)->SetIntField(_jenv, obj, fieldID, value);
#else
    _jenv->SetIntField(obj, fieldID, value);
#endif /* __cplusplus */
    }
    void SetLongField(jobj obj, jfID fieldID, jlong value)
    {
#ifndef __cplusplus
        (*_jenv)->SetLongField(_jenv, obj, fieldID, value);
#else
    _jenv->SetLongField(obj, fieldID, value);
#endif /* __cplusplus */
    }
    void SetFloatField(jobj obj, jfID fieldID, jfloat value)
    {
#ifndef __cplusplus
        (*_jenv)->SetFloatField(_jenv, obj, fieldID, value);
#else
    _jenv->SetFloatField(obj, fieldID, value);
#endif /* __cplusplus */
    }
    void SetDoubleField(jobj obj, jfID fieldID, jdouble value)
    {
#ifndef __cplusplus
        (*_jenv)->SetDoubleField(_jenv, obj, fieldID, value);
#else
    _jenv->SetDoubleField(obj, fieldID, value);
#endif /* __cplusplus */
    }

    jmID GetStaticMethodID(jcls clazz, cstr name, cstr sig)
    {
#ifndef __cplusplus
        return (*_jenv)->GetStaticMethodID(_jenv, clazz, name, sig);
#else
    return _jenv->GetStaticMethodID(clazz, name, sig);
#endif /* __cplusplus */
    }

    jobj CallStaticObjectMethod(jcls clazz, jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallStaticObjectMethod(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticObjectMethod(clazz, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jobj CallStaticObjectMethodV(jcls clazz, jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallStaticObjectMethodV(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticObjectMethodV(clazz, methodID, args);
#endif /* __cplusplus */
    }
    jobj CallStaticObjectMethodA(jcls clazz, jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallStaticObjectMethodA(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticObjectMethodA(clazz, methodID, args);
#endif /* __cplusplus */
    }
    jbool CallStaticBooleanMethod(jcls clazz, jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallStaticBooleanMethod(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticBooleanMethod(clazz, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jbool CallStaticBooleanMethodV(jcls clazz, jmID methodID,
                                   va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallStaticBooleanMethodV(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticBooleanMethodV(clazz, methodID, args);
#endif /* __cplusplus */
    }
    jbool CallStaticBooleanMethodA(jcls clazz, jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallStaticBooleanMethodA(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticBooleanMethodA(clazz, methodID, args);
#endif /* __cplusplus */
    }
    jbyte CallStaticByteMethod(jcls clazz, jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallStaticByteMethod(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticByteMethod(clazz, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jbyte CallStaticByteMethodV(jcls clazz, jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallStaticByteMethodV(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticByteMethodV(clazz, methodID, args);
#endif /* __cplusplus */
    }
    jbyte CallStaticByteMethodA(jcls clazz, jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallStaticByteMethodA(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticByteMethodA(clazz, methodID, args);
#endif /* __cplusplus */
    }
    jchar CallStaticCharMethod(jcls clazz, jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallStaticCharMethod(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticCharMethod(clazz, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jchar CallStaticCharMethodV(jcls clazz, jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallStaticCharMethodV(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticCharMethodV(clazz, methodID, args);
#endif /* __cplusplus */
    }
    jchar CallStaticCharMethodA(jcls clazz, jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallStaticCharMethodA(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticCharMethodA(clazz, methodID, args);
#endif /* __cplusplus */
    }
    jshort CallStaticShortMethod(jcls clazz, jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallStaticShortMethod(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticShortMethod(clazz, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jshort CallStaticShortMethodV(jcls clazz, jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallStaticShortMethodV(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticShortMethodV(clazz, methodID, args);
#endif /* __cplusplus */
    }
    jshort CallStaticShortMethodA(jcls clazz, jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallStaticShortMethodA(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticShortMethodA(clazz, methodID, args);
#endif /* __cplusplus */
    }
    jint CallStaticIntMethod(jcls clazz, jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallStaticIntMethod(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticIntMethod(clazz, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jint CallStaticIntMethodV(jcls clazz, jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallStaticIntMethodV(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticIntMethodV(clazz, methodID, args);
#endif /* __cplusplus */
    }
    jint CallStaticIntMethodA(jcls clazz, jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallStaticIntMethodA(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticIntMethodA(clazz, methodID, args);
#endif /* __cplusplus */
    }
    jlong CallStaticLongMethod(jcls clazz, jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallStaticLongMethod(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticLongMethod(clazz, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jlong CallStaticLongMethodV(jcls clazz, jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallStaticLongMethodV(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticLongMethodV(clazz, methodID, args);
#endif /* __cplusplus */
    }
    jlong CallStaticLongMethodA(jcls clazz, jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallStaticLongMethodA(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticLongMethodA(clazz, methodID, args);
#endif /* __cplusplus */
    }
    jfloat CallStaticFloatMethod(jcls clazz, jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallStaticFloatMethod(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticFloatMethod(clazz, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jfloat CallStaticFloatMethodV(jcls clazz, jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallStaticFloatMethodV(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticFloatMethodV(clazz, methodID, args);
#endif /* __cplusplus */
    }
    jfloat CallStaticFloatMethodA(jcls clazz, jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallStaticFloatMethodA(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticFloatMethodA(clazz, methodID, args);
#endif /* __cplusplus */
    }
    jdouble CallStaticDoubleMethod(jcls clazz, jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        return (*_jenv)->CallStaticDoubleMethod(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticDoubleMethod(clazz, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    jdouble CallStaticDoubleMethodV(jcls clazz, jmID methodID, va_list args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallStaticDoubleMethodV(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticDoubleMethodV(clazz, methodID, args);
#endif /* __cplusplus */
    }
    jdouble CallStaticDoubleMethodA(jcls clazz, jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        return (*_jenv)->CallStaticDoubleMethodA(_jenv, clazz, methodID, args);
#else
    return _jenv->CallStaticDoubleMethodA(clazz, methodID, args);
#endif /* __cplusplus */
    }
    void CallStaticVoidMethod(jcls clazz, jmID methodID, ...)
    {
        va_list args;
        va_start(args, methodID);
#ifndef __cplusplus
        (*_jenv)->CallStaticVoidMethod(_jenv, clazz, methodID, args);
#else
    _jenv->CallStaticVoidMethod(clazz, methodID, args);
#endif /* __cplusplus */
        va_end(args);
    }
    void CallStaticVoidMethodV(jcls clazz, jmID methodID, va_list args)
    {
#ifndef __cplusplus
        (*_jenv)->CallStaticVoidMethodV(_jenv, clazz, methodID, args);
#else
    _jenv->CallStaticVoidMethodV(clazz, methodID, args);
#endif /* __cplusplus */
    }
    void CallStaticVoidMethodA(jcls clazz, jmID methodID, const jval *args)
    {
#ifndef __cplusplus
        (*_jenv)->CallStaticVoidMethodA(_jenv, clazz, methodID, args);
#else
    _jenv->CallStaticVoidMethodA(clazz, methodID, args);
#endif /* __cplusplus */
    }

    jfID GetStaticFieldID(jcls clazz, cstr name, cstr sig)
    {
#ifndef __cplusplus
        return (*_jenv)->GetStaticFieldID(_jenv, clazz, name, sig);
#else
    return _jenv->GetStaticFieldID(clazz, name, sig);
#endif /* __cplusplus */
    }

    jobj GetStaticObjectField(jcls clazz, jfID fieldID)
    {
#ifndef __cplusplus
        return (*_jenv)->GetStaticObjectField(_jenv, clazz, fieldID);
#else
    return _jenv->GetStaticObjectField(clazz, fieldID);
#endif /* __cplusplus */
    }
    jbool GetStaticBooleanField(jcls clazz, jfID fieldID)
    {
#ifndef __cplusplus
        return (*_jenv)->GetStaticBooleanField(_jenv, clazz, fieldID);
#else
    return _jenv->GetStaticBooleanField(clazz, fieldID);
#endif /* __cplusplus */
    }
    jbyte GetStaticByteField(jcls clazz, jfID fieldID)
    {
#ifndef __cplusplus
        return (*_jenv)->GetStaticByteField(_jenv, clazz, fieldID);
#else
    return _jenv->GetStaticByteField(clazz, fieldID);
#endif /* __cplusplus */
    }
    jchar GetStaticCharField(jcls clazz, jfID fieldID)
    {
#ifndef __cplusplus
        return (*_jenv)->GetStaticCharField(_jenv, clazz, fieldID);
#else
    return _jenv->GetStaticCharField(clazz, fieldID);
#endif /* __cplusplus */
    }
    jshort GetStaticShortField(jcls clazz, jfID fieldID)
    {
#ifndef __cplusplus
        return (*_jenv)->GetStaticShortField(_jenv, clazz, fieldID);
#else
    return _jenv->GetStaticShortField(clazz, fieldID);
#endif /* __cplusplus */
    }
    jint GetStaticIntField(jcls clazz, jfID fieldID)
    {
#ifndef __cplusplus
        return (*_jenv)->GetStaticIntField(_jenv, clazz, fieldID);
#else
    return _jenv->GetStaticIntField(clazz, fieldID);
#endif /* __cplusplus */
    }
    jlong GetStaticLongField(jcls clazz, jfID fieldID)
    {
#ifndef __cplusplus
        return (*_jenv)->GetStaticLongField(_jenv, clazz, fieldID);
#else
    return _jenv->GetStaticLongField(clazz, fieldID);
#endif /* __cplusplus */
    }
    jfloat GetStaticFloatField(jcls clazz, jfID fieldID)
    {
#ifndef __cplusplus
        return (*_jenv)->GetStaticFloatField(_jenv, clazz, fieldID);
#else
    return _jenv->GetStaticFloatField(clazz, fieldID);
#endif /* __cplusplus */
    }
    jdouble GetStaticDoubleField(jcls clazz, jfID fieldID)
    {
#ifndef __cplusplus
        return (*_jenv)->GetStaticDoubleField(_jenv, clazz, fieldID);
#else
    return _jenv->GetStaticDoubleField(clazz, fieldID);
#endif /* __cplusplus */
    }

    void SetStaticObjectField(jcls clazz, jfID fieldID, jobj value)
    {
#ifndef __cplusplus
        (*_jenv)->SetStaticObjectField(_jenv, clazz, fieldID, value);
#else
    _jenv->SetStaticObjectField(clazz, fieldID, value);
#endif /* __cplusplus */
    }
    void SetStaticBooleanField(jcls clazz, jfID fieldID, jbool value)
    {
#ifndef __cplusplus
        (*_jenv)->SetStaticBooleanField(_jenv, clazz, fieldID, value);
#else
    _jenv->SetStaticBooleanField(clazz, fieldID, value);
#endif /* __cplusplus */
    }
    void SetStaticByteField(jcls clazz, jfID fieldID, jbyte value)
    {
#ifndef __cplusplus
        (*_jenv)->SetStaticByteField(_jenv, clazz, fieldID, value);
#else
    _jenv->SetStaticByteField(clazz, fieldID, value);
#endif /* __cplusplus */
    }
    void SetStaticCharField(jcls clazz, jfID fieldID, jchar value)
    {
#ifndef __cplusplus
        (*_jenv)->SetStaticCharField(_jenv, clazz, fieldID, value);
#else
    _jenv->SetStaticCharField(clazz, fieldID, value);
#endif /* __cplusplus */
    }
    void SetStaticShortField(jcls clazz, jfID fieldID, jshort value)
    {
#ifndef __cplusplus
        (*_jenv)->SetStaticShortField(_jenv, clazz, fieldID, value);
#else
    _jenv->SetStaticShortField(clazz, fieldID, value);
#endif /* __cplusplus */
    }
    void SetStaticIntField(jcls clazz, jfID fieldID, jint value)
    {
#ifndef __cplusplus
        (*_jenv)->SetStaticIntField(_jenv, clazz, fieldID, value);
#else
    _jenv->SetStaticIntField(clazz, fieldID, value);
#endif /* __cplusplus */
    }
    void SetStaticLongField(jcls clazz, jfID fieldID, jlong value)
    {
#ifndef __cplusplus
        (*_jenv)->SetStaticLongField(_jenv, clazz, fieldID, value);
#else
    _jenv->SetStaticLongField(clazz, fieldID, value);
#endif /* __cplusplus */
    }
    void SetStaticFloatField(jcls clazz, jfID fieldID, jfloat value)
    {
#ifndef __cplusplus
        (*_jenv)->SetStaticFloatField(_jenv, clazz, fieldID, value);
#else
    _jenv->SetStaticFloatField(clazz, fieldID, value);
#endif /* __cplusplus */
    }
    void SetStaticDoubleField(jcls clazz, jfID fieldID, jdouble value)
    {
#ifndef __cplusplus
        (*_jenv)->SetStaticDoubleField(_jenv, clazz, fieldID, value);
#else
    _jenv->SetStaticDoubleField(clazz, fieldID, value);
#endif /* __cplusplus */
    }

    jstr NewString(const jchar *unicodeChars, jsize len)
    {
#ifndef __cplusplus
        return (*_jenv)->NewString(_jenv, unicodeChars, len);
#else
    return _jenv->NewString(unicodeChars, len);
#endif /* __cplusplus */
    }
    jsize GetStringLength(jstr string)
    {
#ifndef __cplusplus
        return (*_jenv)->GetStringLength(_jenv, string);
#else
    return _jenv->GetStringLength(string);
#endif /* __cplusplus */
    }
    const jchar *GetStringChars(jstr string, jbool *isCopy)
    {
#ifndef __cplusplus
        return (*_jenv)->GetStringChars(_jenv, string, isCopy);
#else
    return _jenv->GetStringChars(string, isCopy);
#endif /* __cplusplus */
    }
    void ReleaseStringChars(jstr string, const jchar *chars)
    {
#ifndef __cplusplus
        (*_jenv)->ReleaseStringChars(_jenv, string, chars);
#else
    _jenv->ReleaseStringChars(string, chars);
#endif /* __cplusplus */
    }
    jstr NewStringUTF(cstr bytes)
    {
#ifndef __cplusplus
        return (*_jenv)->NewStringUTF(_jenv, bytes);
#else
    return _jenv->NewStringUTF(bytes);
#endif /* __cplusplus */
    }
    jsize GetStringUTFLength(jstr string)
    {
#ifndef __cplusplus
        return (*_jenv)->GetStringUTFLength(_jenv, string);
#else
    return _jenv->GetStringUTFLength(string);
#endif /* __cplusplus */
    }
    /* JNI spec says this returns const jbyte*, but that's inconsistent */
    cstr GetStringUTFChars(jstr string, jbool *isCopy)
    {
#ifndef __cplusplus
        return (*_jenv)->GetStringUTFChars(_jenv, string, isCopy);
#else
    return _jenv->GetStringUTFChars(string, isCopy);
#endif /* __cplusplus */
    }
    void ReleaseStringUTFChars(jstr string, cstr utf)
    {
#ifndef __cplusplus
        (*_jenv)->ReleaseStringUTFChars(_jenv, string, utf);
#else
    _jenv->ReleaseStringUTFChars(string, utf);
#endif /* __cplusplus */
    }
    jsize GetArrayLength(jarr array)
    {
#ifndef __cplusplus
        return (*_jenv)->GetArrayLength(_jenv, array);
#else
    return _jenv->GetArrayLength(array);
#endif /* __cplusplus */
    }
    jobjArr NewObjectArray(jsize length, jcls elementClass,
                           jobj initialElement)
    {
#ifndef __cplusplus
        return (*_jenv)->NewObjectArray(_jenv, length, elementClass, initialElement);
#else
    return _jenv->NewObjectArray(length, elementClass, initialElement);
#endif /* __cplusplus */
    }
    jobj GetObjectArrayElement(jobjArr array, jsize index)
    {
#ifndef __cplusplus
        return (*_jenv)->GetObjectArrayElement(_jenv, array, index);
#else
    return _jenv->GetObjectArrayElement(array, index);
#endif /* __cplusplus */
    }
    void SetObjectArrayElement(jobjArr array, jsize index, jobj value)
    {
#ifndef __cplusplus
        (*_jenv)->SetObjectArrayElement(_jenv, array, index, value);
#else
    _jenv->SetObjectArrayElement(array, index, value);
#endif /* __cplusplus */
    }

    jboolArr NewBooleanArray(jsize length)
    {
#ifndef __cplusplus
        return (*_jenv)->NewBooleanArray(_jenv, length);
#else
    return _jenv->NewBooleanArray(length);
#endif /* __cplusplus */
    }
    jbyteArr NewByteArray(jsize length)
    {
#ifndef __cplusplus
        return (*_jenv)->NewByteArray(_jenv, length);
#else
    return _jenv->NewByteArray(length);
#endif /* __cplusplus */
    }
    jcharArr NewCharArray(jsize length)
    {
#ifndef __cplusplus
        return (*_jenv)->NewCharArray(_jenv, length);
#else
    return _jenv->NewCharArray(length);
#endif /* __cplusplus */
    }
    jshortArr NewShortArray(jsize length)
    {
#ifndef __cplusplus
        return (*_jenv)->NewShortArray(_jenv, length);
#else
    return _jenv->NewShortArray(length);
#endif /* __cplusplus */
    }
    jintArr NewIntArray(jsize length)
    {
#ifndef __cplusplus
        return (*_jenv)->NewIntArray(_jenv, length);
#else
    return _jenv->NewIntArray(length);
#endif /* __cplusplus */
    }
    jlongArr NewLongArray(jsize length)
    {
#ifndef __cplusplus
        return (*_jenv)->NewLongArray(_jenv, length);
#else
    return _jenv->NewLongArray(length);
#endif /* __cplusplus */
    }
    jfloatArr NewFloatArray(jsize length)
    {
#ifndef __cplusplus
        return (*_jenv)->NewFloatArray(_jenv, length);
#else
    return _jenv->NewFloatArray(length);
#endif /* __cplusplus */
    }
    jdoubleArr NewDoubleArray(jsize length)
    {
#ifndef __cplusplus
        return (*_jenv)->NewDoubleArray(_jenv, length);
#else
    return _jenv->NewDoubleArray(length);
#endif /* __cplusplus */
    }

    jbool *GetBooleanArrayElements(jboolArr array, jbool *isCopy)
    {
#ifndef __cplusplus
        return (*_jenv)->GetBooleanArrayElements(_jenv, array, isCopy);
#else
    return _jenv->GetBooleanArrayElements(array, isCopy);
#endif /* __cplusplus */
    }
    jbyte *GetByteArrayElements(jbyteArr array, jbool *isCopy)
    {
#ifndef __cplusplus
        return (*_jenv)->GetByteArrayElements(_jenv, array, isCopy);
#else
    return _jenv->GetByteArrayElements(array, isCopy);
#endif /* __cplusplus */
    }
    jchar *GetCharArrayElements(jcharArr array, jbool *isCopy)
    {
#ifndef __cplusplus
        return (*_jenv)->GetCharArrayElements(_jenv, array, isCopy);
#else
    return _jenv->GetCharArrayElements(array, isCopy);
#endif /* __cplusplus */
    }
    jshort *GetShortArrayElements(jshortArr array, jbool *isCopy)
    {
#ifndef __cplusplus
        return (*_jenv)->GetShortArrayElements(_jenv, array, isCopy);
#else
    return _jenv->GetShortArrayElements(array, isCopy);
#endif /* __cplusplus */
    }
    jint *GetIntArrayElements(jintArr array, jbool *isCopy)
    {
#ifndef __cplusplus
        return (*_jenv)->GetIntArrayElements(_jenv, array, isCopy);
#else
    return _jenv->GetIntArrayElements(array, isCopy);
#endif /* __cplusplus */
    }
    jlong *GetLongArrayElements(jlongArr array, jbool *isCopy)
    {
#ifndef __cplusplus
        return (*_jenv)->GetLongArrayElements(_jenv, array, isCopy);
#else
    return _jenv->GetLongArrayElements(array, isCopy);
#endif /* __cplusplus */
    }
    jfloat *GetFloatArrayElements(jfloatArr array, jbool *isCopy)
    {
#ifndef __cplusplus
        return (*_jenv)->GetFloatArrayElements(_jenv, array, isCopy);
#else
    return _jenv->GetFloatArrayElements(array, isCopy);
#endif /* __cplusplus */
    }
    jdouble *GetDoubleArrayElements(jdoubleArr array, jbool *isCopy)
    {
#ifndef __cplusplus
        return (*_jenv)->GetDoubleArrayElements(_jenv, array, isCopy);
#else
    return _jenv->GetDoubleArrayElements(array, isCopy);
#endif /* __cplusplus */
    }

    void ReleaseBooleanArrayElements(jboolArr array, jbool *elems,
                                     jint mode)
    {
#ifndef __cplusplus
        (*_jenv)->ReleaseBooleanArrayElements(_jenv, array, elems, mode);
#else
    _jenv->ReleaseBooleanArrayElements(array, elems, mode);
#endif /* __cplusplus */
    }
    void ReleaseByteArrayElements(jbyteArr array, jbyte *elems,
                                  jint mode)
    {
#ifndef __cplusplus
        (*_jenv)->ReleaseByteArrayElements(_jenv, array, elems, mode);
#else
    _jenv->ReleaseByteArrayElements(array, elems, mode);
#endif /* __cplusplus */
    }
    void ReleaseCharArrayElements(jcharArr array, jchar *elems,
                                  jint mode)
    {
#ifndef __cplusplus
        (*_jenv)->ReleaseCharArrayElements(_jenv, array, elems, mode);
#else
    _jenv->ReleaseCharArrayElements(array, elems, mode);
#endif /* __cplusplus */
    }
    void ReleaseShortArrayElements(jshortArr array, jshort *elems,
                                   jint mode)
    {
#ifndef __cplusplus
        (*_jenv)->ReleaseShortArrayElements(_jenv, array, elems, mode);
#else
    _jenv->ReleaseShortArrayElements(array, elems, mode);
#endif /* __cplusplus */
    }
    void ReleaseIntArrayElements(jintArr array, jint *elems,
                                 jint mode)
    {
#ifndef __cplusplus
        (*_jenv)->ReleaseIntArrayElements(_jenv, array, elems, mode);
#else
    _jenv->ReleaseIntArrayElements(array, elems, mode);
#endif /* __cplusplus */
    }
    void ReleaseLongArrayElements(jlongArr array, jlong *elems,
                                  jint mode)
    {
#ifndef __cplusplus
        (*_jenv)->ReleaseLongArrayElements(_jenv, array, elems, mode);
#else
    _jenv->ReleaseLongArrayElements(array, elems, mode);
#endif /* __cplusplus */
    }
    void ReleaseFloatArrayElements(jfloatArr array, jfloat *elems,
                                   jint mode)
    {
#ifndef __cplusplus
        (*_jenv)->ReleaseFloatArrayElements(_jenv, array, elems, mode);
#else
    _jenv->ReleaseFloatArrayElements(array, elems, mode);
#endif /* __cplusplus */
    }
    void ReleaseDoubleArrayElements(jdoubleArr array, jdouble *elems,
                                    jint mode)
    {
#ifndef __cplusplus
        (*_jenv)->ReleaseDoubleArrayElements(_jenv, array, elems, mode);
#else
    _jenv->ReleaseDoubleArrayElements(array, elems, mode);
#endif /* __cplusplus */
    }

    void GetBooleanArrayRegion(jboolArr array, jsize start, jsize len,
                               jbool *buf)
    {
#ifndef __cplusplus
        (*_jenv)->GetBooleanArrayRegion(_jenv, array, start, len, buf);
#else
    _jenv->GetBooleanArrayRegion(array, start, len, buf);
#endif /* __cplusplus */
    }
    void GetByteArrayRegion(jbyteArr array, jsize start, jsize len,
                            jbyte *buf)
    {
#ifndef __cplusplus
        (*_jenv)->GetByteArrayRegion(_jenv, array, start, len, buf);
#else
    _jenv->GetByteArrayRegion(array, start, len, buf);
#endif /* __cplusplus */
    }
    void GetCharArrayRegion(jcharArr array, jsize start, jsize len,
                            jchar *buf)
    {
#ifndef __cplusplus
        (*_jenv)->GetCharArrayRegion(_jenv, array, start, len, buf);
#else
    _jenv->GetCharArrayRegion(array, start, len, buf);
#endif /* __cplusplus */
    }
    void GetShortArrayRegion(jshortArr array, jsize start, jsize len,
                             jshort *buf)
    {
#ifndef __cplusplus
        (*_jenv)->GetShortArrayRegion(_jenv, array, start, len, buf);
#else
    _jenv->GetShortArrayRegion(array, start, len, buf);
#endif /* __cplusplus */
    }
    void GetIntArrayRegion(jintArr array, jsize start, jsize len,
                           jint *buf)
    {
#ifndef __cplusplus
        (*_jenv)->GetIntArrayRegion(_jenv, array, start, len, buf);
#else
    _jenv->GetIntArrayRegion(array, start, len, buf);
#endif /* __cplusplus */
    }
    void GetLongArrayRegion(jlongArr array, jsize start, jsize len,
                            jlong *buf)
    {
#ifndef __cplusplus
        (*_jenv)->GetLongArrayRegion(_jenv, array, start, len, buf);
#else
    _jenv->GetLongArrayRegion(array, start, len, buf);
#endif /* __cplusplus */
    }
    void GetFloatArrayRegion(jfloatArr array, jsize start, jsize len,
                             jfloat *buf)
    {
#ifndef __cplusplus
        (*_jenv)->GetFloatArrayRegion(_jenv, array, start, len, buf);
#else
    _jenv->GetFloatArrayRegion(array, start, len, buf);
#endif /* __cplusplus */
    }
    void GetDoubleArrayRegion(jdoubleArr array, jsize start, jsize len,
                              jdouble *buf)
    {
#ifndef __cplusplus
        (*_jenv)->GetDoubleArrayRegion(_jenv, array, start, len, buf);
#else
    _jenv->GetDoubleArrayRegion(array, start, len, buf);
#endif /* __cplusplus */
    }

    /* spec shows these without const; some jni.h do, some don't */
    void SetBooleanArrayRegion(jboolArr array, jsize start, jsize len,
                               const jbool *buf)
    {
#ifndef __cplusplus
        (*_jenv)->SetBooleanArrayRegion(_jenv, array, start, len, buf);
#else
    _jenv->SetBooleanArrayRegion(array, start, len, buf);
#endif /* __cplusplus */
    }
    void SetByteArrayRegion(jbyteArr array, jsize start, jsize len,
                            const jbyte *buf)
    {
#ifndef __cplusplus
        (*_jenv)->SetByteArrayRegion(_jenv, array, start, len, buf);
#else
    _jenv->SetByteArrayRegion(array, start, len, buf);
#endif /* __cplusplus */
    }
    void SetCharArrayRegion(jcharArr array, jsize start, jsize len,
                            const jchar *buf)
    {
#ifndef __cplusplus
        (*_jenv)->SetCharArrayRegion(_jenv, array, start, len, buf);
#else
    _jenv->SetCharArrayRegion(array, start, len, buf);
#endif /* __cplusplus */
    }
    void SetShortArrayRegion(jshortArr array, jsize start, jsize len,
                             const jshort *buf)
    {
#ifndef __cplusplus
        (*_jenv)->SetShortArrayRegion(_jenv, array, start, len, buf);
#else
    _jenv->SetShortArrayRegion(array, start, len, buf);
#endif /* __cplusplus */
    }
    void SetIntArrayRegion(jintArr array, jsize start, jsize len,
                           const jint *buf)
    {
#ifndef __cplusplus
        (*_jenv)->SetIntArrayRegion(_jenv, array, start, len, buf);
#else
    _jenv->SetIntArrayRegion(array, start, len, buf);
#endif /* __cplusplus */
    }
    void SetLongArrayRegion(jlongArr array, jsize start, jsize len,
                            const jlong *buf)
    {
#ifndef __cplusplus
        (*_jenv)->SetLongArrayRegion(_jenv, array, start, len, buf);
#else
    _jenv->SetLongArrayRegion(array, start, len, buf);
#endif /* __cplusplus */
    }
    void SetFloatArrayRegion(jfloatArr array, jsize start, jsize len,
                             const jfloat *buf)
    {
#ifndef __cplusplus
        (*_jenv)->SetFloatArrayRegion(_jenv, array, start, len, buf);
#else
    _jenv->SetFloatArrayRegion(array, start, len, buf);
#endif /* __cplusplus */
    }
    void SetDoubleArrayRegion(jdoubleArr array, jsize start, jsize len,
                              const jdouble *buf)
    {
#ifndef __cplusplus
        (*_jenv)->SetDoubleArrayRegion(_jenv, array, start, len, buf);
#else
    _jenv->SetDoubleArrayRegion(array, start, len, buf);
#endif /* __cplusplus */
    }

    jint RegisterNatives(jcls clazz, const JNINativeMethod *methods,
                         jint nMethods)
    {
#ifndef __cplusplus
        return (*_jenv)->RegisterNatives(_jenv, clazz, methods, nMethods);
#else
    return _jenv->RegisterNatives(clazz, methods, nMethods);
#endif /* __cplusplus */
    }
    jint UnregisterNatives(jcls clazz)
    {
#ifndef __cplusplus
        return (*_jenv)->UnregisterNatives(_jenv, clazz);
#else
    return _jenv->UnregisterNatives(clazz);
#endif /* __cplusplus */
    }
    jint MonitorEnter(jobj obj)
    {
#ifndef __cplusplus
        return (*_jenv)->MonitorEnter(_jenv, obj);
#else
    return _jenv->MonitorEnter(obj);
#endif /* __cplusplus */
    }
    jint MonitorExit(jobj obj)
    {
#ifndef __cplusplus
        return (*_jenv)->MonitorExit(_jenv, obj);
#else
    return _jenv->MonitorExit(obj);
#endif /* __cplusplus */
    }
    jint GetJVM(JVM **vm)
    {
#ifndef __cplusplus
        return (*_jenv)->GetJavaVM(_jenv, vm);
#else
    return _jenv->GetJavaVM(vm);
#endif /* __cplusplus */
    }

    void GetStringRegion(jstr str, jsize start, jsize len, jchar *buf)
    {
#ifndef __cplusplus
        (*_jenv)->GetStringRegion(_jenv, str, start, len, buf);
#else
    _jenv->GetStringRegion(str, start, len, buf);
#endif /* __cplusplus */
    }
    void GetStringUTFRegion(jstr str, jsize start, jsize len, char *buf)
    {
#ifndef __cplusplus
        (*_jenv)->GetStringUTFRegion(_jenv, str, start, len, buf);
#else
    _jenv->GetStringUTFRegion(str, start, len, buf);
#endif /* __cplusplus */
    }

    void *GetPrimitiveArrayCritical(jarr array, jbool *isCopy)
    {
#ifndef __cplusplus
        (*_jenv)->GetPrimitiveArrayCritical(_jenv, array, isCopy);
#else
    _jenv->GetPrimitiveArrayCritical(array, isCopy);
#endif /* __cplusplus */
    }
    void ReleasePrimitiveArrayCritical(jarr array, void *carray, jint mode)
    {
#ifndef __cplusplus
        (*_jenv)->ReleasePrimitiveArrayCritical(_jenv, array, carray, mode);
#else
    _jenv->ReleasePrimitiveArrayCritical(array, carray, mode);
#endif /* __cplusplus */
    }

    const jchar *GetStringCritical(jstr string, jbool *isCopy)
    {
#ifndef __cplusplus
        return (*_jenv)->GetStringCritical(_jenv, string, isCopy);
#else
    return _jenv->GetStringCritical(string, isCopy);
#endif /* __cplusplus */
    }
    void ReleaseStringCritical(jstr string, const jchar *carray)
    {
#ifndef __cplusplus
        (*_jenv)->ReleaseStringCritical(_jenv, string, carray);
#else
    _jenv->ReleaseStringCritical(string, carray);
#endif /* __cplusplus */
    }

    jweak NewWeakGlobalRef(jobj obj)
    {
#ifndef __cplusplus
        return (*_jenv)->NewWeakGlobalRef(_jenv, obj);
#else
    return _jenv->NewWeakGlobalRef(obj);
#endif /* __cplusplus */
    }
    void DeleteWeakGlobalRef(jweak obj)
    {
#ifndef __cplusplus
        (*_jenv)->DeleteWeakGlobalRef(_jenv, obj);
#else
    _jenv->DeleteWeakGlobalRef(obj);
#endif /* __cplusplus */
    }

    jbool ExceptionCheck()
    {
#ifndef __cplusplus
        return (*_jenv)->ExceptionCheck(_jenv);
#else
    return _jenv->ExceptionCheck();
#endif /* __cplusplus */
    }

    jobj NewDirectByteBuffer(void *address, jlong capacity)
    {
#ifndef __cplusplus
        return (*_jenv)->NewDirectByteBuffer(_jenv, address, capacity);
#else
    return _jenv->NewDirectByteBuffer(address, capacity);
#endif /* __cplusplus */
    }
    void *GetDirectBufferAddress(jobj buf)
    {
#ifndef __cplusplus
        (*_jenv)->GetDirectBufferAddress(_jenv, buf);
#else
    _jenv->GetDirectBufferAddress(buf);
#endif /* __cplusplus */
    }
    jlong GetDirectBufferCapacity(jobj buf)
    {
#ifndef __cplusplus
        return (*_jenv)->GetDirectBufferCapacity(_jenv, buf);
#else
    return _jenv->GetDirectBufferCapacity(buf);
#endif /* __cplusplus */
    }

    /* added in JNI 1.6 */
    jobjRefT GetObjectRefType(jobj obj)
    {
#ifndef __cplusplus
        return (*_jenv)->GetObjectRefType(_jenv, obj);
#else
    return _jenv->GetObjectRefType(obj);
#endif /* __cplusplus */
    }

#ifdef JNI_VERSION_1_8
    _JNI_IMPORT_OR_EXPORT_ jint JNICALL
    JNI_GetDefaultJVMInitArgs(void *args)
    {
        return JNI_GetDefaultJavaVMInitArgs(args);
    }

    _JNI_IMPORT_OR_EXPORT_ jint JNICALL
    JNI_CreateJVM(JVM **pvm, JNIEnv **penv, void *args)
    {
        return JNI_CreateJavaVM(pvm, penv, args);
    }

    _JNI_IMPORT_OR_EXPORT_ jint JNICALL
    JNI_GetCreatedJVMs(JVM **vmBuf, jsize bufLen, jsize *nVMs)
    {
        return JNI_GetCreatedJavaVMs(vmBuf, bufLen, nVMs);
    }
#endif /* JNI_VERSION_1_8 */

#ifdef __cplusplus
} /* namespace JNIX */
#endif /* __cplusplus */
