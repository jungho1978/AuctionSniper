package com.lge.auctionsniper.unittest;

import java.util.HashMap;

import junit.framework.TestCase;

import org.jivesoftware.smack.packet.Message;

import com.lge.auctionsniper.MainActivity;

public class MainActivityTest extends TestCase {
	MainActivity activity;
	
	private boolean auctionClosedCalled = false;
	private boolean auctionPriceCalled = false;
	private int priceAmount;
	
	class MainActivityStub extends MainActivity {
		@Override
		public void setLostStatus() {
			auctionClosedCalled = true;
		}
		
		@Override
		public void setBiddingStatus() {
			auctionPriceCalled = true;	
		}
	}
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		activity = new MainActivity();
	}
	
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
		
		auctionClosedCalled = false;
		auctionPriceCalled = false;
		priceAmount = 0;
	}

	// parse method moved to AuctionMessageHandler
//	public void testParseCloseEvent() {
//		Message event = new Message();
//		event.setBody("SOLVersion: 1.1; Event: CLOSE;");
//		
//		HashMap<String, String> parsed = activity.parse(event);
//		assertNotNull(parsed);
//		assertEquals(2, parsed.size());
//		assertEquals("1.1", parsed.get("SOLVersion").trim());
//		assertEquals("CLOSE", parsed.get("Event").trim());
//	}
	
	// parse method moved to AuctionMessageHandler
//	public void testParsePriceEvent() {
//		Message event = new Message();
//		event.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else;");
//		
//		HashMap<String, String> parsed = activity.parse(event);
//		assertNotNull(parsed);
//		assertEquals(5, parsed.size());
//		assertEquals("1.1", parsed.get("SOLVersion").trim());
//		assertEquals("PRICE", parsed.get("Event").trim());
//		assertEquals("192", parsed.get("CurrentPrice").trim());
//		assertEquals("7", parsed.get("Increment").trim());
//		assertEquals("Someone else", parsed.get("Bidder").trim());
//	}
	

}
