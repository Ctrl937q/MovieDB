package com.example.moviedb.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.moviedb.R;
import com.example.moviedb.model.genre.Genre;
import java.util.List;

public class AdapterForListViewGenres extends BaseAdapter {

    Context context;
    List<Genre> castsList;
    LayoutInflater layoutInflater;

    public AdapterForListViewGenres(Context context, List<Genre> castsList) {
        this.context = context;
        this.castsList = castsList;
    }

    @Override
    public int getCount() {
        return castsList.size();
    }

    @Override
    public Object getItem(int position) {
        return castsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = convertView;
        TextView textViewGenre;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_for_recycler_view_genres, parent, false);
        }
        textViewGenre = (TextView) view.findViewById(R.id.textView_genres);

        textViewGenre.setText("" + castsList.get(position).getName());
        return view;
    }
}