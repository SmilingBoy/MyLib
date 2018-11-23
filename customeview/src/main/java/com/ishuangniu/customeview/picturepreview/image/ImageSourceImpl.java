package com.ishuangniu.customeview.picturepreview.image;

/**
 * Created by Smile on 2018/4/29.
 */

public class ImageSourceImpl implements ImageSource {

    private String url;

    public ImageSourceImpl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String imageUrl() {
        return null == getUrl() ? "" : getUrl();
    }
}
