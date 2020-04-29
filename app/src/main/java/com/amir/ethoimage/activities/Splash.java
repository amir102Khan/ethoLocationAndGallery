package com.amir.ethoimage.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.amir.ethoimage.R;
import com.amir.ethoimage.core.BaseActivity;
import com.amir.ethoimage.databinding.ActivitySplashBinding;

public class Splash extends BaseActivity {


    private ActivitySplashBinding binding;
    private Handler handler;
    private static final int TIMER = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_splash);
        handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(mContext,Dashboard.class));
                finish();
            }
        }, TIMER);
    }
}
