package com.example.sheliza.grid_nav.Salary;

import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
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
import com.example.sheliza.grid_nav.AttendanceFingerPrint.AttendanceAdapter;
import com.example.sheliza.grid_nav.AttendanceFingerPrint.AttendanceBean;
import com.example.sheliza.grid_nav.AttendanceFingerPrint.AttendanceView;
import com.example.sheliza.grid_nav.AttendanceFingerPrint.SuceedActivity;
import com.example.sheliza.grid_nav.R;
import com.example.sheliza.grid_nav.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;



import static android.R.id.list;
import static com.example.sheliza.grid_nav.R.id.listView;

public class SalaryCalculate extends AppCompatActivity {

 TextView txtsalary;

    int currentMonth,lastmonth;
    ListView listView;
    ArrayList< String> list;
    int SalaryDeducted;
    String newSalary;
    int C;
    String first;



    ArrayAdapter<String> Adapter;
    RequestQueue requestQueue;
    SharedPreferences sharedPreferences;
    String email,sal;
    int i=0;
    int salary,salaryy;
    Date a = null;
    Date b =null;
    int Count;
    int month=0;
    int months;
    String lastdate ="";
    long elapsedDays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_calculate);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Salary Details");
        txtsalary=(TextView)findViewById(R.id.textViewsalary) ;
        sharedPreferences = getSharedPreferences(Util.PREFS_NAME,MODE_PRIVATE);
        requestQueue= Volley.newRequestQueue(this);
        email=sharedPreferences.getString(Util.COL_EMAIL,"");
        Log.i("shag",email);
        sal=sharedPreferences.getString(Util.COL_SALARY,"");
        salary=Integer.parseInt(sal);
        Log.i("salary",salary+"");

        list=new ArrayList<String>();


        listView=(ListView)findViewById(R.id.listview);

        Calendar calendar = Calendar.getInstance();
        currentMonth = calendar.get(Calendar.MONTH) + 1;
        lastmonth=currentMonth-1;
        Log.i("monthss", String.valueOf(currentMonth));
        Calculate();
        Log.i("SALARY",salary+"");
        RetrieveFromServer();
        RetrieveFromServerlast();


        Log.i("Count", C + "");

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    void Calculate(){
        if(currentMonth==2){
            salaryy=salary/28;
        }
        else if(currentMonth==1||currentMonth==3||currentMonth==5||currentMonth==7||currentMonth==9||currentMonth==10||currentMonth==12){
            salaryy=salary/31;
            Log.i("perday",salaryy+"");
        }
        else {
            salaryy=salary/30;
        }
    }

    void RetrieveFromServer(){
        final StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://sakshi294.esy.es/insert/RetreiveAbsent.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Info");
                    String n ="";
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        n = jObj.getString("DATE");

                        Log.i("n",n);
                        list.add(n);
                    }

                   first=list.get(0);
                    Log.i("firsst",first);

                    Adapter = new ArrayAdapter<>(SalaryCalculate.this,android.R.layout.simple_list_item_1,list);
                    listView.setAdapter(Adapter);

                }catch (Exception e){
                    e.printStackTrace();

                    Toast.makeText(SalaryCalculate.this,"Some Exception"+e,Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SalaryCalculate.this,"Some Error",Toast.LENGTH_LONG).show();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("EMAIL",email );
                map.put("MONTH", String.valueOf(currentMonth));
                Log.i("SHAG",email);

                return map;
            }

        };
        requestQueue.add(stringRequest); // Execute the Request
    }

    void fun() {
     Count=0;

        SimpleDateFormat simpleDateFormat =
                new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

     try {
         a = simpleDateFormat.parse(first);
         Log.i("aaa",a+"");
         b = simpleDateFormat.parse(lastdate);
         Log.i("bbb",b+"");
     } catch (ParseException e) {
         e.printStackTrace();
     }
printDifference(b,a);

 }


    void RetrieveFromServerlast(){
        final StringRequest stringRequest = new StringRequest(Request.Method.POST,"http://sakshi294.esy.es/insert/RetrieveLastMonth.php", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Info");

                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jObj = jsonArray.getJSONObject(i);
                        lastdate = jObj.getString("DATE");


                        Log.i("lastdate",lastdate);
                        fun();
                    }
                }catch (Exception e){
                    e.printStackTrace();

                    Toast.makeText(SalaryCalculate.this,"Some Exception"+e,Toast.LENGTH_LONG).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(SalaryCalculate.this,"Some Error",Toast.LENGTH_LONG).show();
            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("EMAIL",email );
                map.put("LASTMONTH", String.valueOf(lastmonth));
                Log.i("SHAG",email);

                return map;
            }

        };
        requestQueue.add(stringRequest); // Execute the Request
    }

    public int printDifference(Date startDate, Date endDate){

        //milliseconds
        long different = endDate.getTime() - startDate.getTime();
        long secondsInMilli = 1000;
        long minutesInMilli = secondsInMilli * 60;
        long hoursInMilli = minutesInMilli * 60;
        long daysInMilli = hoursInMilli * 24;

       elapsedDays= different / daysInMilli;
      Log.i("elapsedDays",elapsedDays+"");
        long diff=elapsedDays;

        Log.i("diff", String.valueOf(diff));

        if (diff>37) {

            C = list.size()-1;
            Log.i("Countt", C + "");
        }
        else {
            C =list.size();
            Log.i("Countt", C + "");

        }

        SalaryDeducted=salaryy*C;
        newSalary= String.valueOf(salary-SalaryDeducted);
        Log.i("newsalary",newSalary+"");
        txtsalary.setText(newSalary);


        Log.i("ii",i+"");
        return 0;
    }


}


























