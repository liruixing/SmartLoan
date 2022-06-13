package com.mmt.smartloan.ui.login

import com.lrx.module_base.base.BasePresenter
import com.mmt.smartloan.R
import com.mmt.smartloan.http.APIManager
import com.mmt.smartloan.http.bean.request.LoginRequest
import com.mmt.smartloan.http.bean.request.RegisterRequest
import com.mmt.smartloan.http.bean.request.VerCodeRequest
import com.mmt.smartloan.utils.AFUtil
import com.mmt.smartloan.utils.BeanMapUtils
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
        APIManager.getInstance().existsByMobile(str,"loginphone")
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
                return@flatMap APIManager.getInstance().getCode(request,"loginphone")
            }
            .subscribe(
                {
                    if(it.isEnableAutoLogin){
                        submit(str,it.code,true,isExisted)
                    }else{
                        mView.gotoRegister(isExisted,it)
                    }
                },
                {
                    if(!it.message.isNullOrEmpty()){
                        ToastUtils.showToast(mView.activity.resources.getString(R.string.network_error_toast))
                        AFUtil.up(mView.activity, "toast_loginphone_"+mView.activity.resources.getString(R.string.network_error_toast))
                    }
                    hideLoading()
                },
                {
                }
            )

    }



    fun submit(phone: String, code: String,isAuto:Boolean,isExisted:Boolean) {
        val str = phone.replace(" ","")
        AFUtil.up(mView.activity, "login_automatic")
        if(isExisted){//登录
            AFUtil.up(mView.activity, "loginPhone_login")
            val request = LoginRequest()
            request.mobile = str
            request.verifyCode = code
            request.verified = !isAuto
            val map:MutableMap<String,Any> = BeanMapUtils.getObjectToMap(request)
            APIManager.getInstance().login(map,"loginphone")
                .subscribe(
                    {
                        AFUtil.up(mView.activity, "login_automatic_success")
                        mView.loginRegisterSuccess(it)
                    },
                    {
                        if(!it.message.isNullOrEmpty()){
                            ToastUtils.showToast(mView.activity.resources.getString(R.string.network_error_toast))
                            AFUtil.up(mView.activity, "toast_loginphone_"+mView.activity.resources.getString(R.string.network_error_toast))
                        }
                        AFUtil.up(mView.activity, "login_automatic_fail")
                        hideLoading()
                    },
                    {
                        hideLoading()
                    }
                )
        }else{//注册
            AFUtil.up(mView.activity, "loginPhone_reg")
            val request = RegisterRequest()
            request.isVerified = !isAuto
            request.mobile = str
            request.verifyCode = code
            APIManager.getInstance().register(request,"loginphone")
                .subscribe(
                    {
                        mView.loginRegisterSuccess(it)
                    },
                    {
                        hideLoading()
                        if(!it.message.isNullOrEmpty()){
                            ToastUtils.showToast(mView.activity.resources.getString(R.string.network_error_toast))
                            AFUtil.up(mView.activity, "toast_loginphone_"+mView.activity.resources.getString(R.string.network_error_toast))
                        }
                    },
                    {
                        hideLoading()
                    }
                )
        }

    }
}