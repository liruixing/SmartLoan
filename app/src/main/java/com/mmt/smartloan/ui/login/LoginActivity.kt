package com.mmt.smartloan.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import butterknife.BindView
import com.lrx.module_base.base.BaseMVPActivity
import com.lrx.module_base.manager.AppManagerUtil
import com.mmt.smartloan.R
import com.mmt.smartloan.http.APIStore
import com.mmt.smartloan.http.bean.response.VerCode
import com.mmt.smartloan.ui.web.WebActivity
import com.mmt.smartloan.utils.TextUtil
import com.mmt.smartloan.utils.ToastUtils
import com.mmt.smartloan.view.PhoneFormatEditText
import com.siberiadante.titlelayoutlib.utils.ScreenUtil

/**
 * create by Dennis
 * on 2022/4/11
 * description：
 **/
class LoginActivity:BaseMVPActivity<ILoginView,LoginPresenter>(),ILoginView {
    @JvmField
    @BindView(R.id.im_back)
    var im_back: ImageView? = null
    @JvmField
    @BindView(R.id.tv_login)
    var tv_login: TextView? = null
    @JvmField
    @BindView(R.id.tv_precode)
    var tv_precode: TextView? = null
    @JvmField
    @BindView(R.id.cet_phone)
    var et_phone: PhoneFormatEditText? = null
    @JvmField
    @BindView(R.id.btn_login)
    var btn_login: RelativeLayout? = null
    @JvmField
    @BindView(R.id.cb_privacy)
    var cb_privacy: CheckBox? = null
    @JvmField
    @BindView(R.id.tv_privacy)
    var tv_privacy: TextView? = null



    companion object{
        const val MAX_PHONE_LEN = 10
        fun start(context:Context){
            val intent = Intent(context,LoginActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun createPresenter(): LoginPresenter {
        return LoginPresenter()
    }

    override fun getContentViewResId(): Int {
        return R.layout.activity_login
    }

    @SuppressLint("ServiceCast")
    override fun init() {
        super.init()
        ScreenUtil.setStatusTranslucent(this)
        cb_privacy?.isChecked = true

        val sp = TextUtil.setLoginCorlor(getString(R.string.login_privacy_hint),
                getString(R.string.login_privacy1_hint),
                getString(R.string.login_privacy2_hint))
        tv_privacy?.setMovementMethod(LinkMovementMethod.getInstance())
        val content = getString(R.string.login_privacy_hint)
        val s1 = getString(R.string.login_privacy1_hint)
        val s2 = getString(R.string.login_privacy2_hint)
        val index1 = content.indexOf(s1)
        val index2 = index1+s1.length
        val index3 = content.indexOf(s2)
        val index4 = index3+s2.length

        sp.setSpan(object : ClickableSpan(){
            override fun onClick(widget: View) {
                WebActivity.start(this@LoginActivity,APIStore.CONDITION_URL)
            }
        },index1,index2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        sp.setSpan(object : ClickableSpan(){
            override fun onClick(widget: View) {
                WebActivity.start(this@LoginActivity,APIStore.PROVICY_URL)
            }
        },index3,index4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_privacy?.setText(sp)

        im_back?.setOnClickListener {
            AppManagerUtil.getInstance().finishAllActivity()
        }

        btn_login?.setOnClickListener{
            val isAgree = cb_privacy?.isChecked?:false
            val isEmpty = et_phone?.text.isNullOrBlank()
            val isTen = et_phone?.getPhone()?.length!! >9

            if(isEmpty || !isTen){
                ToastUtils.showToast(R.string.login_empty_phone_toast)
                return@setOnClickListener
            }

            if(!isTen){
                ToastUtils.showToast(R.string.login_empty_phone_notright)
                return@setOnClickListener
            }
            if(!isAgree){
                ToastUtils.showToast(R.string.login_agree_privacy_toast)
                return@setOnClickListener
            }
            mPresenter.existsByMobile(et_phone?.text.toString())
        }

        et_phone?.postDelayed({
            et_phone?.requestFocus()
            val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.showSoftInput(et_phone, 0)
        }, 300)
    }

    override fun gotoRegister(
        existed: Boolean,
        bean: VerCode
    ) {
        super.gotoRegister(existed,bean)
        RegisterActivity.start(this@LoginActivity, et_phone?.text.toString()?:"",existed,bean)
    }

}