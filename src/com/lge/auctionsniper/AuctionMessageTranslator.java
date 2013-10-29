package com.lge.auctionsniper;

import java.util.HashMap;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

public class AuctionMessageTranslator implements MessageListener {
	private AuctionEventListener listener;

	public AuctionMessageTranslator(AuctionEventListener listener) {
		this.listener = listener;
	}

	@Override
	public void processMessage(Chat chat, Message message) {
		HashMap<String, String> event = parse(message);
		
		if (event.get("Event").equals("CLOSE")) {
			listener.auctionClosed();
		} else if (event.get("Event").equals("PRICE")) {
			int price = Integer.valueOf(event.get("CurrentPrice"));
			int increment = Integer.valueOf(event.get("Increment"));
			
			listener.currentPrice(price, increment);
		}
	}
	
	private HashMap<String, String> parse(Message message) {
		HashMap<String, String> event = new HashMap<String, String>();
		
		String[] elements = message.getBody().split(";");
		
		for (String element : elements) {
			String[] pair = element.split(":");
			event.put(pair[0].trim(), pair[1].trim());
		}
		
		return event;
	}
}
