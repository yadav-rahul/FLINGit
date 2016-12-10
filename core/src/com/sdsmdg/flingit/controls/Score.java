package com.sdsmdg.flingit.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

/**
 * Created by rahul on 8/12/16.
 */

public class Score {
    private int score = 0;
    private int finalBlockId;
    private boolean initiated = false;
    private boolean collide = false;
    private Preferences preferences;

    public Score() {
        score = 0;
        preferences = Gdx.app.getPreferences("UserPreferences");
    }

    public void updateScore() {

        if (initiated && collide) {

            int temp = score % 3;
            switch (temp) {
                case 0:
                    if (finalBlockId == 1) {
                        increaseScore();
                    }
                    break;
                case 1:
                    if (finalBlockId == 2) {
                        increaseScore();
                    }
                    break;

                case 2:
                    if (finalBlockId == 3) {
                        increaseScore();
                    }
                    break;


            }

            initiated = false;
            collide = false;
        }
    }

    private void increaseScore() {
        score += 1;
        if (score > getHighScore()) {
            updateHighScore();
        }
    }

    public int getHighScore() {
        return preferences.getInteger("highscore");
    }

    private void updateHighScore() {
        preferences.putInteger("highscore", getScore());
        preferences.flush();
    }

    public void setInitiated(boolean initiated) {
        this.initiated = initiated;
    }

    public void setCollide(boolean collide, int blockId) {
        //Gdx.app.log("TAG", "Score : " + score + " - Block Id : " + blockId);
        finalBlockId = blockId;
        this.collide = collide;
    }

    public int getScore() {
        return score;
    }

    public int getModuloThreeScore() {
        return (score % 3);
    }
}
