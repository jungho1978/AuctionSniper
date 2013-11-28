package com.lge.auctionsniper.integrationtest;

import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.lge.auctionsniper.Auction;
import com.lge.auctionsniper.AuctionEventManager;
import com.lge.auctionsniper.MainActivity;
import com.lge.auctionsniper.SniperStatusListener;

import junit.framework.TestCase;

public class AuctionEventManagerTest extends TestCase {
    SniperStatusListener listener = mock(SniperStatusListener.class);
    Auction auction = mock(Auction.class);;
    AuctionEventManager manager = new AuctionEventManager(auction, listener);

    public void testSniperLostCalledWhenAuctionClosed() throws Exception {
        manager.auctionClosed();
        verify(listener, atLeast(1)).sniperLost();
    }

    public void testSniperBiddingCalledWhenCurrentPrice() throws Exception {
        int price = 1000;
        int increment = 100;
        manager.currentPrice(price, increment, "Other");
        verify(listener, atLeast(1)).sniperBidding();
    }

    public void testBidCalledWhenCurrentPrice() throws Exception {
        int price = 1000;
        int increment = 100;
        manager.currentPrice(price, increment, "Other");
        verify(auction, atLeast(1)).bid(price + increment);
    }
    
    public void testSniperWinningCalledWhenCurrentPriceWithSniperAsBidder() throws Exception {
        manager.currentPrice(100, 10, MainActivity.SNIPER_ID);
        verify(listener, atLeast(1)).sniperWinning();
    }
    
    public void testSniperWonCalledWhenAuctionClosedIfSniperWinning() throws Exception {
        manager.currentPrice(100, 10, MainActivity.SNIPER_ID);
        manager.auctionClosed();
        verify(listener, atLeast(1)).sniperWon();
    }
    
    public void testSniperLostCalledWhenAuctionClosedIfOtherBidsHighDuringSniperWining() throws Exception {
        manager.currentPrice(100, 10, MainActivity.SNIPER_ID);
        manager.currentPrice(110, 10, "Other");
        manager.auctionClosed();
        verify(listener, atLeast(1)).sniperLost();
    }

}
