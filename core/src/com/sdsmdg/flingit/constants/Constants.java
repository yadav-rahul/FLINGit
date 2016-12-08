package com.sdsmdg.flingit.constants;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by rahul on 7/12/16.
 */

public class Constants {
    public static final int BLOCK_COUNT = 3;

    public static final float RADIUS_FACTOR = 20;
    public static final Color COLOR_BODY_FADED = toRGB(245, 0, 87);
    public static final Color COLOR_BODY = Color.PINK;
    public static final Color COLOR_BOTTOM = Color.OLIVE;
    public static final Color COLOR_TOP = Color.GOLD;


    private static Color toRGB(int r, int g, int b) {
        float RED = r / 255.0f;
        float GREEN = g / 255.0f;
        float BLUE = b / 255.0f;
        return new Color(RED, GREEN, BLUE, 1);
    }

}
