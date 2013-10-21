package com.tyl.commom;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

public class StartNewActivity implements OnClickListener {

	private Context context;

	private Class<?> clzss;

	private Intent intent;

	public StartNewActivity() {

	}

	public StartNewActivity(Context context, Intent intent) {
		super();
		this.context = context;
		this.intent = intent;
	}

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public Class<?> getClzss() {
		return clzss;
	}

	public void setClzss(Class<?> clzss) {
		this.clzss = clzss;
	}

	public void setIntent(Intent intent) {
		this.intent = intent;
	}

	public StartNewActivity(Context context, Class<?> clzss) {
		super();
		this.context = context;
		this.clzss = clzss;
	}

	@Override
	public void onClick(View v) {
		context.startActivity(getIntent());
	}

	private Intent getIntent() {
		if (intent != null) {
			return intent;
		}
		Intent newIntent = new Intent(context, clzss);
		return newIntent;
	}

}
