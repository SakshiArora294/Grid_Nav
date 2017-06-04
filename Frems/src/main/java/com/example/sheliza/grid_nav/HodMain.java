package com.example.sheliza.grid_nav;

/**
 * Created by Sheliza on 17-05-2017.
 */
import android.content.SharedPreferences;
        import android.graphics.drawable.AnimationDrawable;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.GridView;
        import android.widget.ImageView;
        import android.widget.ListView;

        import java.util.ArrayList;

public class HodMain extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ImageView imageView;
    SharedPreferences sharedPreferences;
    GridView gridView;
    ArrayList<firstfragmentbean> userList;
    FirstFragmentAdapter userAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_fragment);
        sharedPreferences = getSharedPreferences(Util.PREFS_NAME, MODE_PRIVATE);
        imageView = (ImageView) findViewById(R.id.imageViewA);
        AnimationDrawable animationDrawable = (AnimationDrawable) imageView.getBackground();
        animationDrawable.start();
        initViews();
    }

    void initViews(){
        gridView = (GridView)findViewById(R.id.gridView);




        userList = new ArrayList<>();

        firstfragmentbean u1 = new firstfragmentbean(R.drawable.profile, "My Profile");
        firstfragmentbean u2 = new firstfragmentbean(R.drawable.reminder, "Reminder");
        firstfragmentbean u3 = new firstfragmentbean(R.drawable.attendance, "Attendance");
        firstfragmentbean u4 = new firstfragmentbean(R.drawable.notification, "Notification");
        firstfragmentbean u5 = new firstfragmentbean(R.drawable.salary, "Salary");
        firstfragmentbean u6 = new firstfragmentbean(R.drawable.timetable, "TimeTable");



        userList.add(u1); //0
        userList.add(u2);
        userList.add(u3);
        userList.add(u4);
        userList.add(u5); //n-1
        userList.add(u6);

        userAdapter = new FirstFragmentAdapter(this,R.layout.grid_item1,userList);
        gridView.setAdapter(userAdapter);
        gridView.setOnItemClickListener(this);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

}

