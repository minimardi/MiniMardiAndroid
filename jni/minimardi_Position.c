#include "minimardi_Position.h"
#include "jni_constants.h"

#include "position.h"
/*
 * Class:     minimardi_Position
 * Method:    nativeFree
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_minimardi_Position_nativeFree
  (JNIEnv *env, jobject thisobject, jlong cptr)
  {
        position p = (position)cptr;
        free_position(p);
  }
  
  /*
 * Class:     minimardi_Position
 * Method:    nativeCreate
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_minimardi_Position_nativeCreate
  (JNIEnv * env, jobject thisobject)
  {
        position p = new_chess_position();
        return (jlong)p;
  }
  
 /*
 * Class:     minimardi_Position
 * Method:    nativeNewChessPositionFen
 * Signature: (Ljava/lang/String;)J
 */
 
JNIEXPORT jlong JNICALL Java_minimardi_Position_nativeNewChessPositionFen
  (JNIEnv * env, jobject thisobject, jstring jstr)
  {
      position pos = NULL;
      char* fen =  JStringToCCharPtr(env,jstr);
      pos = new_chess_position_fen(fen);
      free(fen);
      return (jlong)pos;
  }
  
/*
 * Class:     minimardi_Position
 * Method:    moveGeneratorNative
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Position_moveGeneratorNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
    position p = (position)cptr;
    move_list ml = move_generator(p);        
    return ml;
  }
/*
 * Class:     minimardi_Position
 * Method:    getZobristKeyNative
 * Signature: (J)I
 */
JNIEXPORT jlong JNICALL Java_minimardi_Position_getZobristKeyNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
    position p = (position)cptr;
    U64 key = getZobristKey(p);
    return (jlong)key;
  }
  
/*
 * Class:     minimardi_Position
 * Method:    getToMoveNative
 * Signature: (J)C
 */
JNIEXPORT jchar JNICALL Java_minimardi_Position_getToMoveNative
  (JNIEnv * env, jclass cls, jlong cptr)
  {
    position p = (position)cptr;
    char c = getToMove(p);
    return c;
  }

/*
 * Class:     minimardi_Position
 * Method:    getMaterialNative
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Position_getMaterialNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
    position p = (position)cptr;
    int mat = getMaterial(p);
    return mat;
  }
/*
 * Class:     minimardi_Position
 * Method:    getPosMovesNative
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Position_getPosMovesNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
    position p = (position)cptr;
    move_list ml = getPosMoves(p);
    return ml;
  }
  
  /*
 * Class:     minimardi_Position
 * Method:    getOutOfBookNative
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Position_getOutOfBookNative
  (JNIEnv * env, jclass cls, jlong cptr)
  {
      position p = (position)cptr;
      int ret = getOutOfBook(p);
      return ret;
  }
  /*
 * Class:     minimardi_Position
 * Method:    setToMoveNative
 * Signature: (CJ)V
 */
JNIEXPORT void JNICALL Java_minimardi_Position_setToMoveNative
  (JNIEnv *env, jclass cls, jchar tomove, jlong cptr)
  {
    position p = (position)cptr;
    setToMove(p,tomove);
  }

/*
 * Class:     minimardi_Position
 * Method:    doMoveNative
 * Signature: (IJ)V
 */
JNIEXPORT void JNICALL Java_minimardi_Position_doMoveNative
  (JNIEnv *env, jclass cls, jint move, jlong cptr)
  {
    position p = (position)cptr;
    do_move(p,move);
  }

/*
 * Class:     minimardi_Position
 * Method:    undoMoveNative
 * Signature: (IJ)V
 */
JNIEXPORT void JNICALL Java_minimardi_Position_undoMoveNative
  (JNIEnv *env, jclass cls, jint move, jlong cptr)
  {
    position p = (position)cptr;
    undo_move(p,move);
  }
  
/*
 * Class:     minimardi_Position
 * Method:    debugPrintCapListNative
 * Signature: (IJ)V
 */
JNIEXPORT void JNICALL Java_minimardi_Position_debugPrintCapListNative
  (JNIEnv *env, jclass cls, jint movelist, jlong cptr)
  {
    position p = (position)cptr;
    debug_print_cap_list(movelist,p);
  }
  
  /*
 * Class:     minimardi_Position
 * Method:    printPositionNative
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_minimardi_Position_printPositionNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
    position p = (position)cptr;
    print_position(p);
  }

/*
 * Class:     minimardi_Position
 * Method:    legalOpponentMoveNative
 * Signature: (IJ)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Position_legalOpponentMoveNative
  (JNIEnv *env, jclass cls, jint mov, jlong cptr)
  {
    position p = (position)cptr;
    move result = legal_opponent_move(p,mov);
    return result;
  }
  
  /*
 * Class:     minimardi_Position
 * Method:    drawOrCheckmateNative
 * Signature: (IJ)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Position_drawOrCheckmateNative
  (JNIEnv *env, jclass cls,jlong cptr)
  {
    position p = (position)cptr;
    int ret = draw_or_checkmate(p);
    return ret;
  }
  
  /*
 * Class:     minimardi_Position
 * Method:    inCheckIllegalNative
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_minimardi_Position_inCheckIllegalNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
    position p = (position)cptr;
    int ret = in_check_illegal(p);
    return ret;
  }
  
  /*
 * Class:     minimardi_Position
 * Method:    getOpponentSideNative
 * Signature: (J)C
 */
JNIEXPORT jchar JNICALL Java_minimardi_Position_getOpponentSideNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
    position p = (position)cptr;
    piece side = getOpponentSide(p);
    return side;
  }
  
  /*
 * Class:     minimardi_Position
 * Method:    setSideWhiteNative
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_minimardi_Position_setSideWhiteNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
    position p = (position)cptr;
    setSideWhite(p);        
  }
  
  /*
 * Class:     minimardi_Position
 * Method:    setSideBlackNative
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_minimardi_Position_setSideBlackNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
    position p = (position)cptr;
    setSideBlack(p);        
  }

/*
 * Class:     minimardi_Position
 * Method:    getCompSideNative
 * Signature: (J)C
 */
JNIEXPORT jchar JNICALL Java_minimardi_Position_getCompSideNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
    position p = (position)cptr;
    piece side = getCompSide(p);
    return side;
  }
  
 /*
 * Class:     minimardi_Position
 * Method:    isCompBlackNative
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_minimardi_Position_isCompBlackNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
      position p = (position)cptr;
      int ret = isCompBlack(p);
      return ret;
  }

/*
 * Class:     minimardi_Position
 * Method:    isGameOverNative
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_minimardi_Position_isGameOverNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
      position p = (position)cptr;
      int go = isGameOver(p);
      return go;
  }

/*
 * Class:     minimardi_Position
 * Method:    getValueNative
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Position_getValueNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
    position p = (position)cptr;
    int v = getValue(p);
    return v;     
  }
  
/*
 * Class:     minimardi_Position
 * Method:    getNodesNative
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Position_getNodesNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
    position p = (position)cptr;
    int nodes = getNodes(p);
    return nodes;
  }  
  
  /*
 * Class:     minimardi_Position
 * Method:    setValueNative
 * Signature: (IIJ)V
 */
JNIEXPORT void JNICALL Java_minimardi_Position_setValueNative
  (JNIEnv *env, jclass cls, jint value, jint nodes, jlong cptr)
  {
    position p = (position)cptr;
    setValue(p,value,nodes);
  }

/*
 * Class:     minimardi_Position
 * Method:    setOutOfBookNative
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_minimardi_Position_setOutOfBookNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
    position p = (position)cptr;
    setOutOfBook(p);
  }

/*
 * Class:     minimardi_Position
 * Method:    inCheckNative
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_minimardi_Position_inCheckNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
    position p = (position)cptr;
    jboolean ret = in_check(p);
    return ret;
  }
  
  
/*
 * Class:     minimardi_Position
 * Method:    repSearchNative
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Position_repSearchNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
    position p = (position)cptr;
    int result = rep_search(p);
    return result;
  }
  
  /*
 * Class:     minimardi_Position
 * Method:    getPlyNative
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Position_getPlyNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
    position p = (position)cptr;
    int result = getPly(p);
    return result;
  }
  
  
  /*
 * Class:     minimardi_Position
 * Method:    whiteIsCastledNative
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_minimardi_Position_whiteIsCastledNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
    position p = (position)cptr;
    int result = white_is_castled(p);
    return result;
  }
  
  /*
 * Class:     minimardi_Position
 * Method:    blackIsCastledNative
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_minimardi_Position_blackIsCastledNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
    position p = (position)cptr;
    int result = black_is_castled(p);
    return result;
  }
    
  /*
 * Class:     minimardi_Position
 * Method:    lastMoveNullNative
 * Signature: (J)Z
 */
JNIEXPORT jboolean JNICALL Java_minimardi_Position_lastMoveNullNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
     position p = (position)cptr;
     int result = last_move_null(p);
     return result;
  }
  
  /*
 * Class:     minimardi_Position
 * Method:    piecesNative
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Position_piecesNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
    position p = (position)cptr;
    int result = pieces(p);
    return result;
  }
  
  
  
  /*
 * Class:     minimardi_Position
 * Method:    getHistHeuristicNative
 * Signature: (J)Lminimardi/HistoryHeuristic;
 */
JNIEXPORT jobject JNICALL Java_minimardi_Position_getHistHeuristicNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
    position p = (position)cptr;
    history_heuristic hh = get_hist_heuristic(p);
    static jmethodID s_cid = 0;
    static jclass s_cls = 0;
    jobject ret = 0;
    if(!s_cid || s_cls)
    {
        jclass cls = (*env)->FindClass(env,HISTORY_HEURISTIC_CLASS_DESCRIPTOR);
        jthrowable exc = (*env)->ExceptionOccurred(env);
        if(exc)
        {
            return 0;
        }
        s_cls = (jclass)(*env)->NewGlobalRef(env,cls);
        (*env)->DeleteLocalRef(env,cls);
        
        s_cid = (*env)->GetMethodID(env,s_cls,JNI_CONSTRUCTOR,HISTORY_HEURISTIC_CONSTRUCTOR);
        exc = (*env)->ExceptionOccurred(env);
        if(exc)
        {
            return 0;
        }        
    }
    
    ret = (*env)->NewObject(env,s_cls,s_cid,(jlong)hh);
    return ret;        
    
  }
  
 /*
 * Class:     minimardi_Position
 * Method:    getMoveStackNative
 * Signature: (J)Lminimardi/MoveListStack;
 */
JNIEXPORT jobject JNICALL Java_minimardi_Position_getMoveStackNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
      position p = (position)cptr;
      move_list ml = get_move_stack(p);
      static jmethodID s_cid = 0;
      static jclass s_cls = 0;
      jobject ret = 0;
      if(!s_cid || s_cls)
      {
          jclass cls = (*env)->FindClass(env,MOVE_LIST_STACK_CLASS_DESCRIPTOR);
          jthrowable exc = (*env)->ExceptionOccurred(env);
          if(exc)
          {
              return 0;
          }
          s_cls = (jclass)(*env)->NewGlobalRef(env,cls);
          (*env)->DeleteLocalRef(env,cls);

          s_cid = (*env)->GetMethodID(env,s_cls,JNI_CONSTRUCTOR,MOVE_LIST_STACK_CONSTRUCTOR);
          exc = (*env)->ExceptionOccurred(env);
          if(exc)
          {
              return 0;
          }        
      }

      ret = (*env)->NewObject(env,s_cls,s_cid,(jlong)ml);
      return ret;        
  }

  /*
 * Class:     minimardi_Position
 * Method:    getBoardNative
 * Signature: (J)Lminimardi/Board;
 */
JNIEXPORT jobject JNICALL Java_minimardi_Position_getBoardNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
    position p = (position)cptr;
    board b = p->b;
    static jmethodID s_cid = 0;
    static jclass s_cls = 0;
    jobject ret = 0;
    if(!s_cid || s_cls)
    {
        jclass cls = (*env)->FindClass(env,BOARD_CLASS_DESCRIPTOR);
        jthrowable exc = (*env)->ExceptionOccurred(env);
        if(exc)
        {
            return 0;
        }
        s_cls = (jclass)(*env)->NewGlobalRef(env,cls);
        (*env)->DeleteLocalRef(env,cls);
        
        s_cid = (*env)->GetMethodID(env,s_cls,JNI_CONSTRUCTOR,BOARD_CONSTRUCTOR);
        exc = (*env)->ExceptionOccurred(env);
        if(exc)
        {
            return 0;
        }        
    }
    
    ret = (*env)->NewObject(env,s_cls,s_cid,(jlong)b);
    return ret;        
    
  }
  