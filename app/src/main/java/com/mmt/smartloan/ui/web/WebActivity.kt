package com.mmt.smartloan.ui.web

import ai.advance.liveness.lib.LivenessResult
import ai.advance.liveness.sdk.activity.LivenessActivity
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.ContactsContract
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.*
import android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
import android.widget.Button
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import butterknife.BindView
import com.getmessage.rawdatasdk.RawDataSDK
import com.getmessage.rawdatasdk.SDKCallback
import com.lrx.module_base.base.BaseMVPActivity
import com.lrx.module_base.manager.AppManagerUtil
import com.lrx.module_base.utils.SPUtils
import com.lrx.module_ui.View.dialog.DialogFactory
import com.mmt.smartloan.MyApplication
import com.mmt.smartloan.R
import com.mmt.smartloan.config.AccountInfo
import com.mmt.smartloan.http.APIManager
import com.mmt.smartloan.http.APIStore
import com.mmt.smartloan.http.bean.Event
import com.mmt.smartloan.http.bean.JSBean
import com.mmt.smartloan.http.bean.response.VersionInfo
import com.mmt.smartloan.ui.login.LoginActivity
import com.mmt.smartloan.utils.*
import com.mmt.smartloan.view.FloatButton
import com.tbruyelle.rxpermissions2.RxPermissions
import org.json.JSONObject
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


/**
 * create by Dennis
 * on 2022/4/11
 * description：
 **/
class WebActivity : BaseMVPActivity<IWebView, WebPresenter>(), IWebView {
    @JvmField
    @BindView(R.id.wv_web)
    var webView: WebView? = null
    @JvmField
    @BindView(R.id.btn_float)
    var btn_float: FloatButton? = null

    @JvmField
    @BindView(R.id.btn_1)
    var btn1: Button? = null

    @JvmField
    @BindView(R.id.btn_2)
    var btn2: Button? = null

    @JvmField
    @BindView(R.id.btn_3)
    var btn3: Button? = null

    var url: String? = null
    var injector: WebViewInjector? = null
    var mRawDataSDK: RawDataSDK? = null

    private var mUploadMessage: ValueCallback<Array<Uri>>? = null
    private var size: Long = 0
    var rxPermission: RxPermissions? = null

    var isFirstTime:Boolean = true

    companion object {
        const val URL_KEY = "url_key"
        const val REQUEST_CODE_LIVENESS = 1000
        const val REQUEST_CODE_CONTACT = 1001
        const val REQUEST_CODE_PICTURE = 1002

        fun start(context: Context, url: String? = null) {
            val intent = Intent(context, WebActivity::class.java)
            url?.let {
                intent.putExtra(URL_KEY, it)
            }
            context.startActivity(intent)
        }

    }

    override fun createPresenter(): WebPresenter {
        return WebPresenter()
    }

    override fun getContentViewResId(): Int {
        return R.layout.activity_web
    }

    private fun initGoogleInstall() {
        GoogleReferrerHelper.getIns().start(this)
    }

    override fun init() {
        super.init()
        initGoogleInstall()
        url = intent.getStringExtra(URL_KEY)
        mRawDataSDK = RawDataSDK(this, object : SDKCallback {
            override fun onSuccess(file: File?, md5: String?, orderno: String?, isSubmit: Boolean, json: String?) {
                up6In1(file, md5, orderno, isSubmit, 0, json);
                Log.d("TimeSDK","onSuccess      file="+file?.absoluteFile+"  orderno="+orderno+"  isSubmit="+isSubmit+"  json="+json)
            }

            override fun onFail(msg: String?, orderno: String?, isSubmit: Boolean, json: String?) {
                Log.d("TimeSDK","onFail  orderno="+orderno+"  isSubmit="+isSubmit+"  json="+json)
                if (isSubmit) {//  判断回调h5
                    toJsResult(false, json, isSubmit);// 原生回调h5
                }
            }
        })
        rxPermission = RxPermissions(this)
        injector = WebViewInjector(webView!!, this, mRawDataSDK!!,rxPermission!!)
        initWebSetting()

        if(APIStore.PROVICY_URL == url || APIStore.CONDITION_URL == url){
            btn_float?.visibility = View.GONE
        }else{
            val token = SPUtils.get(this@WebActivity,AccountInfo.TOKEN_KEY,"") as String
            if(token.isNullOrBlank()){
                LoginActivity.start(this@WebActivity)
                this.finish()
            }else{
                APIManager.getInstance().updateToken(token)
                mPresenter.checkupdate()
            }
            btn_float?.visibility = View.VISIBLE
        }

        btn_float?.setOnClickListener{
            mPresenter.clearCache(this@WebActivity, webView!!)
            webView?.loadUrl(APIStore.H5_URL)
            showLoadingView()
            Log.d("TAG","点击重新打开webview")
        }

        btn1?.setOnClickListener {
            val bean: JSBean = JSBean()
            bean.action = "selectContact"
            bean.id = "0.8459706990489422"
            bean.callback = "getWebViewSelectContact"

            val data = Event()
            data.isSelectContact = true
            data.selectContactIndex = 1

            bean.data = data

            injector?.selectContact(bean)
        }

        btn2?.setOnClickListener {
            goTakePicture()
        }
        btn3?.setOnClickListener {
            val bean: JSBean = JSBean()
            bean.action = "getAccuauthSDK"
            bean.id = "0.798405398239914"
            bean.callback = "webViewFaceImg"
            injector?.getAccuauthSDK(bean)
        }

    }


    private fun up6In1(file: File?, md5: String?, orderno: String?, isSubmit: Boolean, num: Int, json: String?) {
        var n = num + 1
        file?.let {
            APIManager.getInstance().uploadZip6in1(file, "file", md5, orderno,"webactivity")
                    .subscribe(
                            {
                                if (it) {
                                    toJsResult(true, json, isSubmit)
                                } else {
                                    if (isSubmit) {
                                        if (n < 3) {
                                            up6In1(file, md5, orderno, isSubmit, n, json)
                                        } else {
                                            toJsResult(false, json, isSubmit)
                                        }
                                    } else {
                                        toJsResult(false, json, isSubmit)
                                    }
                                }
                            },
                            {
                                if (isSubmit) {
                                    if (n < 3) {
                                        up6In1(file, md5, orderno, isSubmit, n, json)
                                    } else {
                                        toJsResult(false, json, isSubmit)
                                    }
                                } else {
                                    toJsResult(false, json, isSubmit)
                                }
                            }
                    )
        }
    }


    private fun toJsResult(b: Boolean, json: String?, submit: Boolean) {
        if (!submit) return
        var isOK = true
        if (b && submit) {
            isOK = true
        } else {
            isOK = false
        }
        injector?.sendMessage("{}", "webViewToTime", injector?.timeId ?: "", isOK)
    }


    /**
     * 设置webview 1234567890
     */
    @SuppressLint("JavascriptInterface")
    private fun initWebSetting() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) WebView.setWebContentsDebuggingEnabled(
                true
        )
        val webSettings: WebSettings = webView?.getSettings()!!
        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.mixedContentMode = MIXED_CONTENT_ALWAYS_ALLOW
        }
        webSettings.domStorageEnabled = true
        webSettings.javaScriptEnabled = true
        webView?.addJavascriptInterface(injector!!, "FKSDKJsFramework");

        /**
         * 是否支持 viewport 标签或宽视图，
         * fasle时，宽度为css样式设置的像素值
         * true：如果有 viewport 标签，会使用标签中声明的宽，如果没有viewport标签或者未指定具体宽度，
         * 则使用宽视图模式
         * @param use whether to enable support for the viewport meta tag
         */
        webSettings.useWideViewPort = true
        /**
         * getUseWideViewPort为true时，设置此模式为true，网页内容的宽度大于WebView控件的宽度时，
         * 会缩小网页内容以适应webView的宽度
         */
        webSettings.loadWithOverviewMode = true
        /**
         * //是否支持缩放及显示缩放控件，即一个放大缩小的图标
         * @param support whether the WebView should support zoom
         */
        webSettings.setSupportZoom(true)
        /**
         * //是否使用内置缩放机制，包括缩放控件显示以及手势放大缩小，推荐使用
         * @param enabled whether the WebView should use its built-in zoom mechanisms
         */
        webSettings.builtInZoomControls = true
        /**
         * 使用内置缩放机制时是否支显示放控件，加号减号图标的显示，默认显示
         * @param enabled whether the WebView should display on-screen zoom controls
         */
        webSettings.displayZoomControls = false
        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.mixedContentMode = MIXED_CONTENT_ALWAYS_ALLOW
        }
        // WebView是否支持多个窗口。
//        webSettings.setSupportMultipleWindows(true);
        /**
         * 设置初始缩放比例，数字代表的百分比值，效果和WebSettings#getUseWideViewPort()以及WebSettings#getLoadWithOverviewMode()有关
         * 如果内容适应 webview 控件宽度，将不进行缩放，如果大于webview宽度且WebSettings#getLoadWithOverviewMode()为true，
         * 网页内容将会缩小以适应 webview 控件，否则不缩放。
         * 注：此方法不考虑屏幕像素面密度
         */
        webView?.setInitialScale(200)
        webSettings.cacheMode = WebSettings.LOAD_DEFAULT
        //排版适应屏幕
        webSettings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS
        webView?.setWebChromeClient(object : WebChromeClient() {
            override fun onProgressChanged(view: WebView, newProgress: Int) {
//                progressBar.setProgress(newProgress)
//                if (newProgress == 100) progressBar.setVisibility(View.GONE) else progressBar.setVisibility(
//                    View.VISIBLE
//                )
            }

            override fun onReceivedTitle(view: WebView, title: String) {
//                toolbar.setTitle(title)
            }

            override fun onShowFileChooser(webView: WebView?, filePath: ValueCallback<Array<Uri>>?, fileChooserParams: FileChooserParams?): Boolean {
                //调起onShowFileChooser方法后，filePathCallback必须要回调，有数据回调数据，没数据回调null
                //检查权限，调起原生相机，执行拍照，回传的图片尺寸限制 大于768，小于2048，图片大小限制 小于1m
                mUploadMessage = filePath;
                Log.e("FileCooserParams => ", filePath.toString());
                goTakePicture()
                return true
            }
        })
        webView?.setWebViewClient(object : WebViewClient() {
            override fun onPageStarted(
                    view: WebView?,
                    url: String?,
                    favicon: Bitmap?
            ) {
                Log.i("TAG", "onPageStarted: $url")
                if(isFirstTime){
                    showLoadingView()
                }
                super.onPageStarted(view, url, favicon)
            }

            //当网页有重定向的时候会走，如百度，淘宝等，有重定向执行顺序,
            //onPageStarted -> shouldOverrideUrlLoading
            //-> onPageFinished(重定向前的地址) -> onPageStarted -> onPageFinished(重定向后)
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Log.i("TAG", "shouldOverrideUrlLoading: $url")
                view?.loadUrl(url!!)
                return true
            }

            override fun onPageFinished(webView: WebView?, s: String?) {
                Log.i("TAG", "onPageFinished: $s")
                heidenLoadingView()
                if(isFirstTime){
                    isFirstTime = false
                }
                super.onPageFinished(webView, s)
            }
        })
        webView?.setLayerType(View.LAYER_TYPE_HARDWARE, null)
        webView?.loadUrl(url ?: APIStore.H5_URL)
    }

    private fun goTakePicture() {
        //检查权限，调起原生相机，执行拍照，回传的图片尺寸限制 大于768，小于2048，图片大小限制 小于1m
        val hasfile = PermissionUtils.hasPermission(this, PermissionUtils.WRITE_EXTERNAL_STORAGE)
        val hasCamera = PermissionUtils.hasPermission(this, PermissionUtils.CAMERA)

        rxPermission!!.requestEachCombined(*PermissionUtils.STORAGE_AND_CAMERA)
                .subscribe {
                    if (it.granted) {
                        //权限通过
                        val intent = createCameraIntent()
                        startActivityForResult(intent, REQUEST_CODE_PICTURE)
                        if(!hasfile){
                            addEvent("click","file_yes")
                            AFUtil.up(this@WebActivity, "author_file_yes")
                        }
                        if(!hasCamera){
                            addEvent("click","camera_yes")
                            AFUtil.up(this@WebActivity, "author_media_yes")
                        }
                    } else if (it.shouldShowRequestPermissionRationale) {
//                        用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                        mUploadMessage?.onReceiveValue(null);
                        if(!hasfile){
                            addEvent("click","file_no")
                            AFUtil.up(this@WebActivity, "author_file_no")
                        }
                        if(!hasCamera){
                            addEvent("click","camera_no")
                            AFUtil.up(this@WebActivity, "author_media_no")
                        }
                    } else {
                        //用户选中了  不再询问
                        mUploadMessage?.onReceiveValue(null);

                        if(!hasfile){
                            addEvent("click","file_completeNo")
                            AFUtil.up(this@WebActivity, "author_file_no")
                        }
                        if(!hasCamera){
                            addEvent("click","camera_completeNo")
                            AFUtil.up(this@WebActivity, "author_media_no")
                        }
                        AccountInfo.showCameraOrFileToast(this@WebActivity)
                    }
                }

    }

    override fun gotoLifeCheck() {
        super.gotoLifeCheck()

        val intent = Intent(this@WebActivity, LivenessActivity::class.java)
        activity.startActivityForResult(
                intent,
                REQUEST_CODE_LIVENESS
        )
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //人脸检测
        if (LivenessResult.isSuccess() && requestCode == REQUEST_CODE_LIVENESS) {
            val bitmap = LivenessResult.getLivenessBitmap()
            val result = BitmapUtils.decodeBitmap(bitmap, 2048, 2048, 1 * 1024 * 1024 * 1000)
            val base64 = BitmapUtils.bitmapToBase64(result)
            var value = JSONObject()
            value.put("livenessId", LivenessResult.getLivenessId())
            value.put("file", base64)
            injector?.sendMessage(value.toString(), "webViewFaceImg", injector?.faceId ?: "")
        } else if (requestCode == REQUEST_CODE_LIVENESS && !LivenessResult.isSuccess()) {
            var value = JSONObject()
            value.put("errorMsg", LivenessResult.getErrorMsg())
            injector?.sendMessage(value.toString(), "webViewFaceImg", injector?.faceId ?: "", false)
        }
        //选择联系人
        if (requestCode == REQUEST_CODE_CONTACT && resultCode == Activity.RESULT_OK) {
            val contactUri = data?.data
            var number = ""
            var name = ""
            contactUri?.let {
                this.contentResolver.query(contactUri, null, null, null, null).use { cursor ->
                    if (cursor?.moveToFirst() == true) {
                        val numberIndex =
                                cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

                        if (numberIndex != -1) {
                            number = cursor.getString(numberIndex)
                        }

                        val nameIndex =
                                cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                        if (nameIndex != -1) {
                            name = cursor.getString(nameIndex)
                        }
                    }
                }
            }

            var value = JSONObject()
            value.put("isSelectContact", injector?.isSelectContact)
            value.put("selectContactIndex", injector?.selectContactIndex)
            value.put("name", name)
            value.put("phone", number)

            injector?.sendMessage(value.toString(), "getWebViewSelectContact", injector?.contactId
                    ?: "")
        }
        //选择图片
        if (requestCode == REQUEST_CODE_PICTURE && resultCode == Activity.RESULT_OK) {
            if (mUploadMessage == null || mCameraFilePath.isNullOrBlank()) {
                mUploadMessage?.onReceiveValue(null)
                return
            }
            try {
                var imgUri:Uri? = data?.getData()?:null
                if(imgUri == null && !mCameraFilePath.isNullOrEmpty()){
                    val file = File(mCameraFilePath)
                    size = file.length();//文件大小
                    if (file.exists() && size > 1 * 1024 * 1024) {//大于1M 需要压缩
                        mCameraFilePath = BitmapUtils.decodeFile(file, 2048, 2048, 1 * 1024 * 1024 * 1024)
                    }
                    val uri = Uri.fromFile(File(mCameraFilePath));
                    mUploadMessage?.onReceiveValue(arrayOf(uri));
                    mUploadMessage = null;
                }else if(imgUri != null){
                    mCameraFilePath = getRealFilePath(this,imgUri)
                    val file = File(mCameraFilePath)
                    size = file.length();//文件大小
                    if (file.exists() && size > 1 * 1024 * 1024) {//大于1M 需要压缩
                        mCameraFilePath = BitmapUtils.decodeFile(file, 2048, 2048, 1 * 1024 * 1024 * 1024)
                    }
                    val uri = Uri.fromFile(File(mCameraFilePath));
                    mUploadMessage?.onReceiveValue(arrayOf(uri));
                    mUploadMessage = null;
                }

            } catch (e: Exception) {
                Log.e("Error!", "Error while opening image file" + e.getLocalizedMessage());
            }

        }else if(requestCode == REQUEST_CODE_PICTURE && resultCode != Activity.RESULT_OK){
            mUploadMessage?.onReceiveValue(null)
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == RawDataSDK.RequestCode && mRawDataSDK != null) {
            mRawDataSDK?.onRequestPermission();
        }

        permissions.forEachIndexed { index, s ->
            if(PermissionUtils.READ_PHONE_STATE == s && grantResults.size > index){
                val grant = grantResults[index]
                if(grant ==  PackageManager.PERMISSION_GRANTED){//权限通过
                    addEvent("click","phone_yes")
                    AFUtil.up(this@WebActivity, "author_phone_yes")
                }else if(!ActivityCompat.shouldShowRequestPermissionRationale(this,PermissionUtils.READ_PHONE_STATE)
                    && ActivityCompat.checkSelfPermission(this,PermissionUtils.READ_PHONE_STATE)==PackageManager.PERMISSION_DENIED
                ){//权限拒绝并不再询问
                    addEvent("click","phone_completeNo")
                    AFUtil.up(this@WebActivity, "author_phone_no")
                    AccountInfo.showToast(this@WebActivity,AccountInfo.PHONE_TOAST_KEY)
                }else{
                    addEvent("click","phone_no")
                    AFUtil.up(this@WebActivity, "author_phone_no")
                }
            }

            if(PermissionUtils.ACCESS_FINE_LOCATION == s && grantResults.size > index){
                val grant = grantResults[index]
                if(grant ==  PackageManager.PERMISSION_GRANTED){//权限通过
                    addEvent("click","location_yes")
                    AFUtil.up(this@WebActivity, "author_locate_yes")
                }else if(!ActivityCompat.shouldShowRequestPermissionRationale(this,PermissionUtils.ACCESS_FINE_LOCATION)
                    && ActivityCompat.checkSelfPermission(this,PermissionUtils.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_DENIED
                ){//权限拒绝并不再询问
                    addEvent("click","location_completeNo")
                    AFUtil.up(this@WebActivity, "author_locate_no")
                    AccountInfo.showToast(this@WebActivity,AccountInfo.LOCATION_TOAST_KEY)
                }else{
                    addEvent("click","location_no")
                    AFUtil.up(this@WebActivity, "author_locate_no")
                }
            }

            if(PermissionUtils.READ_SMS == s && grantResults.size > index){
                val grant = grantResults[index]
                if(grant ==  PackageManager.PERMISSION_GRANTED){//权限通过
                    addEvent("click","message_yes")
                    AFUtil.up(this@WebActivity, "author_message_yes")
                }else if(!ActivityCompat.shouldShowRequestPermissionRationale(this,PermissionUtils.READ_SMS)
                    && ActivityCompat.checkSelfPermission(this,PermissionUtils.READ_SMS)==PackageManager.PERMISSION_DENIED
                ){//权限拒绝并不再询问
                    addEvent("click","message_completeNo")
                    AFUtil.up(this@WebActivity, "author_message_no")
                    AccountInfo.showToast(this@WebActivity,AccountInfo.MESSAGE_TOAST_KEY)
                }else{
                    addEvent("click","message_no")
                    AFUtil.up(this@WebActivity, "author_message_no")
                }
            }

            if(PermissionUtils.READ_CONTACTS == s && grantResults.size > index){
                val grant = grantResults[index]
                if(grant ==  PackageManager.PERMISSION_GRANTED){//权限通过
                    addEvent("click","contact_yes")
                    AFUtil.up(this@WebActivity, "author_contact_yes")
                }else if(!ActivityCompat.shouldShowRequestPermissionRationale(this,PermissionUtils.READ_CONTACTS)
                    && ActivityCompat.checkSelfPermission(this,PermissionUtils.READ_CONTACTS)==PackageManager.PERMISSION_DENIED
                ){//权限拒绝并不再询问
                    addEvent("click","contact_completeNo")
                    AFUtil.up(this@WebActivity, "author_contact_no")
                    AccountInfo.showToast(this@WebActivity,AccountInfo.CONTACT_TOAST_KEY)
                }else{
                    addEvent("click","contact_no")
                    AFUtil.up(this@WebActivity, "author_contact_no")
                }
            }

            if(PermissionUtils.WRITE_EXTERNAL_STORAGE == s && grantResults.size > index){
                val grant = grantResults[index]
                if(grant ==  PackageManager.PERMISSION_GRANTED){//权限通过
                    addEvent("click","file_yes")
                    AFUtil.up(this@WebActivity, "author_file_yes")
                }else if(!ActivityCompat.shouldShowRequestPermissionRationale(this,PermissionUtils.WRITE_EXTERNAL_STORAGE)
                    && ActivityCompat.checkSelfPermission(this,PermissionUtils.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_DENIED
                ){//权限拒绝并不再询问
                    addEvent("click","file_completeNo")
                    AFUtil.up(this@WebActivity, "author_file_no")
                    AccountInfo.showToast(this@WebActivity,AccountInfo.FILE_TOAST_KEY)
                }else{
                    addEvent("click","file_no")
                    AFUtil.up(this@WebActivity, "author_file_no")
                }
            }

        }
    }

    override fun showUpdateDialog(
        vi: VersionInfo,
        forcedUpdate: Boolean
    ) {
        super.showUpdateDialog(vi, forcedUpdate)
        DialogFactory.getInstance().getUpdateDialog(this,!vi.isForcedUpdate,
            { v: View? ->
                if (!vi.isForcedUpdate) {
                    DialogFactory.getInstance().dismiss()
                }
                var link = vi.link
                if(!link.startsWith("http")){
                    link = "http://"+link
                }
                val intent = Intent("android.intent.action.VIEW")
                val content_url = Uri.parse(link)
                intent.data = content_url
                startActivity(intent)
                AFUtil.up(this@WebActivity, "updateConfirm_yes")
            }, { v: View? ->
                DialogFactory.getInstance().dismiss()
                AFUtil.up(this@WebActivity, "updateConfirm_no")
        },vi.isForcedUpdate
        )
    }

    override fun cleanCacheAndReload() {
        super.cleanCacheAndReload()
        webView?.let {
            mPresenter.clearCache(this, it)
            it.loadUrl(APIStore.H5_URL)
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val url = webView?.url?:""
            var shouldOver = false
            run outside@{
                AccountInfo.hasUrl.forEach {
                    if (url!!.contains(it)) {
                        shouldOver = true
                        return@outside
                    }
                }
            }
            if(webView != null && shouldOver){
                webView?.loadUrl(APIStore.H5_URL)
                return true
            }else if(url.equals(APIStore.HOME_URL)){
                return super.onKeyDown(keyCode, event)
            } else if (null != webView && webView?.canGoBack()!!) {
                webView?.goBack()
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    private fun addEvent(type:String,option:String){
        Log.d("logevent","type:"+type+"    option:"+option)
        EventUtils.addEvent("author",type,option)
    }

    override fun onStop() {
        super.onStop()
        MyApplication.getAppContext()?.let { AccountInfo.uploadLog(it) }
    }

    override fun onDestroy() {
        webView?.let { mPresenter.clearCache(this, it) }
        heidenLoadingView()
        super.onDestroy()

    }
}