package com.example.customizehistogramview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

import com.example.customizehistogramview.bean.DataPoint;
import com.example.customizehistogramview.view.HistogramView;
import com.example.customizehistogramview.view.PolyLineView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity implements View.OnClickListener {

    private PolyLineView polyLineView;
    private List<DataPoint> lineData;
    private List<String> dataTextX;
    private List<String> dataTextY;
    private Button clearChartBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        initView();
        initData();
    }

    private void initView() {
        polyLineView = (PolyLineView) findViewById(R.id.poly_line_view);
        clearChartBtn = (Button) findViewById(R.id.clear_chart);

        clearChartBtn.setOnClickListener(this);
    }

    private void initData() {
        //设置相邻的刻度差值
        polyLineView.setValueMinus(1, 10);

        //使用方式一绘制坐标轴刻度
//        dataTextX = new ArrayList<>();
//        String[] textX = {"1", "2", "3", "4", "5", "6"};
//        for (int i = 0; i < textX.length; i++)
//            dataTextX.add(textX[i]);
//        dataTextY = new ArrayList<>();
//        String[] textY = {"10", "20", "30", "40", "50", "60"};
//        for (int i = 0; i < textY.length; i++)
//            dataTextY.add(textY[i]);
//        polyLineView.setOrdinaryAxisData(dataTextX, dataTextY);

        //使用方式二绘制坐标轴刻度
        polyLineView.setDigitalAxisData(1, 6, 10, 6);

        polyLineView.setTopLineDataColor(Color.RED);
//        polyLineView.setOriginAxis(400, 300);
        polyLineView.setLineXYColor(Color.RED);
        polyLineView.setDrawSmallTick(false);

        lineData = new ArrayList<>();
        lineData.add(new DataPoint(1, 10));
        lineData.add(new DataPoint(2, 50));
        lineData.add(new DataPoint(3, 35));
        lineData.add(new DataPoint(4, 25));
        lineData.add(new DataPoint(5, 10));
        lineData.add(new DataPoint(6, 60));
        polyLineView.setLineData(lineData);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.clear_chart:
                polyLineView.clearChart(true);
                break;
            default:
                break;
        }
    }
}