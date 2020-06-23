#!/system/bin/sh

grep_prop() {
    local REGEX="s/^$1=//p"
    shift
    local FILES=$@
    [[ -z "$FILES" ]] && FILES='/system/build.prop'
    sed -n "$REGEX" ${FILES} 2>/dev/null | head -n 1
}

MODDIR=${0%/*}

RIRU_PATH="/data/misc/riru"
TARGET="${RIRU_PATH}/modules"
[[ "$(getenforce)" == "Enforcing" ]] && ENFORCE=true || ENFORCE=false

EDXP_VERSION=$(grep_prop version "${MODDIR}/module.prop")

ANDROID_SDK=$(getprop ro.build.version.sdk)
BUILD_DESC=$(getprop ro.build.description)
PRODUCT=$(getprop ro.build.product)
MODEL=$(getprop ro.product.model)
MANUFACTURER=$(getprop ro.product.manufacturer)
BRAND=$(getprop ro.product.brand)
FINGERPRINT=$(getprop ro.build.fingerprint)
ARCH=$(getprop ro.product.cpu.abi)
DEVICE=$(getprop ro.product.device)
ANDROID=$(getprop ro.build.version.release)
BUILD=$(getprop ro.build.id)

RIRU_VERSION=$(cat "${RIRU_PATH}/version_name")
RIRU_VERCODE=$(cat "${RIRU_PATH}/version_code")
RIRU_APICODE=$(cat "${RIRU_PATH}/api_version")

MAGISK_VERSION=$(su -v)
MAGISK_VERCODE=$(su -V)

EDXP_MANAGER="org.meowcat.edxposed.manager"
XP_INSTALLER="de.robv.android.xposed.installer"
PATH_PREFIX="/data/user_de/0/"
#PATH_PREFIX_LEGACY="/data/user/0/"

sepolicy() {
    # necessary for using mmap in system_server process
    # read configs set in our app
    # for built-in apps // TODO: maybe narrow down the target classes
    # read module apk file in zygote
    # TODO: remove coredomain sepolicy
    supolicy --live "allow system_server system_server process { execmem }"\
                    "allow system_server system_server memprotect { mmap_zero }"\
                    "allow coredomain coredomain process { execmem }"\
                    "allow coredomain app_data_file * *"\
                    "attradd { system_app platform_app } mlstrustedsubject"\
                    "allow zygote apk_data_file * *"
}

#if [[ ${ANDROID_SDK} -ge 24 ]]; then
#    PATH_PREFIX="${PATH_PREFIX_PROT}"
#else
#    PATH_PREFIX="${PATH_PREFIX_LEGACY}"
#fi

DEFAULT_BASE_PATH="${PATH_PREFIX}${EDXP_MANAGER}"
BASE_PATH="${DEFAULT_BASE_PATH}"

if [[ ! -d ${BASE_PATH} ]]; then
    BASE_PATH="${PATH_PREFIX}${XP_INSTALLER}"
    if [[ ! -d ${BASE_PATH} ]]; then
        BASE_PATH="${DEFAULT_BASE_PATH}"
    fi
fi

LOG_PATH="${BASE_PATH}/log"
CONF_PATH="${BASE_PATH}/conf"
DISABLE_VERBOSE_LOG_FILE="${CONF_PATH}/disable_verbose_log"
LOG_VERBOSE=true
OLD_PATH=${PATH}
PATH=${PATH#*:}
PATH_INFO=$(ls -ldZ "${BASE_PATH}")
PATH=${OLD_PATH}
PATH_OWNER=$(echo "${PATH_INFO}" | awk -F " " '{print $3":"$4}')
PATH_CONTEXT=$(echo "${PATH_INFO}" | awk -F " " '{print $5}')

if [[ -f ${DISABLE_VERBOSE_LOG_FILE} ]]; then
    LOG_VERBOSE=false
fi

# If logcat client is kicked out by klogd server, we'll restart it.
# However, if it is killed manually or by EdXposed Manager, we'll exit.
# Refer to https://github.com/ElderDrivers/EdXposed/pull/575 for more information.
loop_logcat() {
    while true
    do
        logcat $*
        if [[ $? -ne 1 ]]; then
            break
        fi
    done
}

start_log_cather () {
    LOG_FILE_NAME=$1
    LOG_TAG_FILTERS=$2
    CLEAN_OLD=$3
    START_NEW=$4
    LOG_FILE="${LOG_PATH}/${LOG_FILE_NAME}.log"
    PID_FILE="${LOG_PATH}/${LOG_FILE_NAME}.pid"
    mkdir -p ${LOG_PATH}
    if [[ ${CLEAN_OLD} == true ]]; then
        rm "${LOG_FILE}.old"
        mv "${LOG_FILE}" "${LOG_FILE}.old"
    fi
    rm "${LOG_PATH}/${LOG_FILE_NAME}.pid"
    if [[ ${START_NEW} == false ]]; then
        return
    fi
    touch ${LOG_FILE}
    touch ${PID_FILE}
    echo "--------- beginning of head">>${LOG_FILE}
    echo "EdXposed Log">>${LOG_FILE}
    echo "Powered by Log Catcher">>${LOG_FILE}
    echo "QQ support group: 855219808">>${LOG_FILE}
    echo "Telegram support group: @Code_Of_MeowCat">>${LOG_FILE}
    echo "Telegram channel: @EdXposed">>${LOG_FILE}
    echo "--------- beginning of information">>${LOG_FILE}
    echo "Manufacturer: ${MANUFACTURER}">>${LOG_FILE}
    echo "Brand: ${BRAND}">>${LOG_FILE}
    echo "Device: ${DEVICE}">>${LOG_FILE}
    echo "Product: ${PRODUCT}">>${LOG_FILE}
    echo "Model: ${MODEL}">>${LOG_FILE}
    echo "Fingerprint: ${FINGERPRINT}">>${LOG_FILE}
    echo "ROM description: ${BUILD_DESC}">>${LOG_FILE}
    echo "Architecture: ${ARCH}">>${LOG_FILE}
    echo "Android build: ${BUILD}">>${LOG_FILE}
    echo "Android version: ${ANDROID}">>${LOG_FILE}
    echo "Android sdk: ${ANDROID_SDK}">>${LOG_FILE}
    echo "EdXposed version: ${EDXP_VERSION}">>${LOG_FILE}
    echo "EdXposed api: 91.0">>${LOG_FILE}
    echo "Riru version: ${RIRU_VERSION} (${RIRU_VERCODE})">>${LOG_FILE}
    echo "Riru api: ${RIRU_APICODE}">>${LOG_FILE}
    echo "Magisk: ${MAGISK_VERSION%:*} (${MAGISK_VERCODE})">>${LOG_FILE}
    loop_logcat -f ${LOG_FILE} *:S ${LOG_TAG_FILTERS} &
    LOG_PID=$!
    echo "${LOG_PID}">"${LOG_PATH}/${LOG_FILE_NAME}.pid"
}

# Backup app_process to avoid bootloop caused by original Xposed replacement in Android Oreo
# TODO: Magisk mount replace
rm -rf "${MODDIR}/system/bin"
mkdir "${MODDIR}/system/bin"
cp -f "/system/bin/app_process32" "${MODDIR}/system/bin/app_process32"
[[ -f "/system/bin/app_process64" ]] && cp -f "/system/bin/app_process64" "${MODDIR}/system/bin/app_process64"

# install stub if manager not installed
if [[ "$(pm path org.meowcat.edxposed.manager)" == "" && "$(pm path de.robv.android.xposed.installer)" == "" ]]; then
    NO_MANAGER=true
fi
if [[ ${NO_MANAGER} == true ]]; then
    ${ENFORCE} && setenforce 0
    pm install "${MODDIR}/EdXposed.apk"
    ${ENFORCE} && setenforce 1
fi

# execute live patch if rule not found
[[ -f "${MODDIR}/sepolicy.rule" ]] || sepolicy

# start_verbose_log_catcher
start_log_cather all "EdXposed:V XSharedPreferences:V EdXposed-Bridge:V EdXposedManager:V XposedInstaller:V" true ${LOG_VERBOSE}

# start_bridge_log_catcher
start_log_cather error "XSharedPreferences:V EdXposed-Bridge:V" true true


if [[ -f "/data/misc/riru/modules/edxp.prop" ]]; then
    CONFIG=$(cat "/data/misc/riru/modules/edxp.prop")
    [[ -d "${TARGET}/${CONFIG}" ]] || mkdir -p "${TARGET}/${CONFIG}"
    cp "${MODDIR}/module.prop" "${TARGET}/${CONFIG}/module.prop"
fi

chcon -R u:object_r:system_file:s0 "${MODDIR}"
chcon -R ${PATH_CONTEXT} "${LOG_PATH}"
chown -R ${PATH_OWNER} "${LOG_PATH}"
chmod -R 666 "${LOG_PATH}"
