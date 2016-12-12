package com.sdsmdg.flingit.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.sdsmdg.flingit.FLINGitGame;
import com.sdsmdg.flingit.screens.PlayScreen;

/**
 * Created by rahul on 8/12/16.
 */

public class Score {
    private int score = 0;
    private int finalBlockId;
    private boolean initiated = false;
    private boolean collide = false;
    private Preferences preferences;
    private FLINGitGame game;
    private PlayScreen playScreen;

    public Score(PlayScreen playScreen, FLINGitGame game) {
        this.playScreen = playScreen;
        this.game = game;
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
        if (getScore() == 10) {
            game.playServices.unlockAchievementTenPoints();
        } else if (getScore() == 20) {
            game.playServices.unlockAchievementTwentyPoints();
        } else if (getScore() == 50) {
            game.playServices.unlockAchievementFiftyPoints();
        } else if (getScore() == 100) {
            game.playServices.unlockAchievementHundredPoints();
        }

        if (score % 15 == 0) {
            playScreen.getCoin().getRandomPosition();
            playScreen.getCoin().setRenderCoin(1, true);
        } else if (score % 5 == 0) {
            playScreen.getCoin().getRandomPosition();
            playScreen.getCoin().setRenderCoin(0, true);
        }
    }

    public int getHighScore() {
        return preferences.getInteger("highscore");
    }

    private void updateHighScore() {
        game.playServices.submitScore(getScore());
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
