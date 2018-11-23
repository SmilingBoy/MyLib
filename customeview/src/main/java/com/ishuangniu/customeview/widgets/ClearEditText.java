package com.ishuangniu.customeview.widgets;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.ishuangniu.customeview.R;


/**
 * Created by Smile on 2017/7/11.
 */

public class ClearEditText extends android.support.v7.widget.AppCompatEditText implements View.OnFocusChangeListener, TextWatcher {

    /**
     * 左右图片
     */
    private Drawable leftDrawable;
    private Drawable clearDrawable;

    /**
     * 是否获取焦点，默认没有焦点
     */
    private boolean hasFocus = false;

    /**
     * 手指抬起时的X坐标
     */
    private int xUp = 0;

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

    /**
     * 初始化
     */
    private void init() {

        leftDrawable = getCompoundDrawables()[0];
        clearDrawable = getCompoundDrawables()[2];

        if (leftDrawable == null) {
            leftDrawable = getResources().getDrawable(R.drawable.clear_edittext_img_left);
        }

        if (clearDrawable == null) {
            clearDrawable = getResources().getDrawable(R.drawable.btn_clear_edit_text);

        }

        leftDrawable.setBounds(0, 0, leftDrawable.getIntrinsicWidth(), leftDrawable.getIntrinsicHeight());
        clearDrawable.setBounds(0, 0, clearDrawable.getIntrinsicWidth(), clearDrawable.getIntrinsicHeight());
        setCompoundDrawables(leftDrawable, null, null, null);

        //设置监听
        setOnFocusChangeListener(this);
        addTextChangedListener(this);

        setCompoundDrawablePadding(dip2px(10));

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count,
                                  int after) {
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int after) {
        if (hasFocus) {
            if (TextUtils.isEmpty(s)) {
                // 如果为空，则不显示删除图标
                setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, null, null);
            } else {
                // 如果非空，则要显示删除图标
                if (null == clearDrawable) {
                    clearDrawable = getCompoundDrawables()[2];
                }
                setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, clearDrawable, null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        try {
            if (!hasFocus) return super.onTouchEvent(event);

            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    // 获取点击时手指抬起的X坐标
                    xUp = (int) event.getX();
                    // 当点击的坐标到当前输入框右侧的距离小于等于getCompoundPaddingRight()的距离时，则认为是点击了删除图标
                    // getCompoundPaddingRight()的说明：Returns the right padding of the view, plus space for the right Drawable if any.
                    if ((getWidth() - xUp) <= getCompoundPaddingRight()) {
                        if (!TextUtils.isEmpty(getText().toString())) {
                            setText("");
                        }
                    }
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void afterTextChanged(Editable s) {
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        try {
            this.hasFocus = hasFocus;

            if (hasFocus) {
                setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, clearDrawable, null);
            } else {
                setCompoundDrawablesWithIntrinsicBounds(leftDrawable, null, null, null);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
