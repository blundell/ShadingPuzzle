package com.blundell.shadingpuzzle;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class ZoomablePuzzle extends GridLayout {
    private final List<Zoomable> zoomables = new ArrayList<>();

    private ScaleGestureDetector scaleGestureDetector;
    private int xLightBoxes;
    private int colMaxHints;
    private int yLightBoxes;
    private int rowMaxHints;
    private float zoomLevel = 1;

    public ZoomablePuzzle(Context context) {
        super(context);
    }

    public ZoomablePuzzle(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZoomablePuzzle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ZoomablePuzzle setGridXSize(int lightBoxes, int maxHints) {
        this.xLightBoxes = lightBoxes;
        this.colMaxHints = maxHints;
        return this;
    }

    public ZoomablePuzzle setGridYSize(int lightBoxes, int maxHints) {
        this.yLightBoxes = lightBoxes;
        this.rowMaxHints = maxHints;
        return this;
    }

    public void doTheLayoutThatWillEventuallyMoveToXmlAttrs() {
        setColumnCount(colMaxHints + xLightBoxes);
        setRowCount(rowMaxHints + yLightBoxes);

        for (int r = colMaxHints; r < colMaxHints + yLightBoxes; r++) {
            for (int c = 0; c < rowMaxHints; c++) {
                HintBox hintBox = new HintBox(getContext());
                LayoutParams params = getBoxLayoutParams(r, c);
                hintBox.setLayoutParams(params);
                hintBox.setText("1");
                addView(hintBox);
            }
        }

        for (int c = rowMaxHints; c < rowMaxHints + xLightBoxes; c++) {
            for (int r = 0; r < colMaxHints; r++) {
                HintBox hintBox = new HintBox(getContext());
                LayoutParams params = getBoxLayoutParams(r, c);
                hintBox.setLayoutParams(params);
                hintBox.setText("1");
                addView(hintBox);
            }
        }

        for (int r = colMaxHints; r < xLightBoxes + colMaxHints; r++) {
            for (int c = rowMaxHints; c < yLightBoxes + rowMaxHints; c++) {
                LightBox lightBox = new LightBox(getContext());
                LayoutParams params = getBoxLayoutParams(r, c);
                lightBox.setLayoutParams(params);
                addView(lightBox);
            }
        }

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (!(view instanceof Zoomable)) {
                throw new IllegalStateException("All children must implement Zoomable. Bad boi:" + view);
            }
            zoomables.add((Zoomable) view);
        }
    }

    private LayoutParams getBoxLayoutParams(int rowPos, int colPos) {
        Spec rowSpec = GridLayout.spec(rowPos);
        Spec columnSpec = GridLayout.spec(colPos);
        LayoutParams params = new LayoutParams(rowSpec, columnSpec);
        params.leftMargin = 1;
        params.rightMargin = 1;
        params.topMargin = 1;
        params.bottomMargin = 1;
        params.setGravity(Gravity.CENTER);
        return params;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        scaleGestureDetector = new ScaleGestureDetector(
                getContext(),
                new ScaleGestureDetector.OnScaleGestureListener() {
                    @Override
                    public boolean onScale(ScaleGestureDetector detector) {
                        float scaleFactor = detector.getScaleFactor();

                        if (scaleFactor > 1.2) {
                            zoomLevel = 0.8F;
                        } else if (scaleFactor > 1.0) {
                            zoomLevel = 0.5F;
                        } else if (scaleFactor > 0.8) {
                            zoomLevel = 0.3F;
                        } else if (scaleFactor > 0.7) {
                            zoomLevel = 0.2F;
                        } else if (scaleFactor > 0.6) {
                            zoomLevel = 0.1F;
                        }

                        Log.d("XXX", "Factor " + scaleFactor);
                        for (Zoomable zoomable : zoomables) {
                            zoomable.adjustZoom(zoomLevel);
                        }
                        return false;
                    }

                    @Override
                    public boolean onScaleBegin(ScaleGestureDetector detector) {
                        return true;
                    }

                    @Override
                    public void onScaleEnd(ScaleGestureDetector detector) {

                    }
                }
        );
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    interface Zoomable {
        void adjustZoom(float scaleFactor);
    }
}
