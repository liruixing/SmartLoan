package com.mmt.smartloan.ui.login

import com.lrx.module_base.base.BasePresenter
import com.lrx.module_base.manager.AppManagerUtil
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
class RegisterPresenter: BasePresenter<IRegisterView>() {

    fun getCode(mobile:String,type:Int){

        val str = mobile.replace(" ","")

        val request = VerCodeRequest()
        request.mobile = str
        request.type = type
        showLoading()
        APIManager.getInstance().getCode(request,"logincode")
                .subscribe(
                        {
                            mView.getCodeSuccess(it)
                        },
                        {
                            hideLoading()
                            if(!it.message.isNullOrEmpty()){
                                ToastUtils.showToast(mView.activity.resources.getString(R.string.network_error_toast))
                                AFUtil.up(mView.activity, "toast_logincode_"+mView.activity.resources.getString(R.string.network_error_toast))
                            }
                        },
                        {
                            hideLoading()
                        }
                )
    }


    fun submit(phone: String, code: String,isAuto:Boolean,isExisted:Boolean) {
        val str = phone.replace(" ","")
        showLoading()
        if(isExisted){//登录
            AFUtil.up(mView.activity, "logincode_login")
            val request = LoginRequest()
            request.mobile = str
            request.verifyCode = code
            request.verified = !isAuto
            val map:MutableMap<String,Any> = BeanMapUtils.getObjectToMap(request)
            APIManager.getInstance().login(map,"logincode")
                    .subscribe(
                            {
                                AFUtil.up(mView.activity, "logincode_LoginSuccess")
                                mView.registerSuccess(it)
                            },
                            {
                                AFUtil.up(mView.activity, "logincode_Loginfailed")
                                hideLoading()
                                if(!it.message.isNullOrEmpty()){
                                    ToastUtils.showToast(mView.activity.resources.getString(R.string.network_error_toast))
                                    AFUtil.up(mView.activity, "toast_logincode_"+mView.activity.resources.getString(R.string.network_error_toast))
                                }
                            },
                            {
                                hideLoading()
                            }
                    )
        }else{//注册
            AFUtil.up(mView.activity, "logincode_reg")
            val request = RegisterRequest()
            request.isVerified = !isAuto
            request.mobile = str
            request.verifyCode = code
            APIManager.getInstance().register(request,"logincode")
                    .subscribe(
                            {
                                AFUtil.up(mView.activity, "logincode_RegSuccess")
                                mView.registerSuccess(it)
                            },
                            {
                                AFUtil.up(mView.activity, "logincode_Regfailed")
                                hideLoading()
                                if(!it.message.isNullOrEmpty()){
                                    ToastUtils.showToast(mView.activity.resources.getString(R.string.network_error_toast))
                                    AFUtil.up(mView.activity, "toast_logincode_"+mView.activity.resources.getString(R.string.network_error_toast))
                                }
                            },
                            {
                                hideLoading()
                            }
                    )
        }

    }
}