package com.tyl.tab;

import com.tyl.R;
import com.tyl.R.drawable;
import com.tyl.R.id;
import com.tyl.R.layout;

import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class ActivityGroupDemo extends ActivityGroup {

	GridView gvTopBar;
	LinearLayout container;

	int[] images = new int[] { R.drawable.icon, R.drawable.normal, R.drawable.focused, R.drawable.pressed };

	private ImageAdapter imageAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_group_gridview);
		gvTopBar = (GridView) findViewById(R.id.gvTopBar);
		container = (LinearLayout) findViewById(R.id.container);

		gvTopBar.setNumColumns(images.length);
		gvTopBar.setSelector(new ColorDrawable(Color.TRANSPARENT));

		int width = this.getWindowManager().getDefaultDisplay().getWidth() / images.length;
		imageAdapter = new ImageAdapter(this, images, width, 50, R.drawable.background);
		gvTopBar.setAdapter(imageAdapter);
		gvTopBar.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				switchActivity(position);
			}
		});
		switchActivity(0);
	}

	private void switchActivity(int id) {
		imageAdapter.setFocus(id);// 选中项获得高亮
		container.removeAllViews();// 必须先清除容器中所有的View
		Intent intent = null;
		if (id == 0 || id == 2) {
			intent = new Intent(ActivityGroupDemo.this, ActivityA.class);
		} else if (id == 1 || id == 3) {
			intent = new Intent(ActivityGroupDemo.this, ActivityB.class);
		}
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		// Activity 转为 View
		Window subActivity = getLocalActivityManager().startActivity("subActivity", intent);
		// 容器添加View
		container.addView(subActivity.getDecorView(), LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
	}

	private class ImageAdapter extends BaseAdapter {

		private Context context;

		private ImageView[] imageViews;

		private int selResId;

		public ImageAdapter(Context context, int[] picIds, int width, int height, int selResId) {
			this.context = context;
			this.selResId = selResId;
			imageViews = new ImageView[picIds.length];
			for (int i = 0; i < picIds.length; i++) {
				imageViews[i] = new ImageView(context);
				imageViews[i].setLayoutParams(new GridView.LayoutParams(width, height));// 设置ImageView宽高
				imageViews[i].setAdjustViewBounds(false);
				imageViews[i].setPadding(2, 2, 2, 2);
				imageViews[i].setImageResource(picIds[i]);
			}
		}

		public int getCount() {
			return imageViews.length;
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView imageView;
			if (convertView == null) {
				imageView = imageViews[position];
			} else {
				imageView = (ImageView) convertView;
			}
			return imageView;
		}

		/**
		 * 设置选中的效果
		 */
		public void setFocus(int index) {
			for (int i = 0; i < imageViews.length; i++) {
				if (i != index) {
					imageViews[i].setBackgroundResource(0);// 恢复未选中的样式
				}
			}
			imageViews[index].setBackgroundResource(selResId);// 设置选中的样式
		}
	}
}
