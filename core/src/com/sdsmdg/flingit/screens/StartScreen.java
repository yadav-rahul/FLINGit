package com.sdsmdg.flingit.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.sdsmdg.flingit.FLINGitGame;
import com.sdsmdg.flingit.constants.Assets;
import com.sdsmdg.flingit.sprites.Bar;

/**
 * Created by rahul on 11/12/16.
 */

public class StartScreen implements Screen {

    private FLINGitGame game;
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private Assets assets;
    private ShapeRenderer renderer;
    private Bar bar;
    private GlyphLayout glyphLayout;

    public StartScreen(FLINGitGame game, Assets assets) {
        this.assets = assets;
        this.game = game;

        glyphLayout = new GlyphLayout();
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.dimensions.getScreenWidth(), game.dimensions.getScreenHeight());
        spriteBatch.setProjectionMatrix(camera.combined);

        bar = new Bar(game, 0, game.dimensions.getScreenHeight() / 4, game.dimensions.getScreenWidth(),
                game.dimensions.getScreenHeight() / 7, camera);
        Gdx.input.setInputProcessor(bar);

    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor((float) 66.0 / 255, (float) 66.0 / 255, (float) 100.0 / 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setProjectionMatrix(camera.combined);


        renderer.begin();
        bar.render(renderer);

        renderer.end();
        spriteBatch.begin();

        renderText("FLINGIT", game.assets.getBitmapTitleFont(), game.dimensions.getScreenWidth() / 2,
                6 * game.dimensions.getScreenHeight() / 9);

        renderText("PLAY", game.assets.getBitmapSmallFont(), game.dimensions.getScreenWidth() / 2,
                bar.getPosition().y + (bar.getDimensions().y / 2));
        spriteBatch.end();

    }

    private void renderText(String text, BitmapFont font, float x, float y) {
        font.setColor(Color.WHITE);
        glyphLayout.setText(font, text);
        float layoutWidth = glyphLayout.width;
        float layoutHeight = glyphLayout.height;
        font.draw(spriteBatch, text, x - layoutWidth / 2, y + layoutHeight / 2);


    }

    @Override
    public void show() {

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
        spriteBatch.dispose();
        renderer.dispose();
    }
}
