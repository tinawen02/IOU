package com.example.iou.bill.models;


import com.parse.ParseClassName;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.util.Date;
import java.util.List;

@ParseClassName("Bill")
public class BillParse extends ParseObject {

    public static final String KEY_USER = "user";
    public static final String KEY_LOCATION = "location";
    public static final String KEY_FINAL_BILL = "finalBill";
    public static final String KEY_AMOUNTS_OWED = "amountsOwed";
    public static final String KEY_CREATED_AT = "createdAt";
    public static final String KEY_SELECTED_INDICES = "selectedIndices";

    public ParseUser getUser() {
        return getParseUser(KEY_USER);
    }

    public void setUser(ParseUser user) {
        put(KEY_USER, user);
    }

    public String getLocation() {
        return getString(KEY_LOCATION);
    }

    public void setLocation(String location) {
        put(KEY_LOCATION, location);
    }

    public double getFinalBill() {
        return getDouble(KEY_FINAL_BILL);
    }

    public void setFinalBill(double finalBill) {
        put(KEY_FINAL_BILL, finalBill);
    }

    public String getAmountsOwed() {
        return getString(KEY_AMOUNTS_OWED);
    }

    public void setAmountsOwed(String amountsOwed) {
        put(KEY_AMOUNTS_OWED, amountsOwed);
    }

    public List<Boolean> getSelectedIndices() {
        try {
            return fetchIfNeeded().getList(KEY_SELECTED_INDICES);
        } catch (ParseException e) {
            e.printStackTrace();
            return getList(KEY_SELECTED_INDICES);
        }
    }

    public void setSelectedIndices(List<Boolean> selectedIndices) {put(KEY_SELECTED_INDICES, selectedIndices); }

    public static String calculateTimeAgo(Date createdAt) {

        int SECOND_MILLIS = 1000;
        int MINUTE_MILLIS = 60 * SECOND_MILLIS;
        int HOUR_MILLIS = 60 * MINUTE_MILLIS;
        int DAY_MILLIS = 24 * HOUR_MILLIS;

        try {
            createdAt.getTime();
            long time = createdAt.getTime();
            long now = System.currentTimeMillis();

            final long diff = now - time;
            if (diff < MINUTE_MILLIS) {
                return "just now";
            } else if (diff < 2 * MINUTE_MILLIS) {
                return "a minute ago";
            } else if (diff < 50 * MINUTE_MILLIS) {
                return diff / MINUTE_MILLIS + " m";
            } else if (diff < 90 * MINUTE_MILLIS) {
                return "an hour ago";
            } else if (diff < 24 * HOUR_MILLIS) {
                return diff / HOUR_MILLIS + " h";
            } else if (diff < 48 * HOUR_MILLIS) {
                return "yesterday";
            } else {
                return diff / DAY_MILLIS + " d";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

}
