package com.trafficticketbuddy.lawyer;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.trafficticketbuddy.lawyer.model.login.Response;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SplashActivity extends BaseActivity{
    private String deviceToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

            preference.setDeviceToken(refreshedToken);



        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.trafficticketbuddy.lawyer", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }


        callNewScreen();
    }

    @Override
    protected void onStart() {
        super.onStart();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

            preference.setDeviceToken(refreshedToken);

    }

    private void callNewScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                if(preference.getDeviceToken().isEmpty()){
                    preference.setDeviceToken(refreshedToken);
                }
                Gson gson = new Gson();
                String json = preference.getString("login_user", "");
                Response mLoginMain = gson.fromJson(json, Response.class);
                if(mLoginMain!=null) {
                    if(mLoginMain.getIsEmailVerified().equalsIgnoreCase("0")){
                        startActivity(new Intent(SplashActivity.this,LoginActivity.class));
                        finish();
                    }else{
                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
                        finish();
                    }
                }else{
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }

            }
        }, 5000);
    }

    @Override
    protected void onResume() {
        super.onResume();
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

            preference.setDeviceToken(refreshedToken);

    }
}
