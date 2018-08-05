package com.trafficticketbuddy.lawyer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SettingsActivity extends BaseActivity {

    private TextView tvHeading;
    private ImageView back;
    private LinearLayout linAboutUs,linCntactUs,linPrivacyPolicy,linTramsCondition,linChangePAss;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
  */      setContentView(R.layout.activity_settings);

        linAboutUs = (LinearLayout)findViewById(R.id.linAboutUs);
        linCntactUs = (LinearLayout)findViewById(R.id.linCntactUs);
        linPrivacyPolicy = (LinearLayout)findViewById(R.id.linPrivacyPolicy);
        linTramsCondition = (LinearLayout)findViewById(R.id.linTramsCondition);
        linChangePAss = (LinearLayout)findViewById(R.id.linChangePAss);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_arrow_back_white_24dp));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        linAboutUs.setOnClickListener(this);
        linTramsCondition.setOnClickListener(this);
        linCntactUs.setOnClickListener(this);
        linChangePAss.setOnClickListener(this);
        linPrivacyPolicy.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()){
                case R.id.linAboutUs:
                    Intent intent=new Intent(SettingsActivity.this,AboutUsActivity.class);
                    intent.putExtra("key","About Us");
                     startActivity(intent);
                break;
                case R.id.linChangePAss:
                startActivity(new Intent(SettingsActivity.this,ChangePAsswordActivity.class));
                break;
                case R.id.linTramsCondition:
                    if (isNetworkConnected()) {
                        Intent intent2 = new Intent(SettingsActivity.this, ContactUsActivity.class);
                        intent2.putExtra("key", "Trams and Condition");
                        startActivity(intent2);
                    }
                    break;
                case R.id.linPrivacyPolicy:
                    if (isNetworkConnected()) {
                        Intent intent3 = new Intent(SettingsActivity.this, ContactUsActivity.class);
                        intent3.putExtra("key", "Privacy Policy");
                        startActivity(intent3);
                    }
                    break;
                case R.id.linCntactUs:
                    if (isNetworkConnected()) {
                        Intent intent4 = new Intent(SettingsActivity.this, ContactUsActivity.class);
                        intent4.putExtra("key", "Contact Us");
                        startActivity(intent4);
                    }

                break;
        }
    }
}
