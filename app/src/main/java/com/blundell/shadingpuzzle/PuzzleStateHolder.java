package com.blundell.shadingpuzzle;

import android.content.Context;
import android.content.SharedPreferences;

class PuzzleStateHolder {

    private static final String PREFS_NAME = "com.blundell.shadingpuzzle.PuzzleStateHolder";
    public static final String TOTAL_X = "x";
    public static final String TOTAL_Y = "y";

    private SharedPreferences preferences;

    static PuzzleStateHolder newInstance(Context context) {
        SharedPreferences preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return new PuzzleStateHolder(preferences);
    }

    PuzzleStateHolder(SharedPreferences preferences) {
        this.preferences = preferences;
    }

    public void clear() {
        preferences.edit().clear().apply();
    }

    public void saveSate(LightBox[][] grid) { // TODO save half shaded
        SharedPreferences.Editor editor = preferences.edit();
        editor
            .putInt(TOTAL_X, grid.length)
            .putInt(TOTAL_Y, grid[0].length);
        for (int x = 0; x < grid.length; x++) {
            for (int y = 0; y < grid[x].length; y++) {
                LightBox lightBox = grid[x][y];
                boolean halfShaded = lightBox.isHalfShaded();
                editor.putBoolean(getHalfShadedKey(x, y), halfShaded);
                boolean fullyShaded = lightBox.isShaded();
                editor.putBoolean(getShadedKey(x, y), fullyShaded);
            }
        }
        editor.apply();
    }

    public void restoreState(LightBox[][] grid) {
        int gridX = preferences.getInt(TOTAL_X, 0);
        int gridY = preferences.getInt(TOTAL_Y, 0);
        for (int x = 0; x < gridX; x++) {
            for (int y = 0; y < gridY; y++) {
                LightBox lightBox = grid[x][y];
                boolean halfShaded = preferences.getBoolean(getShadedKey(x, y), false);
                lightBox.setHalfShaded(halfShaded);
                boolean fullyShaded = preferences.getBoolean(getShadedKey(x, y), false);
                lightBox.setShaded(fullyShaded);
            }
        }
    }

    private String getShadedKey(int x, int y) {
        return "state" + x + "/" + y;
    }

    private String getHalfShadedKey(int x, int y) {
        return "state-halfShadedKey" + x + "/" + y;
    }
}
