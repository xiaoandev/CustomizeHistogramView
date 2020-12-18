package com.example.customizehistogramview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.customizehistogramview.view.HistogramView;

public class MainActivity extends AppCompatActivity {

    private HistogramView histogramView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         histogramView = (HistogramView) findViewById(R.id.line_chat_one);

    }
}