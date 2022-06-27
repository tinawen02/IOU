package com.example.iou.bill.models;


import com.parse.ParseUser;

import org.parceler.Parcel;

import java.sql.Array;
import java.util.List;

@Parcel
public class SplitBill{

    private String restaurantName;
    private List<String> people;
    private List<Double> items;
    private double billTotal;
    private String currencyCode;

    // Empty constructor for parcels
    public SplitBill() {

    }

    // Constructor without the currency code
    public SplitBill(String restaurantName, List<String> people, List<Double> items, double billTotal) {
        this.restaurantName = restaurantName;
        this.people = people;
        this.items = items;
        this.billTotal = billTotal;
    }

    // Constructor for even split
    public SplitBill(String restaurantName, List<String> people, double billTotal) {
        this.restaurantName = restaurantName;
        this.people = people;
        this.billTotal = billTotal;
    }

    // Constructor with the currency code
    public SplitBill(String restaurantName, List<String> people, List<Double> items, double billTotal, String currencyCode) {
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

    public List<String> getPeople() {
        return people;
    }

    public void setPeople(List<String> people) {
        this.people = people;
    }

    public List<Double> getItems() {
        return items;
    }

    public void setItems(List<Double> items) {
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
