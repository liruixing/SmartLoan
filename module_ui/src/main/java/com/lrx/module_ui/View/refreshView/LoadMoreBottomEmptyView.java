package com.lrx.module_ui.View.refreshView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.lcodecore.tkrefreshlayout.IBottomView;
import com.lrx.module_ui.R;

/**
 * 上拉加载再多,去掉默认，不显示任何东西
 * Created by zhuxian on 2017/8/24.
 */

public class LoadMoreBottomEmptyView implements IBottomView {


    private Context context;
    private View view;

    public LoadMoreBottomEmptyView(Context context) {
        this.context = context;
    }

    @Override
    public View getView() {
        view = LayoutInflater.from(context).inflate(R.layout.layout_tw_empty_view, null);
        return view;
    }

    @Override
    public void onPullingUp(float fraction, float maxBottomHeight, float bottomHeight) {

    }

    @Override
    public void onPullReleasing(float fraction, float maxBottomHeight, float bottomHeight) {
    }

    @Override
    public void startAnim(float maxBottomHeight, float bottomHeight) {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public void reset() {
    }
}
