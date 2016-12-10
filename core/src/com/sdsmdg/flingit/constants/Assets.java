package com.sdsmdg.flingit.constants;

/**
 * Created by rahul on 9/12/16.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.sdsmdg.flingit.FLINGitGame;

public class Assets {

    private BitmapFont bitmapSmallFont, bitmapMediumFont, bitmapLargeFont;
    private FLINGitGame game;
    private Texture groupLogoTexture;
    private Sprite groupLogoSprite;

    public Assets(FLINGitGame game) {
        this.game = game;
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

    public void loadSplashScreenSprites(){
        groupLogoTexture = new Texture(Gdx.files.internal("mdg_logo.png"));
        //for minification and magnification
        groupLogoTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        groupLogoSprite = new Sprite(groupLogoTexture);
    }

    public void loadGameScreenSprites(){

    }

    public void loadAllFonts() {
        setBitmapSmallFont();
        setBitmapMediumFont();
        setBitmapLargeFont();
    }

    public BitmapFont getBitmapSmallFont() {
        return bitmapSmallFont;
    }

    private void setBitmapSmallFont() {
        bitmapSmallFont = loadFont(Constants.RATIO_SMALL);
    }

    public BitmapFont getBitmapMediumFont() {
        return bitmapMediumFont;
    }

    private void setBitmapMediumFont() {
        bitmapMediumFont = loadFont(Constants.RATIO_MEDIUM);
    }

    public BitmapFont getBitmapLargeFont() {
        return bitmapLargeFont;
    }

    private void setBitmapLargeFont() {
        bitmapLargeFont = loadFont(Constants.RATIO_LARGE);
    }


    public Sprite getGroupLogoSprite() {
        return groupLogoSprite;
    }

    public void dispose() {
        //disposing textures
        groupLogoTexture.dispose();

        //disposing fonts
        bitmapSmallFont.dispose();
        bitmapMediumFont.dispose();
        bitmapLargeFont.dispose();
    }
}
