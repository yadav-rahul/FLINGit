package com.sdsmdg.flingit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.GamesActivityResultCodes;
import com.google.example.games.basegameutils.GameHelper;

public class AndroidLauncher extends AndroidApplication implements PlayServices, AboutUs {

    private static String TAG = AndroidLauncher.class.getSimpleName();
    private GameHelper gameHelper;
    private final static int requestCode = 1;
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RelativeLayout layout = new RelativeLayout(this);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().clearFlags(
                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
        );

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();

        //Game View
        View gameView = initializeForView(new FLINGitGame(this, this), config);
        layout.addView(gameView);

        setContentView(layout);

        gameHelper = new GameHelper(this, GameHelper.CLIENT_GAMES);

        GameHelper.GameHelperListener gameHelperListener = new GameHelper.GameHelperListener() {
            @Override
            public void onSignInFailed() {
                Log.i("AndroidLauncher", "Sign in Failed!");
            }

            @Override
            public void onSignInSucceeded() {
                Log.i("AndroidLauncher", "Sign in Successful!");
            }
        };

        gameHelper.setup(gameHelperListener);
        gameHelper.setMaxAutoSignInAttempts(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        gameHelper.onStart(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        gameHelper.onStop();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        gameHelper.onActivityResult(requestCode, resultCode, data);

        if (resultCode == GamesActivityResultCodes.RESULT_RECONNECT_REQUIRED) {
            // force a disconnect to sync up state, ensuring that mClient reports "not connected"
            gameHelper.disconnect();
        }

    }

    @Override
    public void signIn() {
        gameHelper.beginUserInitiatedSignIn();
    }

    @Override
    public void signOut() {
        gameHelper.signOut();
    }

    @Override
    public void rateGame() {
        String str = "Your PlayStore Link";
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(str)));
    }


    @Override
    public void unlockAchievementTwoGames() {
        if (gameHelper.isSignedIn())
            Games.Achievements.unlock(gameHelper.getApiClient(),
                    getString(R.string.achievement_play_2_games));
    }

    @Override
    public void unlockAchievementTenPoints() {
        if (gameHelper.isSignedIn())
            Games.Achievements.unlock(gameHelper.getApiClient(),
                    getString(R.string.achievement_10_points));
    }

    @Override
    public void unlockAchievementTwentyPoints() {
        if (gameHelper.isSignedIn())
            Games.Achievements.unlock(gameHelper.getApiClient(),
                    getString(R.string.achievement_20_points));
    }

    @Override
    public void unlockAchievementFiftyPoints() {
        if (gameHelper.isSignedIn())
            Games.Achievements.unlock(gameHelper.getApiClient(),
                    getString(R.string.achievement_50_points));
    }

    @Override
    public void unlockAchievementHundredPoints() {
        if (gameHelper.isSignedIn())
            Games.Achievements.unlock(gameHelper.getApiClient(),
                    getString(R.string.achievement_100_points));
    }

    @Override
    public void unlockAchievementTenSilverCoins() {
        if (gameHelper.isSignedIn())
            Games.Achievements.unlock(gameHelper.getApiClient(),
                    getString(R.string.achievement_10_silver_coins));
    }

    @Override
    public void unlockAchievementHundredGames() {
        if (gameHelper.isSignedIn())
            Games.Achievements.unlock(gameHelper.getApiClient(),
                    getString(R.string.achievement_play_100_games));
    }

    @Override
    public void unlockAchievementFiveGoldCoins() {
        if (gameHelper.isSignedIn())
            Games.Achievements.unlock(gameHelper.getApiClient(),
                    getString(R.string.achievement_5_gold_coins));
    }

    @Override
    public void unlockAchievementTwentyFiveGoldCoins() {
        if (gameHelper.isSignedIn())
            Games.Achievements.unlock(gameHelper.getApiClient(),
                    getString(R.string.achievement_25_gold_coins));
    }

    @Override
    public void unlockAchievementHundredSilverCoins() {
        if (gameHelper.isSignedIn())
            Games.Achievements.unlock(gameHelper.getApiClient(),
                    getString(R.string.achievement_100_silver_coins));
    }

    @Override
    public void unlockAchievementImpossible() {
        if (gameHelper.isSignedIn())
            Games.Achievements.unlock(gameHelper.getApiClient(),
                    getString(R.string.achievement_impossible));
    }

    @Override
    public void submitScore(int highScore) {
        if (isSignedIn()) {
            Games.Leaderboards.submitScore(gameHelper.getApiClient(),
                    getString(R.string.leaderboard_leaderboard), highScore);
        }

    }

    @Override
    public void showAchievement() {
        if (isSignedIn()) {
            startActivityForResult(Games.Achievements.getAchievementsIntent(gameHelper.getApiClient()), requestCode);
        } else {
            signIn();
        }

        Log.i(TAG, "showAchievement: ");
    }

    @Override
    public void showScore() {
        if (isSignedIn()) {
            startActivityForResult(Games.Leaderboards.getLeaderboardIntent(gameHelper.getApiClient(),
                    getString(R.string.leaderboard_leaderboard)), requestCode + 1);
        } else {
            signIn();
        }

        Log.i(TAG, "showScore: ");
    }

    @Override
    public boolean isSignedIn() {
        Log.i(TAG, "isSignedIn: ");
        return gameHelper.isSignedIn();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick() {
        Intent i = new Intent(this, AboutUsActivity.class);
        startActivity(i);
    }

    @Override
    public void onBackPressed() {
        count++;
        if (count == 1) {
            Toast.makeText(this, "Press once again to exit", Toast.LENGTH_SHORT).show();
        } else if (count == 2) {
            count = 0;
            super.onBackPressed();
        }

    }
}
