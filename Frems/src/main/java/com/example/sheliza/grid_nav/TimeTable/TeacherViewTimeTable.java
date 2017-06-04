package com.example.sheliza.grid_nav.TimeTable;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.sheliza.grid_nav.R;
import com.example.sheliza.grid_nav.Util;
import com.squareup.picasso.Picasso;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.sheliza.grid_nav.MyProfileTeacher;
import com.example.sheliza.grid_nav.R;
import com.example.sheliza.grid_nav.Util;
import com.squareup.picasso.Picasso;



    public class TeacherViewTimeTable extends AppCompatActivity {
        SharedPreferences sharedPreferences;
        String email,name;
        ImageView imageView;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_teacher_view_time_table);
            sharedPreferences=getSharedPreferences(Util.PREFS_NAME,MODE_PRIVATE);
            email= sharedPreferences.getString(Util.COL_EMAIL,"");
            name=sharedPreferences.getString(Util.COL_NAME,"");
            imageView=(ImageView)findViewById(R.id.Timetable);
            setTitle(name);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            String url="http://sakshi294.esy.es/insert/Time_Table/"+email+".JPG";
            Log.i("url",url.toString());
            Picasso.with(com.example.sheliza.grid_nav.TimeTable.TeacherViewTimeTable.this).load(url).into(imageView);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            if(item.getItemId() == android.R.id.home){
                finish();
            }
            return super.onOptionsItemSelected(item);
        }
    }


