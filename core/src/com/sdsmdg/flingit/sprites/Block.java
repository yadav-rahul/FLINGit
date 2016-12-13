package com.sdsmdg.flingit.sprites;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.sdsmdg.flingit.FLINGitGame;
import com.sdsmdg.flingit.constants.Constants;
import com.sdsmdg.flingit.controls.Score;
import com.sdsmdg.flingit.screens.PlayScreen;
import com.sdsmdg.flingit.screens.StartScreen;

/**
 * Created by rahul on 7/12/16.
 */

public class Block {

    private final static String TAG = Block.class.getSimpleName();
    private int id;
    private int topBlockMargin;

    private Vector3 paramsBottomBlock, paramsTopBlock;
    private Rectangle bottomRectBlock, topRectBlock, topRectLine;
    private PlayScreen playScreen;
    private OrthographicCamera camera;
    private Body body;
    private Array<Vector3> paramsBlockRecord;
    private FLINGitGame game;
    private ShapeRenderer renderer;

    public Block(PlayScreen playScreen, OrthographicCamera camera, Body body, int id, int x, int width, int height, FLINGitGame game) {
        this.game = game;
        this.playScreen = playScreen;
        this.paramsBlockRecord = game.dimensions.getParamsBlockRecord();
        this.body = body;
        this.camera = camera;
        this.id = id;
        topBlockMargin = game.dimensions.getScreenWidth() / 40;

        paramsBottomBlock = new Vector3(x, width, height - topBlockMargin);
        paramsTopBlock = new Vector3(x - topBlockMargin, width + 2 * topBlockMargin, topBlockMargin);

        bottomRectBlock = new Rectangle(x, 0, width, height - topBlockMargin);
        topRectBlock = new Rectangle(x - topBlockMargin, height - topBlockMargin, width + 2 * topBlockMargin, topBlockMargin);
        topRectLine = new Rectangle(x - topBlockMargin, height - (topBlockMargin / 2), width + 2 * topBlockMargin, topBlockMargin / 2);
    }


    public void update(float delta) {

    }

    public void render(FLINGitGame game, Block block, ShapeRenderer renderer, int score) {
        this.renderer = renderer;
        switch (body.getCurrentBlockId()) {
            case 0: {
                //render only one block
                if (block.getId() == 1) {
                    renderBlocks(renderer);
                }
                break;
            }
            case 1: {
                if (block.getId() == 1 || block.getId() == 2) {
                    renderBlocks(renderer);
                } else if (block.getId() == 3 && (paramsBlockRecord.get(2).x < paramsBlockRecord.get(0).x)) {
                    renderBlocks(renderer);
                }
                break;
            }
            case 2: {
                if (block.getId() == 2 || block.getId() == 3) {
                    renderBlocks(renderer);
                } else if (block.getId() == 1 && (paramsBlockRecord.get(0).x < paramsBlockRecord.get(1).x)) {
                    renderBlocks(renderer);
                }
                break;
            }
            case 3: {
                if (block.getId() == 3 || block.getId() == 1) {
                    renderBlocks(renderer);
                } else if (block.getId() == 2 && (paramsBlockRecord.get(1).x < paramsBlockRecord.get(2).x)) {
                    renderBlocks(renderer);
                }
                break;
            }

        }

    }

    private void renderBlocks(ShapeRenderer renderer) {

        if (!playScreen.isGameOver()) {
            renderer.set(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(Constants.COLOR_BOTTOM);
            renderer.rect(paramsBottomBlock.x, 0, paramsBottomBlock.y, paramsBottomBlock.z);
            renderer.setColor(Constants.COLOR_TOP);
            renderer.rect(paramsTopBlock.x, paramsBottomBlock.z, paramsTopBlock.y, paramsTopBlock.z);
        }
    }

    public void reposition(int x, int width, int height) {
        paramsBottomBlock.set(x, width, height - topBlockMargin);
        paramsTopBlock.set(x - topBlockMargin, width + 2 * topBlockMargin, topBlockMargin);
        bottomRectBlock.set(x, 0, width, height - topBlockMargin);
        topRectBlock.set(x - topBlockMargin, height - topBlockMargin, width + 2 * topBlockMargin, topBlockMargin);
        topRectLine.set(x - topBlockMargin, height - (topBlockMargin / 2), width + 2 * topBlockMargin, topBlockMargin / 2);
    }

    public Vector3 getParamsBlock() {
        return paramsBottomBlock;
    }

    public int getId() {
        return id;
    }

    public boolean collide(Block block, Body body, Rectangle rectBody, Score score) {

        if (score.getScore() == 0) {
            if (block.getId() == 1) {
                return detectCollision(block, rectBody, score);
            }
        } else if ((score.getModuloThreeScore() == 0 && block.getId() != 2) || ((score.getModuloThreeScore() == 1 && block.getId() != 3)) ||
                (score.getModuloThreeScore() == 2 && block.getId() != 1)) {
            return detectCollision(block, rectBody, score);
        }

        return false;
    }

    private boolean detectCollision(Block block, Rectangle rectBody, Score score) {


        if (rectBody.overlaps(topRectLine) && body.getVelocity().y >= 0) {

            if (body.isInAir()) {
                if (StartScreen.isSound)
                    game.assets.getPipeLandSound().play(0.5f);
                body.setInAir(false);
                playScreen.setUpdateCamera(true);
                score.setCollide(true, block.getId());
                body.setCurrentBlockId(block.getId());
                score.updateScore();
            }

            //Set the current position of the block to that position
            body.getPosition().y = topRectLine.getY() + topRectLine.getHeight() + body.getBaseRadius();
            body.getVelocity().y = 0;
            body.getVelocity().x = 0;
            body.setUpdate(false);

            return false;
        } else if (rectBody.overlaps(bottomRectBlock) || rectBody.overlaps(topRectBlock)) {
            if (StartScreen.isSound && (!playScreen.isGameOver())) {
                game.assets.getDieSound().play(0.5f);
            }

            playScreen.setGameOver(true);
            body.setUpdate(false);
            return true;
        }
        return false;
    }
}