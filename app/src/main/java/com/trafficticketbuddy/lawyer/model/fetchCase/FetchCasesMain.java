package com.trafficticketbuddy.lawyer.model.fetchCase;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.trafficticketbuddy.lawyer.R;

import java.util.List;

public class FetchCasesMain {
    @SerializedName("status")
    @Expose
    private Boolean status;
    @SerializedName("response")
    @Expose
    private List<Response<R>> response = null;
    @SerializedName("message")
    @Expose
    private String message;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public List<Response<R>> getResponse() {
        return response;
    }

    public void setResponse(List<Response<R>> response) {
        this.response = response;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
