package com.sdsmdg.flingit.Anim;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.sdsmdg.flingit.FLINGitGame;

/**
 * Created by rahul on 28/1/17.
 */

public class Anim {
    public Anim() {
    }

    public Animation getDraggedAnimation(FLINGitGame game) {
        return new Animation(1f / 60f, game.assets.getTextureAtlas().getRegions());
    }

    public Animation getJumpAnimation(FLINGitGame game) {
        return new Animation(1f / 20f, game.assets.getJumpAtlas().getRegions());
    }

    public Animation getStandAnimation(FLINGitGame game) {
        return new Animation(1f / 60f, game.assets.getStandAtlas().getRegions());
    }
}
