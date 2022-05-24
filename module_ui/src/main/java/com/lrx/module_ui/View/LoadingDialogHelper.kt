package com.lrx.module_ui.View

import android.content.Context
import android.util.Log

// 最顶层Loading工具类，统一管理
object LoadingDialogHelper {
    var loadingDialog: ILoadingDialog? = null
    var contextHash = 0

    /**
     * 显示不带提示语的loading
     *
     * @param context Context
     * @param canCancel Boolean 是否可以取消，默认不可
     */
    @JvmOverloads // 注解用于可使Java调用时只传非默认参数即可
    fun showAnimationDialog(context: Context?, canCancel: Boolean = false) {
        if(context == null){
            return
        }
        // 生命周期管理
        if (context.hashCode() == contextHash && loadingDialog is AnimationLoadingDialog) {
            return
        }
        if (loadingDialog?.isShowing() == true) {
            loadingDialog?.dismiss()
            loadingDialog = null
        }
        contextHash = context.hashCode()
        loadingDialog = loadingDialog ?: run {
            AnimationLoadingDialog(context, canCancel).apply {
                show()
            }
        }
        log(context)

    }



    private fun log(context: Context) {
        Log.i("LoadingDialogHelper", "last context ==== $context")
    }

    fun dismiss() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }
}