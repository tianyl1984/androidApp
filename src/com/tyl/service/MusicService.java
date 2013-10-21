package com.tyl.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.tyl.R;

public class MusicService extends Service {

	private MediaPlayer player;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		player = MediaPlayer.create(this.getApplicationContext(), R.raw.newmp3);
		player.start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		player.stop();
	}
}
