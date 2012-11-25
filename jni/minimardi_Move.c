#include "minimardi_Move.h"

#include "jni_constants.h"

#include "move.h"
/*
 * Class:     minimardi_Move
 * Method:    setMoveCastle
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Move_setMoveCastle
  (JNIEnv * env, jclass cls, jint mov)
  {
    move m = set_mov_castle(mov);
    return m;
  }
  
  /*
 * Class:     minimardi_Move
 * Method:    newMoveWithProm
 * Signature: (IIC)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Move_newMoveWithProm
  (JNIEnv *env, jclass cls, jint src, jint dest, jchar prom_piece)
  {
     move m = new_move_with_prom(src,dest,prom_piece);
     return m;
  }
  
  /*
 * Class:     minimardi_Move
 * Method:    newMoveWithPassant
 * Signature: (IIC)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Move_newMoveWithPassant
  (JNIEnv *env, jclass cls, jint src, jint dest, jchar cap)
  {
      move m = new_move_with_cap(src,dest,cap);
      return m;
  }
  
  /*
 * Class:     minimardi_Move
 * Method:    newMoveAll
 * Signature: (IICCII)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Move_newMoveAll
  (JNIEnv *env, jclass cls, jint src, jint dest, jchar cap, jchar prom, jint ca, jint pa)
  {
       move m = new_move_all(src,dest,cap,prom,ca,pa);  
       return m;
  }
  
  /*
 * Class:     minimardi_Move
 * Method:    printMoveAlg
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_minimardi_Move_printMoveAlg
  (JNIEnv *env, jclass cls, jint mov)
  {
    print_move_alg(mov);
  }
  
  /*
 * Class:     minimardi_Move
 * Method:    printMove
 * Signature: (I)V
 */
JNIEXPORT void JNICALL Java_minimardi_Move_printMove
  (JNIEnv * env, jclass cls, jint mov)
  {
    print_move(mov);
  }
  
  /*
 * Class:     minimardi_Move
 * Method:    moveToAlg
 * Signature: (I[C)V
 */
JNIEXPORT jstring JNICALL Java_minimardi_Move_moveToAlg
(JNIEnv *env, jclass cls, jint mov)
  {
    jstring str = 0;
    static char buf[6];
    memset(buf, 0, sizeof(buf));
    move_to_alg(mov,buf);
    str = (*env)->NewStringUTF(env,buf);
    return str;
    
  }
  
  /*
 * Class:     minimardi_Move
 * Method:    algToMove
 * Signature: (Ljava/lang/String;C)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Move_algToMove
  (JNIEnv * env, jclass cls, jstring movelag, jchar side)
  {
    char* c_movalg = JStringToCCharPtr(env,movelag);
    int result = alg_to_move(c_movalg,side);
    free(c_movalg);
    return result;
  }
  
  /*
 * Class:     minimardi_Move
 * Method:    debugLegalMove
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Move_debugLegalMove
  (JNIEnv *env, jclass cls, jint mov)
  {
    int result = debug_legal_move(mov);
    return result;
  }
  
  /*
 * Class:     minimardi_Move
 * Method:    getPromPiece
 * Signature: (I)C
 */
JNIEXPORT jchar JNICALL Java_minimardi_Move_getPromPiece
  (JNIEnv *env, jclass cls, jint mov)
  {
    jchar result = get_prom_piece(mov);
    return result;
  }
  
  /*
 * Class:     minimardi_Move
 * Method:    getCapPiece
 * Signature: (I)C
 */
JNIEXPORT jchar JNICALL Java_minimardi_Move_getCapPiece
  (JNIEnv *env, jclass cls, jint mov)
  {
    jchar result = get_cap_piece(mov);
    return result;
  }
  
  /*
 * Class:     minimardi_Move
 * Method:    getSrc
 * Signature: (I)C
 */
JNIEXPORT jchar JNICALL Java_minimardi_Move_getSrc
  (JNIEnv *env, jclass cls, jint mov)
  {
    jchar result = get_src(mov);
    return result;
  }
  
  /*
 * Class:     minimardi_Move
 * Method:    getDest
 * Signature: (I)C
 */
JNIEXPORT jchar JNICALL Java_minimardi_Move_getDest
  (JNIEnv *env, jclass cls, jint mov)
  {
    jchar result = get_dst(mov);
    return result;
  }
  
  /*
 * Class:     minimardi_Move
 * Method:    isCastleMove
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_minimardi_Move_isCastleMove
  (JNIEnv *env, jclass cls, jint mov)
  {
    jchar result = is_castle_move(mov);
    return result;
  }
  
  /*
 * Class:     minimardi_Move
 * Method:    isPassantMove
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_minimardi_Move_isPassantMove
  (JNIEnv *env, jclass cls, jint mov)
  {
    jboolean result = is_passant_move(mov);
    return result;
  }
  
  /*
 * Class:     minimardi_Move
 * Method:    isPromMove
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_minimardi_Move_isPromMove
  (JNIEnv *env, jclass cls, jint mov)
  {
    jboolean result = is_prom_move(mov);
    return result;
  }
  
  /*
 * Class:     minimardi_Move
 * Method:    isCaptureMove
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_minimardi_Move_isCaptureMove
  (JNIEnv *env, jclass cls, jint mov)
  {
    jboolean result = is_capture_move(mov);
    return result;
  }
  
  /*
 * Class:     minimardi_Move
 * Method:    legaMove
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_minimardi_Move_legaMove
  (JNIEnv *env, jclass cls, jint mov)
  {
    jboolean result = legal_move(mov);
    return result;
  }
  
  /*
 * Class:     minimardi_Move
 * Method:    newMove
 * Signature: (II)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Move_newMove
  (JNIEnv *env, jclass cls, jint src, jint dest)
  {
    jint result = new_move(src,dest);
    return result;
  }
  
  /*
 * Class:     minimardi_Move
 * Method:    newMoveWithCap
 * Signature: (III)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Move_newMoveWithCap
  (JNIEnv *env, jclass cls, jint src, jint dest, jint cap)
  {
    jint result = new_move_with_cap(src,dest,cap);
    return result;
  }