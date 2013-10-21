package com.tyl.preference;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.tyl.R;
import com.tyl.commom.AlertDialogUtil;

public class PreferenceTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.perference_test);

		findViewById(R.id.btnSetting).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(PreferenceTestActivity.this, PreferenceSetting.class), 0);
			}
		});
		findViewById(R.id.btnReadOther).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					Context otherAppContext = createPackageContext("com.unitever.android.demo", Context.CONTEXT_IGNORE_SECURITY);
					SharedPreferences sharedPreferences = otherAppContext.getSharedPreferences("MainActivity", Context.MODE_WORLD_READABLE);
					Toast.makeText(PreferenceTestActivity.this, sharedPreferences.getString("MainActivity_url", ""), Toast.LENGTH_LONG).show();
				} catch (NameNotFoundException e) {
					e.printStackTrace();
					AlertDialogUtil.showMessage("所读app不存在！", "错误", PreferenceTestActivity.this);
				}
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

	}
}
