package com.example.iou.map.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RestaurantItem {
    private final String name;
    private final String address;
    private final String vicinity;
    private final double rating;
    private final double latitude;
    private final double longitude;
    private final Boolean isOpen;

    public RestaurantItem(JSONObject jsonObject) throws JSONException {
        this.name = jsonObject.getString("name");


        if (jsonObject.has("formatted_address")) {
            this.address = jsonObject.getString("formatted_address");
        } else {
            this.address = null;
        }

        if (jsonObject.has("vicinity")) {
            this.vicinity = jsonObject.getString("vicinity");
        } else {
            this.vicinity = null;
        }

        this.latitude = jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lat");
        this.longitude = jsonObject.getJSONObject("geometry").getJSONObject("location").getDouble("lng");

        // Checks to see if a restaurant is open or not
        if (jsonObject.has("opening_hours")) {
            JSONObject openingHours = jsonObject.getJSONObject("opening_hours");
            if (openingHours.has("open_now")) {
                this.isOpen = jsonObject.getJSONObject("opening_hours").getBoolean("open_now");
            } else {
                this.isOpen = false;
            }

        } else {
            this.isOpen = null;
        }

        // Gets the rating of a restaurant if it has one
        this.rating = jsonObject.optDouble("rating", -1);

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
    public String getVicinity() {
        return vicinity;
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
