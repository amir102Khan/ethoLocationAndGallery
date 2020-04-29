package com.amir.ethoimage.core;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.amir.ethoimage.interfaces.Constants;
import com.amir.ethoimage.util.SharedPref;


public class BaseFragment extends Fragment implements Constants {
    public static Activity mContext;
    public String TAG = "Error";
    public LinearLayoutManager layoutManager;
    public SharedPref sp;
    public ConstraintLayout loader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeVariable();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initializeVariable(){
        mContext = getActivity();
        layoutManager = new LinearLayoutManager(getContext());
        sp = new SharedPref(mContext);
    }


    public void showToast(String message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

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

}
