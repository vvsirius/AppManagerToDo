package com.example.vv.appmanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{
    @Override
    public int getCount() {
        return 1000;
    }

    @Override
    public Object getItem(int i) {
        return i * 3;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.myitem, viewGroup, false);
        ImageView pic = view.findViewById(R.id.itemImage);
        TextView text = view.findViewById(R.id.itemText);
        text.setText(getItem(i) + "");
        if (i % 2 == 0) pic.setImageResource(android.R.drawable.star_big_on);
        else pic.setImageResource(android.R.drawable.star_big_off);
        return view;
    }
}
