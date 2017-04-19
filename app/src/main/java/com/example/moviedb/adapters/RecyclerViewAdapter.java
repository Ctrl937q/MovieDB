package com.example.moviedb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.moviedb.Const;
import com.example.moviedb.Converter.DateConverter;
import com.example.moviedb.R;
import com.example.moviedb.model.Movie;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.Holder> {

    private List<Movie> movies;
    private Context context;

    public RecyclerViewAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        Picasso.with(context).load(Const.IMAGE_POSTER_PATH_URL + movies
                .get(position).getPosterPath()).placeholder(R.drawable.placeholder_item_recycler_view)
                .resize(140, 170).centerCrop().into(holder.imageView);
        holder.textViewName.setText(movies.get(position).getTitle());

        holder.textViewYear.setText(DateConverter.formateDateFromstring("yyyy-MM-dd", "dd, MMMM, yyy",
                movies.get(position).getReleaseDate()));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewName;
        TextView textViewYear;

        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view_for_item);
            textViewName = (TextView) itemView.findViewById(R.id.item_text_view_name);
            textViewYear = (TextView) itemView.findViewById(R.id.item_text_view_year);
        }
    }
}
