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

    fun decodeFile(file:File,maxW:Int, maxH:Int, maxSize:Int):String{
        val filePath = file.absolutePath
        var bitmap = BitmapFactory.decodeFile(filePath)
        //先进行尺寸压缩，再进行大小压缩
        bitmap = preZoom(bitmap,maxW,maxH)
        //进行大小压缩
        bitmap = ImageCompressL(bitmap, maxSize)

        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_compress"
        val newPath = file.parent+File.separator+imageFileName+".jpg"
        val result = saveBitmapFile(bitmap,newPath)
        return newPath
    }


    fun decodeBitmap(file:Bitmap,maxW:Int, maxH:Int, maxSize:Int):Bitmap{
        var bitmap = file
        //先进行尺寸压缩，再进行大小压缩
        bitmap = preZoom(bitmap,maxW,maxH)
        //进行大小压缩
        bitmap = ImageCompressL(bitmap, maxSize)
        return bitmap
    }

    private fun ImageCompressL(bitmap:Bitmap,maxSize: Int):Bitmap {
        val targetwidth = Math.sqrt(maxSize.toDouble()/1024);
        var result:Bitmap = bitmap
        if (bitmap.getWidth() > targetwidth || bitmap.getHeight() > targetwidth) {
            // 创建操作图片用的matrix对象
            var matrix =  Matrix()
            // 计算宽高缩放率
            var x = Math.max(targetwidth / bitmap.getWidth(), targetwidth / bitmap.getHeight());
            // 缩放图片动作
            matrix.postScale(x.toFloat(), x.toFloat());
            result = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),
                    bitmap.getHeight(), matrix, true);
        }
        return result;
    }

    fun preZoom(bitmap: Bitmap, maxW:Int, maxH:Int):Bitmap{
        var result = bitmap
        val w = bitmap.width
        val h = bitmap.height
        if(w>maxW && h<=maxH){//需要压缩，以长度为标准
            val realH = h*((maxW/w).toDouble())
            result = zoomImage(bitmap, maxW.toDouble(),realH.toDouble())
        }else if(w<=maxW && h>maxH){//需要压缩，以高度为标准
            val realW = w*((maxH/h).toDouble())
            result = zoomImage(bitmap, realW,maxH.toDouble())
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
            result = zoomImage(bitmap, realW.toDouble(),realH.toDouble())
        }
        return result
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