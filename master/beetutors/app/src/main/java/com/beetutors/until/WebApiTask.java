package com.beetutors.until;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.os.AsyncTask;
import android.util.Log;
import android.view.Window;
import android.widget.ProgressBar;

import com.beetutors.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by TruyenLT1 on 2016/12/23.
 */
public class WebApiTask extends AsyncTask<String, Void, String> {

    //Define status connect
    private static final int STATUS_ERROR_404 = 404;
    private static final int STATUS_ERROR_407 = 407;
    private static final int STATUS_ERROR_400 = 400;
    private static final int STATUS_ERROR_200 = 200;
    private static final int STATUS_ERROR_502 = 502;
    private static final int TIME_OUT_LOADING = 200;

    ProgressDialog progressDialog;
    AnimationDrawable frameAnimation;
    Context context;
    CallBackApi callBackApi;


    ProgressBar progressBar;

    public WebApiTask(Context context, CallBackApi callBackApi) {
        super();
        this.callBackApi = callBackApi;
        this.context = context;
    }

    @Override
    protected String doInBackground(String... params) {
        Log.d("DebugApp", "doInBackground");

        StringBuilder src = new StringBuilder();
        String getUrl = params[0].toString();
        HttpURLConnection connection = null;

        try {
            Thread.sleep(1000);
            URL url = new URL(getUrl);
            InputStream is;
            connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(5000); //set timeout to 5 seconds
            connection.connect();
            is = connection.getInputStream();
            while (true) {
                byte[] line = new byte[1024];
                int size = is.read(line);
                if (size <= 0)
                    break;
                src.append(new String(line, "UTF-8"));
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("DemoApp", e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("DemoApp", e.getMessage());
        } finally {
            connection.disconnect();
        }
        return src.toString();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //initDialog();
        initDialog();
        Log.d("DebugApp", "onPreExecute");
    }

    @Override
    protected void onPostExecute(String t) {
        super.onPostExecute(t);
        Log.d("DebugApp", "onPostExecute");
        closeDialog();
        callBackApi.onSuccess(t);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }

    @Override
    protected void onCancelled(String t) {
        super.onCancelled(t);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void closeDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            if (frameAnimation != null) {
                frameAnimation.stop();
            }

            handler.removeCallbacks(runnable);
        }
    }

    public void initDialog() {
        if (null == progressDialog) {
            progressDialog = new ProgressDialog(context);
            progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        }
        progressDialog.show();
        progressDialog.setContentView(R.layout.layout_dialog_custom);
        progressBar = (ProgressBar) progressDialog.findViewById(R.id.progressBar);
        handler.postDelayed(runnable, TIME_OUT_LOADING);
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            int max = progressBar.getMax();
            int current = progressBar.getProgress();
            Log.d("debug", "max:" + max + " - current:" + current);
            if (current < max) {
                current = current + 5;
                progressBar.setProgress(current);
            } else {
                progressBar.setProgress(1);
            }
            handler.postDelayed(runnable, TIME_OUT_LOADING);
        }
    };

    interface CallBackApi {
        public void onSuccess(String result);
    }

}
