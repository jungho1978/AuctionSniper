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

	private TextView tv;
	private Chat chat;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv = (TextView) findViewById(R.id.status);
		setStatus("Joining");
		
		Runnable run = new Runnable() {

			public void run() {
				try {
					ConnectionConfiguration config = new ConnectionConfiguration(
							"localhost", 5222);
					XMPPConnection connection = new XMPPConnection(config);
					connection.connect();
					connection.login("sniper", "sniper");
					chat = connection.getChatManager().createChat(
							"auction-item-54321@localhost",
							new MessageListener() {

								@Override
								public void processMessage(Chat arg0, Message msg) {
									Log.d("sniper", "MSG: " + msg.getBody());
									
									if (msg.getBody().contains("CLOSE")) {
										setStatus("Lost");
									}
								}

							});
					Message msg = new Message();
					msg.setBody("SOLVersion:1.1; Command:JOIN;");
					chat.sendMessage(msg);
				} catch (XMPPException e) {
					Log.d("sniper", " ",e);
				}
			}
		};
		new Thread(run).start();
		
	}
	

	private void setStatus(final String status) {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				tv.setText(status);
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
