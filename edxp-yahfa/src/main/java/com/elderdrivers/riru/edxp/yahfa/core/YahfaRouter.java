package com.elderdrivers.riru.edxp.yahfa.core;

import com.elderdrivers.riru.edxp.config.EdXpConfigGlobal;
import com.elderdrivers.riru.edxp.proxy.BaseRouter;
import com.elderdrivers.riru.edxp.yahfa.config.YahfaEdxpConfig;
import com.elderdrivers.riru.edxp.yahfa.config.YahfaHookProvider;
import com.elderdrivers.riru.edxp.yahfa.dexmaker.DynamicBridge;

import de.robv.android.xposed.XposedBridge;

public class YahfaRouter extends BaseRouter {

    public void onEnterChildProcess() {
        DynamicBridge.onForkPost();
    }

    public void injectConfig() {
        EdXpConfigGlobal.sConfig = new YahfaEdxpConfig();
        EdXpConfigGlobal.sHookProvider = new YahfaHookProvider();
        XposedBridge.log("using HookProvider: " + EdXpConfigGlobal.sHookProvider.getClass().getName());
    }

}
