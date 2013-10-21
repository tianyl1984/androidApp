package com.tyl.preference;

import com.tyl.R;
import com.tyl.R.xml;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PreferenceSetting extends PreferenceActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
	}
}
