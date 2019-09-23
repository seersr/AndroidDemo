package com.example.sb.myandroidtest.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sb.myandroidtest.R;
import com.example.sb.myandroidtest.base.BaseActivity;
import com.example.sb.myandroidtest.utils.JumpUtils;
import com.example.sb.myandroidtest.utils.ToastUtils;

public class LoginActivity extends BaseActivity implements View.OnClickListener {


    /**
     * 注册
     */
    private Button mBtRegister;
    /**
     * 忘记密码
     */
    private Button mBtForgetPassword;
    /**
     * 登录
     */
    private Button mBtLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

    }

    private void initView() {
        mBtRegister = (Button) findViewById(R.id.bt_register);
        mBtRegister.setOnClickListener(this);
        mBtForgetPassword = (Button) findViewById(R.id.bt_forgetPassword);
        mBtForgetPassword.setOnClickListener(this);
        mBtLogin = (Button) findViewById(R.id.bt_login);
        mBtLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt_register:
                ToastUtils.show(this,"跳转注册页面",0);
                break;
            case R.id.bt_forgetPassword:
                ToastUtils.show(this,"跳转忘记密码页面",0);
                break;
            case R.id.bt_login:
                JumpUtils.goNext(this,MainActivity.class,true);
                break;
        }
    }
}
