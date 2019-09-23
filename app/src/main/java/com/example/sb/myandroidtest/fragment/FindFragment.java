package com.example.sb.myandroidtest.fragment;

import android.view.View;
import android.widget.TextView;

import com.example.sb.myandroidtest.R;
import com.example.sb.myandroidtest.base.BaseFragment;
import com.example.sb.myandroidtest.eventbus.EventCode;
import com.example.sb.myandroidtest.eventbus.EventMessage;
import com.example.sb.myandroidtest.eventbus.UpdateTextEventbus;


/**
 * Created by shishaobin on 2019/8/22
 */
public class FindFragment extends BaseFragment{

    private TextView mtv_find;

    @Override
    public int setLayoutID() {
        return R.layout.fragment_find;
    }

    @Override
    public void findviewByid(View view) {
        mtv_find = view.findViewById(R.id.tv_find);
    }

    @Override
    public void initData() {

    }

    @Override
    protected boolean isRegisteredEventBus() {
        return true;
    }

    @Override
    public void onReceiveEvent(EventMessage event) {
        if(EventCode.EVENT_A == event.getCode()){
            UpdateTextEventbus data = (UpdateTextEventbus) event.getData();
            mtv_find.setText(data.getName());
        }
    }
}
