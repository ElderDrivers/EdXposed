SKIPUNZIP=1

getRandomNameExist() {
    RAND_PATH=$4
    RAND_SUFFIX=$3
    RAND_PREFIX=$2
    RAND_DIGIT=$1
    RAND_RAND="$(cat /proc/sys/kernel/random/uuid|md5sum|cut -c 1-"${RAND_DIGIT}")"
    RAND_PATH_EXIST=false
    for TARGET in ${RAND_PATH}; do
        if [[ -e "${TARGET}/${RAND_PREFIX}${RAND_RAND}${RAND_SUFFIX}" ]]; then
            RAND_PATH_EXIST=true
        fi
    done
    if [[ "${RAND_PATH_EXIST}" == true ]]; then
        getRandomNameExist "${RAND_DIGIT}" "${RAND_PREFIX}" "${RAND_SUFFIX}" "${RAND_PATH}"
    else
        echo "${RAND_RAND}"
    fi
}

RIRU_PATH="/data/adb/riru"
RIRU_EDXP="$(getRandomNameExist 4 "libriru_" ".so" "
/system/lib
/system/lib64
")"
RIRU_MODULES="${RIRU_PATH}/modules"
RIRU_TARGET="${RIRU_MODULES}/${RIRU_EDXP}"

VERSION=$(grep_prop version "${TMPDIR}/module.prop")
RIRU_MIN_API_VERSION=$(grep_prop api "${TMPDIR}/module.prop")

PROP_MODEL=$(getprop ro.product.model)
PROP_DEVICE=$(getprop ro.product.device)
PROP_PRODUCT=$(getprop ro.build.product)
PROP_BRAND=$(getprop ro.product.brand)
PROP_MANUFACTURER=$(getprop ro.product.manufacturer)

JAR_EDXP="$(getRandomNameExist 8 "" ".dex" "
/system/framework
").dex"
JAR_EDDALVIKDX="$(getRandomNameExist 8 "" ".dex" "
/system/framework
").dex"
JAR_EDDEXMAKER="$(getRandomNameExist 8 "" ".dex" "
/system/framework
").dex"
#JAR_EDCONFIG="$(getRandomNameExist 8 "" ".jar" "
#/system/framework
#").jar"
LIB_RIRU_EDXP="libriru_${RIRU_EDXP}.so"
LIB_WHALE_EDXP="lib$(getRandomNameExist 10 "lib" ".so" "
/system/lib
/system/lib64
").so"
LIB_SANDHOOK_EDXP="lib$(getRandomNameExist 13 "lib" ".so" "
/system/lib
/system/lib64
").so"

MODEL="
HD1900
HD1910
"
DEVICE="
OnePlus7T
OnePlus7TPro
"
PRODUCT="
OnePlus7T
OnePlus7TPro
"
BRAND="
HUAWEI
HONOR
"
MANUFACTURER="
HUAWEI
"

OLD_MAGISK=false
DETECTED_DEVICE=false
#NO_PERSIST=false
[[ "$(getenforce)" == "Enforcing" ]] && ENFORCE=true || ENFORCE=false

abortC() {
  rm -rf "${MODPATH}"
  abort "$1"
}

require_new_magisk() {
#    if [[ "${NO_PERSIST}" == true ]]; then
#        ui_print "******************************"
#        ui_print "! Special device detected"
#        ui_print "! But persist is not found in your device, SEPolicy rules will not take effect correctly"
#        ui_print "! Deprecated custom Magisk v20.1 is required"
#        ui_print "! Change Magisk update channel to http://edxp.meowcat.org/repo/version.json"
#        ui_print "! And re-install Magisk"
#        abortC   "******************************"
#    else
        ui_print "******************************"
        ui_print "! Special device detected"
        ui_print "! Magisk v20.2+ or custom Magisk v20.1(Deprecated) is required"
        ui_print "! You can update from 'Magisk Manager' or https://github.com/topjohnwu/Magisk/releases"
        abortC   "******************************"
#    fi
}

update_new_magisk() {
    ui_print "******************************"
    ui_print "- Deprecated custom Magisk v20.1 detected"
    ui_print "- We will still keep the rule file for you"
    ui_print "- You can update to the latest Magisk directly from official update channel"
    ui_print "******************************"
}

require_yahfa() {
    ui_print "******************************"
    ui_print "! Architecture x86 or x86_64 detected"
    ui_print "! Only YAHFA variant supports x86 or x86_64 architecture devices"
    ui_print "! You can download from 'Magisk Manager' or 'EdXposed Manager'"
    abortC   "******************************"
}

require_new_android() {
    ui_print "******************************"
    ui_print "! Old Android ${1} (below Oreo) detected"
    ui_print "! Only the original Xposed Framework can be used under Android 8.0"
    ui_print "! You can download from 'Xposed Installer' or 'Magisk Manager(Systemless-ly)'"
    ui_print "! Learn more: https://github.com/ElderDrivers/EdXposed/wiki/Available-Android-versions"
    abortC   "******************************"
}

check_old_magisk_device() {
    OLD_MAGISK=true
    ui_print "******************************"
    ui_print "- Old Magisk ${1} (below v20.2) detected"
    ui_print "- The old Magisk may cause some problems (it may be fixed in new version)"
    ui_print "- And support may be cancelled in subsequent versions"
    ui_print "- In any case, you should update to the latest version in time"
    ui_print "******************************"
    if [[ "${DETECTED_DEVICE}" == true ]]; then
        require_new_magisk
    fi
}

check_magisk_version() {
    for TARGET in ${MODEL}; do
        if [[ "${PROP_MODEL}" == "${TARGET}" ]]; then
            DETECTED_DEVICE=true
        fi
    done
    for TARGET in ${DEVICE}; do
        if [[ "${PROP_DEVICE}" == "${TARGET}" ]]; then
            DETECTED_DEVICE=true
        fi
    done
    for TARGET in ${PRODUCT}; do
        if [[ "${PROP_PRODUCT}" == "${TARGET}" ]]; then
            DETECTED_DEVICE=true
        fi
    done
    for TARGET in ${BRAND}; do
        if [[ "${PROP_BRAND}" == "${TARGET}" ]]; then
            DETECTED_DEVICE=true
        fi
    done
    for TARGET in ${MANUFACTURER}; do
        if [[ "${PROP_MANUFACTURER}" == "${TARGET}" ]]; then
            DETECTED_DEVICE=true
        fi
    done
    if [[ "${DETECTED_DEVICE}" == true ]]; then
        ui_print "- Special device detected"
    fi
    ui_print "- Magisk version: ${MAGISK_VER_CODE}"
    [[ ${MAGISK_VER_CODE} -ge 20101 ]] || check_old_magisk_device "${MAGISK_VER_CODE}"
    [[ ${MAGISK_VER_CODE} -eq 20101 ]] && update_new_magisk
}

edxp_check_architecture() {
    if [[ "${MODID}" == "riru_edxposed_sandhook" ]]; then
        VARIANTS="SandHook"
    else
        VARIANTS="YAHFA"
    fi
    ui_print "- EdXposed Variant: ${VARIANTS}"
    if [[ "${ARCH}" != "arm" && "${ARCH}" != "arm64" && "${ARCH}" != "x86" && "${ARCH}" != "x64" ]]; then
        abortC "! Unsupported platform: ${ARCH}"
    else
        ui_print "- Device platform: ${ARCH}"
        if [[ "${ARCH}" == "x86" || "${ARCH}" == "x64" ]]; then
            if [[ "${VARIANTS}" == "SandHook" ]]; then
                require_yahfa
            fi
        fi
    fi
}

check_android_version() {
    if [[ ${API} -ge 26 ]]; then
        ui_print "- Android sdk: ${API}"
    else
        require_new_android "${API}"
    fi
}

#check_persist() {
#    if [[ "$(cat /proc/mounts | grep /sbin/.magisk/mirror/persist)" == "" ]]; then
#        NO_PERSIST=true
#    fi
#}

# extract riru.sh
unzip -o "$ZIPFILE" riru.sh -d "$MODPATH" >&2
. $MODPATH/riru.sh

ui_print "- EdXposed Version ${VERSION}"

#check_persist
check_android_version
check_magisk_version
check_riru_version
edxp_check_architecture

ui_print "- Extracting module files"
unzip -o "${ZIPFILE}" EdXposed.apk module.prop post-fs-data.sh sepolicy.rule system.prop uninstall.sh 'system/*' -d "${MODPATH}" >&2

if [[ "${ARCH}" == "x86" || "${ARCH}" == "x64" ]]; then
    ui_print "- Replacing x86 and x86_64 libraries"
    unzip -o "${ZIPFILE}" 'system_x86/*' -d "${MODPATH}" >&2
    rm -rf "${MODPATH}/system/lib"
    rm -rf "${MODPATH}/system/lib64"
    mv "${MODPATH}/system_x86/lib" "${MODPATH}/system/lib"
    mv "${MODPATH}/system_x86/lib64" "${MODPATH}/system/lib64"
    rm -rf "${MODPATH}/system_x86"
fi

if [[ "${IS64BIT}" == false ]]; then
    ui_print "- Removing 64-bit libraries"
    rm -rf "${MODPATH}/system/lib64"
fi

if [[ "$(pm path org.meowcat.edxposed.manager)" == "" && "$(pm path de.robv.android.xposed.installer)" == "" ]]; then
    NO_MANAGER=true
fi

if [[ ${BOOTMODE} == true && ${NO_MANAGER} == true ]]; then
    ui_print "- Installing stub apk"
    ${ENFORCE} && setenforce 0
    (pm install "${MODPATH}/EdXposed.apk" >/dev/null 2>&2) || ui_print "  - Stub install failed! Do not forget install EdXposed Manager manually"
    ${ENFORCE} && setenforce 1
fi

if [[ "${OLD_MAGISK}" == true ]]; then
    ui_print "- Removing SEPolicy rule for old Magisk"
    rm "${MODPATH}"/sepolicy.rule
fi

#echo "- Mounted persist:" >&2
#mount | grep persist >&2

#if [[ "${NO_PERSIST}" == true ]]; then
#    ui_print "- Persist not detected, remove SEPolicy rule"
#    rm ${MODPATH}/sepolicy.rule
#fi

ui_print "- Copying framework libraries"

mv "${MODPATH}/system/framework/eddalvikdx.dex" "${MODPATH}/system/framework/${JAR_EDDALVIKDX}"
mv "${MODPATH}/system/framework/edxp.dex" "${MODPATH}/system/framework/${JAR_EDXP}"
mv "${MODPATH}/system/framework/eddexmaker.dex" "${MODPATH}/system/framework/${JAR_EDDEXMAKER}"
#mv "${MODPATH}/system/framework/edconfig.jar" "${MODPATH}/system/framework/${JAR_EDCONFIG}"
mv "${MODPATH}/system/lib/libriru_edxp.so" "${MODPATH}/system/lib/${LIB_RIRU_EDXP}"
#mv "${MODPATH}/system/lib/libwhale.edxp.so" "${MODPATH}/system/lib/${LIB_WHALE_EDXP}"
mv "${MODPATH}/system/lib/libsandhook-native.so" "${MODPATH}/system/lib/libsandhook-native.so"

if [[ "${IS64BIT}" == true ]]; then
    mv "${MODPATH}/system/lib64/libriru_edxp.so" "${MODPATH}/system/lib64/${LIB_RIRU_EDXP}"
    #mv "${MODPATH}/system/lib64/libwhale.edxp.so" "${MODPATH}/system/lib64/${LIB_WHALE_EDXP}"
    mv "${MODPATH}/system/lib64/libsandhook-native.so" "${MODPATH}/system/lib64/libsandhook-native.so"
fi

if [[ "${VARIANTS}" == "SandHook" ]]; then
    mv "${MODPATH}/system/lib/libsandhook.edxp.so" "${MODPATH}/system/lib/${LIB_SANDHOOK_EDXP}"
    if [[ "${IS64BIT}" == true ]]; then
        mv "${MODPATH}/system/lib64/libsandhook.edxp.so" "${MODPATH}/system/lib64/${LIB_SANDHOOK_EDXP}"
    fi
fi

ui_print "- Resetting libraries path"

sed -i 's:/system/framework/edxp.dex\:/system/framework/eddalvikdx.dex\:/system/framework/eddexmaker.dex:/system/framework/'"${JAR_EDXP}"'\:/system/framework/'"${JAR_EDDALVIKDX}"'\:/system/framework/'"${JAR_EDDEXMAKER}"':g' "${MODPATH}/system/lib/${LIB_RIRU_EDXP}"
#sed -i 's:/system/framework/edconfig.jar:/system/framework/'"${JAR_EDCONFIG}"':g' "${MODPATH}/system/lib/${LIB_RIRU_EDXP}"
sed -i 's:libriru_edxp.so:'"${LIB_RIRU_EDXP}"':g' "${MODPATH}/system/lib/${LIB_RIRU_EDXP}"
#sed -i 's:libwhale.edxp.so:'"${LIB_WHALE_EDXP}"':g' "${MODPATH}/system/lib/${LIB_RIRU_EDXP}"
sed -i 's:libsandhook.edxp.so:'"${LIB_SANDHOOK_EDXP}"':g' "${MODPATH}/system/lib/${LIB_RIRU_EDXP}"

if [[ "${IS64BIT}" == true ]]; then
    sed -i 's:/system/framework/edxp.dex\:/system/framework/eddalvikdx.dex\:/system/framework/eddexmaker.dex:/system/framework/'"${JAR_EDXP}"'\:/system/framework/'"${JAR_EDDALVIKDX}"'\:/system/framework/'"${JAR_EDDEXMAKER}"':g' "${MODPATH}/system/lib64/${LIB_RIRU_EDXP}"
#    sed -i 's:/system/framework/edconfig.jar:/system/framework/'"${JAR_EDCONFIG}"':g' "${MODPATH}/system/lib64/${LIB_RIRU_EDXP}"
    sed -i 's:libriru_edxp.so:'"${LIB_RIRU_EDXP}"':g' "${MODPATH}/system/lib64/${LIB_RIRU_EDXP}"
    #sed -i 's:libwhale.edxp.so:'"${LIB_WHALE_EDXP}"':g' "${MODPATH}/system/lib64/${LIB_RIRU_EDXP}"
    sed -i 's:libsandhook.edxp.so:'"${LIB_SANDHOOK_EDXP}"':g' "${MODPATH}/system/lib64/${LIB_RIRU_EDXP}"
fi

ui_print "- Removing old configuration"

if [[ -f "${RIRU_MODULES}/edxp.prop" ]]; then
    OLD_CONFIG=$(cat "${RIRU_MODULES}/edxp.prop")
    rm -rf "${RIRU_MODULES}/${OLD_CONFIG}"
fi

if [[ -e "${RIRU_MODULES}/edxp" ]]; then
    rm -rf "${RIRU_MODULES}/edxp"
fi

# extract Riru files
ui_print "- Extracting Riru files"
ui_print $RIRU_TARGET
[ -d "$RIRU_TARGET" ] || mkdir -p "$RIRU_TARGET" || abort "! Can't create $RIRU_TARGET"

rm -f "$RIRU_TARGET/module.prop.new"
unzip -o "$ZIPFILE" 'riru/module.prop.new' -d "$RIRU_TARGET"  >&2
mv "$RIRU_TARGET/riru/module.prop.new" "$RIRU_TARGET/module.prop"
rm -rf "$RIRU_TARGET/riru/"
set_perm "$RIRU_TARGET/module.prop" 0 0 0600 $RIRU_SECONTEXT

ui_print "- Copying extra files"

[[ -d "${RIRU_TARGET}" ]] || mkdir -p "${RIRU_TARGET}" || abort "! Can't mkdir -p ${RIRU_TARGET}"

echo "${RIRU_EDXP}">"${RIRU_MODULES}/edxp.prop"

rm "${RIRU_TARGET}/module.prop"

cp "${MODPATH}/module.prop" "${RIRU_TARGET}/module.prop" || abort "! Can't create ${RIRU_TARGET}/module.prop"

set_perm_recursive "${MODPATH}" 0 0 0755 0644

ui_print "- Welcome to EdXposed ${VERSION}!"
