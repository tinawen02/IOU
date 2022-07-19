package com.example.iou.map.activities;

import static com.example.iou.IOUKeys.WHERE_TO_EAT_KEY;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.example.iou.R;
import com.example.iou.databinding.ActivityGoogleMapsBinding;
import com.example.iou.map.models.RestaurantItem;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

public class GoogleMapsActivity extends FragmentActivity implements OnMapReadyCallback {

    public String URL;
    private GoogleMap mMap;
    private final static String KEY_LOCATION = "location";
    private List<RestaurantItem> nearbyRestaurants;
    Location mCurrentLocation;
    double currLatitude;
    double currLongitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        com.example.iou.databinding.ActivityGoogleMapsBinding binding = ActivityGoogleMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Used to store the restaurants nearby the user
        nearbyRestaurants = new ArrayList<>();

        // Unwrap the parcel from the map fragment
        String whereTo = Parcels.unwrap(getIntent().getParcelableExtra(WHERE_TO_EAT_KEY));

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if (savedInstanceState != null && savedInstanceState.keySet().contains(KEY_LOCATION)) {
            // Since KEY_LOCATION was found in the Bundle, we can be sure that mCurrentLocation is not null
            mCurrentLocation = savedInstanceState.getParcelable(KEY_LOCATION);
        }

        // Finds the current location of the user
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ContextCompat.checkSelfPermission(GoogleMapsActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(GoogleMapsActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(GoogleMapsActivity.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

        // Registers for location updates using the GPS
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 25, 5, new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

                // Retrieve the URL for the API call based on the user's location
                URL = generateURL(location, whereTo);

                // Makes a GET request from the Google Maps API
                AsyncHttpClient client = new AsyncHttpClient();
                client.get(URL, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, okhttp3.Headers headers, JSON json) {
                        JSONObject jsonObject = json.jsonObject;
                        try {
                            JSONArray results = jsonObject.getJSONArray("results");
                            // Adds the nearby restaurants to nearbyRestaurants
                            nearbyRestaurants.addAll(RestaurantItem.fromJsonArray(results));
                            // Sets the pin of open restaurants to be magenta
                            BitmapDescriptor openPin = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA);
                            // Sets the pin of closed restaurants to be orange
                            BitmapDescriptor closedPin = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
                            // Sets the pin of restaurants with no information to be yellow
                            BitmapDescriptor noInfoPin = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);

                            // Iterates through the restaurants near the user
                            for (int i = 0; i < nearbyRestaurants.size(); i++) {
                                // Gets the position of a restaurant
                                RestaurantItem restaurant = nearbyRestaurants.get(i);
                                // Sets the pin on the map based on the status of the restaurant's hour of operation
                                setRestaurantPin(restaurant, openPin, closedPin, noInfoPin, whereTo);
                            }
                        } catch (JSONException e) {
                            Toast.makeText(GoogleMapsActivity.this, "Error while generating nearby pins!", Toast.LENGTH_SHORT).show();
                            Log.e("GoogleMapsActivity", e.toString());
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, okhttp3.Headers headers, String response, Throwable throwable) {
                        Toast.makeText(GoogleMapsActivity.this, "Error while displaying map!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    // Generates the URL for the API request based on the user's location
    private String generateURL(Location location,String whereTo) {

        // Retrieve the longitude and latitude of the user
        double longitude = location.getLongitude();
        double latitude = location.getLatitude();

        currLatitude = latitude;
        currLongitude = longitude;

        // Add a pin at user's current location
        final LatLng userCoordinates = new LatLng(latitude, longitude);

        // TODO: fix legend
        Marker myLocation = mMap.addMarker(new MarkerOptions().position(userCoordinates)
                .title("Restaurant Status")
                .snippet("Magenta = Open\n Orange = Closed\n Yellow = No Information"));

        myLocation.showInfoWindow();

        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(userCoordinates, 13);
        mMap.animateCamera(cameraUpdate);

        if (whereTo.equals("")) {
            URL = String.format("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=%f,%f&radius=5000&types=restaurant&key=%s", latitude, longitude, getString(R.string.MAPS_API_KEY));
            System.out.println("URL " + URL);
        } else {
            // Used to get the Google Maps API request given the API key
            URL = String.format("https://maps.googleapis.com/maps/api/place/textsearch/json?location=%f,%f&radius=5000&query=%s&key=%s", latitude, longitude, whereTo, getString(R.string.MAPS_API_KEY));
        }
        return URL;
    }

    // Sets the pin on the map based on the status of the restaurant's hour of operation
    private void setRestaurantPin(RestaurantItem restaurant, BitmapDescriptor openPin, BitmapDescriptor closedPin, BitmapDescriptor noInfoPin, String whereTo) {
        // Sets the longitude and latitude of the restaurant
        double restaurantLatitude = restaurant.getLatitude();
        double restaurantLongitude = restaurant.getLongitude();
        LatLng restaurantLocation = new LatLng(restaurantLatitude, restaurantLongitude);

        MarkerOptions markerOptions;

        // If the user has opted to search for a specific food, show the foods nearby
        if (!(whereTo.equals(""))) {
            // Creates a marker (pin) on the map
             markerOptions = new MarkerOptions()
                    .title(restaurant.getName())
                     .snippet(String.valueOf(restaurant.getAddress()))
                    .position(restaurantLocation);
        // If the user has opted to go directly to the map, show all restaurants nearby
        } else {
            // Creates a marker (pin) on the map
            markerOptions = new MarkerOptions()
                    .title(restaurant.getName())
                    .snippet(String.valueOf(restaurant.getVicinity()))
                    .position(restaurantLocation);
        }

        if (restaurant.isOpen() != null && restaurant.isOpen()) {
            // Sets a magenta pin if the restaurant is open
            markerOptions.icon(openPin);
        }  else if (restaurant.isOpen() != null && !restaurant.isOpen()) {
            // Sets an orange pin if the restaurant is closed
            markerOptions.icon(closedPin);
        } else {
            // Sets a yellow pin if the restaurant has no information
            markerOptions.icon(noInfoPin);
        }

        // Adds the pin to the map
        mMap.addMarker(markerOptions);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        UiSettings uiSettings = mMap.getUiSettings();
        // Allows a user to use gestures
        uiSettings.setAllGesturesEnabled(true);
        // Allows a user to get directions to a pin from their current location
        uiSettings.setMapToolbarEnabled(true);
        // Allows a user to zoom in/out
        uiSettings.setZoomControlsEnabled(true);
        // Allows a user to scroll
        uiSettings.setScrollGesturesEnabled(true);

    }
}