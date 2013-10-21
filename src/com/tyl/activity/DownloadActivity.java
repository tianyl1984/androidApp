package com.tyl.activity;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tyl.R;
import com.tyl.commom.DownloadCallBack;
import com.tyl.commom.MyNetUtil;
import com.tyl.commom.Util;

public class DownloadActivity extends Activity {

	EditText countEditText;

	ProgressBar downloadProgressBar;

	TextView rateTextView;

	TextView timeTextView;

	Long startTime;

	private static final int msg_flush = 0;
	private Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case msg_flush:
				String str = msg.obj.toString();
				int rate = Integer.valueOf(str);
				downloadProgressBar.setProgress(rate);
				rateTextView.setText(rate + "");
				timeTextView.setText("用时：" + (System.currentTimeMillis() - startTime) + "");
				break;

			default:
				break;
			}
			return false;
		}
	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download);
		countEditText = (EditText) findViewById(R.id.countEditText);
		downloadProgressBar = (ProgressBar) findViewById(R.id.downloadProgressBar);
		rateTextView = (TextView) findViewById(R.id.rateTextView);
		timeTextView = (TextView) findViewById(R.id.timeTextView);
		findViewById(R.id.btnStart).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				v.setEnabled(false);
				startTime = System.currentTimeMillis();
				String url = "http://192.168.1.122:9092/dc-base//component/attachment!download.action?checkUser=false&period=year&downloadToken=201205251557457182871117753789137214f42213be7e221c9f3f63685d1246";
				File file = new File(Util.getSdkPath() + "/aaa");
				try {
					MyNetUtil.downloadFile(url, file, new DownloadCallBack() {

						@Override
						public void downloadFinish(File file) {
							handler.sendMessage(Message.obtain(handler, msg_flush, "100"));
						}

						@Override
						public void download(long sum, long finished, File file) {
							handler.sendMessage(Message.obtain(handler, msg_flush, finished * 100 / sum));
						}
					}, Integer.valueOf(countEditText.getText().toString()));
				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}
}
