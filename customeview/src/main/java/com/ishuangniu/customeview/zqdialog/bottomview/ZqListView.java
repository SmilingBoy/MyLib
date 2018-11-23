package com.ishuangniu.customeview.zqdialog.bottomview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;

import com.ishuangniu.customeview.R;

/**
 *
 * @author Smile
 * @date 2018/5/14
 */

public class ZqListView extends Dialog {

    public ZqListView(@NonNull Context context) {
        super(context);
    }

    public ZqListView(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
    }

    protected ZqListView(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zq_dialog_list);
    }
}
