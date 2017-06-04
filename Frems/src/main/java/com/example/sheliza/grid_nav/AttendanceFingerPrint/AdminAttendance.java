package com.example.sheliza.grid_nav.AttendanceFingerPrint;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sheliza.grid_nav.AdminAttendanceView;
import com.example.sheliza.grid_nav.R;
import com.example.sheliza.grid_nav.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
//import butterknife.InjectView;

public class AdminAttendance extends AppCompatActivity implements  AdapterView.OnItemClickListener {
    @BindView(R.id.listView)
    ListView listView;

    @BindView(R.id.editTextSearch)
    EditText eTxtSearch;

    ArrayList<TeacherName> TeacherList;

    AdminAttendanceAdapter adapter;

    TeacherName teacherName;
    int pos;

    RequestQueue requestQueue;

    ProgressDialog progressDialog;
    String n,g,mail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_attendance);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);

        requestQueue = Volley.newRequestQueue(this);


        eTxtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String str = charSequence.toString();
                if(adapter!=null){
                    adapter.filter(str);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        //retrieveFromDB();
        retrieveFromCloud();
    }

    void retrieveFromCloud(){

        progressDialog.show();

        TeacherList = new ArrayList<>();

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Util.RetrieveRegistration, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Registration");
                    n="";
                    g="";
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        n = jObj.getString("NAME");
                        g=jObj.getString("EMAIL");
                        TeacherList.add(new TeacherName(n,g));
                    }

                    adapter = new AdminAttendanceAdapter(AdminAttendance.this,R.layout.teachername_item,TeacherList);
                    listView.setAdapter(adapter);
                    listView.setOnItemClickListener(AdminAttendance.this);

                    progressDialog.dismiss();

                }catch (Exception e){
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(AdminAttendance.this,"Some Exception" +e,Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(AdminAttendance.this,"Some Error",Toast.LENGTH_LONG).show();
            }
        });

        requestQueue.add(stringRequest); // Execute the Request
    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        pos = i;
        teacherName= TeacherList.get(i);
        mail=teacherName.getEmail();
        showStudent();
    }



    void showStudent(){
        Intent intent=new Intent(AdminAttendance.this, AdminAttendanceView.class);
        intent.putExtra("mail", mail);
            startActivity(intent);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
