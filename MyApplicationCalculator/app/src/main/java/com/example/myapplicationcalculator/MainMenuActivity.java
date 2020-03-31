package com.example.myapplicationcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton imageCalucator,imagePicture,imageWeather;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main_menu);
        imageCalucator = findViewById(R.id.imageCalculator);
        imageWeather = findViewById(R.id.image_Weather);
        imagePicture = findViewById(R.id.imagePicture);

        imageCalucator.setOnClickListener(this);
        imageWeather.setOnClickListener(this);
        imagePicture.setOnClickListener(this);

    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.imageCalculator:
                intent = new Intent();
                intent.setClass(MainMenuActivity.this,CalculateActivity.class);
                startActivity(intent);
                break;
            case R.id.image_Weather:
                intent = new Intent();
                intent.setClass(MainMenuActivity.this,WeatherActivity.class);
                startActivity(intent);


                break;
            case R.id.imagePicture:
                intent = new Intent();
                intent.setClass(MainMenuActivity.this,PictureActivity.class);
                startActivity(intent);
                break;
        }
    }
}
