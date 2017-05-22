package com.example.moviedb.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CoordinatorLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.view.View.OnClickListener;

import com.example.moviedb.activity.ActivityDetails;
import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.converter.DateConverter;
import com.example.moviedb.model.movie.CombinedCredits;
import com.squareup.picasso.Picasso;
import java.util.List;

public class GridViewCastDetailsAdapter extends BaseAdapter {

    List<CombinedCredits.Cast> combineCreditsList;
    private Context context;
    LayoutInflater layoutInflater;
    //GridView gridView;
    //ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();


    public GridViewCastDetailsAdapter(Context context, List<CombinedCredits.Cast> combineCreditsList) {
        this.context = context;
        this.combineCreditsList = combineCreditsList;
        //this.gridView = gridView;
    }

    @Override
    public int getCount() {
        if (combineCreditsList.size() >= 6) {
            return 6;
        } else {
           /* gridView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));*/
            return combineCreditsList.size();
        }
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
        final Holder holder = new Holder();
        final View rowView;
        rowView = layoutInflater.inflate(R.layout.item_card_for_related_movies, null);
        holder.textViewName = (TextView) rowView.findViewById(R.id.text_view_title_for_card_view_related_movies);
        holder.textViewYear = (TextView) rowView.findViewById(R.id.text_view_year_for_card_view_realted_movies);
        holder.imageView = (ImageView) rowView.findViewById(R.id.image_view_for_card_view_related_movies);
        holder.textViewName.setEllipsize(TextUtils.TruncateAt.END);
        holder.textViewName.setMaxLines(2);

        if(combineCreditsList.get(position).getOriginalName() != null){
            holder.textViewName.setText(combineCreditsList.get(position).getOriginalName());
        }else {
            holder.textViewName.setText(combineCreditsList.get(position).getTitle());
        }

        if (combineCreditsList.get(position).getFirstAirDate() != null) {
            holder.textViewYear.setText(DateConverter.formateDateFromstring("yyyy-MM-dd", "yyyy",
                    combineCreditsList.get(position).getFirstAirDate()));
        } else {

            holder.textViewYear.setText(DateConverter.formateDateFromstring("yyyy-MM-dd", "yyyy",
                    combineCreditsList.get(position).getReleaseDate()));
        }
        Picasso.with(context).load(Const.IMAGE_POSTER_PATH_URL + combineCreditsList
                .get(position).getPosterPath()).placeholder(R.drawable.placeholder_item_recycler_view)
                .resize(350, 550)
                .into(holder.imageView);

        rowView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == rowView.getId()) {
                    Intent intent = new Intent(context, ActivityDetails.class);
                    intent.putExtra("id", combineCreditsList.get(position).getId());
                    intent.putExtra("title", combineCreditsList.get(position).getTitle());
                    context.startActivity(intent);
                }
            }
        });

        return rowView;
    }
}