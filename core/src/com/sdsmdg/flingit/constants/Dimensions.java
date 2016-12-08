package com.sdsmdg.flingit.constants;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.sdsmdg.flingit.FLINGitGame;

import java.util.Random;

/**
 * Created by rahul on 6/12/16.
 */

public class Dimensions {

    private FLINGitGame game;
    private int screenWidth;
    private int screenHeight;
    private int gameHeight;
    private int gameWidth;
    private Array<Vector3> paramsBlockRecord;

    private int MIN_BLOCK_SPACING;
    private int MIN_BLOCK_HEIGHT;

    public Dimensions(FLINGitGame game, int screenWidth, int screenHeight) {
        this.game = game;
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.gameHeight = (int) (1.5 * screenHeight);
        this.gameWidth = 2 * screenWidth;
        this.MIN_BLOCK_SPACING = screenWidth / 10;
        this.MIN_BLOCK_HEIGHT = screenHeight / 4;
    }

    public int getScreenWidth() {
        return this.screenWidth;
    }

    public int getScreenHeight() {
        return this.screenHeight;
    }

    private Vector2 getRandom() {
        Vector2 vector2 = new Vector2();
        vector2.y = (getScreenWidth() / 20) * (new Random().nextInt(5) + 1);
        vector2.x = new Random().nextInt((int) ((getScreenWidth() / 2) - vector2.y) / 2) + getScreenWidth() / 8;

        return vector2;
    }

    public Array<Vector3> getBlockParams() {
        paramsBlockRecord = new Array<Vector3>();
        Array<Vector3> blockParams = new Array<Vector3>();
        //First Block
        Vector2 blockOne = getRandom();
        float baseValue = 3 * getScreenWidth() / 10;
        blockParams.add(new Vector3(MIN_BLOCK_SPACING + baseValue + blockOne.x, blockOne.y, MIN_BLOCK_HEIGHT + (getScreenHeight() / 10) * (new Random().nextInt(6))));

        //Second Block
        Vector2 blockTwo = getRandom();
        baseValue = blockParams.get(0).x + blockParams.get(0).y;
        blockParams.add(new Vector3(MIN_BLOCK_SPACING + baseValue + blockTwo.x, blockTwo.y, MIN_BLOCK_HEIGHT + (getScreenHeight() / 10) * (new Random().nextInt(6))));

        //Third Block
        Vector2 blockThree = getRandom();
        baseValue = blockParams.get(1).x + blockParams.get(1).y;
        blockParams.add(new Vector3(MIN_BLOCK_SPACING + baseValue + blockThree.x, blockThree.y, MIN_BLOCK_HEIGHT + (getScreenHeight() / 10) * (new Random().nextInt(6))));

        paramsBlockRecord = blockParams;
        //return x coordinate, width and height of the block in the order
        return blockParams;
    }

    //given a vector 3,output it's next value
    public Vector3 getNewBlockParams(int blockNumber) {
        Vector2 blockNew = getRandom();
        Vector3 newBlockParams = new Vector3();
        switch (blockNumber) {
            case 0: {
                //Repositioning of Block 1(required params are of Block 3)
                newBlockParams.x = paramsBlockRecord.get(2).x + paramsBlockRecord.get(2).y + MIN_BLOCK_SPACING + blockNew.x;
                newBlockParams.y = blockNew.y;
                newBlockParams.z = MIN_BLOCK_HEIGHT + (getScreenHeight() / 10) * (new Random().nextInt(6));
                break;
            }
            case 1: {
                //Repositioning of Block 2(required param are of Block 1)
                newBlockParams.x = paramsBlockRecord.get(0).x + paramsBlockRecord.get(0).y + MIN_BLOCK_SPACING + blockNew.x;
                newBlockParams.y = blockNew.y;
                newBlockParams.z = MIN_BLOCK_HEIGHT + (getScreenHeight() / 10) * (new Random().nextInt(6));
                break;
            }
            case 2: {
                //Repositioning of Block 3(required params are of Block 2)
                newBlockParams.x = paramsBlockRecord.get(1).x + paramsBlockRecord.get(1).y + MIN_BLOCK_SPACING + blockNew.x;
                newBlockParams.y = blockNew.y;
                newBlockParams.z = MIN_BLOCK_HEIGHT + (getScreenHeight() / 10) * (new Random().nextInt(6));
                break;
            }
        }
        //Update params record

        paramsBlockRecord.get(blockNumber).set(newBlockParams);

        return newBlockParams;
    }

    public int getGameHeight() {
        return this.gameHeight;
    }

    public int getGameWidth() {
        return this.gameWidth;
    }
}
