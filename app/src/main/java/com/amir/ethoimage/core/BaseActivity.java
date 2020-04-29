package com.amir.ethoimage.core;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.amir.ethoimage.R;
import com.amir.ethoimage.interfaces.Constants;
import com.amir.ethoimage.util.SharedPref;


public class BaseActivity extends AppCompatActivity implements Constants {

    public SharedPref sp;
    public Activity mContext;
    public String TAG = "Error";
    public LinearLayoutManager layoutManager;
    public ConstraintLayout loader;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }
        intitializeVariable();
    }

    public void intitializeVariable(){
        mContext = BaseActivity.this;
        sp = new SharedPref(mContext);
        layoutManager = new LinearLayoutManager(getApplicationContext());
    }

    /**
     * show the toast message
     * @param message
     */
    public void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    /**
     * hide the soft keyboard
     */
    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) mContext.getSystemService(Activity.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && mContext.getCurrentFocus() != null)
            inputMethodManager.hideSoftInputFromWindow(mContext.getCurrentFocus().getWindowToken(), 0);
    }

    public void showLoader() {
        loader.setVisibility(View.VISIBLE);
    }

    public void hideLoader() {
        loader.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.stays, R.anim.slide_out_to_right);
    }
}
