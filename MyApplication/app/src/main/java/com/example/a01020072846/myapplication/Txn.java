package com.example.a01020072846.myapplication;

public class Txn {
    private String mname_id;
    private String item_type;
    private int item_name_id;
    private String item_name;
    private int unit_price;
    private int quantity;
    private int amount;
    private String txn_date;

    public String getMname_id() {
        return mname_id;
    }
    public void setMname_id(String mname_id) {
        this.mname_id = mname_id;
    }
    public String getItem_type() {
        return item_type;
    }
    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }
    public int getItem_name_id() {
        return item_name_id;
    }
    public void setItem_name_id(int item_name_id) {
        this.item_name_id = item_name_id;
    }
    public String getItem_name() {
        return item_name;
    }
    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }
    public int getUnit_price() {
        return unit_price;
    }
    public void setUnit_price(int unit_price) {
        this.unit_price = unit_price;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getTxn_date() {
        return txn_date;
    }
    public void setTxn_date(String txn_date) {
        this.txn_date = txn_date;
    }
    public int getAmount() {
        return amount;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    @Override
    public String toString() {
        return "TxnsDTO [mname_id=" + mname_id + ", item_type=" + item_type + ", item_name_id=" + item_name_id
                + ", item_name=" + item_name + ", unit_price=" + unit_price + ", quantity=" + quantity + "]";
    }
}