package com.example.moviedb.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.moviedb.Const;
import com.example.moviedb.R;
import com.example.moviedb.converter.CircleTransform;
import com.example.moviedb.model.Cast;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.util.List;

public class CastListViewAdapter extends BaseAdapter {

    Context context;
    List<Cast> castsList;
    LayoutInflater layoutInflater;
    ImageLoader imageLoader;
    private final int CacheSize = 52428800; // 50MB
    private final int MinFreeSpace = 2048; // 2MB

    DisplayImageOptions options = new DisplayImageOptions.Builder()
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.EXACTLY)
            .cacheInMemory(false)
            .cacheOnDisk(true)
            .build();

    public CastListViewAdapter(Context context, List<Cast> castsList) {
        this.context = context;
        this.castsList = castsList;
        layoutInflater = LayoutInflater.from(context);
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
        final ImageView imageView_cast;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.item_for_list_view_cast, parent, false);
        }
        textViewCastName = (TextView) view.findViewById(R.id.text_view_for_item_list_view_name);
        textViewCastCharacter = (TextView) view.findViewById(R.id.text_view_for_item_list_view_CastCharacter);
        imageView_cast = (ImageView) view.findViewById(R.id.image_view_for_item_list_view_cast);

        File cacheDir = StorageUtils.getCacheDirectory(context);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .defaultDisplayImageOptions(options)
                .build();
        ImageLoader.getInstance().init(config);
        imageLoader = ImageLoader.getInstance();
        long size = 0;
        File[] filesCache = cacheDir.listFiles();
        for (File file : filesCache) {
            size += file.length();
        }
        if (cacheDir.getUsableSpace() < MinFreeSpace || size > CacheSize) {
            ImageLoader.getInstance().getDiskCache().clear();
        }

        if (castsList.get(position).getProfilePath() == null) {
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
        } else {
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

        textViewCastName.setText(castsList.get(position).getName());
        textViewCastCharacter.setText(castsList.get(position).getCharacter());
        return view;
    }
}
