package com.ishuangniu.customeview.zqdialog.loading;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.widget.TextView;

import com.ishuangniu.customeview.R;


/**
 *
 * @author Smile
 * @date 2017/7/13
 */

public class LoadingDialog extends Dialog {

    private String text;

    public LoadingDialog(@NonNull Context context) {
        this(context, R.style.BaseDialog);
        this.text = "";
    }

    public LoadingDialog(@NonNull Context context, String text) {
        this(context, R.style.BaseDialog);
        this.text = text;
    }

    private LoadingDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, R.style.BaseDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_loading_dialog);

        TextView title = (TextView) findViewById(R.id.tv_bottom);
        title.setText(text);
    }

    /**
     * @see Dialog#show()
     */
    @Override
    public void show() {
        try {
            if (!isShowing()) {
                super.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
