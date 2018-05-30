package com.trafficticketbuddy.lawyer;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
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
import com.trafficticketbuddy.lawyer.model.login.Response;
import com.trafficticketbuddy.lawyer.utils.Constant;

import com.trafficticketbuddy.lawyer.adapter.MyAdapter;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends BaseActivity {

    private Toolbar toolbar;
    private LinearLayout linMyProfile,linSettings,linFileCase,linMyCase,linMyCase_drawer,linLogout;
    private TextView tvName,tvEmail;
    private ImageView profile_image;
    private Response mLogin;
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static final Integer[] XMEN= {R.drawable.home_banner_1,R.drawable.home_banner_1,R.drawable.home_banner_1,R.drawable.home_banner_1,R.drawable.home_banner_1};
    private ArrayList<Integer> XMENArray = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Home");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        linMyProfile=(LinearLayout)findViewById(R.id.linMyProfile);
        linSettings=(LinearLayout)findViewById(R.id.linSettings);
        linFileCase=(LinearLayout)findViewById(R.id.linFileCase);
        linMyCase=(LinearLayout)findViewById(R.id.linMyCase);
        linLogout=(LinearLayout)findViewById(R.id.linLogout);
        linMyCase_drawer=(LinearLayout)findViewById(R.id.linMyCase_drawer);
        linLogout=(LinearLayout)findViewById(R.id.linLogout);
        tvName=(TextView)findViewById(R.id.tvName);
        tvEmail=(TextView)findViewById(R.id.tvEmail);
        profile_image=(ImageView)findViewById(R.id.profile_image);
        linMyProfile.setOnClickListener(this);
        linSettings.setOnClickListener(this);
        linFileCase.setOnClickListener(this);
        linMyCase.setOnClickListener(this);
        linLogout.setOnClickListener(this);
        linMyCase_drawer.setOnClickListener(this);
        linLogout.setOnClickListener(this);
        String deviceToken=    preference.getDeviceToken();
        System.out.println("!!!!!!!!!!!"+deviceToken);
        init();


    }

    private void init() {
        for(int i=0;i<XMEN.length;i++)
            XMENArray.add(XMEN[i]);

        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(new MyAdapter(MainActivity.this,XMENArray));
        CircleIndicator indicator = (CircleIndicator) findViewById(R.id.indicator);
        indicator.setViewPager(mPager);

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == XMEN.length) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 2500, 2500);
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
                case R.id.linFileCase:
                startActivity(new Intent(MainActivity.this,FileCaseActivity.class));
                break;
                case R.id.linMyCase:
                startActivity(new Intent(MainActivity.this,MyCaseActivity.class));
                break;
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

        Gson gson = new Gson();
        String json = preference.getString("login_user", "");
        mLogin = gson.fromJson(json, com.trafficticketbuddy.lawyer.model.login.Response.class);
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
        }

    }

    /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/


}
