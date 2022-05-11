package com.mmt.smartloan.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.mmt.smartloan.R;

/**
 * create by Dennis
 * on 2022/5/12
 * description：
 **/
public class FloatButton extends AbastractDragFloatActionButton {
    public FloatButton(Context context) {
        super(context);
    }

    public FloatButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FloatButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public int getLayoutId() {
        return R.layout.float_button;//拿到你自己定义的悬浮布局
    }

    @Override
    public void renderView(View view) {
        //初始化那些布局
    }
}
