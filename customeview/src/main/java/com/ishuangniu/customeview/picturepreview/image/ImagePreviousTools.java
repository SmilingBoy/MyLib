package com.ishuangniu.customeview.picturepreview.image;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Smile
 */
public class ImagePreviousTools {

    private ImageLoader imageLoader = null;
    private Context context = null;
    private ArrayList<ImageSource> arrayList = new ArrayList<>();

    public ImagePreviousTools(Context context) {
        this.context = context;
    }

    public static ImagePreviousTools with(Context context) {
        return new ImagePreviousTools(context);
    }

    public ImagePreviousTools setArrayList(List<ImageSource> arrayList) {
        this.arrayList.addAll(arrayList);
        return this;
    }

    public ImagePreviousTools setArrayList(String[] arrayList) {

        for (String anArrayList : arrayList) {
            this.arrayList.add(new ImageSourceImpl(anArrayList));
        }

        return this;
    }

    public ImagePreviousTools setArrayList(String imageUrl) {
        this.arrayList.add(new ImageSourceImpl(imageUrl));
        return this;
    }


    public ImagePreviousTools setImageLoader(ImageLoader imageLoader) {
        this.imageLoader = imageLoader;
        return this;
    }

    public void show() {
        ImagePreActivity.start(context, arrayList, imageLoader);
    }

}