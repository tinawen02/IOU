package com.example.iou;

import static com.example.iou.IOUKeys.IS_FIRST_TIME_KEY;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.iou.databinding.ActivityMainBinding;
import com.example.iou.home.activities.SettingsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.onesignal.OneSignal;

import org.parceler.Parcels;

public class MainActivity extends AppCompatActivity {

    private static final String ONESIGNAL_APP_ID = "2866504b-99d9-400e-8ef7-9a2995f5057d";

    private ActivityMainBinding binding;
    private BottomNavigationView mainBottomNav;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Used for debugging
        OneSignal.setLogLevel(OneSignal.LOG_LEVEL.VERBOSE, OneSignal.LOG_LEVEL.NONE);

        // Initialize the OneSignal services with the application context
        OneSignal.initWithContext(this);
        OneSignal.setAppId(ONESIGNAL_APP_ID);

        // Unwrap the boolean to see if it is the first time user is logging in
        Boolean isFirstTime = Parcels.unwrap(getIntent().getParcelableExtra(IS_FIRST_TIME_KEY));

        // Runs the tutorial
        /*
        if (isFirstTime) {
            runTutorial();
        }

         */

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Create a toolbar to display settings
        final Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        // Find the view pager that will allow the user to swipe between fragments
        viewPager = binding.pager;
        swipeBetweenViews(viewPager);

        // Create an adapter that knows which fragment should be shown on each page
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Swipes between the icons in the bottom navigation view
        setUpBottomViewNavigation();
    }

    private void runTutorial() {
        // Make it so that a user cannot click between fragments (tutorial is mandatory)
            // Disable the bottom navigation bar (visually looks the same)
        //

    }

    private void swipeBetweenViews(ViewPager viewPager) {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

            // Allows a user to swipe between the different views
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        mainBottomNav.getMenu().findItem(R.id.navigation_home).setChecked(true);
                        break;
                    case 1:
                        mainBottomNav.getMenu().findItem(R.id.navigation_bill).setChecked(true);
                        break;
                    case 2:
                        mainBottomNav.getMenu().findItem(R.id.navigation_map).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) { }
        });
    }

    // Swipes between the icons in the bottom navigation view
    @SuppressLint("NonConstantResourceId")
    private void setUpBottomViewNavigation() {

        mainBottomNav = binding.navView;
        mainBottomNav.setOnNavigationItemSelectedListener(item -> {
            int selection;
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    selection = 0;
                    break;
                case R.id.navigation_bill:
                    selection = 1;
                    break;
                case R.id.navigation_map:
                default:
                    selection = 2;
                    break;
            }
            viewPager.setCurrentItem(selection);
            return true;
        });

        // Defaults to the home fragment
        mainBottomNav.setSelectedItemId(R.id.navigation_home);
    }

    // Displays the name in the action bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        return true;
    }

    // Brings the user to the settings page when they on the gear icon
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.toolbar_settings) {
            Intent i = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}