package com.amir.ethoimage.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.amir.ethoimage.R;
import com.amir.ethoimage.core.BaseActivity;
import com.amir.ethoimage.databinding.ActivityDashboardBinding;
import com.amir.ethoimage.fragments.Bluetooth;
import com.amir.ethoimage.fragments.Camera;
import com.amir.ethoimage.fragments.Gallery;
import com.amir.ethoimage.util.Common;
import com.google.android.material.tabs.TabLayout;

public class Dashboard extends BaseActivity {

    private ActivityDashboardBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        setUpTab();
        setTabSelectedListner();
        switchFragment( new Camera());
        setToolbar("Camera");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = getSupportFragmentManager().findFragmentById(binding.flContainer.getId());
        if (fragment != null) {
            fragment.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void setUpTab() {
        setCustomTabView( R.drawable.camera_selector);
        setCustomTabView( R.drawable.gallery_selector);
        setCustomTabView(R.drawable.bluetooth_selector);
    }

    private void setCustomTabView( int icon) {
        LinearLayout customView = (LinearLayout) LayoutInflater.from(Dashboard.this).inflate(R.layout.item_custom_tab, null);
        ((ImageView) customView.findViewById(R.id.ivIcon)).setImageResource(icon);

        binding.tabs.addTab(binding.tabs.newTab().setCustomView(customView));
    }

    private void switchFragment(Fragment fragment) {
        if (fragment == null)
            return;
        else if (fragment == getSupportFragmentManager().findFragmentById(binding.flContainer.getId())) {
            return;
        }
        getSupportFragmentManager().beginTransaction().replace(binding.flContainer.getId(), fragment).commit();
    }

    private void setTabSelectedListner() {
        binding.tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        switchFragment(new Camera());
                        setToolbar("Camera");
                        break;
                    case 1:
                        switchFragment(new Gallery());
                        setToolbar("Gallery");
                        break;
                    case 2:
                        switchFragment(new Bluetooth());
                        setToolbar("Bluetooth");
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setToolbar(String title){
        Common.setToolbarWithBackAndTitle(mContext,title,false,0);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Fragment fragment = getSupportFragmentManager().findFragmentById(binding.flContainer.getId());
        if (fragment != null) {
            fragment.onRequestPermissionsResult(requestCode,permissions,grantResults);
        }
    }
}
