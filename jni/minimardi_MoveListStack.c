#include "minimardi_MoveListStack.h"

#include "jni_constants.h"

#include "move_list.h"

/*
 * Class:     minimardi_MoveListStack
 * Method:    nativeCreate
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_minimardi_MoveListStack_nativeCreate
  (JNIEnv *env, jobject thisObject)
  {
       move_list_stack mls = new_move_list_stack();  
       jlong result = (jlong)mls;
       return result;
  }
  
  /*
 * Class:     minimardi_MoveListStack
 * Method:    nativeFree
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_minimardi_MoveListStack_nativeFree
  (JNIEnv *env, jobject thisobject, jlong cptr)
  {
        // do nothing
  }
  
  /*
 * Class:     minimardi_MoveListStack
 * Method:    newHistMovesNative
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_minimardi_MoveListStack_newHistMovesNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
    move_list_stack mls = (move_list_stack)cptr;
    move_list ml = new_hist_moves(mls);
    return ml;
  }
  
/*
 * Class:     minimardi_MoveListStack
 * Method:    pushNative
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_minimardi_MoveListStack_pushNative
  (JNIEnv *env, jclass cls, jlong cptr, jint mov, jint score)
  {
    move_list_stack mls = (move_list_stack)cptr;
    push(mls,mov,score);
  }
  
  
  /*
 * Class:     minimardi_MoveListStack
 * Method:    popNative
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_minimardi_MoveListStack_popNative
  (JNIEnv * env, jclass cls, jlong cptr, jint old_sp)
  {
    move_list_stack mls = (move_list_stack)cptr;
    move m = pop(mls,old_sp);
    return m;
  }
  
  /*
 * Class:     minimardi_MoveListStack
 * Method:    lookNative
 * Signature: (JII)I
 */
JNIEXPORT jint JNICALL Java_minimardi_MoveListStack_lookNative
  (JNIEnv *env, jclass cls, jlong cptr, jint old_sp, jint look_at)
  {
    move_list_stack mls = (move_list_stack)cptr;
    move m = look(mls,old_sp,look_at);
    return m;
  }
  
  /*
 * Class:     minimardi_MoveListStack
 * Method:    lookAtHistNative
 * Signature: (JII)I
 */
JNIEXPORT jint JNICALL Java_minimardi_MoveListStack_lookAtHistNative
  (JNIEnv *env, jclass cls, jlong cptr, jint old_sp, jint look_at)
  {
    move_list_stack mls = (move_list_stack)cptr;
    move m = look_at_hist(mls,old_sp,look_at);
    return m;
  }
  
  /*
 * Class:     minimardi_MoveListStack
 * Method:    initMoveListNative
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_minimardi_MoveListStack_initMoveListNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
      move_list_stack mls = (move_list_stack)cptr;
      init_move_list(mls);      
  }
  
  /*
 * Class:     minimardi_MoveListStack
 * Method:    pushHistNative
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_minimardi_MoveListStack_pushHistNative
  (JNIEnv *env, jclass cls, jlong cptr, jint mov)
  {
      move_list_stack mls = (move_list_stack)cptr;
      push_hist(mls,mov);
  }
  
  /*
 * Class:     minimardi_MoveListStack
 * Method:    popHistNative
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_minimardi_MoveListStack_popHistNative
  (JNIEnv *env, jclass cls, jlong cptr, jint old_sp)
  {
    move_list_stack mls = (move_list_stack)cptr;
    move m = pop_hist(mls,old_sp);
    return m;
  }
  
  /*
 * Class:     minimardi_MoveListStack
 * Method:    lookHistNative
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_minimardi_MoveListStack_lookHistNative
  (JNIEnv *env, jclass cls, jlong cptr, jint old_sp)
  {
    move_list_stack mls = (move_list_stack)cptr;
    move m = look_hist(mls,old_sp);
    return m;
  }
  
  /*
 * Class:     minimardi_MoveListStack
 * Method:    freeMoveListNative
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_minimardi_MoveListStack_freeMoveListNative
  (JNIEnv *env, jclass cls, jlong cptr, jint old_sp)
  {
    move_list_stack mls = (move_list_stack)cptr;
    free_move_list(mls,old_sp);
  }
  
  /*
 * Class:     minimardi_MoveListStack
 * Method:    sortMoveScoresNative
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_minimardi_MoveListStack_sortMoveScoresNative
  (JNIEnv *env, jclass cls, jlong cptr, jint moves)
  {
    move_list_stack mls = (move_list_stack)cptr;
    sort_move_scores(mls,moves);
  }
  
  /*
 * Class:     minimardi_MoveListStack
 * Method:    lookStackNative
 * Signature: (JII)I
 */
JNIEXPORT jint JNICALL Java_minimardi_MoveListStack_lookStackNative
  (JNIEnv *env, jclass cls, jlong cptr, jint old_sp, jint look_at)
  {
    move_list_stack mls = (move_list_stack)cptr;
    move m = look_stack(mls,old_sp,look_at);
    return m;
  }