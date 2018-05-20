package com.trafficticketbuddy.lawyer;

import android.os.Bundle;
import android.widget.TextView;

public class FileCaseActivity extends BaseActivity {

    private TextView tvHeading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       /* requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
     */   setContentView(R.layout.activity_filecase);
        tvHeading = (TextView)findViewById(R.id.tvHeading);
        tvHeading.setText("FILE A CASE");
    }
}
