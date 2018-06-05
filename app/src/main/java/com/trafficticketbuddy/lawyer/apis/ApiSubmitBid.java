package com.trafficticketbuddy.lawyer.apis;

import com.trafficticketbuddy.lawyer.model.placebid.PlaceBidMain;
import com.trafficticketbuddy.lawyer.restservice.APIHelper;
import com.trafficticketbuddy.lawyer.restservice.OnApiResponseListener;
import com.trafficticketbuddy.lawyer.restservice.RestService;

import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 05-06-2018.
 */

public class ApiSubmitBid {
    private OnApiResponseListener listener;
    private Map<String, String> param;

    public ApiSubmitBid(Map<String, String> param, OnApiResponseListener listener) {
        this.param = param;
        this.listener = listener;
        doWebServiceCall();

    }

    public void doWebServiceCall() {

        Call<PlaceBidMain> data = RestService.getInstance().restInterface.submitBid(param);
        APIHelper.enqueueWithRetry(data, new Callback<PlaceBidMain>() {
            @Override
            public void onResponse(Call<PlaceBidMain> call, Response<PlaceBidMain> response) {
                if(response.code() == 200 && response !=null){
                    listener.onSuccess(response.body());
                }else{
                    listener.onError();
                }
            }
            @Override
            public void onFailure(Call<PlaceBidMain> call, Throwable t) {
                listener.onError();
            }
        });

    }
}
