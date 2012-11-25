#ifndef JNI_CONSTANTS_H
#define JNI_CONSTANTS_H

#include <jni.h>
#define JNI_CONSTRUCTOR "<init>"

#define HISTORY_HEURISTIC_CLASS_DESCRIPTOR "minimardi/HistoryHeuristic"

#define BOARD_CLASS_DESCRIPTOR "minimardi/Board"

#define MOVE_LIST_STACK_CLASS_DESCRIPTOR "minimardi/MoveListStack"

#define HISTORY_HEURISTIC_CONSTRUCTOR "(J)V"

#define MOVE_LIST_STACK_CONSTRUCTOR "(J)V"

#define BOARD_CONSTRUCTOR "(J)V"

jlong GetHandle(JNIEnv* env,jobject minimardiOb);

char* JStringToCCharPtr(JNIEnv* env,jstring jstr);

#endif