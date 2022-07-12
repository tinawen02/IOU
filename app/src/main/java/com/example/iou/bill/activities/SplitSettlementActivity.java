package com.example.iou.bill.activities;

import static com.example.iou.IOUKeys.AMOUNTS_OWED_KEY;
import static com.example.iou.IOUKeys.SPLIT_BILL_INFORMATION_KEY;
import static com.example.iou.IOUKeys.USER_BILL_KEY;

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
import java.util.Map;
import java.util.concurrent.TimeUnit;

import nl.dionsegijn.konfetti.core.Party;
import nl.dionsegijn.konfetti.core.PartyFactory;
import nl.dionsegijn.konfetti.core.Position;
import nl.dionsegijn.konfetti.core.emitter.Emitter;
import nl.dionsegijn.konfetti.core.emitter.EmitterConfig;
import nl.dionsegijn.konfetti.core.models.Shape;
import nl.dionsegijn.konfetti.core.models.Size;
import nl.dionsegijn.konfetti.xml.KonfettiView;

public class SplitSettlementActivity extends AppCompatActivity {

    private SplitBill splitBill;
    private KonfettiView konfettiView;
    private Shape.DrawableShape drawableShape = null;
    private final BillParse bill = new BillParse();
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_split_settlement);

        // Unwrap the information from the Dividing Items Activity
        splitBill = Parcels.unwrap(getIntent().getParcelableExtra(SPLIT_BILL_INFORMATION_KEY));
        Map<String, Double> amountsOwed = Parcels.unwrap(getIntent().getParcelableExtra(AMOUNTS_OWED_KEY));

        final Button btnSaveBillSplit = findViewById(R.id.btnSaveBillSplit);
        final TextView tvLocation = findViewById(R.id.tvLocation);
        final TextView tvBillAmount = findViewById(R.id.tvBillAmount);
        final TextView tvAmountsOwed = findViewById(R.id.tvAmountsOwed);
        konfettiView = findViewById(R.id.konfettiViewSplit);

        // Set the views with specific information regarding the transaction
        tvLocation.setText(splitBill.getRestaurantName());
        tvBillAmount.setText(String.valueOf(splitBill.getBillTotal()));

        // Build the string of names of people and amounts each person owes
        StringBuilder str = new StringBuilder();
        for (String name : amountsOwed.keySet()) {
                String value = decimalFormat.format(amountsOwed.get(name));
                str.append(name + " owes $" + value + "\n");
            }

        // Set the names of people and amounts each person owes
        tvAmountsOwed.setText(str);

        // Update BillParse information to store in Parse
        bill.setLocation(splitBill.getRestaurantName());
        bill.setUser(ParseUser.getCurrentUser());
        bill.setFinalBill(splitBill.getBillTotal());
        bill.setAmountsOwed(str.toString());

        bill.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) {
                    Toast.makeText(SplitSettlementActivity.this, "Error while saving!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Bring user to the Home Fragment
        btnSaveBillSplit.setOnClickListener(new View.OnClickListener() {
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

        // Delays the transition to the home fragment for 2 seconds
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
        Intent i = new Intent(SplitSettlementActivity.this, MainActivity.class);
        i.putExtra(USER_BILL_KEY, Parcels.wrap(bill));
        i.putExtra(SPLIT_BILL_INFORMATION_KEY, Parcels.wrap(splitBill));
        startActivity(i);
    }

    // Completes an animation with confetti once a user clicks the "save bill" button
    public void explode() {
        EmitterConfig emitterConfig = new Emitter(100L, TimeUnit.MILLISECONDS).max(100);
        konfettiView.start(
                new PartyFactory(emitterConfig)
                        .spread(360)
                        .shapes(Arrays.asList(Shape.Square.INSTANCE, Shape.Circle.INSTANCE, drawableShape))
                        .colors(Arrays.asList(0xfce18a, 0xff726d, 0xf4306d, 0xb48def))
                        .setSpeedBetween(0f, 30f)
                        .position(new Position.Relative(0.5, 0.3))
                        .build()
        );
    }
}