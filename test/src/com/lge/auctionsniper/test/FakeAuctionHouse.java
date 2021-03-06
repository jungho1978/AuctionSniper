package com.lge.auctionsniper.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManager;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import android.util.Log;

import junit.framework.Assert;

public class FakeAuctionHouse {
	private static final String TAG = "FakeAuctionHose";
	
	private static final String XMPP_SERVER_HOST = "localhost";
	private static final int XMPP_SERVER_PORT = 5222;
	
	private static final String AUCTION_ID = "auction-item-54321";
	private static final String AUCTION_PASSWORD = "auction";
	

	private ArrayBlockingQueue<Message> queue = new ArrayBlockingQueue<Message>(1);
	private Chat currentChat;

	public void startSellingItem() {
		try {
			ConnectionConfiguration config = new ConnectionConfiguration(
					XMPP_SERVER_HOST, XMPP_SERVER_PORT);
			XMPPConnection connection = new XMPPConnection(config);
			connection.connect();
			connection.login(AUCTION_ID, AUCTION_PASSWORD);
			ChatManager chatmanager = connection.getChatManager();
			chatmanager.addChatListener(new ChatManagerListener() {
				@Override
				public void chatCreated(Chat chat, boolean arg1) {
					
					currentChat = chat;
					chat.addMessageListener(new MessageListener() {
						
						@Override
						public void processMessage(Chat arg0, Message msg) {
							try {
								queue.put(msg);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					});
				}
			});
		} catch (XMPPException e) {
			Log.d(TAG, "", e);
		}
	}

	public void hasReceivedJoinCommandFromSniper() throws InterruptedException {
		Message received = queue.poll(2, TimeUnit.SECONDS);
		
		Assert.assertNotNull(received);
		String expected = "SOLVersion:1.1; Command:JOIN;";
		String actual = received.getBody();
		Assert.assertEquals(expected, actual);
	}

	public void announceClosedToSniper() throws XMPPException {
		Message msg = new Message();
		msg.setBody("SOLVersion:1.1; Event:CLOSE;");
		currentChat.sendMessage(msg);
	}

	public void reportPrice(int price, int increment, String bidder) throws XMPPException {
		Message msg = new Message();
		msg.setBody("SOLVersion:1.1; Event:PRICE; CurrentPrice: " + price + "; Increment: " + increment + "; Bidder: " + bidder +";");
		currentChat.sendMessage(msg);
	}

	public void hasReceivedBiddingFromSniper(int price) throws InterruptedException {
		Message received = queue.poll(2, TimeUnit.SECONDS);
		
		Assert.assertNotNull(received);
		String expected = "SOLVersion: 1.1; Command: BID; Price: " + price + ";";
		String actual = received.getBody();
		Assert.assertEquals(expected, actual);
	}

}
