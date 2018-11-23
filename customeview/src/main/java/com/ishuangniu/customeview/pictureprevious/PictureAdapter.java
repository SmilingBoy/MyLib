package com.ishuangniu.customeview.pictureprevious;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.OnScaleChangedListener;
import com.github.chrisbanes.photoview.PhotoView;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Smile
 * @date 2017/11/13
 */

public class PictureAdapter extends PagerAdapter {

    private List<ImgesPreviousTools.ImagePreviousData> imagePathes = new ArrayList<>();
    private OnPhotoTapListener onPhotoTapListener = null;

    public PictureAdapter(List<ImgesPreviousTools.ImagePreviousData> imagePathes) {
        this.imagePathes = imagePathes;
    }

    public void setOnPhotoTapListener(OnPhotoTapListener onPhotoTapListener) {
        this.onPhotoTapListener = onPhotoTapListener;
    }

    public List<ImgesPreviousTools.ImagePreviousData> getImagePathes() {
        return imagePathes;
    }

    @Override
    public int getCount() {
        return imagePathes.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        final PhotoView photoView = new PhotoView(container.getContext());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        photoView.setLayoutParams(layoutParams);
        Glide.with(container.getContext()).load(imagePathes.get(position).imgUrl()).into(photoView);
        container.addView(photoView);
        if (onPhotoTapListener != null) {
            photoView.setOnPhotoTapListener(onPhotoTapListener);
        }
        photoView.setOnScaleChangeListener(new OnScaleChangedListener() {
            @Override
            public void onScaleChange(float scaleFactor, float focusX, float focusY) {
                if (Math.abs(photoView.getScale() - 1.0) <= 0.02) {
                    ZqPictureReviousActivity.pictureViewScale = false;
                } else {
                    ZqPictureReviousActivity.pictureViewScale = true;
                }
            }
        });
        return photoView;
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
