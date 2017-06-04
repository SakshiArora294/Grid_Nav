package com.example.sheliza.grid_nav.Salary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sheliza.grid_nav.R;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SalaryInfo extends AppCompatActivity {

        ListView listview;
        ArrayList<String> list;
        ArrayAdapter<String> adapter;

        private Calendar cal = null;
        private int year = -1;

        private ArrayList weekendList = null;

        RequestQueue requestQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salary_info);


    listview=(ListView)findViewById(R.id.listview);
            requestQueue = Volley.newRequestQueue(this);
            list=new ArrayList<>();
            fun(2017);
            findWeekends();
            displayWeekends();


            insertIntoServer();

        }

        void fun(int year){
            this.year = year;

            cal = Calendar.getInstance();
            try{
                // Sets the date to the 1st day of the 1st month of the specified year
                cal.setTime(new SimpleDateFormat("dd/MM/yyyy").parse("01/01/" + this.year));
            }catch(java.text.ParseException e){
                System.err.println("Error parsing date: " + e.getMessage());
                e.printStackTrace();
            }

            weekendList = new ArrayList(53);
        }

        public void findWeekends(){
            // The while loop ensures that you are only checking dates in the specified year
            while(cal.get(Calendar.YEAR) == this.year){
                // The switch checks the day of the week for Saturdays and Sundays
                switch(cal.get(Calendar.DAY_OF_WEEK)){
                    case Calendar.SATURDAY:
                    case Calendar.SUNDAY:
                        weekendList.add(cal.getTime());
                        break;
                }
                // Increment the day of the year for the next iteration of the while loop
                cal.add(Calendar.DAY_OF_YEAR, 1);
            }
        }

        // Convenience method which just displays the contents of the ArrayList to the console
        public void displayWeekends() {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           for (int i = 0; i < weekendList.size(); i++) {

               list.add((sdf.format((Date) weekendList.get(i))));
               Log.i("app",sdf.format((Date) weekendList.get(i)));
           }
            list.add("2017-01-05");
            list.add("2017-02-26");
            list.add("2017-02-10");
            list.add("2017-02-24");
            list.add("2017-03-13");
            list.add("2017-03-23");
            list.add("2017-04-04");
            list.add("2017-04-13");
            list.add("2017-04-14");
            list.add("2017-05-29");
            list.add("2017-06-26");
            list.add("2017-08-15");
            list.add("2017-10-02");
            list.add("2017-10-05");
            list.add("2017-10-19");
            list.add("2017-10-20");
            list.add("2017-11-23");
            list.add("2017-12-25");
            Log.i("iiiii", String.valueOf(list.size()));
            Log.i("lissst",list.toString());



            adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
            listview.setAdapter(adapter);

        }

        void insertIntoServer(){
            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sakshi294.esy.es/insert/Date.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(SalaryInfo.this, " Success "+ response, Toast.LENGTH_SHORT).show();
                    Log.i("response",response);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Toast.makeText(SalaryInfo.this, " Error "+ error, Toast.LENGTH_SHORT).show();
                    Log.i("error",error.toString());
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    StringBuffer stringBuffer = new StringBuffer();
                    map.put("LIST", stringBuffer.append(list+",").toString());
                    Log.i("listssss", stringBuffer.append(list.toString() + ",").toString() );
                    return map;
                }

            };
            requestQueue.add(stringRequest);
        }


    }

    //public static void main(String[] args){
    // The program requires one argument of a year, so for example you could run the program with "java WeekendCalculator 2006"








