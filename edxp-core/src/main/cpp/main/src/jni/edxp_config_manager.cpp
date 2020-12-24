
#include <config_manager.h>
#include <nativehelper/jni_macros.h>
#include <native_util.h>
#include <sstream>
#include "edxp_config_manager.h"

namespace edxp {

    static jboolean ConfigManager_isBlackWhiteListEnabled(JNI_START) {
        return (jboolean) ConfigManager::GetInstance()->IsBlackWhiteListEnabled();
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

    static jstring ConfigManager_getLibSandHookName(JNI_START) {
        return env->NewStringUTF(ConfigManager::GetInstance()->GetLibSandHookName().c_str());
    }

    static jstring ConfigManager_getDataPathPrefix(JNI_START) {
        return env->NewStringUTF(ConfigManager::GetInstance()->GetDataPathPrefix().c_str());
    }

    static jstring ConfigManager_getConfigPath(JNI_START, jstring jSuffix) {
        const char *suffix = env->GetStringUTFChars(jSuffix, JNI_FALSE);
        auto result = ConfigManager::GetInstance()->GetConfigPath(suffix);
        env->ReleaseStringUTFChars(jSuffix, suffix);
        return env->NewStringUTF(result.c_str());
    }

    static jstring ConfigManager_getPrefsPath(JNI_START, jstring jSuffix) {
        const char *suffix = env->GetStringUTFChars(jSuffix, JNI_FALSE);
        auto result = ConfigManager::GetInstance()->GetPrefsPath(suffix);
        env->ReleaseStringUTFChars(jSuffix, suffix);
        return env->NewStringUTF(result.c_str());
    }

    static jstring ConfigManager_getCachePath(JNI_START, jstring jSuffix) {
        const char *suffix = env->GetStringUTFChars(jSuffix, JNI_FALSE);
        auto result = ConfigManager::GetCachePath(suffix);
        env->ReleaseStringUTFChars(jSuffix, suffix);
        return env->NewStringUTF(result.c_str());
    }

    static jstring ConfigManager_getBaseConfigPath(JNI_START) {
        auto result = ConfigManager::GetInstance()->GetBaseConfigPath();
        return env->NewStringUTF(result.c_str());
    }

    static jstring ConfigManager_getModulesList(JNI_START) {
        auto module_list = Context::GetInstance()->GetAppModulesList();
        std::ostringstream join;
        std::copy(module_list.begin(), module_list.end(), std::ostream_iterator<std::string>(join, "\n"));
        const auto &list = join.str();
        LOGD("module list: %s", list.c_str());
        return env->NewStringUTF(list.c_str());
    }

    static JNINativeMethod gMethods[] = {
            NATIVE_METHOD(ConfigManager, isBlackWhiteListEnabled, "()Z"),
            NATIVE_METHOD(ConfigManager, isResourcesHookEnabled, "()Z"),
            NATIVE_METHOD(ConfigManager, isDeoptBootImageEnabled, "()Z"),
            NATIVE_METHOD(ConfigManager, isNoModuleLogEnabled, "()Z"),
            NATIVE_METHOD(ConfigManager, getInstallerPackageName, "()Ljava/lang/String;"),
            NATIVE_METHOD(ConfigManager, getXposedPropPath, "()Ljava/lang/String;"),
            NATIVE_METHOD(ConfigManager, getLibSandHookName, "()Ljava/lang/String;"),
            NATIVE_METHOD(ConfigManager, getDataPathPrefix, "()Ljava/lang/String;"),
            NATIVE_METHOD(ConfigManager, getConfigPath,
                          "(Ljava/lang/String;)Ljava/lang/String;"),
            NATIVE_METHOD(ConfigManager, getPrefsPath,
                          "(Ljava/lang/String;)Ljava/lang/String;"),
            NATIVE_METHOD(ConfigManager, getCachePath,
                          "(Ljava/lang/String;)Ljava/lang/String;"),
            NATIVE_METHOD(ConfigManager, getBaseConfigPath,"()Ljava/lang/String;"),
            NATIVE_METHOD(ConfigManager, getModulesList, "()Ljava/lang/String;"),
    };

    void RegisterConfigManagerMethods(JNIEnv *env) {
        REGISTER_EDXP_NATIVE_METHODS("com.elderdrivers.riru.edxp.config.ConfigManager");
    }

}