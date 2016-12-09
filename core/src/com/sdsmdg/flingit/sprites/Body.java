package com.sdsmdg.flingit.sprites;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.sdsmdg.flingit.FLINGitGame;
import com.sdsmdg.flingit.constants.Constants;
import com.sdsmdg.flingit.controls.Score;
import com.sdsmdg.flingit.screens.PlayScreen;

/**
 * Created by rahul on 6/12/16.
 */

public class Body extends InputAdapter {

    private final static String TAG = "com.sdsmdg.flingit.sprites";
    private Vector3 position;
    private Vector3 velocity;
    private Vector3 acc;
    private Rectangle rectBody;
    private Color bodyColor = Constants.COLOR_BODY;
    private float radiusFactor = 1.0f / Constants.RADIUS_FACTOR;

    private FLINGitGame game;
    private Vector3 flickStart;
    private Vector3 flickDragged;
    private Vector3 flickDraggedVector;
    private boolean flicking = false;
    private boolean isUpdate = false;
    private OrthographicCamera camera;
    private float baseRadius;
    private PlayScreen playScreen;
    private Score score;
    private int currentBlockId = 0;
    private boolean isInAir = false;

    //Will be used to grow or shrink radius of ball dynamically
    private float radiusMultiplier;

    public Body(FLINGitGame flinGitGame, PlayScreen playScreen, Score score, OrthographicCamera camera, int x, int y) {

        this.score = score;
        this.playScreen = playScreen;
        this.camera = camera;
        this.game = flinGitGame;
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        acc = new Vector3(0, ((float) -game.dimensions.getScreenWidth()) / 100, 0);
        init();
        rectBody = new Rectangle(x - baseRadius, y - baseRadius, 2 * baseRadius, 2 * baseRadius);

    }

    private void init() {
        baseRadius = radiusFactor * (game.dimensions.getScreenWidth());
        radiusMultiplier = 1;
    }

    public void update(float delta) {
        if (isUpdate) {
            delta = (((float) -game.dimensions.getScreenWidth()) / 35) * delta;
            velocity.y += delta * acc.y;

            position.x += delta * velocity.x;
            position.y += (int) (delta * (velocity.y + 0.5 * acc.y * delta));

            //Change this method to set when radius starts changing because then dimensions of rect will
            //also change
            rectBody.setPosition(position.x - baseRadius, position.y - baseRadius);
        }

//        Gdx.app.log(TAG, "Position X : " + position.x + " || Position Y : " + position.y +
//                " || Velocity X : " + velocity.x + " || Velocity Y : " + velocity.y);

        collideWithWalls(baseRadius * radiusMultiplier, game.dimensions.getScreenWidth(),
                game.dimensions.getScreenHeight());

    }

    public Rectangle getRectBody() {
        return rectBody;
    }

    private void collideWithWalls(float radius, float viewportWidth, float viewportHeight) {
        if (position.x - radius < 0) {
            position.x = radius;
            velocity.x = 0;
            game.setScreen(new PlayScreen(game));
        }
        if (position.x + radius > viewportWidth) {

        }
        if (position.y - radius < 0) {
            position.y = radius;
            velocity.y = 0;
            velocity.x = 0;
            isUpdate = false;
            game.setScreen(new PlayScreen(game));
        }
        if (position.y + radius > viewportHeight) {
            //TODO Do something when it goes above the certain height.
        }
    }

    public void render(ShapeRenderer renderer) {

        renderer.set(ShapeRenderer.ShapeType.Filled);
        renderer.setColor(bodyColor);
        renderer.circle(position.x, position.y, baseRadius * radiusMultiplier);

    }

    @Override
    public boolean keyDown(int keycode) {
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 worldClick = camera.unproject(new Vector3(screenX, screenY, 0));
        if (worldClick.dst(position) < baseRadius * radiusMultiplier) {
            flicking = true;
            flickStart = worldClick;

            //Fade in the color of body
            bodyColor = Constants.COLOR_BODY_FADED;
        }

        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        flickDragged = camera.unproject(new Vector3(screenX, screenY, 0));
        if (flickStart != null && flicking) {
            flickDraggedVector = new Vector3(flickDragged.x - flickStart.x, flickDragged.y - flickStart.y, 0);
            flickDraggedVector.x = (float) (flickDraggedVector.x * 0.3);
            flickDraggedVector.y = (float) (flickDraggedVector.y * 0.3);

            //Change radius factor according to the length of the dragged Vector
            radiusFactor = 1.0f / (20 + flickDraggedVector.len() / 20);
            init();

            if (flickDraggedVector.x < 0) {
                playScreen.setAttachCamera(true, flickDraggedVector.x);
            }
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (flicking) {
            isInAir = true;
            //Fade out the color of body
            bodyColor = Constants.COLOR_BODY;
            flicking = false;
            Vector3 flickEnd = camera.unproject(new Vector3(screenX, screenY, 0));
            Vector3 flickVector = new Vector3(flickEnd.x - flickStart.x, flickEnd.y - flickStart.y, 0);
            flickVector.x = (float) (flickVector.x * 0.3);
            flickVector.y = (float) (flickVector.y * 0.3);
            velocity = (flickVector);

            isUpdate = true;
            playScreen.setUpdateCamera(false);
            playScreen.setUpdateBodyRadius(true);
            score.setInitiated(true);
        }
        playScreen.setAttachCamera(false, 0);

        return true;
    }

    public Vector3 getPosition() {
        return position;
    }

    public Vector3 getVelocity() {
        return velocity;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public float getBaseRadius() {
        return baseRadius;
    }

    public void setBaseRadius(float baseRadius) {
        this.baseRadius = baseRadius;
    }

    public int getCurrentBlockId() {
        return currentBlockId;
    }

    public void setCurrentBlockId(int currentBlockId) {
        this.currentBlockId = currentBlockId;
    }

    public boolean isInAir() {
        return isInAir;
    }

    public void setInAir(boolean inAir) {
        isInAir = inAir;
    }
}
