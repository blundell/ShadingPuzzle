package com.blundell.shadingpuzzle;

import android.app.Activity;
import android.os.Bundle;

public class GchqActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gchq);

        ZoomablePuzzle puzzle = (ZoomablePuzzle) findViewById(R.id.root);

        puzzle.setGridXSize(25, 9)
                .setGridYSize(25, 9)
                .doTheLayoutThatWillEventuallyMoveToXmlAttrs();

    }

}
