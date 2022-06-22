package com.example.iou.bill.models;


import org.parceler.Parcel;

import java.sql.Array;
@Parcel
public class SplitBill {

    private String restaurantName;
    private String[] people;
    private double[] items;
    private double billTotal;
    private String currencyCode;

    // Empty constructor for parcels
    public SplitBill() {

    }

    // Constructor without the currency code
    public SplitBill(String restaurantName, String[] people, double[] items, double billTotal) {
        this.restaurantName = restaurantName;
        this.people = people;
        this.items = items;
        this.billTotal = billTotal;
    }

    // Constructor with the currency code
    public SplitBill(String restaurantName, String[] people, double[] items, double billTotal, String currencyCode) {
        this.restaurantName = restaurantName;
        this.people = people;
        this.items = items;
        this.billTotal = billTotal;
        this.currencyCode = currencyCode;
    }


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
