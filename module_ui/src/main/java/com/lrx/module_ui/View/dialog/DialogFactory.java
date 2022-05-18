package com.lrx.module_ui.View.dialog;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.lrx.module_ui.R;

/**
 * 弹框工具类
 *
 * @author zhuxian
 */
public class DialogFactory {

    private Dialog dialog;

    private static DialogFactory instance;

    /**
     * 弹窗工具类
     */
    private DialogFactory() {
    }

    public static DialogFactory getInstance() {
        if (instance == null) {
            instance = new DialogFactory();
        }
        return instance;
    }


    View.OnClickListener canselListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }
    };

    public void dismiss() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    public boolean isShown() {
        if (dialog == null) {
            return false;
        }
        return dialog.isShowing();
    }

    /**
     * @param context
     * @param themeId       主题
     * @param view          对话框内容布局
     * @param cancelable    是否可取消
     * @param outsideCancel 点外边框是否可以取消
     * @return
     */
    private Dialog initDialog(Context context, int themeId, View view, boolean cancelable,
                              boolean outsideCancel) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        //设置宽度和高度
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        ViewGroup.LayoutParams layoutParams =
                new ViewGroup.LayoutParams((int) (dm.widthPixels * 0.8f),
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        //主题以及大小
        dialog = new Dialog(context, themeId);
        dialog.setContentView(view, layoutParams);
        //设置监听事件
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(outsideCancel);
        dialog.show();
        return dialog;
    }

    /*******************************************************以下是自定义的dialog
     * 方法*******************************************************************/

    /**
     * @param context     上下文
     * @return
     */
    public Dialog getUpdateDialog(Context context,boolean canCancel,
                                           View.OnClickListener configClick,
                                           View.OnClickListener cancelClick,boolean isForceUpdate) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_update_smloan, null);
        Button btnConfig = view.findViewById(R.id.btn_confirm);
        ImageView imCancel = view.findViewById(R.id.im_cancel);
        if(isForceUpdate){
            imCancel.setVisibility(View.INVISIBLE);
            imCancel.setOnClickListener(null);
        }else{
            imCancel.setVisibility(View.VISIBLE);
            imCancel.setOnClickListener(cancelClick);
        }

        btnConfig.setOnClickListener(configClick);

        return initDialog(context, R.style.style_dialog, view, canCancel, false);
    }
    /**
     * @param context     上下文
     * @param title       标题
     * @param leftButton  左按钮
     * @param rightButton 有按钮
     * @param tips        提示内容
     * @param leftClick   左按钮监听器
     * @param rightClick  右侧按钮监听器
     * @return
     */
    public Dialog getTwoButtonDialogNormal(Context context, String title,
                                           String leftButton, String rightButton,
                                           String tips,
                                           View.OnClickListener leftClick,
                                           View.OnClickListener rightClick) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_normal_two_button, null);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvContent = view.findViewById(R.id.tv_content);
        Button btnLeft = view.findViewById(R.id.btn_left);
        Button btnRight = view.findViewById(R.id.btn_right);

        tvTitle.setText(title);
        tvContent.setText(tips);
        btnLeft.setText(leftButton);
        btnRight.setText(rightButton);
        btnLeft.setOnClickListener(leftClick);
        btnRight.setOnClickListener(rightClick);
        return initDialog(context, R.style.style_dialog, view, true, false);
    }

    /**
     * @param context
     * @param title
     * @param leftButton
     * @param tips
     * @param leftClick
     * @return
     */
    public Dialog getSingleButtonDialogNormal(Context context, String title,
                                              String tips,
                                              String leftButton,
                                              View.OnClickListener leftClick) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_normal_single_button,
                null);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvContent = view.findViewById(R.id.tv_content);
        Button btnLeft = view.findViewById(R.id.btn_confirm);

        tvTitle.setText(title);
        tvContent.setText(tips);
        btnLeft.setText(leftButton);
        btnLeft.setOnClickListener(leftClick);
        return initDialog(context, R.style.style_dialog, view, true, false);
    }

    /**
     * @param context
     * @param title
     * @param leftButton
     * @param tips
     * @param leftClick
     * @return
     */
    public Dialog getOtherLoginDialog(Context context, String title,
                                      String tips,
                                      String leftButton,
                                      View.OnClickListener leftClick) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_normal_single_button,
                null);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tvContent = view.findViewById(R.id.tv_content);
        Button btnLeft = view.findViewById(R.id.btn_confirm);

        tvTitle.setText(title);
        tvContent.setText(tips);
        btnLeft.setText(leftButton);
        btnLeft.setOnClickListener(leftClick);
        return initDialog(context, R.style.style_dialog, view, false, false);
    }
    /**
     * @param context
     * @param title
     * @param leftButton
     * @param data
     * @param dialogListClickListener
     * @return
     */
    public Dialog getListDialogCentre(Context context, String title,
                                      String leftButton,
                                      final String[] data,
                                      final IDialogListClickListener dialogListClickListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_list_button_centre, null);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        ListView lvList = view.findViewById(R.id.lv_list);
        Button btnLeft = view.findViewById(R.id.btn_confirm);

        DialogCentreAdapter adapter = new DialogCentreAdapter(context, data);
        lvList.setAdapter(adapter);

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (dialogListClickListener != null) {
                    dialogListClickListener.ClickItemListener(position, data[position]);
                }
               // dismiss();
            }
        });

        tvTitle.setText(title);
        btnLeft.setText(leftButton);
        btnLeft.setOnClickListener(canselListener);
        return initDialog(context, R.style.style_dialog, view, true, false);
    }
    /**
     * @param context
     * @param title
     * @param leftButton
     * @param data
     * @param dialogListClickListener
     * @return
     */
    public Dialog getListDialogNormal(Context context, String title,
                                      String leftButton,
                                      final String[] data,
                                      final IDialogListClickListener dialogListClickListener) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_list_button, null);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        ListView lvList = view.findViewById(R.id.lv_list);
        Button btnLeft = view.findViewById(R.id.btn_confirm);

        DialogAdapter adapter = new DialogAdapter(context, data);
        lvList.setAdapter(adapter);

        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (dialogListClickListener != null) {
                    dialogListClickListener.ClickItemListener(position, data[position]);
                }
                dismiss();
            }
        });

        tvTitle.setText(title);
        btnLeft.setText(leftButton);
        btnLeft.setOnClickListener(canselListener);
        return initDialog(context, R.style.style_dialog, view, true, false);
    }

    public void release() {
        if (dialog != null) {
            if (dialog.isShowing())
                dialog.dismiss();
            dialog = null;
        }
    }
}
