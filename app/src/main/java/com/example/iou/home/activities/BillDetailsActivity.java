package com.example.iou.home.activities;

import static com.example.iou.IOUKeys.BILL_DETAILS_KEY;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.iou.R;
import com.example.iou.bill.models.BillItem;
import com.example.iou.bill.models.BillParse;

import org.parceler.Parcels;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class BillDetailsActivity extends AppCompatActivity {

    private LinearLayout llPricesContainer;
    private final BillItem billItem = new BillItem();
    private BillParse bill;
    private static final DecimalFormat decimalFormat = new DecimalFormat("$0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);

        // Unwrap the parcel
        bill = Parcels.unwrap(getIntent().getParcelableExtra(BILL_DETAILS_KEY));

        final TextView tvLocationDetails = findViewById(R.id.tvLocationDetails);
        final TextView tvBillAmountDetails = findViewById(R.id.tvBillAmountDetails);
        final TextView tvAmountsOwedDetails = findViewById(R.id.tvAmountsOwedDetails);
        final TextView tvTimeStampDetails = findViewById(R.id.tvTimeStampDetails);
        llPricesContainer = findViewById(R.id.llPricesContainer);

        tvLocationDetails.setText(bill.getLocation());
        tvBillAmountDetails.setText("Total Bill: " + decimalFormat.format(bill.getFinalBill()));
        tvAmountsOwedDetails.setText(bill.getAmountsOwed());

        // Calculate relative date of the post
        Date createdAt = bill.getCreatedAt();
        String timeAgo = BillParse.calculateTimeAgo(createdAt);
        tvTimeStampDetails.setText(timeAgo);

        // Creates the checkboxes to help user determine which people have paid
        createCheckboxes();
    }


    private void createCheckboxes() {
        // Set the names of people for the BillItem
        List<String> namesOfPeople = getNames(bill.getAmountsOwed());
        billItem.setPeople(namesOfPeople);

        // Gets the number of people who dined
        int numPeople = new StringTokenizer(bill.getAmountsOwed(), "\r\n").countTokens();

        llPricesContainer.removeAllViewsInLayout();

        // Retrieves the status of the boxes which are checked/unchecked from Parse
        List<Boolean> currentSelectedIndices = bill.getSelectedIndices();

        // Only initializes a list of Booleans if user has not checked any boxes
        if (!containsTrue(currentSelectedIndices)) {
            // Initialize the array with false's
            Boolean[] initializedArray = new Boolean[numPeople];
            Arrays.fill(initializedArray, false);

            // Replace the array with a list
            List<Boolean> initializedList = new ArrayList<>(Arrays.asList(initializedArray));

            // Update the status of the checkboxes in Parse
            bill.setSelectedIndices(initializedList);
            bill.saveInBackground(e -> {
                if (e != null) {
                    Toast.makeText(BillDetailsActivity.this, "Error while saving to Parse!", Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Programmatically adds numPeople number of checkboxes to each bill
        addDynamicCheckboxes(numPeople);
    }

    private List<String> getNames(String amountsOwed) {
        List<String> namesOfPeople = new ArrayList<>();

        // Splits the string by line
        String[] eachLine = amountsOwed.split("\n");

        // Adds the first word of each line to namesOfPeople
        for (String line : eachLine) {
            String name = line.split(" ")[0];
            namesOfPeople.add(name);
        }
        return namesOfPeople;
    }

    private boolean containsTrue(List<Boolean> currentSelectedIndices) {
        // Iterates through each individual boolean in the list
        for (Boolean bool : currentSelectedIndices) {
            // If the list contains true, returns true
            if (bool.equals(true)) {
                return true;
            }
        }
        return false;
    }

    private void addDynamicCheckboxes(int numPeople) {
        // Programmatically define the number of checkboxes in each recycler view
        for (int i = 0; i < numPeople; i++) {
            // Creates a new checkbox for each name
            CheckBox checkbox = new CheckBox(this);
            checkbox.setText(billItem.getPeople().get(i));
            checkbox.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            // Set the font containing the text of each checkbox
            checkbox.setTypeface(ResourcesCompat.getFont(this, R.font.lemon_milk_light));

            int finalI = i;

            // Used to ensure the UI is correctly showcasing whether a checkbox is checked
            boolean isChecked = bill.getSelectedIndices().get(finalI);
            checkbox.setChecked(isChecked);

            // Checks to see if a checkbox was clicked or not
            checkbox.setOnClickListener(v -> {
                // Gets the current status of the checkbox
                boolean checked = bill.getSelectedIndices().get(finalI);

                // Set the UI to display the check or uncheck
                if (checked) {
                    checkbox.setChecked(false);
                    checked = false;
                }
                else {
                    checkbox.setChecked(true);
                    checked = true;
                }

                // Update list of selected indices in Parse
                List<Boolean> currentParseSelectedIndices = bill.getSelectedIndices();
                currentParseSelectedIndices.set(finalI, checked);
                bill.setSelectedIndices(currentParseSelectedIndices);
                bill.saveInBackground(e -> {
                    if (e != null) {
                        Toast.makeText(BillDetailsActivity.this, "Error while saving to Parse!", Toast.LENGTH_SHORT).show();
                    }
                });
            });
            llPricesContainer.addView(checkbox);
        }
    }
}