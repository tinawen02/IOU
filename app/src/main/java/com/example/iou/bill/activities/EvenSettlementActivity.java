package com.example.iou.bill.activities;

import static com.example.iou.IOUKeys.SPLIT_BILL_INFORMATION_KEY;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.iou.MainActivity;
import com.example.iou.R;
import com.example.iou.bill.models.BillParse;
import com.example.iou.bill.models.SplitBill;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.parceler.Parcels;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.Position;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.xml.KonfettiView;
import nl.dionsegijn.konfetti.core.models.Size;

public class EvenSettlementActivity extends AppCompatActivity {

    private KonfettiView konfettiView;
    private Shape.DrawableShape drawableShape = null;
    private final BillParse bill = new BillParse();
    private static final DecimalFormat decimalFormat = new DecimalFormat("$0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_even_settlement);

        // Unwrap the information from the Dividing Items Activity
        final SplitBill splitBill = Parcels.unwrap(getIntent().getParcelableExtra(SPLIT_BILL_INFORMATION_KEY));

        final Button btnSaveBillEven = findViewById(R.id.btnSaveBillEven);
        final TextView tvLocationEven = findViewById(R.id.tvLocationEven);
        final TextView tvBillAmountEven = findViewById(R.id.tvBillAmountEven);
        final TextView tvAmountsOwedEven = findViewById(R.id.tvAmountsOwedEven);
        konfettiView = findViewById(R.id.konfettiView);

        // Set the views with specific information regarding the transaction
        tvLocationEven.setText(splitBill.getRestaurantName());
        tvBillAmountEven.setText("Total Bill: " + decimalFormat.format(splitBill.getBillTotal()));

        // Calculate the amounts each individual owes
        int numPeople = splitBill.getPeople().size();
        Double amountOwed = (double) Math.round(splitBill.getBillTotal() / numPeople * 100) / 100;

        // Build the string of names of people and amounts each person owes
        StringBuilder str = new StringBuilder();

        for (String name : splitBill.getPeople()) {
            str.append(name + " owes " + decimalFormat.format(amountOwed) + "\n");
        }

        // Set the names of people and amounts each person owes
        tvAmountsOwedEven.setText(str);

        bill.setLocation(splitBill.getRestaurantName());
        bill.setUser(ParseUser.getCurrentUser());
        bill.setFinalBill(splitBill.getBillTotal());
        bill.setAmountsOwed(str.toString());

        bill.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Toast.makeText(EvenSettlementActivity.this, "Error while saving!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Bring user to the Home Fragment and displays confetti
        btnSaveBillEven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startConfetti();
            }
        });
    }

    // Starts the animation once a button is clicked
    private void startConfetti() {
        final Drawable drawable = ContextCompat.getDrawable(getApplicationContext(), R.drawable.ic_dashboard_black_24dp);
        drawableShape = new Shape.DrawableShape(drawable, true);
        EmitterConfig emitterConfig = new Emitter(5L, TimeUnit.SECONDS).perSecond(50);
        Party party = new PartyFactory(emitterConfig)
                .angle(270)
                .spread(90)
                .setSpeedBetween(1f, 5f)
                .timeToLive(2000L)
                .shapes(new Shape.Rectangle(0.2f), drawableShape)
                .sizes(new Size(12, 5f, 0.2f))
                .position(0.0, 0.0, 1.0, 0.0)
                .build();

        // Sets an on click listener on the KonfettiView
        konfettiView.setOnClickListener(view ->
                konfettiView.start(party)
        );

        // Shows exploding confetti
        explode();

        // Delays the transition to the home fragment for 3 seconds
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                toHomeFragment();
            }
        }, 2000);
    }

    // Brings a user to the Home Fragment when a button is clicked
    private void toHomeFragment() {
        Intent i = new Intent(EvenSettlementActivity.this, MainActivity.class);
        startActivity(i);
    }

    // Completes an animation with confetti once a user clicks the "save bill" button
    public void explode() {
        EmitterConfig emitterConfig = new Emitter(100L, TimeUnit.MILLISECONDS).max(100);
        konfettiView.start(
                new PartyFactory(emitterConfig)
                        .spread(360)
                        .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE, drawableShape))
                        .colors(Arrays.asList(0xbd8334, 0xA865C9, 0xb48def))
                        .setSpeedBetween(0f, 30f)
                        .position(new Position.Relative(0.5, 0.3))
                        .build()
        );
    }
}