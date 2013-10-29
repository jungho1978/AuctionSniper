package com.lge.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class AuctionXmppNetworkManager implements AuctionNetworkManager {
	private Chat chat;

	public AuctionXmppNetworkManager(Chat chat) {
		this.chat = chat;
	}

	@Override
	public void bid(int price) {
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Command: BID; Price: " + price + ";");
		try {
			chat.sendMessage(message);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void join() {
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Command: JOIN;");
		try {
			chat.sendMessage(message);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}
}
