package com.example.a01020072846.myapplication;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class ItemAdapter extends BaseAdapter {
    private  ViewHolder viewHolder;
    private ArrayList<Item> list;
    private boolean[] isCheckedConfrim;
    private Context mContext = null;

    public ItemAdapter() {

    }

    public ItemAdapter(Context context, ArrayList<Item> list) {
        this.mContext = context;
        this.list = list;
        isCheckedConfrim = new boolean[list.size()];

    }

    // CheckBox를 모두 선택하는 메서드
    public void setAllChecked(boolean ischeked) {
        int tempSize = isCheckedConfrim.length;
        for(int a=0 ; a<tempSize ; a++){
            isCheckedConfrim[a] = ischeked;
        }
    }

    public void setChecked(int position) {
        isCheckedConfrim[position] = !isCheckedConfrim[position];
    }

    public ArrayList<Item> getChecked(){
        int tempSize = isCheckedConfrim.length;
        ArrayList<Item> mArrayList = new ArrayList<Item>();
        for(int b=0 ; b<tempSize ; b++){
            if(isCheckedConfrim[b]){
                mArrayList.add(list.get(b));
            }
        }
        return mArrayList;
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
            convertView = inflater.inflate(R.layout.item, parent, false);

            viewHolder.checkBox = (CheckBox) convertView.findViewById(R.id.item_check);
            viewHolder.itemName = (TextView) convertView.findViewById(R.id.item_name);
            viewHolder.itemPrice = (TextView) convertView.findViewById(R.id.item_price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Item item = (Item) list.get(pos);
        viewHolder.checkBox.setFocusable(false);
        viewHolder.checkBox.setClickable(false);
        viewHolder.itemName.setText(item.getItemName());
        viewHolder.itemPrice.setText(item.getUnitPrice());

        return convertView;
    }

    static class ViewHolder {
        private CheckBox checkBox;
        private TextView itemName;
        private TextView itemPrice;
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