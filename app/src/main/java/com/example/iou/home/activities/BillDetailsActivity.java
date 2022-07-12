package com.example.iou.home.activities;

import static com.example.iou.IOUKeys.BILL_DETAILS_KEY;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iou.R;
import com.example.iou.bill.models.BillParse;

import org.parceler.Parcels;

import java.util.Date;

public class BillDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);

        // Unwrap the parcel
        BillParse bill = Parcels.unwrap(getIntent().getParcelableExtra(BILL_DETAILS_KEY));

        final TextView tvLocationDetails = findViewById(R.id.tvLocationDetails);
        final TextView tvBillAmountDetails = findViewById(R.id.tvBillAmountDetails);
        final TextView tvAmountsOwedDetails = findViewById(R.id.tvAmountsOwedDetails);
        final TextView tvTimeStampDetails = findViewById(R.id.tvTimeStampDetails);

        tvLocationDetails.setText(bill.getLocation());
        tvBillAmountDetails.setText(String.valueOf(bill.getFinalBill()));
        tvAmountsOwedDetails.setText(bill.getAmountsOwed());

        // Calculate relative date of the post
        Date createdAt = bill.getCreatedAt();
        String timeAgo = BillParse.calculateTimeAgo(createdAt);
        tvTimeStampDetails.setText(timeAgo);
    }
}