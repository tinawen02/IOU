package com.example.iou.models;


import java.sql.Array;

public class SplitBill {

    private String restaurantName;
    private String[] people;
    private double[] items;
    private double billTotal;
    private String currencyCode;

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String[] getPeople() {
        return people;
    }

    public void setPeople(String[] people) {
        this.people = people;
    }

    public double[] getItems() {
        return items;
    }

    public void setItems(double[] items) {
        this.items = items;
    }

    public double getBillTotal() {
        return billTotal;
    }

    public void setBillTotal(double billTotal) {
        this.billTotal = billTotal;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

}
