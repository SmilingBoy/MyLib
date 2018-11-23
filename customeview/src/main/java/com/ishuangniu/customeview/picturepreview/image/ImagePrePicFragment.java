package com.ishuangniu.customeview.picturepreview.image;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ishuangniu.customeview.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class ImagePrePicFragment extends Fragment {

    private PinchImageView pinchImageView = null;
    private CircleProgressView circleProgressView = null;

    public static ImagePrePicFragment newInstance(ImageSource imageSource, ImageLoader imageLoader) {

        Bundle args = new Bundle();
        args.putSerializable("data", imageSource);
        args.putSerializable("loader", imageLoader);
        ImagePrePicFragment fragment = new ImagePrePicFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser && pinchImageView != null) {
            pinchImageView.reset();
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image_pre, container, false);
        pinchImageView = view.findViewById(R.id.iv_pic);
        circleProgressView = view.findViewById(R.id.progressView1);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getArguments() != null) {
            ImageSource imageSource = (ImageSource) getArguments().getSerializable("data");
            ImageLoader imageLoader = (ImageLoader) getArguments().getSerializable("loader");
            if (imageLoader != null) {
                imageLoader.loadImage(pinchImageView, circleProgressView, imageSource);
            }
        }
        initEvent();
    }

    private void initEvent() {
        pinchImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
                getActivity().overridePendingTransition(0, 0);
            }
        });
    }
}
