package com.trafficticketbuddy.lawyer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import com.trafficticketbuddy.lawyer.adapter.MadeBidRecyclerAdapter;
import com.trafficticketbuddy.lawyer.apis.ApiFetchAllCases;
import com.trafficticketbuddy.lawyer.apis.ApiGetAllCases;
import com.trafficticketbuddy.lawyer.interfaces.ItemClickListner;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.trafficticketbuddy.lawyer.apis.ApiHomeBanner;
import com.trafficticketbuddy.lawyer.fragement.AutoScrollPagerFragment;
import com.trafficticketbuddy.lawyer.fragement.TextFragment;
import com.trafficticketbuddy.lawyer.model.cases.GetAllCasesMain;
import com.trafficticketbuddy.lawyer.model.fetchCase.FetchCasesMain;
import com.trafficticketbuddy.lawyer.model.homeBanner.HomeBannerMain;

import com.trafficticketbuddy.lawyer.model.login.Response;
import com.trafficticketbuddy.lawyer.restservice.OnApiResponseListener;
import com.trafficticketbuddy.lawyer.utils.Constant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {

    private Toolbar toolbar;
    private LinearLayout linMyProfile,linSettings,linFileCase,linMyCase,linMyCase_drawer,linLogout;
    private TextView tvName,tvEmail;
    private ImageView profile_image;
    private Response mLogin;
    private static int currentPage = 0;
    private static final Integer[] XMEN= {R.drawable.home_banner_1,R.drawable.home_banner_1,R.drawable.home_banner_1,R.drawable.home_banner_1,R.drawable.home_banner_1};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    private RecyclerView rvRecycler;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private List<com.trafficticketbuddy.lawyer.model.fetchCase.Response> caseListData = new ArrayList<>();
    private MadeBidRecyclerAdapter mAllCasesRecyclerAdapter;
    private  ViewPager mPager;

    public static List<com.trafficticketbuddy.lawyer.model.homeBanner.Response> bannerList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Gson gson = new Gson();
        String json = preference.getString("login_user", "");
        mLogin = gson.fromJson(json, com.trafficticketbuddy.lawyer.model.login.Response.class);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Home");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        mPager = (ViewPager) findViewById(R.id.pager);
        linMyProfile=(LinearLayout)findViewById(R.id.linMyProfile);
        linSettings=(LinearLayout)findViewById(R.id.linSettings);
       // linFileCase=(LinearLayout)findViewById(R.id.linFileCase);
        //linMyCase=(LinearLayout)findViewById(R.id.linMyCase);
        linLogout=(LinearLayout)findViewById(R.id.linLogout);
        linMyCase_drawer=(LinearLayout)findViewById(R.id.linMyCase_drawer);
        linLogout=(LinearLayout)findViewById(R.id.linLogout);
        tvName=(TextView)findViewById(R.id.tvName);
        tvEmail=(TextView)findViewById(R.id.tvEmail);
        profile_image=(ImageView)findViewById(R.id.profile_image);
        linMyProfile.setOnClickListener(this);
        linSettings.setOnClickListener(this);
        //linFileCase.setOnClickListener(this);
        //linMyCase.setOnClickListener(this);
        linLogout.setOnClickListener(this);
        linMyCase_drawer.setOnClickListener(this);
        linLogout.setOnClickListener(this);
        String deviceToken=    preference.getDeviceToken();
        System.out.println("!!!!!!!!!!!"+deviceToken);
        init();

        rvRecycler = (RecyclerView)findViewById(R.id.rvRecycler);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setEnabled(false);
        mLayoutManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvRecycler.setLayoutManager(mLayoutManager);
        setAdapterRecyclerView();
        fetchAllCases();
    }

    private void init() {
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(configuration);
        getallBanner();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        closeDrawer();
        switch (view.getId()){
            case R.id.linMyProfile:
                startActivity(new Intent(MainActivity.this,MyProfileActivity.class));
                break;
                case R.id.linSettings:
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                break;
//                case R.id.linFileCase:
//                startActivity(new Intent(MainActivity.this,FileCaseActivity.class));
//                break;
//                case R.id.linMyCase:
//                startActivity(new Intent(MainActivity.this,MyCaseActivity.class));
//                break;
                case R.id.linMyCase_drawer:
                startActivity(new Intent(MainActivity.this,MyCaseActivity.class));
                break;
                case R.id.linLogout:
                    preference.clearData();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                break;

        }
    }

    public void setTitle(String title){
        getSupportActionBar().setTitle(title);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    private void closeDrawer(){
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();


        if(mLogin!=null) {
            if (mLogin.getFirstName() != null && mLogin.getLastName() != null) {
                tvName.setText(mLogin.getFirstName() + " " + mLogin.getLastName());
            }
            if (mLogin.getCity() != null) {
                tvEmail.setText(mLogin.getEmail());
            }
            if (mLogin.getProfileImage() != null) {
                String path = Constant.BASE_URL + mLogin.getProfileImage();
                Glide.with(this).load(path).into(profile_image);
            }
            //getAllCase();
        }


    }

    /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/
    private void setAdapterRecyclerView() {
       /* caseListData.add(new com.trafficticketbuddy.lawyer.model.cases.Response());
        caseListData.add(new com.trafficticketbuddy.lawyer.model.cases.Response());
        caseListData.add(new com.trafficticketbuddy.lawyer.model.cases.Response());*/
        mAllCasesRecyclerAdapter = new MadeBidRecyclerAdapter(this, caseListData, new ItemClickListner() {
            @Override
            public void onItemClick(Object viewID, int position) {
                switch (position) {
                    case R.id.linAllCase:

                        break;
                }
            }
        });
        rvRecycler.setAdapter(mAllCasesRecyclerAdapter);
    }


    void fetchAllCases(){
        if (isNetworkConnected()){


            showProgressDialog();
       new ApiFetchAllCases(getParams(), new OnApiResponseListener() {
           @Override
           public <E> void onSuccess(E t) {
               caseListData.clear();
               dismissProgressDialog();
               FetchCasesMain main=(FetchCasesMain)t;
               if (main.getStatus()){
                   caseListData.addAll(main.getResponse());
                   mAllCasesRecyclerAdapter.notifyDataSetChanged();

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

    private Map<String, String> getParams() {
        Map<String,String> map=new HashMap<>();
        map.put("lawyer_id",mLogin.getId());
        return map;
    }

    void getallBanner(){
        if (isNetworkConnected()){
            showProgressDialog();
            new ApiHomeBanner(new OnApiResponseListener() {
                @Override
                public <E> void onSuccess(E t) {
                    bannerList.clear();
                    dismissProgressDialog();
                    HomeBannerMain main=(HomeBannerMain)t;
                    if (main.getStatus()){
                        bannerList.addAll(main.getResponse());
                        mPager.setOffscreenPageLimit(bannerList.size());
                        mPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
                            @Override
                            public Fragment getItem(int i) {
                                if (i == 0) {
                                    return new AutoScrollPagerFragment();
                                }
                                return TextFragment.newInstance("Fragment " + i);
                            }

                            @Override
                            public int getCount() {
                                return bannerList.size();
                            }
                        });
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
        map.put("lawyer_id",mLogin.getId());
        return map;
    }

}
