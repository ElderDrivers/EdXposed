//
// Created by Solo on 2019/1/27.
//

#include <cstdio>
#include <unistd.h>
#include <jni.h>
#include <cstdlib>
#include <array>
#include <thread>
#include <vector>
#include <string>
#include <include/logging.h>
#include <include/android_build.h>
#include "config_manager.h"

static char package_name[256];
static bool global_mode = false;
static bool dynamic_modules = false;
static bool inited = false;
static bool use_prot_storage = GetAndroidApiLevel() >= ANDROID_N;
static std::string config_path = use_prot_storage ? "/data/user_de/0/org.meowcat.edxposed.manager/conf/"
        : "/data/user/0/org.meowcat.edxposed.manager/conf/";
static std::string blacklist_path = config_path + "blacklist/";
static std::string whitelist_path = config_path + "whitelist/";
static std::string use_whitelist_path = config_path + "usewhitelist";
static std::string forceglobal_path = config_path + "forceglobal";
static std::string dynamicmodules_path = config_path + "dynamicmodules";

void initOnce() {
    if (!inited) {
        global_mode = access(forceglobal_path.c_str(), F_OK) == 0;
        dynamic_modules = access(dynamicmodules_path.c_str(), F_OK) == 0;
        inited = true;
    }
}

// default is true
int is_app_need_hook(JNIEnv *env, jstring appDataDir) {
    if (is_global_mode()) {
        return 1;
    }
    if (!appDataDir) {
        LOGW("appDataDir is null");
        return 1;
    }
    const char *app_data_dir = env->GetStringUTFChars(appDataDir, nullptr);
    int user = 0;
    if (sscanf(app_data_dir, "/data/%*[^/]/%d/%s", &user, package_name) != 2) {
        if (sscanf(app_data_dir, "/data/%*[^/]/%s", package_name) != 1) {
            package_name[0] = '\0';
            LOGW("can't parse %s", app_data_dir);
            return 1;
        }
    }
    env->ReleaseStringUTFChars(appDataDir, app_data_dir);
    if (strcmp(package_name, "org.meowcat.edxposed.manager") == 0) {
        // always hook installer app
        return 1;
    }
    bool use_white_list = access(use_whitelist_path.c_str(), F_OK) == 0;
    bool white_list_exists = access(whitelist_path.c_str(), F_OK) == 0;
    bool black_list_exists = access(blacklist_path.c_str(), F_OK) == 0;
    if (use_white_list && white_list_exists) {
        char path[PATH_MAX];
        snprintf(path, PATH_MAX,  "%s%s", whitelist_path.c_str(), package_name);
        int res = access(path, F_OK) == 0;
        LOGD("use whitelist, res=%d", res);
        return res;
    } else if (!use_white_list && black_list_exists) {
        char path[PATH_MAX];
        snprintf(path, PATH_MAX, "%s%s", blacklist_path.c_str(), package_name);
        int res = access(path, F_OK) != 0;
        LOGD("use blacklist, res=%d", res);
        return res;
    } else {
        LOGD("use nonlist, res=%d", 1);
        return 1;
    }
}

bool is_global_mode() {
    initOnce();
    return global_mode;
}

bool is_dynamic_modules() {
    initOnce();
    return dynamic_modules;
}
