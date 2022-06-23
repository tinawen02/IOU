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

    //private final String SPLIT_BILL_INFORMATION_KEY = "SPLIT_BILL_INFORMATION";
    //private final String AMOUNTS_OWED_KEY = "AMOUNTS_OWED";

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

    // For now, each person is a number - I can convert them later

    private Map<String, Double> calculateSplitSettlement() {

        Map<String, Double> amountsOwed = new HashMap<String, Double>();
        Map<Integer, Double> amountsOwedInt = new HashMap<Integer, Double>();

        // Iterate through each BillItem in items
        for (BillItem item : items) {

            // Retrieve the list of checks
            SparseBooleanArray checkedList = item.getChecksList();

            // Iterate through the checkmarks of each item
            for (int i = 0; i < checkedList.size(); i++) {

                // Check to see if the index of checkedList is true (said person should pay)
                if (checkedList.valueAt(i)) {
                    // Check to see if a key already exists
                    if (!amountsOwedInt.containsKey(checkedList.keyAt(i))) {
                        // If key does not exist, set the key and value
                        amountsOwedInt.put(i, item.getPrice());
                    } else {
                        // Saves the old value to oldVal
                        Double oldVal = amountsOwedInt.get(i);

                        // If key does exist, overwrite and update to the new value
                        amountsOwedInt.replace(i, oldVal + item.getPrice());
                    }

                }
            }
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