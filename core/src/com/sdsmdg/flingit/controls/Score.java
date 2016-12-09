package com.sdsmdg.flingit.controls;

/**
 * Created by rahul on 8/12/16.
 */

public class Score {
    private int score = 0;
    private int finalBlockId;
    private boolean initiated = false;
    private boolean collide = false;

    public Score() {
        score = 0;
    }

    public void updateScore() {

        if (initiated && collide) {

            int temp = score % 3;
            switch (temp) {
                case 0:
                    if (finalBlockId == 1) {
                        score += 1;
                    }
                    break;
                case 1:
                    if (finalBlockId == 2) {
                        score += 1;
                    }
                    break;

                case 2:
                    if (finalBlockId == 3) {
                        score += 1;
                    }
                    break;


            }

//            Gdx.app.log("TAG", "Score : " + score + " FinalBlockId : " + finalBlockId);
            initiated = false;
            collide = false;
        }
    }


    public void setInitiated(boolean initiated) {
        this.initiated = initiated;
    }

    public void setCollide(boolean collide, int blockId) {
        //Gdx.app.log("TAG", "Score : " + score + " - Block Id : " + blockId);
        finalBlockId = blockId;
        this.collide = collide;
    }

    public int getScore() {
        return score;
    }
}
