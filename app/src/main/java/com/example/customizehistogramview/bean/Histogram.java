package com.example.customizehistogramview.bean;

public class Histogram {

    private float dataX;
    private float dataY;

    public Histogram(float dataX, float dataY) {
        this.dataX = dataX;
        this.dataY = dataY;
    }

    public float getDataX() {
        return dataX;
    }

    public void setDataX(float dataX) {
        this.dataX = dataX;
    }

    public float getDataY() {
        return dataY;
    }

    public void setDataY(float dataY) {
        this.dataY = dataY;
    }
}
