package com.example.sb.myandroidtest.base;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

/**
 * Created by shishaobin on 2019/8/20
 */
public class MyApp extends Application{
    public static MyApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        //log日志 初始化
        Logger.addLogAdapter(new AndroidLogAdapter());
    }

    public static MyApp getInstance() {
        return mInstance;
    }
}
