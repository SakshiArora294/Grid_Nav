package com.example.sheliza.grid_nav.AttendanceFingerPrint;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sheliza.grid_nav.R;
import com.example.sheliza.grid_nav.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
//import butterknife.InjectView;

public class AttendanceView extends AppCompatActivity {
    @BindView(R.id.listview)
    ListView listView;
    SharedPreferences sharedPreferences;
    String email;
    ProgressDialog progressDialog;
    RequestQueue requestQueue;
    AttendanceAdapter adapter;
    ArrayList<AttendanceBean> list;
    AttendanceBean bean;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_view);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        requestQueue = Volley.newRequestQueue(this);
        sharedPreferences = getSharedPreferences(Util.PREFS_NAME, MODE_PRIVATE);
        email = sharedPreferences.getString(Util.COL_EMAIL, "");
        RetrieveFromServer();

    }
    void RetrieveFromServer(){
        progressDialog.show();

        list = new ArrayList<>();
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, Util.RETRIEVE_STUDENT_RECORD, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Info");

                    int id=0;
                    String n="",p="",e="",f="",g="";
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jObj = jsonArray.getJSONObject(i);

                        id = jObj.getInt("ID");
                        n = jObj.getString("DATE");
                        p = jObj.getString("LOCATION");
                        e = jObj.getString("ATTENDANCE");
                        f=  jObj.getString("NAME");
                        g=  jObj.getString("EMAIL");
                        Log.i("n",n);



                        list.add(new AttendanceBean(n.toString(),p,e,f));
                    }

                    adapter = new AttendanceAdapter(AttendanceView.this,R.layout.attendance_item,list);
                    listView.setAdapter(adapter);


                    progressDialog.dismiss();

                }catch (Exception e){
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(AttendanceView.this,"Some Exception"+e,Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(AttendanceView.this,"Some Error",Toast.LENGTH_LONG).show();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("EMAIL",email );

                return map;
            }

        };
        requestQueue.add(stringRequest); // Execute the Request
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}

