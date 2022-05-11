package com.mmt.smartloan.ui.login

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.text.Editable
import android.text.Spannable
import android.text.TextWatcher
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import butterknife.BindView
import com.lrx.module_base.base.BaseMVPActivity
import com.lrx.module_base.manager.AppManagerUtil
import com.lrx.module_base.utils.SPUtils
import com.mmt.smartloan.R
import com.mmt.smartloan.config.AccountInfo
import com.mmt.smartloan.http.APIManager
import com.mmt.smartloan.http.APIStore
import com.mmt.smartloan.http.bean.response.RegisterInfo
import com.mmt.smartloan.http.bean.response.VerCode
import com.mmt.smartloan.ui.web.WebActivity
import com.mmt.smartloan.utils.TextUtil
import com.mmt.smartloan.utils.ToastUtils
import com.siberiadante.titlelayoutlib.utils.ScreenUtil


/**
 * create by Dennis
 * on 2022/4/11
 * description：
 **/
class RegisterActivity:BaseMVPActivity<IRegisterView,RegisterPresenter>(),IRegisterView {

    @JvmField
    @BindView(R.id.im_back)
    var im_back: ImageView? = null
    @JvmField
    @BindView(R.id.tv_login)
    var tv_login: TextView? = null
    @JvmField
    @BindView(R.id.im_clear)
    var im_clear: ImageView? = null
    @JvmField
    @BindView(R.id.et_code)
    var et_code: EditText? = null
    @JvmField
    @BindView(R.id.tv_getCode)
    var tv_getCode: TextView? = null
    @JvmField
    @BindView(R.id.btn_login)
    var btn_login: RelativeLayout? = null
    @JvmField
    @BindView(R.id.cb_privacy)
    var cb_privacy: CheckBox? = null
    @JvmField
    @BindView(R.id.tv_privacy)
    var tv_privacy: TextView? = null

    private var phone:String? = null
    private var isExisted:Boolean = false


    companion object{
        const val PHONE_KEY = "phone_key"
        const val IS_EXISTED = "is_existed"

        fun start(context: Context, phone:String,existed: Boolean){
            val intent = Intent(context,RegisterActivity::class.java)
            intent.putExtra(PHONE_KEY,phone)
            intent.putExtra(IS_EXISTED,existed)
            context.startActivity(intent)
        }

    }


    override fun createPresenter(): RegisterPresenter {
        return RegisterPresenter()
    }

    override fun getContentViewResId(): Int {
        return R.layout.activity_register
    }

    override fun init() {
        super.init()

        phone = intent.getStringExtra(PHONE_KEY)?:""
        isExisted = intent.getBooleanExtra(IS_EXISTED,false)


        ScreenUtil.setStatusTranslucent(this)
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
                WebActivity.start(this@RegisterActivity, APIStore.PROVICY_URL)
            }
        },index1,index2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        sp.setSpan(object : ClickableSpan(){
            override fun onClick(widget: View) {
                WebActivity.start(this@RegisterActivity, APIStore.CONDITION_URL)
            }
        },index3,index4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_privacy?.setText(sp)

        cb_privacy?.isChecked = true
        cb_privacy?.isEnabled = false

        im_back?.setOnClickListener {
            finish()
        }
        im_clear?.setOnClickListener { et_code?.setText("") }
        et_code?.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if(et_code?.text.isNullOrBlank()){
                    im_clear?.visibility = View.GONE
                }else{
                    im_clear?.visibility = View.VISIBLE
                }
                var input = et_code?.text.toString()

                if (s!!.length > 6) {
                    s.delete(LoginActivity.MAX_PHONE_LEN, s.length)
                }
                if(!TextUtil.isVercode(input) && input.length > 0){
                    val str = input.subSequence(0,input.length-1)
                    et_code?.setText(str)
                    et_code?.setSelection(str.length)
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        tv_getCode?.setOnClickListener{
            val type =
            if(isExisted){
                1
            }else{
                2
            }
            mPresenter.getCode(phone!!,type)
        }

        btn_login?.setOnClickListener{
            val isAgree = cb_privacy?.isChecked?:false
            val isEmpty = et_code?.text.isNullOrBlank()

            val isSix = et_code?.text?.length!! >5
            if(isEmpty || !isSix){
                ToastUtils.showToast(R.string.login_empty_phone_toast)
                return@setOnClickListener
            }
            if(!isAgree){
                ToastUtils.showToast(R.string.login_agree_privacy_toast)
                return@setOnClickListener
            }

            mPresenter.submit(phone!!,et_code?.text.toString(),false,isExisted)
        }

        tv_getCode?.performClick()

    }

    override fun getCodeSuccess(bean: VerCode) {
        super.getCodeSuccess(bean)
        //开启倒计时
        if(bean.isEnableAutoLogin){
            //自动登录
            et_code?.setText(bean.code)
            startCountDown()
            mPresenter.submit(phone!!,et_code?.text.toString(),true,isExisted)
        }else{
            startCountDown()
            et_code?.postDelayed({
                et_code?.requestFocus()
                val manager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.showSoftInput(et_code, 0)
            }, 100)
        }
    }

    override fun registerSuccess(registerInfo: RegisterInfo?) {
        super.registerSuccess(registerInfo)
        SPUtils.put(this,AccountInfo.TOKEN_KEY,registerInfo?.token?:"")
        SPUtils.put(this,AccountInfo.USERID_KEY,registerInfo?.userId?:"")
        SPUtils.put(this,AccountInfo.PHONE_KEY,phone)
        APIManager.getInstance().updateToken(registerInfo?.token?:"")
        WebActivity.start(this)
        AppManagerUtil.getInstance().finishAllButNot(WebActivity::class.java)
    }

    private fun startCountDown() {
        val timer: CountDownTimer = object : CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished:Long) {
                val str:String = (millisUntilFinished/1000).toString()+"s"
                tv_getCode?.text = str
            }

            override fun onFinish() {
                tv_getCode?.text = getString(R.string.register_get_code)
            }
        }
        timer.start()
    }
}