package com.sdsmdg.flingit.sprites;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.sdsmdg.flingit.FLINGitGame;
import com.sdsmdg.flingit.constants.Constants;
import com.sdsmdg.flingit.screens.PlayScreen;
import com.sdsmdg.flingit.screens.StartScreen;

/**
 * Created by rahul on 11/12/16.
 */

public class Bar {

    private final static String TAG = Bar.class.getSimpleName();

    private FLINGitGame game;
    private boolean isTouched = false;
    private OrthographicCamera camera;
    private Vector2 position;
    private Vector2 dimensions;
    private Color barPrimaryColor = Constants.COLOR_PRIMARY_BAR_BLUE;
    private Color barSecondaryColor = Constants.COLOR_SECONDARY_BAR_BLUE;

    public Bar(FLINGitGame game, float x, int y, int width, int height, OrthographicCamera camera) {

        this.game = game;
        this.camera = camera;
        position = new Vector2(x, y);
        dimensions = new Vector2(width, height);

    }


    private Color chooseColor() {
        return (isTouched ? barSecondaryColor : barPrimaryColor);
    }

    public void render(ShapeRenderer renderer, int flag) {

        renderer.set(ShapeRenderer.ShapeType.Filled);
        if (flag == 0){
            renderer.setColor(chooseColor());
        }else if (flag == 1){
            renderer.setColor(Constants.COLOR_DEFAULT_BACKGROUND);
        }
        renderer.rect(position.x, position.y, dimensions.x, dimensions.y);

    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 worldClick = camera.unproject(new Vector3(screenX, screenY, 0));
        if (worldClick.y > position.y && worldClick.y < position.y + dimensions.y) {
            if (StartScreen.isSound)
                game.assets.getPressSound().play(0.5f);
            isTouched = true;
        }

        return true;
    }

    public boolean touchUp() {
        if (isTouched) {
            isTouched = false;
            game.setScreen(new PlayScreen(game));
        }

        return true;
    }

    public Vector2 getDimensions() {
        return dimensions;
    }

    public Vector2 getPosition() {
        return position;
    }
}
