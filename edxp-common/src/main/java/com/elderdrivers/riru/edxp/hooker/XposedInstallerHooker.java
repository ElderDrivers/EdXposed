package com.elderdrivers.riru.edxp.hooker;

import com.elderdrivers.riru.edxp.common.BuildConfig;
import com.elderdrivers.riru.edxp.core.EdxpImpl;
import com.elderdrivers.riru.edxp.core.Main;
import com.elderdrivers.riru.edxp.util.Utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;

public class XposedInstallerHooker {

    private static final String LEGACY_INSTALLER_PACKAGE_NAME = "de.robv.android.xposed.installer";

    public static void hookXposedInstaller(ClassLoader classLoader) {
        try {
            final String xposedAppClass = LEGACY_INSTALLER_PACKAGE_NAME + ".XposedApp";
            final Class InstallZipUtil = XposedHelpers.findClass(LEGACY_INSTALLER_PACKAGE_NAME
                    + ".util.InstallZipUtil", classLoader);
            XposedHelpers.findAndHookMethod(xposedAppClass, classLoader, "getActiveXposedVersion", new XC_MethodHook() {
                @Override
                protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                    Utils.logD("after getActiveXposedVersion...");
                    param.setResult(XposedBridge.getXposedVersion());
                }
            });
            XposedHelpers.findAndHookMethod(xposedAppClass, classLoader,
                    "reloadXposedProp", new XC_MethodHook() {
                        @Override
                        protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                            Utils.logD("before reloadXposedProp...");
                            final String propFieldName = "mXposedProp";
                            final Object thisObject = param.thisObject;
                            if (thisObject == null) {
                                return;
                            }
                            if (XposedHelpers.getObjectField(thisObject, propFieldName) != null) {
                                param.setResult(null);
                                Utils.logD("reloadXposedProp already done, skip...");
                                return;
                            }
                            //version=92.0-$version ($backend)
                            StringBuilder stringBuilder = new StringBuilder();
                            stringBuilder.append("version=");
                            stringBuilder.append(XposedBridge.getXposedVersion());
                            stringBuilder.append(".0-");
                            stringBuilder.append(BuildConfig.VERSION_NAME);
                            stringBuilder.append(" (");
                            String variant = "None";
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
                            stringBuilder.append(variant);
                            stringBuilder.append(")");
                            try (ByteArrayInputStream is = new ByteArrayInputStream(stringBuilder.toString().getBytes())) {
                                Object props = XposedHelpers.callStaticMethod(InstallZipUtil,
                                        "parseXposedProp", is);
                                synchronized (thisObject) {
                                    XposedHelpers.setObjectField(thisObject, propFieldName, props);
                                }
                                Utils.logD("reloadXposedProp done...");
                                param.setResult(null);
                            } catch (IOException e) {
                                Utils.logE("Could not reloadXposedProp", e);
                            }
                        }
                    });
        } catch (Throwable t) {
            Utils.logE("Could not hook Xposed Installer", t);
        }
    }
}
