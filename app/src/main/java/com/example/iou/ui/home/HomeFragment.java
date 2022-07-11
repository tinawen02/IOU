package com.example.iou.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.iou.EndlessRecyclerViewScrollListener;
import com.example.iou.R;
import com.example.iou.bill.models.BillParse;
import com.example.iou.databinding.FragmentHomeBinding;
import com.example.iou.home.adapters.FeedAdapter;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private RecyclerView rvBills;
    protected FeedAdapter adapter;
    protected List<BillParse> allBills;

    private SwipeRefreshLayout swipeContainer;
    private EndlessRecyclerViewScrollListener scrollListener;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // TODO: unwrap the splitBill parcel


        rvBills = view.findViewById(R.id.rvBills);

        // Create the adapter
        allBills = new ArrayList<>();
        adapter = new FeedAdapter(getContext(), allBills);

        // Set the adapter on the recyclerView
        rvBills.setAdapter(adapter);

        // Set the layout manager on the recycler view
        rvBills.setLayoutManager(new LinearLayoutManager(getContext()));

        // Allows the user to refresh their timeline to update feed time and post
        allowRefreshing(view);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvBills.setLayoutManager(linearLayoutManager);

        // Query the posts
        queryPosts(null, 0);

        // Used to allow endless scrolling
        scrollListener = new com.example.iou.EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                // Triggers when new data is appended to the list
                queryPosts(allBills.get(0).getCreatedAt(), allBills.size());
            }
        };

        // Adds the scroll listener to RecyclerView
        rvBills.addOnScrollListener(scrollListener);

    }

    protected void allowRefreshing(View view) {
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                adapter.clear();
                queryPosts(null, 0);
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
    }

    // Gets all of the posts from Parse
    protected void queryPosts(Date time, int skip) {
        // Specify what type of data we want to query - Post.class
        ParseQuery<BillParse> query = ParseQuery.getQuery(BillParse.class);


        // Include data referred by user key
        query.include(BillParse.KEY_USER);

        // Only includes posts from a logged in user
        query.whereEqualTo(BillParse.KEY_USER, ParseUser.getCurrentUser());

        // Allows for user to vies more than 20 posts
        if(time != null) {
            query.whereLessThan(BillParse.KEY_CREATED_AT, time);
        }

        // Limit query to latest 20 items
        query.setLimit(20);
        query.setSkip(skip);

        // Order posts by creation date (newest first)
        query.addDescendingOrder("createdAt");

        // Start an asynchronous call for bills
        query.findInBackground(new FindCallback<BillParse>() {
            @Override
            public void done(List<BillParse> posts, ParseException e) {
                // Check for errors
                if (e != null) {
                    Toast.makeText(getContext(), "Error while querying!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Save received posts to list and notify adapter of new data
                adapter.addAll(posts);
                swipeContainer.setRefreshing(false);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}