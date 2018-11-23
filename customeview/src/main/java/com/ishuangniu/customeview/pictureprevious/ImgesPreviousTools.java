package com.ishuangniu.customeview.pictureprevious;

import android.content.Context;
import android.content.Intent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Smile
 * @date 2017/11/30
 */

public class ImgesPreviousTools {

    private ArrayList<ImagePreviousData> imgs = null;

    public static ImgesPreviousTools newInstance() {
        return new ImgesPreviousTools();
    }

    public void start(Context context) {
        if (null == imgs || imgs.size() == 0) {
            throw new NullPointerException("imgs不能为空");
        }
        Intent intent = new Intent(context, ZqPictureReviousActivity.class);
        intent.putExtra("imgs", imgs);
        context.startActivity(intent);
    }

    public ImgesPreviousTools setImgs(ArrayList<ImagePreviousData> imgs) {
        this.imgs = imgs;
        return this;
    }

    public List<ImagePreviousData> getImgs() {
        return imgs;
    }

    public interface ImagePreviousData extends Serializable {
        /**
         * @return 图片地址
         */
        String imgUrl();
    }

}
