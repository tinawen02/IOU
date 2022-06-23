package com.example.iou.bill.models;

import java.util.List;

public class BillItem {

    private double price;
    private List<String> people;
    private List<Boolean> checkedList;

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

    public List<Boolean> getChecks() {
        return checkedList;
    }

    public void setChecks(List<Boolean> checkedList) {
        this.checkedList = checkedList;
    }

    public void toggleCheckbox(int index, boolean isChecked) {
        checkedList.set(index, isChecked);
    }
}
