package com.lrx.module_ui.View.refreshView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.lcodecore.tkrefreshlayout.IBottomView;
import com.lrx.module_ui.R;

/**
 * 上拉加载再多
 * Created by zhuxian on 2017/8/24.
 */

public class LoadMoreBottomView implements IBottomView {


    private Context context;
    private ImageView im_loading_circle;//转圈
    private TextView tv_loading_tips;//文字提示
    private View view;
    private Animation animation;//动画对象

    public LoadMoreBottomView(Context context) {
        this.context = context;
        animation = AnimationUtils.loadAnimation(context, R.anim.anim_loading_circle);
    }

    @Override
    public View getView() {
        view = LayoutInflater.from(context).inflate(R.layout.layout_refresh_headview, null);
        im_loading_circle = (ImageView) view.findViewById(R.id.im_loading_circle);
        tv_loading_tips = (TextView) view.findViewById(R.id.tv_loading_tips);
        return view;
    }

    @Override
    public void onPullingUp(float fraction, float maxBottomHeight, float bottomHeight) {
        im_loading_circle.setRotation(fraction * 360);
        if (fraction >= 1) {
            tv_loading_tips.setText("放手以刷新");
        } else {
            tv_loading_tips.setText("上拉加载更多");
        }
    }

    @Override
    public void onPullReleasing(float fraction, float maxBottomHeight, float bottomHeight) {
        im_loading_circle.setRotation(fraction * 360);
    }

    @Override
    public void startAnim(float maxBottomHeight, float bottomHeight) {
        tv_loading_tips.setText("加载中");
        im_loading_circle.startAnimation(animation);
    }

    @Override
    public void onFinish() {
        tv_loading_tips.setText("加载完成");
        animation.cancel();
    }

    @Override
    public void reset() {
        im_loading_circle.clearAnimation();
    }
}
