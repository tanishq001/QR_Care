package com.example.qrcare;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

public class ChooseAdapter extends ArrayAdapter<String> {
    private final Activity context;
    private final int[] imgid;
    String[] name;

    public ChooseAdapter(Activity context, String[] name, int[] imgid) {
        super(context, R.layout.choose_bag,name);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.imgid=imgid;
        this.name=name;

    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.choose_bag, null,true);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.layout_1);


        imageView.setImageResource(imgid[position]);
        return rowView;

    };
}
