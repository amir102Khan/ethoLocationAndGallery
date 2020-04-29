package com.amir.ethoimage.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.amir.ethoimage.R;


public class Common {

    /**
     * method to check string is empty or not
     * @param text
     * @return
     */
    public static boolean validateEditText(String text) {
        return !TextUtils.isEmpty(text) && text.trim().length() > 0;
    }

    /**
     * method to check email is valid or not
     * @param email
     * @return
     */
    public static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * method to check internet connectivity
     * @param context
     * @return
     */
    public static boolean checkInternetConnection(Context context) {
        ConnectivityManager conMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return conMgr != null && (conMgr.getActiveNetworkInfo() != null && conMgr.getActiveNetworkInfo().isAvailable() && conMgr.getActiveNetworkInfo().isConnected());
    }

    /**
     * method to set the toolbar with back icon and title
     * @param ctx
     * @param title
     * @param value
     * @param backResource
     */
    public static void setToolbarWithBackAndTitle(final Context ctx, String title, Boolean value, int backResource) {
        Toolbar toolbar = ((AppCompatActivity) ctx).findViewById(R.id.toolbar);
        ((AppCompatActivity) ctx).setSupportActionBar(toolbar);
        TextView title_tv = toolbar.findViewById(R.id.title_tv);
        ActionBar actionBar = ((AppCompatActivity) ctx).getSupportActionBar();
        if (actionBar != null) {
            if (backResource != 0){
                toolbar.setNavigationIcon(backResource);
                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ((AppCompatActivity) ctx).onBackPressed();
                    }
                });
            }

            actionBar.setDisplayShowHomeEnabled(value);
            actionBar.setDisplayShowTitleEnabled(false);
            title_tv.setText(title);
        }
    }
}
