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
        if (bidder.equals(MainActivity.SNIPER_ID)) {
            isWinning = true;
            listener.sniperWinning();
        } else {
            isWinning = false;
            listener.sniperBidding();
            auction.bid(price + increment);
        }
    }

}
