package com.example.iou.bill.models;

public class BillItem {

    private double price;
    private String[] people;

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String[] getPeople() {
        return people;
    }

    public void setPeople(String[] people) {
        this.people = people;
    }
}
