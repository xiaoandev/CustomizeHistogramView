package com.example.customizehistogramview;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.customizehistogramview.bean.DataPoint;
import com.example.customizehistogramview.view.HistogramView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private final static String TAG = MainActivity.class.getSimpleName();

    private HistogramView histogramView;
    private List<DataPoint> topLineData;
    private List<DataPoint> bottomLineData;
    private List<String> dataTextX;
    private List<String> dataTextY;
    private Button sendTopDataBtn;
    private Button sendBottomDataBtn;
    private int numberX;
    private int numberY;
    private int numberPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

        histogramView.setReplaceTopLineByBitmap(false);
        histogramView.setReplaceBottomLineByBitmap(true);
        histogramView.setBottomLineBitmap(R.drawable.ic_background);

        for (int i = 0; i < 6; i++)
            topLineData.add(new DataPoint(50));
        histogramView.setTopLineData(topLineData);


        bottomLineData.add(new DataPoint(1, 20));
        bottomLineData.add(new DataPoint(2, 20));
        bottomLineData.add(new DataPoint(3, 25));
        bottomLineData.add(new DataPoint(4, 35));
        bottomLineData.add(new DataPoint(5, 10));
        bottomLineData.add(new DataPoint(6, 60));
        histogramView.setBottomLineData(bottomLineData);
    }

    private void initView() {
        histogramView = (HistogramView) findViewById(R.id.histogram_view);
        sendTopDataBtn = (Button) findViewById(R.id.send_top_data);
        sendBottomDataBtn = (Button) findViewById(R.id.send_bottom_data);

        sendTopDataBtn.setOnClickListener(this);
        sendBottomDataBtn.setOnClickListener(this);
    }

    private void initData() {
        topLineData = new ArrayList<>();
        bottomLineData = new ArrayList<>();
        numberX = 6;
        numberY = 6;
        numberPoint = 0;

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
        histogramView.setDigitalAxisData(1, numberX, 10, numberY);

        histogramView.setTopLineDataColor(Color.RED);
        histogramView.setBottomLineDataColor(Color.BLUE);
        histogramView.setOriginAxis(400, 300);
        histogramView.setLineXYColor(Color.RED);

    }

    /**
     * 生成 n ~ m 之间的随机数
     * @param n
     * @param m
     */
    private int getRandomData(int n, int m) {

        int currentData = (int)(Math.random() * (m-n+1) + m);
        return currentData;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_top_data:
                List<DataPoint> addTopLineData = new ArrayList<>();
//                addTopLineData.add(new DataPoint(getRandomData(10, 20)));
                addTopLineData.add(new DataPoint(20));
                histogramView.addTopLineData(addTopLineData);
                Log.d(TAG, "Top Data Add -----------------");
                break;
            case R.id.send_bottom_data:
                List<DataPoint> addBottomLineData = new ArrayList<>();
                addBottomLineData.add(new DataPoint(getRandomData(10, 30)));
                histogramView.addBottomLineData(addBottomLineData);
                break;
            default:
                break;
        }
    }
}