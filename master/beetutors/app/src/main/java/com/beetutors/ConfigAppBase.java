package com.beetutors;

import android.app.Application;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Created by TruyenLT1 on 2016/09/22.
 */
public class ConfigAppBase extends Application
{

    @Override
    public void onCreate() {
        super.onCreate();
        Authenticator.setDefault(new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("TruyenLT1", "$abcd1234".toCharArray());
            }
        });
    }
}
