package com.sdsmdg.flingit.constants;

/**
 * Created by rahul on 6/12/16.
 */

public class Dimensions {

    private int screenWidth;
    private int screenHeight;
    private int gameHeight;
    private int gameWidth;

    public Dimensions(int screenWidth, int screenHeight) {

        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.gameHeight = (int) (1.5 * screenHeight);
        this.gameWidth = 2 * screenWidth;
    }

    public int getScreenWidth() {
        return this.screenWidth;
    }

    public int getScreenHeight() {
        return this.screenHeight;
    }

    public int getGameHeight() {
        return this.gameHeight;
    }

    public int getGameWidth() {
        return this.gameWidth;
    }
}
