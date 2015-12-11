package com.blundell.shadingpuzzle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.CheckBox;

public class LightBox extends CheckBox implements ZoomablePuzzle.Zoomable {

    private final Paint onPaint = new Paint();
    private final Paint offPaint = new Paint();

    private float scaleFactor = 1;
    private int normPaddingLeft;
    private int normPaddingTop;
    private int normPaddingRight;
    private int normPaddingBottom;
    private int widthSize;
    private int heightSize;

    public LightBox(Context context) {
        super(context);
        init();
    }

    private void init() {
        onPaint.setColor(Color.BLACK);
        offPaint.setColor(Color.WHITE);
        normPaddingLeft = getPaddingLeft();
        normPaddingTop = getPaddingTop();
        normPaddingRight = getPaddingRight();
        normPaddingBottom = getPaddingBottom();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        heightSize = MeasureSpec.getSize(heightMeasureSpec);
//
//        int zoomedWidth = (int) (widthSize * scaleFactor);
//        int zoomedHeight = (int) (heightSize * scaleFactor);
//
//        setMeasuredDimension(zoomedWidth, zoomedHeight);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isChecked()) {
            canvas.drawPaint(onPaint);
        } else {
            canvas.drawPaint(offPaint);
        }
    }

    @Override
    public void adjustZoom(float scaleFactor) {
        this.scaleFactor = scaleFactor;
//        setWidth((int) (widthSize * scaleFactor));
//        setHeight((int) (heightSize * scaleFactor));
        invalidate();
    }

    public void setAlwaysDimmed() {
        setChecked(true);
        setEnabled(false);
    }
}
