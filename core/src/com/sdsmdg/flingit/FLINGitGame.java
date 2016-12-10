package com.sdsmdg.flingit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.sdsmdg.flingit.constants.Assets;
import com.sdsmdg.flingit.constants.Dimensions;
import com.sdsmdg.flingit.screens.SplashScreen;

public class FLINGitGame extends Game {

    public Dimensions dimensions;
    public Assets assets;

    @Override
    public void create() {
        dimensions = new Dimensions(this, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        assets = new Assets(this);

        assets.loadSplashScreenSprites();
        setScreen(new SplashScreen(this, assets));

    }

    @Override
    public void render() {
        //It delegates the action to the main screen or the screen which is active
        //at that point of time
        super.render();
    }

    @Override
    public void dispose() {
        super.dispose();
        assets.dispose();
    }

}
