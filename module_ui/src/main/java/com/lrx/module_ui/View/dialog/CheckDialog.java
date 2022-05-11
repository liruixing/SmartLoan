package com.lrx.module_ui.View.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.CallSuper;

import com.lrx.module_ui.R;

/**
 * Created by WangKang.
 * Date: 2019/4/18 17:19
 * describle:dialog
 */
public class CheckDialog extends Dialog implements View.OnClickListener {

    private Context mContext;
    private float widthPrecent = 0.8f;
    private float heightPrecent = 0.4f;
    private String title;
    private EditText dialog_check_edt_unit;
    private EditText dialog_check_edt_wonumber;
    private OnListener listener;


    public CheckDialog(Context context) {
        super(context, R.style.dialog);
        this.mContext = context;
    }

    public CheckDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public CheckDialog setListenser(OnListener closeListenser) {
        this.listener = closeListenser;
        return this;
    }

    /**
     * 设置dialog宽度百分比
     * 设置dialog宽需要在show之后设置
     *
     * @param mContext
     * @param widthPrecent  0.8
     * @param heightPrecent 0.8
     * @return
     */
    public CheckDialog setDialogSize(Context mContext, float widthPrecent, float heightPrecent) {
        if (this.isShowing()) {
            WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
            DisplayMetrics dm = new DisplayMetrics();
            wm.getDefaultDisplay().getMetrics(dm);
            int width = dm.widthPixels;         // 屏幕宽度（像素）
            int height = dm.heightPixels;       // 屏幕高度（像素）
            WindowManager.LayoutParams lp = this.getWindow().getAttributes();
            if (widthPrecent > 0)
                lp.width = (int) (width * widthPrecent);
            if (heightPrecent > 0)
                lp.height = (int) (height * heightPrecent);
            //设置宽度
            this.getWindow().setAttributes(lp);
        } else {
            this.widthPrecent = widthPrecent;
            this.heightPrecent = heightPrecent;
        }
        return this;
    }

    /**
     * 获取点击事件
     */
    @CallSuper
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isShouldHideKeyBord(view, ev)) {
                hideSoftInput(view.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判定当前是否需要隐藏
     */
    protected boolean isShouldHideKeyBord(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            return !(ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom);
        }
        return false;
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager =
                    (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_check);
        setCanceledOnTouchOutside(false);
        initView();

    }

    private void initView() {
        TextView dialog_check_tv_title = findViewById(R.id.dialog_check_tv_title);
        dialog_check_edt_unit = findViewById(R.id.dialog_check_edt_unit);
        dialog_check_edt_wonumber = findViewById(R.id.dialog_check_edt_wonumber);
        Button dialog_check_btn_cancle = findViewById(R.id.dialog_check_btn_cancle);
        Button dialog_check_btn_check = findViewById(R.id.dialog_check_btn_check);
        dialog_check_btn_check.setOnClickListener(this);
        dialog_check_btn_cancle.setOnClickListener(this);
        if (!TextUtils.isEmpty(title)) {
            dialog_check_tv_title.setText(title);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.dialog_check_btn_cancle) {
            back();
        } else if (id == R.id.dialog_check_btn_check) {
            String unit = dialog_check_edt_unit.getText().toString().trim();
            String woNumber = dialog_check_edt_wonumber.getText().toString().trim();
            if (listener != null) {
                listener.onClick(unit, woNumber, true);
            }
        }
    }

    private void back() {
        if (listener != null) {
            listener.onClick(null, null, false);
        }
        this.dismiss();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back();
    }

    public interface OnListener {

        void onClick(String unit, String woNumber, boolean confirm);

    }

    @Override
    public void show() {
        super.show();
        if (widthPrecent > 0 || heightPrecent > 0) {//设置dialog宽需要在show之后设置
            setDialogSize(mContext, widthPrecent, heightPrecent);
        }
        dialog_check_edt_unit.setText("D06&12");
        dialog_check_edt_wonumber.setText("1111475");
    }

}