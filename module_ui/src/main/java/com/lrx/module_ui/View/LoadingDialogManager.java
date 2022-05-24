package com.lrx.module_ui.View;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.lrx.module_ui.R;

import java.util.logging.Handler;

/**
 * create by Dennis
 * on 2020-01-06
 * description：
 **/
public class LoadingDialogManager implements ILoadingDialogManager{

    private Dialog dialog;

    private static LoadingDialogManager instance = null;

    private LoadingDialogManager() {}

    public static LoadingDialogManager getInstance(){
        if(instance == null){
            instance = new LoadingDialogManager();
        }
        return instance;
    }
    /**
     * 展示对话框，采用默认
     *
     * @param context
     */
    @Override
    public void showLoading(Context context) {
        showLoading(context, "", false, null);
    }

    /**
     * 关闭加载提示框
     */
    @Override
    public void dismissLoading() {
        LoadingDialogState.INSTANCE.setTimelong(System.currentTimeMillis());
        if (dialog != null && dialog.isShowing()) {
            try {
                dialog.dismiss();
                dialog = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 展示对话框
     *
     * @param context
     * @param text             提示文字内容
     * @param onCancelListener 取消监听(如果不需要直接传null）
     * @param cancelable       是否可以取消
     */
    private void showLoading(Context context, CharSequence text, boolean cancelable, DialogInterface.OnCancelListener onCancelListener) {
        try {
            if (dialog != null && dialog.isShowing()) {
                return;
            }
            if (dialog == null) {
                dialog = buildLoadingDialog(context, text, cancelable, onCancelListener);
            }
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 构建对话框
     *
     * @param context          上下文
     * @param text             弹窗提示的文字
     * @param onCancelListener 取消监听(如果不需要直接传null）
     * @param cancelable       是否可以取消
     * @return 返回对话框对象
     */
    private Dialog buildLoadingDialog(Context context, CharSequence text, boolean cancelable, DialogInterface.OnCancelListener onCancelListener) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_dialog_loading, null);// 得到加载view
        TextView loadingTextView = (TextView) view.findViewById(R.id.tv_loading_tips);
        final ImageView im_loading_logo = (ImageView) view.findViewById(R.id.im_loading_logo);//需要旋转的图片
        if(!TextUtils.isEmpty(text)){
            loadingTextView.setVisibility(View.VISIBLE);
            loadingTextView.setText(text);
        }
        //开启动画,不断循环
        Animation myAnima = AnimationUtils.loadAnimation(context, R.anim.anim_loading_dialog);
        im_loading_logo.startAnimation(myAnima);
        return initDialog(context,R.style.style_dialog_loading, view, cancelable, onCancelListener);
    }

    /**
     * 配置对话框参数
     *
     * @param themeId          显示主题
     * @param view             对话框布局
     * @param cancelable       是否可以取消
     * @param onCancelListener 取消监听(如果不需要直接传null）
     * @return 返回对话框对象
     */
    private Dialog initDialog(Context ctx,int themeId, View view, boolean cancelable, DialogInterface.OnCancelListener onCancelListener) {
        //主题
        dialog = new Dialog(ctx, themeId);
        dialog.setContentView(view);
        //设置监听事件
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);
        dialog.setOnCancelListener(onCancelListener);

        return dialog;
    }

    /**
     * 检测是否在showing loading
     * @return
     */
    public boolean isLoadingShow(){
        return dialog==null?false:dialog.isShowing();
    }
}
