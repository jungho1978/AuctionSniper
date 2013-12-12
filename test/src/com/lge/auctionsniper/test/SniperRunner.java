package com.lge.auctionsniper.test;

import junit.framework.Assert;
import android.widget.ListView;
import android.widget.TextView;

import com.jayway.android.robotium.solo.Solo;
import com.lge.auctionsniper.MainActivity;
import com.lge.auctionsniper.R;

public class SniperRunner {
    private static final int DEFAULT_TIMEOUT = 2000;

    private Solo solo;
    private TextView item;
    private TextView price;
    private TextView bid;

    public SniperRunner(AuctionSniperEndToEndTest inst) {
        solo = new Solo(inst.getInstrumentation(), inst.getActivity());
        item = (TextView) solo.getView(R.id.item);
        price = (TextView) solo.getView(R.id.price);
        bid = (TextView) solo.getView(R.id.bid);
    }

    public void join() {
        solo.clickOnMenuItem("Start");        
    }

    public void showsJoiningStatus() {
        ListView lv = solo.getCurrentListViews().get(0);
        Assert.assertNotNull(lv);
        Assert.assertTrue(isDisplayed(MainActivity.ITEM_ID, 0, 0, solo.getString(R.string.status_joining)));
    }

    public void showsLostStatus() {
        Assert.assertTrue(isDisplayed(solo.getString(R.string.status_lost)));
    }

    public void showsBiddingStatus(int lastPrice, int winningBid) {
        Assert.assertTrue(isDisplayed(MainActivity.ITEM_ID, lastPrice, winningBid, solo.getString(R.string.status_bidding)));
    }

    public void showsWinningStatus(int lastPrice) {
        Assert.assertTrue(isDisplayed(MainActivity.ITEM_ID, lastPrice, lastPrice, solo.getString(R.string.status_winning)));
    }

    public void showsWonStatus() {
        Assert.assertTrue(isDisplayed(solo.getString(R.string.status_won)));
    }
    
    private boolean isDisplayed(String expected) {
        return solo.waitForText(expected, 1, DEFAULT_TIMEOUT);
    }
    
    private boolean isDisplayed(String item, int price, int bid, String status) {
        boolean isDisplayed = false;
        
        if (solo.waitForText(status, 1, DEFAULT_TIMEOUT)) {
            if (this.item.getText().equals(item)
                    && this.price.getText().equals(String.valueOf(price))
                    && this.bid.getText().equals(String.valueOf(bid))) {
                isDisplayed = true;
            }
        }
        
        return isDisplayed;
    }
    
}
