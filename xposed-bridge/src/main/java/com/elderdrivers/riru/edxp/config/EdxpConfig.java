package com.elderdrivers.riru.edxp.config;

public interface EdxpConfig {

    String getConfigPath(String suffix);

    String getDataPathPrefix();

    String getInstallerPackageName();

    String getLibSandHookName();

    boolean isNoModuleLogEnabled();

    boolean isResourcesHookEnabled();

    boolean isSELinuxEnforced();

    boolean isBlackWhiteListMode();

    String getModulesList();
}
