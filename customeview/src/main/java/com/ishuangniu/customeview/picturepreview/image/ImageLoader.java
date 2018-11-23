package com.ishuangniu.customeview.picturepreview.image;

import android.widget.ImageView;
import android.widget.ProgressBar;

import java.io.Serializable;

public interface ImageLoader extends Serializable {
    void loadImage(ImageView imageView, ProgressBar progressBar, ImageSource imageSource);
}
