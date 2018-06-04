package com.trafficticketbuddy.lawyer.interfaces;

import com.trafficticketbuddy.lawyer.R;

import java.util.List;

/**
 * Created by User on 26-05-2018.
 */

public interface AcceptedCaseDataLoaded {
    public void acceptedCaseDataLoaded(List<com.trafficticketbuddy.lawyer.model.fetchCase.Response<R>> caseListData);
}
