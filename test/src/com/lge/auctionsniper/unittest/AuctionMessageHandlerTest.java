package com.lge.auctionsniper.unittest;

import junit.framework.TestCase;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.packet.Message;

import com.lge.auctionsniper.AuctionMessageHandler;
import com.lge.auctionsniper.AuctionNetworkManager;
import com.lge.auctionsniper.MainActivity;

public class AuctionMessageHandlerTest extends TestCase {
	private boolean setLostStatusCalled = false;
	private boolean setBiddingStatusCalled = false;
	private int priceAmount;
	
	private boolean bidCalled = false;
	
	private MainActivityStub activityStub = new MainActivityStub();
	private AuctionNetworkManagerStub networkStub = new AuctionNetworkManagerStub(null);
	
	private AuctionMessageHandler handler = new AuctionMessageHandler(activityStub, networkStub);
	
	class MainActivityStub extends MainActivity {
		@Override
		public void setLostStatus() {
			setLostStatusCalled = true;
		}
		
		@Override
		public void setBiddingStatus() {
			setBiddingStatusCalled = true;
		}
	}
	
	class AuctionNetworkManagerStub extends AuctionNetworkManager {
		public AuctionNetworkManagerStub(Chat chat) {
			super(chat);
		}

		@Override
		public void bid(int price) {
			bidCalled = true;
			priceAmount = price;
		}
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		setLostStatusCalled = false;
		setBiddingStatusCalled = false;
		
		bidCalled = false;
		priceAmount = 0;
	}
	
	public void testSetLostStatusCalledWhenCloseEventReceived() {
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: CLOSE;");
		
		handler.processMessage(null, message);
		
		assertEquals(true, setLostStatusCalled);
	}
	
	public void testSetBiddingStatusCalledWhenPriceEventReceived() {
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else;");
		
		handler.processMessage(null, message);
		
		assertEquals(true, setBiddingStatusCalled);
	}
	
	public void testBidCalledWhenPriceEventReceived() {
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else;");
		
		handler.processMessage(null, message);
		
		assertEquals(true, bidCalled);
		assertEquals(199, priceAmount);
	}
	
}
