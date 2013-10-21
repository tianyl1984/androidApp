package com.tyl.commom;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Looper;

public class ProgressThreadWrap {

	private Context context;

	private RunnableWrap runnableWrap;

	private String tipInfo;

	public ProgressThreadWrap(Context context, RunnableWrap runnableWrap) {
		this.context = context;
		this.runnableWrap = runnableWrap;
		this.tipInfo = "正在操作，请等待...";
	}

	public ProgressThreadWrap(Context context, RunnableWrap runnableWrap, String tipInfo) {
		this.context = context;
		this.runnableWrap = runnableWrap;
		this.tipInfo = tipInfo;
	}

	public void start() {
		final ProgressDialog pd = new ProgressDialog(context);
		pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pd.setMessage(tipInfo);
		pd.setCancelable(true);
		pd.show();
		new Thread(new Runnable() {

			@Override
			public void run() {
				Looper.prepare();
				runnableWrap.run(pd);
				Looper.loop();
			}
		}).start();
	}
}
