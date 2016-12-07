package com.sdsmdg.flingit.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sdsmdg.flingit.FLINGitGame;
import com.sdsmdg.flingit.sprites.Body;

/**
 * Created by rahul on 6/12/16.
 */

public class PlayScreen implements Screen {

    private final static String TAG = "com.sdsmdg.flingit.screens";
    private FLINGitGame game;
    private OrthographicCamera camera;
    ShapeRenderer renderer;
    Body body;
    public PlayScreen(FLINGitGame game) {
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.dimensions.getScreenWidth(), game.dimensions.getScreenHeight());

        body = new Body(game,camera, 100, 500);
        Gdx.app.log(TAG, "Width : " + game.dimensions.getScreenWidth() + "\nHeight : " +
            game.dimensions.getScreenHeight());
        Gdx.input.setInputProcessor(body);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.setProjectionMatrix(camera.combined);
        body.update(delta);

        renderer.begin(ShapeRenderer.ShapeType.Filled);
        body.render(renderer);
        renderer.end();
        game.batch.begin();

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