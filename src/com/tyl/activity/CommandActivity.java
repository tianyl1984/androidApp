package com.tyl.activity;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tyl.R;

public class CommandActivity extends Activity {

	TextView resultTextView;

	EditText commandEditText;

	Button exeBtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.command);
		resultTextView = (TextView) findViewById(R.id.resultTextView);
		commandEditText = (EditText) findViewById(R.id.commandEditText);
		exeBtn = (Button) findViewById(R.id.exeBtn);
		exeBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String cmd = commandEditText.getText().toString().trim();
				if (cmd.length() == 0) {
					return;
				}
				commandEditText.setText("");
				Process pro = null;
				try {
					pro = Runtime.getRuntime().exec(cmd);
					StringBuffer sb = new StringBuffer();
					BufferedReader br = new BufferedReader(new InputStreamReader(pro.getInputStream()));
					String temp = null;
					while ((temp = br.readLine()) != null) {
						sb.append(temp + "\n");
					}
					pro.waitFor();
					resultTextView.setText(sb.toString());
				} catch (Exception e) {
					e.printStackTrace();
					resultTextView.setText(e.getMessage());
				} finally {
					if (pro != null) {
						pro.destroy();
					}
				}
			}
		});
	}
}
