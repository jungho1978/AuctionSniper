package com.lge.auctionsniper;

import java.util.HashMap;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.packet.Message;

public class AuctionMessageTranslator implements MessageListener {

    private AuctionEventListener listener;

    public AuctionMessageTranslator(AuctionEventListener listener) {
        this.listener = listener;
    }

    @Override
    public void processMessage(Chat chat, Message msg) {
        HashMap<String, String> elements = parse(msg);
        String event = elements.get("Event");

        if (event.equalsIgnoreCase("close")) {
            listener.auctionClosed();
        } else if (event.equalsIgnoreCase("price")) {
            int price = Integer.valueOf(elements.get("CurrentPrice"));
            int increment = Integer.valueOf(elements.get("Increment"));

            listener.currentPrice(price, increment);
        }
    }
    
    private HashMap<String, String> parse(Message message) {
        HashMap<String, String> parsed = new HashMap<String, String>();
        String[] fields = message.getBody().split(";");

        for (String field : fields) {
            String[] elements = field.split(":");
            parsed.put(elements[0].trim(), elements[1].trim());
        }
        
        return parsed;
    }
}
