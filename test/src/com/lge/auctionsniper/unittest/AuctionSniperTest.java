package com.lge.auctionsniper.unittest;

import junit.framework.TestCase;

import org.jivesoftware.smack.Chat;
import org.jmock.Expectations;
import org.jmock.Mockery;

import com.lge.auctionsniper.AuctionNetworkManager;
import com.lge.auctionsniper.AuctionSniper;
import com.lge.auctionsniper.SniperStatusListener;

public class AuctionSniperTest extends TestCase {
    private final Mockery context = new Mockery();
    private final SniperStatusListener statusListener = context.mock(SniperStatusListener.class);
    private final AuctionNetworkManager network = context.mock(AuctionNetworkManager.class);

    private final AuctionSniper sniper = new AuctionSniper(network, statusListener);

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        context.assertIsSatisfied();
    }

    public void testSetLostStatusCalledWhenAuctionClosedCalled() {
        context.checking(new Expectations() {
            {
                one(statusListener).setLostStatus();
            }
        });

        sniper.auctionClosed();
    }

    public void testBidAndSetBidStatusCalledWhenCurrentPriceCalledWithOtherBidder() {
        context.checking(new Expectations() {
            {
                one(network).bid(110);
                one(statusListener).setBiddingStatus();
            }
        });

        sniper.currentPrice(100, 10, "Other bidder");
    }
    
    public void testBidAndSetBidStatusCalledWhenCurrentPriceCalledWithSniper() {
        context.checking(new Expectations() {
            {
                one(network).bid(110);
                one(statusListener).setBiddingStatus();
            }
        });

        sniper.currentPrice(100, 10, "Sniper");
    }    
}
