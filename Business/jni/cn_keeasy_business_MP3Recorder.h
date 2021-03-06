/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class cn_keeasy_business_MP3Recorder */

#ifndef _Included_cn_keeasy_business_MP3Recorder
#define _Included_cn_keeasy_business_MP3Recorder
#ifdef __cplusplus
extern "C" {
#endif
#undef cn_keeasy_business_MP3Recorder_MSG_REC_STARTED
#define cn_keeasy_business_MP3Recorder_MSG_REC_STARTED 1L
#undef cn_keeasy_business_MP3Recorder_MSG_REC_STOPPED
#define cn_keeasy_business_MP3Recorder_MSG_REC_STOPPED 2L
#undef cn_keeasy_business_MP3Recorder_MSG_REC_PAUSE
#define cn_keeasy_business_MP3Recorder_MSG_REC_PAUSE 3L
#undef cn_keeasy_business_MP3Recorder_MSG_REC_RESTORE
#define cn_keeasy_business_MP3Recorder_MSG_REC_RESTORE 4L
#undef cn_keeasy_business_MP3Recorder_MSG_ERROR_GET_MIN_BUFFERSIZE
#define cn_keeasy_business_MP3Recorder_MSG_ERROR_GET_MIN_BUFFERSIZE -1L
#undef cn_keeasy_business_MP3Recorder_MSG_ERROR_CREATE_FILE
#define cn_keeasy_business_MP3Recorder_MSG_ERROR_CREATE_FILE -2L
#undef cn_keeasy_business_MP3Recorder_MSG_ERROR_REC_START
#define cn_keeasy_business_MP3Recorder_MSG_ERROR_REC_START -3L
#undef cn_keeasy_business_MP3Recorder_MSG_ERROR_AUDIO_RECORD
#define cn_keeasy_business_MP3Recorder_MSG_ERROR_AUDIO_RECORD -4L
#undef cn_keeasy_business_MP3Recorder_MSG_ERROR_AUDIO_ENCODE
#define cn_keeasy_business_MP3Recorder_MSG_ERROR_AUDIO_ENCODE -5L
#undef cn_keeasy_business_MP3Recorder_MSG_ERROR_WRITE_FILE
#define cn_keeasy_business_MP3Recorder_MSG_ERROR_WRITE_FILE -6L
#undef cn_keeasy_business_MP3Recorder_MSG_ERROR_CLOSE_FILE
#define cn_keeasy_business_MP3Recorder_MSG_ERROR_CLOSE_FILE -7L
/*
 * Class:     cn_keeasy_business_MP3Recorder
 * Method:    init
 * Signature: (IIIII)V
 */
JNIEXPORT void JNICALL Java_cn_keeasy_business_MP3Recorder_init
  (JNIEnv *, jclass, jint, jint, jint, jint, jint);

/*
 * Class:     cn_keeasy_business_MP3Recorder
 * Method:    encode
 * Signature: ([S[SI[B)I
 */
JNIEXPORT jint JNICALL Java_cn_keeasy_business_MP3Recorder_encode
  (JNIEnv *, jclass, jshortArray, jshortArray, jint, jbyteArray);

/*
 * Class:     cn_keeasy_business_MP3Recorder
 * Method:    flush
 * Signature: ([B)I
 */
JNIEXPORT jint JNICALL Java_cn_keeasy_business_MP3Recorder_flush
  (JNIEnv *, jclass, jbyteArray);

/*
 * Class:     cn_keeasy_business_MP3Recorder
 * Method:    close
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_cn_keeasy_business_MP3Recorder_close
  (JNIEnv *, jclass);

#ifdef __cplusplus
}
#endif
#endif
