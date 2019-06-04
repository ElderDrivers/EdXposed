package com.elderdrivers.riru.edxp._hooker.yahfa;

import android.app.ActivityThread;

import com.elderdrivers.riru.common.KeepMembers;
import com.elderdrivers.riru.edxp._hooker.impl.OneplusWorkaround;

import de.robv.android.xposed.XC_MethodHook;

public class SystemMainHooker implements KeepMembers {

    public static String className = "android.app.ActivityThread";
    public static String methodName = "systemMain";
    public static String methodSig = "()Landroid/app/ActivityThread;";

    public static ClassLoader systemServerCL;

    public static ActivityThread hook() throws Throwable {
        final XC_MethodHook methodHook = new OneplusWorkaround();
        final XC_MethodHook.MethodHookParam param = new XC_MethodHook.MethodHookParam();
        param.thisObject = null;
        param.args = new Object[]{};
        methodHook.callBeforeHookedMethod(param);
        if (!param.returnEarly) {
            param.setResult(backup());
        }
        methodHook.callAfterHookedMethod(param);
        return (ActivityThread) param.getResult();
    }

    public static ActivityThread backup() {
        return null;
    }
}