package com.example.iou;

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

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private BottomNavigationView mainBottomNav;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Create a toolbar to display settings
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Find the view pager that will allow the user to swipe between fragments
        viewPager = (ViewPager)findViewById(R.id.pager);

        // Allows a user to swipe between pages
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                return;
            }

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
            public void onPageScrollStateChanged(int state) {
                return;
            }
        });


        // Create an adapter that knows which fragment should be shown on each page
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Set the adapter onto the view pager
        viewPager.setAdapter(adapter);

        // Swipes between the icons in the bottom navigation view
        setUpBottomViewNavigation();
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