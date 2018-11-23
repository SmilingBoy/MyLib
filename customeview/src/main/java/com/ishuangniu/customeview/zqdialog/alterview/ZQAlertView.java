package com.ishuangniu.customeview.zqdialog.alterview;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ishuangniu.customeview.R;
import com.ishuangniu.customeview.zqdialog.impl.OnCancelBtnListener;
import com.ishuangniu.customeview.zqdialog.impl.OnOkListener;


/**
 * @author Smile
 * @date 2017/9/25
 */

public class ZQAlertView extends Dialog implements View.OnClickListener {

    private OnOkListener okListener = null;
    private OnCancelBtnListener cancelBtnListener = null;

    private TextView tvContent;
    private Button btnOk;
    private Button btnCancel;

    private String contentText = "";
    private String rightText;
    private String leftText;

    public ZQAlertView(@NonNull Context context) {
        super(context, R.style.BaseDialog);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zqdilog_alterview);

        tvContent = findViewById(R.id.tv_content);
        btnOk = findViewById(R.id.btn_ok);
        btnCancel = findViewById(R.id.btn_cancel);

        btnOk.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        tvContent.setText(contentText);

        if (!TextUtils.isEmpty(leftText)) {
            btnCancel.setText(leftText);
        }

        if (!TextUtils.isEmpty(rightText)) {
            btnOk.setText(rightText);
        }

    }

    @Override
    public void onClick(View view) {
        int i = view.getId();
        if (i == R.id.btn_ok) {
            dismiss();
            if (null != okListener) {
                okListener.onOk();
            }
        } else if (i == R.id.btn_cancel) {
            dismiss();
            if (null != cancelBtnListener) {
                cancelBtnListener.onCancel();
            }
        }

    }

    public ZQAlertView setText(String content) {
        if (!content.isEmpty()) {
            contentText = content;
        }
        return this;
    }

    public ZQAlertView setLeftText(String content) {
        if (!content.isEmpty()) {
            leftText = content;
        }
        return this;
    }

    public ZQAlertView setRightText(String content) {
        if (!content.isEmpty()) {
            rightText = content;
        }
        return this;
    }

    public ZQAlertView setOkListener(OnOkListener okListener) {
        this.okListener = okListener;
        return this;
    }

    public ZQAlertView setCancelBtnListener(OnCancelBtnListener cancelBtnListener) {
        this.cancelBtnListener = cancelBtnListener;
        return this;
    }
}
