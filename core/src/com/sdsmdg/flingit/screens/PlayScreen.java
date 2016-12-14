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
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.sdsmdg.flingit.FLINGitGame;
import com.sdsmdg.flingit.constants.Constants;
import com.sdsmdg.flingit.controls.Score;
import com.sdsmdg.flingit.sprites.BlackHole;
import com.sdsmdg.flingit.sprites.Block;
import com.sdsmdg.flingit.sprites.Body;
import com.sdsmdg.flingit.sprites.Coin;
import com.sdsmdg.flingit.sprites.GameOverBar;

/**
 * Created by rahul on 6/12/16.
 */

public class PlayScreen implements Screen {

    private final static String TAG = PlayScreen.class.getSimpleName();
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
    private Coin coin;
    private BlackHole blackHole;
    private GameOverBar gameOverBar;
    private boolean isGameOver = false;

    public PlayScreen(FLINGitGame game) {
        glyphLayout = new GlyphLayout();
        score = new Score(this, game);

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

        gameOverBar = new GameOverBar(game, gameCam);
        body = new Body(game, this, gameOverBar, score, gameCam, defaultLeftMarginX, defaultLeftMarginY);
        Gdx.app.log(TAG, "Width : " + game.dimensions.getScreenWidth() + "\nHeight : " +
                game.dimensions.getScreenHeight());
        Gdx.input.setInputProcessor(body);
        blockParams = game.dimensions.getBlockParams();


        for (int i = 0; i < Constants.BLOCK_COUNT; i++) {
            blocks.add(new Block(this, gameCam, body, i + 1, (int) (blockParams.get(i).x), (int) (blockParams.get(i).y),
                    (int) (blockParams.get(i).z), game));
        }
        gameCam.position.x = body.getPosition().x - defaultLeftMarginX + gameCam.viewportWidth / 2;
        guiCam.position.x = body.getPosition().x - defaultLeftMarginX + guiCam.viewportWidth / 2;
        coin = new Coin(game, gameCam, score);
        blackHole = new BlackHole(game, gameCam, score);

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
                body.setBaseRadius((float) (body.getBaseRadius() + delta * game.dimensions.getScreenWidth() / 25.0));
            } else {
                setUpdateBodyRadius(false);
            }
        }

        if (isAttachCamera) {
            gameCam.position.x = body.getPosition().x + gameCamDisplacement - defaultLeftMarginX + gameCam.viewportWidth / 2;
        }

        renderer.setProjectionMatrix(gameCam.combined);

        body.update(delta);
        tempBlockNumber = 0;

        spriteBatch.setProjectionMatrix(guiCam.combined);
        if (isGameOver){
            getGameOverBar().render(renderer, spriteBatch, score.getHighScore());
        }

        spriteBatch.setProjectionMatrix(gameCam.combined);
        for (Block block : blocks) {
            block.render(spriteBatch, game, block, renderer, score.getScore());
        }
        if (body.getLine().isShow()){
            body.getLine().render(renderer);
        }
        renderer.begin();
        body.render(renderer);

        renderer.end();
        for (Block block : blocks) {
            if (gameCam.position.x - (gameCam.viewportWidth / 2) > block.getParamsBlock().x + block.getParamsBlock().y) {
                Vector3 newBlockParams = game.dimensions.getNewBlockParams(tempBlockNumber);
                block.reposition((int) (newBlockParams.x), (int) (newBlockParams.y), (int) (newBlockParams.z));
            }

            if (block.collide(block, body, body.getRectBody(), score)) {

            }
            tempBlockNumber++;
        }


        renderer.setProjectionMatrix(guiCam.combined);
        renderer.begin();
        coinBarRender();
        scoreBarRender();
        renderer.end();

        gameCam.update();

        spriteBatch.setProjectionMatrix(guiCam.combined);
        spriteBatch.begin();
        renderScore();
        renderCoins();
        spriteBatch.end();

        spriteBatch.setProjectionMatrix(gameCam.combined);
        spriteBatch.begin();
        if (coin.isRenderCoin()) {
            coin.render(this, body, spriteBatch);
        }
        if (blackHole.isRenderBlackHole()){
            blackHole.render(this,body,spriteBatch);
        }
        spriteBatch.end();
    }

    private void coinBarRender() {
        glyphLayout.setText(game.assets.getBitmapSmallFont(),
                score.getSilverCoinCount() + "/" + score.getGoldCoinCount());
        float layoutWidthMain = glyphLayout.width;
        float layoutHeight = glyphLayout.height;

        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Constants.COLOR_DEFAULT_BACKGROUND);
        renderer.rect(game.dimensions.getScreenWidth() - layoutWidthMain - 3 * game.dimensions.getScreenWidth() / 100,
                game.dimensions.getScreenHeight() - (float) (1.75 * layoutHeight), layoutWidthMain + 2 * game.dimensions.getScreenWidth() / 100, (float) (1.50 * layoutHeight));
    }

    private void renderCoins() {
        BitmapFont font = game.assets.getBitmapSmallFont();
        String main = score.getSilverCoinCount() + "/" + score.getGoldCoinCount();
        glyphLayout.setText(font, main);
        float layoutWidthMain = glyphLayout.width;
        float layoutHeight = glyphLayout.height;

        font.setColor(Constants.COLOR_SILVER);
        String coinToText = String.valueOf(score.getSilverCoinCount());
        font.draw(spriteBatch, coinToText, game.dimensions.getScreenWidth() - layoutWidthMain - game.dimensions.getScreenWidth() / 50,
                game.dimensions.getScreenHeight() - (float) (0.5 * layoutHeight));

        glyphLayout.setText(font, coinToText);
        float layoutWidth = glyphLayout.width;
        font.setColor(Color.WHITE);
        font.draw(spriteBatch, "/", game.dimensions.getScreenWidth() - (layoutWidthMain - layoutWidth) - game.dimensions.getScreenWidth() / 50,
                game.dimensions.getScreenHeight() - (float) (0.5 * layoutHeight));

        glyphLayout.setText(font, String.valueOf(score.getGoldCoinCount()));
        layoutWidth = glyphLayout.width;
        font.setColor(Constants.COLOR_GOLD);
        font.draw(spriteBatch, String.valueOf(score.getGoldCoinCount()), game.dimensions.getScreenWidth() - layoutWidth - game.dimensions.getScreenWidth() / 50,
                game.dimensions.getScreenHeight() - (float) (0.5 * layoutHeight));


    }

    private void scoreBarRender() {
        glyphLayout.setText(game.assets.getBitmapSmallFont(), String.valueOf(score.getScore()));
        float layoutWidthMain = glyphLayout.width;
        float layoutHeight = glyphLayout.height;

        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(Constants.COLOR_DEFAULT_BACKGROUND);
        renderer.rect(game.dimensions.getScreenWidth() / 100,
                game.dimensions.getScreenHeight() - (float) (1.75 * layoutHeight), layoutWidthMain + 2 * game.dimensions.getScreenWidth() / 100, (float) (1.50 * layoutHeight));
    }

    private void renderScore() {
        BitmapFont font = game.assets.getBitmapSmallFont();
        font.setColor(Color.WHITE);
        String scoreToText = String.valueOf(score.getScore());
        glyphLayout.setText(font, scoreToText);
        float layoutWidth = glyphLayout.width;
        float layoutHeight = glyphLayout.height;
        font.draw(spriteBatch, scoreToText, game.dimensions.getScreenWidth() / 50 ,
                game.dimensions.getScreenHeight() - (float) 0.5 * layoutHeight);

//        font = game.assets.getBitmapMediumFont();
//        String highScoreToText = String.valueOf(score.getHighScore());
//        glyphLayout.setText(font, highScoreToText);
//        layoutWidth = glyphLayout.width;
//        layoutHeight = glyphLayout.height;
//        font.draw(spriteBatch, highScoreToText, (int) 0.5 * layoutWidth,
//                game.dimensions.getScreenHeight() - layoutHeight);
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

    public Coin getCoin() {
        return coin;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public void setGameOver(boolean gameOver) {
        isGameOver = gameOver;
    }

    public GameOverBar getGameOverBar() {
        return gameOverBar;
    }

    public BlackHole getBlackHole() {
        return blackHole;
    }
}