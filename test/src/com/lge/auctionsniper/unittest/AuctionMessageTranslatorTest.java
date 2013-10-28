package com.lge.auctionsniper.unittest;

import junit.framework.TestCase;

import org.jivesoftware.smack.Chat;
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
	
	public void testAuctionClosedCalledWhenCloseMessageReceived() {
		context.checking(new Expectations(){
			{
				oneOf(listener).auctionClosed();
			}
		});
		
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Event: CLOSE;");
		translator.processMessage(UNUSED_CHAT, message);
		
		context.assertIsSatisfied();
	}
	
}
