package com.ishuangniu.customeview.picturepreview.image;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.ishuangniu.customeview.picturepreview.glidehelper.ProgressInterceptor;
import com.ishuangniu.customeview.picturepreview.glidehelper.ProgressListener;

public class ImageLoaderImpl implements ImageLoader {

    private static ImageLoaderImpl instance;

    public static ImageLoaderImpl getInstance() {

        if (instance == null) {
            synchronized (ImageLoaderImpl.class) {
                if (instance == null) {
                    instance = new ImageLoaderImpl();
                }
            }
        }

        return instance;
    }

    @Override
    public void loadImage(ImageView imageView, final ProgressBar progressBar, final ImageSource imageSource) {

        ProgressInterceptor.addListener(imageSource.imageUrl(), new ProgressListener() {
            @Override
            public void onProgress(int progress) {
                progressBar.setProgress(progress);
            }
        });

        Glide.with(imageView.getContext())
                .load(imageSource.imageUrl())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(new GlideDrawableImageViewTarget(imageView) {
                    @Override
                    public void onLoadStarted(Drawable placeholder) {
                        super.onLoadStarted(placeholder);
                        progressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                        super.onResourceReady(resource, animation);
                        progressBar.setVisibility(View.GONE);
                        ProgressInterceptor.removeListener(imageSource.imageUrl());
                    }

                });
    }

}
