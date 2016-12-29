package com.beetutors.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.beetutors.R;

public class SplashActivity extends BaseActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.context = this;
        new Handler().postDelayed(runnable, SPLASH_DISPLAY_LENGTH);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(isNetwork()) {
                Intent web = new Intent(context, WebviewActivity.class);
                //web.putExtra("url_webview","http://google.com/");
                web.putExtra("url_webview", "http://beetutors.com/");
                startActivity(web);
            }
            finish();
            //overridePendingTransition(R.anim.trans_left_in, R.anim.trans_left_out);
            //overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        }
    };
}
