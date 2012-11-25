#include "minimardi_Utils.h"
#include "jni_constants.h"

#include "utils.h"


/*
 * Class:     minimardi_Utils
 * Method:    setTimeControl
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Utils_setTimeControl
  (JNIEnv *env, jclass cls, jint base, jint increment)
  {
    int result = set_time_control(base,increment);
    return result;
  }
  
  /*
 * Class:     minimardi_Utils
 * Method:    setCompTime
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Utils_setCompTime
  (JNIEnv *env, jclass cls, jint time)
  {
    int result = set_comp_time(time);
    return result;
  }
  
  /*
 * Class:     minimardi_Utils
 * Method:    setOppTime
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Utils_setOppTime
  (JNIEnv *env, jclass cls, jint time)
  {
    int result = set_opp_time(time);
    return result;
  }
  
  /*
 * Class:     minimardi_Utils
 * Method:    outOfTime
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_minimardi_Utils_outOfTime
  (JNIEnv *env, jclass cls)
  {
      int result = out_of_time();
      return result;
  }
  
  /*
 * Class:     minimardi_Utils
 * Method:    allocateTime
 * Signature: ()I
 */
JNIEXPORT jint JNICALL Java_minimardi_Utils_allocateTime
  (JNIEnv *env, jclass cls)
  {
     int result = allocate_time();
     return result;
  }
