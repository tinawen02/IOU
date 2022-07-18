package com.example.iou.home.activities;

import static com.example.iou.IOUKeys.BILL_DETAILS_KEY;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iou.R;
import com.example.iou.bill.models.BillParse;

import org.parceler.Parcels;

import java.util.Date;

public class BillDetailsActivity extends AppCompatActivity {

    private LinearLayout llPricesContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_details);

        // Unwrap the parcel
        BillParse bill = Parcels.unwrap(getIntent().getParcelableExtra(BILL_DETAILS_KEY));

        final TextView tvLocationDetails = findViewById(R.id.tvLocationDetails);
        final TextView tvBillAmountDetails = findViewById(R.id.tvBillAmountDetails);
        //final TextView tvAmountsOwedDetails = findViewById(R.id.tvAmountsOwedDetails);
        final TextView tvTimeStampDetails = findViewById(R.id.tvTimeStampDetails);
        llPricesContainer = findViewById(R.id.llPricesContainer);

        //

        llPricesContainer.removeAllViewsInLayout();

        // Programmatically define the number of checkboxes in each recycler view
//        for (int i = 0; i < splitBill.getPeople().size(); i++) {
//
//            CheckBox checkbox = new CheckBox(context);
//            checkbox.setText(splitBill.getPeople().get(i));
//            checkbox.setLayoutParams(new LinearLayout.LayoutParams(
//                    LinearLayout.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT));
//
//            // Set the font
//            checkbox.setTypeface(ResourcesCompat.getFont(context, R.font.lemon_milk_light));
//
//            // Checks to see if a checkbox was clicked or not
//            int finalI = i;
//            checkbox.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    boolean checked = !bill.getChecks(finalI);
//                    // Set the UI to display the check or uncheck
//                    checkbox.setChecked(checked);
//                    // Update the BillItem bill
//                    bill.toggleCheckbox(finalI, checked);
//                }
//            });
//            llCheckboxesContainer.addView(checkbox);
//        }




        tvLocationDetails.setText(bill.getLocation());
        tvBillAmountDetails.setText(String.valueOf(bill.getFinalBill()));
        //tvAmountsOwedDetails.setText(bill.getAmountsOwed());

        // Calculate relative date of the post
        Date createdAt = bill.getCreatedAt();
        String timeAgo = BillParse.calculateTimeAgo(createdAt);
        tvTimeStampDetails.setText(timeAgo);
    }
}