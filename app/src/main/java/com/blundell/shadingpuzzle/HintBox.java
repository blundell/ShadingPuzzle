package com.blundell.shadingpuzzle;

import android.content.Context;
import android.graphics.Color;
import android.widget.TextView;

public class HintBox extends TextView implements ZoomablePuzzle.Zoomable {

    private float originalTextSize;

    public HintBox(Context context) {
        super(context);
        setTextColor(Color.BLACK);
        originalTextSize = getTextSize();
    }

    @Override
    public void adjustZoom(float scaleFactor) {
        setTextSize(originalTextSize * scaleFactor);
    }
}
