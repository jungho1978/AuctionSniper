package com.lge.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity implements SniperStatusListener {
    private static final String TAG = "AuctionSniper";

    private static final String XMPP_SERVER_HOST = "localhost";
    private static final int XMPP_SERVER_PORT = 5222;

    public static final String SNIPER_ID = "sniper";
    private static final String SNIPER_PASSWORD = "sniper";

    private static final String AUCTION_ID = "auction-item-54321@localhost";
    public static final String ITEM_ID = "item-54321";

    private Chat chat;
    
    private SnipersAdapter adapter;
    private Item item = new Item(ITEM_ID, 0, 0, "");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        adapter = new SnipersAdapter(this, item);
        final ListView lv = (ListView) findViewById(R.id.list);
        lv.setAdapter(adapter);
    }

    private void run() {
        Runnable run = new Runnable() {

            public void run() {
                joinAuction();
            }
        };
        new Thread(run).start();
    }

    public void setStatus(final int resId) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                item.status = getString(resId);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.start:
            run();
            return true;

        default:
            return super.onOptionsItemSelected(item);
        }
    }

    public void joinAuction() {
        setStatus(R.string.status_joining);

        ConnectionConfiguration config = new ConnectionConfiguration(XMPP_SERVER_HOST, XMPP_SERVER_PORT);
        XMPPConnection connection = new XMPPConnection(config);
        try {
            connection.connect();
            connection.login(SNIPER_ID, SNIPER_PASSWORD);
        } catch (XMPPException e) {
            e.printStackTrace();
        }
        chat = connection.getChatManager().createChat(AUCTION_ID, null);
        Auction auction = new XmppAuction(chat);
        chat.addMessageListener(new AuctionMessageTranslator(new AuctionEventManager(auction, this)));
        auction.join();
    }

    @Override
    public void sniperLost() {
        setStatus(R.string.status_lost);
    }

    @Override
    public void sniperBidding(int lastPrice, int winningBid) {
        this.item.price = lastPrice;
        this.item.bid = winningBid;
        setStatus(R.string.status_bidding);
    }

    @Override
    public void sniperWinning(int lastPrice) {
        this.item.price = lastPrice;
        this.item.bid = lastPrice;
        setStatus(R.string.status_winning);
    }

    @Override
    public void sniperWon() {
        setStatus(R.string.status_won);
    }
}
