package com.example.iou.bill.activities;

import static com.example.iou.IOUKeys.AMOUNTS_OWED_KEY;
import static com.example.iou.IOUKeys.SPLIT_BILL_INFORMATION_KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.iou.MainActivity;
import com.example.iou.R;
import com.example.iou.bill.models.SplitBill;

import org.parceler.Parcels;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SplitSettlementActivity extends AppCompatActivity {

    private SplitBill splitBill;
    private Map<String, Double> amountsOwed;

    private TextView tvLocation;
    private TextView tvBillAmount;
    private TextView tvAmountsOwed;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_settlement);

        // Unwrap the information from the Dividing Items Activity
        splitBill = Parcels.unwrap(getIntent().getParcelableExtra(SPLIT_BILL_INFORMATION_KEY));
        amountsOwed = Parcels.unwrap(getIntent().getParcelableExtra(AMOUNTS_OWED_KEY));


        final Button btnSaveBillSplit = findViewById(R.id.btnSaveBillSplit);
        tvLocation = findViewById(R.id.tvLocation);
        tvBillAmount = findViewById(R.id.tvBillAmount);
        tvAmountsOwed = findViewById(R.id.tvAmountsOwed);

        // Set the views with specific information regarding the transaction
        tvLocation.setText(splitBill.getRestaurantName());
        tvBillAmount.setText(String.valueOf(splitBill.getBillTotal()));

        // Get the names of the people
        Set keys = amountsOwed.keySet();

        // Build the string of names of people and amounts each person owes
        StringBuilder str = new StringBuilder();
        for (Iterator i = keys.iterator(); i.hasNext(); ) {
            String name = (String) i.next();
            Double value = amountsOwed.get(name);
            str.append(name + " owes " + value + "\n");
        }

        // Set the names of people and amounts each person owes
        tvAmountsOwed.setText(str);

        // Bring user to the Home Fragment
        btnSaveBillSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toHomeFragment();
            }
        });
    }

    // Brings a user to the Home Fragment when a button is clicked
    // TODO: look into activity to fragment
    private void toHomeFragment() {
        Intent i = new Intent(SplitSettlementActivity.this, MainActivity.class);
        startActivity(i);
    }
}