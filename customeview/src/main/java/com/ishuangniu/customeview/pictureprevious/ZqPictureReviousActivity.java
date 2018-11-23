package com.ishuangniu.customeview.pictureprevious;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.ishuangniu.customeview.R;

import java.util.ArrayList;

/**
 * @author Smile
 */
public class ZqPictureReviousActivity extends AppCompatActivity {

    private static final float FLIP_DISTANCE = 50;
    public static final int DURATION_TIME = 300;
    private final static int MAX_DISTANCE = 333;
    public static boolean pictureViewScale = false;

    private ViewPager viewPager = null;
    private PictureAdapter adapter = null;
    private ImageButton ibtnClose = null;
    private ImageButton ibtnDownload = null;
    private TextView tvPage = null;
    private TextView tvDownload = null;
    private RelativeLayout rlRoot = null;

    private GestureDetector detector = null;
    private int rlRootAlpha255 = 255;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_zqpicture_revious);
        initViews();
        initData();
        initEvent();
    }


    private void initViews() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        ibtnClose = (ImageButton) findViewById(R.id.ibtn_close);
        ibtnDownload = (ImageButton) findViewById(R.id.ibtn_download);
        tvPage = (TextView) findViewById(R.id.tv_page);
        rlRoot = (RelativeLayout) findViewById(R.id.rl_root);
        tvDownload = (TextView) findViewById(R.id.tv_download);

    }

    private void initData() {

        ArrayList<ImgesPreviousTools.ImagePreviousData> imgs = (ArrayList<ImgesPreviousTools.ImagePreviousData>) getIntent().getSerializableExtra("imgs");
        adapter = new PictureAdapter(imgs);
        viewPager.setAdapter(adapter);
        tvPage.setText((viewPager.getCurrentItem() + 1) + "/" + adapter.getCount());
    }


    private void initEvent() {

        ibtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        ibtnDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tvDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImgDonwloads.getInstance().downLoadImg(
                        ZqPictureReviousActivity.this,
                        adapter.getImagePathes().get(viewPager.getCurrentItem()).imgUrl()
                );
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                pictureViewScale = false;
                tvPage.setText((viewPager.getCurrentItem() + 1) + "/" + adapter.getCount());
            }
        });

        adapter.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                showOrHideViews(ibtnClose.getVisibility() == View.VISIBLE);
            }
        });

        detector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {

                if (!pictureViewScale && Math.abs(e1.getY() - e2.getY()) > FLIP_DISTANCE) {
                    viewPager.setY(viewPager.getY() - distanceY);
                    float alpha = Math.abs(viewPager.getY()) / viewPager.getHeight();
                    rlRoot.setBackgroundColor(Color.argb(rlRootAlpha255 = (int) (255 - alpha * 255), 0, 0, 0));
                    showOrHideViews(true);
                    return true;
                }
                return false;
            }

        });

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            onUp(ev);
        }
        detector.onTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 显示或隐藏控件
     */
    private void showOrHideViews(boolean isHide) {
        if (isHide) {
            ibtnClose.setVisibility(View.INVISIBLE);
            ibtnDownload.setVisibility(View.GONE);
            tvPage.setVisibility(View.INVISIBLE);
            tvDownload.setVisibility(View.INVISIBLE);
        } else {
            ibtnClose.setVisibility(View.VISIBLE);
            ibtnDownload.setVisibility(View.GONE);
            tvPage.setVisibility(View.VISIBLE);
            tvDownload.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 当手指离开屏幕
     */
    private void onUp(MotionEvent ev) {
        if (viewPager.getY() > MAX_DISTANCE) {
            playDownOutAnimition();
        } else if (viewPager.getY() < -MAX_DISTANCE) {
            playUpOutAnimition();
        } else {
            playBackAnimition();
        }
    }

    /**
     * 播放viewpager归位动画
     */
    private void playBackAnimition() {
        ValueAnimator va1 = ValueAnimator.ofFloat(viewPager.getY(), 0L);
        va1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                viewPager.setY((Float) animation.getAnimatedValue());
            }
        });

        ValueAnimator va2 = ValueAnimator.ofInt(rlRootAlpha255, 255);
        va2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int alpha = (int) animation.getAnimatedValue();
                rlRoot.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(va1).with(va2);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.setDuration(DURATION_TIME);
        animatorSet.start();
    }

    /**
     * 向下移出
     */
    private void playDownOutAnimition() {
        ValueAnimator va1 = ValueAnimator.ofFloat(viewPager.getY(), viewPager.getHeight() + 30);
        va1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                viewPager.setY((Float) animation.getAnimatedValue());
            }
        });

        ValueAnimator va2 = ValueAnimator.ofInt(rlRootAlpha255, 0);
        va2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int alpha = (int) animation.getAnimatedValue();
                rlRoot.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(va1).with(va2);
        animatorSet.setDuration(DURATION_TIME);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                finish();
            }
        });
        animatorSet.start();
    }

    /**
     * 向上移出
     */
    private void playUpOutAnimition() {
        ValueAnimator va1 = ValueAnimator.ofFloat(viewPager.getY(), -viewPager.getHeight() - 30);
        va1.setInterpolator(new LinearInterpolator());
        va1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                viewPager.setY((Float) animation.getAnimatedValue());
            }
        });

        ValueAnimator va2 = ValueAnimator.ofInt(rlRootAlpha255, 0);
        va2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int alpha = (int) animation.getAnimatedValue();
                rlRoot.setBackgroundColor(Color.argb(alpha, 0, 0, 0));
            }
        });

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(va1).with(va2);
        animatorSet.setDuration(DURATION_TIME);
        animatorSet.addListener(new SimpleAnimatorListener() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                finish();
            }
        });
        animatorSet.start();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
