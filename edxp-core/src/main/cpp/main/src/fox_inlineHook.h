//
// Created by user on 2021/7/5.
//

#ifndef EDXP_FOX_INLINEHOOK_H
#define EDXP_FOX_INLINEHOOK_H

#define far
#define FAR far

namespace SlimHook
{
    class SlimHookConfiguration
    {
    public:
        using boolean = bool;
        static boolean registerInlineHook();
    };
    class SlimHookNativeLiteModule
    {
    public:
        using boolean = bool;
        using PVOID = void*;
        using LPVOID = FAR PVOID;
        using Integer = int;
        class SlimHookNativeFactory
        {
        public:
            static SlimHookNativeLiteModule get();
            static SlimHookNativeLiteModule get(Integer);
        };
    public:
        virtual boolean FoxHookFunction(LPVOID originAddr, LPVOID targetAddr, LPVOID targetForBackup);
    };

    extern "C" void* SubstrateLikeInlineHookFunction(void*,void*,void**);
}

#endif //EDXP_FOX_INLINEHOOK_H
