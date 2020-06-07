/**
 * @author Liangcheng Juves
 * Created at 2020/6/7 18:22
 */
#include <jni.h>
#include <string>

extern "C" {
JNIEXPORT jstring JNICALL
Java_com_meyoustu_amuse_single_MainActivity_stringFromJNI(JNIEnv *env, jobject/* this */) {
    std::string hello = std::string("hello,amuse");
    return env->NewStringUTF(hello.c_str());
}
}
