package com.oxigenoxide.balls;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.service.autofill.UserData;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.utils.Timer;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.games.AnnotatedData;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.Player;
import com.google.android.gms.games.leaderboard.LeaderboardScore;
import com.google.android.gms.games.leaderboard.LeaderboardVariant;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.oxigenoxide.balls.Main;

import java.io.File;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

import static android.content.ContentValues.TAG;


public class AndroidLauncher extends AndroidApplication implements GoogleApiClient.OnConnectionFailedListener {
    FirebaseManager fbm;
    FirebaseAuth mAuth;
    GoogleApiClient mGoogleApiClient;
    public static RewardedVideoAd rewardedVideoAd;
    public static InterstitialAd interstitialAd;
    AdMobManager amm;
    GameManager gm;
    private static final int RC_SIGN_IN = 9001;
    private static final int RC_LEADERBOARD_UI = 9004;
    public static AndroidApplication androidApplication;
    Main main;
    public static int versionCode;
    GoogleSignInOptions gso;

    public AndroidLauncher() {
        amm = new AdMobManager("ca-app-pub-1235754856548433/9540824853");
        fbm = new FirebaseManager(this);
        gm = new GameManager(this);
        main = new Main(fbm, amm, gm);
        androidApplication=this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        amm.init(this);

        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            versionCode = pInfo.versionCode;
            System.out.println("Version: "+ versionCode);
        }
        catch(PackageManager.NameNotFoundException e){
            System.out.println(e);
        }

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        config.useImmersiveMode = true;

        View gameView = initializeForView(main, config);

        RelativeLayout layout = new RelativeLayout(this);
        layout.addView(gameView);
        layout.addView(amm.adView, amm.adParams);

        setContentView(layout);

        mAuth = FirebaseAuth.getInstance();

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN)
                .requestIdToken("1059170856086-o78c4cvfvgokdpkga8dip1irimjjcg98.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        interstitialAd = new InterstitialAd(this);
        interstitialAd.setAdUnitId("ca-app-pub-1235754856548433/9507468595");

        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                interstitialAd.loadAd(new AdRequest.Builder()
                        .addTestDevice("1F6CD9CD10347732E1456E8FCC22D236")
                        .build());
            }

            @Override
            public void onAdLoaded() {
            }
        });
        interstitialAd.loadAd(new AdRequest.Builder()
                .addTestDevice("1F6CD9CD10347732E1456E8FCC22D236")
                .build());

            startSignInIntent(gso);

    }

    public void startSignInIntent(){
        startSignInIntent(gso);
    }
    private void startSignInIntent(GoogleSignInOptions gso) {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                gso);
        Intent intent = signInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }

    public void showLeaderBoards() {
        Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .getLeaderboardIntent(getString(R.string.leaderboard_scores))
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_LEADERBOARD_UI);
                    }
                });
    }
    public void goToPlayStore(){
        Intent browserIntent=new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.oxigenoxide.balls"));
        startActivity(browserIntent);
    }
    public void submitScore(int i) {
        Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .submitScore(getString(R.string.leaderboard_scores),i);
        getRank();
    }

    public void getPlayer() {
        Games.getPlayersClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .getCurrentPlayer().addOnSuccessListener(new OnSuccessListener<Player>() {
            @Override
            public void onSuccess(Player player) {
                gm.player=player;
                getRank();
            }
        });
    }
    public void getRank() {
        Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .loadPlayerCenteredScores(getString(R.string.leaderboard_scores), LeaderboardVariant.TIME_SPAN_ALL_TIME, LeaderboardVariant.COLLECTION_PUBLIC,1)
                .addOnSuccessListener(new OnSuccessListener<AnnotatedData<LeaderboardsClient.LeaderboardScores>>() {
                    @Override
                    public void onSuccess(AnnotatedData<LeaderboardsClient.LeaderboardScores> leaderboardScoresAnnotatedData) {
                        try {
                            for(LeaderboardScore ls : leaderboardScoresAnnotatedData.get().getScores()){
                                //if(ls.getScoreHolderDisplayName()==gm.player.getDisplayName()){
                                    gm.rank=ls.getRank();
                                //}
                            }
                        }
                        catch(IllegalStateException e){
                            System.out.println(e);
                        }
                    }
                });
    }

    public void setUpGame(){
        getPlayer();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data); // Sometimes throws and exception here
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                setUpGame();
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
                signOut();
            }
            Log.w(TAG, "RC_SING_IN");
        }

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "SHPONGLEbob: " + acct.getDisplayName());


        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        //AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            //showLeaderBoards();
                            Task<GoogleSignInAccount> taskG = GoogleSignIn.getSignedInAccountFromIntent(Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient));
                            handleSignInResult(taskG);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            //Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });

    }

    GoogleSignInAccount gsia;
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            gsia = completedTask.getResult(ApiException.class);
            Games.getLeaderboardsClient(this,gsia).getLeaderboardIntent("CgkIltmC3OkeEAIQAQ");
        } catch (ApiException e) {
            Log.println(4,"LOSER",e.toString()+ " "+e.getMessage() + " "+e.getLocalizedMessage());
        }
    }

    void signIn() {
    }

    public void signOut() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN);
        signInClient.signOut().addOnCompleteListener(this,
                new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                    }
                });
    }

    void signOutFirebase() {
        mAuth.signOut();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        main.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        main.onResume();
    }
}
