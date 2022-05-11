package com.lrx.module_ui.View.refreshView;

import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;
import com.lrx.module_ui.R;

/**
 * 头部刷新控件
 * Created by zhuxian on 2017/8/23.
 */

public class RefreshHeadView implements IHeaderView {

    private Context context;
    private ImageView im_loading_circle;//转圈
    private TextView tv_loading_tips;//文字提示
    private View view;
    private Animation animation;//动画对象
    private Handler mHandler;
    private int defaultDismissDisplayTime = 1000;//默认延时时间
    private String refleshTextTip = "正在刷新...";//统一显示的刷新提示文字

    public RefreshHeadView(Context context) {
        this.context = context;
        mHandler = new Handler();
        animation = AnimationUtils.loadAnimation(context, R.anim.anim_loading_circle);
    }

    /**
     * 设置默认的延时取消动画时间
     *
     * @param displayTime
     */
    public void setDefaultDismissDisplayTime(int displayTime) {
        this.defaultDismissDisplayTime = displayTime;
    }

    @Override
    public View getView() {
        view = LayoutInflater.from(context).inflate(R.layout.layout_refresh_headview, null);
        im_loading_circle = (ImageView) view.findViewById(R.id.im_loading_circle);
        tv_loading_tips = (TextView) view.findViewById(R.id.tv_loading_tips);
        return view;
    }

    /**
     * 正在下拉的过程
     *
     * @param fraction      当前下拉高度与总高度的比
     * @param maxHeadHeight
     * @param headHeight
     */
    @Override
    public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {
        im_loading_circle.setRotation(fraction * 360);
        tv_loading_tips.setText(refleshTextTip);
    }

    /**
     * 正在释放的过程
     *
     * @param fraction
     * @param maxHeadHeight
     * @param headHeight
     */
    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
        im_loading_circle.setRotation(fraction * 360);
    }

    /**
     * 开始启动动画
     *
     * @param maxHeadHeight
     * @param headHeight
     */
    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        tv_loading_tips.setText(refleshTextTip);
        im_loading_circle.startAnimation(animation);
    }

    /**
     * 结束动画
     *
     * @param animEndListener
     */
    @Override
    public void onFinish(OnAnimEndListener animEndListener) {
        tv_loading_tips.setText(refleshTextTip);
        mHandler.postDelayed(new RunFinish(animEndListener), defaultDismissDisplayTime);
    }

    /**
     * 重置动画
     */
    @Override
    public void reset() {
        im_loading_circle.clearAnimation();
    }

    /**
     * 延时取消动画
     */
    private class RunFinish implements Runnable {

        OnAnimEndListener animEndListener;

        public RunFinish(OnAnimEndListener animEndListener) {
            this.animEndListener = animEndListener;
        }

        @Override
        public void run() {
            animation.cancel();
            animEndListener.onAnimEnd();
        }
    }
}
