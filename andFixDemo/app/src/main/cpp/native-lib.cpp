#include <jni.h>
#include <string>
#include "art_method.h"

extern "C" JNIEXPORT jstring JNICALL
Java_com_xiaxiayige_andfixdemo_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


extern "C"
JNIEXPORT void JNICALL
Java_com_xiaxiayige_andfixdemo_MainActivity_replaceMethod(JNIEnv *env, jobject instance,
                                                          jobject srcMethod,
                                                          jobject destMethods) {


    art::ArtMethod *wrongMethod =(art::ArtMethod *) env->FromReflectedMethod(srcMethod);
    art::ArtMethod *rightMethod =(art::ArtMethod *) env->FromReflectedMethod(destMethods);


    wrongMethod->access_flags_=rightMethod->access_flags_;
    wrongMethod->dex_code_item_offset_=rightMethod->dex_code_item_offset_;
    wrongMethod->dex_method_index_=rightMethod->dex_method_index_;
    wrongMethod->method_index_=rightMethod->method_index_;
    wrongMethod->hotness_count_=rightMethod->hotness_count_;

}