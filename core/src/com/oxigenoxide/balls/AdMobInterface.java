package com.oxigenoxide.balls;

public interface AdMobInterface {
    void show();
    void hide();
    void showAd();
    void loadAd();
    void showInterstitial();
    void loadInterstitial();
    int getAdsClicked();
    boolean isInterstitialLoaded();
}
