package com.example.iou.home.activities;

import static com.example.iou.IOUKeys.BILL_DETAILS_KEY;
import static com.example.iou.IOUKeys.SPLIT_BILL_INFORMATION_KEY;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.iou.R;
import com.example.iou.bill.models.BillParse;

import org.parceler.Parcels;

import java.util.Date;

public class BillDetailsActivity extends AppCompatActivity {

    private TextView tvLocationDetails;
    private TextView tvBillAmountDetails;
    private TextView tvAmountsOwedDetails;
    private TextView tvTimeStampDetails;

    private BillParse bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);

        // Unwrap the parcel
        bill = Parcels.unwrap(getIntent().getParcelableExtra(BILL_DETAILS_KEY));

        tvLocationDetails = findViewById(R.id.tvLocationDetails);
        tvBillAmountDetails = findViewById(R.id.tvBillAmountDetails);
        tvAmountsOwedDetails = findViewById(R.id.tvAmountsOwedDetails);
        tvTimeStampDetails = findViewById(R.id.tvTimeStampDetails);

        tvLocationDetails.setText(bill.getLocation());
        tvBillAmountDetails.setText(String.valueOf(bill.getFinalBill()));
        tvAmountsOwedDetails.setText(bill.getAmountsOwed());

        // Calculate relative date of the post
        Date createdAt = bill.getCreatedAt();
        String timeAgo = BillParse.calculateTimeAgo(createdAt);
        tvTimeStampDetails.setText(timeAgo);
    }
}