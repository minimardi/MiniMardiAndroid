#include "minimardi_Piece.h"

#include "jni_constants.h"

#include "piece.h"


/*
 * Class:     minimardi_Piece
 * Method:    valid_piece
 * Signature: (C)Z
 */
JNIEXPORT jboolean JNICALL Java_minimardi_Piece_valid_1piece
  (JNIEnv *env, jclass cls, jchar p)
  {
       jboolean b = valid_piece(p);
       return b;
  }
  
  /*
 * Class:     minimardi_Piece
 * Method:    valid_piece_not_empty
 * Signature: (C)Z
 */
JNIEXPORT jboolean JNICALL Java_minimardi_Piece_valid_1piece_1not_1empty
  (JNIEnv *env, jclass cls, jchar p)
  {
    jboolean b = valid_piece_not_empty(p);
    return b;
  }
  
  /*
 * Class:     minimardi_Piece
 * Method:    makeSameColor
 * Signature: (CC)C
 */
JNIEXPORT jchar JNICALL Java_minimardi_Piece_makeSameColor
  (JNIEnv *env, jclass cls, jchar p1, jchar p2)
  {
    piece p = make_same_color(p1,p2);
    return p;
  }
