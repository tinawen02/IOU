package com.example.iou.bill.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.iou.R;
import com.example.iou.bill.adapters.BillItemAdapter;
import com.example.iou.bill.models.BillItem;
import com.example.iou.bill.models.SplitBill;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DividingItemsActivity extends AppCompatActivity {

    private SplitBill splitBill;
    private List<BillItem> items;
    private BillItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dividing_items);

        final Button btnDivideNext = findViewById(R.id.btnDivideNext);
        final RecyclerView rvItems = findViewById(R.id.rvItems);

        // Unwrap the information from the Split Information Activity
        splitBill = Parcels.unwrap(getIntent().getParcelableExtra("Split Bill Information"));

        // Retrieve the prices the user entered
        items = new ArrayList<>();
        for (Double itemPrice : splitBill.getItems()) {
            BillItem billItem = new BillItem();
            billItem.setPrice(itemPrice);
            items.add(billItem);
        }

        adapter = new BillItemAdapter(this, items, splitBill);

        // Attach the adapter to the recyclerview to populate items
        rvItems.setAdapter(adapter);

        // Set layout manager to position the items
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvItems.setLayoutManager(linearLayoutManager);

        // Brings user to Split Settlement Activity
        btnDivideNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toSplitSettlement();
            }
        });
    }

    //TODO: calculate totals for each person
    // Create a dict: keys are names, values are amounts owed
    // Get the values from the Bill Item
    // For loop iterating through items to get each billItem

    private Map<String, Double> calculateSplitSettlement() {
        Map<String, Double> amountsOwed = new HashMap<String, Double>();

        // Iterate through each BillItem in items
        for (BillItem item : items) {

            // Retrieves the checkboxes for each item price
            List<Boolean> itemChecks = item.getChecks();

            for (Boolean isChecked : itemChecks) {
                // not too sure about the logic for getting the checks

                //TODO: look at implementing checkboxes
            }
        }

        return amountsOwed;
    }

    // Brings user to the Split Settlement Activity
    private void toSplitSettlement() {

        Map<String, Double> amountsOwed = calculateSplitSettlement();

        // Send information to the Split Settlement Activity
        Intent i = new Intent(DividingItemsActivity.this, SplitSettlementActivity.class);
        i.putExtra("Split Bill Information", Parcels.wrap(splitBill));
        i.putExtra("Amounts Owed", Parcels.wrap(amountsOwed));
        startActivity(i);
    }
}