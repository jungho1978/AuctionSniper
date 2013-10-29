package com.lge.auctionsniper.unittest;

import junit.framework.TestCase;

import org.jivesoftware.smack.Chat;
import org.jmock.Expectations;
import org.jmock.Mockery;

import com.lge.auctionsniper.AuctionSniper;
import com.lge.auctionsniper.SniperStatusListener;

public class AuctionSniperTest extends TestCase {
	private final Mockery context = new Mockery();
	private final SniperStatusListener listener = context.mock(SniperStatusListener.class);
	
	private Chat NOTUSED_CHAT = null;
	
	private final AuctionSniper sniper = new AuctionSniper(NOTUSED_CHAT, listener);
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		context.assertIsSatisfied();
	}

	public void testSetLostStatusCalledWhenAuctionClosedCalled() {
		context.checking(new Expectations(){
			{
				one(listener).setLostStatus();
			}
		});
		
		sniper.auctionClosed();
	}
	
	public void testSetBidStatusCalledWhenCurrentPriceCalled() {
		context.checking(new Expectations(){
			{
				one(listener).setBiddingStatus();
			}
		});
		
		sniper.currentPrice(0, 0);
	}
}
