package com.elderdrivers.riru.edxp.sandhook.entry.hooker;

import android.app.ActivityThread;
import android.app.LoadedApk;
import android.content.ComponentName;
import android.content.pm.ApplicationInfo;
import android.content.res.CompatibilityInfo;

import com.elderdrivers.riru.common.KeepMembers;
import com.elderdrivers.riru.edxp.Main;
import com.elderdrivers.riru.edxp.sandhook.entry.Router;
import com.elderdrivers.riru.edxp.util.Utils;
import com.swift.sandhook.SandHook;
import com.swift.sandhook.annotation.HookClass;
import com.swift.sandhook.annotation.HookMethod;
import com.swift.sandhook.annotation.HookMethodBackup;
import com.swift.sandhook.annotation.Param;
import com.swift.sandhook.annotation.SkipParamCheck;
import com.swift.sandhook.annotation.ThisObject;

import java.lang.reflect.Method;

import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.XposedInit;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static com.elderdrivers.riru.edxp.config.InstallerChooser.INSTALLER_PACKAGE_NAME;
import static com.elderdrivers.riru.edxp.sandhook.entry.hooker.XposedBlackListHooker.BLACK_LIST_PACKAGE_NAME;
import static com.elderdrivers.riru.edxp.util.ClassLoaderUtils.replaceParentClassLoader;

// normal process initialization (for new Activity, Service, BroadcastReceiver etc.)
@HookClass(ActivityThread.class)
public class HandleBindAppHooker implements KeepMembers {

    public static String className = "android.app.ActivityThread";
    public static String methodName = "handleBindApplication";
    public static String methodSig = "(Landroid/app/ActivityThread$AppBindData;)V";

    @HookMethodBackup("handleBindApplication")
    @SkipParamCheck
    static Method backup;

    @HookMethod("handleBindApplication")
    public static void hook(@ThisObject ActivityThread thiz, @Param("android.app.ActivityThread$AppBindData") Object bindData) throws Throwable {
        if (XposedBlackListHooker.shouldDisableHooks("")) {
            backup(thiz, bindData);
            return;
        }
        try {
            Router.logD("ActivityThread#handleBindApplication() starts");
            ActivityThread activityThread = (ActivityThread) thiz;
            ApplicationInfo appInfo = (ApplicationInfo) XposedHelpers.getObjectField(bindData, "appInfo");
            // save app process name here for later use
            Main.appProcessName = (String) XposedHelpers.getObjectField(bindData, "processName");
            String reportedPackageName = appInfo.packageName.equals("android") ? "system" : appInfo.packageName;
            Utils.logD("processName=" + Main.appProcessName +
                    ", packageName=" + reportedPackageName + ", appDataDir=" + Main.appDataDir);

            if (XposedBlackListHooker.shouldDisableHooks(reportedPackageName)) {
                return;
            }

            ComponentName instrumentationName = (ComponentName) XposedHelpers.getObjectField(bindData, "instrumentationName");
            if (instrumentationName != null) {
                Router.logD("Instrumentation detected, disabling framework for");
                XposedBridge.disableHooks = true;
                return;
            }
            CompatibilityInfo compatInfo = (CompatibilityInfo) XposedHelpers.getObjectField(bindData, "compatInfo");
            if (appInfo.sourceDir == null) {
                return;
            }

            XposedHelpers.setObjectField(activityThread, "mBoundApplication", bindData);
            XposedInit.loadedPackagesInProcess.add(reportedPackageName);
            LoadedApk loadedApk = activityThread.getPackageInfoNoCheck(appInfo, compatInfo);

            replaceParentClassLoader(loadedApk.getClassLoader());

            XC_LoadPackage.LoadPackageParam lpparam = new XC_LoadPackage.LoadPackageParam(XposedBridge.sLoadedPackageCallbacks);
            lpparam.packageName = reportedPackageName;
            lpparam.processName = (String) XposedHelpers.getObjectField(bindData, "processName");
            lpparam.classLoader = loadedApk.getClassLoader();
            lpparam.appInfo = appInfo;
            lpparam.isFirstApplication = true;
            XC_LoadPackage.callAll(lpparam);

            if (reportedPackageName.equals(INSTALLER_PACKAGE_NAME)) {
                XposedInstallerHooker.hookXposedInstaller(lpparam.classLoader);
            }
            if (reportedPackageName.equals(BLACK_LIST_PACKAGE_NAME)) {
                XposedBlackListHooker.hook(lpparam.classLoader);
            }
        } catch (Throwable t) {
            Router.logE("error when hooking bindApp", t);
        } finally {
            backup(thiz, bindData);
        }
    }

    public static void backup(Object thiz, Object bindData) throws Throwable {
        SandHook.callOriginByBackup(backup, thiz, bindData);
    }
}