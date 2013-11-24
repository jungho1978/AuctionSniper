package com.lge.auctionsniper;

public interface SniperStatusListener {
    public void setLostStatus();
    public void setBiddingStatus();
    public void setWinningStatus();
    public void setWonStatus();
}
