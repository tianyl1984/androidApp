package com.tyl.activity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tyl.R;

public class MyAsyncTaskActivity extends Activity {

	ProgressBar downloadProgressBar = null;
	Button startBtn = null;
	TextView resultTextView = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.asynctask);
		downloadProgressBar = (ProgressBar) findViewById(R.id.downloadProgressBar);
		startBtn = (Button) findViewById(R.id.startBtn);
		resultTextView = (TextView) findViewById(R.id.resultTextView);
		startBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String url = "http://192.168.1.122:9090/myWebApp/download";
				MyAsyncTaskActivity.this.new DownloadAsyncTask().execute(url);
			}
		});
	}

	private class DownloadAsyncTask extends AsyncTask<String, Integer, Integer> {

		@Override
		protected Integer doInBackground(String... params) {
			String url = params[0];
			BufferedInputStream bis = null;
			FileOutputStream fos = null;
			String saveFile = getApplicationContext().getFilesDir().getAbsolutePath() + "/aaa";
			try {
				File newFile = new File(saveFile);
				if (!newFile.getParentFile().exists()) {
					newFile.getParentFile().mkdirs();
				}
				fos = new FileOutputStream(newFile);
				HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
				if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
					throw new Exception("文件下载错误！");
				}
				InputStream is = con.getInputStream();
				bis = new BufferedInputStream(is);
				byte[] b = new byte[1024];
				int length = 0;
				int sum = con.getContentLength();
				int finished = 0;
				while ((length = bis.read(b)) != -1) {
					fos.write(b, 0, length);
					finished += length;
					publishProgress(finished * 100 / sum);
				}
				fos.flush();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (bis != null) {
					try {
						bis.close();
					} catch (IOException e) {
					}
				}
				if (fos != null) {
					try {
						fos.close();
					} catch (IOException e) {
					}
				}
			}
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			resultTextView.setText(values[0] + "%");
			downloadProgressBar.setProgress(values[0]);
		}

		@Override
		protected void onPreExecute() {
			startBtn.setEnabled(false);
		}

		@Override
		protected void onPostExecute(Integer result) {
			resultTextView.setText("100%");
			downloadProgressBar.setProgress(100);
			startBtn.setEnabled(true);
		}
	}
}
