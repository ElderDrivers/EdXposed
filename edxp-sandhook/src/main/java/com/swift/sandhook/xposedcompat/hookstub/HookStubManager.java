package com.swift.sandhook.xposedcompat.hookstub;

import android.util.Log;

import com.elderdrivers.riru.edxp.sandhook.BuildConfig;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.SandHookMethodResolver;
import com.swift.sandhook.utils.ParamWrapper;
import com.swift.sandhook.wrapper.StubMethodsFactory;
import com.swift.sandhook.xposedcompat.XposedCompat;
import com.swift.sandhook.xposedcompat.utils.DexLog;

import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.concurrent.atomic.AtomicInteger;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
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
public class HookStubManager {

    public static volatile boolean is64Bit;
    //64bits arg0 - arg7 is in reg x1 - x7 and > 7 is in stack, but can not match
    public final static int MAX_64_ARGS = 7;

    public static int MAX_STUB_ARGS = 0;

    public static int[] stubSizes;

    public static boolean hasStubBackup;

    public static AtomicInteger[] curUseStubIndexes;

    public static int ALL_STUB = 0;

    public static Member[] originMethods;
    public static HookMethodEntity[] hookMethodEntities;
    public static XposedBridge.AdditionalHookInfo[] additionalHookInfos;

    static {
        is64Bit = SandHook.is64Bit();
        Class stubClass = is64Bit ? MethodHookerStubs64.class : MethodHookerStubs32.class;
        stubSizes = (int[]) XposedHelpers.getStaticObjectField(stubClass, "stubSizes");
        Boolean hasBackup = (Boolean) XposedHelpers.getStaticObjectField(stubClass, "hasStubBackup");
        hasStubBackup = hasBackup != null && (hasBackup && !XposedCompat.useNewCallBackup);
        if (stubSizes != null && stubSizes.length > 0) {
            MAX_STUB_ARGS = stubSizes.length - 1;
            curUseStubIndexes = new AtomicInteger[MAX_STUB_ARGS + 1];
            for (int i = 0; i < MAX_STUB_ARGS + 1; i++) {
                curUseStubIndexes[i] = new AtomicInteger(0);
                ALL_STUB += stubSizes[i];
            }
            originMethods = new Member[ALL_STUB];
            hookMethodEntities = new HookMethodEntity[ALL_STUB];
            additionalHookInfos = new XposedBridge.AdditionalHookInfo[ALL_STUB];
        }
    }


    public static HookMethodEntity getHookMethodEntity(Member origin, XposedBridge.AdditionalHookInfo additionalHookInfo) {

        if (!support()) {
            return null;
        }

        Class[] parType;
        Class retType;
        boolean isStatic = Modifier.isStatic(origin.getModifiers());

        if (origin instanceof Method) {
            Method method = (Method) origin;
            retType = method.getReturnType();
            parType = method.getParameterTypes();
        } else if (origin instanceof Constructor) {
            Constructor constructor = (Constructor) origin;
            retType = Void.TYPE;
            parType = constructor.getParameterTypes();
        } else {
            return null;
        }

        if (!ParamWrapper.support(retType))
            return null;

        int needStubArgCount = isStatic ? 0 : 1;

        if (parType != null) {
            needStubArgCount += parType.length;
            if (needStubArgCount > MAX_STUB_ARGS)
                return null;
            if (is64Bit && needStubArgCount > MAX_64_ARGS)
                return null;
            for (Class par:parType) {
                if (!ParamWrapper.support(par))
                    return null;
            }
        } else {
            parType = new Class[0];
        }

        synchronized (HookStubManager.class) {
            StubMethodsInfo stubMethodInfo = getStubMethodPair(is64Bit, needStubArgCount);
            if (stubMethodInfo == null)
                return null;
            HookMethodEntity entity = new HookMethodEntity(origin, stubMethodInfo.hook, stubMethodInfo.backup);
            entity.retType = retType;
            entity.parType = parType;
            if (hasStubBackup && !tryCompileAndResolveCallOriginMethod(entity.backup, stubMethodInfo.args, stubMethodInfo.index)) {
                DexLog.w("internal stub <" + entity.hook.getName() + "> call origin compile failure, skip use internal stub");
                return null;
            } else {
                int id = getMethodId(stubMethodInfo.args, stubMethodInfo.index);
                originMethods[id] = origin;
                hookMethodEntities[id] = entity;
                additionalHookInfos[id] = additionalHookInfo;
                return entity;
            }
        }
    }

    public static int getMethodId(int args, int index) {
        int id = index;
        for (int i = 0;i < args;i++) {
            id += stubSizes[i];
        }
        return id;
    }

    public static String getHookMethodName(int index) {
        return "stub_hook_" + index;
    }

    public static String getBackupMethodName(int index) {
        return "stub_backup_" + index;
    }

    public static String getCallOriginClassName(int args, int index) {
        return "call_origin_" + args + "_" + index;
    }


    static class StubMethodsInfo {
        int args = 0;
        int index = 0;
        Method hook;
        Method backup;

        public StubMethodsInfo(int args, int index, Method hook, Method backup) {
            this.args = args;
            this.index = index;
            this.hook = hook;
            this.backup = backup;
        }
    }

    private static synchronized StubMethodsInfo getStubMethodPair(boolean is64Bit, int stubArgs) {

        stubArgs = getMatchStubArgsCount(stubArgs);

        if (stubArgs < 0)
            return null;

        int curUseStubIndex = curUseStubIndexes[stubArgs].getAndIncrement();
        Class[] pars = getFindMethodParTypes(is64Bit, stubArgs);
        try {
            if (is64Bit) {
                Method hook = MethodHookerStubs64.class.getDeclaredMethod(getHookMethodName(curUseStubIndex), pars);
                Method backup = hasStubBackup ? MethodHookerStubs64.class.getDeclaredMethod(getBackupMethodName(curUseStubIndex), pars) : StubMethodsFactory.getStubMethod();
                if (hook == null || backup == null)
                    return null;
                return new StubMethodsInfo(stubArgs, curUseStubIndex, hook, backup);
            } else {
                Method hook = MethodHookerStubs32.class.getDeclaredMethod(getHookMethodName(curUseStubIndex), pars);
                Method backup = hasStubBackup ? MethodHookerStubs32.class.getDeclaredMethod(getBackupMethodName(curUseStubIndex), pars) : StubMethodsFactory.getStubMethod();
                if (hook == null || backup == null)
                    return null;
                return new StubMethodsInfo(stubArgs, curUseStubIndex, hook, backup);
            }
        } catch (Throwable throwable) {
            return null;
        }
    }

    public static Method getCallOriginMethod(int args, int index) {
        Class stubClass = is64Bit ? MethodHookerStubs64.class : MethodHookerStubs32.class;
        String className = stubClass.getName();
        className += "$";
        className += getCallOriginClassName(args, index);
        try {
            Class callOriginClass = Class.forName(className, true, stubClass.getClassLoader());
            return callOriginClass.getDeclaredMethod("call", long[].class);
        } catch (Throwable e) {
            Log.e("HookStubManager", "load call origin class error!", e);
            return null;
        }
    }

    public static boolean tryCompileAndResolveCallOriginMethod(Method backupMethod, int args, int index) {
        Method method = getCallOriginMethod(args, index);
        if (method != null) {
            SandHookMethodResolver.resolveMethod(method, backupMethod);
            return SandHook.compileMethod(method);
        } else {
            return false;
        }
    }

    public static int getMatchStubArgsCount(int stubArgs) {
        for (int i = stubArgs;i <= MAX_STUB_ARGS;i++) {
            if (curUseStubIndexes[i].get() < stubSizes[i])
                return i;
        }
        return -1;
    }

    public static Class[] getFindMethodParTypes(boolean is64Bit, int stubArgs) {
        if (stubArgs == 0)
            return null;
        Class[] args = new Class[stubArgs];
        if (is64Bit) {
            for (int i = 0;i < stubArgs;i++) {
                args[i] = long.class;
            }
        } else {
            for (int i = 0;i < stubArgs;i++) {
                args[i] = int.class;
            }
        }
        return args;
    }

    public static long hookBridge(int id, CallOriginCallBack callOrigin, long... stubArgs) throws Throwable {

        Member originMethod = originMethods[id];
        HookMethodEntity entity = hookMethodEntities[id];

        Object thiz = null;
        Object[] args = null;

        if (hasArgs(stubArgs)) {
            thiz = entity.getThis(stubArgs[0]);
            args = entity.getArgs(stubArgs);
        }

        if (thiz == null)
        {
            thiz = originMethod.getDeclaringClass();
        }

        if (XposedBridge.disableHooks) {
            if (hasStubBackup) {
                return callOrigin.call(stubArgs);
            } else {
                return callOrigin(entity, originMethod, thiz, args);
            }
        }

        DexLog.printMethodHookIn(originMethod);

        Object[] snapshot = additionalHookInfos[id].callbacks.getSnapshot();

        if (snapshot == null || snapshot.length == 0) {
            if (hasStubBackup) {
                return callOrigin.call(stubArgs);
            } else {
                return callOrigin(entity, originMethod, thiz, args);
            }
        }

        XC_MethodHook.MethodHookParam param = new XC_MethodHook.MethodHookParam();

        param.method = originMethod;
        param.thisObject = thiz;
        param.args = args;

        int beforeIdx = 0;
        do {
            try {
                ((XC_MethodHook) snapshot[beforeIdx]).callBeforeHookedMethod(param);
            } catch (Throwable t) {
                // reset result (ignoring what the unexpectedly exiting callback did)
                if( BuildConfig.DEBUG ) XposedBridge.log(t);
                param.setResult(null);
                param.returnEarly = false;
                continue;
            }

            if (param.returnEarly) {
                // skip remaining "before" callbacks and corresponding "after" callbacks
                beforeIdx++;
                break;
            }
        } while (++beforeIdx < snapshot.length);

        // call original method if not requested otherwise
        if (!param.returnEarly) {
            try {
                if (hasStubBackup) {
                    //prepare new args
                    long[] newArgs = entity.getArgsAddress(stubArgs, param.args);
                    param.setResult(entity.getResult(callOrigin.call(newArgs)));
                } else {
                    param.setResult(SandHook.callOriginMethod(originMethod, entity.backup, thiz, param.args));
                }
            } catch (Throwable e) {
                if( BuildConfig.DEBUG ) XposedBridge.log(e);
                param.setThrowable(e);
            }
        }

        // call "after method" callbacks
        int afterIdx = beforeIdx - 1;
        do {
            Object lastResult =  param.getResult();
            Throwable lastThrowable = param.getThrowable();

            try {
                ((XC_MethodHook) snapshot[afterIdx]).callAfterHookedMethod(param);
            } catch (Throwable t) {
                if( BuildConfig.DEBUG ) XposedBridge.log(t);
                if (lastThrowable == null)
                    param.setResult(lastResult);
                else
                    param.setThrowable(lastThrowable);
            }
        } while (--afterIdx >= 0);
        if (!param.hasThrowable()) {
            return entity.getResultAddress(param.getResult());
        } else {
            throw param.getThrowable();
        }
    }

    public static Object hookBridge(Member origin, Method backup, XposedBridge.AdditionalHookInfo additionalHookInfo, Object thiz, Object... args) throws Throwable {


        if (XposedBridge.disableHooks) {
            return SandHook.callOriginMethod(true, origin, backup, thiz, args);
        }

        DexLog.printMethodHookIn(origin);

        Object[] snapshot = additionalHookInfo.callbacks.getSnapshot();

        if (snapshot == null || snapshot.length == 0) {
            return SandHook.callOriginMethod(origin, backup, thiz, args);
        }

        XC_MethodHook.MethodHookParam param = new XC_MethodHook.MethodHookParam();

        param.method = origin;
        param.thisObject = thiz;
        param.args = args;

        int beforeIdx = 0;
        do {
            try {
                ((XC_MethodHook) snapshot[beforeIdx]).callBeforeHookedMethod(param);
            } catch (Throwable t) {
                if( BuildConfig.DEBUG ) XposedBridge.log(t);
                // reset result (ignoring what the unexpectedly exiting callback did)
                param.setResult(null);
                param.returnEarly = false;
                continue;
            }

            if (param.returnEarly) {
                // skip remaining "before" callbacks and corresponding "after" callbacks
                beforeIdx++;
                break;
            }
        } while (++beforeIdx < snapshot.length);

        // call original method if not requested otherwise
        if (!param.returnEarly) {
            try {
                param.setResult(SandHook.callOriginMethod(true, origin, backup, thiz, param.args));
            } catch (Throwable e) {
                if( BuildConfig.DEBUG ) XposedBridge.log(e);
                param.setThrowable(e);
            }
        }

        // call "after method" callbacks
        int afterIdx = beforeIdx - 1;
        do {
            Object lastResult =  param.getResult();
            Throwable lastThrowable = param.getThrowable();

            try {
                ((XC_MethodHook) snapshot[afterIdx]).callAfterHookedMethod(param);
            } catch (Throwable t) {
                if( BuildConfig.DEBUG ) XposedBridge.log(t);
                if (lastThrowable == null)
                    param.setResult(lastResult);
                else
                    param.setThrowable(lastThrowable);
            }
        } while (--afterIdx >= 0);
        if (!param.hasThrowable()) {
            return param.getResult();
        } else {
            throw param.getThrowable();
        }
    }

    public final static long callOrigin(HookMethodEntity entity, Member origin, Object thiz, Object[] args) throws Throwable {
        Object res = SandHook.callOriginMethod(true, origin, entity.backup, thiz, args);
        return entity.getResultAddress(res);
    }

    private static boolean hasArgs(long... args) {
        return args != null && args.length > 0;
    }

    public static boolean support() {
        return MAX_STUB_ARGS > 0 && SandHook.canGetObject() && SandHook.canGetObjectAddress();
    }

}
