package com.sdsmdg.flingit.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.sdsmdg.flingit.FLINGitGame;
import com.sdsmdg.flingit.constants.Assets;
import com.sdsmdg.flingit.sprites.Bar;

/**
 * Created by rahul on 11/12/16.
 */

public class StartScreen extends InputAdapter implements Screen {

    private FLINGitGame game;
    private OrthographicCamera camera;
    private SpriteBatch spriteBatch;
    private Assets assets;
    private ShapeRenderer renderer;
    private int width, height;
    private Bar bar;
    private GlyphLayout glyphLayout;
    public static boolean isSound;
    private Sprite aboutUs, leaderboard, achievement, soundOn, soundOff, sound;

    public StartScreen(FLINGitGame game, Assets assets) {
        isSound = true;
        this.assets = assets;
        this.game = game;

        width = game.dimensions.getScreenWidth();
        height = game.dimensions.getScreenHeight();

        glyphLayout = new GlyphLayout();
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        spriteBatch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, width, height);
        spriteBatch.setProjectionMatrix(camera.combined);

        bar = new Bar(game, 0, height / 4, width,
                height / 7, camera);

        soundOn = assets.getSoundOnSprite();
        soundOn.setSize(width / 10, width / 10);
        soundOn.setPosition(width / 20, height - width / 30 - soundOn.getHeight());

        achievement = assets.getAchievementSprite();
        achievement.setSize(width / 10, width / 10 - 6);
        achievement.setPosition(6 * width / 20, height - width / 30 - achievement.getHeight() - 3);

        leaderboard = assets.getLeaderboardSprite();
        leaderboard.setSize(width / 10, width / 10 - 3);
        leaderboard.setPosition(55 * width / 100, height - width / 30 - leaderboard.getHeight());

        aboutUs = assets.getAboutUsSprite();
        aboutUs.setSize(width / 10, width / 10 - 6);
        aboutUs.setPosition(8 * width / 10, height - width / 30 - aboutUs.getHeight() - 3);

        soundOff = assets.getSoundOffSprite();
        soundOff.setSize(width / 10, width / 10);
        soundOff.setPosition(width / 20, height - width / 30 - soundOn.getHeight());
        sound = soundOn;
        Gdx.input.setInputProcessor(this);
    }


    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor((float) 66.0 / 255, (float) 66.0 / 255, (float) 100.0 / 255, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setProjectionMatrix(camera.combined);


        renderer.begin();
        bar.render(renderer, 0);

        renderer.end();
        spriteBatch.begin();
        sound.draw(spriteBatch);
        achievement.draw(spriteBatch);
        leaderboard.draw(spriteBatch);
        aboutUs.draw(spriteBatch);

        renderText("FLINGIT", game.assets.getBitmapTitleFont(), width / 2,
                7 * height / 10);

        renderText("PLAY", game.assets.getBitmapSmallFont(), width / 2,
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

    private void changeSound() {
        if (isSound) {
            //change sound icon as well as turn of sound
            isSound = false;
            sound = soundOff;
        } else {
            isSound = true;
            sound = soundOn;
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {

        Vector3 worldClick = camera.unproject(new Vector3(screenX, screenY, 0));

        if (worldClick.y > (height - (4 * width / 30))) {
            if (worldClick.x > width / 20 && worldClick.x < 3 * width / 20) {
                //Sound button clicked
                if (isSound)
                    game.assets.getPressSound().play(0.5f);
                changeSound();
            } else if (worldClick.x > 6 * width / 20 && worldClick.x < 4 * width / 10) {
                //achievement button clicked
                if (isSound)
                    game.assets.getPressSound().play(0.5f);
                game.playServices.showAchievement();
            } else if (worldClick.x > 55 * width / 100 && worldClick.x < 65 * width / 100) {
                //leaderboard button clicked
                if (isSound)
                    game.assets.getPressSound().play(0.5f);
                game.playServices.showScore();
            } else if (worldClick.x > 8 * width / 10 && worldClick.x < 9 * width / 10) {
                //aboutUs Button Clicked
                if (isSound)
                    game.assets.getPressSound().play(0.5f);
                game.aboutUs.onClick();
            }
        }

        bar.touchDown(screenX, screenY, pointer, button);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        bar.touchUp();
        return true;
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
