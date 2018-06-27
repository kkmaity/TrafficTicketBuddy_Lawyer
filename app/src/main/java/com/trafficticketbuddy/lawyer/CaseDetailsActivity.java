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

import okhttp3.ResponseBody;


public class CaseDetailsActivity extends BaseActivity {

    private TextView tvHeading;
    private Response<R> mCaseResponse;
    private ImageView ivLicense,ivBackImage,ivFontImage;
    private TextView tvDesc,tvState,tvCity,tvCaseno;
    private CardView cardBidNow;
    private com.trafficticketbuddy.lawyer.model.login.Response mLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
  */
        setContentView(R.layout.activity_casedetails);
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
        tvDesc = (TextView)findViewById(R.id.tvDesc);
        tvState = (TextView)findViewById(R.id.tvState);
        tvCity = (TextView)findViewById(R.id.tvCity);
        tvCaseno = (TextView)findViewById(R.id.tvCaseno);
        cardBidNow = (CardView)findViewById(R.id.cardBidNow);
        cardBidNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mCaseResponse.getIs_bided().equals("0")) {
                    confirmBidDialog();
                }else{
                    showDialog("Bid has been already placed");
                }
            }
        });

        Intent mIntent = getIntent();
        if(mIntent!=null){
            mCaseResponse = (Response<R>) mIntent.getSerializableExtra("data");
            loadImage(this,mCaseResponse.getDrivingLicense(),ivLicense);
            loadImage(this,mCaseResponse.getCaseFrontImg(),ivFontImage);
            loadImage(this,mCaseResponse.getCaseRearImg(),ivBackImage);
            tvState.setText(mCaseResponse.getCity());
            tvCity.setText(mCaseResponse.getState());
            tvDesc.setText(mCaseResponse.getCaseDetails());
            tvCaseno.setText("Case details for case no. "+mCaseResponse.getCaseNumber());
            String json = preference.getString("login_user", "");
            mLogin = new Gson().fromJson(json, com.trafficticketbuddy.lawyer.model.login.Response.class);
        }

        setView();
    }

    void confirmBidDialog(){
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_placebid);
        final EditText et_bid_price = (EditText)dialog.findViewById(R.id.et_bid_price);
        final EditText et_description = (EditText)dialog.findViewById(R.id.et_description);
        CardView cardSubmit = (CardView) dialog.findViewById(R.id.cardSubmit);
        cardSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitBidApi(et_bid_price.getText().toString(), et_description.getText().toString());
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        dialog.show();

    }

    private void submitBidApi(String amount,String bid_text) {
        showProgressDialog();
        new ApiSubmitBid(getParam( amount, bid_text), new OnApiResponseListener() {

            @Override
            public <E> void onSuccess(E t) {
                {
                    dismissProgressDialog();

                    try {
                        PlaceBidMain mPlaceBidMain= (PlaceBidMain) t;
                        if (mPlaceBidMain.getStatus()){
                            showDialog(mPlaceBidMain.getMessage());
                            finish();
                        }
                        else
                            showDialog(mPlaceBidMain.getMessage());
                    } catch (Exception e) {
                        e.printStackTrace();
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


    private Map<String,String> getParam(String amount,String bid_text){
        Map<String,String> map=new HashMap<>();
        map.put("lawyer_id",mLogin.getId());
        map.put("client_id",mCaseResponse.getClientId());
        map.put("case_id", mCaseResponse.getId());
        map.put("bid_text", bid_text);
        map.put("bid_amount", amount);
        return map;
    }


    private void setView() {
        if (isNetworkConnected()){
            showProgressDialog();
            new ApiSetViewed(getParam(), new OnApiResponseListener() {
                @Override
                public <E> void onSuccess(E t) {
                    dismissProgressDialog();
                    String res=(String)t;
                    try {
                        JSONObject object=new JSONObject(res);
                        if (object.getBoolean("status")){
                            showDialog(object.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    System.out.print(res);
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
    private Map<String, String> getParam() {
        Map<String,String> map=new HashMap<>();
        map.put("user_id",preference.getUserId());
        map.put("case_id",mCaseResponse.getId());
        return map;
    }
}
