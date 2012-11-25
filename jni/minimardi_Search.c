#include "minimardi_Search.h"
#include "jni_constants.h"

#include "position.h"
#include "search.h"


/*
 * Class:     minimardi_Search
 * Method:    bestMove
 * Signature: (Lminimardi/Position;II)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Search_bestMove
(JNIEnv *env, jclass cls, jobject pos, jint depth, jint time_ply)
  {
    
    position p = 0;
    jlong cptr  = 0;
    move result = 0;
    cptr = GetHandle(env,pos);
    p = (position)cptr;    
    result = bestMove(p,depth,time_ply);    
    return result;
  }
  
  
/*
 * Class:     minimardi_Search
 * Method:    perft
 * Signature: (Lminimardi/Position;I)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Search_perft
  (JNIEnv *env, jclass cls, jobject pos, jint depth)
  {
    jlong cptr  = 0;
    position p = 0;
    int result = 0;
    cptr = GetHandle(env,pos);
    p = (position)cptr;
    result = perft(p,depth);
    return result;
        
  }  