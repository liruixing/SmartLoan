package com.mmt.smartloan.ui.login

import com.lrx.module_base.base.BasePresenter
import com.mmt.smartloan.http.APIManager
import com.mmt.smartloan.utils.ToastUtils

/**
 * create by Dennis
 * on 2022/4/11
 * description：
 **/
class LoginPresenter:BasePresenter<ILoginView>() {


    fun existsByMobile(phone: String) {

        val str = phone.replace(" ","")
        showLoading()
        APIManager.getInstance().existsByMobile(str)
            .subscribe(
                {
                    mView.gotoRegister(it.isExisted)
                },
                {
                    ToastUtils.showToast(it.message)
                    hideLoading()
                },
                {
                    hideLoading()
                }
            )

    }
}