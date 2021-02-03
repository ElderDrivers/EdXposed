package com.elderdrivers.riru.edxp.hooker;

import android.app.AndroidAppHelper;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elderdrivers.riru.edxp.common.BuildConfig;
import com.elderdrivers.riru.edxp.config.ConfigManager;
import com.elderdrivers.riru.edxp.config.EdXpConfigGlobal;
import com.elderdrivers.riru.edxp.core.EdxpImpl;
import com.elderdrivers.riru.edxp.core.Main;
import com.elderdrivers.riru.edxp.util.Utils;

import java.io.File;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class XposedInstallerHooker {

    private static final String CONSTANTS_CLASS = "org.meowcat.edxposed.manager.Constants";

    public static void hookXposedInstaller(final ClassLoader classLoader) {
        // Deopt manager. It will not throw exception.
        deoptMethod(classLoader, "org.meowcat.edxposed.manager.ModulesFragment", "onActivityCreated", Bundle.class);
        deoptMethod(classLoader, "org.meowcat.edxposed.manager.ModulesFragment", "showMenu", Context.class, View.class, ApplicationInfo.class);
        deoptMethod(classLoader, "org.meowcat.edxposed.manager.StatusInstallerFragment", "onCreateView", LayoutInflater.class, ViewGroup.class, Bundle.class);
        deoptMethod(classLoader, "org.meowcat.edxposed.manager.util.ModuleUtil", "updateModulesList", boolean.class, View.class);

        try {
            String variant = "Unknown";
            switch (Main.getEdxpVariant()) {
                case EdxpImpl.NONE:
                    break;
                case EdxpImpl.YAHFA:
                    variant = "YAHFA";
                    break;
                case EdxpImpl.SANDHOOK:
                    variant = "SandHook";
                    break;
            }

            XposedHelpers.findAndHookMethod(CONSTANTS_CLASS, classLoader, "getActiveXposedVersion",
                    XC_MethodReplacement.returnConstant(XposedBridge.getXposedVersion())
            );

            XposedHelpers.findAndHookMethod(CONSTANTS_CLASS, classLoader, "getInstalledXposedVersion",
                    XC_MethodReplacement.returnConstant(BuildConfig.VERSION_NAME + "_" + BuildConfig.VERSION_CODE + " (" + variant + ")")
            );

            XposedHelpers.findAndHookMethod(CONSTANTS_CLASS, classLoader, "getBaseDir",
                    XC_MethodReplacement.returnConstant(ConfigManager.getBaseConfigPath() + "/")
            );

            XposedHelpers.findAndHookMethod(CONSTANTS_CLASS, classLoader, "isSELinuxEnforced",
                    XC_MethodReplacement.returnConstant(ConfigManager.isSELinuxEnforced())
            );

        } catch (Throwable t) {
            Utils.logE("Could not hook EdXposed Manager", t);
        }
    }

    private static void deoptMethod(ClassLoader cl, String className, String methodName, Class<?> ...params) {
        try {
            Class clazz = XposedHelpers.findClassIfExists(className, cl);
            if (clazz == null) {
                Utils.logE("Class " + className + " not found when deoptimizing EdXposed Manager");
                return;
            }

            Object method = XposedHelpers.findMethodExact(clazz, methodName, params);
            EdXpConfigGlobal.getHookProvider().deoptMethodNative(method);
        } catch (Exception e) {
            Utils.logE("Error when deoptimizing " + className + ":" + methodName, e);
        }

    }
}
