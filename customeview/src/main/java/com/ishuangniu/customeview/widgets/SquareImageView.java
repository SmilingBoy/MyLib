package com.ishuangniu.customeview.widgets;

import android.content.Context;
import android.util.AttributeSet;

/**
 * @author Smile
 */
public class SquareImageView extends android.support.v7.widget.AppCompatImageView {

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec);
        //重写此方法后默认调用父类的onMeasure方法,分别将宽度测量空间与高度测量空间传入
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}