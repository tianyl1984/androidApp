package com.tyl.activity;

import com.tyl.R;
import com.tyl.R.id;
import com.tyl.R.layout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ProgressbarActivity extends Activity {

	ProgressBar bar1;

	TextView showText;

	private Handler myHandler = new Handler();

	private int mProgressStatus = 0;

	int count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.progressbar);
		bar1 = (ProgressBar) findViewById(R.id.bar1);
		showText = (TextView) findViewById(R.id.showText);

		bar1.setIndeterminate(false);

		bar1.setMax(100);
		bar1.setProgress(0);

		findViewById(R.id.btnShowProgress).setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				bar1.setVisibility(View.VISIBLE);
				new Thread(new Runnable() {

					public void run() {
						while (mProgressStatus < 100) {
							try {
								Thread.sleep(1000);
								mProgressStatus += 30;
								myHandler.post(new Runnable() {

									public void run() {
										if (mProgressStatus >= 100) {
											bar1.setVisibility(View.GONE);
										} else {
											bar1.setProgress(mProgressStatus);
										}
										showText.setText(mProgressStatus + "");
									}
								});
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}).start();
			}
		});

		findViewById(R.id.btnShowProgress2).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				final ProgressDialog pd = new ProgressDialog(ProgressbarActivity.this);
				pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				pd.setCancelable(false);
				pd.setMessage("正在操作，请等待...");
				pd.show();
				new Thread(new Runnable() {

					public void run() {
						try {
							while (count < 10) {
								count++;
								Thread.sleep(100);
							}
							pd.cancel();
							count = 0;
						} catch (Exception e) {
							pd.cancel();
						}
					}
				}).start();
			}
		});
	}
}
