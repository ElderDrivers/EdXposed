package com.elderdrivers.riru.edxp.proxy;

import com.elderdrivers.riru.edxp.config.ConfigManager;
import com.elderdrivers.riru.edxp.deopt.PrebuiltMethodsDeopter;

import static com.elderdrivers.riru.edxp.util.FileUtils.getDataPathPrefix;

public class NormalProxy extends BaseProxy {

    public NormalProxy(Router router) {
        super(router);
    }

    public void forkAndSpecializePre(int uid, int gid, int[] gids, int debugFlags,
                                     int[][] rlimits, int mountExternal, String seInfo,
                                     String niceName, int[] fdsToClose, int[] fdsToIgnore,
                                     boolean startChildZygote, String instructionSet,
                                     String appDataDir) {
        // mainly for secondary zygote
        mRouter.onForkStart();
        mRouter.initResourcesHook();
        // call this to ensure the flag is set to false ASAP
        mRouter.prepare(false);
        PrebuiltMethodsDeopter.deoptBootMethods(); // do it once for secondary zygote
        // install bootstrap hooks for secondary zygote
        mRouter.installBootstrapHooks(false);
        // only load modules for secondary zygote
        mRouter.loadModulesSafely(true);
    }

    public void forkAndSpecializePost(int pid, String appDataDir, String niceName) {
        // TODO consider processes without forkAndSpecializePost called
        ConfigManager.appDataDir = appDataDir;
        ConfigManager.niceName = niceName;
        mRouter.prepare(false);
        mRouter.onEnterChildProcess();
        // load modules for each app process on its forked if dynamic modules mode is on
        mRouter.loadModulesSafely(false);
        mRouter.onForkFinish();
    }

    public void forkSystemServerPre(int uid, int gid, int[] gids, int debugFlags, int[][] rlimits,
                                    long permittedCapabilities, long effectiveCapabilities) {
        mRouter.onForkStart();
        mRouter.initResourcesHook();
        // set startsSystemServer flag used when loadModules
        mRouter.prepare(true);
        PrebuiltMethodsDeopter.deoptBootMethods(); // do it once for main zygote
        // install bootstrap hooks for main zygote as early as possible
        // in case we miss some processes not forked via forkAndSpecialize
        // for instance com.android.phone
        mRouter.installBootstrapHooks(true);
        // loadModules have to be executed in zygote even isDynamicModules is false
        // because if not global hooks installed in initZygote might not be
        // propagated to processes not forked via forkAndSpecialize
        mRouter.loadModulesSafely(true);
    }

    public void forkSystemServerPost(int pid) {
        // in system_server process
        ConfigManager.appDataDir = getDataPathPrefix() + "android";
        ConfigManager.niceName = "system_server";
        mRouter.prepare(true);
        mRouter.onEnterChildProcess();
        // reload module list if dynamic mode is on
        mRouter.loadModulesSafely(false);
        mRouter.onForkFinish();
    }

}
