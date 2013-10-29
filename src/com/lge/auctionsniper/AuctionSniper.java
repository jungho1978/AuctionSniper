package com.lge.auctionsniper;

public class AuctionSniper implements AuctionEventListener {

	private SniperStatusListener statusListener;
	private AuctionNetworkManager networkManager;

	public AuctionSniper(AuctionNetworkManager networkManager, SniperStatusListener listener) {
		this.networkManager = networkManager;
		this.statusListener = listener;
	}

	@Override
	public void auctionClosed() {
		statusListener.setLostStatus();
	}

	@Override
	public void currentPrice(int price, int increment) {
		networkManager.bid(price + increment);
		statusListener.setBiddingStatus();
	}

}
