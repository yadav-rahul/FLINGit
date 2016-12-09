package com.sdsmdg.flingit.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.sdsmdg.flingit.FLINGitGame;
import com.sdsmdg.flingit.constants.Constants;
import com.sdsmdg.flingit.controls.Score;
import com.sdsmdg.flingit.sprites.Block;
import com.sdsmdg.flingit.sprites.Body;

/**
 * Created by rahul on 6/12/16.
 */

public class PlayScreen implements Screen {

    private final static String TAG = "com.sdsmdg.flingit.screens";
    private FLINGitGame game;

    private OrthographicCamera gameCam;
    private OrthographicCamera guiCam;
    private ShapeRenderer renderer;
    private Body body;
    private Array<Block> blocks;
    private int tempBlockNumber = 0;
    private Array<Vector3> blockParams;
    private boolean isUpdateCamera = false;
    private boolean isUpdateBodyRadius = false;
    private boolean isAttachCamera = false;
    private float gameCamDisplacement;
    private int defaultLeftMarginX;
    private int defaultLeftMarginY;
    private boolean isCameraMoving = false;
    private Score score;
    private GlyphLayout glyphLayout;
    private SpriteBatch spriteBatch;

    public PlayScreen(FLINGitGame game) {
        glyphLayout = new GlyphLayout();
        score = new Score();

        spriteBatch = new SpriteBatch();

        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        this.game = game;
        blocks = new Array<Block>();
        gameCam = new OrthographicCamera();
        guiCam = new OrthographicCamera();
        gameCam.setToOrtho(false, game.dimensions.getScreenWidth(), game.dimensions.getScreenHeight());
        guiCam.setToOrtho(false, game.dimensions.getScreenWidth(), game.dimensions.getScreenHeight());

        defaultLeftMarginX = game.dimensions.getScreenWidth() / 7;
        defaultLeftMarginY = game.dimensions.getScreenHeight() / 3;

        body = new Body(game, this, score, gameCam, defaultLeftMarginX, defaultLeftMarginY);
        Gdx.app.log(TAG, "Width : " + game.dimensions.getScreenWidth() + "\nHeight : " +
                game.dimensions.getScreenHeight());
        Gdx.input.setInputProcessor(body);
        blockParams = game.dimensions.getBlockParams();


        for (int i = 0; i < Constants.BLOCK_COUNT; i++) {
            blocks.add(new Block(gameCam, body, i + 1, (int) (blockParams.get(i).x), (int) (blockParams.get(i).y),
                    (int) (blockParams.get(i).z), game));
        }
        gameCam.position.x = body.getPosition().x - defaultLeftMarginX + gameCam.viewportWidth / 2;
        guiCam.position.x = body.getPosition().x - defaultLeftMarginX + guiCam.viewportWidth / 2;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (isUpdateCamera) {
            if (gameCam.position.x - body.getPosition().x < gameCam.viewportWidth / 2 - defaultLeftMarginX) {
                isCameraMoving = true;
                gameCam.position.x += delta * (game.dimensions.getScreenWidth());
            } else {
                setUpdateCamera(false);
                isCameraMoving = false;
                //Now gameCam is updated to it's new position and it's time for new block to pop-up

            }
        }

        if (isUpdateBodyRadius) {
            if (body.getBaseRadius() < (1.0f / Constants.RADIUS_FACTOR * (game.dimensions.getScreenWidth()))) {
                body.setBaseRadius((float) (body.getBaseRadius() + delta * game.dimensions.getScreenWidth() / 100.0));
            } else {
                setUpdateBodyRadius(false);
            }
        }

        if (isAttachCamera) {
            gameCam.position.x = body.getPosition().x + gameCamDisplacement - defaultLeftMarginX + gameCam.viewportWidth / 2;
        }

        renderer.setProjectionMatrix(gameCam.combined);

        body.update(delta);
        score.updateScore();
        tempBlockNumber = 0;
        for (Block block : blocks) {
            if (gameCam.position.x - (gameCam.viewportWidth / 2) > block.getParamsBlock().x + block.getParamsBlock().y) {
                Vector3 newBlockParams = game.dimensions.getNewBlockParams(tempBlockNumber);
                block.reposition((int) (newBlockParams.x), (int) (newBlockParams.y), (int) (newBlockParams.z));
            }

            if (block.collide(this, block, body, body.getRectBody(), score)) {
                game.setScreen(new PlayScreen(game));
            }
            tempBlockNumber++;
        }

        //Gdx.app.log(TAG, "Score : " + score.getScore());
        renderer.begin();
        body.render(renderer);
        for (Block block : blocks) {
            block.render(game, block, renderer, score.getScore());
        }
        renderer.end();

        gameCam.update();

        spriteBatch.setProjectionMatrix(guiCam.combined);
        spriteBatch.begin();
        renderScore();
        spriteBatch.end();
    }

    private void renderScore() {
        BitmapFont smallFont = game.assets.getBitmapSmallFont();
        String scoreToText = String.valueOf(score.getScore());
        glyphLayout.setText(smallFont, scoreToText);
        float layoutWidth = glyphLayout.width;
        float layoutHeight = glyphLayout.height;
        smallFont.draw(spriteBatch, scoreToText, game.dimensions.getScreenWidth() - layoutWidth,
                game.dimensions.getScreenHeight() - (float) 0.1 * layoutHeight);
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
        spriteBatch.dispose();
    }

    public void setUpdateCamera(boolean updateCamera) {
        isUpdateCamera = updateCamera;
    }

    public void setUpdateBodyRadius(boolean updateBodyRadius) {
        isUpdateBodyRadius = updateBodyRadius;
    }

    public void setAttachCamera(boolean attachCamera, float displacement) {
        gameCamDisplacement = displacement;
        isAttachCamera = attachCamera;
    }

    public boolean isCameraMoving() {
        return isCameraMoving;
    }
}