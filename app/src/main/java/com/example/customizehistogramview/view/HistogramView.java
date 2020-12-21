package com.example.customizehistogramview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.print.PrinterId;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.app.NavUtils;

import com.example.customizehistogramview.R;
import com.example.customizehistogramview.bean.Histogram;

import java.util.ArrayList;
import java.util.List;

public class HistogramView extends View {

    private static final String TAG = HistogramView.class.getSimpleName();
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 坐标轴原点 X 的默认值
     */
    private final static float DEFAULT_CENTER_X = 100;
    /**
     * 坐标轴原点 Y 的默认值
     */
    private final static float DEFAULT_CENTER_Y = 300;
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
    private int mLineXYColor = Color.BLACK;
    /**
     * XY 轴画笔宽度
     */
    private float mLineXYSize = 2;
    /**
     * 柱状图画笔
     */
    private Paint mPaintLineData;
    /**
     * 柱状图宽度
     */
    private float mLineWidth = 30;
    /**
     * 坐标原点处的值
     */
    private String mCenterValue = "0";
    /**
     * X轴每个刻度之间的距离
     */
    private float mIntervalDataX = 40;
    /**
     * Y轴每个刻度之间的距离
     */
    private float mIntervalDataY = 40;
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
     * X 轴刻度值数量（不包括原点值）
     */
    private int mValueNumX = 7;
    /**
     * Y 轴刻度值数量（不包括原点值）
     */
    private int mValueNumY = 4;
    /**
     * 顶部柱状图数据
     */
    private List<Histogram> mTopLineData;
    /**
     * 底部柱状图数据
     */
    private List<Histogram> mBottomLineData;
    /**
     * 大刻度线宽度
     */
    private float mBigTickMarkWidth = 10;
    /**
     * 判断是否根据坐标轴数据量自动设置宽和高
     */
    private boolean mIsAutoSetAxis = true;


    public HistogramView(Context context) {
        super(context);
        init();
    }

    public HistogramView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public HistogramView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HistogramView);
        //柱状图的宽度
        mCenterX = array.getDimension(R.styleable.HistogramView_center_X, dip2px(mContext, DEFAULT_CENTER_X));
        mCenterY = array.getDimension(R.styleable.HistogramView_center_y, dip2px(mContext, DEFAULT_CENTER_Y));
        array.recycle();
        init();
    }

    /**
     * 初始画笔和数值
     */
    private void init() {

        this.mDataTextX = new ArrayList<>();
        this.mDataTextY = new ArrayList<>();
        this.mTopLineData = new ArrayList<>();
        this.mBottomLineData = new ArrayList<>();

        mScaleX = mIntervalDataX / mValueMinusX;
        mScaleY = mIntervalDataY / mValueMinusY;

        //XY轴
        mPaintTextXY = new Paint();
        mPaintTextXY.setStrokeWidth(mLineXYSize);
//        mPaintTextXY.setColor(mLineXYColor);
//        mPaintTextXY.setTextSize(mLineXYSize);
        mPaintTextXY.setAntiAlias(true);

        mPaintTextXY.setTextSize(22);
        mPaintTextXY.setColor(Color.RED);
        mPaintTextXY.setTextAlign(Paint.Align.CENTER);

        //柱状图
        mPaintLineData = new Paint();
        mPaintLineData.setColor(Color.GREEN);
        mPaintLineData.setStrokeWidth(mLineWidth);
        mPaintLineData.setAntiAlias(true);

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
        //绘制坐标轴
        drawLineXY(canvas);
        if (mTopLineData != null)
            drawTopLineData(canvas);//绘制顶部柱状图
        if (mBottomLineData != null)
            drawBottomLineData(canvas);//绘制底部柱状图
    }

    /**
     * 绘制坐标轴
     * @param canvas
     */
    private void drawLineXY(Canvas canvas) {

        canvas.drawLine(mCenterX, mCenterY, mCenterX + mLengthX, mCenterY, mPaintTextXY);
        canvas.drawLine(mCenterX, mCenterY + textHeightX + 2 * marginDataX,
                mCenterX + mLengthX, mCenterY + textHeightX + 2 * marginDataX, mPaintTextXY);
        canvas.drawLine(mCenterX, mCenterY, mCenterX, mCenterY - mLengthY, mPaintTextXY);
        canvas.drawLine(mCenterX, mCenterY, mCenterX, mCenterY + mLengthY + textHeightX + 2 * marginDataX, mPaintTextXY);
    }

    /**
     * 绘制顶部柱状图
     * @param canvas
     */
    private void drawTopLineData(Canvas canvas) {
        float startLineX, stopLineX, stopLineY;
        float startLineY = mCenterY;
        for (int i = 1; i <= mDataTextX.size(); i++) {
            startLineX = mCenterX + i * mIntervalDataX;
            stopLineX = startLineX;
            stopLineY = startLineY - mTopLineData.get(i-1).getDataY() * mScaleY;
            Log.d("stopLineY", i + "--" + stopLineY);
            canvas.drawLine(startLineX, startLineY, stopLineX, stopLineY, mPaintLineData);
        }
    }

    /**
     * 绘制底部柱状图
     * @param canvas
     */
    private void drawBottomLineData(Canvas canvas) {
        float startLineX, stopLineX, stopLineY;
        float startLineY = mCenterY + textHeightX + 2 * marginDataX;
        for (int i = 1; i <= mDataTextX.size(); i++) {
            startLineX = mCenterX + i * mIntervalDataX;
            stopLineX = startLineX;
            stopLineY = startLineY + mBottomLineData.get(i-1).getDataY() * mScaleY;
            Log.d("stopLineY", i + "--" + stopLineY);
            canvas.drawLine(startLineX, startLineY, stopLineX, stopLineY, mPaintLineData);
        }
    }

    /**
     * 绘制X轴的刻度值
     * @param canvas
     */
    private void drawDataLineX(Canvas canvas) {
        float startTextX;
        float centerTextWidth = mPaintTextXY.measureText(mCenterValue, 0, mCenterValue.length());
        textHeightX = measureTextHeight(mPaintTextXY);
        float startTextY = mCenterY + textHeightX + marginDataX;
        startTextX = mCenterX - centerTextWidth;
        canvas.drawText(mCenterValue, startTextX, startTextY, mPaintTextXY);
        for (int i = 1; i <= mDataTextX.size(); i++) {
            //绘制进度数字
            String text = mDataTextX.get(i - 1);
            startTextX = mCenterX + i * mIntervalDataX;
            canvas.drawText(text, startTextX, startTextY, mPaintTextXY);
        }
    }

    /**
     * 绘制Y轴的刻度值
     * @param canvas
     */
    private void drawDataLineY(Canvas canvas) {
        float startTopTextY, startBottomTextY;
        float startTopTextX = mCenterX - marginDataY;
        float startBottomTextX = mCenterX - marginDataY;
        for (int i = 1; i <= mDataTextY.size(); i++) {
            //绘制进度数字
            String text = mDataTextY.get(i-1);
            startTopTextY = mCenterY - i * mIntervalDataY;
            startBottomTextY = mCenterY + i * mIntervalDataY + textHeightX + 2 * marginDataX;
            canvas.drawText(text, startTopTextX, startTopTextY + textHeightX / 2, mPaintTextXY);
            canvas.drawText(text, startBottomTextX, startBottomTextY, mPaintTextXY);
            canvas.drawLine(mCenterX, startTopTextY, mCenterX - mBigTickMarkWidth, startTopTextY, mPaintTextXY);
            canvas.drawLine(mCenterX, startBottomTextY, mCenterX - mBigTickMarkWidth, startBottomTextY, mPaintTextXY);
        }
    }

    /**
     * 手动输入坐标轴数值
     * @param dataTextX
     * @param dataTextY
     */
    public void setOrdinaryAxisData(List<String> dataTextX, List<String> dataTextY) {
        this.mDataTextX = dataTextX;
        this.mDataTextY = dataTextY;
    }

    /**
     * 自动录入数字刻度值
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
        for (int j = 0; j < valueNumY; j++)
            digitalValueY.add(String.valueOf(startValueY + j * mValueMinusY));
        this.mDataTextX.clear();
        this.mDataTextY.clear();
        this.mDataTextX = digitalValueX;
        this.mDataTextY = digitalValueY;
        if (mIsAutoSetAxis) {
            mLengthX = mIntervalDataX * mDataTextX.size() + mLineWidth * 2;
            mLengthY = mIntervalDataY * mDataTextY.size();
        }
        Log.d(TAG, String.valueOf(mLengthX));
        Log.d(TAG, String.valueOf(mLengthY));
        invalidate();
    }

    /**
     * 设置柱状图的数据
     * @param topLineData
     */
    public void setTopLineData(List<Histogram> topLineData) {
        if (topLineData == null)
            return;
        this.mTopLineData.clear();
        this.mTopLineData = topLineData;
        invalidate(); //请求重新draw
    }

    /**
     * 设置柱状图的数据
     * @param topBottomData
     */
    public void setBottomLineData(List<Histogram> topBottomData) {
        if (topBottomData == null)
            return;
        this.mBottomLineData.clear();
        this.mBottomLineData = topBottomData;
        invalidate(); //请求重新draw
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
}
