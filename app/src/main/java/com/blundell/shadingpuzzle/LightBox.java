package com.blundell.shadingpuzzle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

public class LightBox extends View implements PuzzleGrid.Zoomable {

    private final Paint onPaint = new Paint();
    private final Paint alwaysOnPaint = new Paint();
    private final Paint offPaint = new Paint();
    private final int widthSize = (int) (getResources().getDisplayMetrics().density * 30);
    private final int heightSize = (int) (getResources().getDisplayMetrics().density * 30);
    private final Path leftTrianglePath;
    private final Path rightTrianglePath;

    private boolean halfShaded;
    private boolean shaded;

    public LightBox(Context context) {
        super(context);
        onPaint.setColor(Color.parseColor("#404040"));
        alwaysOnPaint.setColor(Color.BLACK);
        offPaint.setColor(Color.WHITE);
        leftTrianglePath = new Path();
        rightTrianglePath = new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN) {
            return true;
        }
        if (action == MotionEvent.ACTION_UP) {
            invalidate();
            if (shaded) {
                shaded = false;
                halfShaded = false;
                return true;
            }
            if (halfShaded) {
                shaded = true;
                halfShaded = false;
                return true;
            }
            halfShaded = true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthSize, heightSize);

        leftTrianglePath.moveTo(0, 0);
        leftTrianglePath.lineTo(widthSize, heightSize);
        leftTrianglePath.lineTo(0, heightSize);
        leftTrianglePath.close();

        rightTrianglePath.moveTo(widthSize, 0);
        rightTrianglePath.lineTo(widthSize, heightSize);
        rightTrianglePath.lineTo(0, 0);
        rightTrianglePath.close();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (shaded && !isEnabled()) {
            canvas.drawPaint(alwaysOnPaint);
        } else if (shaded) {
            canvas.drawPaint(onPaint);
        } else if (halfShaded) {
            canvas.drawPath(leftTrianglePath, onPaint);
            canvas.drawPath(rightTrianglePath, offPaint);
        } else {
            canvas.drawPaint(offPaint);
        }
    }

    @Override
    public void adjustZoom(float scaleFactor) {
        setMeasuredDimension((int) (widthSize * scaleFactor), (int) (heightSize * scaleFactor));
    }

    public void setAlwaysShaded() {
        shaded = true;
        halfShaded = false;
        setEnabled(false);
    }

    public boolean isShaded() {
        return shaded;
    }

    public void setShaded(boolean shaded) {
        this.shaded = shaded;
    }

    public boolean isHalfShaded() {
        return halfShaded && !shaded;
    }

    public void setHalfShaded(boolean halfShaded) {
        this.halfShaded = halfShaded;
    }
}
