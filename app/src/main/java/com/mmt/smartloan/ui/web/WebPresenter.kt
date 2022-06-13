package com.mmt.smartloan.ui.web

import android.app.Activity
import android.content.Context
import android.webkit.WebView
import com.lrx.module_base.base.BasePresenter
import com.lrx.module_base.utils.SPUtils
import com.mmt.smartloan.BuildConfig
import com.mmt.smartloan.config.AccountInfo
import com.mmt.smartloan.http.APIManager
import java.lang.Exception

/**
 * create by Dennis
 * on 2022/4/11
 * description：
 **/
class WebPresenter: BasePresenter<IWebView>() {

    fun gotoLifeCheck(activity: Activity){

    }

    fun checkupdate(){
        APIManager.getInstance().getNewVersion(BuildConfig.APPLICATION_ID,"webactivity")
            .subscribe(
                {
                    try {
                        val forcedUpdate = it.isForcedUpdate
                        val versionC:Int = it.versionCode.toInt()
                        val cv = BuildConfig.VERSION_CODE
                        if(cv<versionC){//需要更新
                            mView.showUpdateDialog(it,forcedUpdate)
                        }
                        val oldH5Code:Int = SPUtils.get(mView.activity,AccountInfo.H5VC_KEY,-1) as Int
                        if(oldH5Code>=0 && oldH5Code<it.h5VersionCode){
                            mView.cleanCacheAndReload()
                        }
                        SPUtils.put(mView.activity,AccountInfo.H5VC_KEY,it.h5VersionCode)
                    }catch (e:Exception){

                    }

                },{

                },{
                }
            )
    }

    fun clearCache(context:Context,webView:WebView){
//        context.deleteDatabase("webviewCache.db");
//        context.deleteDatabase("webview.db");
        webView.clearCache(true);
        webView.clearHistory();
        webView.clearFormData();
    }
}