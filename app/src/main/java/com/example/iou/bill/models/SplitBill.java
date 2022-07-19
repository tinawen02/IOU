package com.example.iou.bill.models;


import org.parceler.Parcel;

import java.util.List;

@Parcel
public class SplitBill{

    private String restaurantName;
    private List<String> people;
    private List<Double> items;
    private double billTotal;

    // Empty constructor for parcels
    public SplitBill() {

    }

    // Constructor for split items
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

}
