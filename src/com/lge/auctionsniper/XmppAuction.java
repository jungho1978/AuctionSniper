package com.lge.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

class XmppAuction implements Auction {
    private Chat chat;

    public XmppAuction(Chat chat) {
        this.chat = chat;
    }

    @Override
    public void bid(int amount) {
        Message request = new Message();
        request.setBody("SOLVersion: 1.1; Command: BID; Price: " + amount + ";");
        try {
            chat.sendMessage(request);
        } catch (XMPPException e) {
            e.printStackTrace();
        }
    }

    @Override
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