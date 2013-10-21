package com.tyl.activity;

import com.tyl.R;
import com.tyl.R.id;
import com.tyl.R.layout;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class BroadcastTestActivity extends Activity {

	private Button btnStatusMsg;

	private Button btnRegister;

	private Button btnUnRegister;

	private Button btnSendBroadcast;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.broadcast_test);
		btnStatusMsg = (Button) findViewById(R.id.btnStatusMsg);
		btnRegister = (Button) this.findViewById(R.id.btnRegister);
		btnUnRegister = (Button) this.findViewById(R.id.btnUnRegister);
		btnSendBroadcast = (Button) this.findViewById(R.id.btnSendBroadcast);
		btnRegister.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				IntentFilter filter = new IntentFilter();
				filter.addAction(Intent.ACTION_BATTERY_CHANGED);
				filter.addAction("com.tyl.msg");
				BroadcastTestActivity.this.registerReceiver(receiver, filter);
				btnRegister.setEnabled(false);
				btnStatusMsg.setText("已启动接收器！！");
			}
		});
		btnUnRegister.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				BroadcastTestActivity.this.unregisterReceiver(receiver);
				btnRegister.setEnabled(true);
			}
		});
		btnSendBroadcast.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setAction("com.tyl.msg");
				Bundle b = new Bundle();
				b.putString("aaa", "发送广播信息");
				intent.putExtras(b);
				BroadcastTestActivity.this.sendBroadcast(intent);
			}
		});
	}

	private BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			Bundle bundle = intent.getExtras();
			Object[] names = bundle.keySet().toArray();
			String msg = "";
			for (Object obj : names) {
				msg += obj.toString() + ":" + bundle.get(obj.toString()).toString() + "\n";
			}
			if (Intent.ACTION_BATTERY_CHANGED.equals(action)) {
				Toast.makeText(context, "电量发生变化：" + msg, Toast.LENGTH_LONG).show();
			}
			if ("com.tyl.msg".equals(action)) {
				Toast.makeText(context, "自定义广播：" + msg, Toast.LENGTH_LONG).show();
				btnStatusMsg.setText("接收到广播！");
			}
		}
	};

}
