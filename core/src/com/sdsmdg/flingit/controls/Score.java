package com.sdsmdg.flingit.controls;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.sdsmdg.flingit.FLINGitGame;
import com.sdsmdg.flingit.screens.PlayScreen;
import com.sdsmdg.flingit.screens.StartScreen;

/**
 * Created by rahul on 8/12/16.
 */

public class Score {
    private int score = 0;
    private int silverCoinCount;
    private int goldCoinCount;
    private int finalBlockId;
    private boolean initiated = false;
    private boolean collide = false;
    private Preferences preferences;
    private FLINGitGame game;
    private PlayScreen playScreen;
    private int gamesPlayed;

    public Score(PlayScreen playScreen, FLINGitGame game) {
        this.playScreen = playScreen;
        this.game = game;
        score = 0;
        silverCoinCount = 0;
        goldCoinCount = 0;
        preferences = Gdx.app.getPreferences("UserPreferences");
        updateGamesPlayed();
    }

    private void updateGamesPlayed() {
        preferences.putInteger("gamesplayed", getGamesPlayed() + 1);
        preferences.flush();

        if (getGamesPlayed() == 2) {
            game.playServices.unlockAchievementTwoGames();
            if (StartScreen.isSound)
                game.assets.getAchievementSound().play(0.5f);
        } else if (getGamesPlayed() == 100) {
            game.playServices.unlockAchievementHundredGames();
            if (StartScreen.isSound)
                game.assets.getAchievementSound().play(0.5f);
        }
    }

    private int getGamesPlayed() {
        return preferences.getInteger("gamesplayed");
    }

    public void updateScore(int count) {

        if (initiated && collide) {

            int temp = score % 3;
            switch (temp) {
                case 0:
                    if (finalBlockId == 1) {
                        increaseScore(count);
                    }
                    break;
                case 1:
                    if (finalBlockId == 2) {
                        increaseScore(count);
                    }
                    break;

                case 2:
                    if (finalBlockId == 3) {
                        increaseScore(count);
                    }
                    break;


            }

            initiated = false;
            collide = false;
        }
    }

    private void increaseScore(int count) {
        score += count;
        if (score > getHighScore()) {
            updateHighScore();
        }
        if (getHighScore() == 10) {
            game.playServices.unlockAchievementTenPoints();
            if (StartScreen.isSound)
                game.assets.getAchievementSound().play(0.5f);
        } else if (getHighScore() == 20) {
            game.playServices.unlockAchievementTwentyPoints();
            if (StartScreen.isSound)
                game.assets.getAchievementSound().play(0.5f);
        } else if (getHighScore() == 50) {
            game.playServices.unlockAchievementFiftyPoints();
            if (StartScreen.isSound)
                game.assets.getAchievementSound().play(0.5f);
        } else if (getHighScore() == 100) {
            game.playServices.unlockAchievementHundredPoints();
            if (StartScreen.isSound)
                game.assets.getAchievementSound().play(0.5f);
        } else if (getHighScore() == 1000) {
            game.playServices.unlockAchievementImpossible();
            if (StartScreen.isSound)
                game.assets.getAchievementSound().play(0.5f);
        }

        if (score % 16 == 0) {
            playScreen.getCoin().getRandomPosition();
            playScreen.getCoin().setRenderCoin(1, true);
        } else if (score % 8 == 0) {
            playScreen.getCoin().getRandomPosition();
            playScreen.getCoin().setRenderCoin(0, true);
        }else if (score % 4 == 0){
            playScreen.getBlackHole().getRandomPosition();
            playScreen.getBlackHole().setRenderBlackHole(true);
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

    public void increaseCoinCount(int flag) {
        if (flag == 0) {
            preferences.putInteger("silvercoincount", getSilverCoinCount() + 1);
        } else if (flag == 1) {
            preferences.putInteger("goldcoincount", getGoldCoinCount() + 1);
        }
        preferences.flush();

        if (getSilverCoinCount() == 10) {
            game.playServices.unlockAchievementTenSilverCoins();
            if (StartScreen.isSound)
                game.assets.getAchievementSound().play(0.5f);
        } else if (getGoldCoinCount() == 5) {
            game.playServices.unlockAchievementFiveGoldCoins();
            if (StartScreen.isSound)
                game.assets.getAchievementSound().play(0.5f);
        } else if (getGoldCoinCount() == 25) {
            game.playServices.unlockAchievementTwentyFiveGoldCoins();
            if (StartScreen.isSound)
                game.assets.getAchievementSound().play(0.5f);
        } else if (getSilverCoinCount() == 100) {
            game.playServices.unlockAchievementHundredSilverCoins();
            if (StartScreen.isSound)
                game.assets.getAchievementSound().play(0.5f);
        }
    }

    public void setInitiated(boolean initiated) {
        this.initiated = initiated;
    }

    public void setCollide(boolean collide, int blockId) {
        //Gdx.app.log("TAG", "Score : " + score + " - Block Id : " + blockId);
        finalBlockId = blockId;
        this.collide = collide;
    }

    public int getSilverCoinCount() {
        return preferences.getInteger("silvercoincount");
    }

    public int getGoldCoinCount() {
        return preferences.getInteger("goldcoincount");
    }

    public int getScore() {
        return score;
    }

    public int getModuloThreeScore() {
        return (score % 3);
    }
}
