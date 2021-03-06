package com.trafficticketbuddy.lawyer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.facebook.GraphResponse;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.trafficticketbuddy.lawyer.apis.ApiFaceBookLogin;
import com.trafficticketbuddy.lawyer.apis.ApiGoogleLogin;
import com.trafficticketbuddy.lawyer.apis.ApiLogin;
import com.trafficticketbuddy.lawyer.interfaces.FbLoginCompleted;
import com.trafficticketbuddy.lawyer.interfaces.GoogleLoginCompleted;
import com.trafficticketbuddy.lawyer.model.login.LoginMain;
import com.trafficticketbuddy.lawyer.restservice.OnApiResponseListener;
import com.trafficticketbuddy.lawyer.utils.Constant;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {
    private CardView cardLogin,cvGoogleLogin,cvFbLogin;
    private TextView tvForgetPassword;
    private EditText etEmail,etPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

            preference.setDeviceToken(refreshedToken);

        String refreshedToken1 = FirebaseInstanceId.getInstance().getToken();

            preference.setDeviceToken(refreshedToken1);


        cardLogin=(CardView)findViewById(R.id.cardSignUp);
        cvGoogleLogin=(CardView)findViewById(R.id.cvGoogleLogin);
        cvFbLogin=(CardView)findViewById(R.id.cvFbLogin);
        tvForgetPassword=(TextView)findViewById(R.id.tvForgetPassword);
        etEmail=(EditText)findViewById(R.id.etEmail);
        etPassword=(EditText)findViewById(R.id.etPassword);
        tvForgetPassword.setOnClickListener(this);
        cvGoogleLogin.setOnClickListener(this);
        cvFbLogin.setOnClickListener(this);
        cardLogin.setOnClickListener(this);
    String deviceToken=    preference.getDeviceToken();
    System.out.println("!!!!!!!!!!!"+deviceToken);

        findViewById(R.id.txtRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this,RegistrationActivity.class));
            }
        });

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.tvForgetPassword:
                startActivity(new Intent(LoginActivity.this,ForgetPasswordActivity.class));
                break;
            case R.id.cvGoogleLogin:
                signIn(new GoogleLoginCompleted() {
                    @Override
                    public void onGoogleCompleted(GoogleSignInAccount account) {
                        String email = account.getEmail();
                        String first_name = account.getDisplayName();
                        String last_name = "";
                        String id = account.getId();
                        String pic_url="";
                        if(account.getPhotoUrl()!=null) {
                            pic_url = account.getPhotoUrl().getPath();
                        }
                        if(!email.isEmpty()) {
                            doGoogleLoginApi(first_name, last_name, email, "", "", pic_url, id, "", "", "");
                        }else{
                            showDialog("No email address found");
                        }
                    }
                });
                break;
            case R.id.cvFbLogin:
                fbSignInClick(new FbLoginCompleted(){
                    @Override
                    public void onFaceBookCompleted(JSONObject object, GraphResponse response) {
                        try {
                            String email = object.getString("email");
                            String first_name = object.getString("first_name");
                            String last_name = object.getString("last_name");
                            String id = object.getString("id");
                            String pic_url="https://graph.facebook.com/" +id+ "/picture?type=large";
                            if(!email.isEmpty()) {
                                doFBLoginApi(first_name, last_name, email, "", "", pic_url, id, "", "", "");
                            }else {
                                showDialog("No email address found");
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case R.id.cardSignUp:
                isValidate();
                break;
        }
    }

    private void doLoginApi() {
        showProgressDialog();
        new ApiLogin(getParam(), new OnApiResponseListener() {
            @Override
            public <E> void onSuccess(E t) {
                {
                    dismissProgressDialog();
                    LoginMain mLoginMain = (LoginMain) t;
                    if(mLoginMain.getStatus()){
                        preference.setUserId(mLoginMain.getResponse().getId());
                        preference.setLoggedInUser(new Gson().toJson(mLoginMain.getResponse()));
                        if(mLoginMain.getResponse().getPhone().isEmpty() || mLoginMain.getResponse().getCountry().isEmpty()
                                || mLoginMain.getResponse().getState().isEmpty() || mLoginMain.getResponse().getCity().isEmpty()){
                            startActivity(new Intent(LoginActivity.this,EditProfileActivity.class));
                        }else if(mLoginMain.getResponse().getIsEmailVerified().equalsIgnoreCase("0")){
                            startActivity(new Intent(LoginActivity.this,EmailOTPActivity.class));
                        }else{
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        }
                    }else{
                        showDialog(mLoginMain.getMessage());
                    }

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

    private void doFBLoginApi(String first_name,String last_name,String email,String phone,
                              String gender,String image,String facebook_id,String state,
                              String country,String city) {
        showProgressDialog();
        new ApiFaceBookLogin(getFBParam(first_name,last_name,email,phone,
                gender,image,facebook_id,state,country,city), new OnApiResponseListener() {
            @Override
            public <E> void onSuccess(E t) {
                dismissProgressDialog();
                {LoginMain mLoginMain = (LoginMain)t;
                    if(mLoginMain.getStatus()){
                        preference.setUserId(mLoginMain.getResponse().getId());
                        preference.setLoggedInUser(new Gson().toJson(mLoginMain.getResponse()));
                        if(mLoginMain.getResponse().getPhone().isEmpty() || mLoginMain.getResponse().getCountry().isEmpty()
                                || mLoginMain.getResponse().getState().isEmpty() || mLoginMain.getResponse().getCity().isEmpty()){
                            startActivity(new Intent(LoginActivity.this,EditProfileActivity.class));
                        }else if(mLoginMain.getResponse().getIsEmailVerified().equalsIgnoreCase("0")){
                            startActivity(new Intent(LoginActivity.this,EmailOTPActivity.class));
                        }else{
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        }
                    }else{
                        showDialog(mLoginMain.getMessage());
                    }
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


    private void doGoogleLoginApi(String first_name,String last_name,String email,String phone,
                                  String gender,String image,String google_id,String state,
                                  String country,String city) {
        showProgressDialog();
        new ApiGoogleLogin(getGoogleParam(first_name,last_name,email,phone,
                gender,image,google_id,state,country,city), new OnApiResponseListener() {
            @Override
            public <E> void onSuccess(E t) {
                {
                    dismissProgressDialog();
                    LoginMain mLoginMain = (LoginMain)t;
                    if(mLoginMain.getStatus()){
                        preference.setUserId(mLoginMain.getResponse().getId());
                        preference.setLoggedInUser(new Gson().toJson(mLoginMain.getResponse()));
                        if(mLoginMain.getResponse().getPhone().isEmpty() || mLoginMain.getResponse().getCountry().isEmpty()
                                || mLoginMain.getResponse().getState().isEmpty() || mLoginMain.getResponse().getCity().isEmpty()){
                            startActivity(new Intent(LoginActivity.this,EditProfileActivity.class));
                        }else if(mLoginMain.getResponse().getIsEmailVerified().equalsIgnoreCase("0")){
                            startActivity(new Intent(LoginActivity.this,EmailOTPActivity.class));
                        }else{
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                        }
                    }else{
                        showDialog(mLoginMain.getMessage());
                    }
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

    private void isValidate(){
        if(etEmail.getText().toString().isEmpty()){
            etEmail.setError("Please enter email id");
        }else if(etPassword.getText().toString().isEmpty()){
            etPassword.setError("Please enter password");
        }else {
            doLoginApi();
        }
    }


    private Map<String,String> getParam(){
        Map<String,String> map=new HashMap<>();
        map.put("email",etEmail.getText().toString());
        map.put("password",etPassword.getText().toString());
        map.put("user_type",Constant.USER_TYPE);
        map.put("device_type", "ANDROID");
        map.put("token", preference.getDeviceToken());
        return map;
    }

    private Map<String,String> getFBParam(String first_name,String last_name,String email,String phone,
                                          String gender,String image,String facebook_id,String state,
                                          String country,String city){
        Map<String,String> map=new HashMap<>();
        map.put("first_name",first_name);
        map.put("last_name",last_name);
        map.put("email",email);
        map.put("phone",phone);
        map.put("degree","");
        map.put("gender",gender);
        map.put("image",image);
        map.put("facebook_id",facebook_id);
        map.put("state",state);
        map.put("country",country);
        map.put("city",city);
        map.put("user_type",Constant.USER_TYPE);
        map.put("device_type", "ANDROID");
        map.put("token", preference.getDeviceToken());
        return map;
    }

    private Map<String,String> getGoogleParam(String first_name,String last_name,String email,String phone,
                                          String gender,String image,String google_id,String state,
                                          String country,String city){
        Map<String,String> map=new HashMap<>();
        map.put("first_name",first_name);
        map.put("last_name",last_name);
        map.put("email",email);
        map.put("phone",phone);
        map.put("degree","");
        map.put("gender",gender);
        map.put("image",image);
        map.put("google_id",google_id);
        map.put("state",state);
        map.put("country",country);
        map.put("city",city);
        map.put("user_type",Constant.USER_TYPE);
        map.put("device_type", "ANDROID");
        map.put("token", preference.getDeviceToken());
        return map;
    }
}
