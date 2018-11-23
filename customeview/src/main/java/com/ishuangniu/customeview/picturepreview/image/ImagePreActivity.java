package com.ishuangniu.customeview.picturepreview.image;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ishuangniu.customeview.R;
import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Smile
 */
public class ImagePreActivity extends AppCompatActivity {

    private ArrayList<ImageSource> imageSources = null;
    private MyFragmentPagerAdapter adapter = null;
    private List<Fragment> fragments = null;
    private ImageLoader imageLoader = null;
    private FloatViewPager viewPager = null;
    private TextView tvTitle = null;
    private TextView tvDownload = null;
    private View rlRoot = null;

    public static void start(Context context, ArrayList<ImageSource> imageSources, ImageLoader imageLoader) {
        Intent starter = new Intent(context, ImagePreActivity.class);
        starter.putExtra("data", imageSources);
        starter.putExtra("loader", imageLoader);
        context.startActivity(starter);
        ((Activity) context).overridePendingTransition(0, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_pre);

        initView();
        initData();
        initEvent();
    }

    private void initView() {
        viewPager = findViewById(R.id.view_pager);
        tvDownload = findViewById(R.id.tv_download);
        tvTitle = findViewById(R.id.tv_page);
        rlRoot = findViewById(R.id.rl_root);
    }

    private void initEvent() {
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                tvTitle.setText((position + 1) + "/" + imageSources.size());
            }
        });

        viewPager.setPositionListener(new FloatViewPager.OnPositionChangeListener() {
            @Override
            public void onPositionChange(int initTop, int nowTop, float ratio) {
                rlRoot.setAlpha(Math.max(0, 1 - ratio));
            }

            @Override
            public void onFlingOutFinish() {
                finish();
                overridePendingTransition(0, 0);
            }
        });

        tvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoadImage();
            }
        });
    }

    private void downLoadImage() {
        AndPermission.with(this)
                .runtime()
                .permission(Permission.Group.STORAGE)
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> strings) {
                        Toast.makeText(ImagePreActivity.this, "请打开授权", Toast.LENGTH_SHORT).show();
                    }
                })
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> strings) {
                        ImgDonwloads.getInstance().downLoadImg(ImagePreActivity.this,
                                imageSources.get(viewPager.getCurrentItem()).imageUrl());
                    }
                })
                .start();
    }

    @SuppressWarnings("unchecked")
    private void initData() {
        imageSources = (ArrayList<ImageSource>) getIntent().getSerializableExtra("data");
        imageLoader = (ImageLoader) getIntent().getSerializableExtra("loader");
        fragments = new ArrayList<>(imageSources.size());
        for (int i = 0; i < imageSources.size(); i++) {
            fragments.add(ImagePrePicFragment.newInstance(imageSources.get(i), imageLoader));
        }
        adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
        tvTitle.setText("1" + "/" + imageSources.size());
    }
}
