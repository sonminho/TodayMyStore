package com.example.a01020072846.myapplication;

public class Item {

    private String itemType;
    private String itemName;
    private String unitPrice;

    public Item(String itemType, String itemName, String unitPrice) {
        this.itemType = itemType;
        this.itemName = itemName;
        this.unitPrice = unitPrice;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemType='" + itemType + '\'' +
                ", itemName='" + itemName + '\'' +
                ", unitPrice=" + unitPrice +
                '}';
    }

}
