package com.example.sheliza.grid_nav.Easy_Touch.activity.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.example.sheliza.grid_nav.Easy_Touch.activity.common.Constants;
import com.example.sheliza.grid_nav.Easy_Touch.activity.common.SharedPreferencesUtils;
import com.example.sheliza.grid_nav.Easy_Touch.activity.view.EasyTouchView;

public class AuxiliaryService extends Service {

    private EasyTouchView mEasyTouchView;

    @Override
    public IBinder onBind(Intent intent) {
        return null;  
    }  
  
    public void onCreate() {  
        super.onCreate();
        mEasyTouchView = new EasyTouchView(this);
        mEasyTouchView.initTouchViewEvent();
    }  
  


    @Override
    public void onDestroy() {
        super.onDestroy();
        mEasyTouchView.quitTouchView();
        SharedPreferencesUtils.setParam(this, Constants.WINDOWSWITCH, false);
    }
}