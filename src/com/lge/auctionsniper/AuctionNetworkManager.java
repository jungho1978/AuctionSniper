package com.lge.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class AuctionNetworkManager {
	private Chat chat;

	public AuctionNetworkManager(Chat chat) {
		this.chat = chat;
	}

	public void bid(int price) {
		Message message = new Message();
		message.setBody("SOLVersion: 1.1; Command: BID; Price: " + price + ";");
		try {
			chat.sendMessage(message);
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}

	public void join() {
		Message msg = new Message();
		msg.setBody("SOLVersion:1.1; Command:JOIN;");
		try {
			chat.sendMessage(msg);
		} catch (XMPPException e) {
			e.printStackTrace();
		}

	}
}
