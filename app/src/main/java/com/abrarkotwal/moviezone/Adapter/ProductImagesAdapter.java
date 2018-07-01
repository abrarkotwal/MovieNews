package com.abrarkotwal.moviezone.Adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.abrarkotwal.moviezone.Adapter.Pojo.MovieImages;
import com.abrarkotwal.moviezone.R;
import com.bumptech.glide.Glide;

import java.util.List;

import static com.abrarkotwal.moviezone.DB.Api.IMG_DISP_URL;

public class ProductImagesAdapter extends PagerAdapter {

    private List<MovieImages> images;
    private LayoutInflater inflater;
    private Context context;

    public ProductImagesAdapter(Context context, List<MovieImages> images) {
        this.context = context;
        this.images=images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View myImageLayout = inflater.inflate(R.layout.movie_image_slider, view, false);
        ImageView myImage = (ImageView) myImageLayout
                .findViewById(R.id.image);

        Glide.with(context).load(IMG_DISP_URL+images.get(position).getImagePath()).into(myImage);
        view.addView(myImageLayout, 0);
        return myImageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}