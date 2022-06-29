package com.example.iou.map.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RestaurantItem {
    private String name;
    private String address;
    private double rating;
    private double latitude;
    private double longitude;
    private Boolean isOpen;

    public RestaurantItem(JSONObject jsonObject) throws JSONException {
        this.name = jsonObject.getString("name");
        this.address = jsonObject.getString("vicinity");
        this.latitude = jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
        this.longitude = jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng");

        try {
            this.isOpen = jsonObject.getJSONObject("opening_hours").getBoolean("open_now");
        }
        catch (JSONException e) {
            isOpen = null;
        }
        try {
            this.rating = jsonObject.getDouble("rating");
        }
        catch (JSONException e) {
            this.rating = -1;
            e.printStackTrace();
        }
    }

    public static List<RestaurantItem> fromJsonArray(JSONArray restaurantJsonArray) throws JSONException {
        List<RestaurantItem> nearbyRestaurants = new ArrayList<>();
        for (int i = 0; i < restaurantJsonArray.length(); i++) {
            nearbyRestaurants.add(new RestaurantItem(restaurantJsonArray.getJSONObject(i)));
        }
        return nearbyRestaurants;
    }

    public String getName() {
        return name;
    }
    public String getAddress() {
        return address;
    }
    public double getRating() {
        return rating;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public Boolean isOpen() {
        return isOpen;
    }
}
