package com.sdsmdg.flingit.controls;

/**
 * Created by rahul on 8/12/16.
 */

public class Score {
    private int score;
    private boolean initiated = false;
    private boolean collide = false;

    public Score() {
        score = 0;
    }

    public void updateScore() {
        if (initiated && collide) {
            //Increase score by one
            score += 1;

            initiated = false;
            collide = false;
        }
    }


    public void setInitiated(boolean initiated) {
        this.initiated = initiated;
    }

    public void setCollide(boolean collide) {
        this.collide = collide;
    }

    public int getScore() {
        return score;
    }
}
