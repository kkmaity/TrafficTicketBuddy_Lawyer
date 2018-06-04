package com.trafficticketbuddy.lawyer.fragement;


import android.os.Bundle;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trafficticketbuddy.lawyer.MyCaseActivity;
import com.trafficticketbuddy.lawyer.R;
import com.trafficticketbuddy.lawyer.adapter.AcceptedCasesRecyclerAdapter;
import com.trafficticketbuddy.lawyer.interfaces.ItemClickListner;
import com.trafficticketbuddy.lawyer.interfaces.AcceptedCaseDataLoaded;

import java.util.ArrayList;
import java.util.List;

public class AcceptedCaseFragment extends BaseFragment implements AcceptedCaseDataLoaded {
    private RecyclerView rvRecycler;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private List<com.trafficticketbuddy.lawyer.model.fetchCase.Response<R>> caseListData = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_opencase, container, false);
        initialize(view);
        return view;
    }
    private void initialize(View view) {
        rvRecycler = (RecyclerView)view.findViewById(R.id.rvRecycler);
        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setEnabled(false);
        mLayoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvRecycler.setLayoutManager(mLayoutManager);
        MyCaseActivity mActivity = (MyCaseActivity) getActivity();
        mActivity.setMyCaseOpenCaseListener(this);
        setAdapterRecyclerView();
    }
    private void setAdapterRecyclerView() {
        AcceptedCasesRecyclerAdapter mAcceptedCasesRecyclerAdapter =new AcceptedCasesRecyclerAdapter(baseActivity, caseListData, new ItemClickListner() {
            @Override
            public void onItemClick(Object viewID, int position) {
                switch (position){
                    case R.id.linOpenCase:
                       /* Intent mIntent = new Intent(getActivity(),CaseDetailsActivity.class);
                        mIntent.putExtra("data",caseListData.get(Integer.parseInt(String.valueOf(viewID))));
                        startActivity(mIntent);*/
                        break;
                }
            }
        });
        rvRecycler.setAdapter(mAcceptedCasesRecyclerAdapter);
    }

    @Override
    public void acceptedCaseDataLoaded(List<com.trafficticketbuddy.lawyer.model.fetchCase.Response<R>> caseListData) {
        this.caseListData.clear();
        for (com.trafficticketbuddy.lawyer.model.fetchCase.Response<R> mResponse:caseListData) {
            if(!mResponse.getStatus().equalsIgnoreCase("COMPLETED")){
                this.caseListData.add(mResponse);
            }
        }
        setAdapterRecyclerView();
    }
}
