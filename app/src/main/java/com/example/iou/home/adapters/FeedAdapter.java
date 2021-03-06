package com.example.iou.home.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iou.R;
import com.example.iou.bill.models.BillParse;
import com.example.iou.home.activities.BillDetailsActivity;

import org.parceler.Parcels;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;


public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private final Context context;
    private final List<BillParse> bills;
    private static final DecimalFormat decimalFormat = new DecimalFormat("$0.00");

    public FeedAdapter(Context context, List<BillParse> bills) {
        this.context = context;
        this.bills = bills;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bill_feed, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FeedAdapter.ViewHolder holder, int position) {
        BillParse bill = bills.get(position);
        holder.bind(bill);
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvLocationFeed;
        private final TextView tvBillAmountFeed;
        private final TextView tvAmountsOwedFeed;
        private final TextView tvTimeStampFeed;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvLocationFeed = itemView.findViewById(R.id.tvLocationFeed);
            tvBillAmountFeed = itemView.findViewById(R.id.tvBillAmountFeed);
            tvAmountsOwedFeed = itemView.findViewById(R.id.tvAmountsOwedFeed);
            tvTimeStampFeed = itemView.findViewById(R.id.tvTimeStampFeed);

            itemView.setOnClickListener(this);
        }

        public void bind(BillParse bill) {

            // Bind the post data to the view elements
            tvLocationFeed.setText(bill.getLocation());
            tvBillAmountFeed.setText("Total Bill: " + decimalFormat.format(bill.getFinalBill()));
            tvAmountsOwedFeed.setText(bill.getAmountsOwed());

            // Calculate relative date of the post
            Date createdAt = bill.getCreatedAt();
            String timeAgo = BillParse.calculateTimeAgo(createdAt);
            tvTimeStampFeed.setText(timeAgo);
        }

        @Override
        public void onClick(View v) {
            int position = getAbsoluteAdapterPosition(); // gets item position
            if (position != RecyclerView.NO_POSITION) { // Check if an item was deleted, but the user clicked it before the UI removed it
                BillParse bill = bills.get(position);
                Intent intent = new Intent(context, BillDetailsActivity.class);
                // Serialize the bill using parceler, use its short name as a key
                intent.putExtra("BILL_DETAILS", Parcels.wrap(bill));
                context.startActivity(intent);
            }
        }
    }

    // Clean all posts of the recycler
    public void clear() {
        bills.clear();
        notifyDataSetChanged();
    }

    // Add a list of bills
    public void addAll(List<BillParse> list) {
        bills.addAll(list);
        notifyDataSetChanged();
    }
}