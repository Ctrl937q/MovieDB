package com.example.moviedb.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.converter.DateConverter;
import com.example.moviedb.model.Similar;
import com.squareup.picasso.Picasso;
import java.util.List;

public class RelatedMoviesAdapter extends RecyclerView.Adapter<RelatedMoviesAdapter.Holder> {

    List<Similar.Result> relatedMovies;
    private Context context;
    LayoutInflater layoutInflater;

    public RelatedMoviesAdapter(Context context, List<Similar.Result> relatedMovies) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.relatedMovies = relatedMovies;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(layoutInflater.inflate(R.layout.item_card_for_related_movies, parent, false));
    }

    @Override
    public void onBindViewHolder(final RelatedMoviesAdapter.Holder holder, int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        try {
            Picasso.with(context).load(Const.IMAGE_POSTER_PATH_URL + relatedMovies
                    .get(position).getPosterPath()).placeholder(R.drawable.placeholder_item_recycler_view)
                    .resize(350, 550)
                    .into(holder.imageView);
            holder.textViewName.setText(relatedMovies.get(position).getTitle());
            holder.textViewYear.setText(DateConverter.formateDateFromstring("yyyy-MM-dd", "yyyy",
                    relatedMovies.get(position).getReleaseDate()));
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    static class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textViewName;
        TextView textViewYear;

        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view_for_card_view_related_movies);
            textViewName = (TextView) itemView.findViewById(R.id.text_view_title_for_card_view_related_movies);
            textViewYear = (TextView) itemView.findViewById(R.id.text_view_year_for_card_view_realted_movies);
        }
    }
}