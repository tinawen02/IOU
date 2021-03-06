package com.example.iou.bill.activities;

import static com.example.iou.IOUKeys.AMOUNTS_OWED_KEY;
import static com.example.iou.IOUKeys.SPLIT_BILL_INFORMATION_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iou.R;
import com.example.iou.bill.SplitSettlementCalculator;
import com.example.iou.bill.adapters.BillItemAdapter;
import com.example.iou.bill.models.BillItem;
import com.example.iou.bill.models.SplitBill;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DividingItemsActivity extends AppCompatActivity {

    private SplitBill splitBill;
    private List<BillItem> items;

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

        final BillItemAdapter adapter = new BillItemAdapter(this, items, splitBill);

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

    // Brings user to the Split Settlement Activity
    private void toSplitSettlement() {

        SplitSettlementCalculator calculator = new SplitSettlementCalculator(splitBill, items);

        Map<String, Double> amountsOwed = calculator.calculateSplitSettlement();

        // Send information to the Split Settlement Activity
        Intent i = new Intent(DividingItemsActivity.this, SplitSettlementActivity.class);
        i.putExtra(SPLIT_BILL_INFORMATION_KEY, Parcels.wrap(splitBill));
        i.putExtra(AMOUNTS_OWED_KEY, Parcels.wrap(amountsOwed));
        startActivity(i);
    }
}