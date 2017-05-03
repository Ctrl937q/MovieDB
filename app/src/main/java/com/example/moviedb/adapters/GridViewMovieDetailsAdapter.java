package com.example.moviedb.adapters;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.example.moviedb.activity.ActivityDetails;
import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.converter.DateConverter;
import com.example.moviedb.model.Similar;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridViewMovieDetailsAdapter extends BaseAdapter {

    List<Similar.Result> relatedMovies;
    private Context context;
    LayoutInflater layoutInflater;

    public GridViewMovieDetailsAdapter(Context context, List<Similar.Result> relatedMovies) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.relatedMovies = relatedMovies;
    }

    @Override
    public int getCount() {
        return 6;
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
        final Holder holder = new Holder();
        final View rowView;
        rowView = layoutInflater.inflate(R.layout.item_card_for_related_movies, null);
        holder.textViewName = (TextView) rowView.findViewById(R.id.text_view_title_for_card_view_related_movies);
        holder.textViewYear = (TextView) rowView.findViewById(R.id.text_view_year_for_card_view_realted_movies);
        holder.imageView = (ImageView) rowView.findViewById(R.id.image_view_for_card_view_related_movies);
        holder.textViewName.setEllipsize(TextUtils.TruncateAt.END);
        holder.textViewName.setMaxLines(2);

        holder.textViewName.setText(relatedMovies.get(position).getTitle());
        holder.textViewYear.setText(DateConverter.formateDateFromstring("yyyy-MM-dd", "yyyy",
                relatedMovies.get(position).getReleaseDate()));
        Picasso.with(context).load(Const.IMAGE_POSTER_PATH_URL + relatedMovies
                .get(position).getPosterPath()).placeholder(R.drawable.placeholder_item_recycler_view)
                .resize(350, 550)
                .into(holder.imageView);
        
        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == rowView.getId()) {
                    Intent intent = new Intent(context, ActivityDetails.class);
                    intent.putExtra("id", relatedMovies.get(position).getId());
                    intent.putExtra("title", relatedMovies.get(position).getTitle());
                    context.startActivity(intent);
                }
            }
        });

        return rowView;
    }

}