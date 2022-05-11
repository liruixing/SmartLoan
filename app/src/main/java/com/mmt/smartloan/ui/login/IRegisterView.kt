package com.mmt.smartloan.ui.login

import com.lrx.module_base.base.IBaseMVPView
import com.mmt.smartloan.http.bean.response.RegisterInfo
import com.mmt.smartloan.http.bean.response.VerCode

/**
 * create by Dennis
 * on 2022/4/11
 * description：
 **/
interface IRegisterView:IBaseMVPView {
    fun getCodeSuccess(bean: VerCode) {}
    fun registerSuccess(registerInfo: RegisterInfo?) {}
}