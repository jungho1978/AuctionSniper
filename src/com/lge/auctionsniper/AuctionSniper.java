package com.lge.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;


public class AuctionSniper implements AuctionEventListener {

	private SniperStatusListener listener;
	private Chat chat;

	public AuctionSniper(Chat chat, SniperStatusListener listener) {
		this.chat = chat;
		this.listener = listener;
	}

	@Override
	public void auctionClosed() {
		listener.setLostStatus();
	}

	@Override
	public void currentPrice(int price, int increment) {
		listener.setBiddingStatus();
	
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Command: BID; Price: " + (price + increment) + ";");
		try {
			chat.sendMessage(message);
		} catch (XMPPException e) {
			e.printStackTrace();
		}	
	}

}
