package com.tyl.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.tyl.R;

public class I18nActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.i18n);
		TextView textView2 = (TextView) findViewById(R.id.textView2);
		textView2.setText(R.string.hello2);
		final List<String> languages = new ArrayList<String>();
		languages.add("中文");
		languages.add("English");
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, languages);
		Spinner spinner = (Spinner) findViewById(R.id.languageSpinner);
		spinner.setAdapter(adapter);
		Resources res = getResources();
		Configuration config = res.getConfiguration();
		if (config.locale == Locale.ENGLISH) {
			spinner.setSelection(1);
		}
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				String lan = languages.get(position);
				Resources res = getResources();
				Configuration config = res.getConfiguration();
				if (config.locale == Locale.CHINA && lan.equals("中文")) {
					return;
				}
				if (config.locale == Locale.ENGLISH && !lan.equals("中文")) {
					return;
				}
				if (lan.equals("中文")) {
					config.locale = Locale.CHINA;
				} else {
					config.locale = Locale.ENGLISH;
				}
				DisplayMetrics dm = res.getDisplayMetrics();
				res.updateConfiguration(config, dm);
				Intent intent = new Intent(I18nActivity.this, I18nActivity.class);
				I18nActivity.this.startActivity(intent);
				I18nActivity.this.finish();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}

		});
	}
}
