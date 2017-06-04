package com.example.sheliza.grid_nav.AttendanceFingerPrint;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sheliza.grid_nav.R;
import com.example.sheliza.grid_nav.firstfragmentbean;

import java.util.ArrayList;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;



import android.widget.TextView;

import java.util.ArrayList;

import java.util.ArrayList;


/**
 * Created by Sheliza on 18-05-2017.
 */

public class AttendanceAdapter extends ArrayAdapter {

        Context ctx;  int resource;  ArrayList<AttendanceBean> objects;

        public AttendanceAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<AttendanceBean> objects) {
            super(context, resource, objects);
            ctx=context;
            this.resource=resource;
            this.objects=objects;
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view =null;
            view = LayoutInflater.from(getContext()).inflate(resource,parent,false);


            TextView textView1=(TextView)view.findViewById(R.id.Textname);
            TextView textView2=(TextView)view.findViewById(R.id.textView_attendance);
            TextView textView3=(TextView)view.findViewById(R.id.textView_date);
            TextView textView4=(TextView) view.findViewById(R.id.textView3) ;

            AttendanceBean bean = objects.get(position);

            textView1.setText(bean.getName());
            textView2.setText(bean.getAttendance());
            textView3.setText(bean.getDate());
            textView4.setText(bean.getLocation());

            return view;
        }
    }


