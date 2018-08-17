package com.example.a01020072846.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class TrasactionAdapter extends BaseAdapter {
    private ViewHolder viewHolder;
    private ArrayList<Item> list = null;
    private Context mContext = null;

    public TrasactionAdapter(Context mContext, ArrayList<Item> list) {
        this.mContext = mContext;
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
            viewHolder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.transaction_item, parent, false);

            viewHolder.itemName = (TextView) convertView.findViewById(R.id.tx_item_name);
            viewHolder.itemCount = (EditText) convertView.findViewById(R.id.tx_item_count);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Item item = (Item) list.get(pos);
        viewHolder.itemName.setText(item.getItemName());

        if(item.getItemCount() == null)
            viewHolder.itemCount.setText("0");
        else
            viewHolder.itemCount.setText(item.getItemCount());

        return convertView;
    }

    static class ViewHolder {
        private TextView itemName;
        private EditText itemCount;
    }

}