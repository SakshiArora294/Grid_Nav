package com.example.sheliza.grid_nav.Notifi;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sheliza.grid_nav.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Get extends AppCompatActivity {

    ListView listView;
    ArrayList<Bean> arrayList;
    UserAdaptar userAdaptar;
    RequestQueue requestQueue;
    Bean bean;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        requestQueue = Volley.newRequestQueue(this);
        listView = (ListView) findViewById(R.id.listView);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCancelable(false);
        viewNotification();
    }

    public void viewNotification() {
        progressDialog.show();
        arrayList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://sakshi294.esy.es/insert/NotiRetrieve.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Notification");
                    String t="",m="",d="";
                    for(int i = 0;i< jsonArray.length(); i++){
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        t = jObj.getString("Title");
                        m = jObj.getString("Message");
                        d = jObj.getString("Date");
                        Log.i("name", t);

                        arrayList.add(new Bean(t, m, d));
                    }
                    userAdaptar = new UserAdaptar(Get.this, R.layout.list_item, arrayList);
                    listView.setAdapter(userAdaptar);
                    Toast.makeText(Get.this, " Success ", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(Get.this, " Some Error" + response, Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(Get.this, " Some Exception " + error, Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
