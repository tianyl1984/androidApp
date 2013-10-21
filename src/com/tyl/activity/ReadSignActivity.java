package com.tyl.activity;

import com.tyl.R;
import com.tyl.R.id;
import com.tyl.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class ReadSignActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.read_sign);
		EditText locationEditText = (EditText) findViewById(R.id.locationEditText);
		TextView readResultView = (TextView) findViewById(R.id.readResultView);
		findViewById(R.id.readBtn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// PKCS7 pkcs7 = new PKCS7(null);
			}
		});
	}
}
