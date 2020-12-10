
#include "HookMain.h"
#include <nativehelper/jni_macros.h>
#include "jni.h"
#include "native_util.h"
#include "edxp_yahfa.h"
#include "edxp_pending_hooks.h"
#include "art/runtime/class_linker.h"

namespace edxp {

    static void Yahfa_init(JNI_START, jint sdkVersion) {
        Java_lab_galaxy_yahfa_HookMain_init(env, clazz, sdkVersion);
    }

    static jobject Yahfa_findMethodNative(JNI_START, jclass targetClass,
                                          jstring methodName, jstring methodSig) {
        return Java_lab_galaxy_yahfa_HookMain_findMethodNative(env, clazz, targetClass, methodName,
                                                               methodSig);
    }

    static jboolean Yahfa_backupAndHookNative(JNI_START, jobject target,
                                              jobject hook, jobject backup) {
        return Java_lab_galaxy_yahfa_HookMain_backupAndHookNative(env, clazz, target, hook, backup);
    }

    static void Yahfa_setMethodNonCompilable(JNI_START, jobject member) {
        if (!member) {
            LOGE("setNonCompilableNative: member is null");
            return;
        }
        void *art_method = getArtMethod(env, member);

        if (!art_method) {
            LOGE("setNonCompilableNative: art_method is null");
            return;
        }
        setNonCompilable(art_method);
    }

    static jboolean Yahfa_setNativeFlag(JNI_START, jobject member, jboolean is_native) {
        if (!member) {
            LOGE("setNativeFlagNative: member is null");
            return JNI_FALSE;
        }
        void *art_method = getArtMethod(env, member);
        if (!art_method) {
            LOGE("setNativeFlagNative: art_method is null");
            return JNI_FALSE;
        }
        return (jboolean) setNativeFlag(art_method, is_native);
    }

    static void Yahfa_recordHooked(JNI_START, jobject member) {
        edxp::recordHooked(getArtMethod(env, member));
    }

    static jboolean Yahfa_isHooked(JNI_START, jobject member) {
        return edxp::isHooked(getArtMethod(env, member));
    }

    static void
    Yahfa_makeInitializedClassesVisiblyInitialized(JNI_START, jlong thread, jboolean wait) {
        art::ClassLinker::Current()->MakeInitializedClassesVisiblyInitialized(
                reinterpret_cast<void *>(thread), wait);
    }

    static JNINativeMethod gMethods[] = {
            NATIVE_METHOD(Yahfa, init, "(I)V"),
            NATIVE_METHOD(Yahfa, findMethodNative,
                          "(Ljava/lang/Class;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/reflect/Member;"),
            NATIVE_METHOD(Yahfa, backupAndHookNative,
                          "(Ljava/lang/Object;Ljava/lang/reflect/Method;Ljava/lang/reflect/Method;)Z"),
            NATIVE_METHOD(Yahfa, setMethodNonCompilable, "(Ljava/lang/reflect/Member;)V"),
            NATIVE_METHOD(Yahfa, setNativeFlag, "(Ljava/lang/reflect/Member;Z)Z"),
            NATIVE_METHOD(Yahfa, recordHooked, "(Ljava/lang/reflect/Member;)V"),
            NATIVE_METHOD(Yahfa, isHooked, "(Ljava/lang/reflect/Member;)Z"),
            NATIVE_METHOD(Yahfa, makeInitializedClassesVisiblyInitialized, "(JZ)V"),
    };

    void RegisterEdxpYahfa(JNIEnv *env) {
        REGISTER_EDXP_NATIVE_METHODS("com.elderdrivers.riru.edxp.core.Yahfa");
    }

}