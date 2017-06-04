package com.example.sheliza.grid_nav.Easy_Touch.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import com.kyleduo.switchbutton.SwitchButton;
import com.example.sheliza.grid_nav.R;
import com.example.sheliza.grid_nav.Easy_Touch.activity.common.Constants;
import com.example.sheliza.grid_nav.Easy_Touch.activity.common.SharedPreferencesUtils;
import com.example.sheliza.grid_nav.Easy_Touch.activity.service.AuxiliaryService;


//import butterknife.InjectView;
import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity {

    @BindView(R.id.main_open)
    SwitchButton mainOpen;
    @BindView(R.id.containerLayout)
    LinearLayout containerLayout;

    private Intent intent;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initEvent();
    }

    private void initEvent() {
        mainOpen.setChecked((boolean) SharedPreferencesUtils.getParam(MainActivity.this, Constants.WINDOWSWITCH, false));
        intent = new Intent(MainActivity.this, AuxiliaryService.class);
        mainOpen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    startService(intent);
                    SharedPreferencesUtils.setParam(MainActivity.this, Constants.WINDOWSWITCH, true);
                } else {
                    stopService(intent);
                    SharedPreferencesUtils.setParam(MainActivity.this, Constants.WINDOWSWITCH, false);
                }
            }
        });
    }



    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        Log.d("main:","onSaveInstanceState");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("main:","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }
}
