package com.blundell.shadingpuzzle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.widget.CheckBox;

public class LightBox extends CheckBox implements PuzzleGrid.Zoomable {

    private final Paint onPaint = new Paint();
    private final Paint alwaysOnPaint = new Paint();
    private final Paint offPaint = new Paint();

    private int widthSize;
    private int heightSize;

    public LightBox(Context context) {
        super(context);
        onPaint.setColor(Color.parseColor("#404040"));
        alwaysOnPaint.setColor(Color.BLACK);
        offPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthSize = getMeasuredWidth();
        heightSize = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isChecked() && !isEnabled()) {
            canvas.drawPaint(alwaysOnPaint);
        } else if (isChecked()) {
            canvas.drawPaint(onPaint);
        } else {
            canvas.drawPaint(offPaint);
        }
    }

    @Override
    public void adjustZoom(float scaleFactor) {
        setMeasuredDimension((int) (widthSize * scaleFactor), (int) (heightSize * scaleFactor));
    }

    public void setAlwaysDimmed() {
        setChecked(true);
        setEnabled(false);
    }
}
