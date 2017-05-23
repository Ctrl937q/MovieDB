package com.example.moviedb.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.model.tv.details.CastTV;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.List;

public class CastListViewTVShowAdapter extends BaseAdapter {

    Context context;
    List<CastTV> castsList;
    LayoutInflater layoutInflater;
    ImageLoader imageLoader;
    private final int CacheSize = 52428800;
    private final int MinFreeSpace = 2048;

    public CastListViewTVShowAdapter(Context context, List<CastTV> castsList) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        layoutInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = convertView;
        TextView textViewCastName;
        TextView textViewCastCharacter;
        final ImageView imageView_cast;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_for_list_view_cast, parent, false);
        }
        textViewCastName = (TextView) view.findViewById(R.id.text_view_for_item_list_view_name);
        textViewCastCharacter = (TextView) view.findViewById(R.id.text_view_for_item_list_view_CastCharacter);
        imageView_cast = (ImageView) view.findViewById(R.id.image_view_for_item_list_view_cast);

        File cacheDir = StorageUtils.getCacheDirectory(context);
        long size = 0;
        File[] filesCache = cacheDir.listFiles();
        for (File file : filesCache) {
            size += file.length();
        }
        if (cacheDir.getUsableSpace() < MinFreeSpace || size > CacheSize) {
            ImageLoader.getInstance().getDiskCache().clear();
        }

        if (castsList.get(position).getProfilePath() == null) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(context).load(R.drawable.no_image_available)
                            .asBitmap().centerCrop()
                            .override(80, 80)
                            .into(new BitmapImageViewTarget(imageView_cast) {
                                @Override
                                protected void setResource(Bitmap resource) {
                                    RoundedBitmapDrawable circularBitmapDrawable =
                                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                                    circularBitmapDrawable.setCircular(true);
                                    imageView_cast.setImageDrawable(circularBitmapDrawable);
                                }
                            });
                }
            });
            t.run();
        } else {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    Glide.with(context).load(Const.IMAGE_POSTER_PATH_URL + castsList
                            .get(position).getProfilePath())
                            .asBitmap().centerCrop()
                            .override(80, 80)
                            .into(new BitmapImageViewTarget(imageView_cast) {
                                @Override
                                protected void setResource(Bitmap resource) {
                                    RoundedBitmapDrawable circularBitmapDrawable =
                                            RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                                    circularBitmapDrawable.setCircular(true);
                                    imageView_cast.setImageDrawable(circularBitmapDrawable);
                                }
                            });
                }
            });
            t.run();
        }
        textViewCastName.setText(castsList.get(position).getName());
        textViewCastCharacter.setText(castsList.get(position).getCharacter());
        return view;
    }
}
