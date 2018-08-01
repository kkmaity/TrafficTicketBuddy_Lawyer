package com.trafficticketbuddy.lawyer.apis;

import com.trafficticketbuddy.lawyer.model.StateNameMain;
import com.trafficticketbuddy.lawyer.restservice.APIHelper;
import com.trafficticketbuddy.lawyer.restservice.OnApiResponseListener;
import com.trafficticketbuddy.lawyer.restservice.RestService;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiState {
    private OnApiResponseListener listener;
    private Map<String, String> param;

    public ApiState(Map<String, String> param,OnApiResponseListener listener) {
        this.listener = listener;
        this.param=param;
        doWebServiceCall();

    }

    public void doWebServiceCall() {

        Call<StateNameMain> getDepartment = RestService.getInstance().restInterface.getStateName(param);
        APIHelper.enqueueWithRetry(getDepartment, new Callback<StateNameMain>() {
            @Override
            public void onResponse(Call<StateNameMain> call, Response<StateNameMain> response) {
                if(response.code() == 200 && response !=null){

                        listener.onSuccess( response.body());

                }else{
                    listener.onError();
                }
            }

            @Override
            public void onFailure(Call<StateNameMain> call, Throwable t) {
                listener.onError();
            }
        });

      /*  getDepartment.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.code() == 200 && response !=null){
                    try {
                        listener.onSuccess( response.body().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    listener.onError();
                }

            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                    listener.onError();

            }
        });*/
    }
}
