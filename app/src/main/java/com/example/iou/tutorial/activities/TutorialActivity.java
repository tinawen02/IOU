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
    private TabLayout tabIndicator;
    private Button btnNext;
    private Button btnGetStarted;
    private TextView tvSkip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ensures the activity is shown on the entire screen
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_tutorial);

        tvSkip = findViewById(R.id.tvSkip);
        btnNext = findViewById(R.id.btnNext);
        btnGetStarted = findViewById(R.id.btnGetStarted);
        tabIndicator = findViewById(R.id.tlTabIndicator);
        Animation btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);

        // Populate the tutorial screens
        List<TutorialItem> items = new ArrayList<>();
        items.add(new TutorialItem("Create a Bill","Select 'Split by Item' to split your bill based on item ordered. Simply check the names of the people who ordered each item and input the bill before taxes/discounts/tips and after taxes/discounts/tips!",R.drawable.bill_tutorial));
        items.add(new TutorialItem("Explore the Map","Find restaurants near you to explore. Are you craving sushi? Just type 'Sushi' into the search bar, and we'll get you all the delicious options!", R.drawable.map_tutorial));
        items.add(new TutorialItem("View your History","Explore your Home feed to view your previous transactions. Check names off as your friends pay you back to keep track of your bills!",R.drawable.home_tutorial));

        // Set up the viewpager
        screenPager = findViewById(R.id.vpScreen);
        TutorialAdapter tutorialAdapter = new TutorialAdapter(this, items);
        screenPager.setAdapter(tutorialAdapter);

        // Set up the tab indicator at the bottom of the screen
        tabIndicator.setupWithViewPager(screenPager);

        // Allows a user to go to the next screen when clicking the next button
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toNextScreen(items, btnAnim);
            }
        });

        // Ensures the tab at the bottom of the screen matches the tutorial screen the user is on
        tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == items.size() - 1) {
                    loadLastScreen(btnAnim);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });


        btnGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Brings the user to the MainActivity after clicking the "Get Started" button
                toMainActivity();
            }
        });

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenPager.setCurrentItem(items.size());
            }
        });

    }

    private void toNextScreen(List<TutorialItem> mList, Animation btnAnim) {
        int position = screenPager.getCurrentItem();
        if (position < mList.size()) {
            position++;
            screenPager.setCurrentItem(position);
        }
        if (position == mList.size() - 1) {
            loadLastScreen(btnAnim);
        }
    }

    private void toMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

    // Shows the last screen before the MainActivity is loaded
    private void loadLastScreen(Animation btnAnim) {
        // Removes the views of the buttons and tab indicators
        btnNext.setVisibility(View.INVISIBLE);
        btnGetStarted.setVisibility(View.VISIBLE);
        tvSkip.setVisibility(View.INVISIBLE);
        tabIndicator.setVisibility(View.INVISIBLE);
        btnGetStarted.setAnimation(btnAnim);
    }
}