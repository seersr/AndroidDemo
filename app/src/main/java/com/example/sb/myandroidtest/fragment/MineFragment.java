package com.example.sb.myandroidtest.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sb.myandroidtest.R;
import com.example.sb.myandroidtest.adapter.HomeAdapter;
import com.example.sb.myandroidtest.base.BaseFragment;
import com.example.sb.myandroidtest.bean.TencentList;
import com.example.sb.myandroidtest.utils.ApiService;
import com.example.sb.myandroidtest.utils.ToastUtils;
import com.example.sb.myandroidtest.utils.networkUtils.RetrofitUtil;
import com.example.sb.myandroidtest.utils.networkUtils.RetryFunction;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.StickyScrollLinearLayout;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by shishaobin on 2019/8/22
 */
public class MineFragment extends BaseFragment{

    private XRecyclerView mXrecyclerview;
    private List<TencentList.DataBean> mlist = new ArrayList<>();
    private HomeAdapter homeAdapter;

    @Override
    public int setLayoutID() {
        return R.layout.fragment_mine;
    }

    @Override
    public void findviewByid(View view) {

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
                        if(mlist != null){
                            initRecyclerView();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initRecyclerView() {
        mXrecyclerview.setLayoutManager(new LinearLayoutManager(mactivity));
        homeAdapter = new HomeAdapter(mactivity, mlist);
        mXrecyclerview.setAdapter(homeAdapter);

        //设置是否可以上下拉刷新（默认是可以的）
        mXrecyclerview.setPullRefreshEnabled(true);
        mXrecyclerview.setLoadingMoreEnabled(true);
        //设置上拉下拉刷新的样式
        mXrecyclerview.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mXrecyclerview.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
        mXrecyclerview.setArrowImageView(R.mipmap.ic_launcher);
        //设置上下拉的文字提示
        mXrecyclerview.getDefaultFootView().setNoMoreHint("自定义加载完毕提示");
        //设置刷新的回调（onRefrsh为刷新回调，onLoadMore为下拉更新）
        mXrecyclerview.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                ToastUtils.show(mactivity,"刷新",0);

                mXrecyclerview.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                ToastUtils.show(mactivity,"onLoadMore为下拉更新",0);
                mXrecyclerview.loadMoreComplete();
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mXrecyclerview = view.findViewById(R.id.xrecy_mine);


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 释放内存
        if(mXrecyclerview != null){
            mXrecyclerview.destroy();
            mXrecyclerview = null;
        }
    }
}
