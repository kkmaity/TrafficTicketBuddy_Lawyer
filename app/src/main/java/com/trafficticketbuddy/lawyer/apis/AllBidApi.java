package com.trafficticketbuddy.lawyer.apis;

import com.trafficticketbuddy.lawyer.model.allbid.AllBidMain;
import com.trafficticketbuddy.lawyer.model.city.CityMain;
import com.trafficticketbuddy.lawyer.restservice.APIHelper;
import com.trafficticketbuddy.lawyer.restservice.OnApiResponseListener;
import com.trafficticketbuddy.lawyer.restservice.RestService;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by User on 06-06-2018.
 */

public class AllBidApi {
    private OnApiResponseListener listener;
    private Map<String, String> param;
    public AllBidApi(Map<String, String> param, OnApiResponseListener listener) {
        this.param = param;
        this.listener = listener;
        doWebServiceCall();
    }
    public void doWebServiceCall() {
        Call<AllBidMain> getDepartment = RestService.getInstance().restInterface.getAllBid(param);
        APIHelper.enqueueWithRetry(getDepartment, new Callback<AllBidMain>() {
            @Override
            public void onResponse(Call<AllBidMain> call, Response<AllBidMain> response) {
                if(response.code() == 200 && response !=null){
                    listener.onSuccess( response.body());
                }else{
                    listener.onError();
                }
            }
            @Override
            public void onFailure(Call<AllBidMain> call, Throwable t) {
                listener.onError();
            }
        });
    }
}
