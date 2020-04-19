package com.elderdrivers.riru.edxp.framework;

import android.os.Process;
import android.os.UserHandle;

import static de.robv.android.xposed.XposedHelpers.callStaticMethod;
import static de.robv.android.xposed.XposedHelpers.getStaticIntField;

public class ProcessHelper {

    /**
     * Defines the UID/GID for the shared RELRO file updater process.
     */
    public static final int SHARED_RELRO_UID = getStaticIntField(Process.class, "SHARED_RELRO_UID");

    /**
     * Defines the UID/GID for the WebView zygote process.
     */
    public static final int WEBVIEW_ZYGOTE_UID = getStaticIntField(Process.class, "WEBVIEW_ZYGOTE_UID");

    public static int getAppId(int uid) {
        return (int) callStaticMethod(UserHandle.class, "getAppId", uid);
    }

    public static boolean isRELROUpdater(int uid) {
        return getAppId(uid) == SHARED_RELRO_UID;
    }

    public static boolean isWebViewZygote(int uid) {
        return getAppId(uid) == WEBVIEW_ZYGOTE_UID;
    }

    public static boolean isIsolated(int uid) {
        return (boolean) callStaticMethod(UserHandle.class, "isIsolated", uid);
    }
}
