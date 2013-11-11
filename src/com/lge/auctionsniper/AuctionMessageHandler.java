package com.lge.auctionsniper;

import java.util.HashMap;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class AuctionMessageHandler implements MessageListener {
	private MainActivity activity;
	private AuctionNetworkManager network;

	public AuctionMessageHandler(MainActivity activity, AuctionNetworkManager network) {
		this.activity = activity;
		this.network = network;
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		HashMap<String, String> elements = parse(message);
		String event = elements.get("Event");
		
		if (event.equalsIgnoreCase("close")) {
			activity.setLostStatus();
		} else if (event.equalsIgnoreCase("price")) {
			activity.setBiddingStatus();
			
			int price = Integer.valueOf(elements.get("CurrentPrice"));
			int increment = Integer.valueOf(elements.get("Increment"));
			
			network.bid(price + increment);
		}
	}
	
	public HashMap<String, String> parse(Message message) {
		HashMap<String, String> parsed = new HashMap<String, String>();
		String[] fields = message.getBody().split(";");
		
		for(String field :fields) {
			String[] elements = field.split(":");
			parsed.put(elements[0].trim(), elements[1].trim());
		}
		return parsed;
	}
}
