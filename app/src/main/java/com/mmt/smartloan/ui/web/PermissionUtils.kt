package com.mmt.smartloan.ui.web

import android.Manifest

/**
 * create by Dennis
 * on 2022/4/20
 * description：
 **/
object PermissionUtils {


    const val CAMERA = Manifest.permission.CAMERA
    const val READ_CONTACTS = Manifest.permission.READ_CONTACTS
    const val READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE
    const val WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE
    val STORAGE_AND_CAMERA = arrayOf(
            WRITE_EXTERNAL_STORAGE,
            READ_EXTERNAL_STORAGE,
            CAMERA
    )



}