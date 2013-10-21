package com.tyl.menu;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.tyl.R;

public class CommonMenu extends Activity {

	private TextView resultTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.common_menu);
		resultTextView = (TextView) findViewById(R.id.resultTextView);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {// 每次都调用，可动态改变菜单
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 每组按顺序取一个组成菜单
		menu.add(0, 0, 0, "关于");
		menu.add(0, 1, 1, "退出");
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			new AlertDialog.Builder(CommonMenu.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Info").setMessage("菜单测试。。。").show();
			break;
		case 1:
			this.finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
