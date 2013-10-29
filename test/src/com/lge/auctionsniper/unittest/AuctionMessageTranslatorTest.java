package com.lge.auctionsniper.unittest;

import junit.framework.TestCase;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jmock.Expectations;
import org.jmock.Mockery;

import com.lge.auctionsniper.AuctionEventListener;
import com.lge.auctionsniper.AuctionMessageTranslator;

public class AuctionMessageTranslatorTest extends TestCase {
	private final Mockery context = new Mockery();
	private final AuctionEventListener listener = context.mock(AuctionEventListener.class);
	private AuctionMessageTranslator translator = new AuctionMessageTranslator(listener);
	
	private final Chat UNUSED_CHAT = null;
	
	public void testSniperClosedCalledWhenCloseMessageReceived() {
		context.checking(new Expectations(){
			{
				one(listener).auctionClosed();
			}
		});
		
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: CLOSE;");
		translator.processMessage(UNUSED_CHAT, message);
		
		context.assertIsSatisfied();
	}
	
	public void testCurrentPriceCalledWhenBidMessageReceived() throws Exception {
		context.checking(new Expectations(){
			{
				one(listener).currentPrice(192, 7);
			}
		});
		
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else;");
		translator.processMessage(UNUSED_CHAT, message);
		
		context.assertIsSatisfied();
	}
	
}
