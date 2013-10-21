package com.tyl.thread;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tyl.R;
import com.tyl.commom.Count;

public class MyThreadActivity extends Activity {

	TextView textView = null;

	Button btnStart = null;

	// ExecutorService executorService;

	ThreadPoolExecutor threadPoolExecutor;

	Count count = null;

	private static final int MSG_FLUSH_COUNT = 0;

	private Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_FLUSH_COUNT:
				textView.setText(count.getSumCount() + ":" + msg.obj.toString());
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
		setContentView(R.layout.thread_test);
		threadPoolExecutor = new ThreadPoolExecutor(2, 5, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
		// executorService = Executors.newCachedThreadPool();
		count = new Count();
		textView = (TextView) findViewById(R.id.textView);
		btnStart = (Button) findViewById(R.id.btnStart);
		btnStart.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				threadPoolExecutor.submit(new Thread(new Runnable() {

					@Override
					public void run() {
						// 不能使用 Looper.prepare();
						for (int i = 0; i < 10; i++) {
							count.addSuccessCount();
							handler.sendMessage(Message.obtain(handler, MSG_FLUSH_COUNT, "" + Thread.currentThread().getId()));
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
						// Looper.loop();
					}
				}));
			}
		});
		findViewById(R.id.btnExecutor).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String msg = "";
				msg += "ActiveCount:" + threadPoolExecutor.getActiveCount() + "\n";
				msg += "CompletedTaskCount:" + threadPoolExecutor.getCompletedTaskCount() + "\n";
				msg += "CorePoolSize:" + threadPoolExecutor.getCorePoolSize() + "\n";
				msg += "KeepAliveTime:" + threadPoolExecutor.getKeepAliveTime(TimeUnit.SECONDS) + "\n";
				msg += "LargestPoolSize:" + threadPoolExecutor.getLargestPoolSize() + "\n";
				msg += "MaximumPoolSize:" + threadPoolExecutor.getMaximumPoolSize() + "\n";
				msg += "PoolSize:" + threadPoolExecutor.getPoolSize() + "\n";
				msg += "TaskCount:" + threadPoolExecutor.getTaskCount() + "\n";
				msg += "QueueSize:" + threadPoolExecutor.getQueue().size() + "\n";
				msg += "isShutdown:" + threadPoolExecutor.isShutdown() + "\n";
				msg += "isTerminated:" + threadPoolExecutor.isTerminated() + "\n";
				msg += "isTerminating:" + threadPoolExecutor.isTerminating() + "\n";
				// msg += "isShutdown:" + executorService.isShutdown() + ";";
				// msg += "isTerminated:" + executorService.isTerminated() +
				// ";";
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
			}
		});
	}
}
