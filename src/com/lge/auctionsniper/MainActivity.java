package com.lge.auctionsniper;

import java.util.HashMap;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String TAG = "AuctionSniper";
	
	private static final String XMPP_SERVER_HOST = "localhost";
	private static final int XMPP_SERVER_PORT = 5222;

	private static final String SNIPER_ID = "sniper";
	private static final String SNIPER_PASSWORD = "sniper";

	private static final String AUCTION_ID = "auction-item-54321@localhost";	

	private Button button;
	private TextView tv;
	private AuctionNetworkManager network;

	private Chat chat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		button = (Button) findViewById(R.id.start_button);
		tv = (TextView) findViewById(R.id.status);

		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				run();
			}

		});
	}

	private void run() {
		Runnable run = new Runnable() {

			public void run() {
				joinAuction();
			}
		};
		new Thread(run).start();
	}

	private void setStatus(final int resId) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				tv.setText(resId);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void setLostStatus() {
		setStatus(R.string.status_lost);
	}

	public void setBiddingStatus() {
		setStatus(R.string.status_bidding);
	}

	public void setJoinStatus() {
		setStatus(R.string.status_joining);

	}

	public void joinAuction() {
		setJoinStatus();
		
		try {
			ConnectionConfiguration config = new ConnectionConfiguration(
					XMPP_SERVER_HOST, XMPP_SERVER_PORT);
			XMPPConnection connection = new XMPPConnection(config);
			connection.connect();
			connection.login(SNIPER_ID, SNIPER_PASSWORD);
			chat = connection.getChatManager().createChat(AUCTION_ID, null);
			
			network = new AuctionNetworkManager(chat);
			chat.addMessageListener(new AuctionMessageHandler(this, network));
			
			network.join();
		} catch (XMPPException e) {
			e.printStackTrace();
		}
	}
}
