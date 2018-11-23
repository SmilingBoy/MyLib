package com.ishuangniu.customeview.titleview.utils;

import android.content.Context;

/**
 * Created by Smile on 2017/9/30.
 */

public class ZQTools {
    /**
     * dp转px
     *
     * @param dpValue dp值
     * @return px值
     */
    public static int dp2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
