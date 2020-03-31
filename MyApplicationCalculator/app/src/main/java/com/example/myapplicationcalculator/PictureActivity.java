package com.example.myapplicationcalculator;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AnimationUtils;

import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher;

import androidx.appcompat.app.AppCompatActivity;

public class PictureActivity extends AppCompatActivity implements ViewSwitcher.ViewFactory, View.OnTouchListener {
    private int[] imageId = new int[]{
            R.raw.t01, R.raw.t02, R.raw.t03, R.raw.t04, R.raw.t05, R.raw.t06, R.raw.t07, R.raw.t08, R.raw.t09, R.raw.t10,
    };
    private ImageSwitcher imgSwitcher;
    private int pictureIndex;
    private float touchDownX;
    private float touchUpX;


    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_picture);

        imgSwitcher = findViewById(R.id.is);//获取图像切换器
        //设置淡入动画效果
        imgSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
        //设置淡出动画效果
        imgSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
        imgSwitcher.setFactory(this);
        imgSwitcher.setOnTouchListener(this);

    }

    public View makeView() {
        ImageView imageView = new ImageView(this);
        imageView.setImageResource(imageId[pictureIndex]);
        return imageView;
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            // 取得左右滑动时手指按下的X坐标
            touchDownX = event.getX();
            return true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            // 取得左右滑动时手指松开的X坐标
            touchUpX = event.getX();
            // 从左往右，看前一张
            if (touchUpX - touchDownX > 100) {
                // 取得当前要看的图片的index
                pictureIndex = pictureIndex == 0 ? imageId.length - 1
                        : pictureIndex - 1;
                // 设置图片切换的动画
//                imgSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
//                        android.R.anim.slide_in_left));
//                imgSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
//                        android.R.anim.slide_out_right));
                // 设置当前要看的图片
                imgSwitcher.setImageResource(imageId[pictureIndex]);
                // 从右往左，看下一张
            } else if (touchDownX - touchUpX > 100) {
                // 取得当前要看的图片的index
                pictureIndex = pictureIndex == imageId.length - 1 ? 0
                        : pictureIndex + 1;
                // 设置图片切换的动画
                // 由于Android没有提供slide_out_left和slide_in_right，所以仿照slide_in_left和slide_out_right
                // 编写了slide_out_left和slide_in_right

                // 设置当前要看的图片
//                if(pictureIndex==)
                 imgSwitcher.setImageResource(imageId[pictureIndex]);
            }
            return true;
        }
        return false;
    }

}