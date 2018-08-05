package com.trafficticketbuddy.lawyer;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.trafficticketbuddy.lawyer.apis.ApiSetViewed;
import com.trafficticketbuddy.lawyer.apis.ApiSubmitBid;
import com.trafficticketbuddy.lawyer.model.fetchCase.Response;
import com.trafficticketbuddy.lawyer.model.placebid.PlaceBidMain;
import com.trafficticketbuddy.lawyer.restservice.OnApiResponseListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class BidDetailsActivity extends BaseActivity {

    private TextView tvHeading;
    private com.trafficticketbuddy.lawyer.model.allbid.Response mCaseResponse;
    private ImageView ivLicense,ivBackImage,ivFontImage;
    private TextView tvDesc,tvState,tvCity,tvCaseno,tvBidDesc,tvBidAmmount;
    private CardView cardBidNow;
    private com.trafficticketbuddy.lawyer.model.login.Response mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_biddetails);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ivLicense = (ImageView)findViewById(R.id.ivLicense);
        ivBackImage = (ImageView)findViewById(R.id.ivBackImage);
        ivFontImage = (ImageView)findViewById(R.id.ivFontImage);
        ivLicense.setOnClickListener(this);
        ivBackImage.setOnClickListener(this);
        ivFontImage.setOnClickListener(this);
        tvDesc = (TextView)findViewById(R.id.tvDesc);
        tvState = (TextView)findViewById(R.id.tvState);
        tvCity = (TextView)findViewById(R.id.tvCity);
        tvCaseno = (TextView)findViewById(R.id.tvCaseno);
        tvBidAmmount = (TextView)findViewById(R.id.tvBidAmmount);
        tvBidDesc = (TextView)findViewById(R.id.tvBidDesc);

        Intent mIntent = getIntent();
        if(mIntent!=null){
            mCaseResponse = (com.trafficticketbuddy.lawyer.model.allbid.Response) mIntent.getSerializableExtra("data");
            loadImage(this,mCaseResponse.getDrivingLicense(),ivLicense);
            loadImage(this,mCaseResponse.getCaseFrontImg(),ivFontImage);
            loadImage(this,mCaseResponse.getCaseRearImg(),ivBackImage);
            tvState.setText(mCaseResponse.getCity());
            tvCity.setText(mCaseResponse.getState());
            tvDesc.setText(mCaseResponse.getCaseDetails());
            tvCaseno.setText("Case details for case no. "+mCaseResponse.getCaseNumber());
            String json = preference.getString("login_user", "");
            mLogin = new Gson().fromJson(json, com.trafficticketbuddy.lawyer.model.login.Response.class);
            tvBidAmmount.setText(mCaseResponse.getBidAmount());
            tvBidDesc.setText(mCaseResponse.getBidText());
        }
    }



    @Override
    public void onClick(View view) {
        super.onClick(view);
        Intent mIntent;
        switch (view.getId()){
            case R.id.ivLicense:
                FullImageActivity.start(BidDetailsActivity.this, mCaseResponse.getDrivingLicense(), ivLicense);
                break;
            case R.id.ivBackImage:
                FullImageActivity.start(BidDetailsActivity.this, mCaseResponse.getCaseRearImg(), ivBackImage);
                break;
            case R.id.ivFontImage:
                FullImageActivity.start(BidDetailsActivity.this, mCaseResponse.getCaseFrontImg(), ivFontImage);
                break;
        }
    }
}
