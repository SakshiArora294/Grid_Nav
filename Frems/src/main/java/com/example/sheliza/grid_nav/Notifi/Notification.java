package com.example.sheliza.grid_nav.Notifi;

import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sheliza.grid_nav.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
//import butterknife.InjectView;

public class Notification extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.editText)
    EditText etxt;
    @BindView(R.id.button2)
    Button btn;
    @BindView(R.id.editText2)
    EditText etxt2;
    String message, title ;
    String string;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);
        string = new SimpleDateFormat("yyyy/MM/dd_HH:mm:ss").format(new Date());
        View v = findViewById(R.id.imageView);
        ObjectAnimator animation = ObjectAnimator.ofFloat(v, "rotationY", 0.0f, 360f);
        animation.setDuration(1000);
        //animation.setRepeatCount(ObjectAnimator.INFINITE);
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
        animation.start();
        requestQueue = Volley.newRequestQueue(this);
        btn.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        if(id==R.id.button2){
            message =   etxt.getText().toString().trim();
            title = etxt2.getText().toString().trim();
            Log.i("m",etxt2.getText().toString());
            Insertintoserver();
        }

    }

    void Insertintoserver() {
        progressDialog.show();
        StringRequest request = new StringRequest(Request.Method.POST, "http://sakshi294.esy.es/insert/send_message.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.i("test", response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");
                    String messages = jsonObject.getString("messages");

                    if (success == 1) {
                        Toast.makeText(Notification.this, message, Toast.LENGTH_LONG).show();
                        Toast.makeText(Notification.this, messages, Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(Notification.this, message, Toast.LENGTH_LONG).show();
                    }
                    progressDialog.dismiss();
                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Log.i("test", e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(Notification.this, "Some Error" + error.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("test", error.toString());
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("MESSAGE", message);
                map.put("TITLE", title);
                map.put("DATE", string);
                return map;
            }
        };
        requestQueue.add(request);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}






