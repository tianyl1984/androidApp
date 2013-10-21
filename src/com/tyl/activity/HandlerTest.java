package com.tyl.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import com.tyl.R;
import com.tyl.R.id;
import com.tyl.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class HandlerTest extends Activity {

	TextView textView = null;

	private Handler myHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				update();
				break;

			default:
				break;
			}
		}

	};

	private void update() {
		textView.setText(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.handler_layout);

		textView = (TextView) findViewById(R.id.handlerTextView);
		textView.setTextSize(30f);

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new MyTask(), 0, 1000);
	}

	private class MyTask extends TimerTask {
		@Override
		public void run() {
			Message message = new Message();
			message.what = 1;
			myHandler.sendMessage(message);
		}
	}
}
