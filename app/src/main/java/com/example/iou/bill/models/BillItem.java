package com.example.iou.bill.models;

import java.util.List;

public class BillItem {

    private double price;
    private List<String> people;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<String> getPeople() {
        return people;
    }

    public void setPeople(List<String> people) {
        this.people = people;
    }
}
