package com.example.iou.bill.activities;

import static com.example.iou.IOUKeys.SPLIT_BILL_INFORMATION_KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.iou.R;
import com.example.iou.bill.models.SplitBill;

import org.parceler.Parcels;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SplitInformationActivity extends AppCompatActivity {

    //private final String SPLIT_BILL_INFORMATION_KEY = "SPLIT_BILL_INFORMATION";

    private EditText etLocation;
    private EditText etPeople;
    private EditText etItemPrices;
    private EditText etFinalBill;
    private Button btnSplitNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_information);

        etLocation = findViewById(R.id.etLocation);
        etPeople = findViewById(R.id.etPeople);
        etItemPrices = findViewById(R.id.etItemPrices);
        etFinalBill = findViewById(R.id.etFinalBill);
        btnSplitNext = findViewById(R.id.btnSplitNext);

        // Bring user to the Dividing Items Activity and passes on relevant information
        btnSplitNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toDividingItems();
            }
        });

    }

    // Bring user to the Dividing Items Activity and passes on relevant information
    private void toDividingItems() {
        // Set the views
        String location = etLocation.getText().toString();
        String[] peopleArray = etPeople.getText().toString().replace(" ", "").split(",");
        String[] itemsArray = etItemPrices.getText().toString().split(",");
        double billTotal = Double.parseDouble(etFinalBill.getText().toString());

        // Converting items to to a List of doubles
        List<Double> items = new ArrayList<>();

        for (int i = 0; i < itemsArray.length; i++) {
            items.add(Double.valueOf(itemsArray[i]));
        }


        // Converting people to a List of Strings
        List<String> people = new ArrayList<>();
        Collections.addAll(people, peopleArray);

        // Create a new splitBill;
        SplitBill splitBill = new SplitBill(location, people, items, billTotal);

        // Send information to the Dividing Item Activity
        Intent i = new Intent(SplitInformationActivity.this, DividingItemsActivity.class);
        i.putExtra(SPLIT_BILL_INFORMATION_KEY, Parcels.wrap(splitBill));
        startActivity(i);
    }
}