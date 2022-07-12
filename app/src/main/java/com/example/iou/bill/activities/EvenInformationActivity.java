package com.example.iou.bill.activities;

import static com.example.iou.IOUKeys.SPLIT_BILL_INFORMATION_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.iou.R;
import com.example.iou.bill.models.SplitBill;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EvenInformationActivity extends AppCompatActivity {

    private EditText etLocationEven;
    private EditText etPeopleEven;
    private EditText etFinalBillEven;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_even_information);

        etLocationEven = findViewById(R.id.etLocationEven);
        etPeopleEven = findViewById(R.id.etPeopleEven);
        etFinalBillEven = findViewById(R.id.etFinalBillEven);
        final Button btnEvenNext = findViewById(R.id.btnEvenNext);

        // Bring user to the Even Settlement Activity and passes on relevant information
        btnEvenNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toEvenSettlement();
            }
        });

    }

    // Bring user to the Dividing Items Activity and passes on relevant information
    private void toEvenSettlement() {
        // Set the views
        final String location = etLocationEven.getText().toString();
        String[] peopleArray = etPeopleEven.getText().toString().replace(" ", "").split(",");
        final double billTotal = Double.parseDouble(etFinalBillEven.getText().toString());

        // Converting people to a List of Strings
        List<String> people = new ArrayList<>();
        Collections.addAll(people, peopleArray);

        // Create a new splitBill;
        final SplitBill splitBill = new SplitBill(location, people, billTotal);

        // Send information to the Even Settlement Activity
        Intent i = new Intent(EvenInformationActivity.this, EvenSettlementActivity.class);
        i.putExtra(SPLIT_BILL_INFORMATION_KEY, Parcels.wrap(splitBill));
        startActivity(i);
    }

}