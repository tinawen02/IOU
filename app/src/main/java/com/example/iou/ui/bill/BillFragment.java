package com.example.iou.ui.bill;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.iou.R;
import com.example.iou.activities.SplitInformationActivity;
import com.example.iou.databinding.FragmentBillBinding;

public class BillFragment extends Fragment {

    private static final String TAG = "BillFragment";
    private Button btnSplitItem;
    private Button btnSplitEven;

    private FragmentBillBinding binding;

    // Required empty public constructor
    public BillFragment() { }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        BillViewModel billViewModel =
                new ViewModelProvider(this).get(BillViewModel.class);

        binding = FragmentBillBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textBill;
        billViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Finding the views by Id
        btnSplitItem = view.findViewById(R.id.btnSplitItem);
        btnSplitEven = view.findViewById(R.id.btnSplitEven);

        // Brings user to the Split Information Activity
        splitItem(btnSplitItem);
    }

    // Brings user to the Split Information Activity
    private void splitItem(Button btnSplitItem) {
        btnSplitItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Send information to the Split Information Activity
                Intent i = new Intent(getContext(), SplitInformationActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}