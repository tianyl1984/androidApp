package com.tyl.activity;

import com.tyl.R;
import com.tyl.R.id;
import com.tyl.R.layout;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class StyleTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.style_test);
		findViewById(R.id.btnprogress).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Toast.makeText(StyleTestActivity.this, "测试", Toast.LENGTH_SHORT).show();
			}
		});

		ListView lv = (ListView) findViewById(R.id.content);

		lv.setAdapter(new ListViewAdapter());

	}

	private class ListViewAdapter extends BaseAdapter {
		// 这里返回10行，ListView有多少行取决于getCount()方法
		public int getCount() {
			return 10;
		}

		public Object getItem(int arg0) {
			return null;
		}

		public long getItemId(int arg0) {
			return 0;
		}

		public View getView(int position, View v, ViewGroup parent) {

			final LayoutInflater inflater = LayoutInflater.from(getApplicationContext());

			if (v == null) {
				v = inflater.inflate(R.layout.listview_style_test, null);
			}
			TextView view1 = (TextView) v.findViewById(R.id.view1);
			TextView view2 = (TextView) v.findViewById(R.id.view2);

			view1.setText("ViewTest" + position);
			view2.setText("TestView" + position);
			return v;
		}

	}
}
