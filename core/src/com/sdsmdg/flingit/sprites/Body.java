package com.sdsmdg.flingit.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.sdsmdg.flingit.Anim.Anim;
import com.sdsmdg.flingit.FLINGitGame;
import com.sdsmdg.flingit.constants.Constants;
import com.sdsmdg.flingit.controls.Score;
import com.sdsmdg.flingit.screens.PlayScreen;
import com.sdsmdg.flingit.screens.StartScreen;

/**
 * Created by rahul on 6/12/16.
 */

public class Body extends InputAdapter {

    private final static String TAG = Body.class.getSimpleName();
    private Vector3 position;
    private Vector3 velocity;
    private Vector3 acc;
    private Rectangle rectBody;
    private Color bodyColor = Constants.COLOR_PRIMARY_BAR_BLUE;
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
    private Line line;
    //Will be used to grow or shrink radius of ball dynamically
    private float radiusMultiplier;
    private SpriteBatch spriteBatch;
    private GameOverBar gameOverBar;
    private boolean isDraggedAnimation = false;
    private Sprite defaultCatSprite;
    private float elapsedTime2 = 0f;
    ;
    private float elapsedTime = 0f;

    public Body(FLINGitGame flinGitGame, PlayScreen playScreen, GameOverBar gameOverBar,
                Score score, OrthographicCamera camera, int x, int y) {
        this.gameOverBar = gameOverBar;
        this.score = score;
        this.playScreen = playScreen;
        this.camera = camera;
        this.game = flinGitGame;
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        acc = new Vector3(0, ((float) -game.dimensions.getScreenWidth()) / 100, 0);
        init();
        rectBody = new Rectangle(x - baseRadius , y - baseRadius,
                2 * baseRadius, 2 * baseRadius);
        line = new Line(this, game.dimensions.getScreenWidth() / 70);
        defaultCatSprite = game.assets.getCatDefaultSprite();
        defaultCatSprite.setSize(2 * baseRadius * radiusMultiplier, 2 * baseRadius * radiusMultiplier);
    }

    public void init() {
        baseRadius = radiusFactor * (game.dimensions.getScreenWidth());
        radiusMultiplier = 1;
    }

    public void update(float delta) {
        if (isUpdate) {
            delta = (-25) * delta;
            velocity.y += delta * acc.y;

            position.x += delta * velocity.x;
            position.y += (int) (delta * (velocity.y + 0.5 * acc.y * delta));

            //Change this method to set when radius starts changing because then dimensions of rect will
            //also change
            rectBody.setPosition(position.x - baseRadius + baseRadius / 7, position.y - baseRadius);
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
        if (position.x < camera.position.x - (camera.viewportWidth)) {
            gameOver();
        } else if (position.x > camera.position.x + (camera.viewportWidth / 2) + baseRadius) {
            gameOver();
        } else if (position.y - radius < 0) {
            gameOver();
        }
    }

    private void gameOver() {
        if ((StartScreen.isSound) && (!playScreen.isGameOver())) {
            Gdx.input.vibrate(100);
            game.assets.getDieSound().play(0.3f);
        }
        playScreen.setGameOver(true);
        isUpdate = false;
    }

    public void render(SpriteBatch spriteBatch) {
        this.spriteBatch = spriteBatch;
        if (!playScreen.isGameOver()) {
            defaultCatSprite.setPosition(position.x - (defaultCatSprite.getWidth() / 2),
                    position.y - (defaultCatSprite.getHeight() / 2));
            //defaultCatSprite.draw(spriteBatch);

            if (isInAir) {
                showAnimation(new Anim().getJumpAnimation(game), false);
            }else{
                showAnimation(new Anim().getStandAnimation(game), true);
            }
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (playScreen.isGameOver()) {
            gameOverBar.touchDown(screenX, screenY, pointer, button);
        }
        Vector3 worldClick = camera.unproject(new Vector3(screenX, screenY, 0));
        if (worldClick.dst(position) < 2 * baseRadius * radiusMultiplier) {
            flicking = true;
            flickStart = worldClick;

            //Fade in the color of body
            bodyColor = Constants.COLOR_SECONDARY_BAR_BLUE;
        }


        return true;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {

        flickDragged = camera.unproject(new Vector3(screenX, screenY, 0));
        if (flickStart != null && flicking) {
            line.setShow(false);

            flickDraggedVector = new Vector3(flickDragged.x - flickStart.x, flickDragged.y - flickStart.y, 0);
            flickDraggedVector.x = (float) (flickDraggedVector.x * 0.3);
            flickDraggedVector.y = (float) (flickDraggedVector.y * 0.35);

            line.setDraggedVector(flickDraggedVector);
            setDraggedAnimation(true);
            //Change radius factor according to the length of the dragged Vector
//            radiusFactor = 1.0f / (20 + flickDraggedVector.len() / 10);
            init();

            if (flickDraggedVector.x < 0) {
                playScreen.setAttachCamera(true, flickDraggedVector.x);
            }
        }

        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        elapsedTime = 0;
        if (flicking) {
            if (StartScreen.isSound) {
                game.assets.getFlingSound().play();
            }
            isInAir = true;
            //Remove the line
            line.setShow(false);
            setDraggedAnimation(false);
            //Fade out the color of body
            bodyColor = Constants.COLOR_PRIMARY_BAR_BLUE;
            flicking = false;
            Vector3 flickEnd = camera.unproject(new Vector3(screenX, screenY, 0));
            Vector3 flickVector = new Vector3(flickEnd.x - flickStart.x, flickEnd.y - flickStart.y, 0);
            flickVector.x = (float) (flickVector.x * 0.3);
            flickVector.y = (float) (flickVector.y * 0.35);
            velocity = (flickVector);

            isUpdate = true;
            playScreen.setUpdateCamera(false);
            playScreen.setUpdateBodyRadius(true);
            score.setInitiated(true);
        }
        playScreen.setAttachCamera(false, 0);

        return true;
    }

    private void showAnimation(Animation draggedAnimation, boolean flag) {
        elapsedTime += Gdx.graphics.getDeltaTime();
        if (true) {
            //Show dragged animation
            //Gdx.app.log("Rahul", "Dragged : " + flickDragged);
            spriteBatch.draw(draggedAnimation.getKeyFrame(elapsedTime, flag), position.x - (1 * defaultCatSprite.getWidth()),
                    position.y - (55 * defaultCatSprite.getHeight() / 100), 0, 0, 500, 500, 0.4f, 0.4f, 0);
        }
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

    public Line getLine() {
        return line;
    }

    public Vector3 getFlickDragged() {
        return flickDragged;
    }

    public void setRadiusFactor(float radiusFactor) {
        this.radiusFactor = radiusFactor;
    }

    public boolean isDraggedAnimation() {
        return isDraggedAnimation;
    }

    public void setDraggedAnimation(boolean draggedAnimation) {
        isDraggedAnimation = draggedAnimation;
    }
}
