package com.trafficticketbuddy.lawyer.apis;

import com.trafficticketbuddy.lawyer.model.fetchCase.FetchCasesMain;
import com.trafficticketbuddy.lawyer.model.login.LoginMain;
import com.trafficticketbuddy.lawyer.restservice.APIHelper;
import com.trafficticketbuddy.lawyer.restservice.OnApiResponseListener;
import com.trafficticketbuddy.lawyer.restservice.RestService;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiFetchAllCases {
    private OnApiResponseListener listener;
    private Map<String, String> param;

    public ApiFetchAllCases(Map<String, String> param, OnApiResponseListener listener) {
        this.param = param;
        this.listener = listener;
        doWebServiceCall();

    }

    public void doWebServiceCall() {

        Call<FetchCasesMain> data = RestService.getInstance().restInterface.fetchCasesOflawyer(param);
        APIHelper.enqueueWithRetry(data, new Callback<FetchCasesMain>() {
            @Override
            public void onResponse(Call<FetchCasesMain> call, Response<FetchCasesMain> response) {
                if(response.code() == 200 && response !=null){
                    listener.onSuccess(response.body());
                }else{
                    listener.onError();
                }
            }
            @Override
            public void onFailure(Call<FetchCasesMain> call, Throwable t) {
                listener.onError();
            }
        });

    }
}
