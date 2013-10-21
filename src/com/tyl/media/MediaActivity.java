package com.tyl.media;

import java.io.IOException;

import android.app.Activity;
import android.content.res.Resources.NotFoundException;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.tyl.R;

public class MediaActivity extends Activity {

	MediaPlayer player = new MediaPlayer();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.media_layout);

		final Button btnPlayMusic = (Button) findViewById(R.id.btnPlayMusic);

		player.setOnCompletionListener(new OnCompletionListener() {

			@Override
			public void onCompletion(MediaPlayer mp) {
				btnPlayMusic.setText("播放音乐");
				// player.release();
				Toast.makeText(MediaActivity.this, "播放结束", Toast.LENGTH_LONG).show();
			}
		});

		player.setOnErrorListener(new OnErrorListener() {

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				Toast.makeText(MediaActivity.this, "播放出错", Toast.LENGTH_LONG).show();
				return true;
			}
		});

		btnPlayMusic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (player.isPlaying()) {
					btnPlayMusic.setText("播放音乐");
					player.stop();
					// player.release();
				} else {
					btnPlayMusic.setText("暂停音乐");
					try {
						player = new MediaPlayer();
						player.setDataSource(getResources().openRawResourceFd(R.raw.newmp3).getFileDescriptor());
						player.prepare();
						player.start();
					} catch (IllegalArgumentException e) {
						e.printStackTrace();
					} catch (IllegalStateException e) {
						e.printStackTrace();
					} catch (NotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
	}
}
