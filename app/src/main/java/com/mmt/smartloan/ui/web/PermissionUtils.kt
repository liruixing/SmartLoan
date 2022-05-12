package com.mmt.smartloan.ui.web

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat

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

    const val READ_PHONE_STATE = Manifest.permission.READ_PHONE_STATE

    const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    const val ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION

    const val READ_SMS = Manifest.permission.READ_SMS


    val STORAGE_AND_CAMERA = arrayOf(
            WRITE_EXTERNAL_STORAGE,
            READ_EXTERNAL_STORAGE,
            CAMERA
    )


    fun hasPermission(context:Context,permission:String):Boolean{
        if (ActivityCompat.checkSelfPermission(
                context,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return false
        }
        return true
    }

}