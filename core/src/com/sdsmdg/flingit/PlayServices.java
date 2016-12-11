package com.sdsmdg.flingit;

public interface PlayServices {

     void signIn();
     void signOut();
     void rateGame();
     void unlockAchievementTwoGames();
     void unlockAchievementTenPoints();
     void unlockAchievementTwentyPoints();
     void unlockAchievementFiftyPoints();
     void unlockAchievementHundredPoints();

     void submitScore(int highScore);
     void showAchievement();
     void showScore();
     boolean isSignedIn();

}
