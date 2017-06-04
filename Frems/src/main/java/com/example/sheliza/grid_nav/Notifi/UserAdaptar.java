package com.example.sheliza.grid_nav.Notifi;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.sheliza.grid_nav.R;

import java.util.ArrayList;

/**
 * Created by Sakshi on 17-May-17.
 */

public class UserAdaptar extends ArrayAdapter<Bean> {

    Context context;
    int resourse;
    ArrayList<Bean> arrayList;

    public UserAdaptar(Context context, int resource, ArrayList<Bean> arrayList) {
        super(context, resource, arrayList);

        this.context = context;
        this.resourse = resource;
        this.arrayList = arrayList;
    }


    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = null;
        view = LayoutInflater.from(context).inflate(resourse, parent, false);
        TextView title = (TextView)view.findViewById(R.id.title);
        TextView message = (TextView)view.findViewById(R.id.message);
        TextView date = (TextView)view.findViewById(R.id.date);

        Bean bean = arrayList.get(position);
        title.setText(bean.getTitle());
        message.setText(bean.getMessage());
        date.setText(bean.getDate());
        return view;

    }
}
