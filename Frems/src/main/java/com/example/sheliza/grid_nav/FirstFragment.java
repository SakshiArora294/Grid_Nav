package com.example.sheliza.grid_nav;

import android.os.Bundle;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sheliza.grid_nav.Alarm.RemindyMain;
import com.example.sheliza.grid_nav.AttendanceFingerPrint.AdminAttendance;
import com.example.sheliza.grid_nav.AttendanceFingerPrint.FingerPrintMain;
import com.example.sheliza.grid_nav.AttendanceFingerPrint.Mark_Attendance;
import com.example.sheliza.grid_nav.AttendanceFingerPrint.SuceedActivity;
import com.example.sheliza.grid_nav.Notifi.Get;
import com.example.sheliza.grid_nav.Salary.SalaryCalculate;
import com.example.sheliza.grid_nav.TimeTable.RegisterList;
import com.example.sheliza.grid_nav.TimeTable.TeacherViewTimeTable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FirstFragment extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener {
    @Nullable
    ImageView imageView;

    GridView gridView;
    ArrayList<firstfragmentbean> list;
    FirstFragmentAdapter adapter;

    RequestQueue requestQueue;
    private Calendar cal = null;
    private int year = -1;

    private ArrayList weekendList = null;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View myView = inflater.inflate(R.layout.activity_first_fragment, container, false);

        gridView = (GridView) myView.findViewById(R.id.gridView);
        imageView = (ImageView) myView.findViewById(R.id.imageViewA);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();

        init();
        return myView;
    }


    void init() {

        requestQueue= Volley.newRequestQueue(getActivity());
        list = new ArrayList<firstfragmentbean>();

        firstfragmentbean u1 = new firstfragmentbean(R.drawable.profile, "My Profile");
        firstfragmentbean u2 = new firstfragmentbean(R.drawable.reminder, "Reminder");
        firstfragmentbean u3 = new firstfragmentbean(R.drawable.attendance, "Attendance");
        firstfragmentbean u4 = new firstfragmentbean(R.drawable.notification, "Notification");
        firstfragmentbean u5 = new firstfragmentbean(R.drawable.salary, "Salary");
        firstfragmentbean u6 = new firstfragmentbean(R.drawable.timetable, "TimeTable");


        list.add(u1); //0
        list.add(u2);
        list.add(u3);
        list.add(u4);
        list.add(u5);
        list.add(u6);//n-1


        adapter = new FirstFragmentAdapter(getContext(), R.layout.grid_item1, list);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        switch (position) {

            case 0:
                Intent intent = new Intent(getActivity(), MyProfileTeacher.class);
                startActivity(intent);
                break;
            case 1:
                Intent intent1 = new Intent(getActivity(), RemindyMain.class);
                startActivity(intent1);
                break;

            case 2:
                RetrieveFromServer();
                break;
            case 3:
                Intent intent3 = new Intent(getActivity(), Get.class);
                startActivity(intent3);
                break;
            case 4:
                Intent intent2 = new Intent(getActivity(), SalaryCalculate.class);
                startActivity(intent2);
                break;


            case 5:
                Intent intent5 = new Intent(getActivity(), TeacherViewTimeTable.class);
                startActivity(intent5);
                break;
        }

    }
    void RetrieveFromServer(){
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sakshi294.esy.es/insert/RetreiveDate.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");
                    if (success == 1) {
                        Toast.makeText(getActivity(),  message, Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getActivity(),Kuch_Bhi.class);
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                        Intent intent1=new Intent(getActivity(),Mark_Attendance.class);
                        startActivity(intent1);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(),"Some Error",Toast.LENGTH_LONG).show();
            }
        }){

        };

        requestQueue.add(stringRequest); // Execute the Request
    }
}

