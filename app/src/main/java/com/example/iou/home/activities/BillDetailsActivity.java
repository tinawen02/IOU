package com.example.iou.home.activities;

import static com.example.iou.IOUKeys.BILL_DETAILS_KEY;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.iou.R;
import com.example.iou.bill.models.BillItem;
import com.example.iou.bill.models.BillParse;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

public class BillDetailsActivity extends AppCompatActivity {

    private LinearLayout llPricesContainer;
    private BillItem billItem = new BillItem();

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
        llPricesContainer = findViewById(R.id.llPricesContainer);

        //

        // Set the names of people for the BillItem
        List<String> namesOfPeople = getNames(bill.getAmountsOwed());
        billItem.setPeople(namesOfPeople);

        // Gets the number of people who dined
        int numPeople = new StringTokenizer(bill.getAmountsOwed(), "\r\n").countTokens();

        llPricesContainer.removeAllViewsInLayout();

        // Initialize the list with falses
        //List<Boolean> initializedList = new ArrayList<>(numPeople);
        //List<Boolean> initializedList = Arrays.asList(new Boolean[numPeople]);

        Boolean initializedArray[] = new Boolean[numPeople];
        Arrays.fill(initializedArray, false);

        // Create an empty List
        List<Boolean> initializedList = new ArrayList<>();

        // Iterate through the array
        for (Boolean bool : initializedArray) {
            // Add each element into the list
            initializedList.add(bool);
        }

        bill.setSelectedIndices(initializedList);

        // Programmatically define the number of checkboxes in each recycler view
        for (int i = 0; i < numPeople; i++) {

            CheckBox checkbox = new CheckBox(this);
            checkbox.setText(billItem.getPeople().get(i));
            checkbox.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));

            // Set the font
            checkbox.setTypeface(ResourcesCompat.getFont(this, R.font.lemon_milk_light));

            // Checks to see if a checkbox was clicked or not
            int finalI = i;
            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    boolean checked = !billItem.getChecks(finalI);
                    // Set the UI to display the check or uncheck
                    checkbox.setChecked(checked);
                    // Update the BillItem bill
                    billItem.toggleCheckbox(finalI, checked);

                    // Update list of selected indices in Parse
                    List<Boolean> currentParseSelectedIndices = bill.getSelectedIndices();
                    currentParseSelectedIndices.set(finalI, checked);
                    bill.setSelectedIndices(currentParseSelectedIndices);
                }
            });
            llPricesContainer.addView(checkbox);
        }


        tvLocationDetails.setText(bill.getLocation());
        tvBillAmountDetails.setText(String.valueOf(bill.getFinalBill()));
        tvAmountsOwedDetails.setText(bill.getAmountsOwed());

        // Calculate relative date of the post
        Date createdAt = bill.getCreatedAt();
        String timeAgo = BillParse.calculateTimeAgo(createdAt);
        tvTimeStampDetails.setText(timeAgo);
    }


    private List<String> getNames(String amountsOwed) {
        List<String> namesOfPeople = new ArrayList<>();

        List<String> eachLine = Arrays.asList(amountsOwed.split("\n"));
        for (String line : eachLine) {
            String name = line.split(" ")[0];
            namesOfPeople.add(name);
        }
        return namesOfPeople;
    }
}