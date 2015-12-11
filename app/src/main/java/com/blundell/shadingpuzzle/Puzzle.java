package com.blundell.shadingpuzzle;

import android.graphics.Point;

import java.util.ArrayList;
import java.util.List;

class Puzzle {

    private static final int LIGHT_BOXES_PER_LINE = 25;

    public int getSize() {
        return LIGHT_BOXES_PER_LINE;
    }

    public List<Point> getShadedBoxes() {
        List<Point> points = new ArrayList<>();
        points.add(new Point(3, 3));
        points.add(new Point(3, 4));
        points.add(new Point(3, 12));
        points.add(new Point(3, 13));
        points.add(new Point(3, 21));

        points.add(new Point(8, 6));
        points.add(new Point(8, 7));
        points.add(new Point(8, 10));
        points.add(new Point(8, 14));
        points.add(new Point(8, 15));
        points.add(new Point(8, 18));

        points.add(new Point(16, 6));
        points.add(new Point(16, 11));
        points.add(new Point(16, 16));
        points.add(new Point(16, 20));

        points.add(new Point(21, 3));
        points.add(new Point(21, 4));
        points.add(new Point(21, 9));
        points.add(new Point(21, 10));
        points.add(new Point(21, 15));
        points.add(new Point(21, 20));
        points.add(new Point(21, 21));

        return points;
    }

    public String[][] getLeftHints() {
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
        return createHints(LIGHT_BOXES_PER_LINE, hintsForRows);
    }

    public String[][] getTopHints() {
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
        return createHints(LIGHT_BOXES_PER_LINE, hintsForCols);
    }

    private String[][] createHints(int totalHintLines, String[] hintLines) {
        if (totalHintLines != hintLines.length) {
            throw new IllegalStateException("Your input is corrupt " + totalHintLines + " != " + hintLines.length);
        }
        String[][] hints = new String[totalHintLines][];
        for (int x = 0; x < hints.length; x++) {
            String hintsForLine = hintLines[x];
            String[] asArray = hintsForLine.split(",");
            hints[x] = asArray;
        }
        return hints;
    }
}
