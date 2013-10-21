package com.tyl.tab;

import android.app.TabActivity;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.Toast;

import com.tyl.R;

public class TabHostTest extends TabActivity {
	TabHost tabHost = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhost_test);
		Resources res = getResources();
		tabHost = getTabHost();
		TabSpec tab1 = tabHost.newTabSpec("tab1");
		tab1.setContent(R.id.Tab1);
		tab1.setIndicator("tab1Test", res.getDrawable(R.drawable.p1));
		tabHost.addTab(tab1);

		TabSpec tab2 = tabHost.newTabSpec("tab2");
		tab2.setContent(R.id.Tab2);
		tab2.setIndicator("tab2Test", res.getDrawable(R.drawable.p2));
		tabHost.addTab(tab2);

		tabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				Toast.makeText(TabHostTest.this, tabId, Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		menu.clear();
		menu.add(0, 0, 0, "关于");
		menu.add(0, 1, 1, "退出");
		if (tabHost.getCurrentTabTag().equals("tab1")) {
			menu.add(0, 2, 1, "Tab1");
		}
		if (tabHost.getCurrentTabTag().equals("tab2")) {
			menu.add(0, 3, 1, "Tab2");
		}
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// menu.add(0, 0, 0, "关于");
		// menu.add(0, 1, 1, "退出");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			Toast.makeText(TabHostTest.this, "关于", Toast.LENGTH_SHORT).show();
			break;
		case 1:
			Toast.makeText(TabHostTest.this, "退出", Toast.LENGTH_SHORT).show();
			break;
		case 2:
			Toast.makeText(TabHostTest.this, "Tab1", Toast.LENGTH_SHORT).show();
			break;
		case 3:
			Toast.makeText(TabHostTest.this, "Tab2", Toast.LENGTH_SHORT).show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
