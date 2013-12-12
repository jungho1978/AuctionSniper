package com.lge.auctionsniper;


public class AuctionEventManager implements AuctionEventListener {
    private SniperStatusListener listener;
    private Auction auction;
    private boolean isWinning;
    
    public AuctionEventManager(Auction auction, SniperStatusListener listener) {
        this.auction = auction;
        this.listener = listener;
        this.isWinning = false;
    }

    @Override
    public void auctionClosed() {
        if (isWinning) {
            listener.sniperWon();
        } else {
            listener.sniperLost();
        }
    }

    @Override
    public void currentPrice(int price, int increment, String bidder) {
        isWinning = bidder.equals(MainActivity.SNIPER_ID);
        
        if (isWinning) {
            listener.sniperWinning(price + increment);
        } else {
            listener.sniperBidding(price, increment);
            auction.bid(price + increment);
        }
    }

}
