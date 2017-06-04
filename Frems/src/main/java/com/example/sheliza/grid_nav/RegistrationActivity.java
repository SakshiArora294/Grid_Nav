package com.example.sheliza.grid_nav;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.media.session.MediaSession;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;


import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
//import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.sheliza.grid_nav.R.id.editTextsalary;

public class RegistrationActivity extends AppCompatActivity implements CompoundButton.OnCheckedChangeListener,View.OnClickListener {

    @BindView(R.id.editTextname)
    EditText eTxtName;
    @BindView(R.id.editTextEmail)
    EditText eTxtEmail;
    @BindView(R.id.spinner)
    Spinner spdesignation;
    @BindView(editTextsalary)
    EditText eTxtSalary;
    @BindView(R.id.editTextaddress)
    EditText eTxtAddress;
    @BindView(R.id.editTextcontact)
    EditText eTxtcontact;
    @BindView(R.id.imageView)
    CircleImageView imageView;
    @BindView(R.id.editTextDateofJoining)
    EditText eTxtdate;
    @BindView(R.id.radioButtonmale)
    RadioButton rbMale;
    @BindView(R.id.radioButtonfemale)
    RadioButton rbFemale;
    @BindView(R.id.buttonsubmit)
    Button btnSubmit;
    @BindView(R.id.editTextPassword)
    EditText password;

    String encodedImage,sdate;
    Teacher teacher, rcvteacher;
    ArrayAdapter<String> adapter;
    SharedPreferences sharedPreferences;

    RadioGroup radioGroup;


    String token;

    boolean updateMode;

    String gender;
    RequestQueue requestQueue;

    ProgressDialog progressDialog;

    ConnectivityManager connectivityManager;
    NetworkInfo networkInfo;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        radioGroup=(RadioGroup)findViewById(R.id.radioGroup);
        sharedPreferences=getSharedPreferences(Util.PREFS_NAME,MODE_PRIVATE);
        ButterKnife.bind(this);
        teacher = new Teacher();
        btnSubmit.setOnClickListener(this);
        imageView.setOnClickListener(this);
        eTxtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                show_Fdate_dialog();
            }
        });

        preferences = getSharedPreferences(Util.PREFS_NAME, MODE_PRIVATE);
        editor = preferences.edit();



        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait..");
        progressDialog.setCancelable(false);



        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item);
        adapter.add("--Select Designation--");
        adapter.add("HOD");
        adapter.add("Assistant Professor");


        spdesignation.setAdapter(adapter);

        spdesignation.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    teacher.setDesignation(adapter.getItem(i));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rbMale.setOnCheckedChangeListener(this);
        rbFemale.setOnCheckedChangeListener(this);

        requestQueue = Volley.newRequestQueue(this);

        Intent rcv = getIntent();
        updateMode = rcv.hasExtra("keyStudent");



        if(updateMode){

            rcvteacher = (Teacher) rcv.getSerializableExtra("keyStudent");
           eTxtName.setText(sharedPreferences.getString(Util.COL_NAME,""));
            //eTxtName.setText(rcvteacher.getName());
            eTxtEmail.setText(sharedPreferences.getString(Util.COL_EMAIL,""));
            eTxtAddress.setText(sharedPreferences.getString(Util.COL_ADDRESS,""));
            eTxtcontact.setText(sharedPreferences.getString(Util.COL_CONTACT,""));
            password.setText(sharedPreferences.getString(Util.COL_PASSWORD,""));
            Log.i("ssss",sharedPreferences.getString(Util.COL_PASSWORD,""));
            eTxtdate.setText(sharedPreferences.getString(Util.COL_DATEOFJOINING,""));
            eTxtSalary.setText(sharedPreferences.getString(Util.COL_SALARY,""));

                if(sharedPreferences.getString(Util.COL_GENDER,"").equals("Male")){
                     rbMale.setChecked(true);
                    gender = "Male";
            }else{
                 rbFemale.setChecked(true);
                    gender = "Female";
            }

            int p = 0;
            for(int i=0;i<adapter.getCount();i++){
                if(adapter.getItem(i).equals(sharedPreferences.getString(Util.COL_DESIGNATION,""))){
                    p = i;
                    break;
                }
            }


            spdesignation.setSelection(p);
            String url="http://sakshi294.esy.es/insert/UPLOAD/"+sharedPreferences.getString(Util.COL_NAME,"")+".JPG";
            Log.i("url",url.toString());
            Picasso.with(RegistrationActivity.this).load(url).into(imageView);
            btnSubmit.setText("Update");
        }
    }

    void setdata()
    {
        editor.clear();
        editor.putString(Util.COL_NAME,eTxtName.getText().toString());
        editor.putString(Util.COL_EMAIL,eTxtEmail.getText().toString());
        editor.putString(Util.COL_PASSWORD,password.getText().toString());
        editor.putString(Util.COL_DESIGNATION,spdesignation.getSelectedItem().toString());
        editor.putString(Util.COL_ADDRESS,eTxtAddress.getText().toString());
        editor.putString(Util.COL_CONTACT,eTxtcontact.getText().toString());
        editor.putString(Util.COL_SALARY,eTxtSalary.getText().toString());
        editor.putString(Util.COL_DATEOFJOINING,eTxtdate.getText().toString());
        editor.putString(Util.COL_GENDER,teacher.getGender());
        editor.commit();


    }

    boolean isNetworkConected() {
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Util.REQCODE && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            imageView.setImageURI(selectedImageUri);
        }
    }

    void show_Fdate_dialog()
    {
        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog dpd = new DatePickerDialog(this,sdate_click,y,m,d);
        dpd.show();
    }
    DatePickerDialog.OnDateSetListener sdate_click = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
            sdate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            eTxtdate.setText(sdate);
        }  };


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id==R.id.buttonsubmit){

            teacher.setName(eTxtName.getText().toString().trim());
            teacher.setEmail(eTxtEmail.getText().toString().trim());
            teacher.setPassword(password.getText().toString().trim());
            teacher.setDate(eTxtdate.getText().toString().trim());
            teacher.setAddress(eTxtAddress.getText().toString().trim());
            teacher.setContact(eTxtcontact.getText().toString().trim());
            teacher.setSalary(eTxtSalary.getText().toString().trim());
         //   Log.i("D",teacher.getDesignation().toString().trim());

            Bitmap image = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 10, byteArrayOutputStream);
            encodedImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
            Log.i("test",encodedImage);
          //  Toast.makeText(this, "encoded", Toast.LENGTH_LONG).show();

            if (validateFields()) {
                if (isNetworkConected()) {
                    insertIntoCloud();
                }else
                    Toast.makeText(this, " Please Connect to Internet ", Toast.LENGTH_LONG).show();
            } else
                Toast.makeText(this, "Please Enter Validate Fields", Toast.LENGTH_LONG).show();

        }


        if (id == R.id.imageView) {
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, Util.REQCODE);
        }
    }

    void insertIntoCloud() {
        String url = "";

        if (!updateMode) {
            url = Util.InsertRegistration;
        } else {
            url = Util.UpdateRegistration;
        }

        progressDialog.show();

        token= FirebaseInstanceId.getInstance().getToken();
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.i("test",response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int success = jsonObject.getInt("success");
                    String message = jsonObject.getString("message");

                    if (success == 1) {
                        Toast.makeText(RegistrationActivity.this, message, Toast.LENGTH_LONG).show();
                        if(!updateMode){

                            Intent home = new Intent(RegistrationActivity.this,LoginActivity.class);
                            startActivity(home);
                            finish();
                        }
                       else  if (updateMode){
                            setdata();
                            Intent data = new Intent();
                            data.putExtra(Util.keyresult, teacher);
                            setResult(Util.UPRESCODE, data);
                        //    Intent intent = new Intent(RegistrationActivity.this, MyProfileTeacher.class);
                        //    startActivity(intent);
                            finish();

                        }}
                    progressDialog.dismiss();
                }   catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(RegistrationActivity.this, "Some Exception", Toast.LENGTH_LONG).show();
                    Log.i("test",e.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(RegistrationActivity.this, "Some Error" + error.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("test",error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                if (!updateMode) {

                    map.put("NAME", teacher.getName());
                    map.put("EMAIL", teacher.getEmail());
                    map.put("PASSWORD", teacher.getPassword());
                    map.put("DESIGNATION", teacher.getDesignation());
                    map.put("ADDRESS", teacher.getAddress());
                    map.put("CONTACT", teacher.getContact());
                    map.put("SALARY", teacher.getSalary());
                    map.put("DATE", teacher.getDate());
                    map.put("GENDER", teacher.getGender());
                    map.put("IMAGENAME",encodedImage);
                    map.put("TOKEN",token);

                }
                else
                {
                    map.put("ID", sharedPreferences.getString(Util.COL_ID, String.valueOf(0)));
                    map.put("NAME", teacher.getName());
                    map.put("EMAIL", teacher.getEmail());
                    map.put("PASSWORD", teacher.getPassword());
                    map.put("DESIGNATION", teacher.getDesignation());
                    map.put("ADDRESS", teacher.getAddress());
                    map.put("CONTACT", teacher.getContact());
                    map.put("SALARY", teacher.getSalary());
                    map.put("DATE", teacher.getDate());
                    map.put("GENDER", teacher.getGender());
                    map.put("IMAGENAME",encodedImage);




                }
                // Log.i("IMAGENAME",student.toString()+"\n"+encodedImage);


                return map;
            }
        };

        requestQueue.add(request); // execute the request, send it ti server

        //clearFields();
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        int id = compoundButton.getId();

        if (b) {
            if (id == R.id.radioButtonmale) {
                teacher.setGender("Male");
            } else {
                teacher.setGender("Female");
            }
        }
    }

    boolean validateFields() {
        boolean flag = true;

        if (teacher.getName().isEmpty()) {
            flag = false;
            eTxtName.setError("Please Enter Name");
        }else if (teacher.getName().contains("0")||teacher.getName().contains("1")||teacher.getName().contains("2")||teacher.getName().contains("3")||
                teacher.getName().contains("4")||teacher.getName().contains("5")||teacher.getName().contains("6")||teacher.getName().contains("7")||
                teacher.getName().contains("8")||teacher.getName().contains("9")) {
            flag=false;
            eTxtName.setError("Name Can't be Numeric Value");
        }
        if (teacher.getPassword().isEmpty()) {
            flag = false;
            password.setError("Please Enter Password");
        } else
            if (teacher.getPassword().length() < 6) {
                flag = false;
                password.setError("Please Enter 6 digits Password");
            }

            if (teacher.getAddress().isEmpty()) {
                flag = false;
                eTxtAddress.setError("Please Enter Address");
            }else if(teacher.getAddress().length() < 10){
                flag = false;
                eTxtAddress.setError(" Address cannot be too short");
            }
            if (teacher.getDate().isEmpty()) {
                flag = false;
                eTxtdate.setError("Please Enter Date");
            }

            if (teacher.getContact().isEmpty()) {
                flag = false;
                eTxtcontact.setError("Please Enter Phone");
            } else
                if (teacher.getContact().length() < 10) {
                    flag = false;
                    eTxtcontact.setError("Please Enter 10 digits Phone Number");
                }


            if (teacher.getEmail().isEmpty()) {
                flag = false;
                eTxtEmail.setError("Please Enter Email");
            } else
                if (!(teacher.getEmail().contains("@") && teacher.getEmail().contains("."))) {
                    flag = false;
                    eTxtEmail.setError("Please Enter correct Email");
                }


            if (spdesignation.getSelectedItem().toString().trim().equals("--Select Designation--")) {
                flag=false;
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
            }
            if(radioGroup.getCheckedRadioButtonId()==-1){
                flag=false;
                rbMale.setError("Please Select The Gender");
                rbFemale.setError("Please Select The Gender");
            }
        if(teacher.getSalary().isEmpty()) {
            flag = false;
            eTxtSalary.setError("Please Enter Salary");
        }else if(teacher.getSalary().startsWith("0")){
            flag = false;
            eTxtSalary.setError(" Salary cannot be zero ");
        }

        return flag;
    }



}
