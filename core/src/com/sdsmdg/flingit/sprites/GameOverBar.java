package com.sdsmdg.flingit.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.sdsmdg.flingit.FLINGitGame;
import com.sdsmdg.flingit.screens.PlayScreen;
import com.sdsmdg.flingit.screens.StartScreen;

/**
 * Created by rahul on 13/12/16.
 */

public class GameOverBar {

    private final static String TAG = Bar.class.getSimpleName();

    private FLINGitGame game;
    private OrthographicCamera camera;
    private int height, width;
    private Bar restartBar, backBar;
    private GlyphLayout glyphLayout;
    private SpriteBatch spriteBatch;

    public GameOverBar(FLINGitGame game, OrthographicCamera camera) {
        glyphLayout = new GlyphLayout();
        width = game.dimensions.getScreenWidth();
        height = game.dimensions.getScreenHeight();
        this.game = game;
        this.camera = camera;
    }


    public void render(ShapeRenderer renderer, SpriteBatch spriteBatch, int highScore) {
        this.spriteBatch = spriteBatch;
        renderer.begin(ShapeRenderer.ShapeType.Filled);
        Gdx.graphics.getGL20().glEnable(GL20.GL_BLEND);
        renderer.setColor(0, 0, 0, 0.5f);
        renderer.rect(camera.position.x - camera.viewportWidth / 2, 0, game.dimensions.getScreenWidth(),
                game.dimensions.getScreenHeight());

        backBar = new Bar(game, camera.position.x + width / 50 - camera.viewportWidth / 2,
                (int) (height * (1.0 / 5 + 1.0 / 7 + 1.0 / 50)), width - 2 * width / 50, height / 7, camera);

        restartBar = new Bar(game, camera.position.x + width / 50 - camera.viewportWidth / 2, height / 5, width - 2 * width / 50,
                height / 7, camera);

        restartBar.render(renderer, 1);
        backBar.render(renderer, 1);
        renderer.end();
        spriteBatch.begin();
        renderText("BEST : " + highScore, game.assets.getbitmapTinyFont(), width / 2,
                3 * height / 4);

        renderText("BACK", game.assets.getBitmapSmallFont(), width / 2,
                (float) (height * (2.0 / 5 + 3.0 / 7 + 2.0 / 50) / 2));

        renderText("RESTART", game.assets.getBitmapSmallFont(), width / 2,
                (float) (height * (2.0 / 5 + 1.0 / 7) / 2));
        spriteBatch.end();
    }

    private void renderText(String text, BitmapFont font, float x, float y) {
        font.setColor(Color.WHITE);
        glyphLayout.setText(font, text);
        float layoutWidth = glyphLayout.width;
        float layoutHeight = glyphLayout.height;
        font.draw(spriteBatch, text, x - layoutWidth / 2, y + layoutHeight / 2);
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 worldClick = camera.unproject(new Vector3(screenX, screenY, 0));
        if (worldClick.y > height / 5 && worldClick.y < height * (1.0 / 5 + 1.0 / 7)) {
            //restart button clicked
            if (StartScreen.isSound)
                game.assets.getPressSound().play(0.5f);
            game.setScreen(new PlayScreen(game));

        } else if (worldClick.y < height * (1.0 / 5 + 2.0 / 7 + 1.0 / 50) && worldClick.y > height * (1.0 / 5 + 1.0 / 7 + 1.0 / 50)) {
            //back button clicked
            if (StartScreen.isSound)
                game.assets.getPressSound().play(0.5f);
            game.setScreen(new StartScreen(game, game.assets));

        }

        return true;
    }
}
