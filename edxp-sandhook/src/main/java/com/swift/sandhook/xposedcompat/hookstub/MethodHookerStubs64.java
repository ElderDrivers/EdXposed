package com.swift.sandhook.xposedcompat.hookstub;

import static com.swift.sandhook.xposedcompat.hookstub.HookStubManager.getMethodId;
import static com.swift.sandhook.xposedcompat.hookstub.HookStubManager.hookBridge;

/*
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
public class MethodHookerStubs64 {

    public static boolean hasStubBackup = false;
    public static int[] stubSizes = {10, 20, 30, 30, 30, 30, 50, 50};


    //stub of arg size 0, index 0
    public static long stub_hook_0() throws Throwable {
        return  hookBridge(getMethodId(0, 0), null );
    }


    //stub of arg size 0, index 1
    public static long stub_hook_1() throws Throwable {
        return  hookBridge(getMethodId(0, 1), null );
    }


    //stub of arg size 0, index 2
    public static long stub_hook_2() throws Throwable {
        return  hookBridge(getMethodId(0, 2), null );
    }


    //stub of arg size 0, index 3
    public static long stub_hook_3() throws Throwable {
        return  hookBridge(getMethodId(0, 3), null );
    }


    //stub of arg size 0, index 4
    public static long stub_hook_4() throws Throwable {
        return  hookBridge(getMethodId(0, 4), null );
    }


    //stub of arg size 0, index 5
    public static long stub_hook_5() throws Throwable {
        return  hookBridge(getMethodId(0, 5), null );
    }


    //stub of arg size 0, index 6
    public static long stub_hook_6() throws Throwable {
        return  hookBridge(getMethodId(0, 6), null );
    }


    //stub of arg size 0, index 7
    public static long stub_hook_7() throws Throwable {
        return  hookBridge(getMethodId(0, 7), null );
    }


    //stub of arg size 0, index 8
    public static long stub_hook_8() throws Throwable {
        return  hookBridge(getMethodId(0, 8), null );
    }


    //stub of arg size 0, index 9
    public static long stub_hook_9() throws Throwable {
        return  hookBridge(getMethodId(0, 9), null );
    }


    //stub of arg size 1, index 0
    public static long stub_hook_0(long a0) throws Throwable {
        return  hookBridge(getMethodId(1, 0), null , a0);
    }


    //stub of arg size 1, index 1
    public static long stub_hook_1(long a0) throws Throwable {
        return  hookBridge(getMethodId(1, 1), null , a0);
    }


    //stub of arg size 1, index 2
    public static long stub_hook_2(long a0) throws Throwable {
        return  hookBridge(getMethodId(1, 2), null , a0);
    }


    //stub of arg size 1, index 3
    public static long stub_hook_3(long a0) throws Throwable {
        return  hookBridge(getMethodId(1, 3), null , a0);
    }


    //stub of arg size 1, index 4
    public static long stub_hook_4(long a0) throws Throwable {
        return  hookBridge(getMethodId(1, 4), null , a0);
    }


    //stub of arg size 1, index 5
    public static long stub_hook_5(long a0) throws Throwable {
        return  hookBridge(getMethodId(1, 5), null , a0);
    }


    //stub of arg size 1, index 6
    public static long stub_hook_6(long a0) throws Throwable {
        return  hookBridge(getMethodId(1, 6), null , a0);
    }


    //stub of arg size 1, index 7
    public static long stub_hook_7(long a0) throws Throwable {
        return  hookBridge(getMethodId(1, 7), null , a0);
    }


    //stub of arg size 1, index 8
    public static long stub_hook_8(long a0) throws Throwable {
        return  hookBridge(getMethodId(1, 8), null , a0);
    }


    //stub of arg size 1, index 9
    public static long stub_hook_9(long a0) throws Throwable {
        return  hookBridge(getMethodId(1, 9), null , a0);
    }


    //stub of arg size 1, index 10
    public static long stub_hook_10(long a0) throws Throwable {
        return  hookBridge(getMethodId(1, 10), null , a0);
    }


    //stub of arg size 1, index 11
    public static long stub_hook_11(long a0) throws Throwable {
        return  hookBridge(getMethodId(1, 11), null , a0);
    }


    //stub of arg size 1, index 12
    public static long stub_hook_12(long a0) throws Throwable {
        return  hookBridge(getMethodId(1, 12), null , a0);
    }


    //stub of arg size 1, index 13
    public static long stub_hook_13(long a0) throws Throwable {
        return  hookBridge(getMethodId(1, 13), null , a0);
    }


    //stub of arg size 1, index 14
    public static long stub_hook_14(long a0) throws Throwable {
        return  hookBridge(getMethodId(1, 14), null , a0);
    }


    //stub of arg size 1, index 15
    public static long stub_hook_15(long a0) throws Throwable {
        return  hookBridge(getMethodId(1, 15), null , a0);
    }


    //stub of arg size 1, index 16
    public static long stub_hook_16(long a0) throws Throwable {
        return  hookBridge(getMethodId(1, 16), null , a0);
    }


    //stub of arg size 1, index 17
    public static long stub_hook_17(long a0) throws Throwable {
        return  hookBridge(getMethodId(1, 17), null , a0);
    }


    //stub of arg size 1, index 18
    public static long stub_hook_18(long a0) throws Throwable {
        return  hookBridge(getMethodId(1, 18), null , a0);
    }


    //stub of arg size 1, index 19
    public static long stub_hook_19(long a0) throws Throwable {
        return  hookBridge(getMethodId(1, 19), null , a0);
    }


    //stub of arg size 2, index 0
    public static long stub_hook_0(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 0), null , a0, a1);
    }


    //stub of arg size 2, index 1
    public static long stub_hook_1(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 1), null , a0, a1);
    }


    //stub of arg size 2, index 2
    public static long stub_hook_2(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 2), null , a0, a1);
    }


    //stub of arg size 2, index 3
    public static long stub_hook_3(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 3), null , a0, a1);
    }


    //stub of arg size 2, index 4
    public static long stub_hook_4(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 4), null , a0, a1);
    }


    //stub of arg size 2, index 5
    public static long stub_hook_5(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 5), null , a0, a1);
    }


    //stub of arg size 2, index 6
    public static long stub_hook_6(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 6), null , a0, a1);
    }


    //stub of arg size 2, index 7
    public static long stub_hook_7(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 7), null , a0, a1);
    }


    //stub of arg size 2, index 8
    public static long stub_hook_8(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 8), null , a0, a1);
    }


    //stub of arg size 2, index 9
    public static long stub_hook_9(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 9), null , a0, a1);
    }


    //stub of arg size 2, index 10
    public static long stub_hook_10(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 10), null , a0, a1);
    }


    //stub of arg size 2, index 11
    public static long stub_hook_11(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 11), null , a0, a1);
    }


    //stub of arg size 2, index 12
    public static long stub_hook_12(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 12), null , a0, a1);
    }


    //stub of arg size 2, index 13
    public static long stub_hook_13(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 13), null , a0, a1);
    }


    //stub of arg size 2, index 14
    public static long stub_hook_14(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 14), null , a0, a1);
    }


    //stub of arg size 2, index 15
    public static long stub_hook_15(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 15), null , a0, a1);
    }


    //stub of arg size 2, index 16
    public static long stub_hook_16(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 16), null , a0, a1);
    }


    //stub of arg size 2, index 17
    public static long stub_hook_17(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 17), null , a0, a1);
    }


    //stub of arg size 2, index 18
    public static long stub_hook_18(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 18), null , a0, a1);
    }


    //stub of arg size 2, index 19
    public static long stub_hook_19(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 19), null , a0, a1);
    }


    //stub of arg size 2, index 20
    public static long stub_hook_20(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 20), null , a0, a1);
    }


    //stub of arg size 2, index 21
    public static long stub_hook_21(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 21), null , a0, a1);
    }


    //stub of arg size 2, index 22
    public static long stub_hook_22(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 22), null , a0, a1);
    }


    //stub of arg size 2, index 23
    public static long stub_hook_23(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 23), null , a0, a1);
    }


    //stub of arg size 2, index 24
    public static long stub_hook_24(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 24), null , a0, a1);
    }


    //stub of arg size 2, index 25
    public static long stub_hook_25(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 25), null , a0, a1);
    }


    //stub of arg size 2, index 26
    public static long stub_hook_26(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 26), null , a0, a1);
    }


    //stub of arg size 2, index 27
    public static long stub_hook_27(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 27), null , a0, a1);
    }


    //stub of arg size 2, index 28
    public static long stub_hook_28(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 28), null , a0, a1);
    }


    //stub of arg size 2, index 29
    public static long stub_hook_29(long a0, long a1) throws Throwable {
        return  hookBridge(getMethodId(2, 29), null , a0, a1);
    }


    //stub of arg size 3, index 0
    public static long stub_hook_0(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 0), null , a0, a1, a2);
    }


    //stub of arg size 3, index 1
    public static long stub_hook_1(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 1), null , a0, a1, a2);
    }


    //stub of arg size 3, index 2
    public static long stub_hook_2(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 2), null , a0, a1, a2);
    }


    //stub of arg size 3, index 3
    public static long stub_hook_3(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 3), null , a0, a1, a2);
    }


    //stub of arg size 3, index 4
    public static long stub_hook_4(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 4), null , a0, a1, a2);
    }


    //stub of arg size 3, index 5
    public static long stub_hook_5(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 5), null , a0, a1, a2);
    }


    //stub of arg size 3, index 6
    public static long stub_hook_6(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 6), null , a0, a1, a2);
    }


    //stub of arg size 3, index 7
    public static long stub_hook_7(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 7), null , a0, a1, a2);
    }


    //stub of arg size 3, index 8
    public static long stub_hook_8(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 8), null , a0, a1, a2);
    }


    //stub of arg size 3, index 9
    public static long stub_hook_9(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 9), null , a0, a1, a2);
    }


    //stub of arg size 3, index 10
    public static long stub_hook_10(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 10), null , a0, a1, a2);
    }


    //stub of arg size 3, index 11
    public static long stub_hook_11(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 11), null , a0, a1, a2);
    }


    //stub of arg size 3, index 12
    public static long stub_hook_12(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 12), null , a0, a1, a2);
    }


    //stub of arg size 3, index 13
    public static long stub_hook_13(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 13), null , a0, a1, a2);
    }


    //stub of arg size 3, index 14
    public static long stub_hook_14(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 14), null , a0, a1, a2);
    }


    //stub of arg size 3, index 15
    public static long stub_hook_15(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 15), null , a0, a1, a2);
    }


    //stub of arg size 3, index 16
    public static long stub_hook_16(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 16), null , a0, a1, a2);
    }


    //stub of arg size 3, index 17
    public static long stub_hook_17(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 17), null , a0, a1, a2);
    }


    //stub of arg size 3, index 18
    public static long stub_hook_18(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 18), null , a0, a1, a2);
    }


    //stub of arg size 3, index 19
    public static long stub_hook_19(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 19), null , a0, a1, a2);
    }


    //stub of arg size 3, index 20
    public static long stub_hook_20(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 20), null , a0, a1, a2);
    }


    //stub of arg size 3, index 21
    public static long stub_hook_21(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 21), null , a0, a1, a2);
    }


    //stub of arg size 3, index 22
    public static long stub_hook_22(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 22), null , a0, a1, a2);
    }


    //stub of arg size 3, index 23
    public static long stub_hook_23(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 23), null , a0, a1, a2);
    }


    //stub of arg size 3, index 24
    public static long stub_hook_24(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 24), null , a0, a1, a2);
    }


    //stub of arg size 3, index 25
    public static long stub_hook_25(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 25), null , a0, a1, a2);
    }


    //stub of arg size 3, index 26
    public static long stub_hook_26(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 26), null , a0, a1, a2);
    }


    //stub of arg size 3, index 27
    public static long stub_hook_27(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 27), null , a0, a1, a2);
    }


    //stub of arg size 3, index 28
    public static long stub_hook_28(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 28), null , a0, a1, a2);
    }


    //stub of arg size 3, index 29
    public static long stub_hook_29(long a0, long a1, long a2) throws Throwable {
        return  hookBridge(getMethodId(3, 29), null , a0, a1, a2);
    }


    //stub of arg size 4, index 0
    public static long stub_hook_0(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 0), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 1
    public static long stub_hook_1(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 1), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 2
    public static long stub_hook_2(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 2), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 3
    public static long stub_hook_3(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 3), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 4
    public static long stub_hook_4(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 4), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 5
    public static long stub_hook_5(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 5), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 6
    public static long stub_hook_6(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 6), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 7
    public static long stub_hook_7(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 7), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 8
    public static long stub_hook_8(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 8), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 9
    public static long stub_hook_9(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 9), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 10
    public static long stub_hook_10(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 10), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 11
    public static long stub_hook_11(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 11), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 12
    public static long stub_hook_12(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 12), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 13
    public static long stub_hook_13(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 13), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 14
    public static long stub_hook_14(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 14), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 15
    public static long stub_hook_15(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 15), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 16
    public static long stub_hook_16(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 16), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 17
    public static long stub_hook_17(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 17), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 18
    public static long stub_hook_18(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 18), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 19
    public static long stub_hook_19(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 19), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 20
    public static long stub_hook_20(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 20), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 21
    public static long stub_hook_21(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 21), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 22
    public static long stub_hook_22(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 22), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 23
    public static long stub_hook_23(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 23), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 24
    public static long stub_hook_24(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 24), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 25
    public static long stub_hook_25(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 25), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 26
    public static long stub_hook_26(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 26), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 27
    public static long stub_hook_27(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 27), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 28
    public static long stub_hook_28(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 28), null , a0, a1, a2, a3);
    }


    //stub of arg size 4, index 29
    public static long stub_hook_29(long a0, long a1, long a2, long a3) throws Throwable {
        return  hookBridge(getMethodId(4, 29), null , a0, a1, a2, a3);
    }


    //stub of arg size 5, index 0
    public static long stub_hook_0(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 0), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 1
    public static long stub_hook_1(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 1), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 2
    public static long stub_hook_2(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 2), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 3
    public static long stub_hook_3(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 3), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 4
    public static long stub_hook_4(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 4), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 5
    public static long stub_hook_5(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 5), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 6
    public static long stub_hook_6(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 6), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 7
    public static long stub_hook_7(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 7), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 8
    public static long stub_hook_8(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 8), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 9
    public static long stub_hook_9(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 9), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 10
    public static long stub_hook_10(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 10), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 11
    public static long stub_hook_11(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 11), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 12
    public static long stub_hook_12(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 12), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 13
    public static long stub_hook_13(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 13), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 14
    public static long stub_hook_14(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 14), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 15
    public static long stub_hook_15(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 15), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 16
    public static long stub_hook_16(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 16), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 17
    public static long stub_hook_17(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 17), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 18
    public static long stub_hook_18(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 18), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 19
    public static long stub_hook_19(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 19), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 20
    public static long stub_hook_20(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 20), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 21
    public static long stub_hook_21(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 21), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 22
    public static long stub_hook_22(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 22), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 23
    public static long stub_hook_23(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 23), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 24
    public static long stub_hook_24(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 24), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 25
    public static long stub_hook_25(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 25), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 26
    public static long stub_hook_26(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 26), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 27
    public static long stub_hook_27(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 27), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 28
    public static long stub_hook_28(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 28), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 5, index 29
    public static long stub_hook_29(long a0, long a1, long a2, long a3, long a4) throws Throwable {
        return  hookBridge(getMethodId(5, 29), null , a0, a1, a2, a3, a4);
    }


    //stub of arg size 6, index 0
    public static long stub_hook_0(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 0), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 1
    public static long stub_hook_1(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 1), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 2
    public static long stub_hook_2(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 2), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 3
    public static long stub_hook_3(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 3), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 4
    public static long stub_hook_4(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 4), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 5
    public static long stub_hook_5(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 5), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 6
    public static long stub_hook_6(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 6), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 7
    public static long stub_hook_7(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 7), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 8
    public static long stub_hook_8(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 8), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 9
    public static long stub_hook_9(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 9), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 10
    public static long stub_hook_10(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 10), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 11
    public static long stub_hook_11(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 11), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 12
    public static long stub_hook_12(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 12), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 13
    public static long stub_hook_13(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 13), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 14
    public static long stub_hook_14(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 14), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 15
    public static long stub_hook_15(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 15), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 16
    public static long stub_hook_16(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 16), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 17
    public static long stub_hook_17(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 17), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 18
    public static long stub_hook_18(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 18), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 19
    public static long stub_hook_19(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 19), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 20
    public static long stub_hook_20(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 20), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 21
    public static long stub_hook_21(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 21), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 22
    public static long stub_hook_22(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 22), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 23
    public static long stub_hook_23(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 23), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 24
    public static long stub_hook_24(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 24), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 25
    public static long stub_hook_25(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 25), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 26
    public static long stub_hook_26(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 26), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 27
    public static long stub_hook_27(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 27), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 28
    public static long stub_hook_28(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 28), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 29
    public static long stub_hook_29(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 29), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 30
    public static long stub_hook_30(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 30), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 31
    public static long stub_hook_31(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 31), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 32
    public static long stub_hook_32(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 32), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 33
    public static long stub_hook_33(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 33), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 34
    public static long stub_hook_34(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 34), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 35
    public static long stub_hook_35(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 35), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 36
    public static long stub_hook_36(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 36), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 37
    public static long stub_hook_37(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 37), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 38
    public static long stub_hook_38(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 38), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 39
    public static long stub_hook_39(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 39), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 40
    public static long stub_hook_40(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 40), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 41
    public static long stub_hook_41(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 41), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 42
    public static long stub_hook_42(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 42), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 43
    public static long stub_hook_43(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 43), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 44
    public static long stub_hook_44(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 44), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 45
    public static long stub_hook_45(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 45), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 46
    public static long stub_hook_46(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 46), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 47
    public static long stub_hook_47(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 47), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 48
    public static long stub_hook_48(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 48), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 6, index 49
    public static long stub_hook_49(long a0, long a1, long a2, long a3, long a4, long a5) throws Throwable {
        return  hookBridge(getMethodId(6, 49), null , a0, a1, a2, a3, a4, a5);
    }


    //stub of arg size 7, index 0
    public static long stub_hook_0(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 0), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 1
    public static long stub_hook_1(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 1), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 2
    public static long stub_hook_2(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 2), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 3
    public static long stub_hook_3(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 3), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 4
    public static long stub_hook_4(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 4), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 5
    public static long stub_hook_5(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 5), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 6
    public static long stub_hook_6(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 6), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 7
    public static long stub_hook_7(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 7), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 8
    public static long stub_hook_8(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 8), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 9
    public static long stub_hook_9(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 9), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 10
    public static long stub_hook_10(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 10), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 11
    public static long stub_hook_11(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 11), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 12
    public static long stub_hook_12(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 12), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 13
    public static long stub_hook_13(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 13), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 14
    public static long stub_hook_14(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 14), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 15
    public static long stub_hook_15(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 15), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 16
    public static long stub_hook_16(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 16), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 17
    public static long stub_hook_17(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 17), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 18
    public static long stub_hook_18(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 18), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 19
    public static long stub_hook_19(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 19), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 20
    public static long stub_hook_20(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 20), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 21
    public static long stub_hook_21(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 21), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 22
    public static long stub_hook_22(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 22), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 23
    public static long stub_hook_23(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 23), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 24
    public static long stub_hook_24(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 24), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 25
    public static long stub_hook_25(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 25), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 26
    public static long stub_hook_26(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 26), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 27
    public static long stub_hook_27(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 27), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 28
    public static long stub_hook_28(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 28), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 29
    public static long stub_hook_29(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 29), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 30
    public static long stub_hook_30(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 30), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 31
    public static long stub_hook_31(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 31), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 32
    public static long stub_hook_32(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 32), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 33
    public static long stub_hook_33(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 33), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 34
    public static long stub_hook_34(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 34), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 35
    public static long stub_hook_35(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 35), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 36
    public static long stub_hook_36(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 36), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 37
    public static long stub_hook_37(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 37), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 38
    public static long stub_hook_38(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 38), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 39
    public static long stub_hook_39(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 39), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 40
    public static long stub_hook_40(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 40), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 41
    public static long stub_hook_41(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 41), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 42
    public static long stub_hook_42(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 42), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 43
    public static long stub_hook_43(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 43), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 44
    public static long stub_hook_44(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 44), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 45
    public static long stub_hook_45(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 45), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 46
    public static long stub_hook_46(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 46), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 47
    public static long stub_hook_47(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 47), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 48
    public static long stub_hook_48(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 48), null , a0, a1, a2, a3, a4, a5, a6);
    }


    //stub of arg size 7, index 49
    public static long stub_hook_49(long a0, long a1, long a2, long a3, long a4, long a5, long a6) throws Throwable {
        return  hookBridge(getMethodId(7, 49), null , a0, a1, a2, a3, a4, a5, a6);
    }

}
