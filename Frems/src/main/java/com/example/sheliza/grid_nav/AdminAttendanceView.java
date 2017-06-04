package com.example.sheliza.grid_nav;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sheliza.grid_nav.AttendanceFingerPrint.AttendanceAdapter;
import com.example.sheliza.grid_nav.AttendanceFingerPrint.AttendanceBean;
import com.example.sheliza.grid_nav.AttendanceFingerPrint.TeacherName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AdminAttendanceView extends AppCompatActivity {
    @BindView(R.id.listView)
    ListView listView;

    @BindView(R.id.editTextSearch)
    EditText eTxtSearch;

    ArrayList<AttendanceBean> list;

    AttendanceAdapter adapter;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

 String mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_attendance);
        ButterKnife.bind(this);
        list=new ArrayList<>();
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait...");
        progressDialog.setCancelable(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        requestQueue = Volley.newRequestQueue(this);
        Intent intent = getIntent();
        mail = intent.getStringExtra("mail");
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
                        n = jObj.getString("DATE");
                        p = jObj.getString("LOCATION");
                        e = jObj.getString("ATTENDANCE");
                        f=  jObj.getString("NAME");
                     //   g=  jObj.getString("EMAIL");
                        Log.i("n",n);

                        list.add(new AttendanceBean(n.toString(),p,e,f));
                    }

                    adapter = new AttendanceAdapter(AdminAttendanceView.this,R.layout.attendance_item,list);
                    listView.setAdapter(adapter);


                    progressDialog.dismiss();

                }catch (Exception e){
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(AdminAttendanceView.this,"Some Exception"+e,Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(AdminAttendanceView.this,"Some Error",Toast.LENGTH_LONG).show();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("EMAIL",mail );


                return map;
            }

        };
        requestQueue.add(stringRequest); // Execute the Request
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}