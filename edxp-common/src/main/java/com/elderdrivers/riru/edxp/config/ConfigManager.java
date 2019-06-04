package com.elderdrivers.riru.edxp.config;

import java.util.HashMap;

import de.robv.android.xposed.SELinuxHelper;

import static com.elderdrivers.riru.edxp.config.InstallerChooser.INSTALLER_DATA_BASE_DIR;

public class ConfigManager {

    public static String appDataDir = "";
    public static String niceName = "";
    public static String appProcessName = "";

    private static final String COMPAT_LIST_PATH = INSTALLER_DATA_BASE_DIR + "conf/compatlist/";
    private static final HashMap<String, Boolean> compatModeCache = new HashMap<>();

    public static boolean shouldUseCompatMode(String packageName) {
        Boolean result;
        if (compatModeCache.containsKey(packageName)
                && (result = compatModeCache.get(packageName)) != null) {
            return result;
        }
        result = isFileExists(COMPAT_LIST_PATH + packageName);
        compatModeCache.put(packageName, result);
        return result;
    }

    private static boolean isFileExists(String path) {
        return SELinuxHelper.getAppDataFileService().checkFileExists(path);
    }

    public static native boolean isBlackWhiteListEnabled();

    public static native boolean isDynamicModulesEnabled();

    public static native boolean isResourcesHookEnabled();

    public static native boolean isDeoptBootImageEnabled();

    public static native String getInstallerPackageName();

    public static native boolean isAppNeedHook(String appDataDir);
}
