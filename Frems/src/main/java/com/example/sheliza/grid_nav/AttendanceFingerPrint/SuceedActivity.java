package com.example.sheliza.grid_nav.AttendanceFingerPrint;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sheliza.grid_nav.R;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sheliza.grid_nav.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



    public class SuceedActivity extends AppCompatActivity implements LocationListener {
        TextView txtLocation,txtTime,TxtAttendance;

        Button btnFetch;
        RequestQueue requestQueue;
        LocationManager locationManager;
        ProgressDialog progressDialog;
        StringBuffer buffer = new StringBuffer();
        SharedPreferences sharedPreferences;
        SharedPreferences.Editor editor;
        String email,name;
        ListView listView;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_finger_print_main);
            initViews();
            sharedPreferences = getSharedPreferences(Util.PREFS_NAME, MODE_PRIVATE);

           email= sharedPreferences.getString(Util.COL_EMAIL,"");
            name=sharedPreferences.getString(Util.COL_NAME,"");
            Log.i("saks",email);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this,"Please Grant Permissions from Settings",Toast.LENGTH_LONG).show();
            }else{
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5, 10, this);
                progressDialog.show();
            }

        }



        void initViews() {
            requestQueue = Volley.newRequestQueue(this);
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Fetching Location...");
            progressDialog.setCancelable(false);

        }



        // After every 5 or 10
        @Override
        public void onLocationChanged(Location location) {

            // Geocoding
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();

            // txtLocation.setText("Location: "+latitude+" : "+longitude);

            progressDialog.dismiss();

            // No More Location Updates Required
            locationManager.removeUpdates(this);

            // Reverse Geocoding
            // We fetch the address from latitude and longitude

            try {
                Geocoder geocoder = new Geocoder(this);
                List<Address> adrsList = geocoder.getFromLocation(latitude, longitude, 2);
                if(adrsList!=null && adrsList.size()>0){
                    Address address = adrsList.get(0);


                    for(int i=0;i<address.getMaxAddressLineIndex();i++){
                        buffer.append(address.getAddressLine(i)+"\n");
                    }
                    // student.setLocation(buffer.toString())  txtLocation.setText(buffer.toString());
                }
                //insert into cloud
                progressDialog.show();
                StringRequest request = new StringRequest(Request.Method.POST, Util.INSERT_STUDENT_RECORD, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {


                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        int success = 0;
                        try {
                            success = jsonObject.getInt("success");
                            String message = jsonObject.getString("message");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressDialog.dismiss();
                    }


                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(SuceedActivity.this,"Some Error"+error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                })
                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> map = new HashMap<>();
                        String time=new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(new Date());

                        map.put("DATE",time);
                        map.put("ATTENDANCE","Present");
                        map.put("LOCATION",buffer.toString());
                        map.put("EMAIL",email);
                        map.put("NAME",name);



                        return map;
                    }
                };

                requestQueue.add(request); // execute the request, send it ti server





            }catch (Exception e){
                e.printStackTrace();
            }

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }



        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            menu.add(0,101,0,"View");


            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

            int id = item.getItemId();
            if(id==101){
                  //  RetrieveFromServer();
               Intent intent=new Intent(SuceedActivity.this,AttendanceView.class);
                startActivity(intent);
            }


            return super.onOptionsItemSelected(item);
        }

        void RetrieveFromServer(){
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


                            AlertDialog.Builder builder = new AlertDialog.Builder(SuceedActivity.this);

                            builder.setMessage("Info are"+"\n"+

                                    "Date is :" + " "+n + "\n"+
                                    "Location is :" +"  "+ p+"\n"+
                                    "Attendance is :" +"  "+e+"\n"+
                                    "Name is :" +"  "+ f+"\n"+
                                    "Email is :" +"  "+g
                            );
                            builder.setPositiveButton("Done",null);
                            builder.create().show();


                        }
                        progressDialog.dismiss();

                    }catch (Exception e){
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(SuceedActivity.this,"Some Exception"+e,Toast.LENGTH_LONG).show();
                    }


                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(SuceedActivity.this,"Some Error",Toast.LENGTH_LONG).show();
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

    }
