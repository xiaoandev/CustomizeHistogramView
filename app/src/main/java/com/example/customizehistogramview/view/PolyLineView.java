package com.example.customizehistogramview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.customizehistogramview.R;
import com.example.customizehistogramview.bean.DataPoint;

import java.util.ArrayList;
import java.util.List;

public class PolyLineView extends View {

    private static final String TAG = HistogramView.class.getSimpleName();
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 坐标轴原点 X 的默认值
     */
    private final static float DEFAULT_CENTER_X = 200;
    /**
     * 坐标轴原点 Y 的默认值
     */
    private final static float DEFAULT_CENTER_Y = 500;
    /**
     * 柱状图默认颜色
     */
    private final static int DEFAULT_LINE_COLOR = Color.GREEN;
    /**
     * 折线图默认宽度
     */
    private final static int DEFAULT_LINE_WIDTH = 2;
    /**
     * X 轴两刻度之间距离的默认值
     */
    private final static float DEFAULT_INTERVAL_DATA_X = 60;
    /**
     * Y 轴两刻度之间距离的默认值
     */
    private final static float DEFAULT_INTERVAL_DATA_Y = 60;
    /**
     * XY轴画笔的默认颜色
     */
    private final static int DEFAULT_LINE_XY_COLOR = Color.BLACK;
    /**
     * XY轴画笔宽度默认值
     */
    private final static float DEFAULT_LINE_XY_SIZE = 2;
    /**
     * 坐标轴原点 X 坐标
     */
    private float mCenterX;
    /**
     * 坐标轴原点 Y 坐标
     */
    private float mCenterY;
    /**
     * X 轴长度
     */
    private float mLengthX = 300;
    /**
     * Y 轴长度
     */
    private float mLengthY = 300;
    /**
     * X轴底部文字
     */
    private List<String> mDataTextX;
    /**
     * Y轴左侧文字
     */
    private List<String> mDataTextY;
    /**
     * X轴底部文字与X轴的间距
     */
    private float marginDataX = 10;
    /**
     * X轴底部文字的高度（只考虑单行文字情况）
     */
    private float textHeightX = 0;
    /**
     * Y轴底部文字与Y轴的间距
     */
    private float marginDataY = 25;
    /**
     * XY轴画笔
     */
    private Paint mPaintTextXY;
    /**
     * XY 轴画笔颜色
     */
    private int mLineXYColor;
    /**
     * XY 轴画笔宽度
     */
    private float mLineXYSize;
    /**
     * 折线图画笔
     */
    private Paint mPaintLineData;
    /**
     * 圆点画笔
     */
    private Paint mPointPaint;
    /**
     * 折线图宽度
     */
    private float mLineWidth;
    /**
     * 折线图的颜色
     */
    private int mLineDataColor;
    /**
     * 坐标原点处的值
     */
    private String mCenterValue = "0";
    /**
     * X轴每个刻度之间的距离
     */
    private float mIntervalDataX;
    /**
     * Y轴每个刻度之间的距离
     */
    private float mIntervalDataY;
    /**
     * X 轴的单位长度
     */
    private float mScaleX;
    /**
     * Y 轴的单位长度
     */
    private float mScaleY;
    /**
     * X 轴相邻两刻度之间的差值
     */
    private int mValueMinusX = 1;
    /**
     * Y 轴相邻两刻度之间的差值
     */
    private int mValueMinusY = 10;
    /**
     * 折线图数据
     */
    private List<DataPoint> mLineData;
    /**
     * Y轴大刻度线宽度
     */
    private float mBigTickMarkWidth = 10;
    /**
     * Y轴小刻度线宽度
     */
    private float mSmallTickMarkWidth = 5;
    /**
     * Y轴每个一个大刻度均分为几个小刻度的数量
     */
    private int mBigPartNum = 5;
    /**
     * 判断是否根据坐标轴数据量自动设置宽和高
     */
    private boolean mIsAutoSetAxis = true;
    /**
     * X轴底部文字的颜色
     */
    private int mTextColorX = Color.BLACK;
    /**
     * X轴底部文字的大小
     */
    private float mTextSizeX = 20;
    /**
     * Y轴左侧文字的颜色
     */
    private int mTextColorY = Color.BLACK;
    /**
     * Y轴左侧文字的大小
     */
    private float mTextSizeY = 20;
    /**
     * 数值平均线的颜色
     */
    private int mAverageValueColor = Color.RED;
    /**
     * 数值平均线的宽度
     */
    private float mAverageValueSize = 2;
    /**
     * 虚线中小实线的长度
     */
    private float mDottedLineWidth = 5;
    /**
     * 设置平均线的显示方式：1、实线；2、虚线。
     */
    private boolean isShowDottedLine = true;
    /**
     * 圆点画笔颜色
     */
    private int mPointColor = Color.BLACK;
    /**
     * 圆点画笔宽度
     */
    private float mPointWidth = 6;
    /**
     * 圆点半径
     */
    private float mPointRadius = 3;
    /**
     * 网格线颜色
     */
    private int mGridLineColor = Color.GRAY;
    /**
     * 网格线宽度
     */
    private float mGridLineWidth = 1;
    /**
     * 判断是否显示网格线
     */
    private boolean isShowGridLine = true;
    /**
     * 判断是否绘制Y轴的小刻度线
     */
    private boolean isDrawSmallTick = true;

    public PolyLineView(Context context) {
        super(context);
        init();
    }

    public PolyLineView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public PolyLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HistogramView);
        mCenterX = array.getDimension(R.styleable.HistogramView_center_x, dip2px(mContext, DEFAULT_CENTER_X));
        mCenterY = array.getDimension(R.styleable.HistogramView_center_y, dip2px(mContext, DEFAULT_CENTER_Y));
        mLineDataColor = array.getInteger(R.styleable.HistogramView_lineDataColor, DEFAULT_LINE_COLOR);
        mIntervalDataX = array.getFloat(R.styleable.HistogramView_intervalDataX, DEFAULT_INTERVAL_DATA_X);
        mIntervalDataY = array.getFloat(R.styleable.HistogramView_intervalDataY, DEFAULT_INTERVAL_DATA_Y);
        mLineWidth = array.getFloat(R.styleable.HistogramView_lineWidth, DEFAULT_LINE_WIDTH);
        mLineXYColor = array.getInteger(R.styleable.HistogramView_lineXYColor, DEFAULT_LINE_XY_COLOR);
        mLineXYSize = array.getFloat(R.styleable.HistogramView_lineXYSize,DEFAULT_LINE_XY_SIZE);
        array.recycle();
        init();
    }


    /**
     * 初始画笔和数值
     */
    private void init() {

        this.mDataTextX = new ArrayList<>();
        this.mDataTextY = new ArrayList<>();
        this.mLineData = new ArrayList<>();

        //XY轴
        mPaintTextXY = new Paint();
        mPaintTextXY.setAntiAlias(true);//抗锯齿

        //折线图
        mPaintLineData = new Paint();
        mPaintLineData.setAntiAlias(true);

        //圆点
        mPointPaint = new Paint();
        mPointPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mDataTextX != null && mDataTextY != null) {
            //绘制X坐标值
            drawDataLineX(canvas);
            //绘制Y坐标值
            drawDataLineY(canvas);
        }

        //判断是否根据录入的数据设置坐标轴长度
        if (mIsAutoSetAxis) {
            mLengthX = mIntervalDataX * mDataTextX.size();
            mLengthY = mIntervalDataY * mDataTextY.size();
        }
        Log.d(TAG, "Length of X----" + mLengthX);
        Log.d(TAG, "Length of Y----" + mLengthY);
        //绘制坐标轴
        drawLineXY(canvas);

        mScaleX = mIntervalDataX / mValueMinusX;
        mScaleY = mIntervalDataY / mValueMinusY;
        if (mLineData != null)
            drawLineData(canvas);//绘制折线图
        drawAverageValueLine(canvas);
        if (isShowGridLine)
            drawGridLine(canvas);

    }

    /**
     * 绘制坐标轴
     * @param canvas
     */
    private void drawLineXY(Canvas canvas) {
        mPaintTextXY.setStrokeWidth(mLineXYSize);
        mPaintTextXY.setColor(mLineXYColor);
        canvas.drawLine(mCenterX, mCenterY, mCenterX + mLengthX, mCenterY, mPaintTextXY);
        canvas.drawLine(mCenterX, mCenterY, mCenterX, mCenterY - mLengthY, mPaintTextXY);
    }

    /**
     * 绘制网格线
     * @param canvas
     */
    private void drawGridLine(Canvas canvas) {
        mPaintTextXY.setStrokeWidth(mGridLineWidth);
        mPaintTextXY.setColor(mGridLineColor);
        mPaintTextXY.setPathEffect(new DashPathEffect(new float[]{0, 0}, 0));
        for (int i = 1; i <= mDataTextX.size(); i++)
            canvas.drawLine(mCenterX + i * mIntervalDataX, mCenterY, mCenterX + i * mIntervalDataX, mCenterY - mLengthY, mPaintTextXY);
        for (int j = 1; j <= mDataTextX.size(); j++)
            canvas.drawLine(mCenterX, mCenterY - j * mIntervalDataY, mCenterX + mLengthX, mCenterY - j * mIntervalDataY, mPaintTextXY);
    }

    /**
     * 绘制折线图数据平均线
     * @param canvas
     */
    private void drawAverageValueLine(Canvas canvas) {
        mPaintTextXY.setStrokeWidth(mAverageValueSize);
        mPaintTextXY.setColor(mAverageValueColor);
        float stopLineX, startLineX, startLineY, stopLineY;
        startLineX = mCenterX;
        stopLineX = startLineX + mLengthX;
        startLineY = stopLineY = mCenterY - getLineAverageValue() * mScaleY;
        if (isShowDottedLine)
            mPaintTextXY.setPathEffect(new DashPathEffect(new float[]{mDottedLineWidth, mDottedLineWidth}, 0));
        canvas.drawLine(startLineX, startLineY, stopLineX, stopLineY, mPaintTextXY);
    }

    /**
     * 绘制折线图
     * @param canvas
     */
    private void drawLineData(Canvas canvas) {
        mPaintLineData.setColor(mLineDataColor);
        mPaintLineData.setStrokeWidth(mLineWidth);
        mPointPaint.setColor(mPointColor);
        mPointPaint.setStrokeWidth(mPointWidth);
        float startPointX, startPointY, stopPointX, stopPointY;
        for (int i = 1; i < mLineData.size(); i++) {
            startPointX = mCenterX + i * mIntervalDataX;
            startPointY = mCenterY - mLineData.get(i-1).getDataY() * mScaleY;
            stopPointX = mCenterX + (i+1) * mIntervalDataX;
            stopPointY = mCenterY - mLineData.get(i).getDataY() * mScaleY;
            canvas.drawLine(startPointX, startPointY, stopPointX, stopPointY, mPaintLineData);
            canvas.drawCircle(startPointX, startPointY, mPointRadius, mPointPaint);
            if ((i+1) == mLineData.size())
                canvas.drawCircle(stopPointX, stopPointY, mPointRadius, mPointPaint);
        }
    }

    /**
     * 绘制X轴的刻度值
     * @param canvas
     */
    private void drawDataLineX(Canvas canvas) {
        mPaintTextXY.setColor(mTextColorX);
        mPaintTextXY.setTextSize(mTextSizeX);
        mPaintTextXY.setTextAlign(Paint.Align.CENTER);
        float startTextX;
        float centerTextWidth = mPaintTextXY.measureText(mCenterValue, 0, mCenterValue.length());
        textHeightX = measureTextHeight(mPaintTextXY);
        float startTextY = mCenterY + textHeightX + marginDataX;
        startTextX = mCenterX - centerTextWidth;
        canvas.drawText(mCenterValue, startTextX, startTextY, mPaintTextXY);
        for (int i = 1; i <= mDataTextX.size(); i++) {
            //绘制进度数字
            String text = mDataTextX.get(i-1);
            startTextX = mCenterX + i * mIntervalDataX;
            canvas.drawText(text, startTextX, startTextY, mPaintTextXY);
        }
    }

    /**
     * 绘制Y轴的刻度值
     * @param canvas
     */
    private void drawDataLineY(Canvas canvas) {
        mPaintTextXY.setColor(mTextColorY);
        mPaintTextXY.setTextSize(mTextSizeY);
        mPaintTextXY.setTextAlign(Paint.Align.CENTER);
        float startTextY, startTickY;
        float startTextX = mCenterX - marginDataY;
        for (int i = 1; i <= mDataTextY.size(); i++) {
            //绘制进度数字
            String text = mDataTextY.get(i-1);
            startTextY = mCenterY - i * mIntervalDataY;
            canvas.drawText(text, startTextX, startTextY + textHeightX / 2, mPaintTextXY);
            //绘制大刻度线
            canvas.drawLine(mCenterX, startTextY, mCenterX - mBigTickMarkWidth, startTextY, mPaintTextXY);
            if (isDrawSmallTick) {
                for (int j = 1; j < mBigPartNum; j++) {
                    startTickY = mCenterY - (i-1) * mIntervalDataY - j * (mIntervalDataY / mBigPartNum);
                    //绘制小刻度线
                    canvas.drawLine(mCenterX, startTickY, mCenterX - mSmallTickMarkWidth, startTickY, mPaintTextXY);
                }
            }
        }
    }

    /**
     * 方式一：手动输入坐标轴数值
     * @param dataTextX
     * @param dataTextY
     */
    public void setOrdinaryAxisData(List<String> dataTextX, List<String> dataTextY) {
        if (dataTextX != null) {
            this.mDataTextX.clear();
            this.mDataTextX = dataTextX;
        }
        if (dataTextY != null) {
            this.mDataTextY.clear();
            this.mDataTextY = dataTextY;
        }
    }

    /**
     * 方式二：自动录入数字刻度值
     * @param startValueX
     * @param valueNumX
     * @param startValueY
     * @param valueNumY
     */
    public void setDigitalAxisData(int startValueX, int valueNumX, int startValueY, int valueNumY) {
        List<String> digitalValueX = new ArrayList<>();
        List<String> digitalValueY = new ArrayList<>();
        for (int i = 0; i < valueNumX; i++)
            digitalValueX.add(String.valueOf(startValueX + i * mValueMinusX));
        for (int j= 0; j < valueNumY; j++)
            digitalValueY.add(String.valueOf(startValueY + j * mValueMinusY));
        this.mDataTextX.clear();
        this.mDataTextY.clear();
        this.mDataTextX = digitalValueX;
        this.mDataTextY = digitalValueY;
        invalidate();
    }

    /**
     * 设置折线图的数据
     * @param topLineData
     */
    public void setLineData(List<DataPoint> topLineData) {
        if (topLineData == null)
            return;
        this.mLineData.clear();
        this.mLineData = topLineData;
        invalidate(); //请求重新draw
    }

    /**
     * 获取折线图数据的平均值
     * @return
     */
    public float getLineAverageValue() {
        float averageValue = 0;
        if (mLineData != null) {
            for (int i = 0; i < mLineData.size(); i++)
                averageValue += mLineData.get(i).getDataY();
            Log.d(TAG, "Line Data Sum----" + averageValue);
            averageValue = averageValue / mLineData.size();
            Log.d(TAG, "Line averageValue----" + averageValue);
        }
        return averageValue;
    }

    /**
     * 测量文字的高度
     * --经测试后发现，采用另一种带Rect的方式，获得的数据并不准确。
     * 特别是在一些对文字有一些倾斜处理的时候
     * @param paint
     * @return
     */
    public static float measureTextHeight(Paint paint){
        float height = 0f;
        if(null == paint){
            return height;
        }
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        height = fontMetrics.descent - fontMetrics.ascent;
//        height = fontMetrics.bottom - fontMetrics.top;
        return height;
    }

    /**
     * sp转px
     * @param context
     * @param spValue
     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * dp转px
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 判断是否自动设置坐标轴宽和高
     * @param isAutoSetAxis
     */
    public void setAutoSetAxis(boolean isAutoSetAxis) {
        mIsAutoSetAxis = isAutoSetAxis;
    }

    /**
     * 设置坐标轴的X轴和Y轴长度（正方向）
     * @param lengthX
     * @param lengthY
     */
    public void setAxisValue(float lengthX, float lengthY) {
        mLengthX = lengthX;
        mLengthY = lengthY;
    }

    /**
     * 设置折线图的颜色
     * @param mLineDataColor
     */
    public void setLineDataColor(int mLineDataColor) {
        this.mLineDataColor = mLineDataColor;
    }

    /**
     * 设置折线图的宽度
     * @param mLineWidth
     */
    public void setLineWidth(int mLineWidth) {
        this.mLineWidth = mLineWidth;
    }

    /**
     * 设置X轴两刻度之间的距离
     * @param mIntervalDataX
     */
    public void setIntervalDataX(int mIntervalDataX) {
        this.mIntervalDataX = mIntervalDataX;
    }

    /**
     * 设置Y轴两刻度之间的距离
     * @param mIntervalDataY
     */
    public void setIntervalDataY(int mIntervalDataY) {
        this.mIntervalDataY = mIntervalDataY;
    }

    /**
     * 设置坐标轴原点
     * @param mCenterX
     * @param mCenterY
     */
    public void setOriginAxis(float mCenterX, float mCenterY) {
        this.mCenterX = mCenterX;
        this.mCenterY = mCenterY;
    }

    /**
     * 设置XY轴画笔的颜色
     * @param mLineXYColor
     */
    public void setLineXYColor(int mLineXYColor) {
        this.mLineXYColor = mLineXYColor;
    }

    /**
     * 设置XY轴画笔的宽度
     * @param mLineXYSize
     */
    public void setLineXYSize(float mLineXYSize) {
        this.mLineXYSize = mLineXYSize;
    }

    /**
     * 设置X轴底部文字的颜色和大小
     * @param colorX
     * @param textSizeX
     */
    public void setTextConfigAxisX(int colorX, float textSizeX) {
        this.mTextColorX = colorX;
        this.mTextSizeX = textSizeX;
    }

    /**
     * 设置Y轴左侧文字的颜色和大小
     * @param colorY
     * @param textSizeY
     */
    public void setTextConfigAxisY(int colorY, float textSizeY) {
        this.mTextColorY = colorY;
        this.mTextSizeY = textSizeY;
    }

    /**
     * 设置X轴底部文字与X轴之间的距离
     * @param marginDataX
     */
    public void setMarginDataX(float marginDataX) {
        this.marginDataX = marginDataX;
    }

    /**
     * 设置Y轴底部文字与Y轴之间的距离
     * @param marginDataY
     */
    public void setMarginDataY(float marginDataY) {
        this.marginDataY = marginDataY;
    }

    /**
     * 设置坐标原点处的文字
     * @param mCenterValue
     */
    public void setCenterValue(String mCenterValue) {
        this.mCenterValue = mCenterValue;
    }

    /**
     * 设置XY坐标轴相邻两刻度之间的差值
     * @param mValueMinusX
     * @param mValueMinusY
     */
    public void setValueMinus(int mValueMinusX, int mValueMinusY) {
        this.mValueMinusX = mValueMinusX;
        this.mValueMinusY = mValueMinusY;
    }

    /**
     * 设置Y轴大小刻度线长度
     * @param mBigTickMarkWidth
     * @param mSmallTickMarkWidth
     */
    public void setTickMarkWidth(float mBigTickMarkWidth, float mSmallTickMarkWidth) {
        this.mBigTickMarkWidth = mBigTickMarkWidth;
        this.mSmallTickMarkWidth = mSmallTickMarkWidth;
    }

    /**
     * 设置Y轴每个大刻度包含的小刻度数量
     * @param mBigPartNum
     */
    public void setBigPartNum(int mBigPartNum) {
        this.mBigPartNum = mBigPartNum;
    }

    /**
     * 设置数据平均线的颜色
     * @param mAverageValueColor
     */
    public void setAverageValueColor(int mAverageValueColor) {
        this.mAverageValueColor = mAverageValueColor;
    }

    /**
     * 设置数据平均线的宽度
     * @param mAverageValueSize
     */
    public void setAverageValueSize(float mAverageValueSize) {
        this.mAverageValueSize = mAverageValueSize;
    }

    /**
     * 设置虚线中每个小实线的长度
     * @param mDottedLineWidth
     */
    public void setDottedLineWidth(float mDottedLineWidth) {
        this.mDottedLineWidth = mDottedLineWidth;
    }

    /**
     * 设置数据平均线的显示方式
     * @param showDottedLine
     */
    public void setShowDottedLine(boolean showDottedLine) {
        isShowDottedLine = showDottedLine;
    }

    /**
     * 设置圆点画笔颜色
     * @param mPointColor
     */
    public void setPointColor(int mPointColor) {
        this.mPointColor = mPointColor;
    }

    /**
     * 设置圆点画笔宽度
     * @param mPointWidth
     */
    public void setPointWidth(float mPointWidth) {
        this.mPointWidth = mPointWidth;
    }

    /**
     * 设置圆点半径
     * @param mPointRadius
     */
    public void setPointRadius(float mPointRadius) {
        this.mPointRadius = mPointRadius;
    }

    /**
     * 设置网格线颜色
     * @param mGridLineColor
     */
    public void setGridLineColor(int mGridLineColor) {
        this.mGridLineColor = mGridLineColor;
    }

    /**
     * 设置网格线宽度
     * @param mGridLineWidth
     */
    public void setGridLineWidth(float mGridLineWidth) {
        this.mGridLineWidth = mGridLineWidth;
    }

    /**
     * 判断是否绘制网格线
     * @param showGridLine
     */
    public void setShowGridLine(boolean showGridLine) {
        isShowGridLine = showGridLine;
    }

    /**
     * 判断是否绘制Y轴的小刻度线
     * @param drawSmallTick
     */
    public void setDrawSmallTick(boolean drawSmallTick) {
        isDrawSmallTick = drawSmallTick;
    }

    /**
     * 判断是否清除图表数据
     * @param isClear
     */
    public void clearChart(boolean isClear) {
        if (isClear)
            mLineData = null;
        Log.d(TAG, "mLineData---" + mLineData);
        invalidate();
    }
}
