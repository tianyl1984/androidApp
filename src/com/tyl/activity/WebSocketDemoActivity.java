package com.tyl.activity;

import java.net.URI;
import java.util.Arrays;

import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.codebutler.android_websockets.WebSocketClient;
import com.tyl.R;

public class WebSocketDemoActivity extends Activity {

	TextView mStatusline;
	Button mStart;

	EditText mMessage;
	Button mSendMessage;

	private Handler handler = new Handler();

	private void alert(final String message) {
		handler.post(new Runnable() {
			@Override
			public void run() {
				Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
				toast.show();
			}
		});
	}

	private void setButtonConnect() {
		mStart.setText("Connect");
		mStart.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				start();
			}
		});
	}

	private void setButtonDisconnect() {
		mStart.setText("Disconnect");
		mStart.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// mConnection.disconnect();
				if (client != null) {
					client.disconnect();
					client = null;
					setButtonConnect();
				}
			}
		});
	}

	// private final WebSocket mConnection = new WebSocketConnection();

	private static final String TAG = "ws demo";

	private WebSocketClient client;

	private void start() {
		final String wsuri = "ws://192.168.123.1:9999/ws";
		mStatusline.setText("Status: Connecting to " + wsuri + " ..");
		setButtonDisconnect();
		client = new WebSocketClient(URI.create(wsuri), new WebSocketClient.Listener() {
			@Override
			public void onConnect() {
				Log.d(TAG, "Connected!");
				alert("Connected!");
			}

			@Override
			public void onMessage(String message) {
				Log.d(TAG, String.format("Got string message! %s", message));
				alert(message);
			}

			@Override
			public void onMessage(byte[] data) {
				Log.d(TAG, "Got binary message! %s" + new String(data));
				alert(new String(data));
			}

			@Override
			public void onDisconnect(int code, String reason) {
				Log.d(TAG, String.format("Disconnected! Code: %d Reason: %s", code, reason));
				alert(String.format("Disconnected! Code: %d Reason: %s", code, reason));
			}

			@Override
			public void onError(Exception error) {
				Log.e(TAG, "Error!", error);
				alert("Error!" + error.getMessage());
			}
		}, Arrays.asList(
				new BasicNameValuePair("Cookie", "session=abcd")
				));
		client.connect();
		// try {
		// mConnection.connect(wsuri, new WebSocketConnectionHandler() {
		// @Override
		// public void onOpen() {
		// mStatusline.setText("Status: Connected to " + wsuri);
		// mSendMessage.setEnabled(true);
		// mMessage.setEnabled(true);
		// }
		//
		// @Override
		// public void onTextMessage(String payload) {
		// alert("Got echo: " + payload);
		// }
		//
		// @Override
		// public void onClose(int code, String reason) {
		// alert("Connection lost.");
		// mStatusline.setText("Status: Ready.");
		// setButtonConnect();
		// mSendMessage.setEnabled(false);
		// mMessage.setEnabled(false);
		// }
		// });
		// } catch (WebSocketException e) {
		// alert(e.getMessage());
		// }
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.websocket_demo);

		mStatusline = (TextView) findViewById(R.id.statusline);
		mStart = (Button) findViewById(R.id.start);
		mMessage = (EditText) findViewById(R.id.msg);
		mSendMessage = (Button) findViewById(R.id.sendMsg);

		setButtonConnect();

		mSendMessage.setOnClickListener(new Button.OnClickListener() {
			public void onClick(View v) {
				// mConnection.sendTextMessage(mMessage.getText().toString());
				if (client != null) {
					client.send(mMessage.getText().toString());
				}
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (client != null) {
			client.disconnect();
			client = null;
		}
		// if (mConnection.isConnected()) {
		// mConnection.disconnect();
		// }
	}

}
