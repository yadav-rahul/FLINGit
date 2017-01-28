package com.sdsmdg.flingit.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private boolean isRenderArrow = true;
    private Sprite arrowSprite, arrowDarkSprite, arrow;
    private boolean isRadiusZero = false;
    private int radiusParam = 0;

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

        arrowSprite = game.assets.getArrowDownSprite();
        arrowSprite.setSize(3 * topBlockMargin / 2, topBlockMargin);

        arrowDarkSprite = game.assets.getArrowDownDarkSprite();
        arrowDarkSprite.setSize(3 * topBlockMargin / 2, topBlockMargin);

        arrow = arrowSprite;
    }


    public void update(float delta) {

    }

    public void render(SpriteBatch spriteBatch, FLINGitGame game, Block block, ShapeRenderer renderer, int score) {
        this.renderer = renderer;
        switch (body.getCurrentBlockId()) {
            case 0: {
                //render only one block
                if (block.getId() == 1) {
                    renderBlocks(renderer, spriteBatch);
                }
                break;
            }
            case 1: {
                if (block.getId() == 1 || block.getId() == 2) {
                    renderBlocks(renderer, spriteBatch);
                } else if (block.getId() == 3 && (paramsBlockRecord.get(2).x < paramsBlockRecord.get(0).x)) {
                    renderBlocks(renderer, spriteBatch);
                }
                break;
            }
            case 2: {
                if (block.getId() == 2 || block.getId() == 3) {
                    renderBlocks(renderer, spriteBatch);
                } else if (block.getId() == 1 && (paramsBlockRecord.get(0).x < paramsBlockRecord.get(1).x)) {
                    renderBlocks(renderer, spriteBatch);
                }
                break;
            }
            case 3: {
                if (block.getId() == 3 || block.getId() == 1) {
                    renderBlocks(renderer, spriteBatch);
                } else if (block.getId() == 2 && (paramsBlockRecord.get(1).x < paramsBlockRecord.get(2).x)) {
                    renderBlocks(renderer, spriteBatch);
                }
                break;
            }

        }

    }

    private void renderBlocks(ShapeRenderer renderer, SpriteBatch spriteBatch) {

        if (!playScreen.isGameOver()) {
            renderer.begin();
            renderer.set(ShapeRenderer.ShapeType.Filled);
            renderer.setColor(Constants.COLOR_BOTTOM);
            renderer.rect(paramsBottomBlock.x, 0, paramsBottomBlock.y, paramsBottomBlock.z);
            renderer.setColor(Constants.COLOR_TOP);
            renderer.rect(paramsTopBlock.x, paramsBottomBlock.z, paramsTopBlock.y, paramsTopBlock.z);
            renderer.end();

            arrowSprite.setPosition(paramsBottomBlock.x + paramsBottomBlock.y / 2 - arrowSprite.getWidth() / 2, paramsBottomBlock.z);
            arrowDarkSprite.setPosition(paramsBottomBlock.x + paramsBottomBlock.y / 2 - arrowDarkSprite.getWidth() / 2, paramsBottomBlock.z);
            spriteBatch.begin();
            if (this.isRenderArrow){
                arrow.draw(spriteBatch);
            }
            spriteBatch.end();
        }
    }

    public void reposition(int x, int width, int height) {
        this.isRenderArrow = true;
        this.setArrow(arrowSprite);
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

            //Set the current position of the block to that position
            body.getVelocity().y = 0;
            body.getPosition().y = topRectLine.getY() + topRectLine.getHeight() + body.getBaseRadius();
            body.getVelocity().x = 0;

            if (body.isInAir()) {
                if (StartScreen.isSound)
                    game.assets.getPipeLandSound().play(0.3f);
                body.setInAir(false);
                playScreen.setUpdateCamera(true);
                score.setCollide(true, block.getId());
                body.setCurrentBlockId(block.getId());
                if (body.getPosition().x > block.getParamsBlock().x + block.getParamsBlock().y / 2 - 3 * topBlockMargin / 4
                        && body.getPosition().x < block.getParamsBlock().x + block.getParamsBlock().y / 2 + 3 * topBlockMargin / 4){
                    score.updateScore(4);
                    block.setArrow(arrowDarkSprite);
                }else{
                    //Remove sprite from this block
                    this.isRenderArrow = false;
                    score.updateScore(1);
                }

            }
            body.setUpdate(false);

            return false;
        } else if (rectBody.overlaps(bottomRectBlock) || rectBody.overlaps(topRectBlock)) {
            //Set the current position of the block to that position
            body.setUpdate(false);
            //Reduce ball radius gradually to zero
            body.setRadiusFactor(1.0f / (20 + 5 * radiusParam));
            body.init();
            radiusParam += 2;
            if (body.getBaseRadius() <= 8) {
                isRadiusZero = true;
            }

            if (isRadiusZero)
                gameOver(playScreen);
            return true;
        }
        return false;
    }
    private void gameOver(PlayScreen playScreen) {
        if (StartScreen.isSound && (!playScreen.isGameOver())) {
            Gdx.input.vibrate(100);
            game.assets.getDieSound().play(0.3f);
        }

        playScreen.setGameOver(true);
        body.setUpdate(false);
    }

    public void setArrow(Sprite arrow) {
        this.arrow = arrow;
    }

    public Rectangle getTopRectBlock() {
        return topRectBlock;
    }
}