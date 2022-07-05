package com.example.iou;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.iou.ui.bill.BillFragment;
import com.example.iou.ui.home.HomeFragment;
import com.example.iou.ui.map.MapFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        if (position == 0) {
            return new HomeFragment();
        }
        else if (position == 1) {
            return new BillFragment();
        }
        else {
            return new MapFragment();
        }
    }

    @Override
    public int getCount()
    {
        return 3;
    }

}
