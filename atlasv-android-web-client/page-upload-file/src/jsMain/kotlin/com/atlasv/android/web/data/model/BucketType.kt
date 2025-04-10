package com.atlasv.android.web.data.model

/**
 * Created by weiping on 2025/4/6
 */
enum class BucketType(val displayName: String, val bucketName: String, val regionName: String, val path: String) {
    MarketEvent("运营活动", "richman-media", "sfo3", "public/events"),

    // https://downloader-media.nyc3.cdn.digitaloceanspaces.com/public/js_parse/prod/fbd/fdown_net.js
    JsCode("Js代码", "downloader-media", "nyc3", "public/js_parse"),
    // https://downloader-media.nyc3.cdn.digitaloceanspaces.com/public/questionnaires/KOL_ins3_2025_2_jp.json
    Questionnaire("调查问卷", "downloader-media", "nyc3", "public/questionnaires"),
    Others("其他", "richman-media", "sfo3", "public/events"),
}
