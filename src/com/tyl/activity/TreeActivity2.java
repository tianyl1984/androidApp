package com.tyl.activity;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.tyl.R;
import com.tyl.commom.tree.Node;
import com.tyl.commom.tree.TreeAdapter;

public class TreeActivity2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tree2);
		ListView tree2 = (ListView) findViewById(R.id.tree2);
		Node rootNode = new Node("SD卡根目录", "root");
		rootNode.setExpanded(true);
		addChildren(rootNode, Environment.getExternalStorageDirectory());
		TreeAdapter adapter = new TreeAdapter(this, rootNode.getChildren());
		tree2.setAdapter(adapter);
		tree2.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView parent, View view, int position, long id) {
				((TreeAdapter) parent.getAdapter()).expandOrCollapse(position);
			}
		});
	}

	private void addChildren(Node rootNode, File file) {
		if (!file.exists()) {
			return;
		}
		File[] files = file.listFiles();
		for (File f : files) {
			Node n = new Node(f.getName(), f.getAbsolutePath());
			if (f.isDirectory()) {
				n.setCheckBox(false);
				addChildren(n, f);
			}
			rootNode.add(n);
		}
	}
}
