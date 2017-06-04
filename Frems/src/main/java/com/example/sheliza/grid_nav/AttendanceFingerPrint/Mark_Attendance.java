package com.example.sheliza.grid_nav.AttendanceFingerPrint;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mark_Attendance extends AppCompatActivity implements LocationListener, CompoundButton.OnCheckedChangeListener {

    CheckBox checkBox;
    RequestQueue requestQueue;
    LocationManager locationManager;
    ProgressDialog progressDialog;
    StringBuffer buffer = new StringBuffer();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String email, name;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark__attendance);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        sharedPreferences = getSharedPreferences(Util.PREFS_NAME, MODE_PRIVATE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        email = sharedPreferences.getString(Util.COL_EMAIL, "");
        name = sharedPreferences.getString(Util.COL_NAME, "");
        initViews();
        checkBox.setOnCheckedChangeListener(this);
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
            if (adrsList != null && adrsList.size() > 0) {
                Address address = adrsList.get(0);


                for (int i = 0; i < address.getMaxAddressLineIndex(); i++) {
                    buffer.append(address.getAddressLine(i) + "\n");
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
                        int success = jsonObject.getInt("success");
                        String message = jsonObject.getString("message");
                        if(success == 1){
                            Toast.makeText(Mark_Attendance.this, " Success "+message, Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Mark_Attendance.this, message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    progressDialog.dismiss();
                }


            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    progressDialog.dismiss();
                    Toast.makeText(Mark_Attendance.this, "Some Error" + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    String time = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(new Date());
                    String date = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

                    map.put("DATE", time);
                    map.put("ATTENDANCE", "Present");
                    map.put("LOCATION", buffer.toString());
                    map.put("EMAIL", email);
                    map.put("NAME", name);
                    map.put("NEWDATE", date);
                    return map;
                }
            };

            requestQueue.add(request); // execute the request, send it ti server


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }


    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        int id = buttonView.getId();
        if(id == R.id.checkBox){
            if(isChecked) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Please Grant Permissions from Settings", Toast.LENGTH_LONG).show();
                } else {
                    locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5, 10, this);
                    progressDialog.show();
                }
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
