package com.mmt.smartloan.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.mmt.smartloan.config.AccountInfo
import com.mmt.smartloan.http.APIManager
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * create by Dennis
 * on 2022/4/23
 * description：
 **/
class LogInfoService:Service() {
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onCreate() {
        super.onCreate()
        startReceiveLogThread()
        startUploadThread()
    }

    //开启收集日志线程
    private fun startReceiveLogThread() {
        Thread(){
            kotlin.run {
                getLocatLog()
            }
        }.start()
    }

    //开启上传信息
    private fun startUploadThread() {
        while (AccountInfo.isUpload){



        }
        APIManager.getInstance()

    }


    private fun getLocatLog(){
        var mLogcatProc: Process? = null
        var reader: BufferedReader? = null
        try {
            //获取logcat日志信息
            mLogcatProc = Runtime.getRuntime().exec("logcat -t")
            reader = BufferedReader(InputStreamReader(mLogcatProc.getInputStream()))
            var line: String
            while (reader.readLine().also { line = it } != null) {
                if(AccountInfo.isCollectLog){
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}