package com.trafficticketbuddy.lawyer;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.widget.TextView;


import com.google.gson.Gson;
import com.trafficticketbuddy.lawyer.adapter.MyCaseAdapter;
import com.trafficticketbuddy.lawyer.apis.ApiGetAllCases;
import com.trafficticketbuddy.lawyer.fragement.AllCasesFragment;
import com.trafficticketbuddy.lawyer.fragement.OpenCaseFragment;
import com.trafficticketbuddy.lawyer.interfaces.MyCaseAllCaseDataLoaded;
import com.trafficticketbuddy.lawyer.interfaces.MyCaseOpenCaseDataLoaded;
import com.trafficticketbuddy.lawyer.model.cases.GetAllCasesMain;
import com.trafficticketbuddy.lawyer.restservice.OnApiResponseListener;

import java.util.HashMap;
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
    private MyCaseAllCaseDataLoaded allcaselistener;
    private MyCaseOpenCaseDataLoaded opencaselistener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycases);
        Gson gson = new Gson();
        String json = preference.getString("login_user", "");
        mLogin = gson.fromJson(json, com.trafficticketbuddy.lawyer.model.login.Response.class);
        viewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mMyCaseAdapter = new MyCaseAdapter(getSupportFragmentManager());

        OpenCaseFragment mOpenCaseFragment= new OpenCaseFragment();
        mMyCaseAdapter.addFragment(mOpenCaseFragment, "Open Cases");

        AllCasesFragment mAllCaseFragment = new AllCasesFragment();
        mMyCaseAdapter.addFragment(mAllCaseFragment, "All Cases");

        viewPager.setAdapter(mMyCaseAdapter);
        tabLayout = (TabLayout) findViewById(R.id.id_tabs);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);

        tvHeading = (TextView)findViewById(R.id.tvHeading);
        tvHeading.setText("MY CASES");
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
        getAllCase();
    }

    private void getAllCase() {
        if (isNetworkConnected()){
            showProgressDialog();
            new ApiGetAllCases(setParam(), new OnApiResponseListener() {
                @Override
                public <E> void onSuccess(E t) {
                    caseListData.clear();
                    dismissProgressDialog();
                    GetAllCasesMain main=(GetAllCasesMain)t;
                    if (main.getStatus()){
                        caseListData.addAll(main.getResponse());
                        allcaselistener.allCaseDataLoaded(caseListData);
                        opencaselistener.openCaseDataLoaded(caseListData);
                    }
                }
                @Override
                public <E> void onError(E t) {
                    dismissProgressDialog();

                }
                @Override
                public void onError() {
                    dismissProgressDialog();

                }
            });
        }
    }

    private Map<String, String> setParam() {
        Map<String,String>map=new HashMap<>();
        map.put("user_id",mLogin.getId());
        return map;
    }


    public void setMyCaseAllCaseListener(MyCaseAllCaseDataLoaded listener) {
        this.allcaselistener = listener;
    }

    public void setMyCaseOpenCaseListener(MyCaseOpenCaseDataLoaded listener) {
        this.opencaselistener = listener;
    }
}