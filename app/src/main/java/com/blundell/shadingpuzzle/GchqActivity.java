package com.blundell.shadingpuzzle;

import android.app.Activity;
import android.os.Bundle;

public class GchqActivity extends Activity {

    public static final int MAX_HINTS_PER_ROW = 9;
    public static final int MAX_HINTS_PER_COL = 9;
    public static final int LIGHT_BOXES_X = 25;
    public static final int LIGHT_BOXES_Y = 25;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gchq);

        ZoomablePuzzle puzzle = (ZoomablePuzzle) findViewById(R.id.root);

        puzzle.setGridXSize(LIGHT_BOXES_X, MAX_HINTS_PER_COL)
                .setGridYSize(LIGHT_BOXES_Y, MAX_HINTS_PER_ROW)
                .setLeftHints(createLeftHints())
                .setTopHints(createTopHints())
                .doTheLayoutThatWillEventuallyMoveToXmlAttrs();

    }

    private String[][] createLeftHints() {
        String[][] hints = new String[LIGHT_BOXES_Y][MAX_HINTS_PER_ROW];
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
        if (hintsForRows.length != LIGHT_BOXES_Y) {
            throw new IllegalStateException("Your data is corrupt " + hintsForRows.length + " != " + LIGHT_BOXES_Y);
        }

        for (int x = 0; x < hints.length; x++) {
            String hintsForRow = hintsForRows[x];
            String[] asArray = hintsForRow.split(",");
            hints[x] = asArray;
        }
        return hints;
    }

    private String[][] createTopHints() {
        String[][] hints = new String[LIGHT_BOXES_X][MAX_HINTS_PER_COL];
        String[] hintsForRows = {
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
        if (hintsForRows.length != LIGHT_BOXES_X) {
            throw new IllegalStateException("Your data is corrupt " + hintsForRows.length + " != " + LIGHT_BOXES_X);
        }

        for (int x = 0; x < hints.length; x++) {
            String hintsForRow = hintsForRows[x];
            String[] asArray = hintsForRow.split(",");
            hints[x] = asArray;
        }
        return hints;
    }

}
