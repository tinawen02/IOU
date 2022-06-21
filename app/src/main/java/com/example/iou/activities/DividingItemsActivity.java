package com.example.iou.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.iou.R;

public class DividingItemsActivity extends AppCompatActivity {

    private Button btnDivideNext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dividing_items);

        // Find view by Id
        btnDivideNext = findViewById(R.id.btnDivideNext);

        // Brings user to Split Settlement Activity
        toSplitSettlement(btnDivideNext);
    }

    // Brings user to the Split Settlement Activity
    private void toSplitSettlement(Button btnSplitNext) {
        btnSplitNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Send information to the Split Settlement Activity
                Intent i = new Intent(DividingItemsActivity.this, SplitSettlementActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}