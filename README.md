# EdXposed Framework

[![Latest builds](https://ci.appveyor.com/api/projects/status/qu3vj1d64nqia1b8/branch/master?svg=true)](https://ci.appveyor.com/project/ElderDrivers/edxposed/branch/master) [![中文说明文档](art/README_CN.png)](README_CN.md)

## Introduction 

A Riru module trying to provide an ART hooking framework (initially for Android Pie) which delivers consistent APIs with the OG Xposed, leveraging YAHFA (or SandHook) hooking framework, supports Android 8.0 ~ **10**.

> Xposed is a framework for modules that can change the behavior of the system and apps without touching any APKs. That's great because it means that modules can work for different versions and even ROMs without any changes (as long as the original code was not changed too much). It's also easy to undo. As all changes are done in the memory, you just need to deactivate the module and reboot to get your original system back. There are many other advantages, but here is just one more: Multiple modules can do changes to the same part of the system or app. With modified APKs, you to decide for one. No way to combine them, unless the author builds multiple APKs with different combinations.

## Supported Versions

- Android Q (10)[sdk29]
- Android Pie (9)[sdk28]
- Android Oreo (8.x)[sdk26/27]

For Android Nougat (7.x) and lower, please use the original Xposed Framework.

## Build Requirements

Same as [Riru-Core's](https://github.com/RikkaApps/Riru/blob/master/README.md#build-requirements)
and zip binaries can be downloaded from [here](http://gnuwin32.sourceforge.net/packages/zip.htm).

## Build

1. Execute task `:edxp-core:[zip|push][Yahfa|Sandhook]Release` to build flashable zip for corresponding variant.
2. Find the flashable under `edxp-core/release/`.
3. Flash the zip in recovery mode or via Magisk Manager.

## Install

1. Install Magisk v19.0+ (for latest EdXposed or Huawei devices, use at least official Magisk v20.2+ or use our custom Magisk: Change Magisk update channel to [this](http://edxp.meowcat.org/repo/version.json)).
2. Install [Riru-Core](https://github.com/RikkaApps/Riru/releases) v19+ from Magisk repo.
3. [Download](#download) and install EdXposed in Magisk Manager or recovery mode.
4. Install [companion application](#companion-applications).
4. Reboot.
5. Have fun! :)

## Download

Edxposed has three different builds

- Beta：Stable version after passing the test, suitable for general users, update slowly.
***Download Beta version in Magisk Manager's [Downloads] tab***

- Alpha: Test version with multiple commits.
***Download Alpha version in [[Github Releases](https://github.com/ElderDrivers/EdXposed/releases)]***

- Canary: Debug version. Automatically build by CI.
***Download Canary version in [[EdXposed Manager](https://github.com/ElderDrivers/EdXposedManager)]***

## Companion Applications

- For v0.2.9.5 and before: [Xposed Installer](https://github.com/DVDAndroid/XposedInstaller).
- For v0.2.9.6 and v0.2.9.7: [Xposed Installer](https://github.com/DVDAndroid/XposedInstaller) + [EdXp Manager](https://github.com/solohsu/EdXpManager)(optional).
- For v0.2.9.8 and later: [EdXposed Installer](https://github.com/solohsu/XposedInstaller) and [EdXposed Manager](https://github.com/ElderDrivers/EdXposedManager).
- For the latest version, we recommend to use [EdXposed Manager](https://github.com/ElderDrivers/EdXposedManager).

## Useful Links

- [List of Xposed Modules For Android Pie Working With EdXposed](https://forum.xda-developers.com/xposed/list-xposed-modules-android-pie-ed-t3892768) (thanks to Uraniam9 @ xda-developers)

## Known Issues

- [ ] May not be compatible with all ART devices.
- [ ] (Provisional solved, Use magiskpolicy to enable needed SELinux policies) File access services are not implemented yet.
- [ ] (Provisional solved, #384) Wait for Magisk sepolicy update.
- [ ] (Provisional solved, #386) SafetyNet and some app tripped EdXposed.
- [ ] (Provisional solved, #401) Some module cannot hook app, such as WeXposed. 
- [x] (Fixed, use EdXposed Manager v4.5.2 and newer) ~~Dynamic modules not work, caused by SELinux.~~

## Get Help

- GitHub issues: [Issues](https://github.com/ElderDrivers/EdXposed/issues/)

- Notice(for Chinese): In view of the low quality of issues submitted, please read the Chinese user report first[EdXposedIssuesReport_cn](http://edxp.meowcat.org/repo/EdXposedIssuesReport_cn.txt)(If you don't read the instructions, the submitted issue is likely to be closed)

## For Developers 

Developers are welcomed to write Xposed modules with hooks based on EdXposed Framework. Module written based on EdXposed framework is fully compatible with the original Xposed Framework, so contrary a Xposed Framework-based module will work well with the EdXposed framework too. 

- [Xposed Framework API](https://api.xposed.info/)

We use the module repository of the original Xposed, so you simply upload the module to repository, then you can download your module in EdXposed.

- [Xposed Module Repository](https://repo.xposed.info/)

## Community Discussion

- QQ Group: [855219808](http://shang.qq.com/wpa/qunwpa?idkey=fae42a3dba9dc758caf63e971be2564e67bf7edd751a2ff1c750478b0ad1ca3f)
- Telegram: [@Code_of_MeowCat](http://t.me/Code_of_MeowCat)

Notice: These community group don't accept any bug report, please use [Get help](#get-help) to report.

## Contribute

- Apparently this framework is far from stable and all kinds of PRs are welcome. :)
- [Buy me a coffee](https://www.paypal.me/givin2u) if you like my work.

## Credits 

- [YAHFA](https://github.com/rk700/YAHFA): the core ART hooking framework
- [Magisk](https://github.com/topjohnwu/Magisk/): makes all these possible
- [Riru](https://github.com/RikkaApps/Riru): provides a way to inject codes into zygote process
- [XposedBridge](https://github.com/rovo89/XposedBridge): the OG xposed framework APIs
- [dexmaker](https://github.com/linkedin/dexmaker) and [dalvikdx](https://github.com/JakeWharton/dalvik-dx): to dynamiclly generate YAHFA hooker classes
- [Whale](https://github.com/asLody/whale): used for inline hooking
- [SandHook](https://github.com/ganyao114/SandHook/): ART hooking framework for SandHook variant

