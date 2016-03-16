package com.example.fluke.liveat500px;

import android.app.Application;

import com.example.fluke.liveat500px.manager.Contextor;

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //Initialize thing(s) here
        Contextor.getInstance().init(getApplicationContext());
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
