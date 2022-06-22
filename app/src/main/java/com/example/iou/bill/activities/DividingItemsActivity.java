package com.example.iou.bill.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.iou.R;
import com.example.iou.bill.adapters.BillItemAdapter;
import com.example.iou.bill.models.SplitBill;

import org.parceler.Parcels;

public class DividingItemsActivity extends AppCompatActivity {

    private SplitBill splitBill;
    private double[] items;
    private BillItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dividing_items);

        final Button btnDivideNext = findViewById(R.id.btnDivideNext);
        final RecyclerView rvItems = findViewById(R.id.rvItems);

        // Unwrap the information from the Split Information Activity
        splitBill = (SplitBill) Parcels.unwrap(getIntent().getParcelableExtra("Split Bill Information"));

        // TODO: Initialize the array of items and adapter
        // items = new double[];


        // Brings user to Split Settlement Activity
        btnDivideNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSplitSettlement();
            }
        });
    }

    // Brings user to the Split Settlement Activity
    private void toSplitSettlement() {
        // Send information to the Split Settlement Activity
        Intent i = new Intent(DividingItemsActivity.this, SplitSettlementActivity.class);
        startActivity(i);
    }
}