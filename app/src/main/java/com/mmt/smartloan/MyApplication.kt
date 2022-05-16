package com.mmt.smartloan

import ai.advance.liveness.lib.GuardianLivenessDetectionSDK
import ai.advance.liveness.lib.Market
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.appsflyer.AFInAppEventType
import com.appsflyer.AppsFlyerConversionListener
import com.appsflyer.AppsFlyerLib
import com.lrx.module_base.utils.SPUtils
import com.mmt.smartloan.config.AccountInfo
import com.mmt.smartloan.http.APIManager
import com.mmt.smartloan.http.bean.request.AddActiveRequest
import com.mmt.smartloan.http.bean.request.EventLogRequest
import com.mmt.smartloan.service.LogInfoService
import com.mmt.smartloan.utils.GoogleReferrerHelper
import com.mmt.smartloan.utils.LocationUtils
import com.mmt.smartloan.utils.TextUtil
import com.st.network.http.config.RxNetwork
import com.st.network.http.config.RxNetworkConfig
import com.tencent.bugly.crashreport.CrashReport
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * create by Dennis
 * on 2022/4/16
 * description：
 **/
class MyApplication : Application() {
    val TAG:String ="haha"
    companion object{
        private var mHandler: Handler? = null
        private var mContext: Context? = null

        fun getAppHandler(): Handler? {
            if (mHandler == null) mHandler = Handler()
            return mHandler
        }

        fun getAppContext(): Context? {
            return mContext
        }
    }

    private var mLifecycleCallback:ActivityLifecycleCallbacks = object :ActivityLifecycleCallbacks{
        override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
            LocationUtils.getLocation(this@MyApplication)
        }

        override fun onActivityStopped(activity: Activity?) {
        }

        override fun onActivityPaused(activity: Activity?) {
        }

        override fun onActivityResumed(activity: Activity?) {
        }

        override fun onActivityStarted(activity: Activity?) {
        }

        override fun onActivityDestroyed(activity: Activity?) {
        }

        override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}


    }



    override fun onCreate() {
        super.onCreate()
        mHandler = Handler()
        mContext = this

        val rxNetworkConfig = RxNetworkConfig.Builder()
            .setConnectTimeOut(10) // 连接超时，单位秒
            .setReadTimeOut(10) // 读超时，单位秒
            .setWriteTimeOut(30) // 写超时，单位秒
            .setCache(false) // 开启OkHttp缓存
            .setEnableSSLSocket(false) // 使用https认证
            .build()

        RxNetwork.init(this, true, rxNetworkConfig) //实例化网络加载库
        initSDKS()

        //监听生命周期
        registerActivityLifecycleCallbacks(mLifecycleCallback)

        mHandler?.postDelayed({
            GoogleReferrerHelper.getIns().addActive()
        },3*1000)
    }

    private fun initSDKS() {

        val conversionListener: AppsFlyerConversionListener = object : AppsFlyerConversionListener {
            override fun onConversionDataSuccess(conversionData: Map<String, Any>) {
                for (attrName in conversionData.keys) {
                    Log.e(TAG,"attribute: " + attrName + " = " + conversionData[attrName])
                }
            }

            override fun onConversionDataFail(errorMessage: String) {
                Log.e(TAG,"error getting conversion data: $errorMessage")
            }

            override fun onAppOpenAttribution(conversionData: Map<String, String>) {
                for (attrName in conversionData.keys) {
                    Log.e(TAG,"attribute: " + attrName + " = " + conversionData[attrName])
                }
            }

            override fun onAttributionFailure(errorMessage: String) {
                Log.e(TAG,"error onAttributionFailure : $errorMessage")
            }
        }
        AppsFlyerLib.getInstance()
            .init(AccountInfo.AppsFlyerId, conversionListener, applicationContext)
        AppsFlyerLib.getInstance().start(this)

        GuardianLivenessDetectionSDK.init(this,AccountInfo.accessKey, AccountInfo.secretKey,Market.Mexico);

        val accessKey = "54e03a28ec301bb8"
        val secretKey = "36181f76c174e848"
        val market = Market.Mexico
        GuardianLivenessDetectionSDK.init(this, accessKey, secretKey, market)
        CrashReport.initCrashReport(getApplicationContext(), "14f0e6cfb1", false);
//        //开启线程收集日志
//        val intent = Intent(this,LogInfoService::class.java)
//        startService(intent)

    }



}