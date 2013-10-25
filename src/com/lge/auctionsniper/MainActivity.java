package com.lge.auctionsniper;

import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ChatManagerListener;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static final String TAG = "AuctionSniper";
	
	private static final String XMPP_SERVER_HOST = "localhost";
	private static final int XMPP_SERVER_PORT = 5222;
	
	private static final String SNIPER_ID = "sniper";
	private static final String SNIPER_PASSWORD = "sniper";
	
	private static final String AUCTION_ID = "auction-item-54321@localhost";

	private TextView tv;
	private Chat chat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv = (TextView) findViewById(R.id.status);
		setStatus(R.string.status_joining);
		
		Runnable run = new Runnable() {

			public void run() {
				try {
					ConnectionConfiguration config = new ConnectionConfiguration(
							XMPP_SERVER_HOST, XMPP_SERVER_PORT);
					XMPPConnection connection = new XMPPConnection(config);
					connection.connect();
					connection.login(SNIPER_ID, SNIPER_PASSWORD);
					chat = connection.getChatManager().createChat(
							AUCTION_ID,
							new MessageListener() {

								@Override
								public void processMessage(Chat arg0, Message msg) {
									if (msg.getBody().contains("CLOSE")) {
										setStatus(R.string.status_lost);
									}
								}

							});
					Message msg = new Message();
					msg.setBody("SOLVersion:1.1; Command:JOIN;");
					chat.sendMessage(msg);
				} catch (XMPPException e) {
					Log.d(TAG, "", e);
				}
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

}
