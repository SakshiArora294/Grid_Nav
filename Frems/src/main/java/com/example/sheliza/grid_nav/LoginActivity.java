package com.example.sheliza.grid_nav;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class
LoginActivity extends AppCompatActivity implements View.OnClickListener {

    TextView newUser, show;
    EditText editEmail;
    EditText editPassword;
    Button btnLogin;
    String emails;
    String password;
    RequestQueue requestQueue, requestQueues;

    int success;
    String message;
    int id = 0;
    String n = "", p = "", e = "", d = "", a = "", c = "", s = "", dt = "", g = "";

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String designation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        requestQueue = Volley.newRequestQueue(this);
        newUser = (TextView) findViewById(R.id.text);

        show=(TextView)findViewById(R.id.textView15);
        show.setVisibility(View.GONE);
        editEmail = (EditText) findViewById(R.id.editEmail);
        editPassword = (EditText) findViewById(R.id.editPassword);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(this);
        newUser.setOnClickListener(this);
        sharedPreferences = getSharedPreferences(Util.PREFS_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        editPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        editPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(editPassword.getText().length()>0){
                    show.setVisibility(View.VISIBLE);
                }
                else {
                    show.setVisibility(View.GONE);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(show.getText()=="SHOW"){
                    show.setText("HIDE");
                    editPassword.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    editPassword.setSelection(editPassword.length());

                }
                else {
                    show.setText("SHOW");
                    editPassword.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    editPassword.setSelection(editPassword.length());

                }
            }
        });
    }



    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.text) {
            Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
            startActivity(intent);
        } else if (id == R.id.btnLogin) {
            emails = editEmail.getText().toString().trim();
            Log.i("eeee", emails);
            password = editPassword.getText().toString().trim();
            if (emails.isEmpty() && password.isEmpty()) {
                Toast.makeText(this, " Please Enter Your Details ", Toast.LENGTH_SHORT).show();
            }
            checkLogin();
        }
    }

    public void checkLogin() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Util.LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    success = jsonObject.getInt("success");
                    message = jsonObject.getString("message");
                    if (success == 1) {
                        Toast.makeText(LoginActivity.this, " Login Successfully " + response, Toast.LENGTH_SHORT).show();
                        retrieveFromCloud();
                        designation = sharedPreferences.getString(Util.COL_DESIGNATION,"");
                        if(designation.equals("HOD")){
                            Log.i("desg",designation);
                            Intent intent = new Intent(LoginActivity.this, MainActivityofHOD.class);
                            startActivity(intent);
                            finish();
                        }else if(designation.equals("Assistant Professor")){
                            Intent intent1 = new Intent(LoginActivity.this, HomeActivityTeacher.class);
                            startActivity(intent1);
                            finish();
                        }

                        }
                     else {
                        Toast.makeText(LoginActivity.this, " Authentication Failed ", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, " Some Error " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("EMAIL", emails);
                map.put("PASSWORD", password);
                return map;
            }
        };

        requestQueue.add(stringRequest);

    }

    void retrieveFromCloud() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Util.Retrieve_Teacher, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Registration");


                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        n = jObj.getString("NAME");
                        e = jObj.getString("EMAIL");
                        p = jObj.getString("PASSWORD");
                        d = jObj.getString("DESIGNATION");
                        Log.i("desig",d);
                        a = jObj.getString("ADDRESS");
                        c = jObj.getString("CONTACT");
                        s = jObj.getString("SALARY");
                        dt = jObj.getString("DATE");
                        g = jObj.getString("GENDER");
                        id =jObj.getInt("ID");
                        Log.i("n", d);
                    }
                    shared();


                } catch (Exception e) {
                    e.printStackTrace();

                    // Toast.makeText(LoginActivity.this, "Some Exception", Toast.LENGTH_LONG).show();
                }

                Toast.makeText(LoginActivity.this, " Success ", Toast.LENGTH_SHORT).show();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(LoginActivity.this, "Some Error", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("EMAIL", emails);
                Log.i("emails", emails);
                return map;
            }

        };
        requestQueue.add(stringRequest); // Execute the Request
    }

    void shared() {
        editor.putString(Util.COL_PASSWORD, p);
        editor.putString(Util.COL_EMAIL, e);
        editor.putString(Util.COL_NAME, n);
        editor.putString(Util.COL_DESIGNATION, d);
        Log.i("yo", d);
        editor.putString(Util.COL_ADDRESS, a);
        editor.putString(Util.COL_SALARY, s);
        editor.putString(Util.COL_DATEOFJOINING, dt);
        editor.putString(Util.COL_GENDER, g);
        editor.putString(Util.COL_CONTACT, c);
        editor.putString(Util.COL_ID, String.valueOf(id));
        editor.commit();

    }
}

