package com.sdsmdg.flingit.constants;

/**
 * Created by rahul on 9/12/16.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.sdsmdg.flingit.FLINGitGame;

public class Assets {

    private BitmapFont bitmapSmallFont, bitmapTinyFont, bitmapTitleFont;
    private FLINGitGame game;
    private Texture groupLogoTexture, aboutUsTexture, leaderboardTexture, achievementTexture,
            soundOnTexture, soundOffTexture, silverCoinTexture, goldCoinTexture, arrowDownTexture,
            arrowDownDarkTexture, blackHoleTexture, catDefaultTexture;
    private Sprite groupLogoSprite, aboutUsSprite, leaderboardSprite, achievementSprite,
            soundOnSprite, soundOffSprite, silverCoinSprite, goldCoinSprite, arrowDownSprite,
            arrowDownDarkSprite, blackHoleSprite, catDefaultSprite;
    private Sound flingSound, dieSound, achievementSound, coinSound, pipeLandSound, pressSound;
    private TextureAtlas textureAtlas, jumpAtlas, standAtlas;
    public Assets(FLINGitGame game) {
        this.game = game;
    }

    public void loadTextureAtlas(){
        textureAtlas = new TextureAtlas(Gdx.files.internal("drag.txt"));
        jumpAtlas = new TextureAtlas(Gdx.files.internal("jump.txt"));
        standAtlas= new TextureAtlas(Gdx.files.internal("stand.txt"));

    }
    public void loadSounds() {
        flingSound = Gdx.audio.newSound(Gdx.files.internal("throw.ogg"));
        dieSound = Gdx.audio.newSound(Gdx.files.internal("dieSound.mp3"));
        achievementSound = Gdx.audio.newSound(Gdx.files.internal("achievement.wav"));
        coinSound = Gdx.audio.newSound(Gdx.files.internal("coinCollide.mp3"));
        pipeLandSound = Gdx.audio.newSound(Gdx.files.internal("land.mp3"));
        pressSound = Gdx.audio.newSound(Gdx.files.internal("press.ogg"));
    }

    private BitmapFont loadFont(double ratio) {
        BitmapFont font;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("colaborate-Thin-Regular.otf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (ratio * game.dimensions.getScreenWidth());
        font = generator.generateFont(parameter);
        font.setColor(Color.BLACK);
        generator.dispose();
        return font;
    }

    private BitmapFont loadTitleFont(double ratio) {
        BitmapFont font;
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("title-font.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = (int) (ratio * game.dimensions.getScreenWidth());
        font = generator.generateFont(parameter);
        font.setColor(Color.BLACK);
        generator.dispose();
        return font;
    }

    public void loadSplashScreenSprites() {
        groupLogoTexture = new Texture(Gdx.files.internal("mdg_logo.png"));
        //for minification and magnification
        groupLogoTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        groupLogoSprite = new Sprite(groupLogoTexture);
    }

    public void loadGameScreenSprites() {
        aboutUsTexture = new Texture(Gdx.files.internal("about.png"));
        aboutUsTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        aboutUsSprite = new Sprite(aboutUsTexture);

        leaderboardTexture = new Texture(Gdx.files.internal("leaderboard.png"));
        leaderboardTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        leaderboardSprite = new Sprite(leaderboardTexture);

        achievementTexture = new Texture(Gdx.files.internal("achievement.png"));
        achievementTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        achievementSprite = new Sprite(achievementTexture);

        soundOnTexture = new Texture(Gdx.files.internal("soundOn.png"));
        soundOnTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        soundOnSprite = new Sprite(soundOnTexture);

        soundOffTexture = new Texture(Gdx.files.internal("soundOff.png"));
        soundOffTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        soundOffSprite = new Sprite(soundOffTexture);

        silverCoinTexture = new Texture(Gdx.files.internal("silvercoin.png"));
        silverCoinTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        silverCoinSprite = new Sprite(silverCoinTexture);

        goldCoinTexture = new Texture(Gdx.files.internal("goldcoin.png"));
        goldCoinTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        goldCoinSprite = new Sprite(goldCoinTexture);

        arrowDownTexture = new Texture(Gdx.files.internal("arrow-down.png"));
        arrowDownTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        arrowDownSprite = new Sprite(arrowDownTexture);

        arrowDownDarkTexture = new Texture(Gdx.files.internal("arrow-down-dark.png"));
        arrowDownDarkTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        arrowDownDarkSprite = new Sprite(arrowDownDarkTexture);

        blackHoleTexture = new Texture(Gdx.files.internal("blackHole.png"));
        blackHoleTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        blackHoleSprite = new Sprite(blackHoleTexture);

        catDefaultTexture = new Texture(Gdx.files.internal("cat_default.png"));
        catDefaultTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        catDefaultSprite = new Sprite(catDefaultTexture);
    }

    public void loadAllFonts() {
        setBitmapTitleFont();
        setBitmapSmallFont();
        setbitmapTinyFont();
    }

    public BitmapFont getBitmapSmallFont() {
        return bitmapSmallFont;
    }

    private void setBitmapSmallFont() {
        bitmapSmallFont = loadFont(Constants.RATIO_SMALL);
    }

    public BitmapFont getbitmapTinyFont() {
        return bitmapTinyFont;
    }

    private void setbitmapTinyFont() {
        bitmapTinyFont = loadFont(Constants.RATIO_TINY);
    }


    public Sprite getGroupLogoSprite() {
        return groupLogoSprite;
    }

    public void dispose() {
        //disposing textures
        groupLogoTexture.dispose();
        aboutUsTexture.dispose();
        leaderboardTexture.dispose();
        soundOnTexture.dispose();
        soundOffTexture.dispose();
        achievementTexture.dispose();
        silverCoinTexture.dispose();
        goldCoinTexture.dispose();
        arrowDownTexture.dispose();
        arrowDownDarkTexture.dispose();
        blackHoleTexture.dispose();
        catDefaultTexture.dispose();

        //disposing fonts
        bitmapSmallFont.dispose();
        bitmapTinyFont.dispose();

        //disposing Sounds
        flingSound.dispose();
        coinSound.dispose();
        achievementSound.dispose();
        pipeLandSound.dispose();
        dieSound.dispose();
        pressSound.dispose();

        textureAtlas.dispose();
        jumpAtlas.dispose();
        standAtlas.dispose();
    }

    public BitmapFont getBitmapTitleFont() {
        return bitmapTitleFont;
    }

    private void setBitmapTitleFont() {
        bitmapTitleFont = loadTitleFont(Constants.RATIO_LARGE);
    }

    public Sprite getAboutUsSprite() {
        return aboutUsSprite;
    }

    public Sprite getLeaderboardSprite() {
        return leaderboardSprite;
    }

    public Sprite getAchievementSprite() {
        return achievementSprite;
    }

    public Sprite getSoundOnSprite() {
        return soundOnSprite;
    }

    public Sprite getSoundOffSprite() {
        return soundOffSprite;
    }

    public Sprite getSilverCoinSprite() {
        return silverCoinSprite;
    }

    public Sprite getGoldCoinSprite() {
        return goldCoinSprite;
    }

    public Sound getFlingSound() {
        return flingSound;
    }

    public Sound getDieSound() {
        return dieSound;
    }

    public Sound getAchievementSound() {
        return achievementSound;
    }

    public Sound getCoinSound() {
        return coinSound;
    }

    public Sound getPipeLandSound() {
        return pipeLandSound;
    }

    public Sound getPressSound() {
        return pressSound;
    }

    public Sprite getArrowDownSprite() {
        return arrowDownSprite;
    }

    public Sprite getArrowDownDarkSprite() {
        return arrowDownDarkSprite;
    }

    public Sprite getBlackHoleSprite() {
        return blackHoleSprite;
    }

    public Sprite getCatDefaultSprite() {
        return catDefaultSprite;
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }

    public TextureAtlas getJumpAtlas() {
        return jumpAtlas;
    }

    public TextureAtlas getStandAtlas() {
        return standAtlas;
    }
}
