package com.trafficticketbuddy.lawyer;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ShareCompat;
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
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

import com.trafficticketbuddy.lawyer.adapter.MadeBidRecyclerAdapter;
import com.trafficticketbuddy.lawyer.apis.ApiFetchAllCases;
import com.trafficticketbuddy.lawyer.apis.ApiLogin;
import com.trafficticketbuddy.lawyer.apis.ApiUserDetails;
import com.trafficticketbuddy.lawyer.interfaces.ItemClickListner;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.trafficticketbuddy.lawyer.apis.ApiHomeBanner;
import com.trafficticketbuddy.lawyer.fragement.AutoScrollPagerFragment;
import com.trafficticketbuddy.lawyer.fragement.TextFragment;
import com.trafficticketbuddy.lawyer.model.fetchCase.FetchCasesMain;
import com.trafficticketbuddy.lawyer.model.homeBanner.HomeBannerMain;

import com.trafficticketbuddy.lawyer.model.login.LoginMain;
import com.trafficticketbuddy.lawyer.model.login.Response;
import com.trafficticketbuddy.lawyer.restservice.OnApiResponseListener;
import com.trafficticketbuddy.lawyer.utils.Constant;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends BaseActivity {

    private Toolbar toolbar;
    private LinearLayout linMyProfile,linSettings,linFileCase,linMyCase,linMyCase_drawer,linLogout;
    private TextView tvName,tvEmail,txtNoItem;
    private ImageView profile_image;
    private Response mLogin;
    private static int currentPage = 0;
    private static final Integer[] XMEN= {R.drawable.home_banner_1,R.drawable.home_banner_1,R.drawable.home_banner_1,R.drawable.home_banner_1,R.drawable.home_banner_1};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();
    private RecyclerView rvRecycler;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayoutManager mLayoutManager;
    private List<com.trafficticketbuddy.lawyer.model.fetchCase.Response<R>> caseListData = new ArrayList<>();
    private MadeBidRecyclerAdapter mAllCasesRecyclerAdapter;
    private  ViewPager mPager;

    public static List<com.trafficticketbuddy.lawyer.model.homeBanner.Response> bannerList=new ArrayList<>();
    private DrawerLayout drawer;
    private LinearLayout linHome;
    private LinearLayout linShare;
    private boolean doubleBackToExitPressedOnce = false;

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
         drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
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
        linHome=(LinearLayout)findViewById(R.id.linHome);
        linShare=(LinearLayout)findViewById(R.id.linShare);
        tvName=(TextView)findViewById(R.id.tvName);
        txtNoItem=(TextView)findViewById(R.id.txtNoItem);
        tvEmail=(TextView)findViewById(R.id.tvEmail);
        profile_image=(ImageView)findViewById(R.id.profile_image);

        linMyProfile.setOnClickListener(this);
        linSettings.setOnClickListener(this);
        //linFileCase.setOnClickListener(this);
        //linMyCase.setOnClickListener(this);
        linLogout.setOnClickListener(this);
        linMyCase_drawer.setOnClickListener(this);
        linHome.setOnClickListener(this);
        linShare.setOnClickListener(this);
        linLogout.setOnClickListener(this);
        String deviceToken=    preference.getDeviceToken();
        System.out.println("!!!!!!!!!!!"+deviceToken);
        init();
        rvRecycler = (RecyclerView)findViewById(R.id.rvRecycler);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setEnabled(true);
        mLayoutManager= new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvRecycler.setLayoutManager(mLayoutManager);
        setAdapterRecyclerView();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                fetchAllCases();
            }
        });

        if(mLogin.getIsActive().equalsIgnoreCase("0")){
            getUser();
        }
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
            case R.id.linHome:
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                break;
                case R.id.linMyProfile:
                if (drawer.isDrawerOpen(GravityCompat.START)) {
                    drawer.closeDrawer(GravityCompat.START);
                }
                startActivity(new Intent(MainActivity.this,MyProfileActivity.class));
                break;
                case R.id.linSettings:
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                startActivity(new Intent(MainActivity.this,SettingsActivity.class));
                break;
                case R.id.linShare:
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                    ShareCompat.IntentBuilder.from(this)
                            .setType("text/plain")
                            .setChooserTitle("Chooser title")
                            .setText("http://play.google.com/store/apps/details?id=" + this.getPackageName())
                            .startChooser();
                break;
//                case R.id.linFileCase:
//                startActivity(new Intent(MainActivity.this,FileCaseActivity.class));
//                break;
//                case R.id.linMyCase:
//                startActivity(new Intent(MainActivity.this,MyCaseActivity.class));
//                break;
                case R.id.linMyCase_drawer:
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                startActivity(new Intent(MainActivity.this,MyCaseActivity.class));
                break;
                case R.id.linLogout:
                    if (drawer.isDrawerOpen(GravityCompat.START)) {
                        drawer.closeDrawer(GravityCompat.START);
                    }
                    confirmLogoutDialog();
                   /* preference.clearData();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));*/
                break;

        }
    }

    public void setTitle(String title){
        getSupportActionBar().setTitle(title);
    }
    @Override
    public void onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToExitPressedOnce=false;
                }
            }, 2000);
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
        fetchAllCases();
//        if(mAllCasesRecyclerAdapter!=null) {
//            mAllCasesRecyclerAdapter.notifyDataSetChanged();
//        }
        if(mLogin!=null) {
            if (mLogin.getFirstName() != null && mLogin.getLastName() != null) {
                tvName.setText(mLogin.getFirstName() + " " + mLogin.getLastName());
            }
            if (mLogin.getCity() != null) {
                tvEmail.setText(mLogin.getEmail());
            }
            if (mLogin.getProfileImage() != null) {
                if(mLogin.getProfileImage().startsWith("http")){
                    Glide.with(this).load(mLogin.getProfileImage()).into(profile_image);
                }else{
                    if(mLogin.getProfileImage().contains("client_profile_image")){
                        String path = mLogin.getProfileImage();
                        path = path.replace("client_profile_image","lawyer_profile_image");
                        path = Constant.BASE_URL+path;
                        Glide.with(this).load(path).into(profile_image);
                    }else {
                        String path = Constant.BASE_URL + mLogin.getProfileImage();
                        Glide.with(this).load(path).into(profile_image);
                    }
                }
            }

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
                        //caseListData.get(Integer.parseInt(String.valueOf(viewID))).setIs_view("1");
                        Intent mIntent = new Intent(MainActivity.this,CaseDetailsActivity.class);
                        mIntent.putExtra("data",caseListData.get(Integer.parseInt(String.valueOf(viewID))));
                        startActivity(mIntent);
                        break;
                }
            }
        });
        rvRecycler.setAdapter(mAllCasesRecyclerAdapter);
    }


    void fetchAllCases(){
        if (isNetworkConnected()){
            swipeRefreshLayout.setRefreshing(true);
       new ApiFetchAllCases(getParams(), new OnApiResponseListener() {
           @Override
           public <E> void onSuccess(E t) {
               caseListData.clear();
               swipeRefreshLayout.setRefreshing(false);
               FetchCasesMain main=(FetchCasesMain)t;
               if (main.getStatus()){
                   for (com.trafficticketbuddy.lawyer.model.fetchCase.Response<R> mResponse :main.getResponse()) {
                       if(mResponse.getAccepted_lawyer_id().equals("0") && mResponse.getIs_bided().equals("0")){
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
                   mAllCasesRecyclerAdapter.notifyDataSetChanged();
               }

           }

           @Override
           public <E> void onError(E t) {
               swipeRefreshLayout.setRefreshing(false);
           }

           @Override
           public void onError() {
               swipeRefreshLayout.setRefreshing(false);
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
    void confirmLogoutDialog(){
        final Dialog dialog=new Dialog(MainActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_logout);
        dialog.setCancelable(false);
        dialog.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();

            }
        });
        dialog.findViewById(R.id.tvConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
               /* preference.clearData();
                dialog.dismiss();
                Intent in=new Intent(MainActivity.this,LoginActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                startActivity(in);*/
                deleteToken();
            }
        });
        dialog.show();

    }


    public void getUser(){
        if (isNetworkConnected()) {
            new ApiUserDetails(getParam(), new OnApiResponseListener() {
                @Override
                public <E> void onSuccess(E t) {
                    LoginMain mLoginMain = (LoginMain) t;
                    if (mLoginMain.getStatus()) {
                        mLogin = mLoginMain.getResponse();
                        preference.setLoggedInUser(new Gson().toJson(mLoginMain.getResponse()));
                        if(mLogin.getDegreeImages().size()<=0 || mLogin.getCountry().equals("")
                                || mLogin.getCity().equals("") || mLogin.getState().equals("")
                                || mLogin.getDegree().equals("")){
                            ProfileActiveDialog("Your profile information is not complete. Please complete your profile");
                        }
                        else if(mLogin.getIsActive().equalsIgnoreCase("0")){
                            ProfileActiveDialog(mLogin.getAdminMessage());
                        }
                    }
                }

                @Override
                public <E> void onError(E t) {
                }

                @Override
                public void onError() {
                }
            });
        }
    }

    private Map<String,String> getParam(){
        Map<String,String> map=new HashMap<>();
        map.put("user_id",mLogin.getId());
        return map;
    }

    void ProfileActiveDialog(String message){
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.account_active_dialog);
        dialog.setCancelable(false);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tvTitle);
        TextView tvMessage = (TextView) dialog.findViewById(R.id.tvMessage);
        tvMessage.setText(message);
        dialog.findViewById(R.id.tvConfirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,EditProfileActivity.class));
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void deleteToken(){
        if (isNetworkConnected()) {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    showProgressDialog();
                }

                @Override
                protected Void doInBackground(Void... params) {
                    {
                        try {
                            FirebaseInstanceId.getInstance().deleteInstanceId();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Void result) {
                    dismissProgressDialog();
                    preference.clearData();

                    Intent in = new Intent(MainActivity.this, SplashActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

                    startActivity(in);
                }
            }.execute();
        }
    }

}
