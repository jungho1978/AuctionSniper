package com.lge.auctionsniper.integrationtest;

import org.jivesoftware.smack.packet.Message;

import com.lge.auctionsniper.AuctionEventListener;
import com.lge.auctionsniper.AuctionMessageTranslator;

import static org.mockito.Mockito.*;

import junit.framework.TestCase;

public class AuctionMessageTranslatorTest extends TestCase {
    
    AuctionEventListener listener = mock(AuctionEventListener.class);
    AuctionMessageTranslator translator = new AuctionMessageTranslator(listener);
    
    public void testAuctionClosedWhenCLOSEEventReceived() throws Exception {
        Message msg = new Message();
        msg.setBody("SOLVersion:1.1; Event:CLOSE;");
        translator.processMessage(null, msg);
        
        verify(listener, atLeast(1)).auctionClosed();
    }
    
    public void testCurrentPriceWhenPRICEEventReceived() throws Exception {
        Message msg = new Message();
        msg.setBody("SOLVersion:1.1; Event:PRICE; CurrentPrice: 1000; Increment: 100; Bidder: kihoon;");
        translator.processMessage(null, msg);
        
        verify(listener, atLeast(1)).currentPrice(1000, 100);
    }
}
