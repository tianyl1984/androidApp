package com.tyl.activity;

import java.io.DataOutputStream;
import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.provider.Settings.SettingNotFoundException;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.tyl.R;
import com.tyl.commom.AlertDialogUtil;
import com.tyl.commom.WifiUtil;

public class SystemInfoActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.system_info);
		// 屏幕分辨率
		findViewById(R.id.btnScreem).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DisplayMetrics dm = new DisplayMetrics();
				getWindowManager().getDefaultDisplay().getMetrics(dm);
				Toast.makeText(SystemInfoActivity.this, "屏幕分辨率:" + dm.widthPixels + "*" + dm.heightPixels, Toast.LENGTH_LONG).show();
			}
		});
		// 拨打电话
		final EditText phoneNum = (EditText) findViewById(R.id.phoneNum);
		findViewById(R.id.btnPhone).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String iptStr = phoneNum.getText().toString();
				if (iptStr.trim().length() > 0) {
					Intent intent = new Intent("android.intent.action.CALL", Uri.parse("tel:13800138000"));
					startActivity(intent);
				} else {
					Toast.makeText(SystemInfoActivity.this, "提示：号码不能为空", Toast.LENGTH_LONG).show();
				}
			}

		});
		// 读取短信
		findViewById(R.id.btnMessage).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				try {
					String[] projection = new String[] { "address", "person", "body", "date" };
					StringBuilder sb = new StringBuilder();
					Cursor cur = managedQuery(Uri.parse("content://sms/"), projection, null, null, "date desc");
					if (cur.moveToFirst()) {
						do {
							String address = cur.getString(cur.getColumnIndex("address"));
							String person = cur.getString(cur.getColumnIndex("person"));
							String body = cur.getString(cur.getColumnIndex("body"));
							Long date = cur.getLong(cur.getColumnIndex("date"));
							SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
							sb.append(address + ":" + person + ":" + sdf.format(new Date(date)) + ":" + body);
							break;
						} while (cur.moveToNext());
					} else {
						sb.append("没有信息！");
					}
					new AlertDialog.Builder(SystemInfoActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("短信内容").setMessage(sb.toString()).show();
				} catch (Exception e) {
					Log.d("Sms Query", e.getMessage());
				}
			}

		});
		// 设为全屏
		findViewById(R.id.btnFullScreem).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// 全屏
				SystemInfoActivity.this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			}
		});
		// wifi速度
		findViewById(R.id.btnWifiSpeed).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WifiManager wifiManager = (WifiManager) SystemInfoActivity.this.getSystemService(Context.WIFI_SERVICE);
				WifiInfo wifiInfo = wifiManager.getConnectionInfo();
				if (wifiInfo == null || wifiManager.getWifiState() != WifiManager.WIFI_STATE_ENABLED) {
					Toast.makeText(SystemInfoActivity.this, "无WIFI信息", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(SystemInfoActivity.this, "LinkSpeed:" + wifiInfo.getLinkSpeed(), Toast.LENGTH_SHORT).show();
				}
			}
		});
		// WifiUtil测试
		findViewById(R.id.btnWifiTest).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (WifiUtil.isWiFiActive(SystemInfoActivity.this)) {
					AlertDialogUtil.showMessage("Wifi可用", "Wifi状态", SystemInfoActivity.this);
				} else {
					WifiUtil.jumpToWifiSetting(SystemInfoActivity.this);
				}
			}
		});

		// 修改安装任意文件
		findViewById(R.id.btnEnableInstallAll).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					int val = Settings.Secure.getInt(getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS);
					if (val == 0) {
						Settings.Secure.putInt(getContentResolver(), Settings.Secure.INSTALL_NON_MARKET_APPS, 1);
						Toast.makeText(SystemInfoActivity.this, "由于权限问题不能修改", Toast.LENGTH_SHORT).show();
					}
					if (val == 1) {
						Toast.makeText(SystemInfoActivity.this, "已允许不需修改", Toast.LENGTH_SHORT).show();
					}
				} catch (SettingNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

		findViewById(R.id.btnSystemInfo).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String msg = "";
				msg += "CPU_ABI:" + Build.CPU_ABI;
				msg += "\nDEVICE:" + Build.DEVICE;
				msg += "\nDISPLAY:" + Build.DISPLAY;
				msg += "\nHARDWARE:" + Build.HARDWARE;
				msg += "\nHOST:" + Build.HOST;
				msg += "\nID:" + Build.ID;
				msg += "\nMODEL:" + Build.MODEL;
				msg += "\nPRODUCT:" + Build.PRODUCT;
				msg += "\nRADIO:" + Build.RADIO;
				msg += "\nUSER:" + Build.USER;
				Toast.makeText(SystemInfoActivity.this, msg, Toast.LENGTH_LONG).show();
			}
		});
		// 获取root权限
		findViewById(R.id.btnGetRoot).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Process process = null;
				DataOutputStream os = null;
				boolean flag = true;
				try {
					String cmd = "chmod 777 " + getPackageCodePath();
					process = Runtime.getRuntime().exec("su"); // 切换到root帐号
					os = new DataOutputStream(process.getOutputStream());
					os.writeBytes(cmd + "\n");
					os.writeBytes("exit\n");
					os.flush();
					process.waitFor();
				} catch (Exception e) {
					e.printStackTrace();
					flag = false;
				} finally {
					try {
						if (os != null) {
							os.close();
						}
						process.destroy();
					} catch (Exception e) {
					}
				}
				if (flag) {
					Toast.makeText(SystemInfoActivity.this, "成功！！！" + getPackageCodePath(), Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(SystemInfoActivity.this, "失败！！！" + getPackageCodePath(), Toast.LENGTH_SHORT).show();
				}
			}
		});
	}
}
