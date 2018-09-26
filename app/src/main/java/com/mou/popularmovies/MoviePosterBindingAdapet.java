package com.mou.popularmovies;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mou.popularmovies.databinding.TrailerItemBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MoviePosterBindingAdapet {

    @BindingAdapter("android:bindImage")
    public static void loadImage(ImageView imageView, String imageUrl) {
        Picasso.with(imageView.getContext()).load(imageUrl).into(imageView);
    }


    @BindingAdapter({"entries", "layout", "vm"})
    public static <T> void setEntries(ViewGroup viewGroup, List<T> entries, int layoutId, MoviePosterDetailsViewModel vm) {
        viewGroup.removeAllViews();
        if (entries != null) {
            LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (layoutId == R.layout.trailer_item) {
                if (entries.size() == 0){
                    TrailerItemBinding binding = DataBindingUtil.inflate(inflater, layoutId, viewGroup, false);
                    binding.name.setText(viewGroup.getContext().getResources().getString(R.string.no_trailer_text));
                    viewGroup.addView(binding.getRoot());
                }else {
                    for (int i = 0; i < entries.size(); i++) {
                        TrailerItemBinding binding = DataBindingUtil.inflate(inflater, layoutId, viewGroup, false);
                        T entry = entries.get(i);
                        binding.name.setText((String) entry);
                        int index = i;
                        binding.name.setOnClickListener(v -> vm.displayTrailer(index));
                        viewGroup.addView(binding.getRoot());
                    }
                }
            }
        }
    }
}
