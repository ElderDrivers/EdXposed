//
// Created by user on 2021/7/5.
//

#include "fox_inlineHook.h"
#ifndef __WIN32__
#include <sys/mman.h>
#else
#include <Windows.h>
#endif
#include <memory>
#include <dlfcn.h>

namespace SlimHook
{
    constexpr static bool useSandHook = sizeof(void*) == 4;
    extern "C" void* SubstrateLikeInlineHookFunction(void* val0,void* val1,void** val2)
    {
        if(useSandHook)
        {
            if(!val0 || !val1 || !val2)return nullptr;
            void *handle = dlopen("libsandhook-native.so", RTLD_NOW);
            if(!handle)return nullptr;
            auto SandInlineHook = reinterpret_cast<void* (*)(void*,void*)>(
                    dlsym(handle,
                          "SandInlineHook")
            );
            *val2 =
                    SandInlineHook(val0,val1);
            return *val2;
        }
        SlimHookNativeLiteModule objectModule = SlimHookNativeLiteModule::SlimHookNativeFactory::get();
        objectModule.FoxHookFunction(val0, val1, val2);
        return *val2;
    }
    bool SlimHook::SlimHookNativeLiteModule::FoxHookFunction(void *originAddr, void *targetAddr,
                                                             void *targetForBackup) {
        return false;
    }

    class SlimHookNativeXAppleIos7Plus : public SlimHookNativeLiteModule
    {
#ifdef __APPLE__
#endif
    };

    /*
     * Author: 0xF
     * Author site: www.die.lu
     * Info
     * Inline hook function without branch fix...
     *
     * 警告：这个方式仅供少数已查明无影响的系统库函数使用，请不要拿到别的函数使用
     *
     * Warnung: Dëse Metod gëtt nëmme vun enger puer Systembibliotheek Funktiounen benotzt, déi fonnt gi sinn,
     * fir keng Effekt sinn. Kuckt w.e.g. keng aner Funktiounen déi benotzt gëtt
     *
     * Warning: Only for function section longer than 30 byte(s),
     * don't tryna use this function for another method hook...
     */
    namespace ARM64_SPatch_Region
    {
        static constexpr unsigned char shellCodeTemplate[] =
                {
                // 20 bytes
                        0x72,0x00,0x00,0x58,
                        0x52,0x02,0x40,0xF9,
                        0x40,0x02,0x1F,0xD6,
                        0,0,0,0,0,0,0,0
                };
        static constexpr unsigned char shellCodeTemplate2[] =
                {
                        0x92,0x01,0x00,0x58,
                        0x51,0x02,0x40,0xF9,
                        0x20,0x02,0x3F,0xD6,
                        0x1F,0x20,0x03,0xD5,
                        0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0, // 20 bytes
                        0xB2,0x00,0x00,0x58,
                        0x51,0x02,0x40,0xF9,
                        0x20,0x02,0x1F,0xD6,
                        0,0,0,0,0,0,0,0,
                        0,0,0,0,0,0,0,0
                };
    }
    class SlimHookNativeAndroid64 : public SlimHookNativeLiteModule
    {
    public:
        virtual boolean
        FoxHookFunction(LPVOID originAddr, LPVOID targetAddr, LPVOID targetForBackup) {
            if(!originAddr || !targetAddr || !targetForBackup)return false;
            using ARM64_SPatch_Region::shellCodeTemplate;
            using ARM64_SPatch_Region::shellCodeTemplate2;
            std::unique_ptr<unsigned char> iFoxPtr(new unsigned char[sizeof(shellCodeTemplate)]);
            mprotect((char*)originAddr - ((uintptr_t)originAddr % 4096), 4096,
                     PROT_EXEC | PROT_READ | PROT_WRITE);
            memcpy(&*iFoxPtr, shellCodeTemplate, sizeof(shellCodeTemplate));
            auto trampoline2 = mmap(nullptr, sizeof(shellCodeTemplate2), PROT_EXEC | PROT_READ | PROT_WRITE,
                                    MAP_ANONYMOUS | MAP_PRIVATE,-1,0);
            if(!trampoline2)return false;
            memcpy(&*iFoxPtr + 12, trampoline2, 8);
            memcpy((char*)trampoline2 + 16, originAddr, 20);
            memcpy((char*)trampoline2 + 48, &targetAddr, 8);
            uintptr_t addressCounter = (uintptr_t)originAddr + 20;
            memcpy((char*)trampoline2 + 56, &addressCounter, 8);
            memcpy(originAddr, &*iFoxPtr, sizeof(shellCodeTemplate));
            *((void**)targetForBackup) = (char*)trampoline2 + 36;
            return true;
        }
    };

    class SlimHookNativeWindows32 : public SlimHookNativeLiteModule
    {
#ifdef __WIN32__
#endif
    };

    class SlimHookNativeWindows64 : public SlimHookNativeLiteModule
    {
#ifdef __WIN64__
#endif
    };

    class SlimHookNativeWindowsARM64 : public SlimHookNativeAndroid64
    {
#if defined(__WIN64__) && defined(__AARCH64__)
#endif
    };

    class SlimHookNativeOSX64 : public SlimHookNativeWindows64
    {
        // Change VirtualAlloc and VirtualProtect to mmap/malloc/mprotect
#ifdef __APPLE__
#endif
    };

    class SlimHookNativeOSARM64 : public SlimHookNativeAndroid64
    {
#ifdef __APPLE__
#endif
    };

    SlimHook::SlimHookNativeLiteModule
    SlimHook::SlimHookNativeLiteModule::SlimHookNativeFactory::get() {
        return SlimHook::SlimHookNativeLiteModule();
    }

    SlimHookNativeLiteModule
    SlimHookNativeLiteModule::SlimHookNativeFactory::get(SlimHookNativeLiteModule::Integer) {
        if(sizeof(void*)==8)
            return SlimHookNativeAndroid64();
        return SlimHookNativeLiteModule();
    }

    SlimHookConfiguration::boolean SlimHookConfiguration::registerInlineHook() {
        if(sizeof(void*)==8)
            return false;
        return true;
    }
}
