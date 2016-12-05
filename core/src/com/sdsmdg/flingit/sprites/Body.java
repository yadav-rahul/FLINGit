package com.sdsmdg.flingit.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by rahul on 6/12/16.
 */

public class Body {
    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acc;

    private Texture body;

    public Body(int x, int y){
        position = new Vector2(x, y);
        velocity = new Vector2(x,y);
        acc = new Vector2(0, -10);
    }

    public void update(float dt){

    }
}
