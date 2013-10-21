package com.tyl.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class CountService extends Service implements ICountService {

	@Override
	public int getCount() {
		return count;
	}

	@Override
	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return myBinder;
	}

	private int count = 0;
	private boolean stop = false;

	private IBinder myBinder = new CountServiceBinder();

	public class CountServiceBinder extends Binder implements ICountService {

		@Override
		public int getCount() {
			return count;
		}

		@Override
		public void setCount(int count) {
			CountService.this.count = count;
		}

	}

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (!stop) {
					if (count >= 200) {
						stopSelf();
					}
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					count++;
					Log.v("aaaaaaaaaaa", count + "");
				}
			}
		}).start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.v("aaaaaaaaaaa", "destroy");
		this.stop = true;
	}
}
