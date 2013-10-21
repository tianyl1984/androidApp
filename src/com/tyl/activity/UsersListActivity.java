package com.tyl.activity;

import java.util.ArrayList;
import java.util.List;

import com.tyl.R;
import com.tyl.R.id;
import com.tyl.R.layout;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class UsersListActivity extends ListActivity {

	TextView userNameView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_list);

		userNameView = (TextView) findViewById(R.id.userNameView);

		List<String> users = new ArrayList<String>();
		users.add("aaa");
		users.add("bbb");
		users.add("ccc");
		users.add("ddd");
		Log.v("aaa", "asdf");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_row, users);
		this.setListAdapter(adapter);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		userNameView.setText(l.getItemAtPosition(position).toString());
	}
}
