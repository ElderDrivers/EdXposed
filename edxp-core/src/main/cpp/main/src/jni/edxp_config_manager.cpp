
#include <config_manager.h>
#include <nativehelper/jni_macros.h>
#include <native_util.h>
#include "edxp_config_manager.h"

namespace edxp {

    static jboolean ConfigManager_isBlackWhiteListEnabled(JNI_START) {
        return (jboolean) ConfigManager::GetInstance()->IsBlackWhiteListEnabled();
    }

    static jboolean ConfigManager_isDynamicModulesEnabled(JNI_START) {
        return (jboolean) ConfigManager::GetInstance()->IsDynamicModulesEnabled();
    }

    static jboolean ConfigManager_isResourcesHookEnabled(JNI_START) {
        return (jboolean) ConfigManager::GetInstance()->IsResourcesHookEnabled();
    }

    static jboolean ConfigManager_isDeoptBootImageEnabled(JNI_START) {
        return (jboolean) ConfigManager::GetInstance()->IsDeoptBootImageEnabled();
    }

    static jboolean ConfigManager_isNoModuleLogEnabled(JNI_START) {
        return (jboolean) ConfigManager::GetInstance()->IsNoModuleLogEnabled();
    }

    static jstring ConfigManager_getInstallerPackageName(JNI_START) {
        return env->NewStringUTF(ConfigManager::GetInstance()->GetInstallerPackageName().c_str());
    }

    static jstring ConfigManager_getXposedPropPath(JNI_START) {
        return env->NewStringUTF(ConfigManager::GetInstance()->GetXposedPropPath().c_str());
    }

    static jstring ConfigManager_getLibWhaleName(JNI_START) {
        return env->NewStringUTF(ConfigManager::GetInstance()->GetLibWhaleName().c_str());
    }

    static jstring ConfigManager_getLibSandHookName(JNI_START) {
        return env->NewStringUTF(ConfigManager::GetInstance()->GetLibSandHookName().c_str());
    }

    static jstring ConfigManager_getDataPathPrefix(JNI_START) {
        return env->NewStringUTF(ConfigManager::GetInstance()->GetDataPathPrefix().c_str());
    }

    static jstring ConfigManager_getInstallerConfigPath(JNI_START, jstring jSuffix) {
        const char *suffix = env->GetStringUTFChars(jSuffix, JNI_FALSE);
        auto result = ConfigManager::GetInstance()->GetConfigPath(suffix);
        env->ReleaseStringUTFChars(jSuffix, suffix);
        return env->NewStringUTF(result.c_str());

    }

    static jboolean ConfigManager_isAppNeedHook(JNI_START, jint userId, jstring appDataDir, jstring niceName) {
        const char *app_data_dir = env->GetStringUTFChars(appDataDir, JNI_FALSE);
        const char *nice_name = env->GetStringUTFChars(niceName, JNI_FALSE);
        auto result = (jboolean) ConfigManager::GetInstance()->IsAppNeedHook(userId, app_data_dir, nice_name);
        env->ReleaseStringUTFChars(appDataDir, app_data_dir);
        env->ReleaseStringUTFChars(niceName, nice_name);
        return result;
    }

    static JNINativeMethod gMethods[] = {
            NATIVE_METHOD(ConfigManager, isBlackWhiteListEnabled, "()Z"),
            NATIVE_METHOD(ConfigManager, isDynamicModulesEnabled, "()Z"),
            NATIVE_METHOD(ConfigManager, isResourcesHookEnabled, "()Z"),
            NATIVE_METHOD(ConfigManager, isDeoptBootImageEnabled, "()Z"),
            NATIVE_METHOD(ConfigManager, isNoModuleLogEnabled, "()Z"),
            NATIVE_METHOD(ConfigManager, getInstallerPackageName, "()Ljava/lang/String;"),
            NATIVE_METHOD(ConfigManager, getXposedPropPath, "()Ljava/lang/String;"),
            NATIVE_METHOD(ConfigManager, getLibSandHookName, "()Ljava/lang/String;"),
            NATIVE_METHOD(ConfigManager, getLibWhaleName, "()Ljava/lang/String;"),
            NATIVE_METHOD(ConfigManager, getDataPathPrefix, "()Ljava/lang/String;"),
            NATIVE_METHOD(ConfigManager, getInstallerConfigPath, "(Ljava/lang/String;)Ljava/lang/String;"),
            NATIVE_METHOD(ConfigManager, isAppNeedHook, "(ILjava/lang/String;Ljava/lang/String;)Z"),
    };

    void RegisterConfigManagerMethods(JNIEnv *env) {
        REGISTER_EDXP_NATIVE_METHODS("com.elderdrivers.riru.edxp.config.ConfigManager");
    }

}