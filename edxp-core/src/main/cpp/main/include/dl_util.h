
#pragma once

#include <dlfcn.h>
#include "logging.h"
#include "config.h"

namespace edxp {
    namespace SandHook_Region
    {
        constexpr bool FUCK_YOU = true;
        extern "C" bool hooked_is_accessible(void* thiz, const std::string& file);
    }

    inline static void *DlOpen(const char *file) {
        void *handle = dlopen(file, RTLD_LAZY | RTLD_GLOBAL);
        if (!handle) {
            LOGE("dlopen(%s) failed: %s", file, dlerror());
        }
        return handle;
    }

    template<typename T>
    inline static T DlSym(void *handle, const char *sym_name) {
        if (!handle) {
            LOGE("dlsym(%s) failed: handle is null", sym_name);
        }
        T symbol = reinterpret_cast<T>(dlsym(handle, sym_name));
        if (!symbol) {
            LOGE("dlsym(%s) failed: %s", sym_name, dlerror());
        }
        return symbol;
    }

    class ScopedDlHandle {
    protected:
        static int fucked;
        void fuck_linker()
        {
            if(fucked)return;
            const char* sAddr = nullptr;
            if(sizeof(void*)==4)
            {
                sAddr = "/apex/com.android.runtime/bin/linker";
            } else{
                sAddr = "/apex/com.android.runtime/bin/linker64";
            }
            void *handle = dlopen("libsandhook-native.so", RTLD_NOW);
            auto getSym = reinterpret_cast<void *(*)(const char*, const char*)>(dlsym(handle,
                                                                                      "SandGetSym"));
            if (!getSym) {
                return;
            }
            auto is_accessible_str = "__dl__ZN19android_namespace_t13is_accessibleERKNSt3__112basic_stringIcNS0_11char_traitsIcEENS0_9allocatorIcEEEE";
            void *is_accessible_addr = getSym(sAddr, is_accessible_str);
            if (is_accessible_addr) {
                // SandInlineHook
                auto SandInlineHook = reinterpret_cast<void* (*)(void*,void*)>(
                        dlsym(handle,
                              "SandInlineHook")
                        );
                if(SandInlineHook)
                {
                    SandInlineHook(is_accessible_addr,
                                   reinterpret_cast<void *>(SandHook_Region::hooked_is_accessible));
                    fucked = 1;
                }
            }
        }

    public:
        ScopedDlHandle(const char *file) {
            handle_ = DlOpen(file);
        }
        ScopedDlHandle(const char *file, int sdkVer) {
            handle_ = [&]()->void*
            {
                if(sdkVer < 29)
                    return DlOpen(file);
                fuck_linker();
                return DlOpen(file);
            }();
            sdkVersion = sdkVer;
        }

        ~ScopedDlHandle() {
            if (handle_) {
                dlclose(handle_);
            }
        }

        void *Get() const {
            return handle_;
        }

        template<typename T>
        T DlSym(const char *sym_name) const {
            return edxp::DlSym<T>(handle_, sym_name);
        }

        bool IsValid() const {
            return handle_ != nullptr;
        }

    private:
        void *handle_;
        typedef int Integer;
        Integer sdkVersion = 0;
    };

}
