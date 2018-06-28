package com.trafficticketbuddy.lawyer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.google.gson.Gson;
import com.trafficticketbuddy.lawyer.adapter.MyCaseAdapter;
import com.trafficticketbuddy.lawyer.apis.AllBidApi;
import com.trafficticketbuddy.lawyer.apis.ApiFetchAllCases;
import com.trafficticketbuddy.lawyer.apis.ApiGetAllCases;
import com.trafficticketbuddy.lawyer.fragement.MadeBidFragment;
import com.trafficticketbuddy.lawyer.fragement.AcceptedCaseFragment;
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
public class MyCaseActivity extends BaseActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycases);
        Gson gson = new Gson();
        String json = preference.getString("login_user", "");
        mLogin = gson.fromJson(json, com.trafficticketbuddy.lawyer.model.login.Response.class);
        viewPager = (ViewPager) findViewById(R.id.id_viewpager);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        mMyCaseAdapter = new MyCaseAdapter(getSupportFragmentManager());
        AcceptedCaseFragment mOpenCaseFragment= new AcceptedCaseFragment();
        mMyCaseAdapter.addFragment(mOpenCaseFragment, "Accepted");
        MadeBidFragment mAllCaseFragment = new MadeBidFragment();
        mMyCaseAdapter.addFragment(mAllCaseFragment, "Made Bid");
        viewPager.setAdapter(mMyCaseAdapter);
        tabLayout = (TabLayout) findViewById(R.id.id_tabs);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        fetchAllBids();
    }





    public void setMyCaseAllCaseListener(MadeBidCaseDataLoaded listener) {
        this.allcaselistener = listener;
    }

    public void setMyCaseOpenCaseListener(AcceptedCaseDataLoaded listener) {
        this.opencaselistener = listener;
    }

    void fetchAllBids(){
        if (isNetworkConnected()){
            showProgressDialog();
            new AllBidApi(getParams(), new OnApiResponseListener() {
                @Override
                public <E> void onSuccess(E t) {
                    caseListData.clear();
                    dismissProgressDialog();
                    AllBidMain main=(AllBidMain)t;
                    if (main.getStatus()){
                        caseListData.addAll(main.getResponse());
                        allcaselistener.madeBidCaseDataLoaded(caseListData);
                        opencaselistener.acceptedCaseDataLoaded(caseListData);
                    }
                }

                @Override
                public <E> void onError(E t) {
                    dismissProgressDialog();
                    caseListData.clear();
                    allcaselistener.madeBidCaseDataLoaded(caseListData);
                    opencaselistener.acceptedCaseDataLoaded(caseListData);
                }

                @Override
                public void onError() {
                    dismissProgressDialog();
                    caseListData.clear();
                    allcaselistener.madeBidCaseDataLoaded(caseListData);
                    opencaselistener.acceptedCaseDataLoaded(caseListData);
                }
            });
        }
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
}