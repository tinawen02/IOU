package com.example.iou.bill.activities;

import static com.example.iou.IOUKeys.AMOUNTS_OWED_KEY;
import static com.example.iou.IOUKeys.SPLIT_BILL_INFORMATION_KEY;
import static com.example.iou.IOUKeys.USER_BILL_KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iou.MainActivity;
import com.example.iou.R;
import com.example.iou.bill.models.BillParse;
import com.example.iou.bill.models.SplitBill;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class SplitSettlementActivity extends AppCompatActivity {

    private SplitBill splitBill;
    private Map<String, Double> amountsOwed;

    private TextView tvLocation;
    private TextView tvBillAmount;
    private TextView tvAmountsOwed;

    private BillParse bill = new BillParse();
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

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
            Double value = Double.valueOf(decimalFormat.format(amountsOwed.get(name)));
            str.append(name + " owes " + value + "\n");
        }

        // Set the names of people and amounts each person owes
        tvAmountsOwed.setText(str);

        // Update BillParse information to store in Parse
        bill.setLocation(splitBill.getRestaurantName());
        bill.setUser(ParseUser.getCurrentUser());
        bill.setFinalBill(splitBill.getBillTotal());
        bill.setAmountsOwed(str.toString());

        bill.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Toast.makeText(SplitSettlementActivity.this, "Error while saving!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Bring user to the Home Fragment
        btnSaveBillSplit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toHomeFragment();
            }
        });
    }

    // Brings a user to the Home Fragment when a button is clicked
    private void toHomeFragment() {
        Intent i = new Intent(SplitSettlementActivity.this, MainActivity.class);
        i.putExtra(USER_BILL_KEY, Parcels.wrap(bill));
        i.putExtra(SPLIT_BILL_INFORMATION_KEY, Parcels.wrap(splitBill));
        startActivity(i);
    }
}