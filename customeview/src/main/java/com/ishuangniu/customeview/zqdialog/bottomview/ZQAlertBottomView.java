package com.ishuangniu.customeview.zqdialog.bottomview;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;

import com.ishuangniu.customeview.R;
import com.ishuangniu.customeview.zqdialog.impl.OnCancelBtnListener;
import com.ishuangniu.customeview.zqdialog.impl.OnItemClickListener;

import java.util.List;

/**
 * @author Smile
 * @date 2017/9/25
 */

public class ZQAlertBottomView<T> extends Dialog implements View.OnClickListener {

    private LinearLayout lySelAction;

    private List<T> items = null;
    private OnCancelBtnListener cancelBtnListener = null;
    private OnItemClickListener itemClickListener = null;
    private View root;


    public ZQAlertBottomView(@NonNull Context context) {
        super(context, R.style.BottomDialog);
        root = LayoutInflater.from(getContext()).inflate(R.layout.zqdilog_alert_bottom_view, null, false);
        Button btnCancel = root.findViewById(R.id.btn_cancel);
        lySelAction = root.findViewById(R.id.ly_sel_action);

        btnCancel.setOnClickListener(this);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(root);

        Window dialogWindow = getWindow();
        dialogWindow.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        lp.x = 0; // 新位置X坐标
        lp.y = 0; // 新位置Y坐标
        lp.width = getContext().getResources().getDisplayMetrics().widthPixels; // 宽度
        root.measure(0, 0);
        lp.height = root.getMeasuredHeight();

        lp.alpha = 9f; // 透明度
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onClick(View view) {
        if (null != cancelBtnListener) {
            cancelBtnListener.onCancel();
        }
        dismiss();
    }

    public ZQAlertBottomView setCancelBtnListener(OnCancelBtnListener cancelBtnListener) {
        this.cancelBtnListener = cancelBtnListener;
        return this;
    }

    public ZQAlertBottomView setItemClickListener(OnItemClickListener<T> itemClickListener) {
        this.itemClickListener = itemClickListener;
        return this;
    }


    public ZQAlertBottomView<T> setItemsText(List<T> items) {
        this.items = items;
        for (int i = 0; i < items.size(); i++) {
            addSelActionView(items.get(i), i);
        }
        return this;
    }


    //添加选项按钮
    private void addSelActionView(T text, int position) {
        LinearLayout.LayoutParams lpFw = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpFw.setMargins(0, 9, 0, 0);
        Button button = new Button(getContext());
        button.setText(text.toString());
        button.setTextColor(Color.BLACK);
        button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 16);
        button.setGravity(Gravity.CENTER);
        button.setTag(position);
        button.setBackgroundResource(R.drawable.bg_btn_round_white);
        lySelAction.addView(button, lpFw);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getTag();
                dismiss();
                if (null != itemClickListener) {
                    itemClickListener.onItemClick(items.get((Integer) view.getTag()), (Integer) view.getTag());
                }
            }
        });
    }
}
