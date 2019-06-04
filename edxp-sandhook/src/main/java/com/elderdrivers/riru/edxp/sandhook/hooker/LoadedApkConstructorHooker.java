package com.elderdrivers.riru.edxp.sandhook.hooker;

import android.app.ActivityThread;
import android.app.LoadedApk;
import android.content.pm.ApplicationInfo;
import android.content.res.CompatibilityInfo;

import com.elderdrivers.riru.common.KeepMembers;
import com.elderdrivers.riru.edxp._hooker.impl.LoadedApkCstr;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.annotation.HookClass;
import com.swift.sandhook.annotation.HookMethod;
import com.swift.sandhook.annotation.HookMethodBackup;
import com.swift.sandhook.annotation.SkipParamCheck;
import com.swift.sandhook.annotation.ThisObject;

import java.lang.reflect.Method;

import de.robv.android.xposed.XC_MethodHook;

@HookClass(LoadedApk.class)
public class LoadedApkConstructorHooker implements KeepMembers {
    public static String className = "android.app.LoadedApk";
    public static String methodName = "<init>";
    public static String methodSig = "(Landroid/app/ActivityThread;" +
            "Landroid/content/pm/ApplicationInfo;" +
            "Landroid/content/res/CompatibilityInfo;" +
            "Ljava/lang/ClassLoader;ZZZ)V";

    @HookMethodBackup
    @SkipParamCheck
    static Method backup;

    @HookMethod
    public static void hook(@ThisObject Object thiz, ActivityThread activityThread,
                            ApplicationInfo aInfo, CompatibilityInfo compatInfo,
                            ClassLoader baseLoader, boolean securityViolation,
                            boolean includeCode, boolean registerPackage) throws Throwable {
        final XC_MethodHook methodHook = new LoadedApkCstr();
        final XC_MethodHook.MethodHookParam param = new XC_MethodHook.MethodHookParam();
        param.thisObject = thiz;
        param.args = new Object[]{activityThread, aInfo, compatInfo, baseLoader, securityViolation,
                includeCode, registerPackage};
        methodHook.callBeforeHookedMethod(param);
        if (!param.returnEarly) {
            backup(thiz, activityThread, aInfo, compatInfo, baseLoader, securityViolation,
                    includeCode, registerPackage);
        }
        methodHook.callAfterHookedMethod(param);
    }

    public static void backup(Object thiz, ActivityThread activityThread,
                              ApplicationInfo aInfo, CompatibilityInfo compatInfo,
                              ClassLoader baseLoader, boolean securityViolation,
                              boolean includeCode, boolean registerPackage) throws Throwable {
        SandHook.callOriginByBackup(backup, thiz, activityThread, aInfo, compatInfo, baseLoader, securityViolation, includeCode, registerPackage);
    }
}