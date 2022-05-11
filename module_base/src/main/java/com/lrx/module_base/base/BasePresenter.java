package com.lrx.module_base.base;

import android.text.TextUtils;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lrx.module_ui.View.refreshView.RefreshHeadView;
import com.st.network.utils.LogUtil;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;

/**
 * create by Dennis
 * on 2020-01-06
 * description：
 **/
public class BasePresenter<V extends IBaseMVPView> {

    protected V mView;
    private WeakReference<V> weakReferenceView;
    protected CompositeDisposable mCompositeDisposable;

    private TwinklingRefreshLayout mTwinklingRefreshLayout;

    //页码，默认1
    protected int pageIndex = 1;
    //每页条数10
    protected final int pageSize = 10;

    //防止空指针 获取到接口层
    protected V getView() {
        if (mView == null) {
            throw new IllegalStateException("view not attached");
        } else {
            return mView;
        }
    }

    /**
     * 绑定view
     *
     * @param view
     */
    public void attachMvpView(V view) {
        mCompositeDisposable = new CompositeDisposable();
        if (view != null) {
            weakReferenceView = new WeakReference<>(view);
            this.mView = weakReferenceView.get();
        }

    }

    /**
     * 解除绑定view
     */
    public void detachMvpView() {
        weakReferenceView.clear();
        mCompositeDisposable.dispose();
        weakReferenceView = null;
        mView = null;
    }

    public void setSetting(TwinklingRefreshLayout mTwinklingRefreshLayout, boolean isRefresh, boolean isLoadmore) {
        if (mTwinklingRefreshLayout != null) {
            this.mTwinklingRefreshLayout = mTwinklingRefreshLayout;
            mTwinklingRefreshLayout.setEnableRefresh(isRefresh);
            mTwinklingRefreshLayout.setEnableLoadmore(isLoadmore);
            mTwinklingRefreshLayout.setEnableOverScroll(true);
            mTwinklingRefreshLayout.setHeaderView(new RefreshHeadView(mView.getActivity()));

            mTwinklingRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {

                @Override
                public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                    super.onRefresh(refreshLayout);
                    onRefreshData(refreshLayout);
                }

                @Override
                public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                    super.onLoadMore(refreshLayout);
                    LogUtil.d("");
                    onLoadMoreData(refreshLayout);
                }
            });
        }
    }

    protected void finishLoadmore() {
        if (mTwinklingRefreshLayout != null)
            mTwinklingRefreshLayout.finishLoadmore();
    }

    protected void finishRefreshing() {
        if (mTwinklingRefreshLayout != null)
            mTwinklingRefreshLayout.finishRefreshing();
    }

    public void canLoadmore(boolean canLoadMore) {
        if (mTwinklingRefreshLayout == null) return;
        this.mTwinklingRefreshLayout.setEnableLoadmore(canLoadMore);
    }

    protected void onLoadMoreData(TwinklingRefreshLayout refreshLayout) {

    }

    protected void onRefreshData(TwinklingRefreshLayout refreshLayout) {

    }

    protected void hideLoading() {
        if (mView != null)
            mView.heidenLoadingView();
    }

    protected void showLoading() {
        if (mView != null)
            mView.showLoadingView();
    }

}
