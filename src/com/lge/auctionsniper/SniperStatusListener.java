package com.lge.auctionsniper;

public interface SniperStatusListener {
    void sniperLost();
    void sniperBidding(int lastPrice, int winningBid);
    void sniperWinning(int lastPrice);
    void sniperWon();
}
