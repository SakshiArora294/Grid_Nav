package com.example.sheliza.grid_nav;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.sheliza.grid_nav.AttendanceFingerPrint.AdminAttendance;
import com.example.sheliza.grid_nav.AttendanceFingerPrint.AdminAttendanceAdapter;
import com.example.sheliza.grid_nav.AttendanceFingerPrint.AttendanceAdapter;
import com.example.sheliza.grid_nav.AttendanceFingerPrint.AttendanceBean;
import com.example.sheliza.grid_nav.AttendanceFingerPrint.AttendanceView;
import com.example.sheliza.grid_nav.AttendanceFingerPrint.TeacherName;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
//import butterknife.InjectView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.sheliza.grid_nav.R;
import com.example.sheliza.grid_nav.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

public class AdminProfile extends AppCompatActivity implements  AdapterView.OnItemClickListener {
        @BindView(R.id.listView)
        ListView listView;

        @BindView(R.id.editTextSearch)
        EditText eTxtSearch;

        ArrayList<TeacherName> TeacherList;

       ArrayList<Teachersprofilebean> teachers;

        AdminAttendanceAdapter adapter;

        TeacherName teacherName;
        int pos;

        RequestQueue requestQueue;

        ProgressDialog progressDialog;
        String n,g, mail;
    String name="",email="",password="",desig="",add="",con="",sal="",dt="",gen="";


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_admin_attendance);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);


            ButterKnife.bind(this);

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

                        adapter = new AdminAttendanceAdapter(AdminProfile.this,R.layout.teachername_item,TeacherList);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(AdminProfile.this);

                        progressDialog.dismiss();

                    }catch (Exception e){
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(AdminProfile.this,"Some Exception",Toast.LENGTH_LONG).show();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(AdminProfile.this,"Some Error",Toast.LENGTH_LONG).show();
                }
            });

            requestQueue.add(stringRequest); // Execute the Request
        }


        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            pos = i;
            teacherName= TeacherList.get(i);
             mail = teacherName.getEmail();
            Log.i("yo", mail);
            showOptions();

        }

        void showOptions(){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            String[] items ={"View", "Delete"};
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    switch (i) {
                        case 0:
                            showStudent();
                            break;
                        case 1:
                            deleteStudent();
                    }

                }
            });
            //AlertDialog dialog = builder.create();
            //dialog.show();

            builder.create().show();
        }

     void deleteStudent() {
         progressDialog.show();
         StringRequest request = new StringRequest(Request.Method.POST, Util.DELETE_STUDENT_PHP, new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {

                 try{
                     JSONObject jsonObject = new JSONObject(response);
                     int success = jsonObject.getInt("success");
                     String message = jsonObject.getString("message");

                     if(success == 1){
                         TeacherList.remove(pos);
                         adapter.notifyDataSetChanged();
                         Toast.makeText(AdminProfile.this,message,Toast.LENGTH_LONG).show();
                     }else{
                         Toast.makeText(AdminProfile.this,message,Toast.LENGTH_LONG).show();
                     }
                     progressDialog.dismiss();
                 }catch (Exception e){
                     e.printStackTrace();
                     progressDialog.dismiss();
                     Toast.makeText(AdminProfile.this,"Some Exception",Toast.LENGTH_LONG).show();
                 }
             }
         }, new Response.ErrorListener() {
             @Override
             public void onErrorResponse(VolleyError error) {
                 progressDialog.dismiss();
                 Toast.makeText(AdminProfile.this,"Some Volley Error: "+error.getMessage(),Toast.LENGTH_LONG).show();
             }
         })
         {
             @Override
             protected Map<String, String> getParams() throws AuthFailureError {
                 Map<String,String> map = new HashMap<>();
                 map.put("email", mail);
                 return map;
             }
         };

         requestQueue.add(request); // Execution of HTTP Request


    }

    void showStudent(){
            final StringRequest stringRequest = new StringRequest(Request.Method.POST, Util.Retrieve_Teacher, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray jsonArray = jsonObject.getJSONArray("Registration");

                        int id=0;

                        for(int i=0;i<jsonArray.length();i++){
                            JSONObject jObj = jsonArray.getJSONObject(i);
                            name = jObj.getString("NAME");
                            email = jObj.getString("EMAIL");
                            password = jObj.getString("PASSWORD");
                            desig = jObj.getString("DESIGNATION");
                            Log.i("dp", desig);
                            add = jObj.getString("ADDRESS");
                            con = jObj.getString("CONTACT");
                            sal = jObj.getString("SALARY");
                            dt = jObj.getString("DATE");
                            Log.i("D", dt);
                            gen = jObj.getString("GENDER");
                            id =jObj.getInt("ID");

                            Intent intent=new Intent(AdminProfile.this,TeachersProfile.class);
                            intent.putExtra("names",name);
                            Log.i("saks",name);
                            intent.putExtra("emails",email);
                            intent.putExtra("passwords",password);
                            intent.putExtra("desigs",desig);
                            Log.i("anmol", desig);
                            intent.putExtra("adds",add);
                            intent.putExtra("cons",con);
                            intent.putExtra("sals",sal);
                            intent.putExtra("dts",dt);
                            intent.putExtra("gens",gen);
                            startActivity(intent);



                          //   new Teachersprofilebean(name,email,password,desig,add,con,sal,dt,gen);
                        }





                        progressDialog.dismiss();

                    }catch (Exception e){
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(AdminProfile.this,"Some Exception"+e,Toast.LENGTH_LONG).show();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(AdminProfile.this,"Some Error",Toast.LENGTH_LONG).show();
                }
            })

            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("EMAIL", mail);
                    Log.i("em", mail);

                    return map;
                }

            };
            requestQueue.add(stringRequest); // Execute the Request
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









