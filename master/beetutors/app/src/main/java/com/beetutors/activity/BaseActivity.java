package com.beetutors.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.beetutors.R;
import com.beetutors.until.NetworkUtils;

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
        networkUtils = new NetworkUtils(this);
    }

    //Todo
    public boolean isNetwork() {
        if (networkUtils.isWifiConnected(this) && networkUtils.isNetworkAvailable(this)) {
            return true;
        } else {
            Toast.makeText(this, "Not connect internet", Toast.LENGTH_LONG).show();
            return false;
        }
    }
}
