#include "minimardi_Board.h"
#include "board.h"
/*
 * Class:     minimardi_Board
 * Method:    nativeCreate
 * Signature: ()J
 */
JNIEXPORT jlong JNICALL Java_minimardi_Board_nativeCreate
  (JNIEnv *env, jobject thisObject)
  {
    board b = newboard();    
    return (jlong)b;
  }

/*
 * Class:     minimardi_Board
 * Method:    nativeFree
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_minimardi_Board_nativeFree
  (JNIEnv *env, jobject thisObject, jlong cptr)
  {
       board b = (board)cptr;
       free_board(b);
  }


/*
 * Class:     minimardi_Board
 * Method:    emptyBoardNative
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_minimardi_Board_emptyBoardNative
  (JNIEnv * env, jclass cls, jlong cptr)
  {
    board b = (board)cptr;
    empty_board(b);
  }
  
  /*
 * Class:     minimardi_Board
 * Method:    printBoardNative
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_minimardi_Board_printBoardNative
  (JNIEnv * env, jclass cls, jlong cptr)
  {
    board b = (board)cptr;
    print_board(b);
  }
  
  /*
 * Class:     minimardi_Board
 * Method:    chessBoardNative
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_minimardi_Board_chessBoardNative
  (JNIEnv *env, jclass cls, jlong cptr)
  {
      board b = (board)cptr;
      chess_board(b);
  }
  
/*
 * Class:     minimardi_Board
 * Method:    onBoard
 * Signature: (I)Z
 */
JNIEXPORT jboolean JNICALL Java_minimardi_Board_onBoard
  (JNIEnv *env, jclass cls, jint mov)
  {
    jboolean result = on_board(mov);
    return result; 
  }
  
  
  /*
 * Class:     minimardi_Board
 * Method:    getPieceNative
 * Signature: (JI)C
 */
JNIEXPORT jchar JNICALL Java_minimardi_Board_getPieceNative
  (JNIEnv *env, jclass cls, jlong cptr, jint pos)
  {
    board b = (board)cptr;
    jchar result = get_piece(b,pos);
    return result;
  }
  
  /*
 * Class:     minimardi_Board
 * Method:    setPieceNative
 * Signature: (CIJ)V
 */
JNIEXPORT void JNICALL Java_minimardi_Board_setPieceNative
  (JNIEnv *env, jclass cls, jchar pc, jint pos, jlong cptr)
  {
    board b = (board)cptr;
    set_piece(b,pc,pos);
  }
  
  /*
 * Class:     minimardi_Board
 * Method:    rank
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Board_rank
  (JNIEnv *env, jclass cls, jint pos)
  {
    jint result = rank(pos);
    return result;
  }
  
  /*
 * Class:     minimardi_Board
 * Method:    file
 * Signature: (I)I
 */
JNIEXPORT jint JNICALL Java_minimardi_Board_file
  (JNIEnv *env, jclass cls, jint pos)
  {
    jint f = file(pos);
    return f;
  }