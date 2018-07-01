package com.trafficticketbuddy.lawyer;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.nshmura.snappyimageviewer.SnappyImageViewer;
import com.trafficticketbuddy.lawyer.utils.Constant;

import java.io.IOException;
import java.net.URL;

public class FullImageActivity extends AppCompatActivity {

    private static final String EXTRA_IMAGE_URL = "EXTRA_IMAGE_URL";
    private static final String TRANSITION_NAME_PHOTO = "TRANSITION_NAME_PHOTO";

    public static void start(Activity activity, String uri, ImageView imageView) {
        Intent intent = new Intent(activity, FullImageActivity.class);
        intent.putExtra(EXTRA_IMAGE_URL, uri);

        //noinspection unchecked
        Bundle options = ActivityOptionsCompat.makeSceneTransitionAnimation(activity,
                Pair.create((View) imageView, TRANSITION_NAME_PHOTO))
                .toBundle();

        ActivityCompat.startActivity(activity, intent, options);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);

        String uri = getIntent().getStringExtra(EXTRA_IMAGE_URL);

        SnappyImageViewer snappyImageViewer = (SnappyImageViewer) findViewById(R.id.snappy_image_viewer);

        Glide.with(this).load(Constant.BASE_URL+uri)
                .thumbnail(0.5f)
                .into(snappyImageViewer.getImageView());

        snappyImageViewer.addOnClosedListener(new SnappyImageViewer.OnClosedListener() {
            @Override
            public void onClosed() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    getWindow().setSharedElementReturnTransition(new Fade(Fade.IN));
                }
                ActivityCompat.finishAfterTransition(FullImageActivity.this);
            }
        });
        ViewCompat.setTransitionName(snappyImageViewer.getImageView(), TRANSITION_NAME_PHOTO);
    }
}