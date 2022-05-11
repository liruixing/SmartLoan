package com.mmt.smartloan.config

import com.mmt.smartloan.http.bean.JSBean
import com.mmt.smartloan.http.bean.request.EventLogItem

/**
 * create by Dennis
 * on 2022/4/16
 * description：
 **/
object AccountInfo {

    var isCollectLog:Boolean = true
    var isUpload: Boolean = false

    val AppsFlyerId:String = "yFbZbrMQ7eoqbZ4BdAPN"

    var accessKey = "54e03a28ec301bb8"
    var secretKey = "36181f76c174e848"

    var installReferce = ""
    var referrerClickTime:Long = 0L
    var installStartTime:Long = 0L



    const val TOKEN_KEY = "token_key"
    const val PHONE_KEY = "phone_key"
    const val H5VC_KEY = "h5VersionCode"
    const val USERID_KEY = "userid_key"
    const val IS_FIRST_KEY = "is_first"
    var hasUrl = arrayListOf<String>(
            "id_card_authentication",
            "emergency_contacts",
            "personal_infomation",
            "review_tips",
            "add_bankcard",
            "confirm_borrow",
            "additionalInfomation"
    )


    var logList:ArrayList<EventLogItem> = arrayListOf()

    var uploadList:ArrayList<EventLogItem> = arrayListOf()

}