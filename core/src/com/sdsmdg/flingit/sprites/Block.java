package com.sdsmdg.flingit.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by rahul on 7/12/16.
 */

public class Block {

    private int topBlockMargin;
    private final Color COLOR_BOTTOM = Color.OLIVE;
    private final Color COLOR_TOP = Color.GOLD;
    private Vector3 paramsBottomBlock, paramsTopBlock;
    private Rectangle bottomRectBlock, topRectBlock, topRectLine;

    public Block(int x, int width, int height, int screenWidth) {
        topBlockMargin = screenWidth / 40;

        paramsBottomBlock = new Vector3(x, width, height - topBlockMargin);
        paramsTopBlock = new Vector3(x - topBlockMargin,  width + 2 * topBlockMargin, topBlockMargin);

        bottomRectBlock = new Rectangle(x, 0, width, height - topBlockMargin);
        topRectBlock = new Rectangle(x - topBlockMargin, height - topBlockMargin, width + 2 * topBlockMargin, topBlockMargin);
        topRectLine = new Rectangle(x - topBlockMargin, height - (topBlockMargin / 2), width + 2 * topBlockMargin, topBlockMargin / 2);
    }


    public void update(float delta) {

    }

    public void render(ShapeRenderer renderer) {

        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(COLOR_BOTTOM);
        renderer.rect(paramsBottomBlock.x, 0, paramsBottomBlock.y, paramsBottomBlock.z);
        renderer.setColor(COLOR_TOP);
        renderer.rect(paramsTopBlock.x, paramsBottomBlock.z, paramsTopBlock.y, paramsTopBlock.z);
    }

    public void reposition(int x, int width, int height) {
        paramsBottomBlock.set(x, width, height - topBlockMargin);
        paramsTopBlock.set(x - topBlockMargin,  width + 2 * topBlockMargin, topBlockMargin);
        bottomRectBlock.set(x, 0, width, height - topBlockMargin);
        topRectBlock.set(x - topBlockMargin, height - topBlockMargin, width + 2 * topBlockMargin, topBlockMargin);
        topRectLine.set(x - topBlockMargin, height - (topBlockMargin / 2), width + 2 * topBlockMargin, topBlockMargin / 2);
    }

    public Vector3 getParamsBlock() {
        return paramsBottomBlock;
    }

    public boolean collide(Body body, Rectangle rectBody) {
        if (rectBody.overlaps(topRectLine)) {
            //Set the current position of the block to that position.
            body.getPosition().y = topRectLine.getY() + topRectLine.getHeight() + body.getBaseRadius();
            body.getVelocity().y = 0;
            body.getVelocity().x = 0;
            body.setUpdate(false);
            return false;
        } else if (rectBody.overlaps(bottomRectBlock) || rectBody.overlaps(topRectBlock)) {
            return true;
        }
        return false;
    }
}
