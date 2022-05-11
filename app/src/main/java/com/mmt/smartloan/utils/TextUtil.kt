package com.mmt.smartloan.utils

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import com.mmt.smartloan.http.bean.JSBean
import com.mmt.smartloan.http.bean.request.EventLogItem
import java.io.*
import java.lang.Exception

/**
 * create by Dennis
 * on 2022/4/17
 * description：
 **/
object TextUtil {

    fun setLoginCorlor(content:String,s1:String,s2:String):SpannableStringBuilder{
        val index1 = content.indexOf(s1)
        val index2 = index1+s1.length
        val index3 = content.indexOf(s2)
        val index4 = index3+s2.length

        val spannable = SpannableStringBuilder(content)
        spannable.setSpan(ForegroundColorSpan(Color.RED),index1,index2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(ForegroundColorSpan(Color.RED),index3,index4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannable
    }

    fun setLoginClick(content:String,s1:String,s2:String):SpannableStringBuilder{
        val index1 = content.indexOf(s1)
        val index2 = index1+s1.length
        val index3 = content.indexOf(s2)
        val index4 = index3+s2.length

        val spannable = SpannableStringBuilder(content)
        spannable.setSpan(ForegroundColorSpan(Color.RED),index1,index2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(ForegroundColorSpan(Color.RED),index3,index4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannable
    }

    fun deepCopy(src: ArrayList<EventLogItem>): ArrayList<EventLogItem> {
        try {
            val byteOut = ByteArrayOutputStream()
            val out = ObjectOutputStream(byteOut)
            out.writeObject(src)
            val byteIn = ByteArrayInputStream(byteOut.toByteArray())
            val input = ObjectInputStream(byteIn)
            return input.readObject() as ArrayList<EventLogItem>
        }catch (e:Exception){
            return arrayListOf()
        }
    }

    fun isPhone(input:String):Boolean{
        input.replace(" ","")
        val regex = Regex("[0-9]+")
        return regex.matches(input)
    }

    fun isVercode(input:String):Boolean{
        val regex = Regex("[0-9]+")
        return regex.matches(input)
    }
}