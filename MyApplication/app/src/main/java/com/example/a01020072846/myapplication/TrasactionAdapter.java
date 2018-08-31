package com.example.a01020072846.myapplication;

import android.content.Context;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class TrasactionAdapter extends BaseAdapter {
    private ViewHolder viewHolder;
    private ArrayList<Item> itemList = null;
    private ArrayList<Txn> txnList = null;
    private Context mContext = null;
    private int type;
    private TextView tvTotal;

    public TrasactionAdapter(Context mContext, ArrayList<Item> itemList, ArrayList<Txn> txnList, int type) {
        this.mContext = mContext;
        this.type = type;

        if(type == 0) {
            this.itemList = itemList;
        } else {
            this.txnList = txnList;
        }
    }

    @Override
    public int getCount() {
        if(type == 0)
            return itemList.size();
        else
            return txnList.size();
    }

    @Override
    public Object getItem(int position) {
        if(type ==0)
            return itemList.get(position);
        else
            return txnList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        private TextView itemName;
        private TextView itemPrice;
        private EditText itemCount;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Context context = parent.getContext();
        final int pos = position;

        if(convertView == null) {
            viewHolder = new ViewHolder();

            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.transaction_item, parent, false);

            tvTotal = (TextView) convertView.findViewById(R.id.tv_total);
            viewHolder.itemName = (TextView) convertView.findViewById(R.id.tx_item_name);
            viewHolder.itemPrice = (TextView) convertView.findViewById(R.id.tx_item_price);
            viewHolder.itemCount = (EditText) convertView.findViewById(R.id.tx_item_count);
            viewHolder.itemCount.setSelection(viewHolder.itemCount.getText().length());

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if(type == 0) {
            Item item = (Item) itemList.get(pos);
            viewHolder.itemName.setText(item.getItemName());
            viewHolder.itemPrice.setText(item.getUnitPrice() + "");

            viewHolder.itemCount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    itemList.get(pos).setItemCount(s.toString());
                }
            });
        } else {
            Txn txn = (Txn) txnList.get(pos);

            viewHolder.itemName.setText(txn.getItem_name());
            viewHolder.itemPrice.setText(txn.getUnit_price() + "");
            viewHolder.itemCount.setText(txn.getQuantity() + "");

            viewHolder.itemCount.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                }

                @Override
                public void afterTextChanged(Editable s) {
                    if(s.toString().equals("")) {
                        txnList.get(pos).setQuantity(0);
                    } else {
                        txnList.get(pos).setQuantity(Integer.valueOf(s.toString()));
                    }
                }
            });
        }

        Editable etext = viewHolder.itemCount.getText();
        Selection.setSelection(etext, viewHolder.itemCount.length());

        return convertView;
    }

    public int itemTotalCalc() {
        int listLen = itemList.size();

        int sum = 0;

        for(int i = 0; i < listLen; i++) {
            sum += (Integer.valueOf(itemList.get(i).getItemCount()) * Integer.valueOf(itemList.get(i).getUnitPrice()));
        }

        return sum;
    }

    public int txnTotalCalc() {
        int listLen = txnList.size();

        int sum = 0;

        for(int i = 0; i < listLen; i++) {
            txnList.get(i).setAmount(txnList.get(i).getUnit_price() * txnList.get(i).getQuantity());
            sum += (txnList.get(i).getAmount());
        }

        return sum;
    }

    public ArrayList<Item> getItemList() {
        return itemList;
    }

    public ArrayList<Txn> getTxnList() {
        return txnList;
    }
}