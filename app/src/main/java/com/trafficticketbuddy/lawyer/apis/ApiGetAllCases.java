package com.trafficticketbuddy.lawyer.apis;

import com.trafficticketbuddy.lawyer.model.cases.GetAllCasesMain;
import com.trafficticketbuddy.lawyer.restservice.APIHelper;
import com.trafficticketbuddy.lawyer.restservice.OnApiResponseListener;
import com.trafficticketbuddy.lawyer.restservice.RestService;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiGetAllCases {
    private OnApiResponseListener listener;
    private Map<String, String> param;

    public ApiGetAllCases(Map<String, String> param, OnApiResponseListener listener) {
        this.param = param;
        this.listener = listener;
        doWebServiceCall();

    }

    public void doWebServiceCall() {

        Call<GetAllCasesMain> data = RestService.getInstance().restInterface.getAllCases(param);
        APIHelper.enqueueWithRetry(data, new Callback<GetAllCasesMain>() {
            @Override
            public void onResponse(Call<GetAllCasesMain> call, Response<GetAllCasesMain> response) {
                if(response.code() == 200 && response !=null){
                    listener.onSuccess(response.body());
                }else{
                    listener.onError();
                }
            }
            @Override
            public void onFailure(Call<GetAllCasesMain> call, Throwable t) {
                listener.onError();
            }
        });

    }
}
