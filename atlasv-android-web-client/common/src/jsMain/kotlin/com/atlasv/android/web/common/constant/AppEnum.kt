package com.atlasv.android.web.common.constant

/**
 * Created by weiping on 2025/2/19
 */
enum class AppEnum(val packageName: String, val firebaseProjectId: String) {
    Ins3(packageName = "instagram.video.downloader.story.saver.ig", firebaseProjectId = "etm-ins"),
    MediaSaver(packageName = "instagram.video.downloader.story.saver.ig.insaver", firebaseProjectId = ""),
    Ttd1(
        packageName = "tiktok.video.downloader.nowatermark.tiktokdownload",
        firebaseProjectId = "tiktokdownloader-9eb12"
    ),
    Ttd2(
        packageName = "tiktok.video.downloader.nowatermark.tiktokdownload.snaptik",
        firebaseProjectId = "tiktokdownloader-9eb12"
    ),
    Fbd2(packageName = "facebook.video.downloader.savefrom.fb.saver.fast", firebaseProjectId = "fastget-fb-downloader"),
    Twd(packageName = "twittervideodownloader.twitter.videoindir.savegif.twdown", firebaseProjectId = ""),
    Ptd(packageName = "pin.pinterest.video.downloader.forpinterest.pinsaver", firebaseProjectId = ""),
    ShotCut(packageName = "video.editor.videomaker.effects.fx", firebaseProjectId = "fx-editor"),
}

fun AppEnum.getFirebaseStorageUrl(): String {
    return "https://console.firebase.google.com/u/0/project/${firebaseProjectId}/storage"
}

fun AppEnum.getAppLifecycleStorageUrl(): String {
    return "${getFirebaseStorageUrl()}/app-lifecycle-storage-${firebaseProjectId}"
}

/**
 * https://console.firebase.google.com/u/0/project/etm-ins/storage/app-lifecycle-storage-etm-ins/files/~2Finstagram.video.downloader.story.saver.ig~2Fatlasv_log_report
 */
fun AppEnum.getXLogStorageBaseUrl(): String {
    return "${getAppLifecycleStorageUrl()}/files/~2F${packageName}~2Fatlasv_log_report"
}

fun AppEnum.getProductApiUrlV2(): String {
    return "https://iap.etm.tech/v2/products?app_package_name=${packageName}&app_platform=GooglePlay"
}