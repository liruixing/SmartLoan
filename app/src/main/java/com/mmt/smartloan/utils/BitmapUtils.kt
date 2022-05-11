package com.mmt.smartloan.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Build
import android.util.Base64
import androidx.annotation.RequiresApi
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.max

/**
 * create by Dennis
 * on 2022/4/20
 * description：
 **/
object BitmapUtils {

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun decodeFile(file:File,maxW:Int, maxH:Int, maxSize:Int):String{
        val filePath = file.absolutePath
        var bitmap = BitmapFactory.decodeFile(filePath)
        bitmap = decodeBitmap(bitmap,maxW, maxH, maxSize)

        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_compress"
        val newPath = file.parent+File.separator+imageFileName+".jpg"
        val result = saveBitmapFile(bitmap,newPath)
        return newPath
    }

    /**
     * 压缩尺寸到指定尺寸
     */
    fun zoomImage( bgimage: Bitmap, newWidth: Double,newHeight: Double): Bitmap {
        // 获取这个图片的宽和高
        val width = bgimage.width.toFloat()
        val height = bgimage.height.toFloat()
        // 创建操作图片用的matrix对象
        val matrix = Matrix()
        // 计算宽高缩放率
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(
            bgimage, 0, 0, width.toInt(),
            height.toInt(), matrix, true
        )
    }

    @RequiresApi(Build.VERSION_CODES.KITKAT)
    fun decodeBitmap(bitmap: Bitmap, maxW:Int, maxH:Int, maxSize:Int):Bitmap{
        if(compareBitmap(bitmap, maxW, maxH, maxSize)){
            val w = bitmap.width
            val h = bitmap.height
            if(w>maxW && h<=maxH){//需要压缩，以长度为标准
                val realH = h*((maxW/w).toDouble())
                zoomImage(bitmap, maxW.toDouble(),realH.toDouble())
            }else if(w<=maxW && h>maxH){//需要压缩，以高度为标准
                val realW = w*((maxH/h).toDouble())
                zoomImage(bitmap, realW,maxH.toDouble())
            }else if(w>maxW && h>maxH){//需要压缩，取最小标准
                val wr:Double = maxW/w.toDouble()
                val hr:Double = maxH/h.toDouble()
                var rate = if(wr>hr){
                    hr
                }else{
                    wr
                }
                val realW = w*rate
                val realH = h*rate
                zoomImage(bitmap, realW.toDouble(),realH.toDouble())
            }
            return bitmap
        }else{
            while (!compareBitmap(bitmap,maxW, maxH, maxSize)){
                //压缩
                val baos = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos)
                val byte = baos.toByteArray()
                val ins = ByteArrayInputStream(byte)
                val bm = BitmapFactory.decodeStream(ins)
                ins.close()
                baos.close()
            }
            val w = bitmap.width
            val h = bitmap.height
            if(w>maxW && h<=maxH){//需要压缩，以长度为标准
                val realH = h*maxW/w
                zoomImage(bitmap, maxW.toDouble(),realH.toDouble())
            }else if(w<=maxW && h>maxH){//需要压缩，以高度为标准
                val realW = w*maxH/h
                zoomImage(bitmap, realW.toDouble(),maxH.toDouble())
            }
            return bitmap
        }
    }


    @RequiresApi(Build.VERSION_CODES.KITKAT)
    private fun compareBitmap(bitmap: Bitmap, maxW:Int, maxH:Int, maxSize:Int):Boolean{

        val size = bitmap.allocationByteCount
        if(size > maxSize){
            return false
        }else{
            return true
        }
    }


    fun bitmapToBase64(bitmap: Bitmap):String{
        var result:String = ""
        if (bitmap != null) {
            var baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            baos.flush();
            baos.close();
            val bitmapBytes = baos.toByteArray();
            result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);

            if (baos != null) {
                baos.flush();
                baos.close();
            }
        }
        return result
    }

    private fun saveBitmapFile(bitmap:Bitmap,path:String):Int{
        val file=File(path);//将要保存图片的路径
        try {
            val bos =  BufferedOutputStream( FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            return 0
        } catch (e:IOException) {
            e.printStackTrace();
        }
        return -1
    }

}