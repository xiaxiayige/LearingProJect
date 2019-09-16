#include <jni.h>
#include <string>
#include "gif_lib.h"
#include <stdio.h>
#include "iostream"
#include <android/log.h>
#include <android/bitmap.h>

#define ANDROID_LOG "=====>"
#define LOGI(...)  __android_log_print(ANDROID_LOG_INFO,ANDROID_LOG,__VA_ARGS__)
#define  argb(a, r, g, b) ( ((a) & 0xff) << 24 ) | ( ((b) & 0xff) << 16 ) | ( ((g) & 0xff) << 8 ) | ((r) & 0xff)

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
void drawableBitmap(GifFileType *gifFileType, GifInfoBean *infoBean, AndroidBitmapInfo info,
                    void *addrPtr) {

    SavedImage savedImage = gifFileType->SavedImages[infoBean->currentPosition];

    //bitmap图片的内存地址
    int *px = (int *) addrPtr;
    int pointPixel;
    GifImageDesc frameInfo = savedImage.ImageDesc;
    //LWZ压缩数据后的数据
    GifByteType gifByteType;
    //获取保存颜色的数据字典
    ColorMapObject *colorMapObject = frameInfo.ColorMap ? frameInfo.ColorMap
                                                        : gifFileType->SColorMap;
    //移动到第一行的地址
    px = (int *) ((char *) px + info.stride * frameInfo.Top);
    //每一行的内存地址
    int *line;
    for (int y = frameInfo.Top; y < frameInfo.Top + frameInfo.Height; ++y) {
        line = px;
        for (int x = frameInfo.Left; x < frameInfo.Left + frameInfo.Width; ++x) {
            //计算像素
            pointPixel = (y - frameInfo.Top) * frameInfo.Width + (x - frameInfo.Left);
            //从lzw压缩数据中还原出正确的数据
            gifByteType = savedImage.RasterBits[pointPixel];
            //从数据字典总读取对应的颜色
            GifColorType gifColorType = colorMapObject->Colors[gifByteType];
            line[x] = argb(255, gifColorType.Red, gifColorType.Green, gifColorType.Blue);
        }
        //下一行
        px = (int *) ((char *) px + info.stride);
    }
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_giflibdemo_GifManager_getHeight(JNIEnv *env, jobject instance, jlong gifAddress) {

    GifFileType *GifFile = (GifFileType *) gifAddress;
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
//    //挂起/加载gif
    DGifSlurp(gifFileType);
    LOGI("SWidth = %d , SHeight = %d  ImageCount = %d", gifFileType->SWidth, gifFileType->SHeight,
         gifFileType->ImageCount);
    GifInfoBean *infoBean = (GifInfoBean *) malloc(sizeof(GifInfoBean));
//    //申请一块内存
    memset(infoBean, 0, sizeof(GifInfoBean));
    gifFileType->UserData = infoBean;
    infoBean->imageCount = gifFileType->ImageCount;
    infoBean->currentPosition = 0;
    env->ReleaseStringUTFChars(gifFile_, gifFile);
    return (jlong) gifFileType;
}

extern "C"
JNIEXPORT jint JNICALL
Java_com_example_giflibdemo_GifManager_updateFrame(JNIEnv *env, jobject instance, jlong gifAddress,
                                                   jobject bitmap) {


    GifFileType *GifFile = (GifFileType *) gifAddress;

    GifInfoBean *infoBean = (GifInfoBean *) GifFile->UserData;
    infoBean->currentPosition += 1;

    if (infoBean->currentPosition >= infoBean->imageCount) {
        infoBean->currentPosition = 0;
    }

    AndroidBitmapInfo bitmapInfo;

    AndroidBitmap_getInfo(env, bitmap, &bitmapInfo);
    //Bitmap内存地址
    void *addrPtr;

    AndroidBitmap_lockPixels(env, bitmap, &addrPtr);

    drawableBitmap(GifFile, infoBean, bitmapInfo, addrPtr);

    AndroidBitmap_unlockPixels(env, bitmap);

    int delayTime;
    SavedImage image = GifFile->SavedImages[infoBean->currentPosition];
    ExtensionBlock *ep;
    for (ep = image.ExtensionBlocks;
         ep < image.ExtensionBlocks + image.ExtensionBlockCount; ep++) {
        //拿到图形控制块数据
        if (ep->Function == GRAPHICS_EXT_FUNC_CODE) {
            GraphicsControlBlock gcb;
            DGifExtensionToGCB(ep->ByteCount, ep->Bytes, &gcb);
            delayTime = gcb.DelayTime*10;
            break;
        }
    }

    //返回下一帧的延迟时间
    return delayTime;

}