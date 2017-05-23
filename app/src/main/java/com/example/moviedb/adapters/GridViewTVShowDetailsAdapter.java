package com.example.moviedb.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.activity.ActivityDetails;
import com.example.moviedb.activity.ActivityTVShowDetails;
import com.example.moviedb.converter.DateConverter;
import com.example.moviedb.model.tv.details.ResultTV;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GridViewTVShowDetailsAdapter extends BaseAdapter {

    List<ResultTV> relatedMovies;
    private Context context;
    LayoutInflater layoutInflater;
    private final int CacheSize = 52428800;
    private final int MinFreeSpace = 2048;

    public GridViewTVShowDetailsAdapter(Context context, List<ResultTV> relatedMovies) {
        this.context = context;
        this.relatedMovies = relatedMovies;
    }

    @Override
    public int getCount() {
        if (relatedMovies.size() >= 6) {
            return 6;
        } else
            return relatedMovies.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class Holder {
        ImageView imageView;
        TextView textViewName;
        TextView textViewYear;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final GridViewTVShowDetailsAdapter.Holder holder = new GridViewTVShowDetailsAdapter.Holder();
        final View rowView;
        rowView = layoutInflater.inflate(R.layout.item_card_for_related_movies, null);
        holder.textViewName = (TextView) rowView.findViewById(R.id.text_view_title_for_card_view_related_movies);
        holder.textViewYear = (TextView) rowView.findViewById(R.id.text_view_year_for_card_view_realted_movies);
        holder.imageView = (ImageView) rowView.findViewById(R.id.image_view_for_card_view_related_movies);
        holder.textViewName.setEllipsize(TextUtils.TruncateAt.END);
        holder.textViewName.setMaxLines(2);
        holder.textViewName.setText(relatedMovies.get(position).getName());
        Collections.sort(relatedMovies, new Comparator<ResultTV>() {
            @Override
            public int compare(ResultTV o1, ResultTV o2) {
                if (o1.getFirstAirDate() == null || o2.getFirstAirDate() == null) {
                    return 0;
                }
                return Integer.parseInt(DateConverter.formateDateFromstring("yyyy-MM-dd", "yyyy", o2.getFirstAirDate())) -
                        Integer.parseInt(DateConverter.formateDateFromstring("yyyy-MM-dd", "yyyy", o1.getFirstAirDate()));
            }
        });
        holder.textViewYear.setText(DateConverter.formateDateFromstring("yyyy-MM-dd", "yyyy",
                relatedMovies.get(position).getFirstAirDate()));

        File cacheDir = StorageUtils.getCacheDirectory(context);
        long size = 0;
        File[] filesCache = cacheDir.listFiles();
        for (File file : filesCache) {
            size += file.length();
        }
        if (cacheDir.getUsableSpace() < MinFreeSpace || size > CacheSize) {
            ImageLoader.getInstance().getDiskCache().clear();
        }

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Glide
                        .with(context)
                        .load(Const.IMAGE_POSTER_PATH_URL + relatedMovies.get(position).getPosterPath())
                        .override(100, 100)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.placeholder_item_recycler_view)
                        .crossFade()
                        .into(holder.imageView);
            }
        });
        t.run();

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == rowView.getId()) {
                    Intent intent = new Intent(context, ActivityDetails.class);
                    intent.putExtra("id", relatedMovies.get(position).getId());
                    intent.putExtra("title", relatedMovies.get(position).getName());
                    context.startActivity(intent);
                }
            }
        });

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == rowView.getId()) {
                    Intent intent = new Intent(context, ActivityTVShowDetails.class);
                    intent.putExtra("id", relatedMovies.get(position).getId());
                    intent.putExtra("title", relatedMovies.get(position).getName());
                    context.startActivity(intent);
                }
            }
        });

        return rowView;
    }
}
