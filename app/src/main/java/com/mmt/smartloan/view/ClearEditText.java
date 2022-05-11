package com.mmt.smartloan.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;


import com.mmt.smartloan.R;

import java.lang.reflect.Field;

public class ClearEditText extends AppCompatEditText implements View.OnFocusChangeListener, TextWatcher {
    private Drawable clearIcon;
    protected int maxLength = -1;
    protected TextWatcher textWatcher;
    private OnClickClearListener mOnClickClearListener;
    private OnCusFocusChangedListener mOnCusFocusChangedListener;

    public ClearEditText(Context context) {
        super(context);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private int getMaxLength() {
        int length = 0;
        InputFilter[] inputFilters = getFilters();
        for (InputFilter filter : inputFilters) {
            Class clazz = filter.getClass();
            if (clazz.getName() == "android.text.InputFilter\\$LengthFilter") {
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    if (field.getName().equals("mMax")) {
                        field.setAccessible(true);
                        try {
                            length = field.getInt(filter);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        return length;
    }

    private void init() {
        maxLength = getMaxLength();
        if (maxLength < 1) {
            maxLength = -1;
        }
        clearIcon = getCompoundDrawables()[2];
        if (clearIcon == null) {
            clearIcon = ContextCompat.getDrawable(getContext(), R.mipmap.ic_delete_menu);

        }
        clearIcon.setBounds(0, 0, (int)getResources().getDimension(R.dimen.px_40), (int)getResources().getDimension(R.dimen.px_40));
        setOnFocusChangeListener(this);
        addTextChangedListener(this);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (textWatcher != null) {
            textWatcher.beforeTextChanged(s, start, count, after);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        isVisibleClean(hasFocus() && getText().length() > 0);
        if (textWatcher != null) {
            textWatcher.onTextChanged(s, start, before, count);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (textWatcher != null) {
            textWatcher.afterTextChanged(s);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            isVisibleClean(getText().length() > 0);
        } else {
            isVisibleClean(false);
        }
        if (mOnCusFocusChangedListener != null) {
            mOnCusFocusChangedListener.onFocusChange(v, hasFocus);
        }
    }

    private void isVisibleClean(boolean isVisible) {
        Drawable[] drawables = getCompoundDrawables();
        setCompoundDrawables(drawables[0], drawables[1], isVisible ? clearIcon : null, drawables[3]);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            Drawable drawable = getCompoundDrawables()[2];
            if (drawable != null && event.getX() >= getWidth() - getPaddingRight() - drawable.getBounds().width() &&
                    event.getX() <= getWidth() - getPaddingRight()) {
                setText("");
                isVisibleClean(false);

                if(null != mOnClickClearListener){
                    mOnClickClearListener.onClickClear();
                }
            }
        }
        return super.onTouchEvent(event);


    }

    public void addOuterTextWatcher(TextWatcher textWatcher) {
        this.textWatcher = textWatcher;
    }

    public void setOnClickClearListener(OnClickClearListener onClickClearListener) {
        this.mOnClickClearListener = onClickClearListener;
    }

    public void setOnCusFocusChangedListener(OnCusFocusChangedListener onCusFocusChangedListener) {
        this.mOnCusFocusChangedListener = onCusFocusChangedListener;
    }

    public interface OnClickClearListener {
        void onClickClear();
    }

    public interface OnCusFocusChangedListener {
        void onFocusChange(View v, boolean hasFocus);
    }
}
