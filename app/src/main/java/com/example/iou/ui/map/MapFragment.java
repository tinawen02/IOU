package com.example.iou.ui.map;

import static com.example.iou.IOUKeys.WHERE_TO_EAT_KEY;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.iou.R;
import com.example.iou.databinding.FragmentMapBinding;
import com.example.iou.map.activities.GoogleMapsActivity;

import org.parceler.Parcels;


public class MapFragment extends Fragment {

    private FragmentMapBinding binding;

    // Required empty constructor
    public MapFragment () { }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MapViewModel MapViewModel =
                new ViewModelProvider(this).get(MapViewModel.class);

        binding = FragmentMapBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textMap;
        MapViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final Button btnToMap = view.findViewById(R.id.btnToMap);
        final EditText etWhereTo = view.findViewById(R.id.etWhereTo);

        btnToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String whereTo = etWhereTo.getText().toString();

                // Brings the user to the GoogleMapsActivity
                toGoogleMapsActivity(whereTo);
            }
        });
    }

    private void toGoogleMapsActivity(String whereTo) {
        Intent i = new Intent(getContext(), GoogleMapsActivity.class);
        i.putExtra(WHERE_TO_EAT_KEY, Parcels.wrap(whereTo));
        startActivity(i);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}