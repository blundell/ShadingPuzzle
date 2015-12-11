package com.blundell.shadingpuzzle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class PuzzleActivity extends Activity {

    private PuzzleGrid puzzleGrid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.puzzle_activity);

        Puzzle puzzle = new Puzzle();
        puzzleGrid = (PuzzleGrid) findViewById(R.id.puzzle_grid);
        puzzleGrid.setGridSize(puzzle.getSize())
                .setLeftHints(puzzle.getLeftHints())
                .setTopHints(puzzle.getTopHints())
                .setShadedBoxes(puzzle.getShadedBoxes())
                .doTheLayoutThatWillEventuallyMoveToXmlAttrs();

        findViewById(R.id.controls_puzzle_reset)
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                puzzleGrid.reset();
                            }
                        }
                );

        findViewById(R.id.controls_puzzle_zoom_in)
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                puzzleGrid.zoomIn();
                            }
                        }
                );

        findViewById(R.id.controls_puzzle_zoom_out)
                .setOnClickListener(
                        new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                puzzleGrid.zoomOut();
                            }
                        }
                );
    }

    @Override
    protected void onResume() {
        super.onResume();
        puzzleGrid.restoreState();
    }

    @Override
    protected void onPause() {
        super.onPause();
        puzzleGrid.saveState();
    }
}
