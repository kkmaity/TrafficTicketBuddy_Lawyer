package com.trafficticketbuddy.lawyer.fragement;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trafficticketbuddy.lawyer.MyBidActivity;
import com.trafficticketbuddy.lawyer.MyCaseActivity;
import com.trafficticketbuddy.lawyer.R;
import com.trafficticketbuddy.lawyer.adapter.AcceptedCasesRecyclerAdapter;
import com.trafficticketbuddy.lawyer.adapter.MadeBidRecyclerAdapter;
import com.trafficticketbuddy.lawyer.interfaces.ItemClickListner;
import com.trafficticketbuddy.lawyer.interfaces.MadeBidCaseDataLoaded;
import com.trafficticketbuddy.lawyer.model.allbid.Response;

import java.util.ArrayList;
import java.util.List;

public class MadeBidFragment extends BaseFragment implements MadeBidCaseDataLoaded {
    private RecyclerView rvRecycler;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private AcceptedCasesRecyclerAdapter mAcceptedCasesRecyclerAdapter;
    private List<Response> caseListData=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_allcases, container, false);
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
        mActivity.setMyCaseAllCaseListener(this);
        setAdapterRecyclerView();

    }
    private void setAdapterRecyclerView() {
        mAcceptedCasesRecyclerAdapter=new AcceptedCasesRecyclerAdapter(baseActivity, caseListData, new ItemClickListner() {
            @Override
            public void onItemClick(Object viewID, int position) {
                switch (position){
                    case R.id.linAllCase:
                        startActivity(new Intent(getActivity(),MyBidActivity.class));
                        break;
                }
            }
        });
        rvRecycler.setAdapter(mAcceptedCasesRecyclerAdapter);
    }

    @Override
    public void madeBidCaseDataLoaded(List<Response> caseListData) {
        this.caseListData.clear();
        for (Response mResponse:caseListData) {
            if(!mResponse.getCaseStatus().equalsIgnoreCase("ACCEPTED")){
                this.caseListData.add(mResponse);
            }
        }
        setAdapterRecyclerView();
    }
}
