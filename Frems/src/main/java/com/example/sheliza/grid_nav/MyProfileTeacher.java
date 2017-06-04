package com.example.sheliza.grid_nav;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sheliza.grid_nav.AttendanceFingerPrint.AttendanceView;
import com.example.sheliza.grid_nav.AttendanceFingerPrint.SuceedActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
//import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileTeacher extends AppCompatActivity {
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
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Teacher teacher;
    String ename,eemail,epassword,edesignation,eaddress,econtact,esalary,edate,egender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile_teacher);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sharedPreferences = getSharedPreferences(Util.PREFS_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        ename=sharedPreferences.getString(Util.COL_NAME,"");
        Log.i("name",ename);
        name.setText(ename.toString());

        eemail=sharedPreferences.getString(Util.COL_EMAIL,"");
        Log.i("email",eemail);
        Emailrcv.setText(eemail);

        epassword=sharedPreferences.getString(Util.COL_PASSWORD,"");
        Log.i("password",epassword);
        passwordrcv.setText(epassword);

        edesignation=sharedPreferences.getString(Util.COL_DESIGNATION,"");
        Log.i("designation",edesignation);
        designationrcv.setText(edesignation);

        eaddress=sharedPreferences.getString(Util.COL_ADDRESS,"");
        Log.i("designation",edesignation);
        Addressrcv.setText(eaddress);


        econtact=sharedPreferences.getString(Util.COL_CONTACT,"");
        Log.i(" Contact",edesignation);
        contactrcv.setText(econtact);

        esalary=sharedPreferences.getString(Util.COL_SALARY,"");
        Log.i("designation",edesignation);
        Salaryrcv.setText(esalary);


        edate=sharedPreferences.getString(Util.COL_DATEOFJOINING,"");
        Log.i("designation",edesignation);
        datercv.setText(edate);

        egender=sharedPreferences.getString(Util.COL_GENDER,"");
        Log.i("designation",edesignation);
        genderrcv.setText(egender);


        String url="http://sakshi294.esy.es/insert/UPLOAD/"+ename+".JPG";
        Log.i("url",url.toString());
        Picasso.with(MyProfileTeacher.this).load(url).into(imageView);




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(0,101,0,"Update");


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id==101){

            Intent intent=new Intent(MyProfileTeacher.this,RegistrationActivity.class);
            intent.putExtra("keyStudent",teacher);
            startActivityForResult(intent, Util.UPREQCODE);
        }else if(item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){



        if (requestCode == Util.UPREQCODE && resultCode == Util.UPRESCODE) {
            Teacher ub = (Teacher) data.getSerializableExtra(Util.keyresult);
            name.setText(ub.getName());
            Emailrcv.setText(ub.getEmail());
            passwordrcv.setText(ub.getPassword());
            designationrcv.setText(ub.getDesignation());
            Addressrcv.setText(ub.getAddress());
            contactrcv.setText(ub.getContact());
            Salaryrcv.setText(ub.getSalary());
            datercv.setText(ub.getDate());
            genderrcv.setText(ub.getGender());


            String url="http://sakshi294.esy.es/insert/UPLOAD/"+ub.getName()+".JPG";
            Log.i("url",url.toString());
            Picasso.with(MyProfileTeacher.this).load(url).into(imageView);

        }}


}
