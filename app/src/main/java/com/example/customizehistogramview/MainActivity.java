package com.example.customizehistogramview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.example.customizehistogramview.bean.Histogram;
import com.example.customizehistogramview.view.HistogramView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private HistogramView histogramView;
    private List<Histogram> topLineData;
    private List<Histogram> bottomLineData;
    private List<String> dataTextX;
    private List<String> dataTextY;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        histogramView = (HistogramView) findViewById(R.id.line_chat_one);
        initData();

    }

    private void initData() {
        //设置相邻的刻度差值
        histogramView.setValueMinus(1, 10);

        //使用方式一绘制坐标轴刻度
//        dataTextX = new ArrayList<>();
//        String[] textX = {"1", "2", "3", "4", "5", "6"};
//        for (int i = 0; i < textX.length; i++)
//            dataTextX.add(textX[i]);
//        dataTextY = new ArrayList<>();
//        String[] textY = {"10", "20", "30", "40", "50", "60"};
//        for (int i = 0; i < textY.length; i++)
//            dataTextY.add(textY[i]);
//        histogramView.setOrdinaryAxisData(dataTextX, dataTextY);

        //使用方式二绘制坐标轴刻度
        histogramView.setDigitalAxisData(1, 6, 10, 6);

        histogramView.setTopLineDataColor(Color.RED);
        histogramView.setBottomLineDataColor(Color.BLUE);
        histogramView.setOriginAxis(400, 300);
        histogramView.setLineXYColor(Color.RED);

        topLineData = new ArrayList<>();
        topLineData.add(new Histogram(1, 10));
        topLineData.add(new Histogram(2, 50));
        topLineData.add(new Histogram(3, 35));
        topLineData.add(new Histogram(4, 25));
        topLineData.add(new Histogram(5, 0));
        topLineData.add(new Histogram(6, 60));
        histogramView.setTopLineData(topLineData);
        bottomLineData = new ArrayList<>();
        bottomLineData.add(new Histogram(1, 20));
        bottomLineData.add(new Histogram(2, 60));
        bottomLineData.add(new Histogram(3, 45));
        bottomLineData.add(new Histogram(4, 35));
        bottomLineData.add(new Histogram(5, 10));
        bottomLineData.add(new Histogram(6, 70));
        histogramView.setBottomLineData(bottomLineData);
    }
}