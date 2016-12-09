package com.sdsmdg.flingit.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
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

    private OrthographicCamera camera;
    private ShapeRenderer renderer;
    private Body body;
    private Array<Block> blocks;
    private int tempBlockNumber = 0;
    private Array<Vector3> blockParams;
    private boolean isUpdateCamera = false;
    private boolean isUpdateBodyRadius = false;
    private boolean isAttachCamera = false;
    private float cameraDisplacement;
    private int defaultLeftMarginX;
    private int defaultLeftMarginY;
    private boolean isCameraMoving = false;
    private Score score;
    public PlayScreen(FLINGitGame game) {
        score = new Score();
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        this.game = game;
        blocks = new Array<Block>();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.dimensions.getScreenWidth(), game.dimensions.getScreenHeight());


        defaultLeftMarginX = game.dimensions.getScreenWidth() / 7;
        defaultLeftMarginY = game.dimensions.getScreenHeight() / 3;

        body = new Body(game, this, score, camera, defaultLeftMarginX, defaultLeftMarginY);
        Gdx.app.log(TAG, "Width : " + game.dimensions.getScreenWidth() + "\nHeight : " +
                game.dimensions.getScreenHeight());
        Gdx.input.setInputProcessor(body);
        blockParams = game.dimensions.getBlockParams();


        for (int i = 0; i < Constants.BLOCK_COUNT; i++) {
            blocks.add(new Block(camera,body, i + 1, (int) (blockParams.get(i).x), (int) (blockParams.get(i).y),
                    (int) (blockParams.get(i).z), game));
        }
        camera.position.x = body.getPosition().x - defaultLeftMarginX + camera.viewportWidth / 2;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (isUpdateCamera) {
            if (camera.position.x - body.getPosition().x < camera.viewportWidth / 2 - defaultLeftMarginX) {
                isCameraMoving = true;
                camera.position.x += delta * (game.dimensions.getScreenWidth());
            } else {
                setUpdateCamera(false);
                isCameraMoving = false;
                //Now camera is updated to it's new position and it's time for new block to pop-up

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
            camera.position.x = body.getPosition().x + cameraDisplacement - defaultLeftMarginX + camera.viewportWidth / 2;
        }

        renderer.setProjectionMatrix(camera.combined);

        body.update(delta);
        score.updateScore();
        tempBlockNumber = 0;
        for (Block block : blocks) {
            if (camera.position.x - (camera.viewportWidth / 2) > block.getParamsBlock().x + block.getParamsBlock().y) {
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
        camera.update();
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

    public void setUpdateCamera(boolean updateCamera) {
        isUpdateCamera = updateCamera;
    }

    public void setUpdateBodyRadius(boolean updateBodyRadius) {
        isUpdateBodyRadius = updateBodyRadius;
    }

    public void setAttachCamera(boolean attachCamera, float displacement) {
        cameraDisplacement = displacement;
        isAttachCamera = attachCamera;
    }

    public boolean isCameraMoving() {
        return isCameraMoving;
    }
}