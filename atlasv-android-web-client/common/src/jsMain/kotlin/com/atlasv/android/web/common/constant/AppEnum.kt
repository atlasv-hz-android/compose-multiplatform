package com.atlasv.android.web.common.constant

/**
 * Created by weiping on 2025/2/19
 */
enum class AppEnum(val packageName: String, val firebaseProjectId: String) {
    Ins3(packageName = "instagram.video.downloader.story.saver.ig", firebaseProjectId = "etm-ins"),
    MediaSaver(packageName = "instagram.video.downloader.story.saver.ig.insaver", firebaseProjectId = ""),
    Ttd1(packageName = "tiktok.video.downloader.nowatermark.tiktokdownload", firebaseProjectId = ""),
    Ttd2(packageName = "tiktok.video.downloader.nowatermark.tiktokdownload.snaptik", firebaseProjectId = ""),
    Fbd2(packageName = "facebook.video.downloader.savefrom.fb.saver.fast", firebaseProjectId = ""),
    Twd(packageName = "twittervideodownloader.twitter.videoindir.savegif.twdown", firebaseProjectId = ""),
    Ptd(packageName = "pin.pinterest.video.downloader.forpinterest.pinsaver", firebaseProjectId = ""),
    ShotCut(packageName = "video.editor.videomaker.effects.fx", firebaseProjectId = ""),
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