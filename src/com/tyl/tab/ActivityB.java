package com.tyl.tab;

import com.tyl.R;
import com.tyl.R.id;
import com.tyl.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ActivityB extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		((TextView) findViewById(R.id.textView)).setText("ActivityB");
		Toast.makeText(this, "ActivityB", Toast.LENGTH_SHORT).show();
	}
}
