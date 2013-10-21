package com.tyl.activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.tyl.R;
import com.tyl.service.CountService;
import com.tyl.service.ICountService;

public class ServiceTestActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.service_test);
		// 启动服务
		findViewById(R.id.btnStartService).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				startService(new Intent("com.tyl.service.MusicService"));
			}
		});

		// 结束服务
		findViewById(R.id.btnEndService).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				stopService(new Intent("com.tyl.service.MusicService"));
			}
		});

		findViewById(R.id.btnGetCount).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "" + countService.getCount(), Toast.LENGTH_SHORT).show();
			}
		});
		findViewById(R.id.btnSetCount).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				countService.setCount(countService.getCount() + 100);
				Toast.makeText(getApplicationContext(), "" + countService.getCount(), Toast.LENGTH_SHORT).show();
			}
		});
		startService(new Intent(this, CountService.class));
	}

	@Override
	protected void onStart() {
		super.onStart();
		this.bindService(new Intent(this, CountService.class), this.serviceConnection, BIND_AUTO_CREATE);
	}

	@Override
	protected void onPause() {
		super.onPause();
		this.unbindService(serviceConnection);
	}

	private ICountService countService;

	private ServiceConnection serviceConnection = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			countService = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			countService = (ICountService) service;
		}
	};
}
