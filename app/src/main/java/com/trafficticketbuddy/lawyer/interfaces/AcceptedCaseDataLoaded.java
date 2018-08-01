package com.trafficticketbuddy.lawyer.interfaces;

import com.trafficticketbuddy.lawyer.R;
import com.trafficticketbuddy.lawyer.model.allbid.Response;

import java.util.List;

/**
 * Created by User on 26-05-2018.
 */

public interface AcceptedCaseDataLoaded {
    public void acceptedCaseDataLoaded(List<Response> caseListData);
}
