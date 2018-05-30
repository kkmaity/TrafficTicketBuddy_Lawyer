package com.trafficticketbuddy.lawyer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.trafficticketbuddy.lawyer.model.cases.Response;

public class CaseDetailsActivity extends BaseActivity {

    private TextView tvHeading;
    private Response mCaseResponse;
    private ImageView ivLicense,ivBackImage,ivFontImage;
    private TextView tvDesc,tvState,tvCity,tvCaseno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      /*  requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
  */      setContentView(R.layout.activity_casedetails);
        tvHeading = (TextView)findViewById(R.id.tvHeading);
        tvHeading.setText("CASE DETAILS");
        ivLicense = (ImageView)findViewById(R.id.ivLicense);
        ivBackImage = (ImageView)findViewById(R.id.ivBackImage);
        ivFontImage = (ImageView)findViewById(R.id.ivFontImage);
        tvDesc = (TextView)findViewById(R.id.tvDesc);
        tvState = (TextView)findViewById(R.id.tvState);
        tvCity = (TextView)findViewById(R.id.tvCity);
        tvCaseno = (TextView)findViewById(R.id.tvCaseno);

        Intent mIntent = getIntent();
        if(mIntent!=null){
            mCaseResponse = (Response) mIntent.getSerializableExtra("data");
            loadImage(this,mCaseResponse.getDrivingLicense(),ivLicense);
            loadImage(this,mCaseResponse.getCaseFrontImg(),ivFontImage);
            loadImage(this,mCaseResponse.getCaseRearImg(),ivBackImage);
            tvState.setText(mCaseResponse.getState());
            tvCity.setText(mCaseResponse.getCity());
            tvDesc.setText(mCaseResponse.getCaseDetails());
            tvCaseno.setText("Case details for case no. "+mCaseResponse.getCaseNumber());
        }
    }
}
