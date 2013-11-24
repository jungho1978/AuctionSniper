package com.lge.auctionsniper;

public class AuctionSniper implements AuctionEventListener {

    private SniperStatusListener statusListener;
    private AuctionNetworkManager networkManager;
    
    private boolean isWinning;

    public AuctionSniper(AuctionNetworkManager networkManager, SniperStatusListener listener) {
        this.networkManager = networkManager;
        this.statusListener = listener;
        this.isWinning = false;
    }

    @Override
    public void auctionClosed() {
        if (isWinning) {
            statusListener.setWonStatus();
        } else {
            statusListener.setLostStatus();    
        }        
    }

    @Override
    public void currentPrice(int price, int increment, String bidder) {
        networkManager.bid(price + increment);

        if (bidder.equalsIgnoreCase("sniper")) {
            isWinning = true;
            statusListener.setWinningStatus();
        } else {
            statusListener.setBiddingStatus();
        }
    }
}
