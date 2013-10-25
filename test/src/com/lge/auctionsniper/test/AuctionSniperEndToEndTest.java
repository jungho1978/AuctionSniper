package com.lge.auctionsniper.test;

import com.lge.auctionsniper.MainActivity;

import android.test.ActivityInstrumentationTestCase2;

public class AuctionSniperEndToEndTest extends
		ActivityInstrumentationTestCase2<MainActivity> {
	private FakeAuctionHouse auction = new FakeAuctionHouse();
	private SniperRunner sniper = new SniperRunner(this);
	
	public AuctionSniperEndToEndTest() {
		super(MainActivity.class);
	}
	
	public void testSniperJoinsAuctionUntilAuctionClosed() throws Exception {
		auction.startSellingItem();
		sniper.join();
		sniper.showsJoiningStatus();
		auction.hasReceivedJoinCommandFromSniper();
		auction.announceClosedToSniper();
		sniper.showsLostStatus();
	}
}
