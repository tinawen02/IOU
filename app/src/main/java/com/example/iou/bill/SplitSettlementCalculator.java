package com.example.iou.bill;

import android.util.SparseBooleanArray;

import com.example.iou.bill.models.BillItem;
import com.example.iou.bill.models.SplitBill;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SplitSettlementCalculator {
    private final List<BillItem> items;
    private final SplitBill splitBill;

    public SplitSettlementCalculator(SplitBill splitBill, List<BillItem> items) {
        this.splitBill = splitBill;
        this.items = items;
    }

    // Does the math behind splitting a bill
    public Map<String, Double> calculateSplitSettlement() {

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
                    int keyVal = checkedList.keyAt(i);
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
}
