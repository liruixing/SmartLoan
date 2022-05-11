package com.mmt.smartloan.ui.splash

import com.lrx.module_base.base.BaseMVPActivity
import com.lrx.module_base.utils.SPUtils
import com.mmt.smartloan.MyApplication
import com.mmt.smartloan.R
import com.mmt.smartloan.config.AccountInfo
import com.mmt.smartloan.ui.login.LoginActivity
import com.mmt.smartloan.ui.web.WebActivity
import com.mmt.smartloan.utils.GoogleReferrerHelper
import com.siberiadante.titlelayoutlib.utils.ScreenUtil

/**
 * create by Dennis
 * on 2022/4/11
 * description：
 **/
class SplashActivity:BaseMVPActivity<ISplashView,SplashPresenter>(), ISplashView {

    override fun createPresenter(): SplashPresenter {
        return SplashPresenter()
    }

    override fun getContentViewResId(): Int {
        return R.layout.activity_splash
    }

    override fun init() {
        super.init()
        MyApplication.getAppHandler()?.postDelayed({
            val token = SPUtils.get(this@SplashActivity,AccountInfo.TOKEN_KEY,"") as String
            if(token.isNullOrBlank()){
                gotoLogin()
            }else{
                WebActivity.start(this@SplashActivity)
                this.finish()
            }
        },1500)
        initGoogleInstall()
    }

    private fun gotoLogin() {
        LoginActivity.start(this@SplashActivity)
        this.finish()
    }


    private fun initGoogleInstall() {
        GoogleReferrerHelper.getIns().start(this)
    }
}