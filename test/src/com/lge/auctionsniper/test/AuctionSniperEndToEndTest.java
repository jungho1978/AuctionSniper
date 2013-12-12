package com.lge.auctionsniper.test;

import com.lge.auctionsniper.MainActivity;

import android.test.ActivityInstrumentationTestCase2;

public class AuctionSniperEndToEndTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private FakeAuctionHouse auction = new FakeAuctionHouse();
    private SniperRunner sniper;

    public AuctionSniperEndToEndTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        sniper = new SniperRunner(this);
    }

    public void testSniperJoinsAuctionUntilAuctionClosed() throws Exception {
        auction.startSellingItem();
        
        sniper.join();
        sniper.showsJoiningStatus();
        auction.hasReceivedJoinCommandFromSniper();
        
        auction.announceClosedToSniper();
        sniper.showsLostStatus();
    }

    public void testSniperMakesAHigherBidButLoses() throws Exception {
        auction.startSellingItem();
        
        sniper.join();
        sniper.showsJoiningStatus();
        auction.hasReceivedJoinCommandFromSniper();

        auction.reportPrice(100, 10, "other bidder");
        sniper.showsBiddingStatus(100, 10);
        
        auction.hasReceivedBiddingFromSniper(110);

        auction.announceClosedToSniper();
        sniper.showsLostStatus();
    }
    
    public void testSniperWinsAnAuctionByBiddingHigher() throws Exception {
        auction.startSellingItem();
        
        sniper.join();
        sniper.showsJoiningStatus();
        auction.hasReceivedJoinCommandFromSniper();

        auction.reportPrice(100, 10, "other bidder");
        sniper.showsBiddingStatus(100, 10);
        
        auction.hasReceivedBiddingFromSniper(110);
        
        auction.reportPrice(110, 10, MainActivity.SNIPER_ID);
        sniper.showsWinningStatus(120);

        auction.announceClosedToSniper();
        sniper.showsWonStatus();
    }

}
