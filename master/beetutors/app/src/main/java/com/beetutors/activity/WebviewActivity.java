package com.beetutors.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.HttpAuthHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.beetutors.R;

import static android.webkit.URLUtil.isHttpUrl;

/**
 * Created by TruyenLT1 on 2016/12/27.
 */

public class WebviewActivity extends BaseActivity {

    private WebView webView;
    private boolean statusError = false;
    private static boolean FLAG_LOADING;
    private String url;
    ProcessDialogUntil processDialogUntil;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_webview);
        FLAG_LOADING = true;
        processDialogUntil = new ProcessDialogUntil(this);
        url = getIntent().getStringExtra("url_webview");
        webView = (WebView) findViewById(R.id.webview_id);

        if (isHttpUrl(url)) {
            //webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setJavaScriptEnabled(true);
            //webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.loadUrl(url);
            //webView.setVisibility(View.VISIBLE);
        } else {
            Toast.makeText(this, "Not connect to server", Toast.LENGTH_SHORT).show();
        }

        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                Log.e("debug", "onReceivedError");
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (isNetwork()) {
                    super.onPageStarted(view, url, favicon);
                    if (FLAG_LOADING) {
                        processDialogUntil.initDialog();
                    }
                    Log.d("debug", "onPageStarted");
                    statusError = false;
                } else {
                    statusError = true;
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (FLAG_LOADING) {
                    processDialogUntil.closeDialog();
                }
                FLAG_LOADING = false;

                if (statusError) {
                    webView.setVisibility(View.INVISIBLE);
                } else {
                    webView.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.e("debug", "URL reload:" + url);

                if (url.startsWith("tel:")) {
                    try {
                        startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(url)).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                    } catch (final ActivityNotFoundException e) {
                        // XXX
                    }
                    return true;
                } else {
                    return super.shouldOverrideUrlLoading(view, url);
                }
            }

            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                super.onReceivedHttpAuthRequest(view, handler, host, realm);
                Log.d("debug", "errorCode:onReceivedHttpAuthRequest");
                //statusError = true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Log.d("debug", "errorCode:onReceivedError");
                statusError = true;
                //webView.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (statusError) {
            webView.loadUrl(webView.getOriginalUrl());
            FLAG_LOADING = true;
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack();
        } else {
            Toast.makeText(this, "See you again!", Toast.LENGTH_SHORT).show();
            super.onBackPressed();
        }
    }
}
