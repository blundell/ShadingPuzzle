package com.blundell.shadingpuzzle;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

public class HintBox extends TextView implements ZoomablePuzzle.Zoomable {

    private float scaleFactor = 1;
    private float originalTextSize;

    public HintBox(Context context) {
        super(context);
        init();
    }

    private void init() {
        setTextColor(Color.BLACK);
        originalTextSize = getTextSize();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//
//        int zoomedWidth = (int) (widthSize * scaleFactor);
//        int zoomedHeight = (int) (heightSize * scaleFactor);
//
//        setMeasuredDimension(zoomedWidth, zoomedHeight);
    }

    @Override
    public void adjustZoom(float scaleFactor) {
        this.scaleFactor = scaleFactor;
        setTextSize(originalTextSize * scaleFactor);
        requestLayout();
        invalidate();
    }
}
