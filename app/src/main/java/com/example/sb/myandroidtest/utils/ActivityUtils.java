package com.example.sb.myandroidtest.utils;

import android.app.Activity;
import android.os.Process;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shishaobin on 2019/8/20
 */
public class ActivityUtils {
    private List<Activity> activityList = new ArrayList<>();
    private static ActivityUtils instance;

    public static synchronized ActivityUtils getInstance(){
        if(instance == null){
            instance = new ActivityUtils();
        }
        return instance;
    }

    //添加activity到容器中
    public void addActivity(Activity activity){
        if(activityList == null){
            activityList = new ArrayList<>();
        }
        activityList.add(activity);
    }

    //移除activity
    public void removeActivity(Activity activity){
        if(activityList != null){
            activityList.remove(activity);
        }
    }

    //遍历所有activity，并退出
    public void exitSystem(){
        for (Activity activity : activityList){
            if(activity != null){
                activity.finish();
            }
        }
        // 退出进程
        Process.killProcess(Process.myPid());
        System.exit(0);
    }
}
