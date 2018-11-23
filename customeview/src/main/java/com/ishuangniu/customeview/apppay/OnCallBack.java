package com.ishuangniu.customeview.apppay;

/**
 * Created by chenjiawei on 16/8/26.
 */
public interface OnCallBack {

    void onForgetPassword();

    void onInputCompleted(CharSequence password, PasswordKeypad passwordKeypad);

    void onPasswordCorrectly();

    void onCancel();
}
