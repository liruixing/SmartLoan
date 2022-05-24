package com.lrx.module_ui.View

/**
 * create by Dennis
 * on 2022/5/24
 * description：
 **/
object LoadingDialogState {

    var timelong:Long = 0L

    fun canClick():Boolean{
        val time = System.currentTimeMillis()
        if((time - timelong)<800){
            return false
        }
        return true
    }
}