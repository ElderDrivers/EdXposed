#!/sbin/sh

MODDIR=${0%/*}

rm -rf /data/misc/riru/modules/edxp
if [[ -f "/data/adb/riru/modules/edxp.prop" ]]; then
    OLD_CONFIG=$(cat "/data/adb/riru/modules/edxp.prop")
    rm -rf "/data/adb/riru/modules/${OLD_CONFIG}"
    rm "/data/adb/riru/modules/edxp.prop"
fi
if [[ -f "/data/misc/riru/modules/edxp.prop" ]]; then
    OLD_CONFIG=$(cat "/data/misc/riru/modules/edxp.prop")
    rm -rf "/data/misc/riru/modules/${OLD_CONFIG}"
    rm "/data/misc/riru/modules/edxp.prop"
fi
