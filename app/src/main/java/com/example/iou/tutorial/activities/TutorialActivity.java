package com.example.iou.tutorial.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.iou.MainActivity;
import com.example.iou.R;
import com.example.iou.tutorial.adapters.TutorialAdapter;
import com.example.iou.tutorial.models.TutorialItem;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class TutorialActivity extends AppCompatActivity {

    private ViewPager screenPager;
    TutorialAdapter introViewPagerAdapter ;
    TabLayout tabIndicator;
    Button btnNext;
    int position = 0 ;
    Button btnGetStarted;
    Animation btnAnim ;
    TextView tvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // Ensures the activity is shown on the entire screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_tutorial);

        btnNext = findViewById(R.id.btn_next);
        btnGetStarted = findViewById(R.id.btn_get_started);
        tabIndicator = findViewById(R.id.tab_indicator);
        btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
        tvSkip = findViewById(R.id.tv_skip);

        // Populate the tutorial screens
        final List<TutorialItem> mList = new ArrayList<>();
        mList.add(new TutorialItem("Create a Bill","Select 'Split by Item' to split your bill based on item ordered. Simply check the names of the people who ordered each item and input the bill before taxes/discounts/tips and after taxes/discounts/tips!",R.drawable.map_tutorial));
        mList.add(new TutorialItem("Explore the Map","Find restaurants near you to explore. Are you craving sushi? Just type 'Sushi' into the search bar, and we'll get you all the delicious options!",R.drawable.bill_tutorial));
        mList.add(new TutorialItem("View your History","Explore your Home feed to view your previous transactions. Check names off as your friends pay you back to keep track of your bills!",R.drawable.home_tutorial));

        // Set up the viewpager
        screenPager =findViewById(R.id.screen_viewpager);
        introViewPagerAdapter = new TutorialAdapter(this,mList);
        screenPager.setAdapter(introViewPagerAdapter);

        // Set up the tab indicator at the bottom of the screen
        tabIndicator.setupWithViewPager(screenPager);

        // Allows a user to go to the next screen
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position = screenPager.getCurrentItem();
                if (position < mList.size()) {
                    position++;
                    screenPager.setCurrentItem(position);
                }
                if (position == mList.size()-1) { // when we rech to the last screen
                    loaddLastScreen();
                }
            }
        });

        // Ensures the tab at the bottom of the screen matches the tutorial screen the user is on
        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (tab.getPosition() == mList.size()-1) {
                    loaddLastScreen();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });



        // Get Started button click listener

        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open main activity
                Intent mainActivity = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(mainActivity);
                // also we need to save a boolean value to storage so next time when the user run the app
                // we could know that he is already checked the intro screen activity
                // i'm going to use shared preferences to that process
                //savePrefsData();
                finish();
            }
        });

        // skip button click listener

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenPager.setCurrentItem(mList.size());
            }
        });



    }

    // show the GETSTARTED Button and hide the indicator and the next button
    private void loaddLastScreen() {

        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tvSkip.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        // TODO : ADD an animation the getstarted button
        // setup animation
        btnGetStarted.setAnimation(btnAnim);
    }
}