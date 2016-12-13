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

     void unlockAchievementTenSilverCoins();
     void unlockAchievementHundredGames();
     void unlockAchievementFiveGoldCoins();
     void unlockAchievementTwentyFiveGoldCoins();
     void unlockAchievementHundredSilverCoins();
     void unlockAchievementImpossible();
     void submitScore(int highScore);
     void showAchievement();
     void showScore();
     boolean isSignedIn();

}
