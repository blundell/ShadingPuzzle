package com.blundell.shadingpuzzle;

import android.content.Context;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.GridLayout;

import java.util.ArrayList;
import java.util.List;

public class PuzzleGrid extends GridLayout {
    private final List<Zoomable> zoomables = new ArrayList<>();
    private final PuzzleStateHolder repository;

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
        repository = PuzzleStateHolder.newInstance(context);
    }

    public PuzzleGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
        repository = PuzzleStateHolder.newInstance(context);
    }

    public PuzzleGrid(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        repository = PuzzleStateHolder.newInstance(context);
    }

    /**
     * @param lightBoxes the X by Y of shaded boxes you want the puzzle ot be
     */
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

    /**
     * @param shadedBoxes the X,Y positions of any boxes you want pre-shaded
     * @return
     */
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

    /**
     * Expected to be called in onPause, unguaranteed behaviour otherwise
     */
    public void saveState() {
        repository.saveSate(grid);
    }

    /**
     * Expected to be called in onResume, unguaranteed behaviour otherwise
     */
    public void restoreState() {
        repository.restoreState(grid);
    }

    /**
     * Restarts the puzzle and all state
     */
    public void reset() {
        repository.clear();
        removeAllViews();
        doTheLayoutThatWillEventuallyMoveToXmlAttrs();
    }

    public void zoomIn() {
        for (Zoomable zoomable : zoomables) {
            zoomable.adjustZoom(0.9f);
        }
    }

    public void zoomOut() {
        for (Zoomable zoomable : zoomables) {
            zoomable.adjustZoom(0.3f);
        }
    }

    interface Zoomable {
        void adjustZoom(float scaleFactor);
    }

}
