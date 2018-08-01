package com.trafficticketbuddy.lawyer.apis;

import com.trafficticketbuddy.lawyer.model.login.LoginMain;
import com.trafficticketbuddy.lawyer.restservice.APIHelper;
import com.trafficticketbuddy.lawyer.restservice.OnApiResponseListener;
import com.trafficticketbuddy.lawyer.restservice.RestService;

import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiEditProfile {
    private OnApiResponseListener listener;
    private Map<String, RequestBody> param;
    private Map<String, MultipartBody.Part> imageparam;

    public ApiEditProfile(Map<String, RequestBody> param,Map<String, MultipartBody.Part> imageparam, OnApiResponseListener listener) {
        this.param = param;
        this.imageparam = imageparam;
        this.listener = listener;
        doWebServiceCall();

    }

    public void doWebServiceCall() {

        Call<LoginMain> data = RestService.getInstance().restInterface.editprofile(param,imageparam.get("profile_image"),
                imageparam.get("degree_image_1"),imageparam.get("degree_image_2"),imageparam.get("degree_image_3"));
        APIHelper.enqueueWithRetry(data, new Callback<LoginMain>() {
            @Override
            public void onResponse(Call<LoginMain> call, Response<LoginMain> response) {
                if(response.code() == 200 && response !=null){
                    listener.onSuccess(response.body());
                }else{
                    listener.onError();
                }
            }
            @Override
            public void onFailure(Call<LoginMain> call, Throwable t) {
                listener.onError();
            }
        });

    }
}
