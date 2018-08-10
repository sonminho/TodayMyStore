package com.example.a01020072846.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {
    private ArrayList<Item> list;

    public ItemAdapter(ArrayList<Item> list) {
            this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        final int pos = position;

        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.item, parent, false);
        }

        TextView nameTextView = (TextView) convertView.findViewById(R.id.item_name);
        TextView priceTextView = (TextView) convertView.findViewById(R.id.item_price);

        Item item = (Item) list.get(pos);

        Log.d(item.getItemName(),"TTTTTT");
        Log.d(item.getUnitPrice(),"TTTTTT");
        nameTextView.setText(item.getItemName());
        priceTextView.setText(item.getUnitPrice());

        return convertView;
    }

    public void addItem(String type, String name, String price) {
        Item item = new Item(type, name, price);

        list.add(item);
    }



    public void setList(ArrayList<Item> list) {
        this.list = list;
    }

    public ArrayList<Item> getList() {
        return this.list;
    }
}
