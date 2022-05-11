package com.mmt.smartloan.ui.login

import com.lrx.module_base.base.BasePresenter
import com.mmt.smartloan.http.APIManager
import com.mmt.smartloan.http.bean.request.VerCodeRequest
import com.mmt.smartloan.utils.ToastUtils

/**
 * create by Dennis
 * on 2022/4/11
 * description：
 **/
class LoginPresenter:BasePresenter<ILoginView>() {


    fun existsByMobile(phone: String) {

        val str = phone.replace(" ","")
        var isExisted = false
        showLoading()
        APIManager.getInstance().existsByMobile(str)
            .flatMap {
                isExisted = it.isExisted
                val str = phone.replace(" ","")
                val type =
                    if(isExisted){
                        1
                    }else{
                        2
                    }
                val request = VerCodeRequest()
                request.mobile = str
                request.type = type
                return@flatMap APIManager.getInstance().getCode(request)
            }
            .subscribe(
                {
                    mView.gotoRegister(isExisted,it)
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