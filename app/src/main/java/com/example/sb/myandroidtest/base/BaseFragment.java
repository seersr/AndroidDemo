package com.example.sb.myandroidtest.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sb.myandroidtest.R;
import com.example.sb.myandroidtest.eventbus.EventMessage;
import com.example.sb.myandroidtest.utils.EventBusUtils;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by shishaobin on 2019/8/21
 */
public abstract class BaseFragment extends Fragment{
    private boolean isInitData = false; //标志位 判断数据是否初始化
    private boolean isVisibleToUser = true; //标志位 判断fragment是否可见
    private boolean isPrepareView = false; //标志位 判断view已经加载完成 避免空指针操作
    public Activity mactivity;//子类调用
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mactivity = (Activity) context;
    }

    public abstract int setLayoutID();
    public abstract void findviewByid(View view);
    //加载数据的方法,由子类实现
    public abstract void initData();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isRegisteredEventBus()) {
            EventBusUtils.register(this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(setLayoutID(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isPrepareView = true;
        findviewByid(view);
    }

    //懒加载方法
    private void lazyInitData(){
        if (!isInitData && isVisibleToUser && isPrepareView) {
            isInitData = true;
            initData();
        }
    }

    //当fragment由可见变为不可见和不可见变为可见时回调

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        this.isVisibleToUser = isVisibleToUser;
        Logger.e("isVisibleToUser");
        lazyInitData();
    }

    //fragment生命周期中onViewCreated之后的方法 在这里调用一次懒加载 避免第一次可见不加载数据
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        lazyInitData();
    }

    @Override
    public void onDestroy() {
        if (isRegisteredEventBus()) {
            EventBusUtils.unregister(this);
        }
        super.onDestroy();
    }

    /**
     * 是否注册事件分发
     *
     * @return true 注册；false 不注册，默认不注册
     */
    protected boolean isRegisteredEventBus() {
        return false;
    }
    /**
     * 接收到分发的事件
     *
     * @param event 事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceiveEvent(EventMessage event) {
    }

    /**
     * 接受到分发的粘性事件
     *
     * @param event 粘性事件
     */
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onReceiveStickyEvent(EventMessage event) {
    }
}
