package com.example.moviedb.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.model.movie.Trailers;
import com.squareup.picasso.Picasso;
import java.util.List;

public class AdapterForTrailerRecyclerView extends RecyclerView.Adapter<AdapterForTrailerRecyclerView.Holder> {

    private List<Trailers.Youtube> list;
    private LayoutInflater layoutInflater;
    private OnClickAdapterForTrailerRecyclerView onClickAdapterForTrailerRecyclerView;
    private Context context;

    public AdapterForTrailerRecyclerView(Context context, List<Trailers.Youtube> list) {
        this.list = list;
        this.context = context;
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public void setOnClickAdapterForTrailerRecyclerView(OnClickAdapterForTrailerRecyclerView onClickAdapterForTrailerRecyclerView) {
        this.onClickAdapterForTrailerRecyclerView = onClickAdapterForTrailerRecyclerView;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Holder(layoutInflater.inflate(R.layout.item_for_recycler_view_yotube, parent, false));
    }

    @Override
    public void onBindViewHolder(final Holder holder, final int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Picasso.with(context).load(Const.IMAGE_YOUTUBE_TRAILER_URL + list
                        .get(position).getSource() + "/hqdefault.jpg")
                        .resize(300, 240)
                        .into(holder.imageView);
            }
        });
        t.run();
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickAdapterForTrailerRecyclerView.onClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface OnClickAdapterForTrailerRecyclerView {
        void onClick(int position);
    }

    static class Holder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.image_view_for_item_recycler_view_youtube);
        }
    }
}

