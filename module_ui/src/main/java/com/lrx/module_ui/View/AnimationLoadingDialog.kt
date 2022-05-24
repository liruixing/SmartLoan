package com.lrx.module_ui.View

import android.app.Dialog
import android.content.Context
import com.lrx.module_ui.R


/**
 * Created by Ellen on 2017/7/31.
 * Copyright (C) 2017 Tungee, Inc.
 * http://www.tungee.com
 */
class AnimationLoadingDialog(context: Context, canCancel: Boolean):ILoadingDialog {
    var dialog: Dialog? = null
    private fun createLoadingDialog(context: Context, canCancel: Boolean) {
        dialog = Dialog(context, R.style.LoadingDialog)
        dialog?.run {
            setContentView(R.layout.layout_animation_loading_dialog)
            setCanceledOnTouchOutside(canCancel)
        }
    }

    fun show() {
        dialog?.run {
            show()
        }
    }

    override fun dismiss() {
        if (dialog?.isShowing == true) {
            dialog?.cancel()
        }
    }

    override fun isShowing(): Boolean {
        return dialog?.isShowing == true
    }

    override fun getContext(): Context? {
        return dialog?.context
    }

    init {
        createLoadingDialog(context, canCancel)
    }
}