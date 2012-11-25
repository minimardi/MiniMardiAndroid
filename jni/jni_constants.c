#include "jni_constants.h"

jlong GetHandle(JNIEnv* env,jobject minimardiOb)
{
    static jfieldID s_cptrfield = 0;       
    jthrowable exc = 0;
    jlong cptr = 0;
    if(!s_cptrfield)
    {
        jclass cls = (*env)->GetObjectClass(env,minimardiOb);        
        s_cptrfield = (*env)->GetFieldID(env, cls,"m_CPtr","J");
        (*env)->DeleteLocalRef(env,cls);
        exc = (*env)->ExceptionOccurred(env);
        if(exc)
        {
            return 0;
        }
    }   
    cptr = (*env)->GetLongField(env,minimardiOb,s_cptrfield);
    return cptr;    
}

char* JStringToCCharPtr(JNIEnv* env,jstring jstr)
{
    jsize len =  (*env)->GetStringUTFLength(env,jstr);
    char* fen = (char*)malloc((len + 1)* sizeof(char));
    (*env)->GetStringUTFRegion(env,jstr, 0, len, fen);                                      
    fen[len] = '\0';
    return fen;
}
