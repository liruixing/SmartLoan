package com.mmt.smartloan.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mmt.smartloan.MyApplication;
import com.mmt.smartloan.R;


/**
 * Created by WangKang.
 * Date: 2019/4/4 9:09
 * describle:
 */
public class ToastUtils extends Toast {

    private static ToastUtils toast;

    public ToastUtils(Context context) {
        super(context);
    }

    /**
     * 显示一个纯文本吐司
     *
     * @param context 上下文
     * @param text    显示的文本
     */
    public static void showText(Context context, CharSequence text) {
        showToast(context, text);
    }

    /**
     * 显示Toast
     *
     * @param context 上下文
     * @param text    显示的文本
     */
    private static void showToast(Context context, CharSequence text) {
        // 初始化一个新的Toast对象
        initToast(context, text);
        // 设置显示时长
//        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setDuration(Toast.LENGTH_LONG);
        // 显示Toast
        toast.show();
    }

    /**
     * 初始化Toast
     *
     * @param context 上下文
     * @param text    显示的文本
     */
    private static void initToast(Context context, CharSequence text) {
        try {
            cancelToast();
            toast = new ToastUtils(context);
            // 获取LayoutInflater对象
            LayoutInflater inflater =
                    (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // 由layout文件创建一个View对象
            // inflater.inflate(R.layout.layout,null);
            View layout = inflater.inflate(R.layout.toast_layout, null);
//            layout.getBackground().setAlpha(180);
            // 吐司上的文字
            TextView toast_text = (TextView) layout.findViewById(R.id.message);
            toast_text.setText(text);
            toast.setView(layout);
            //设置Toast要显示的位置，水平居中并在底部，X轴偏移0个单位，Y轴偏移180个单位，
            toast.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM, 0, 150);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 显示toast
     */
    @Override
    public void show() {
        try {
            super.show();
        } catch (Exception e) {
        }
    }

    /**
     * 隐藏当前Toast
     */
    public static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

    /**
     * 当前Toast消失
     */
    public void cancel() {
        try {
            super.cancel();
        } catch (Exception e) {

        }
    }

    /**
     * 显示toast String
     *
     * @param toastMsg
     */
    public static void showToast(final String toastMsg) {
        if (!AppUtils.isMainThread()) {
            MyApplication.Companion.getAppHandler().post(new Runnable() {
                @Override
                public void run() {
                    showToast(toastMsg);
                }
            });
        } else {
            if (toastMsg != null) {
                showText(MyApplication.Companion.getAppContext(), toastMsg);
            }
        }
    }


    /**
     * 资源ID
     *
     * @param toastMsgID
     */
    public static void showToast(final int toastMsgID) {
        if (!AppUtils.isMainThread()) {
            MyApplication.Companion.getAppHandler().post(new Runnable() {
                @Override
                public void run() {
                    showToast(toastMsgID);
                }
            });
        } else {
            String string = MyApplication.Companion.getAppContext().getString(toastMsgID);
            if (string == null) {
                string = "";
            }
            showText(MyApplication.Companion.getAppContext(), string);
        }
    }

    /**
     * 取消toast，在activity的destory方法中调用
     */
    public static void destory() {
        cancelToast();
    }

}
