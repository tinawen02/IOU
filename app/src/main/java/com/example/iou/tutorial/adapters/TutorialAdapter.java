package com.example.iou.tutorial.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.example.iou.R;
import com.example.iou.tutorial.models.TutorialItem;

import java.util.List;

public class TutorialAdapter extends PagerAdapter {

    Context context ;
    List<TutorialItem> tutorialItems;

    public TutorialAdapter(Context context, List<TutorialItem> mListScreen) {
        this.context = context;
        this.tutorialItems = mListScreen;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        // Inflate the layout for each tutorial item
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layoutScreen = layoutInflater.inflate(R.layout.item_tutorial,null);

        final TextView title = layoutScreen.findViewById(R.id.tvTitle);
        final TextView description = layoutScreen.findViewById(R.id.tvDescription);
        final ImageView image = layoutScreen.findViewById(R.id.ivImage);

        title.setText(tutorialItems.get(position).getTitle());
        description.setText(tutorialItems.get(position).getDescription());
        image.setImageResource(tutorialItems.get(position).getScreenImg());

        // Creates a tutorial page for a given position
        container.addView(layoutScreen);

        return layoutScreen;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        // Removes a page for the given position
        container.removeView((View)object);
    }

    @Override
    public int getCount() {
        // Returns the number of views available
        return tutorialItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        // Returns true if view is associated with tht key object o
        return view == o;
    }


}