package com.beetutors.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.widget.ProgressBar;

import com.beetutors.R;

/**
 * Created by TRUYENLT on 29/12/2016.
 */

public class ProcessDialogUntil {

    Context context;

    ProcessDialogUntil(Context context) {
        this.context = context;
    }

    private ProgressDialog progressDialog;
    private ProgressBar progressBar;
    private static final int TIME_OUT_LOADING = 100;
    private int totalLoading = 0;


    Handler handlerLoading = new Handler();
    Runnable runnableLoading = new Runnable() {
        @Override
        public void run() {
            int max = progressBar.getMax();
            int current = progressBar.getProgress();
            Log.d("debug", "max:" + max + " - current:" + current);
            if (current < max) {
                current = current + 2;
                progressBar.setProgress(current);
            } else {
                totalLoading = totalLoading + TIME_OUT_LOADING;
                progressBar.setProgress(1);
                /*if (totalLoading > 15000) {
                    closeDialog();
                    handlerLoading.removeCallbacks(runnableLoading);
                    Toast.makeText(getApplicationContext(), "Timeout", Toast.LENGTH_LONG).show();
                }*/
            }
            handlerLoading.postDelayed(runnableLoading, TIME_OUT_LOADING);
        }
    };

    public void initDialog() {
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(context);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        progressDialog.show();
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressDialog.setContentView(R.layout.layout_dialog_custom);
        progressBar = (ProgressBar) progressDialog.findViewById(R.id.progressBar);
        handlerLoading.postDelayed(runnableLoading, TIME_OUT_LOADING);
        Log.d("debug", "initDialog");
    }

    public void closeDialog() {
        if (progressDialog != null) {
            handlerLoading.removeCallbacks(runnableLoading);
            progressDialog.dismiss();
        }
        Log.d("debug", "closeDialog");
    }
}
