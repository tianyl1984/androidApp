package com.tyl.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.tyl.R;
import com.tyl.commom.DoNothingOnClickListener;

public class AllPackageInfo extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 去标题，必须在setContentView之前
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 去掉通知栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.appgrids);
		((GridView) findViewById(R.id.gvApp)).setAdapter(new PackageInfoAdapter(this));
		// ((GridView) findViewById(R.id.gvApp)).setOnClickListener(new
		// OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// }
		// });
	}

	class PackageInfoAdapter extends BaseAdapter {

		List<PackageInfo> packageInfos = new ArrayList<PackageInfo>();

		LayoutInflater layoutInflater;

		public PackageInfoAdapter(Context context) {
			List<PackageInfo> all = context.getPackageManager().getInstalledPackages(PackageManager.GET_ACTIVITIES);// PackageManager.GET_UNINSTALLED_PACKAGES
			for (PackageInfo pkInfo : all) {
				if ((pkInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
					packageInfos.add(pkInfo);
				}
			}
			layoutInflater = LayoutInflater.from(context);
		}

		public int getCount() {
			return packageInfos.size();
		}

		public Object getItem(int position) {
			return packageInfos.get(position);
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			final PackageInfo pkgInfo = packageInfos.get(position);
			View view = layoutInflater.inflate(R.layout.package_list, null);
			ImageView iv = (ImageView) view.findViewById(R.id.item_icon);
			iv.setImageDrawable(pkgInfo.applicationInfo.loadIcon(getPackageManager()));
			iv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					StringBuffer message = new StringBuffer();
					message.append("名称:" + pkgInfo.applicationInfo.loadLabel(getPackageManager()) + "\n");
					message.append("VersionName:" + pkgInfo.versionName + "\n");
					message.append("VersionCode:" + pkgInfo.versionCode + "\n");
					final AlertDialog ad = new AlertDialog.Builder(AllPackageInfo.this).create();
					ad.setTitle("详细信息");
					ad.setButton(AlertDialog.BUTTON_POSITIVE, "关闭", new DoNothingOnClickListener());
					ad.setMessage(message.toString());
					ad.show();
				}
			});
			TextView tv = (TextView) view.findViewById(R.id.item_appname);
			tv.setText(pkgInfo.applicationInfo.loadLabel(getPackageManager()));
			return view;
		}
	}

}
