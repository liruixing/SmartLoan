package com.mmt.smartloan.ui.web

import com.lrx.module_base.base.IBaseMVPView
import com.mmt.smartloan.http.bean.response.VersionInfo

/**
 * create by Dennis
 * on 2022/4/11
 * description：
 **/
interface IWebView : IBaseMVPView {
    fun gotoLifeCheck(){}
    fun showUpdateDialog(
        vi: VersionInfo,
        forcedUpdate: Boolean
    ) {}

    fun cleanCacheAndReload() {}
}