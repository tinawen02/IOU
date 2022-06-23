package com.example.iou.bill.models;

import android.util.SparseBooleanArray;

import java.util.ArrayList;
import java.util.List;

public class BillItem extends SplitBill {

    private double price;
    private List<String> people = new ArrayList<>();
    private SparseBooleanArray checkedList = new SparseBooleanArray();

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

    public Boolean getChecks(int index) {
        return checkedList.get(index);
    }

    public SparseBooleanArray getChecksList() {
        return checkedList;
    }

    public void toggleCheckbox(int index, boolean isChecked) {

        // TODO: should I be using .put or .append()
        checkedList.append(index, isChecked);
        //checkedList.put(index, isChecked);
    }

}
