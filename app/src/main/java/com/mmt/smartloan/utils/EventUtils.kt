package com.mmt.smartloan.utils

import com.mmt.smartloan.config.AccountInfo
import com.mmt.smartloan.http.bean.request.EventLogItem

/**
 * create by Dennis
 * on 2022/5/12
 * description：
 **/
object EventUtils {

    fun addEvent(page:String,type:String,option:String){
        val event: EventLogItem = EventLogItem()
        event.pageName = page
        event.eventType = type
        event.eventOption = option
        AccountInfo.logList.add(event)
    }

}