package com.example.sheliza.grid_nav.AttendanceFingerPrint;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sheliza.grid_nav.R;

import java.util.ArrayList;

/**
 * Created by Sheliza on 19-05-2017.
 */

public class AdminAttendanceAdapter extends ArrayAdapter<TeacherName> {
    Context context;
    int resource;
    ArrayList<TeacherName> studentList,tempList;

    public AdminAttendanceAdapter(Context context, int resource, ArrayList<TeacherName> objects) {
        super(context, resource, objects);

        this.context = context;
        this.resource = resource;
        studentList = objects;
        tempList = new ArrayList<>();
        tempList.addAll(studentList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = null;

        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(resource,parent,false);

        TextView txtName = (TextView)view.findViewById(R.id.teachername);
        TextView txtemail = (TextView)view.findViewById(R.id.teacheremail);


        TeacherName teacherName = studentList.get(position);
        txtName.setText(teacherName.getName());
        txtemail.setText(teacherName.getEmail());
        Log.i("txtemail",txtemail.toString());




        return view;
    }

    public void filter(String str){

        studentList.clear();

        if(str.length()==0){
            studentList.addAll(tempList);
        }else{
            for(TeacherName s : tempList){
                if(s.getName().toLowerCase().contains(str.toLowerCase())){
                    studentList.add(s);
                }
            }
        }

        notifyDataSetChanged();
    }
}

