package com.lrx.module_ui.View.refreshView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;
import com.lrx.module_ui.R;

/**
 * 头部刷新控件,去掉默认，不显示任务东西
 * Created by zhuxian on 2017/8/23.
 */

public class RefreshHeadEmptyView implements IHeaderView {

    private Context context;
    private View view;

    public RefreshHeadEmptyView(Context context) {
        this.context = context;
    }


    @Override
    public View getView() {
        view = LayoutInflater.from(context).inflate(R.layout.layout_tw_empty_view, null);
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
    }

    /**
     * 开始启动动画
     *
     * @param maxHeadHeight
     * @param headHeight
     */
    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {

    }

    /**
     * 结束动画
     *
     * @param animEndListener
     */
    @Override
    public void onFinish(OnAnimEndListener animEndListener) {
        animEndListener.onAnimEnd();
    }

    /**
     * 重置动画
     */
    @Override
    public void reset() {
    }
}
