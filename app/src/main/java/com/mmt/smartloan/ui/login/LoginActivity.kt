package com.mmt.smartloan.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.text.Spannable
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import butterknife.BindView
import com.lrx.module_base.base.BaseMVPActivity
import com.lrx.module_base.manager.AppManagerUtil
import com.lrx.module_base.utils.SPUtils
import com.mmt.smartloan.MyApplication
import com.mmt.smartloan.R
import com.mmt.smartloan.config.AccountInfo
import com.mmt.smartloan.http.APIManager
import com.mmt.smartloan.http.APIStore
import com.mmt.smartloan.http.bean.response.RegisterInfo
import com.mmt.smartloan.http.bean.response.VerCode
import com.mmt.smartloan.ui.web.WebActivity
import com.mmt.smartloan.utils.*
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
        const val IS_OPEN_KEY = "isopen"
        fun start(context:Context,isOpen:Boolean = true){
            val intent = Intent(context,LoginActivity::class.java)
            intent.putExtra(IS_OPEN_KEY,isOpen)
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
                if(FastDoubleClickUtils.isFastDoubleClick(widget))return
                addEvent("click","termOfService")
                AFUtil.up(this@LoginActivity, "loginPhone_termOfService")
                WebActivity.start(this@LoginActivity,APIStore.CONDITION_URL)
            }
        },index1,index2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        sp.setSpan(object : ClickableSpan(){
            override fun onClick(widget: View) {
                if(FastDoubleClickUtils.isFastDoubleClick(widget))return
                addEvent("click","privacyPolicy")
                AFUtil.up(this@LoginActivity, "loginPhone_privacy")
                WebActivity.start(this@LoginActivity,APIStore.PROVICY_URL)
            }
        },index3,index4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_privacy?.setText(sp)

        cb_privacy?.setOnCheckedChangeListener { compoundButton, b ->
            addEvent("click","agreement")
        }

        im_back?.setOnClickListener {
            if(FastDoubleClickUtils.isFastDoubleClick(it))return@setOnClickListener
            AppManagerUtil.getInstance().finishAllActivity()
        }

        btn_login?.setOnClickListener{
            if(FastDoubleClickUtils.isFastDoubleClick(it))return@setOnClickListener
            val isAgree = cb_privacy?.isChecked?:false
            val isEmpty = et_phone?.text.isNullOrBlank()
            val isTen = et_phone?.getPhone()?.length!! >9

            if(isEmpty){
                ToastUtils.showToast(R.string.login_empty_phone_toast)
                AFUtil.up(this@LoginActivity, "toast_loginphone_"+resources.getString(R.string.login_empty_phone_toast))
                return@setOnClickListener
            }

            if(!isTen){
                ToastUtils.showToast(R.string.login_empty_phone_notright)
                AFUtil.up(this@LoginActivity, "toast_loginphone_"+resources.getString(R.string.login_empty_phone_notright))
                return@setOnClickListener
            }
            if(!isAgree){
                ToastUtils.showToast(R.string.login_agree_privacy_toast)
                AFUtil.up(this@LoginActivity, "toast_loginphone_"+resources.getString(R.string.login_agree_privacy_toast))
                return@setOnClickListener
            }

            et_phone?.nextFocusUpId

            addEvent("leave","phoneNum")
            addEvent("click","next")
            AFUtil.up(this@LoginActivity, "loginPhone_sendOtp")
            AFUtil.up(this@LoginActivity, "loginPhone_next")
            mPresenter.existsByMobile(et_phone?.text.toString())
        }

        et_phone?.setOnFocusChangeListener { view, b ->
            if(b){
                addEvent("input","phoneNum")
                AFUtil.up(this@LoginActivity, "loginPhone_phoneInput")
            }else{
                addEvent("leave","phoneNum")
            }
        }

        val isopen = intent.extras?.getBoolean(IS_OPEN_KEY)?:true

        addEvent("open","")
        AFUtil.up(this, "loginPhone_open")
    }

    override fun onResume() {
        super.onResume()
        et_phone?.postDelayed({
            et_phone?.requestFocus()
            val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.showSoftInput(et_phone, 0)
        }, 300)
    }

    override fun onPause() {
        super.onPause()
        addEvent("exit","")
        AFUtil.up(this, "loginPhone_back")
        MyApplication.getAppContext()?.let { AccountInfo.uploadLog(it) }
    }
    override fun gotoRegister(
        existed: Boolean,
        bean: VerCode
    ) {
        super.gotoRegister(existed,bean)
        RegisterActivity.start(this@LoginActivity, et_phone?.text.toString()?:"",existed,bean)
    }

    override fun loginRegisterSuccess(registerInfo: RegisterInfo?) {
        super.loginRegisterSuccess(registerInfo)
        SPUtils.put(this, AccountInfo.TOKEN_KEY,registerInfo?.token?:"")
        SPUtils.put(this, AccountInfo.USERID_KEY,registerInfo?.userId?:"")
        SPUtils.put(this, AccountInfo.PHONE_KEY,et_phone?.getPhone())
        APIManager.getInstance().updateToken(registerInfo?.token?:"")
        WebActivity.start(this)
        AppManagerUtil.getInstance().finishAllButNot(WebActivity::class.java)
    }

    private fun addEvent(type:String,option:String){
        Log.d("logevent","type:"+type+"    option:"+option)
        EventUtils.addEvent("loginPhone-手机号页面",type,option)
    }

}