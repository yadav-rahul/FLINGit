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

    public PlayScreen(FLINGitGame game) {
        renderer = new ShapeRenderer();
        renderer.setAutoShapeType(true);
        this.game = game;
        blocks = new Array<Block>();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, game.dimensions.getScreenWidth(), game.dimensions.getScreenHeight());
        body = new Body(game, camera, 200, 500);
        Gdx.app.log(TAG, "Width : " + game.dimensions.getScreenWidth() + "\nHeight : " +
                game.dimensions.getScreenHeight());
        Gdx.input.setInputProcessor(body);
        blockParams = game.dimensions.getBlockParams();


        for (int i = 0; i < Constants.BLOCK_COUNT; i++) {
            blocks.add(new Block((int) (blockParams.get(i).x), (int) (blockParams.get(i).y),
                    (int) (blockParams.get(i).z), game.dimensions.getScreenWidth()));
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if (isUpdateCamera) {
            if (camera.position.x - body.getPosition().x < (game.dimensions.getScreenWidth() / 5)) {
                camera.position.x += delta * (game.dimensions.getScreenWidth() / 3);
            } else {
                isUpdateCamera = false;
            }
        }

        renderer.setProjectionMatrix(camera.combined);

        body.update(delta);
        tempBlockNumber = 0;
        for (Block block : blocks) {
            if (camera.position.x - (camera.viewportWidth / 2) > block.getParamsBlock().x + block.getParamsBlock().y) {
                Vector3 newBlockParams = game.dimensions.getNewBlockParams(tempBlockNumber);
                block.reposition((int) (newBlockParams.x), (int) (newBlockParams.y), (int) (newBlockParams.z));
            }

            if (block.collide(this, body, body.getRectBody())) {
                game.setScreen(new PlayScreen(game));
            }
            tempBlockNumber++;
        }
        renderer.begin();
        body.render(renderer);
        for (Block block : blocks) {
            block.render(renderer);
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
}