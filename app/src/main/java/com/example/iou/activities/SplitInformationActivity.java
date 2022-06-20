package com.example.iou.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.iou.R;

public class SplitInformationActivity extends AppCompatActivity {

    private EditText etLocation;
    private EditText etPeople;
    private EditText etItemPrices;
    private EditText etFinalBill;
    private Button btnSplitNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_information);

        // Finding the views by Id
        etLocation = findViewById(R.id.etLocation);
        etPeople = findViewById(R.id.etPeople);
        etItemPrices = findViewById(R.id.etItemPrices);
        etFinalBill = findViewById(R.id.etFinalBill);
        btnSplitNext = findViewById(R.id.btnSplitNext);

        // TODO: Set the views

        // Bring user to the Dividing Items Activity
        toDividingItems(btnSplitNext);
    }

    // Brings user to the Dividing Item Activity
    private void toDividingItems(Button btnSplitNext) {
        btnSplitNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send information to the Dividing Item Activity
                Intent i = new Intent(SplitInformationActivity.this, DividingItemsActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}