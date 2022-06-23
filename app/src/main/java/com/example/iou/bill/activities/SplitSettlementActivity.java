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
import com.example.iou.home.HomeActivity;
import com.example.iou.ui.home.HomeFragment;

import org.parceler.Parcels;

import java.util.Map;

public class SplitSettlementActivity extends AppCompatActivity {

    //private final String SPLIT_BILL_INFORMATION_KEY = "SPLIT_BILL_INFORMATION";
    //private final String AMOUNTS_OWED_KEY = "AMOUNTS_OWED";

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

        // Get the names of the people and the amounts they each owe

        tvAmountsOwed.setText(splitBill.getPeople().toString().replace("[", "").replace("]", "") );

        // Bring user to the Home Activity
        btnSaveBillSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toHomeActivity();
            }
        });
    }

    // Brings a user to the Home Activity when a button is clicked

    // TODO: look into activity to fragment
    private void toHomeActivity() {
        Intent i = new Intent(SplitSettlementActivity.this, MainActivity.class);
        startActivity(i);
    }
}