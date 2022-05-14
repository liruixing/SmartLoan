package com.lrx.module_base.base;

import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.lrx.module_base.R;
import com.lrx.module_base.utils.SPUtils;
import com.lrx.module_ui.View.dialog.DialogFactory;
import com.siberiadante.titlelayoutlib.TitleBarLayout;
import com.lrx.module_base.manager.AppManagerUtil;
import com.lrx.module_ui.View.LoadingDialogManager;
import com.siberiadante.titlelayoutlib.utils.TransitionTools;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * create by Dennis
 * on 2020-01-06
 * description：
 **/
public abstract class BaseActivity extends FragmentActivity implements IBaseMVPView{

    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentViewResId());
        AppManagerUtil.getInstance().addActivity(this);
        mUnbinder = ButterKnife.bind(this);
        init();
    }

    protected void init() {}


    protected abstract int getContentViewResId();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManagerUtil.getInstance().removeActivity(this);
        if (mUnbinder != Unbinder.EMPTY) {
            mUnbinder.unbind();
        }
    }

    @Override
    public void showLoadingView() {
        if (!LoadingDialogManager.getInstance().isLoadingShow()) {
            LoadingDialogManager.getInstance().showLoading(this);
        } else {
            LoadingDialogManager.getInstance().dismissLoading();
            LoadingDialogManager.getInstance().showLoading(this);
        }
    }

    @Override
    public void heidenLoadingView() {
        if (LoadingDialogManager.getInstance().isLoadingShow()) {
            LoadingDialogManager.getInstance().dismissLoading();
        }
    }

    @Override
    public void writeString(String key, String value) {
        SPUtils.put(this,key,value);
    }

    @Override
    public String readString(String key) {
        return SPUtils.getString(this,key,"");
    }


    public void showTitleStyle(boolean showLeft, boolean showRight, String title,
                               TitleBarLayout titleLayoutbar) {
        titleLayoutbar.setVisibility(View.VISIBLE);
        if (showLeft) {
            titleLayoutbar.setIsLeftBackView(true);
            titleLayoutbar.setLeftImage(R.mipmap.ic_arrow_left_grey);
        } else {
            titleLayoutbar.setIsLeftBackView(false);
            titleLayoutbar.setLeftText("");
        }

        if (showRight) {
            titleLayoutbar.setRightImage(R.mipmap.ic_add_grey);
            titleLayoutbar.setRightText("");
        } else {
            titleLayoutbar.setRightImage(0);
            titleLayoutbar.setRightText("");
        }

        if (!TextUtils.isEmpty(title)) {
            titleLayoutbar.setTitle(title);
        }

        titleLayoutbar.setLeftClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    protected void setTitleRightClick(View view, int rightSourceID,
                                      View.OnClickListener clickListener) {
        if (view == null) return;
        TitleBarLayout titleLayoutbar = view.findViewWithTag("title_layoutbar");
        titleLayoutbar.setRightImage(rightSourceID);
        titleLayoutbar.setRightImageClickListener(clickListener);
    }

    /**
     * 设置title背景
     *
     * @param titleLayoutbar 控件
     * @param title_bg       drawable 或者 png
     * @param height         高度
     */
    protected void initTitleBackGround(TitleBarLayout titleLayoutbar, int title_bg, int height) {
        if (titleLayoutbar != null)
            titleLayoutbar.initBackGroundAndHeight(title_bg, TransitionTools.dip2px(this, height));
    }

    @Override
    public Resources.Theme getTheme() {
        return super.getTheme();
    }
}
