package com.example.sb.myandroidtest.broadcasted;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import com.example.sb.myandroidtest.base.BaseActivity;
import com.example.sb.myandroidtest.utils.networkUtils.NetWorkUtils;
import com.orhanobut.logger.Logger;

public class NetWorkBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Logger.e(""+context);
        // 如果相等的话就说明网络状态发生了变化
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
            boolean netWorkState = NetWorkUtils.isNetworkConnected(context);
            // 接口回调传过去状态的类型
            if (BaseActivity.netEvent != null){
                BaseActivity.netEvent.onNetChange(netWorkState);
            }
        }
    }
    // 网络状态变化接口
    public interface NetChangeListener {
        void onNetChange(boolean netWorkState);
    }
}
