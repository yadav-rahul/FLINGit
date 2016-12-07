package com.sdsmdg.flingit;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.sdsmdg.flingit.constants.Dimensions;
import com.sdsmdg.flingit.screens.PlayScreen;

public class FLINGitGame extends Game {

	public SpriteBatch batch;
	public Dimensions dimensions;
	@Override
	public void create () {
		dimensions = new Dimensions(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		batch = new SpriteBatch();
		setScreen(new PlayScreen(this));
	}

	@Override
	public void render () {
		//It delegates the action to the main screen or the screen which is active
		//at that point of time
		super.render();
	}

	@Override
	public void dispose () {
		batch.dispose();
		super.dispose();
	}
}
