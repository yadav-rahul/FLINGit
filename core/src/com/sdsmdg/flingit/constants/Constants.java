package com.sdsmdg.flingit.constants;

import com.badlogic.gdx.graphics.Color;

/**
 * Created by rahul on 7/12/16.
 */

public class Constants {
    public static final int BLOCK_COUNT = 3;

    public static final double RATIO_TINY = 0.10;
    public static final double RATIO_SMALL = 0.12;
    public static final double RATIO_MEDIUM = 0.2;
    public static final double RATIO_LARGE = 0.25;

    public static final float RADIUS_FACTOR = 20;
    public static final Color COLOR_PRIMARY_BAR_BLUE = toRGB(3, 169, 244);
    public static final Color COLOR_SECONDARY_BAR_BLUE = toRGB(2, 136, 209);
    public static final Color COLOR_GOLD = toRGB(255, 215, 0);

    public static final Color COLOR_BOTTOM = Color.OLIVE;
    public static final Color COLOR_TOP = Color.GOLD;

    private static Color toRGB(int r, int g, int b) {
        float RED = r / 255.0f;
        float GREEN = g / 255.0f;
        float BLUE = b / 255.0f;
        return new Color(RED, GREEN, BLUE, 1);
    }

}
