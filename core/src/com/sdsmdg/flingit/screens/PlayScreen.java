package com.sdsmdg.flingit.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.sdsmdg.flingit.FLINGitGame;

/**
 * Created by rahul on 6/12/16.
 */

public class PlayScreen implements Screen {

    private FLINGitGame game;
    private Texture texture;
    private OrthographicCamera camera;

    public PlayScreen(FLINGitGame game) {
        this.game = game;
        texture = new Texture("badlogic.jpg");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.dimensions.getScreenWidth(), game.dimensions.getScreenHeight());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        game.batch.draw(texture, 0, 0);
        game.batch.end();
    }

    @Override
    public void resize(int width, int height) {
        //Change width and height dynamically
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
        game.batch.dispose();
    }
}