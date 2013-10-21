package com.tyl.activity;

import com.tyl.R;
import com.tyl.R.id;
import com.tyl.R.layout;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class RotationTest extends Activity {

	TextView resultTextView;
	Button showResultBtn;

	String result = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.rotation);
		resultTextView = (TextView) findViewById(R.id.resultTextView);
		findViewById(R.id.addTextBtn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				result = resultTextView.getText() + ":" + System.currentTimeMillis();
				resultTextView.setText(result);
			}
		});
		findViewById(R.id.delTextBtn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				resultTextView.setText("");
				result = "";
			}
		});
		findViewById(R.id.showResultBtn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(RotationTest.this, resultTextView.getText(), Toast.LENGTH_LONG).show();
				Toast.makeText(RotationTest.this, result, Toast.LENGTH_LONG).show();
			}
		});
	}

	@Override
	protected void onStart() {
		super.onStart();
		result = resultTextView.getText() + ":onStart";
		resultTextView.setText(result);
	}

	@Override
	protected void onResume() {
		super.onResume();
		result = resultTextView.getText() + ":onResume";
		resultTextView.setText(result);
	}

	@Override
	protected void onPostResume() {
		super.onPostResume();
		result = resultTextView.getText() + ":onPostResume";
		resultTextView.setText(result);
	}

	@Override
	protected void onPause() {
		super.onPause();
		result = resultTextView.getText() + ":onPause";
		resultTextView.setText(result);
	}

	@Override
	protected void onStop() {
		super.onStop();
		result = resultTextView.getText() + ":onStop";
		resultTextView.setText(result);
	}

	@Override
	protected void onRestart() {
		super.onRestart();
		result = resultTextView.getText() + ":onRestart";
		resultTextView.setText(result);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		result = resultTextView.getText() + ":onConfigurationChanged";
		resultTextView.setText(result);
	}
}
