package com.example.sheliza.grid_nav;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;



import android.widget.TextView;

import java.util.ArrayList;

import java.util.ArrayList;



    public class FirstFragmentAdapter extends ArrayAdapter {

        Context ctx;  int resource;  ArrayList<firstfragmentbean> objects;

        public FirstFragmentAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull ArrayList<firstfragmentbean> objects) {
            super(context, resource, objects);
            ctx=context;
            this.resource=resource;
            this.objects=objects;
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View view =null;
            view = LayoutInflater.from(getContext()).inflate(resource,parent,false);

            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            TextView textView=(TextView)view.findViewById(R.id.textView);

            firstfragmentbean bean = objects.get(position);
            imageView.setBackgroundResource(bean.getImage());
            textView.setText(bean.getText());
            return view;
        }
    }

