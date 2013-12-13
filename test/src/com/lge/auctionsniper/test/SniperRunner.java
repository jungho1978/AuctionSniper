package com.lge.auctionsniper.test;

import junit.framework.Assert;
import android.widget.ListView;
import android.widget.TextView;

import com.jayway.android.robotium.solo.Solo;
import com.lge.auctionsniper.R;

public class SniperRunner {
    private static final int DEFAULT_TIMEOUT = 2000;

    private Solo solo;

    public SniperRunner(AuctionSniperEndToEndTest inst) {
        solo = new Solo(inst.getInstrumentation(), inst.getActivity());
    }

    public void join() {
        solo.clickOnMenuItem("Start");
    }

    public void showsJoiningStatus() {
        ListView lv = (ListView) solo.getCurrentListViews().get(0);
        Assert.assertNotNull(lv);
        Assert.assertEquals(1, lv.getChildCount());
        Assert.assertTrue(isDisplayed(solo.getString(R.string.status_joining)));
    }

    public void showsLostStatus() {
        Assert.assertTrue(isDisplayed(solo.getString(R.string.status_lost)));
    }

    private boolean isDisplayed(String expected) {
        return solo.waitForText(expected, 1, DEFAULT_TIMEOUT);
    }

    public void showsBiddingStatus(int lastPrice, int winningBid) {
        Assert.assertTrue(isDisplayed("item-54321", lastPrice, winningBid, solo.getString(R.string.status_bidding)));
    }

    private boolean isDisplayed(String itemId, int lastPrice, int winningBid, String status) {
        if (solo.waitForText(status, 1, DEFAULT_TIMEOUT)) {
            TextView item = (TextView) solo.getView(R.id.item);
            TextView price = (TextView) solo.getView(R.id.price);
            TextView bid = (TextView) solo.getView(R.id.bid);
            
            return item.getText().equals(itemId)
                    && price.getText().equals(String.valueOf(lastPrice))
                    && bid.getText().equals(String.valueOf(winningBid));
        }
        
        return false;
    }

    public void showsWinningStatus() {
        Assert.assertTrue(isDisplayed(solo.getString(R.string.status_winning)));
    }

    public void showsWonStatus() {
        ListView lv = (ListView) solo.getCurrentListViews().get(0);
        Assert.assertNotNull(lv);
        Assert.assertEquals(1, lv.getChildCount());        
        Assert.assertTrue(isDisplayed(solo.getString(R.string.status_won)));
    }
}
