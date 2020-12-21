package com.example.customizehistogramview;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.customizehistogramview.bean.Histogram;
import com.example.customizehistogramview.view.HistogramView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private HistogramView histogramView;
    private List<Histogram> topLineData;
    private List<Histogram> bottomLineData;

//    private String[] dataTextX = {"1", "2", "3", "4", "5"};
//    private String[] dataTextY = {"10", "20", "30", "40"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        histogramView = (HistogramView) findViewById(R.id.line_chat_one);
        histogramView.setDigitalAxisData(1, 8, 10, 6);
        topLineData = new ArrayList<>();
        topLineData.add(new Histogram(1, 10));
        topLineData.add(new Histogram(2, 20));
        topLineData.add(new Histogram(3, 30));
        topLineData.add(new Histogram(4, 40));
        topLineData.add(new Histogram(5, 50));
        topLineData.add(new Histogram(6, 60));
        topLineData.add(new Histogram(7, 30));
        topLineData.add(new Histogram(8, 40));
        histogramView.setTopLineData(topLineData);
        bottomLineData = new ArrayList<>();
        bottomLineData.add(new Histogram(1, 10));
        bottomLineData.add(new Histogram(2, 20));
        bottomLineData.add(new Histogram(3, 30));
        bottomLineData.add(new Histogram(4, 40));
        bottomLineData.add(new Histogram(5, 50));
        bottomLineData.add(new Histogram(6, 60));
        bottomLineData.add(new Histogram(7, 20));
        bottomLineData.add(new Histogram(8, 30));
        histogramView.setBottomLineData(bottomLineData);

    }
}