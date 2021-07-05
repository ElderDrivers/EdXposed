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

/*
Copyright 2021 0xF www.die.lu 1@die.lu
Copyright 2021 ganyao swift_gan@trendmicro.com.cn

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

/*
Apache License
Version 2.0, January 2004
http://www.apache.org/licenses/

TERMS AND CONDITIONS FOR USE, REPRODUCTION, AND DISTRIBUTION

Definitions
"License" shall mean the terms and conditions for use, reproduction, and distribution as defined by Sections 1 through 9 of this document.

"Licensor" shall mean the copyright owner or entity authorized by the copyright owner that is granting the License.

"Legal Entity" shall mean the union of the acting entity and all other entities that control, are controlled by, or are under common control with that entity. For the purposes of this definition, "control" means (i) the power, direct or indirect, to cause the direction or management of such entity, whether by contract or otherwise, or (ii) ownership of fifty percent (50%) or more of the outstanding shares, or (iii) beneficial ownership of such entity.

"You" (or "Your") shall mean an individual or Legal Entity exercising permissions granted by this License.

"Source" form shall mean the preferred form for making modifications, including but not limited to software source code, documentation source, and configuration files.

"Object" form shall mean any form resulting from mechanical transformation or translation of a Source form, including but not limited to compiled object code, generated documentation, and conversions to other media types.

"Work" shall mean the work of authorship, whether in Source or Object form, made available under the License, as indicated by a copyright notice that is included in or attached to the work (an example is provided in the Appendix below).

"Derivative Works" shall mean any work, whether in Source or Object form, that is based on (or derived from) the Work and for which the editorial revisions, annotations, elaborations, or other modifications represent, as a whole, an original work of authorship. For the purposes of this License, Derivative Works shall not include works that remain separable from, or merely link (or bind by name) to the interfaces of, the Work and Derivative Works thereof.

"Contribution" shall mean any work of authorship, including the original version of the Work and any modifications or additions to that Work or Derivative Works thereof, that is intentionally submitted to Licensor for inclusion in the Work by the copyright owner or by an individual or Legal Entity authorized to submit on behalf of the copyright owner. For the purposes of this definition, "submitted" means any form of electronic, verbal, or written communication sent to the Licensor or its representatives, including but not limited to communication on electronic mailing lists, source code control systems, and issue tracking systems that are managed by, or on behalf of, the Licensor for the purpose of discussing and improving the Work, but excluding communication that is conspicuously marked or otherwise designated in writing by the copyright owner as "Not a Contribution."

"Contributor" shall mean Licensor and any individual or Legal Entity on behalf of whom a Contribution has been received by Licensor and subsequently incorporated within the Work.

Grant of Copyright License. Subject to the terms and conditions of this License, each Contributor hereby grants to You a perpetual, worldwide, non-exclusive, no-charge, royalty-free, irrevocable copyright license to reproduce, prepare Derivative Works of, publicly display, publicly perform, sublicense, and distribute the Work and such Derivative Works in Source or Object form.
Grant of Patent License. Subject to the terms and conditions of this License, each Contributor hereby grants to You a perpetual, worldwide, non-exclusive, no-charge, royalty-free, irrevocable (except as stated in this section) patent license to make, have made, use, offer to sell, sell, import, and otherwise transfer the Work, where such license applies only to those patent claims licensable by such Contributor that are necessarily infringed by their Contribution(s) alone or by combination of their Contribution(s) with the Work to which such Contribution(s) was submitted. If You institute patent litigation against any entity (including a cross-claim or counterclaim in a lawsuit) alleging that the Work or a Contribution incorporated within the Work constitutes direct or contributory patent infringement, then any patent licenses granted to You under this License for that Work shall terminate as of the date such litigation is filed.
Redistribution. You may reproduce and distribute copies of the Work or Derivative Works thereof in any medium, with or without modifications, and in Source or Object form, provided that You meet the following conditions:
You must give any other recipients of the Work or Derivative Works a copy of this License; and
You must cause any modified files to carry prominent notices stating that You changed the files; and
You must retain, in the Source form of any Derivative Works that You distribute, all copyright, patent, trademark, and attribution notices from the Source form of the Work, excluding those notices that do not pertain to any part of the Derivative Works; and
If the Work includes a "NOTICE" text file as part of its distribution, then any Derivative Works that You distribute must include a readable copy of the attribution notices contained within such NOTICE file, excluding those notices that do not pertain to any part of the Derivative Works, in at least one of the following places: within a NOTICE text file distributed as part of the Derivative Works; within the Source form or documentation, if provided along with the Derivative Works; or, within a display generated by the Derivative Works, if and wherever such third-party notices normally appear. The contents of the NOTICE file are for informational purposes only and do not modify the License. You may add Your own attribution notices within Derivative Works that You distribute, alongside or as an addendum to the NOTICE text from the Work, provided that such additional attribution notices cannot be construed as modifying the License.
You may add Your own copyright statement to Your modifications and may provide additional or different license terms and conditions for use, reproduction, or distribution of Your modifications, or for any such Derivative Works as a whole, provided Your use, reproduction, and distribution of the Work otherwise complies with the conditions stated in this License.

Submission of Contributions. Unless You explicitly state otherwise, any Contribution intentionally submitted for inclusion in the Work by You to the Licensor shall be under the terms and conditions of this License, without any additional terms or conditions. Notwithstanding the above, nothing herein shall supersede or modify the terms of any separate license agreement you may have executed with Licensor regarding such Contributions.
Trademarks. This License does not grant permission to use the trade names, trademarks, service marks, or product names of the Licensor, except as required for reasonable and customary use in describing the origin of the Work and reproducing the content of the NOTICE file.
Disclaimer of Warranty. Unless required by applicable law or agreed to in writing, Licensor provides the Work (and each Contributor provides its Contributions) on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied, including, without limitation, any warranties or conditions of TITLE, NON-INFRINGEMENT, MERCHANTABILITY, or FITNESS FOR A PARTICULAR PURPOSE. You are solely responsible for determining the appropriateness of using or redistributing the Work and assume any risks associated with Your exercise of permissions under this License.
Limitation of Liability. In no event and under no legal theory, whether in tort (including negligence), contract, or otherwise, unless required by applicable law (such as deliberate and grossly negligent acts) or agreed to in writing, shall any Contributor be liable to You for damages, including any direct, indirect, special, incidental, or consequential damages of any character arising as a result of this License or out of the use or inability to use the Work (including but not limited to damages for loss of goodwill, work stoppage, computer failure or malfunction, or any and all other commercial damages or losses), even if such Contributor has been advised of the possibility of such damages.
Accepting Warranty or Additional Liability. While redistributing the Work or Derivative Works thereof, You may choose to offer, and charge a fee for, acceptance of support, warranty, indemnity, or other liability obligations and/or rights consistent with this License. However, in accepting such obligations, You may act only on Your own behalf and on Your sole responsibility, not on behalf of any other Contributor, and only if You agree to indemnify, defend, and hold each Contributor harmless for any liability incurred by, or claims asserted against, such Contributor by reason of your accepting any such warranty or additional liability.
END OF TERMS AND CONDITIONS
 */

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
