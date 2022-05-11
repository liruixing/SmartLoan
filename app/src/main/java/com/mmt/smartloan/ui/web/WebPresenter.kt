package com.mmt.smartloan.ui.web

import android.app.Activity
import android.content.Context
import android.webkit.WebView
import com.lrx.module_base.base.BasePresenter
import com.mmt.smartloan.BuildConfig
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
        APIManager.getInstance().getNewVersion(BuildConfig.APPLICATION_ID)
            .subscribe(
                {
                    try {
                        val forcedUpdate = it.isForcedUpdate
                        val versionC:Int = it.versionCode.toInt()
                        val cv = BuildConfig.VERSION_CODE
                        if(cv<versionC){//需要更新
                            mView.showUpdateDialog(it,forcedUpdate)
                        }
                    }catch (e:Exception){

                    }

                },{

                },{
                }
            )
    }

    fun clearCache(context:Context,webView:WebView){
        context.deleteDatabase("webviewCache.db");
        context.deleteDatabase("webview.db");
        webView.clearCache(true);
        webView.clearHistory();
        webView.clearFormData();
    }
}