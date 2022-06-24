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

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class EvenSettlementActivity extends AppCompatActivity {

    private SplitBill splitBill;
    private Map<String, Double> amountsOwed;

    private TextView tvLocationEven;
    private TextView tvBillAmountEven;
    private TextView tvAmountsOwedEven;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_even_settlement);

        // Unwrap the information from the Dividing Items Activity
        splitBill = Parcels.unwrap(getIntent().getParcelableExtra(SPLIT_BILL_INFORMATION_KEY));

        final Button btnSaveBillEven = findViewById(R.id.btnSaveBillEven);
        tvLocationEven = findViewById(R.id.tvLocationEven);
        tvBillAmountEven = findViewById(R.id.tvBillAmountEven);
        tvAmountsOwedEven = findViewById(R.id.tvAmountsOwedEven);

        // Set the views with specific information regarding the transaction
        tvLocationEven.setText(splitBill.getRestaurantName());
        tvBillAmountEven.setText("Total Bill: " + String.valueOf(splitBill.getBillTotal()));

        // Calculate the amounts each individual owes
        int numPeople = splitBill.getPeople().size();
        Double amountOwed = (double) Math.round(splitBill.getBillTotal() / numPeople * 100) / 100;

        // Build the string of names of people and amounts each person owes
        StringBuilder str = new StringBuilder();

        for (String name : splitBill.getPeople()) {
            str.append(name + " owes " + amountOwed + "\n");
        }

        // Set the names of people and amounts each person owes
        tvAmountsOwedEven.setText(str);

        // Bring user to the Home Fragment
        btnSaveBillEven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toHomeFragment();
            }
        });
    }

    // Brings a user to the Home Fragment when a button is clicked
    private void toHomeFragment() {
        Intent i = new Intent(EvenSettlementActivity.this, MainActivity.class);
        startActivity(i);
    }
}