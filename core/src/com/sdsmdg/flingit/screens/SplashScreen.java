package com.sdsmdg.flingit.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sdsmdg.flingit.FLINGitGame;
import com.sdsmdg.flingit.constants.Assets;

/**
 * Created by rahul on 10/12/16.
 */

public class SplashScreen implements Screen {

    private FLINGitGame game;
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private Sprite groupLogo;
    private long time;
    private Assets assets;
    private Thread thread;

    public SplashScreen(FLINGitGame game, Assets assets) {
        this.assets = assets;
        this.game = game;
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.dimensions.getScreenWidth(), game.dimensions.getScreenHeight());
        spriteBatch.setProjectionMatrix(camera.combined);

        if (!game.playServices.isSignedIn())
            game.playServices.signIn();

        groupLogo = assets.getGroupLogoSprite();
        groupLogo.setPosition(game.dimensions.getScreenWidth() / 4, game.dimensions.getScreenHeight() / 2);
        groupLogo.setSize(game.dimensions.getScreenWidth() / 2, 3 * game.dimensions.getScreenWidth() / 16);
    }

    @Override
    public void show() {
        //Called when this screen becomes the current screen
        time = System.currentTimeMillis();

        //load all fonts and remaining sprites to be used in separate thread

        new Thread(new Runnable() {
            @Override
            public void run() {
                // do something important here, asynchronously to the rendering thread

                // post a Runnable to the rendering thread that processes the result
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        assets.loadGameScreenSprites();
                        assets.loadAllFonts();
                        Gdx.app.log("TAG", "Time : " + (System.currentTimeMillis() - time)/1000.0 );
                    }
                });
            }
        }).start();


    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        groupLogo.draw(spriteBatch);
        spriteBatch.end();
        if (System.currentTimeMillis() >= time + 5000) {
            game.setScreen(new StartScreen(game, assets));
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
