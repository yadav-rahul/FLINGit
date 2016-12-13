package com.sdsmdg.flingit.sprites;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sdsmdg.flingit.FLINGitGame;
import com.sdsmdg.flingit.controls.Score;
import com.sdsmdg.flingit.screens.PlayScreen;
import com.sdsmdg.flingit.screens.StartScreen;

import java.util.Random;

/**
 * Created by rahul on 12/12/16.
 */

public class Coin {

    private final static String TAG = Coin.class.getSimpleName();

    private FLINGitGame game;
    private boolean isTouched = false;
    private OrthographicCamera camera;
    private Vector2 position;
    private Sprite silverCoinSprite, goldCoinSprite;
    private boolean isRenderCoin;
    private int flag;
    private Random random;
    private Body body;
    private Rectangle coinRect;
    private Score score;

    public Coin(FLINGitGame game, OrthographicCamera camera, Score score) {
        this.score = score;
        random = new Random();
        this.game = game;
        this.camera = camera;
        silverCoinSprite = game.assets.getSilverCoinSprite();
        goldCoinSprite = game.assets.getGoldCoinSprite();

        silverCoinSprite.setSize(game.dimensions.getScreenWidth() / 10, game.dimensions.getScreenWidth() / 10);
        goldCoinSprite.setSize(game.dimensions.getScreenWidth() / 10, game.dimensions.getScreenWidth() / 10);

    }

    public void getRandomPosition() {
        position = new Vector2(camera.position.x + camera.viewportWidth / 2 +
                (random.nextInt((int) (camera.viewportWidth / 2))),
                random.nextInt((int) (game.dimensions.getScreenHeight() / 4 - silverCoinSprite.getHeight()))
                        + 3 * game.dimensions.getScreenHeight() / 4);
        coinRect = new Rectangle(position.x, position.y, silverCoinSprite.getWidth(), silverCoinSprite.getHeight());
    }

    public void render(PlayScreen playScreen, Body body, SpriteBatch spriteBatch) {
        if (!playScreen.isGameOver()) {
            this.body = body;
            if (isRenderCoin) {
                if (flag == 0) {
                    //Silver Coin

                    silverCoinSprite.setPosition(position.x, position.y);
                    silverCoinSprite.draw(spriteBatch);
                    //Gdx.app.log("TAG", "Silver coin at : " + position.x + " : " + position.y);
                } else if (flag == 1) {
                    //Gold Coin
                    goldCoinSprite.setPosition(position.x, position.y);
                    goldCoinSprite.draw(spriteBatch);
                    //Gdx.app.log("TAG", "Gold coin at : " + position.x + " : " + position.y);
                }
                detectCollision();
            }
            if (position.x < camera.position.x - (camera.viewportWidth / 2)) {
                //TODO Do something here
                isRenderCoin = false;
            }
        }
    }

    private void detectCollision() {
        if (coinRect.overlaps(body.getRectBody())) {
            //Remove coin and increase respective coin count by one
            if (StartScreen.isSound) {
                game.assets.getCoinSound().play(0.5f);
            }
            isRenderCoin = false;
            score.increaseCoinCount(flag);
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isRenderCoin() {
        return isRenderCoin;
    }

    public void setRenderCoin(int flag, boolean renderCoin) {
        this.flag = flag;
        isRenderCoin = renderCoin;
    }
}
