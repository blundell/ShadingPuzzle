package com.blundell.shadingpuzzle;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class PuzzleGrid extends GridLayout {
    private final List<Zoomable> zoomables = new ArrayList<>();

    private ScaleGestureDetector scaleGestureDetector;
    private int xLightBoxes;
    private int colMaxHints;
    private int yLightBoxes;
    private int rowMaxHints;
    private String[][] leftHints;
    private String[][] topHints;
    private LightBox[][] grid;
    private List<Point> shadedBoxes;

    public PuzzleGrid(Context context) {
        super(context);
    }

    public PuzzleGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PuzzleGrid(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        scaleGestureDetector = new ScaleGestureDetector(getContext(), new ZoomAdjuster(zoomables));
    }

    public PuzzleGrid setGridSize(int lightBoxes) {
        this.xLightBoxes = lightBoxes;
        this.yLightBoxes = lightBoxes;
        this.grid = new LightBox[xLightBoxes][yLightBoxes];
        return this;
    }

    /**
     * @param leftHints HAS to be a uniform 2d array i.e. all secondary arrays have to be the same length
     */
    public PuzzleGrid setLeftHints(String[][] leftHints) {
        this.leftHints = leftHints;
        this.rowMaxHints = leftHints[0].length;
        return this;
    }

    /**
     * @param topHints HAS to be a uniform 2d array i.e. all secondary arrays have to be the same length
     */
    public PuzzleGrid setTopHints(String[][] topHints) {
        this.topHints = topHints;
        this.colMaxHints = topHints[0].length;
        return this;
    }

    public PuzzleGrid setShadedBoxes(List<Point> shadedBoxes) {
        this.shadedBoxes = shadedBoxes;
        return this;
    }

    public void doTheLayoutThatWillEventuallyMoveToXmlAttrs() {
        setColumnCount(colMaxHints + xLightBoxes);
        setRowCount(rowMaxHints + yLightBoxes);
        addLeftHints();
        addTopHints();
        addLightBoxes();
        shadeBoxes();
    }

    private void addLeftHints() {
        for (int r = colMaxHints; r < colMaxHints + yLightBoxes; r++) {
            for (int c = 0; c < rowMaxHints; c++) {
                HintBox hintBox = createHintBox(r, c, leftHints[r - colMaxHints][c]);
                addView(hintBox);
                zoomables.add(hintBox);
            }
        }
    }

    private void addTopHints() {
        for (int c = rowMaxHints; c < rowMaxHints + xLightBoxes; c++) {
            for (int r = 0; r < colMaxHints; r++) {
                HintBox hintBox = createHintBox(r, c, topHints[c - rowMaxHints][r]);
                addView(hintBox);
                zoomables.add(hintBox);
            }
        }
    }

    private HintBox createHintBox(int row, int col, String hint) {
        HintBox hintBox = new HintBox(getContext());
        LayoutParams params = getBoxLayoutParams(row, col);
        params.leftMargin = 5;
        params.rightMargin = 5;
        params.setGravity(Gravity.CENTER);
        hintBox.setLayoutParams(params);
        hintBox.setText(hint);
        return hintBox;
    }

    private void addLightBoxes() {
        for (int r = colMaxHints; r < xLightBoxes + colMaxHints; r++) {
            for (int c = rowMaxHints; c < yLightBoxes + rowMaxHints; c++) {
                LightBox lightBox = createLightBox(r, c);
                addView(lightBox);
                grid[r - colMaxHints][c - rowMaxHints] = lightBox;
                zoomables.add(lightBox);
            }
        }
    }

    private LightBox createLightBox(int row, int col) {
        LightBox lightBox = new LightBox(getContext());
        LayoutParams params = getBoxLayoutParams(row, col);
        lightBox.setLayoutParams(params);
        return lightBox;
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

    private void shadeBoxes() {
        for (Point point : shadedBoxes) {
            LightBox lightBox = grid[point.x][point.y];
            lightBox.setAlwaysShaded();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

    /**
     * Restarts the puzzle and all state
     */
    public void reset() {
        removeAllViews();
        doTheLayoutThatWillEventuallyMoveToXmlAttrs();
    }

    interface Zoomable {
        void adjustZoom(float scaleFactor);
    }

    private static class ZoomAdjuster implements ScaleGestureDetector.OnScaleGestureListener {

        private final List<Zoomable> zoomables;

        private ZoomAdjuster(List<Zoomable> zoomables) {
            this.zoomables = zoomables;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            float zoomLevel = 1;
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
            // nothing
        }
    }
}
