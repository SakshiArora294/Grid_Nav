package com.example.sheliza.grid_nav.TimeTable;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sheliza.grid_nav.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.ScrollView;
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


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sheliza on 21-05-2017.
 */


public class Time_Table extends AppCompatActivity {

        Bitmap bitmap;
        String  encodedImage;
        HorizontalScrollView rl;
        Bitmap returnedBitmap;
        RequestQueue requestQueue;

        String name,email;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_timetablemain);
            Intent rcv=getIntent();
            name= rcv.getStringExtra("name");
            email= rcv.getStringExtra("email");
            setTitle(" Time Table of "+name);
            requestQueue = Volley.newRequestQueue(this);
            rl = (HorizontalScrollView) findViewById(R.id.scroll);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {

            menu.add(0, 101, 0, "Upload To Server ");
            menu.add(0, 102, 0, " Save To Gallery ");
            return super.onCreateOptionsMenu(menu);
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
            int id = item.getItemId();
            if(id == 101){
                //   bitmap = getBitmapFromView(rl);
                //      encodedImage = ImageBase64.encode(bitmap);
                //     Log.i("image",encodedImage);
                View u = this.findViewById(R.id.scroll);

                HorizontalScrollView z = (HorizontalScrollView) this.findViewById(R.id.scroll);
                ScrollView v = (ScrollView)this.findViewById(R.id.rl);
                int totalWidth = z.getChildAt(0).getWidth();
                int totalHeight = v.getChildAt(0).getHeight();
                returnedBitmap = getBitmapFromView(u,totalWidth, totalHeight);

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                returnedBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);




                Log.i("image",encodedImage);
                insertIntoCloud();
            }else if(id == 102){
                //  startSave();
                takeScreenShot();


            }else if(id == android.R.id.home){
                finish();

            }
            return super.onOptionsItemSelected(item);
        }

        public void startSave() {
            FileOutputStream fileOutputStream = null;
            File file = getDisc();
            if(!file.exists() && !file.mkdir()){
                Toast.makeText(this, " Can't create directory to save Image ", Toast.LENGTH_SHORT).show();
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yymmddhhmmss");
            String date = simpleDateFormat.format(new Date());
            String name = "Img"+date+".jpg";
            String file_name = file.getAbsolutePath()+"/"+name;
            File new_file = new File(file_name);

            try {
                fileOutputStream = new FileOutputStream(new_file);
                Bitmap bitmap = getBitmapFromView(rl, rl.getWidth(), rl.getHeight());
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                Toast.makeText(this, " Save Image Success ", Toast.LENGTH_SHORT).show();
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            refreshGallery(new_file);
        }

        public void refreshGallery(File file) {
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(file));
            sendBroadcast(intent);
        }

        private File getDisc(){

            File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
            return new File(file,"Image Demo");
        }


        private void takeScreenShot()
        {
            //Save bitmap
            FileOutputStream fileOutputStream = null;
           File file = getDisc();

            if(!file.exists() && !file.mkdir()){
                Toast.makeText(this, " Can't create directory to save Image ", Toast.LENGTH_SHORT).show();
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yymmddhhmmss");
            String date = simpleDateFormat.format(new Date());
            String name = "Img"+date+".jpg";
            String file_name = file.getAbsolutePath()+"/"+name;
            File new_file = new File(file_name);

            try {
                View u = this.findViewById(R.id.scroll);

                HorizontalScrollView z = (HorizontalScrollView) this.findViewById(R.id.scroll);
                ScrollView v = (ScrollView)this.findViewById(R.id.rl);
                int totalWidth = z.getChildAt(0).getWidth();
                int totalHeight = v.getChildAt(0).getHeight();
                fileOutputStream = new FileOutputStream(new_file);
                Bitmap bitmap = getBitmapFromView(u, totalWidth, totalHeight);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                Toast.makeText(this, " Save Image Success ", Toast.LENGTH_SHORT).show();
                fileOutputStream.flush();
                fileOutputStream.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            refreshGallery(new_file);

        }

        public Bitmap getBitmapFromView(View view, int totalWidth, int totalHeight) {

            Bitmap returnedBitmap = Bitmap.createBitmap(totalWidth,totalHeight , Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(returnedBitmap);
            Drawable bgDrawable = view.getBackground();
            if (bgDrawable != null)
                bgDrawable.draw(canvas);
            else
                canvas.drawColor(Color.WHITE);
            view.draw(canvas);
            return returnedBitmap;
        }
        public void insertIntoCloud(){

            StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://sakshi294.esy.es/insert/Time_Table.php", new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(com.example.sheliza.grid_nav.TimeTable.Time_Table.this, " Success " + response, Toast.LENGTH_SHORT).show();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(com.example.sheliza.grid_nav.TimeTable.Time_Table.this, " Error " + error, Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> map = new HashMap<>();
                    map.put("encodedImage", encodedImage);
                    map.put("email",email);
                    Log.i("saks",email);

                    return map;
                }
            };
            requestQueue.add(stringRequest);
        }


}


