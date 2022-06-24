package com.example.iou.bill.activities;

import static com.example.iou.IOUKeys.AMOUNTS_OWED_KEY;
import static com.example.iou.IOUKeys.SPLIT_BILL_INFORMATION_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
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
import java.util.Collections;
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
        splitBill = Parcels.unwrap(getIntent().getParcelableExtra(SPLIT_BILL_INFORMATION_KEY));

        // Retrieve the prices the user entered
        items = new ArrayList<>();
        for (Double itemPrice : splitBill.getItems()) {
            BillItem billItem = new BillItem();
            billItem.setPrice(itemPrice);

            // Populate each item with the people who dined
            billItem.setPeople(splitBill.getPeople());
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

    // Does the math behind splitting a bill
    private Map<String, Double> calculateSplitSettlement() {

        // Stores the amounts each individual owes before tax, tip and discounts are calculated
        Map<String, Double> amountsOwedPrior = new HashMap<String, Double>();
        // Stores the amounts each individual actually owes
        Map<String, Double> amountsOwed = new HashMap<String, Double>();

        // Iterate through each BillItem in items
        for (BillItem item : items) {

            // Retrieve the list of checks
            SparseBooleanArray checkedList = item.getChecksList();
            // Retrieves the number of checks in the item (to determine price)
            int numChecks = item.getChecksList().size();

            // If an item only has one check, iterate through the checkmarks of each item
            for (int i = 0; i < checkedList.size(); i++) {

                // Check to see if the index of checkedList is true (said person should pay)
                if (checkedList.valueAt(i)) {
                    // Sets the key value of this iteration
                    Integer keyVal = checkedList.keyAt(i);
                    String nameKey = item.getPeople().get(keyVal);

                    // Check to see if a key already exists
                    if (!amountsOwedPrior.containsKey(nameKey)) {
                        // If key does not exist, set the key and value
                        amountsOwedPrior.put(nameKey, item.getPrice() / numChecks);
                    } else {
                        // Saves the old value to oldVal
                        Double oldVal = amountsOwedPrior.get(nameKey);
                        // If key does exist, overwrite and update to the new value
                        amountsOwedPrior.replace(nameKey, oldVal + item.getPrice() / numChecks);
                    }
                }
            }
        }

        // Retrieve the total price of the items;
        Double itemsTotal = 0.0;
        for (Double itemPrice : amountsOwedPrior.values()) {
            itemsTotal += itemPrice;
        }

        // Populate amountsOwed with the true amounts each person owes
        for (String name : amountsOwedPrior.keySet()) {

            // Retrieve the amount the individual owes before tax/tip/discount
            Double individualTotal = amountsOwedPrior.get(name);
            // Retrieve the amount the individual in tax/tip/discount
            Double additionalCost = (double ) Math.round(((splitBill.getBillTotal() - itemsTotal) * (individualTotal/itemsTotal)) * 100) / 100;
            // Retrieve the amount the individual owes after tax/tip/discount
            double finalTotal = individualTotal + additionalCost;
            // Puts the amounts owed into amountsOwed
            amountsOwed.put(name, finalTotal);
        }

        return amountsOwed;
    }

    // Brings user to the Split Settlement Activity
    private void toSplitSettlement() {

        Map<String, Double> amountsOwed = calculateSplitSettlement();

        // Send information to the Split Settlement Activity
        Intent i = new Intent(DividingItemsActivity.this, SplitSettlementActivity.class);
        i.putExtra(SPLIT_BILL_INFORMATION_KEY, Parcels.wrap(splitBill));
        i.putExtra(AMOUNTS_OWED_KEY, Parcels.wrap(amountsOwed));
        startActivity(i);
    }
}