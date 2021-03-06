package com.mmt.smartloan.config

import android.content.Context
import android.util.Log
import com.lrx.module_base.utils.SPUtils
import com.mmt.smartloan.R
import com.mmt.smartloan.http.APIManager
import com.mmt.smartloan.http.bean.JSBean
import com.mmt.smartloan.http.bean.request.EventLogItem
import com.mmt.smartloan.http.bean.request.EventLogRequest
import com.mmt.smartloan.utils.AFUtil
import com.mmt.smartloan.utils.TextUtil
import com.mmt.smartloan.utils.ToastUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

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

    var time_s = 3*1000
    var lastUploadTime = 0L

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

    var uploadList:ArrayList<EventLogItem> = arrayListOf()//复制使用，避免线程安全导致的崩溃


    fun uploadLog(context: Context){
        if(System.currentTimeMillis() - lastUploadTime < time_s)return//3s 内不处理上传
        //复制需要上传的日志信息
        uploadList = TextUtil.deepCopy(logList)
        logList.clear()
        if(!uploadList.isEmpty()){
            lastUploadTime = System.currentTimeMillis()
            val request = EventLogRequest()
            request.userId = SPUtils.get(context,USERID_KEY, "") as String?
            request.phoneNumber = SPUtils.get(context,PHONE_KEY, "") as String?
            request.eventList = uploadList

            APIManager.getInstance().uploadLog(request)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe (
                        {
                            Log.d("TAG","uploadLog success")
                        },
                        {
                            uploadList.clear()
                            it.message?.let { it1 -> Log.d("TAG", it1) }
                        },
                        {
                            uploadList.clear()
                        }
                    )
        }
    }


    val CONTACT_TOAST_KEY:String = "contact_toast_key"
    val CAMERA_TOAST_KEY:String = "camera_toast_key"
    val FILE_TOAST_KEY:String = "file_toast_key"
    val PHONE_TOAST_KEY:String = "phone_toast_key"
    val MESSAGE_TOAST_KEY:String = "message_toast_key"
    val LOCATION_TOAST_KEY:String = "location_toast_key"


    fun showToast(context:Context,key:String){
        val hasShow:Boolean = SPUtils.get(context,key, false) as Boolean
        if(!hasShow){
            SPUtils.put(context,key,true)
            AFUtil.up(context, "toast_author_"+context.resources.getString(R.string.permission_complete_no_toast))
            ToastUtils.showToast(context.resources.getString(R.string.permission_complete_no_toast))
        }
    }


    fun showCameraOrFileToast(context:Context){
        val hasShow1:Boolean = SPUtils.get(context, CAMERA_TOAST_KEY, false) as Boolean
        val hasShow2:Boolean = SPUtils.get(context, FILE_TOAST_KEY, false) as Boolean
        if(!hasShow1 || !hasShow2){
            SPUtils.put(context,CAMERA_TOAST_KEY,true)
            SPUtils.put(context,FILE_TOAST_KEY,true)
            AFUtil.up(context, "toast_author_"+context.resources.getString(R.string.permission_complete_no_toast))
            ToastUtils.showToast(context.resources.getString(R.string.permission_complete_no_toast))
        }
    }
}