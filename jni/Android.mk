   LOCAL_PATH := $(call my-dir)

   include $(CLEAR_VARS)

   LOCAL_MODULE    := minimardi-jni
   LOCAL_SRC_FILES := board.c book.c eval.c hist.c jni_constants.c main.c minimardi_Board.c minimardi_Move.c minimardi_Position.c minimardi_Search.c minimardi_Utils.c move.c move_list.c piece.c position.c score.c search.c trans.c utils.c minimardi_MoveListStack.c minimardi_Piece.c
   include $(BUILD_SHARED_LIBRARY)
   