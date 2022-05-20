package com.mmt.smartloan.ui.web

import ai.advance.liveness.sdk.activity.LivenessActivity
import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.ContactsContract
import android.util.Log
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.appsflyer.AppsFlyerLib
import com.getmessage.rawdatasdk.RawDataSDK
import com.google.gson.Gson
import com.lrx.module_base.base.BaseActivity
import com.lrx.module_base.utils.SPUtils
import com.mmt.smartloan.BuildConfig
import com.mmt.smartloan.MyApplication
import com.mmt.smartloan.R
import com.mmt.smartloan.config.AccountInfo
import com.mmt.smartloan.http.APIManager
import com.mmt.smartloan.http.bean.JSBean
import com.mmt.smartloan.http.bean.request.EventLogItem
import com.mmt.smartloan.http.bean.request.EventLogRequest
import com.mmt.smartloan.ui.login.LoginActivity
import com.mmt.smartloan.utils.*
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject


/**
 * create by Dennis
 * on 2022/4/18
 * description：
 **/
class WebViewInjector(var webView: WebView, val context: BaseActivity, val mRawDataSDK: RawDataSDK, val rxPermissions: RxPermissions) {
    private var currentCallBack: String? = null
    var isSelectContact: Boolean = false
    var selectContactIndex: Int = 0

    var timeId = ""
    var faceId = ""
    var contactId = ""

    var needToEvent:Boolean = false



    fun getLoginInfo(bean: JSBean) {
        val token = SPUtils.get(context, AccountInfo.TOKEN_KEY, "")
        val value = JSONObject()
        value.put("token", token)
        sendMessage(value.toString(), bean.callback, bean.id)
    }

    fun getPackageName(bean: JSBean) {
        val value = JSONObject()
        value.put("packageName", "com.mmt.smartloan")
        value.put("androidId", ConfigUtil.getAndroidID(MyApplication.Companion.getAppContext()))
        value.put("imei", ConfigUtil.getIMEI(MyApplication.Companion.getAppContext()))
        value.put("afid", AppsFlyerLib.getInstance().getAppsFlyerUID(MyApplication.Companion.getAppContext()))
        value.put("appVersion", ConfigUtil.getAppVersion(MyApplication.Companion.getAppContext()))
        value.put("appName", "SmartLoan")

        sendMessage(value.toString(), bean.callback, bean.id)
    }

    fun getVersionName(bean: JSBean) {
        val versionName = BuildConfig.VERSION_NAME
        val value = JSONObject()
        value.put("versionName", versionName)
        sendMessage(value.toString(), bean.callback, bean.id)
    }

    fun toLogin(bean: JSBean) {
        logout(bean)
        val token = SPUtils.get(context, AccountInfo.TOKEN_KEY, "")
        val value = JSONObject()
        value.put("token", token)
        sendMessage(value.toString(), bean.callback, bean.id)
    }

    fun logout(bean: JSBean) {
        SPUtils.put(context, AccountInfo.TOKEN_KEY, "")
        SPUtils.put(context, AccountInfo.PHONE_KEY, "")
        SPUtils.put(context, AccountInfo.USERID_KEY, "")
        LoginActivity.start(context)
        if (context is Activity)
            context.finish()
    }

    fun logEventByLocal(bean: JSBean) {
        val data = bean.data

        var item = EventLogItem()
        item.eventOption = data.eventOption
        item.time = data.time
        item.eventType = data.eventType
        item.pageName = data.pageName
        item.orderNo = data.orderNo

        AccountInfo.logList.add(item)

        if (data.isUpload) {
            MyApplication.getAppContext()?.let { AccountInfo.uploadLog(it) }
        }
    }

    fun logEventByAF(bean: JSBean) {
        val data = bean.data
        AFUtil.up(context, data.event)
    }

    fun timeSDK(bean: JSBean,js: String) {
//        调起第三方资料收集六合一
        mRawDataSDK.setJsonString(js)
        Log.d("TimeSDK","JS 触发timesdk")
    }

    fun selectContact(bean: JSBean) {
//        先检查通讯录权限，有权限就去选择联系人，没权限就请求权限，在权限回调里：允许了就去选择联系人，拒绝了不做任何操作
        val data = bean.data
        contactId = bean.id
        if(!PermissionUtils.hasPermission(context,PermissionUtils.READ_CONTACTS)){
            needToEvent = true
        }
        rxPermissions.requestEach(PermissionUtils.READ_CONTACTS)
                .subscribe {
                    if (it.granted) {
                        if(needToEvent){
                            addEvent("click","contact_yes")
                            AFUtil.up(context, "author_contact_yes")
                        }
                        isSelectContact = data.isSelectContact
                        selectContactIndex = data.selectContactIndex
                        var intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
                        context.startActivityForResult(intent, WebActivity.REQUEST_CODE_CONTACT)
                    }else if(it.shouldShowRequestPermissionRationale){
                        if(needToEvent){
                            addEvent("click","contact_no")
                            AFUtil.up(context, "author_contact_no")
                        }
                    }else{
                        if(needToEvent){
                            addEvent("click","contact_completeNo")
                        }
                        AFUtil.up(context, "toast_author_"+context.resources.getString(R.string.permission_complete_no_toast))
                        ToastUtils.showToast(context.resources.getString(R.string.permission_complete_no_toast))
                    }
                }
    }


    fun getAccuauthSDK(bean: JSBean) {
//        调起前先检查拍照权限，有权限就调起sdk，没权限就去请求权限，在权限回调里：允许了就调起sdk，拒绝了不做操作
        val data = bean.data
        faceId = bean.id
        if(!PermissionUtils.hasPermission(context,PermissionUtils.CAMERA)){
            needToEvent = true
        }
        rxPermissions.requestEach(PermissionUtils.CAMERA)
                .subscribe {
                    if (it.granted) {//请求权限
                        if(needToEvent){
                            addEvent("click","camera_yes")
                            AFUtil.up(context, "author_media_yes")
                        }
                        val intent = Intent(context, LivenessActivity::class.java)
                        context.startActivityForResult(intent, WebActivity.REQUEST_CODE_LIVENESS)
                    }else if(it.shouldShowRequestPermissionRationale){
                        if(needToEvent){
                            addEvent("click","camera_no")
                            AFUtil.up(context, "author_media_no")
                        }
                    }else{
                        if(needToEvent){
                            addEvent("click","camera_completeNo")
                            AFUtil.up(context, "author_media_no")
                        }
                        AFUtil.up(context, "toast_author_"+context.resources.getString(R.string.permission_complete_no_toast))
                        ToastUtils.showToast(context.resources.getString(R.string.permission_complete_no_toast))
                    }
                }
    }


    fun setNewToken(bean: JSBean) {
//        先检查通讯录权限，有权限就去选择联系人，没权限就请求权限，在权限回调里：允许了就去选择联系人，拒绝了不做任何操作
        val data = bean.data
        SPUtils.put(context, AccountInfo.TOKEN_KEY, data.token)
        SPUtils.put(context, AccountInfo.PHONE_KEY, data.phoneNumber)
        APIManager.getInstance().updateToken(data.token)
    }

    fun ToWhatsapp(bean: JSBean) {
        val data = bean.data
        val phone = data.phone
        chatInWhatsApp(context, phone)
    }

    private fun chatInWhatsApp(mContext: Context, mobileNum: String) {
        try {
            val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://api.whatsapp.com/send?phone=$mobileNum")
            )
            intent.setPackage("com.whatsapp")
            mContext.startActivity(intent)
        } catch (e: Exception) { //  没有安装WhatsApp
            e.printStackTrace()
        }
    }

    fun toGooglePlayer(bean: JSBean) {
        //跳到谷歌市场 toGooglePlayer
        val data = bean.data
        val packageId = data.packageId
        gotoGooglePlay(context, packageId)
    }

    private fun gotoGooglePlay(ctx: Context, packageId: String) {
        // 做跳转到谷歌play做好评的业务逻辑
        //这里开始执行一个应用市场跳转逻辑，默认this为Context上下文对象
        var intent: Intent = Intent(Intent.ACTION_VIEW)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.data = Uri.parse("market://details?id=" + packageId) //跳转到应用市场，非Google Play市场一般情况也实现了这个接口
        //存在手机里没安装应用市场的情况，跳转会包异常，做一个接收判断
        if (intent.resolveActivity(ctx.packageManager) != null) { //可以接收
            ctx.startActivity(intent)
        } else { //没有应用市场，我们通过浏览器跳转到Google Play
            intent.data = Uri.parse("https://play.google.com/store/apps/details?id=" + packageId);
            //这里存在一个极端情况就是有些用户浏览器也没有，再判断一次
            if (intent.resolveActivity(ctx.packageManager) == null) { //有浏览器
                ctx.startActivity(intent)
            } else { //天哪，这还是智能手机吗？
                ToastUtils.showToast(ctx.resources.getString(R.string.uninstall_google_market))
            }
        }
    }

    fun changeLanguage(bean: JSBean) {
//        修改语言 changeLanguage (暂不使用)
        val json = bean.data
    }

    @JavascriptInterface
    fun postMessage(js: String) {
        Log.d("WEB_JS", js)
        webView.post {
            dealJSData(js)
        }
    }

    private fun dealJSData(js: String) {
        var bean = Gson().fromJson<JSBean>(js, JSBean::class.java)
        when (bean.action) {
            "getLoginInfo" -> {
                getLoginInfo(bean)
            }
            "getPackageName" -> {
                getPackageName(bean)
            }
            "getVersionName" -> {
                getVersionName(bean)
            }
            "toLogin" -> {
                toLogin(bean)
            }
            "logout" -> {
                logout(bean)
            }
            "logEventByLocal" -> {
                logEventByLocal(bean)
            }
            "timeSDK" -> {
                timeSDK(bean,js)
            }
            "logEventByAF" -> {
                logEventByAF(bean)
            }
            "selectContact" -> {
                selectContact(bean)
            }
            "getAccuauthSDK" -> {
                getAccuauthSDK(bean)
            }
            "setNewToken" -> {

                setNewToken(bean)
            }
            "ToWhatsapp" -> {

                ToWhatsapp(bean)
            }
            "toGooglePlayer" -> {

                toGooglePlayer(bean)
            }
            "changeLanguage" -> {
                changeLanguage(bean)
            }
        }
    }


    fun sendMessage(params: String, callback: String, id: String, isOK: Boolean? = true) {
        webView?.post {
            var result = "ok"
            if (!isOK!!) {
                result = "fail"
            }
            var data = "{\"data\":" +
                    params +
                    ",\"action\":" + "\"" +
                    callback + "\"" +
                    ",\"id\":" + "\"" +
                    id + "\"" +
                    ",\"result\":" +
                    "\"" + result + "\"" +
                    "}"
            val loadUrl = "javascript:" + callback + "(" + data + ")"
            webView.loadUrl(loadUrl)
            Log.d("WEB_JS", loadUrl)
        }
    }



    private fun addEvent(type:String,option:String){
        Log.d("logevent","type:"+type+"    option:"+option)
        EventUtils.addEvent("author",type,option)
    }

}