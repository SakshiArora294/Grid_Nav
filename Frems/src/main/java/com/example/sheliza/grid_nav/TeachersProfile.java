package com.example.sheliza.grid_nav;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
//import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

public class TeachersProfile extends AppCompatActivity {
    @BindView(R.id.textView_Name)
    TextView name;
    @BindView(R.id.textView_Email_rcv)
    TextView Emailrcv;
    @BindView(R.id.textView_Password_rcv)
    TextView passwordrcv;
    @BindView(R.id.textView_Designation_rcv)
    TextView designationrcv;
    @BindView(R.id.textView_Address_rcv)
    TextView Addressrcv;
    @BindView(R.id.textView_Contact_rcv)
    TextView contactrcv;
    @BindView(R.id.textView_Salary_rcv)
    TextView Salaryrcv;
    @BindView(R.id.textView_Date_rcv)
    TextView datercv;
    @BindView(R.id.textView_Gender_rcv)
    TextView genderrcv;
    @BindView(R.id.image)
    CircleImageView imageView;

    String ename,eemail,epassword,edesignation,eaddress,econtact,esalary,edate,egender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_teacher);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        ename =    intent.getStringExtra("names");
        eemail =    intent.getStringExtra("emails");
        epassword = intent.getStringExtra("passwords");
        edesignation =  intent.getStringExtra("desigs");
        eaddress =  intent.getStringExtra("adds");
        econtact = intent.getStringExtra("cons");
        esalary =  intent.getStringExtra("sals");
        edate =  intent.getStringExtra("dts");
        egender = intent.getStringExtra("gens");
         Log.i("egender",egender);
        name.setText(ename);
        Emailrcv.setText(eemail);
        passwordrcv.setText(epassword);
        designationrcv.setText(edesignation);
        Addressrcv.setText(eaddress);
        contactrcv.setText(econtact);
        Salaryrcv.setText(esalary);
        datercv.setText(edate);
        genderrcv.setText(egender);


        String url="http://sakshi294.esy.es/insert/UPLOAD/"+ename+".JPG";
        Log.i("url",url.toString());
        Picasso.with(TeachersProfile.this).load(url).into(imageView);



    }

}
