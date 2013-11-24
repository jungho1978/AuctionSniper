package com.lge.auctionsniper.test;

import junit.framework.Assert;
import android.view.View;

import com.jayway.android.robotium.solo.Solo;
import com.lge.auctionsniper.R;

public class SniperRunner {
    private static final int DEFAULT_TIMEOUT = 2000;

    private Solo solo;

    public SniperRunner(AuctionSniperEndToEndTest inst) {
        solo = new Solo(inst.getInstrumentation(), inst.getActivity());
    }

    public void join() {
        View view = (View)solo.getView(R.id.start_button);
        solo.clickOnView(view);
    }

    public void showsJoiningStatus() {
        Assert.assertTrue(isDisplayed(solo.getString(R.string.status_joining)));
    }

    public void showsLostStatus() {
        Assert.assertTrue(isDisplayed(solo.getString(R.string.status_lost)));
    }

    public void showsBiddingStatus() {
        Assert.assertTrue(isDisplayed(solo.getString(R.string.status_bidding)));
    }

    private boolean isDisplayed(String expected) {
        return solo.waitForText(expected, 1, DEFAULT_TIMEOUT);
    }

    public void showsWinningStatus() {
        Assert.assertTrue(isDisplayed(solo.getString(R.string.status_winning)));
    }

    public void showsWonStatus() {
        Assert.assertTrue(isDisplayed(solo.getString(R.string.status_won)));
    }
    
    
}
