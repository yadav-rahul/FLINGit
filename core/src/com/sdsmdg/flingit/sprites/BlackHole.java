package com.sdsmdg.flingit.sprites;

import com.badlogic.gdx.Gdx;
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
 * Created by rahul on 15/12/16.
 */

public class BlackHole {

    private final static String TAG = BlackHole.class.getSimpleName();

    private FLINGitGame game;
    private boolean isTouched = false;
    private OrthographicCamera camera;
    private Vector2 position;
    private Sprite blackHoleSprite;
    private boolean isRenderBlackHole;
    private Random random;
    private Body body;
    private Rectangle blackHoleRect;
    private Score score;
    private boolean isRadiusZero = false;
    private int radiusParam = 0;

    public BlackHole(FLINGitGame game, OrthographicCamera camera, Score score) {
        this.score = score;
        random = new Random();
        this.game = game;
        this.camera = camera;
        blackHoleSprite = game.assets.getBlackHoleSprite();

        blackHoleSprite.setSize(game.dimensions.getScreenWidth() / 5, game.dimensions.getScreenWidth() / 5);
    }

    public void getRandomPosition() {
        position = new Vector2(camera.position.x + camera.viewportWidth / 2 +
                (random.nextInt((int) (camera.viewportWidth / 2))),
                random.nextInt((int) (game.dimensions.getScreenHeight() / 4 - blackHoleSprite.getHeight()))
                        + 3 * game.dimensions.getScreenHeight() / 4);
        blackHoleRect = new Rectangle(position.x + blackHoleSprite.getWidth() / 6, position.y + blackHoleSprite.getWidth() / 6
                , 4 * blackHoleSprite.getWidth() / 6, 4 * blackHoleSprite.getHeight() / 6);
    }

    public void render(PlayScreen playScreen, Body body, SpriteBatch spriteBatch) {
        if (!playScreen.isGameOver()) {
            this.body = body;
            if (isRenderBlackHole) {

                blackHoleSprite.setPosition(position.x, position.y);
                blackHoleSprite.draw(spriteBatch);
                detectCollision(playScreen);
            }
            if (position.x < camera.position.x - (camera.viewportWidth)) {
                isRenderBlackHole = false;
            }
        }
    }

    private void detectCollision(PlayScreen playScreen) {
        if (blackHoleRect.overlaps(body.getRectBody())) {
            body.setUpdate(false);
            //Reduce ball radius gradually to zero
            body.setRadiusFactor(1.0f / (20 + 3 * radiusParam));
            body.init();
            radiusParam += 1;
            if (body.getBaseRadius() <= 8) {
                isRadiusZero = true;
            }
        }

        if (isRadiusZero)
            gameOver(playScreen);
    }

    private void gameOver(PlayScreen playScreen) {
        //Remove blackHole
        if ((StartScreen.isSound) && (!playScreen.isGameOver())) {
            Gdx.input.vibrate(100);
            game.assets.getDieSound().play(0.3f);
        }
        playScreen.setGameOver(true);
        body.setUpdate(false);
        isRenderBlackHole = false;
    }

    public Vector2 getPosition() {
        return position;
    }

    public boolean isRenderBlackHole() {
        return isRenderBlackHole;
    }

    public void setRenderBlackHole(boolean renderBlackHole) {
        isRenderBlackHole = renderBlackHole;
    }
}
