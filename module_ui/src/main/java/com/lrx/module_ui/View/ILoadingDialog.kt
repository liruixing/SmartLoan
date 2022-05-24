package com.lrx.module_ui.View

import android.content.Context

interface ILoadingDialog {
    fun getContext():Context?
    fun dismiss()
    fun isShowing():Boolean
}