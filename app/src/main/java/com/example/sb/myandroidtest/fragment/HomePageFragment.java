package com.example.sb.myandroidtest.fragment;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import com.example.sb.myandroidtest.R;
import com.example.sb.myandroidtest.activity.LoginActivity;
import com.example.sb.myandroidtest.adapter.HomeAdapter;
import com.example.sb.myandroidtest.base.BaseFragment;
import com.example.sb.myandroidtest.bean.TencentList;
import com.example.sb.myandroidtest.eventbus.EventCode;
import com.example.sb.myandroidtest.eventbus.EventMessage;
import com.example.sb.myandroidtest.eventbus.UpdateTextEventbus;
import com.example.sb.myandroidtest.utils.ApiService;
import com.example.sb.myandroidtest.utils.EventBusUtils;
import com.example.sb.myandroidtest.utils.JumpUtils;
import com.example.sb.myandroidtest.utils.ToastUtils;
import com.example.sb.myandroidtest.utils.networkUtils.RetrofitUtil;
import com.example.sb.myandroidtest.utils.networkUtils.RetryFunction;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by shishaobin on 2019/8/21
 */
public class HomePageFragment extends BaseFragment{

    private Button bt_login;
    private RecyclerView mrecyclerView;
    private List<TencentList.DataBean> mlist = new ArrayList<>();
    private HomeAdapter homeAdapter;

    @Override
    public int setLayoutID() {
        return R.layout.fragment_homepage;
    }

    @Override
    public void findviewByid(View view) {
        view.findViewById(R.id.bt_hint).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JumpUtils.goNext(mactivity, LoginActivity.class,true);
            }
        });
        view.findViewById(R.id.bt_event).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                EventBus.getDefault().post(new UpdateTextEventbus("我是传过来的值"));
                UpdateTextEventbus updateTextEventbus = new UpdateTextEventbus("卧槽");

                EventBusUtils.post(new EventMessage(EventCode.EVENT_A,updateTextEventbus));
            }
        });
        mrecyclerView = view.findViewById(R.id.recy_home);
    }

    @Override
    public void initData() {
        ApiService apiService = RetrofitUtil.getInstance().create(ApiService.class);
        Observable<TencentList> observable = apiService.getTencentList();
        observable.retryWhen(new RetryFunction(3,3))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TencentList>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TencentList tencentList) {
                        mlist = tencentList.getData();
                       initRecyclerView();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

        /*HashMap map = new HashMap<>();
        map.put("username","15201585264");
        map.put("password","15201585264");
        map.put("repassword","15201585264");
        Call call = apiService.register(map);
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Logger.e("成功");
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Logger.e("失败");
            }
        });*/
    }

    //初始化recyclerview
    private void initRecyclerView(){
        mrecyclerView.setLayoutManager(new LinearLayoutManager(mactivity));
        homeAdapter = new HomeAdapter(mactivity, mlist);
        homeAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ToastUtils.show(mactivity,"click:"+position,0);
                homeAdapter.addItem(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                ToastUtils.show(mactivity,"longClick:"+position,0);
                homeAdapter.deleteItem(position);
            }
        });
        mrecyclerView.setAdapter(homeAdapter);
        mrecyclerView.addItemDecoration(new DividerItemDecoration(mactivity,DividerItemDecoration.VERTICAL));
        mrecyclerView.setItemAnimator(new DefaultItemAnimator());
    }
}
