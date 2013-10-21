package com.tyl.activity;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.tyl.R;

public class TreeActivity extends Activity {

	ExpandableListView tree;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tree);
		tree = (ExpandableListView) findViewById(R.id.tree);
		final File rootFile = Environment.getExternalStorageDirectory();
		tree.setAdapter(new TreeExpandableListAdapter(this, rootFile));
	}

	public class TreeExpandableListAdapter extends BaseExpandableListAdapter {

		private File rootFile;

		private Activity context;

		public TreeExpandableListAdapter(Activity context, File rootFile) {
			this.context = context;
			this.rootFile = rootFile;
		}

		@Override
		public int getGroupCount() {
			return rootFile.list().length;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			File f = rootFile.listFiles()[groupPosition];
			if (!f.exists() || f.isFile() || f.list() != null) {
				return 0;
			}
			return f.list().length;
		}

		@Override
		public Object getGroup(int groupPosition) {
			return new File(rootFile.list()[groupPosition]).getName();
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			File f = rootFile.listFiles()[groupPosition];
			if (!f.exists()) {
				return null;
			}
			return f.listFiles()[childPosition].getName();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return groupPosition * 100 + childPosition;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				LayoutInflater li = LayoutInflater.from(context);
				view = li.inflate(R.layout.tree_item, null);
			}

			ImageView img = (ImageView) view.findViewById(R.id.img);
			if (getChildrenCount(groupPosition) > 0) {
				if (isExpanded) {
					img.setImageResource(R.drawable.aaa);
				} else {
					img.setImageResource(R.drawable.bbb);
				}
			} else {
				img.setImageDrawable(null);
			}
			TextView infoView = (TextView) view.findViewById(R.id.infoView);
			infoView.setText(getGroup(groupPosition).toString());
			return view;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
			File f = rootFile.listFiles()[groupPosition];
			File sdRoot = Environment.getExternalStorageDirectory();
			int level = 0;
			File curFile = new File(f.getAbsolutePath());
			while (true) {
				level++;
				if (sdRoot.getAbsolutePath().equals(curFile.getAbsolutePath())) {
					break;
				}
				curFile = curFile.getParentFile();
			}
			if (f.isFile() || childPosition > 0) {
				if (childPosition > 0) {
					View view = new View(context);
					AbsListView.LayoutParams lp = new AbsListView.LayoutParams(0, 0);
					view.setLayoutParams(lp);
					return view;
				}
				View view = convertView;
				if (view == null) {
					LayoutInflater li = LayoutInflater.from(context);
					view = li.inflate(R.layout.tree_item, null);
				}
				ImageView img = (ImageView) view.findViewById(R.id.img);
				img.setImageDrawable(null);
				TextView infoView = (TextView) view.findViewById(R.id.infoView);
				infoView.setText(getGroup(groupPosition).toString());
				view.setPadding(20 * level, 0, 0, 0);
				return view;
			} else {
				AbsListView.LayoutParams lp = new AbsListView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, f.list().length * 80);
				ExpandableListView treeView = null;
				if (convertView instanceof ExpandableListView) {
					treeView = (ExpandableListView) convertView;
				}
				if (treeView == null) {
					treeView = new ExpandableListView(context);
				}
				treeView.setLayoutParams(lp);
				treeView.setGroupIndicator(null);
				treeView.setAdapter(new TreeExpandableListAdapter(context, f));
				treeView.setPadding(20 * level, 0, 0, 0);
				return treeView;
			}
		}

		@Override
		public void onGroupCollapsed(int groupPosition) {
			super.onGroupCollapsed(groupPosition);
			File sdRoot = Environment.getExternalStorageDirectory();
			if (!sdRoot.getAbsolutePath().equals(rootFile.getAbsolutePath())) {
				// ExpandableListView root = (ExpandableListView)
				// context.findViewById(R.id.tree);
				// root.computeScroll();
			}
		}

		@Override
		public void onGroupExpanded(int groupPosition) {
			super.onGroupExpanded(groupPosition);
			File sdRoot = Environment.getExternalStorageDirectory();
			if (!sdRoot.getAbsolutePath().equals(rootFile.getAbsolutePath())) {
				// ExpandableListView root = (ExpandableListView)
				// context.findViewById(R.id.tree);
				// root.computeScroll();
			}
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return false;
		}

	}
}
