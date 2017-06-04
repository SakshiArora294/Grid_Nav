package com.example.sheliza.grid_nav;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.sheliza.grid_nav.Alarm.RemindyMain;
import com.example.sheliza.grid_nav.AttendanceFingerPrint.AdminAttendance;
import com.example.sheliza.grid_nav.AttendanceFingerPrint.FingerPrintMain;
import com.example.sheliza.grid_nav.Easy_Touch.activity.MainActivity;
import com.example.sheliza.grid_nav.Easy_Touch.activity.view.EasyTouchView;
import com.example.sheliza.grid_nav.Notifi.Notification;
import com.example.sheliza.grid_nav.TimeTable.RegisterList;
import com.example.sheliza.grid_nav.TimeTable.TeacherViewTimeTable;
import com.example.sheliza.grid_nav.TimeTable.Time_Table;

import java.util.ArrayList;

public class MainActivityofHOD extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AdapterView.OnItemClickListener{

    ImageView imageView;
    GridView gridView;
    ArrayList<firstfragmentbean> list;
    FirstFragmentAdapter adapter;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activityof_hod);

        sharedPreferences = getSharedPreferences(Util.PREFS_NAME, MODE_PRIVATE);
        gridView = (GridView) findViewById(R.id.gridView);
        imageView = (ImageView) findViewById(R.id.imageViewA);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
         setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivityofHOD.this, Web_View.class);
                startActivity(intent);

            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        init();
    }

     void init() {
         list = new ArrayList<>();

             firstfragmentbean u1 = new firstfragmentbean(R.drawable.profile, "View Profile");
             firstfragmentbean u2 = new firstfragmentbean(R.drawable.reminder, "Reminder");
             firstfragmentbean u3 = new firstfragmentbean(R.drawable.attendance, "Attendance");
             firstfragmentbean u4 = new firstfragmentbean(R.drawable.notification, "Notification");
             firstfragmentbean u5 = new firstfragmentbean(R.drawable.timetable, "TimeTable");


             list.add(u1); //0
             list.add(u2);
             list.add(u3);
             list.add(u4);
             list.add(u5);//n-1


             adapter = new FirstFragmentAdapter(MainActivityofHOD.this , R.layout.grid_item1, list);
             gridView.setAdapter(adapter);
             gridView.setOnItemClickListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent intent = new Intent(MainActivityofHOD.this, MyProfileTeacher.class);
            startActivity(intent);

        } else if (id == R.id.nav_gallery) {
            Intent intent = new Intent(MainActivityofHOD.this, MainActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_slideshow) {
            Intent intent = new Intent(MainActivityofHOD.this, About_Us.class);
            startActivity(intent);

        } else if (id == R.id.nav_manage) {
            sharedPreferences.edit().clear().commit();
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {

            case 0:
                  Intent intent = new Intent(MainActivityofHOD.this, AdminProfile.class);
                  startActivity(intent);
                break;
            case 1:
                Intent intent1 = new Intent(MainActivityofHOD.this, RemindyMain.class);
                startActivity(intent1);
                break;

            case 2:
                Intent intent4 = new Intent(MainActivityofHOD.this, AdminAttendance.class);
                startActivity(intent4);
                break;

            case 3:
               // Intent intent3 = new Intent(MainActivityofHOD.this, FingerPrintMain.class);
              //  startActivity(intent3);
                Intent intent3 = new Intent(MainActivityofHOD.this, Notification.class);
                startActivity(intent3);
                break;
            case 4:
                Intent intent5 = new Intent(MainActivityofHOD.this, RegisterList.class);
                startActivity(intent5);
                break;
        }
    }
}
