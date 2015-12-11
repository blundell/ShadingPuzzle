package com.blundell.shadingpuzzle;

import android.app.Activity;
import android.graphics.Point;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class GchqActivity extends Activity {

    private static final int MAX_HINTS_PER_ROW = 9;
    private static final int MAX_HINTS_PER_COL = 9;
    private static final int LIGHT_BOXES_X = 25;
    private static final int LIGHT_BOXES_Y = 25;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gchq);

        ZoomablePuzzle puzzle = (ZoomablePuzzle) findViewById(R.id.root);

        puzzle.setGridXSize(LIGHT_BOXES_X, MAX_HINTS_PER_COL)
                .setGridYSize(LIGHT_BOXES_Y, MAX_HINTS_PER_ROW)
                .setLeftHints(createLeftHints())
                .setTopHints(createTopHints())
                .setDimmedBoxes(createDimBoxes())
                .doTheLayoutThatWillEventuallyMoveToXmlAttrs();

    }

    private List<Point> createDimBoxes() {
        ArrayList<Point> points = new ArrayList<>();
        points.add(new Point(0,0));
        return points;
    }

    private String[][] createLeftHints() {
        String[] hintsForRows = {
                " , , , ,7,3,1,1,7",
                " , , ,1,1,2,2,1,1",
                " ,1,3,1,3,1,1,3,1",
                " ,1,3,1,1,6,1,3,1",
                " ,1,3,1,5,2,1,3,1",
                " , , , ,1,1,2,1,1",
                " , ,7,1,1,1,1,1,7",
                " , , , , , , ,3,3",
                "1,2,3,1,1,3,1,1,2",
                " , , ,1,1,3,2,1,1",
                " , , ,4,1,4,2,1,2",
                " ,1,1,1,1,1,4,1,3",
                " , , ,2,1,1,1,2,5",
                " , , ,3,2,2,6,3,1",
                " , , ,1,9,1,1,2,1",
                " , , ,2,1,2,2,3,1",
                " , ,3,1,1,1,1,5,1",
                " , , , , ,1,2,2,5",
                " , ,7,1,2,1,1,1,3",
                " , ,1,1,2,1,2,2,1",
                " , , ,1,3,1,4,5,1",
                " , , ,1,3,1,3,10,2",
                " , , ,1,3,1,1,6,6",
                " , , ,1,1,2,1,1,2",
                " , , , ,7,2,1,2,5",
        };
        return createHints(LIGHT_BOXES_Y, MAX_HINTS_PER_ROW, hintsForRows);
    }

    private String[][] createTopHints() {
        String[] hintsForCols = {
                " , , , ,7,2,1,1,7",
                " , , ,1,1,2,2,1,1",
                "1,3,1,3,1,3,1,3,1",
                " ,1,3,1,1,5,1,3,1",
                " ,1,3,1,1,4,1,3,1",
                " , , ,1,1,1,2,1,1",
                " , ,7,1,1,1,1,1,7",
                " , , , , , ,1,1,3",
                " , ,2,1,2,1,8,2,1",
                " ,2,2,1,2,1,1,1,2",
                " , , , ,1,7,3,2,1",
                " ,1,2,3,1,1,1,1,1",
                " , , , ,4,1,1,2,6",
                " , ,3,3,1,1,1,3,1",
                " , , , ,1,2,5,2,2",
                "2,2,1,1,1,1,1,2,1",
                " , ,1,3,3,2,1,8,1",
                " , , , , , ,6,2,1",
                " , , ,7,1,4,1,1,3",
                " , , , ,1,1,1,1,4",
                " , , ,1,3,1,3,7,1",
                "1,3,1,1,1,2,1,1,4",
                " , , ,1,3,1,4,3,3",
                " , ,1,1,2,2,2,6,1",
                " , , ,7,1,3,2,1,1",
        };
        return createHints(LIGHT_BOXES_X, MAX_HINTS_PER_COL, hintsForCols);
    }

    private String[][] createHints(int totalHintLines, int maxHintsPerLine, String[] hintLines) {
        if (totalHintLines != hintLines.length) {
            throw new IllegalStateException("Your input is corrupt " + totalHintLines + " != " + hintLines.length);
        }
        String[][] hints = new String[totalHintLines][maxHintsPerLine];
        for (int x = 0; x < hints.length; x++) {
            String hintsForLine = hintLines[x];
            String[] asArray = hintsForLine.split(",");
            hints[x] = asArray;
        }
        return hints;
    }

}
