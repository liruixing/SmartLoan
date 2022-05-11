package com.mmt.smartloan.utils

import android.content.Context
import android.util.Log
import com.appsflyer.AppsFlyerLib
import com.mmt.smartloan.BuildConfig
import com.mmt.smartloan.MyApplication
import com.mmt.smartloan.MyApplication.Companion.getAppContext
import com.mmt.smartloan.config.AccountInfo

/**
 * create by Dennis
 * on 2022/4/21
 * description：
 **/
object AFUtil {

    fun up(context: Context,log: String){
        AppsFlyerLib.getInstance().logEvent(context,log,getmap())
    }

    private fun getmap(): MutableMap<String, Any>? {
        val map:MutableMap<String,Any> = mutableMapOf()
        map.put("packageName",BuildConfig.APPLICATION_ID)
        map.put("androidId",ConfigUtil.getAndroidID(MyApplication.getAppContext()))
        map.put("installReferce", AccountInfo.installReferce)
        map.put("appVersion",ConfigUtil.getAppVersion(MyApplication.getAppContext()))
        return map
    }
}