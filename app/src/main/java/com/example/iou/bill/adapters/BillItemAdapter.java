package com.example.iou.bill.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iou.R;
import com.example.iou.bill.models.BillItem;
import com.example.iou.bill.models.SplitBill;

import java.text.DecimalFormat;
import java.util.List;

public class BillItemAdapter extends RecyclerView.Adapter<BillItemAdapter.ViewHolder> {

    private final Context context;
    private final List<BillItem> bills;
    private final SplitBill splitBill;
    private static final DecimalFormat decimalFormat = new DecimalFormat("$0.00");

    public BillItemAdapter(Context context, List<BillItem> bills, SplitBill splitBill) {
        this.context = context;
        this.bills = bills;
        this.splitBill = splitBill;
    }

    // Inflate the layout for each bill item
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_bill, parent, false);
        return new ViewHolder(view);
    }

    // Bind values based on the position of the element
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the position of the bill
        BillItem bill = bills.get(position);
        holder.bind(bill);
    }

    @Override
    public int getItemCount() {
        return bills.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView tvItemPrice;
        private final LinearLayout llCheckboxesContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvItemPrice = itemView.findViewById(R.id.tvItemPrice);
            this.llCheckboxesContainer = itemView.findViewById(R.id.llCheckboxesContainer);
        }

        @Override
        public void onClick(View v) {

        }

        public void bind(BillItem bill) {

            // Bind the bill item data to the view elements
            tvItemPrice.setText(decimalFormat.format((bill.getPrice())));
            llCheckboxesContainer.removeAllViewsInLayout();

            // Programmatically define the number of checkboxes in each recycler view
            for (int i = 0; i < splitBill.getPeople().size(); i++) {

                CheckBox checkbox = new CheckBox(context);
                checkbox.setText(splitBill.getPeople().get(i));
                checkbox.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));

                // Set the font
                checkbox.setTypeface(ResourcesCompat.getFont(context, R.font.lemon_milk_light));

                // Checks to see if a checkbox was clicked or not
                int finalI = i;
                checkbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean checked = !bill.getChecks(finalI);
                        // Set the UI to display the check or uncheck
                        checkbox.setChecked(checked);
                        // Update the BillItem bill
                        bill.toggleCheckbox(finalI, checked);
                    }
                });
                llCheckboxesContainer.addView(checkbox);
            }
        }
    }

    // Clean all elements of the recycler
    public void clear() {
        bills.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<BillItem> list) {
        bills.addAll(list);
        notifyDataSetChanged();
    }
}
