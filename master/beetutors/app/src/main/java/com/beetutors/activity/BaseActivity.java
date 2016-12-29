package com.beetutors.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.beetutors.R;

/**
 * Created by TRUYENLT on 27/12/2016.
 */

public class BaseActivity extends AppCompatActivity {

    NetworkUtils networkUtils;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setTheme(R.style.AppThemeFull);
        networkUtils = new NetworkUtils();
    }

    public boolean isNetwork() {
        if (networkUtils.isConnected(this)) {
            return true;

        } else {
            Toast.makeText(this, "Not connect internet", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
