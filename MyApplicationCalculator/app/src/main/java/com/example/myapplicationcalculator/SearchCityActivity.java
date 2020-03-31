package com.example.myapplicationcalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.SearchView;

public class SearchCityActivity extends AppCompatActivity {

    SearchView searchView;
    Button btnok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_search_city);

        searchView = findViewById(R.id.search);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                System.out.println(s+"  submit");
                Intent intent = new Intent();
                intent.setClass(SearchCityActivity.this, WeatherActivity.class);
                intent.putExtra("data", s);
                startActivity(intent);

                finish();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                System.out.println(s+"  change");
                return false;
            }
        });
        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence search = searchView.getQuery();
                String stringsearch = search.toString();
                System.out.println(stringsearch);
            }
        });




    }

}
