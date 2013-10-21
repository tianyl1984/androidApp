package com.tyl.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tyl.R;

public class IntentDemoActivity extends Activity {
	Button btnBackMain;
	TextView firstIntendMessage;
	EditText rtnText;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.intentdemo);

		firstIntendMessage = (TextView) findViewById(R.id.firstIntendMessage);

		Bundle bundle = this.getIntent().getExtras();
		if (bundle != null) {
			firstIntendMessage.setText(bundle.getString("firstTextInput"));
		}

		btnBackMain = (Button) findViewById(R.id.btnBackMain);
		rtnText = (EditText) findViewById(R.id.rtnText);
		btnBackMain.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(IntentDemoActivity.this, BaseOperateActivity.class);
				Bundle bundle = new Bundle();
				bundle.putString("result", rtnText.getText().toString());
				intent.putExtras(bundle);
				setResult(123, intent);
				IntentDemoActivity.this.finish();
			}
		});
	}

	@Override
	public void onBackPressed() {
		// 禁止返回
		// super.onBackPressed();
	}
}
