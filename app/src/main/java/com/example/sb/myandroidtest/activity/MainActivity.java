package com.example.sb.myandroidtest.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.sb.myandroidtest.R;
import com.example.sb.myandroidtest.base.BaseActivity;
import com.example.sb.myandroidtest.eventbus.EventMessage;
import com.example.sb.myandroidtest.fragment.FindFragment;
import com.example.sb.myandroidtest.fragment.HomePageFragment;
import com.example.sb.myandroidtest.fragment.MineFragment;

import org.greenrobot.eventbus.EventBus;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    //当前的Fragment
    private Fragment mCurFragment = new Fragment();

    //初始化其他fragemnt
    private HomePageFragment mHomePageFragment = new HomePageFragment();
    private FindFragment mFindFragment = new FindFragment();
    private MineFragment mMineFragment = new MineFragment();

    private LinearLayout mLlHomepage;
    private LinearLayout mLlFind;
    private LinearLayout mLlMine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();

        //Fragment重叠问题，由于Fragment被系统杀掉，并重新初始化时再次将fragment加入activity
        if (savedInstanceState == null) {
            switchFragment(mHomePageFragment);
        }
    }

    private void switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {//如果要显示的targetFragment没有添加过
            transaction
                    .hide(mCurFragment)//隐藏当前Fragment
                    .add(R.id.fr_layout, targetFragment, targetFragment.getClass().getName())//添加targetFragment
                    .addToBackStack(targetFragment.getClass().getName())
                    .commit();
        } else {//如果要显示的targetFragment已经添加过
            transaction
                    .hide(mCurFragment)//隐藏当前Fragment
                    .show(targetFragment)//显示targetFragment
                    .commit();
        }
        //更新当前Fragment为targetFragment
        mCurFragment = targetFragment;
    }

    private void initView() {
        mLlHomepage = (LinearLayout) findViewById(R.id.ll_homepage);
        mLlHomepage.setOnClickListener(this);
        mLlFind = (LinearLayout) findViewById(R.id.ll_find);
        mLlFind.setOnClickListener(this);
        mLlMine = (LinearLayout) findViewById(R.id.ll_mine);
        mLlMine.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.ll_homepage:
                switchFragment(mHomePageFragment);
                break;
            case R.id.ll_find:
                switchFragment(mFindFragment);
                break;
            case R.id.ll_mine:
                switchFragment(mMineFragment);
                break;
        }
    }
}
