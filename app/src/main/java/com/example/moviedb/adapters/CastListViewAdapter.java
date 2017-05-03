package com.example.moviedb.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.model.Cast;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

public class CastListViewAdapter extends BaseAdapter {

    Context context;
    List<Cast> castsList;
    LayoutInflater layoutInflater;

    public CastListViewAdapter(Context context, List<Cast> castsList) {
        layoutInflater = LayoutInflater.from(context);
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
        View view = convertView;
        TextView textViewCastName;
        TextView textViewCastCharacter;
        ImageView imageView_cast;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_for_list_view_cast, parent, false);
        }
        textViewCastName = (TextView) view.findViewById(R.id.text_view_for_item_list_view_name);
        textViewCastCharacter = (TextView) view.findViewById(R.id.text_view_for_item_list_view_CastCharacter);
        imageView_cast = (ImageView) view.findViewById(R.id.image_view_for_item_list_view_cast);

        Transformation transformation = new RoundedTransformationBuilder()
                .build();

        if (castsList.get(position).getProfilePath() == null) {
            Picasso.with(context)
                    .load(R.drawable.no_image_available)
                    .resize(120, 130)
                    .into(imageView_cast);

        } else {
            Picasso.with(context)
                    .load(Const.IMAGE_POSTER_PATH_URL + castsList
                    .get(position).getProfilePath()).placeholder(R.drawable.placeholder_item_recycler_view)
                    .centerCrop()
                    .resize(120, 130)
                    .transform(transformation)
                    .into(imageView_cast);
        }

        textViewCastName.setText(castsList.get(position).getName());
        textViewCastCharacter.setText(castsList.get(position).getCharacter());
        return view;
    }
}
