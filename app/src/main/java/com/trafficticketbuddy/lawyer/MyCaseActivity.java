package com.trafficticketbuddy.lawyer;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.trafficticketbuddy.lawyer.adapter.AcceptedCasesRecyclerAdapter;
import com.trafficticketbuddy.lawyer.adapter.MyCaseAdapter;
import com.trafficticketbuddy.lawyer.apis.AllBidApi;
import com.trafficticketbuddy.lawyer.apis.ApiFetchAllCases;
import com.trafficticketbuddy.lawyer.apis.ApiGetAllCases;
import com.trafficticketbuddy.lawyer.fragement.MadeBidFragment;
import com.trafficticketbuddy.lawyer.fragement.AcceptedCaseFragment;
import com.trafficticketbuddy.lawyer.interfaces.ItemClickListner;
import com.trafficticketbuddy.lawyer.interfaces.MadeBidCaseDataLoaded;
import com.trafficticketbuddy.lawyer.interfaces.AcceptedCaseDataLoaded;
import com.trafficticketbuddy.lawyer.model.allbid.AllBidMain;
import com.trafficticketbuddy.lawyer.model.allbid.Response;
import com.trafficticketbuddy.lawyer.model.cases.GetAllCasesMain;
import com.trafficticketbuddy.lawyer.model.fetchCase.FetchCasesMain;
import com.trafficticketbuddy.lawyer.restservice.OnApiResponseListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
com.appsbee.sad.ui.dashboard
10/11/16
sad
*/
public class MyCaseActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private MyCaseAdapter mMyCaseAdapter;
    private final int ACTION_USAGE_ACCESS_SETTINGS = 101;
    private final int NOTIFICATION_ACCESS = 102;
    private TextView tvHeading;
    private com.trafficticketbuddy.lawyer.model.login.Response mLogin;
    private MadeBidCaseDataLoaded allcaselistener;
    private AcceptedCaseDataLoaded opencaselistener;
    private ImageView back;
    public  static final List<Response> caseListData=new ArrayList<>();
    private RecyclerView rvRecycler;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private TextView txtNoItem;
    private AcceptedCasesRecyclerAdapter mAcceptedCasesRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycases);
        Gson gson = new Gson();
        String json = preference.getString("login_user", "");
        mLogin = gson.fromJson(json, com.trafficticketbuddy.lawyer.model.login.Response.class);
//        if(mLogin==null){
//            Intent in=new Intent(this,SplashActivity.class);
//            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//            in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            startActivity(in);
//            finish();
//        }
        //viewPager = (ViewPager) findViewById(R.id.id_viewpager);
        rvRecycler = (RecyclerView) findViewById(R.id.rvRecycler);
        txtNoItem = (TextView) findViewById(R.id.txtNoItem);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setEnabled(true);
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvRecycler.setLayoutManager(mLayoutManager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

//        mMyCaseAdapter = new MyCaseAdapter(getSupportFragmentManager());
//        AcceptedCaseFragment mOpenCaseFragment= new AcceptedCaseFragment();
//        mMyCaseAdapter.addFragment(mOpenCaseFragment, "Accepted");
//        MadeBidFragment mAllCaseFragment = new MadeBidFragment();
//        mMyCaseAdapter.addFragment(mAllCaseFragment, "Made Bid");
//        viewPager.setAdapter(mMyCaseAdapter);
//        tabLayout = (TabLayout) findViewById(R.id.id_tabs);
//        tabLayout.setupWithViewPager(viewPager);
//        viewPager.setCurrentItem(0);
//        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

        setAdapterRecyclerView();
        onRefresh();
        swipeRefreshLayout.setOnRefreshListener(this);
    }






    public void setMyCaseAllCaseListener(MadeBidCaseDataLoaded listener) {
        this.allcaselistener = listener;
    }

    public void setMyCaseOpenCaseListener(AcceptedCaseDataLoaded listener) {
        this.opencaselistener = listener;
    }

    void fetchAllBids(){
        if (isNetworkConnected()){
            //showProgressDialog();
            new AllBidApi(getParams(), new OnApiResponseListener() {
                @Override
                public <E> void onSuccess(E t) {
                    caseListData.clear();
                    //dismissProgressDialog();
                    swipeRefreshLayout.setRefreshing(false);
                    AllBidMain main=(AllBidMain)t;
                    if (main.getStatus()){
                        for (Response mResponse:main.getResponse()) {
                            if(mResponse.getCaseStatus().equalsIgnoreCase("ACCEPTED")){
                                caseListData.add(mResponse);
                            }
                        }
                        if(caseListData.size()<=0) {
                            rvRecycler.setVisibility(View.GONE);
                            txtNoItem.setVisibility(View.VISIBLE);
                        }else{
                            rvRecycler.setVisibility(View.VISIBLE);
                            txtNoItem.setVisibility(View.GONE);
                        }
                        mAcceptedCasesRecyclerAdapter.notifyDataSetChanged();
                        //caseListData.addAll(main.getResponse());
                        //allcaselistener.madeBidCaseDataLoaded(caseListData);
                        //opencaselistener.acceptedCaseDataLoaded(caseListData);
                    }else{
                        rvRecycler.setVisibility(View.GONE);
                        txtNoItem.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public <E> void onError(E t) {
                    swipeRefreshLayout.setRefreshing(false);
                    rvRecycler.setVisibility(View.GONE);
                    txtNoItem.setVisibility(View.VISIBLE);
                    //dismissProgressDialog();
                    //caseListData.clear();
                    //allcaselistener.madeBidCaseDataLoaded(caseListData);
                    //opencaselistener.acceptedCaseDataLoaded(caseListData);
                }

                @Override
                public void onError() {
                    swipeRefreshLayout.setRefreshing(false);
                    rvRecycler.setVisibility(View.GONE);
                    txtNoItem.setVisibility(View.VISIBLE);
                    //dismissProgressDialog();
                    //caseListData.clear();
                    //allcaselistener.madeBidCaseDataLoaded(caseListData);
                    //opencaselistener.acceptedCaseDataLoaded(caseListData);
                }
            });
        }
    }


    private void setAdapterRecyclerView() {
         mAcceptedCasesRecyclerAdapter =new AcceptedCasesRecyclerAdapter(this, caseListData, new ItemClickListner() {
            @Override
            public void onItemClick(Object viewID, int position) {
                switch (position){
                    case R.id.linOpenCase:

                        break;
                }
            }
        });
        rvRecycler.setAdapter(mAcceptedCasesRecyclerAdapter);
    }

    private Map<String, String> getParams() {
        Map<String,String> map=new HashMap<>();
        map.put("lawyer_id",mLogin.getId());
        return map;
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){

        }
    }

    @Override
    public void onRefresh() {
        swipeRefreshLayout.setRefreshing(true);
        fetchAllBids();
    }
}