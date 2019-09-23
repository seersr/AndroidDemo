package com.example.sb.myandroidtest.utils;

import com.example.sb.myandroidtest.bean.RegisterBean;
import com.example.sb.myandroidtest.bean.TencentList;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by shishaobin on 2019/8/26
 */
public interface ApiService {
    //获取公众号列表
    @GET("wxarticle/chapters/json")
    Observable<TencentList> getTencentList();

    //注册 username,password,repassword
    @POST("user/register")
    @FormUrlEncoded
    Observable<RegisterBean> register(@FieldMap Map<String, String> map);
}
