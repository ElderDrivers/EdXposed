
#include <dlfcn.h>
#include <string>
#include <vector>
#include <config_manager.h>
#include <art/runtime/runtime.h>
#include <dl_util.h>
#include <art/runtime/jni_env_ext.h>
#include "utils.h"
#include "logging.h"
#include "native_hook.h"
#include "riru_hook.h"
#include "art/runtime/mirror/class.h"
#include "art/runtime/art_method.h"
#include "art/runtime/class_linker.h"
#include "art/runtime/gc/heap.h"
#include "art/runtime/hidden_api.h"
#include "art/runtime/art_method.h"
#include "art/runtime/instrumentation.h"
#include "art/runtime/reflection.h"
#include "fox_inlineHook.h"


namespace edxp {

    static volatile bool installed = false;
    static volatile bool art_hooks_installed = false;
    static HookFunType hook_func =  reinterpret_cast<HookFunType>(SlimHook::SubstrateLikeInlineHookFunction);

    void InstallArtHooks(void *art_handle);

    void InstallInlineHooks() {
        if (installed) {
            LOGI("Inline hooks have been installed, skip");
            return;
        }
        installed = true;
        LOGI("Start to install inline hooks");
        int api_level = GetAndroidApiLevel();
        if (UNLIKELY(api_level < __ANDROID_API_L__)) {
            LOGE("API level not supported: %d, skip inline hooks", api_level);
            return;
        }
        LOGI("Using api level %d", api_level);
        InstallRiruHooks();
        // install ART hooks
        if (api_level >= __ANDROID_API_Q__) {
            return;
        } else {
            // do dlopen directly in Android 9-
            ScopedDlHandle art_handle(kLibArtLegacyPath.c_str());
            InstallArtHooks(art_handle.Get());
        }
    }

    void InstallArtHooks(void *art_handle) {
        if (art_hooks_installed) {
            return;
        }
        if(SlimHook::SlimHookConfiguration::registerInlineHook())
        {
            art::hidden_api::DisableHiddenApi(art_handle, hook_func);
            art::Runtime::Setup(art_handle, hook_func);
            art::gc::Heap::Setup(art_handle, hook_func);
            art::art_method::Setup(art_handle, hook_func);
            art::Thread::Setup(art_handle, hook_func);
            art::ClassLinker::Setup(art_handle, hook_func);
            art::mirror::Class::Setup(art_handle, hook_func);
            art::JNIEnvExt::Setup(art_handle, hook_func);
            art::instrumentation::DisableUpdateHookedMethodsCode(art_handle, hook_func);
            art::PermissiveAccessByReflection(art_handle, hook_func);
        }

        art_hooks_installed = true;
        LOGI("ART hooks installed");
    }
}

