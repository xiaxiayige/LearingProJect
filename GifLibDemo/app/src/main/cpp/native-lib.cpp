#include <jni.h>
#include <string>
#include "gif_lib.h"
#include <stdio.h>
#include "iostream"
#include <android/log.h>
#include <android/bitmap.h>

#define ANDROID_LOG "=====>"
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO,ANDROID_LOG,__VA_ARGS__)
#define  argb(a,r,g,b) ( ((a) & 0xff) << 24 ) | ( ((b) & 0xff) << 16 ) | ( ((g) & 0xff) << 8 ) | ((r) & 0xff)

typedef struct GifInfoBean {
    int imageCount;  //视频总帧数
    int currentPosition; //当前是第几帧
} GifInfoBean;

void drawableBitmap(GifFileType *pType, GifInfoBean *pBean, void *pVoid);

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_giflibdemo_GifManager_getWidth(JNIEnv *env, jobject instance, jlong gifAddress) {

    GifFileType *GifFile = (GifFileType *) gifAddress;
    return GifFile->SWidth;

}

/***
* 绘制真实的bitmap
* @param pType
* @param pBean
* @param pVoid
*/
void drawableBitmap(GifFileType *gifFileType, GifInfoBean *infoBean, AndroidBitmapInfo info,void *addrPtr) {

    SavedImage savedImage = gifFileType->SavedImages[infoBean->currentPosition];

    //整幅图片的首地址
    int* px = (int *)addrPtr;
    int  pointPixel;
    GifImageDesc frameInfo = savedImage.ImageDesc;
    GifByteType  gifByteType;//压缩数据
//    rgb数据     压缩工具
    ColorMapObject* colorMapObject=frameInfo.ColorMap;
//    Bitmap 往下偏移
    px = (int *) ((char*)px + info.stride * frameInfo.Top);
    //    每一行的首地址
    int *line;
    for (int y = frameInfo.Top; y <frameInfo.Top+frameInfo.Height ; ++y) {
        line=px;
        for (int x = frameInfo.Left; x <frameInfo.Left + frameInfo.Width ; ++x) {
//            拿到每一个坐标的位置  索引    ---》  数据
            pointPixel=  (y-frameInfo.Top)*frameInfo.Width+(x-frameInfo.Left);
//            索引   rgb   LZW压缩  字典   （）缓存在一个字典
//解压
            gifByteType= savedImage.RasterBits[pointPixel];
            GifColorType gifColorType=colorMapObject->Colors[gifByteType];
            line[x]=argb(255,gifColorType.Red, gifColorType.Green, gifColorType.Blue);
        }
        px = (int *) ((char*)px + info.stride);
    }



}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_giflibdemo_GifManager_getHeight(JNIEnv *env, jobject instance, jlong gifAddress) {

    GifFileType *GifFile = (GifFileType *) gifAddress;
    std::cout << GifFile->SWidth;

    LOGI("GifFile->SWidth=%d", GifFile->SWidth);

    return GifFile->SHeight;
}

extern "C"
JNIEXPORT jlong JNICALL
Java_com_example_giflibdemo_GifManager_loadGifFile(JNIEnv *env, jobject instance,
                                                   jstring gifFile_) {
    const char *gifFile = env->GetStringUTFChars(gifFile_, 0);
    int *Error;
    GifFileType *gifFileType = DGifOpenFileName(gifFile, Error);
    //挂起/加载gif
    DGifSlurp(gifFileType);

    GifInfoBean *infoBean = (GifInfoBean *) malloc(sizeof(GifInfoBean));
    //申请一块内存
    memset(infoBean, 0, sizeof(GifInfoBean));
    gifFileType->UserData = infoBean;

    infoBean->imageCount = gifFileType->ImageCount;
    infoBean->currentPosition = 0;

//    for (int i = 0; i < gifFileType->ImageCount; ++i) {
//        SavedImage image = gifFileType->SavedImages[i];
//        ExtensionBlock *ep;
//        for (ep = image.ExtensionBlocks;
//             ep < image.ExtensionBlocks + image.ExtensionBlockCount; ep++) {
//            if (ep->Function == GRAPHICS_EXT_FUNC_CODE) {
//                GraphicsControlBlock gcb;
//                DGifExtensionToGCB(ep->ByteCount, ep->Bytes, &gcb);
//                infoBean->dealys[i] = gcb.DelayTime;
//                break;
//            }
//        }
//    }
    env->ReleaseStringUTFChars(gifFile_, gifFile);
    return (jlong) gifFileType;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_giflibdemo_GifManager_updateFrame(JNIEnv *env, jobject instance, jlong gifAddress,
                                                   jobject bitmap) {


    GifFileType *GifFile = (GifFileType *) gifAddress;

    GifInfoBean *infoBean = (GifInfoBean *) GifFile->UserData;

    if (infoBean->currentPosition >= infoBean->imageCount) {
        infoBean->currentPosition = 0;
    }

    AndroidBitmapInfo bitmapInfo;

    AndroidBitmap_getInfo(env, bitmap, &bitmapInfo);

    void *addrPtr;

    AndroidBitmap_lockPixels(env, bitmap, &addrPtr);

    drawableBitmap(GifFile, infoBean,bitmapInfo, addrPtr);


    AndroidBitmap_unlockPixels(env, bitmap);

    int delayTime;
    SavedImage image = GifFile->SavedImages[infoBean->currentPosition];
    ExtensionBlock *ep;
    for (ep = image.ExtensionBlocks;
         ep < image.ExtensionBlocks + image.ExtensionBlockCount; ep++) {
        if (ep->Function == GRAPHICS_EXT_FUNC_CODE) {
            GraphicsControlBlock gcb;
            DGifExtensionToGCB(ep->ByteCount, ep->Bytes, &gcb);
            delayTime = gcb.DelayTime;
            break;
        }
    }
    infoBean->currentPosition += 1;

    return delayTime;

}