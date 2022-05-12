package com.mmt.smartloan.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.app.ActivityCompat

/**
 * create by Dennis
 * on 2022/5/13
 * description：
 **/
object LocationUtils {
    var longitude = "NaN"
    var latitude = "NaN"

    fun getLocation(context: Context){
        if (ActivityCompat.checkSelfPermission(context,Manifest.permission.ACCESS_COARSE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            val locationManager: LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager // 默认Android GPS定位实例
            var location: Location? = null
            location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER) // 其他应用使用定位更新了定位信息 需要开启GPS
            if (location == null) {
                Log.i("GPS: ", "获取位置信息失败 采用基站定位")
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) // 基站定位
            }
            if (location != null) {
                Log.i("GPS: ", "获取位置信息成功")
                Log.i("GPS: ", "经度：" + location.getLatitude())
                Log.i("GPS: ", "纬度：" + location.getLongitude())
                longitude = location.longitude.toString()
                latitude = location.latitude.toString()
            }
        }
    }

}