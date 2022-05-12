package com.mmt.smartloan.ui.login

import com.lrx.module_base.base.IBaseMVPView
import com.mmt.smartloan.http.bean.response.RegisterInfo
import com.mmt.smartloan.http.bean.response.VerCode

/**
 * create by Dennis
 * on 2022/4/11
 * description：
 **/
interface ILoginView:IBaseMVPView {
    fun gotoRegister(existed: Boolean, bean: VerCode) {}
    fun loginRegisterSuccess(it: RegisterInfo?) {}
}