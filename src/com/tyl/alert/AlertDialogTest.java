package com.tyl.alert;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RatingBar;
import android.widget.Toast;

import com.tyl.R;

public class AlertDialogTest extends Activity {

	private static final int OK = 0;
	private static final int CANCEL = 1;
	private static final int NEUTRAL = 2;

	private Handler handler = new Handler(new Callback() {
		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case OK:
				Toast.makeText(AlertDialogTest.this, "OK", Toast.LENGTH_SHORT).show();
				// try {
				// Class c =
				// Class.forName("com.android.internal.app.AlertController");
				// Toast.makeText(AlertDialogTest.this, "OK:" +
				// c.getDeclaredMethods().length, Toast.LENGTH_SHORT).show();
				// } catch (Exception e) {
				// e.printStackTrace();
				// }
				break;
			case CANCEL:
				Toast.makeText(AlertDialogTest.this, "Cancel", Toast.LENGTH_SHORT).show();
				break;
			case NEUTRAL:
				Toast.makeText(AlertDialogTest.this, "Neutral", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
			return false;
		}
	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alertdialog_test);
		findViewById(R.id.btnCommon).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AlertDialog ad = new AlertDialog.Builder(AlertDialogTest.this).setTitle("MyTitle").setMessage("普通的弹出框").create();
				// ad.setButton("确定", Message.obtain(handler, OK));
				// ad.setButton2("取消", Message.obtain(handler, CANCEL));
				ad.setButton(DialogInterface.BUTTON_POSITIVE, "OK", Message.obtain(handler, OK));
				ad.setButton(DialogInterface.BUTTON_NEGATIVE, "cancel", Message.obtain(handler, CANCEL));
				ad.setButton(DialogInterface.BUTTON_NEUTRAL, "neutral", Message.obtain(handler, CANCEL));
				ad.show();
				Toast.makeText(AlertDialogTest.this, "AlertDialog位置不能设置", Toast.LENGTH_SHORT).show();
			}
		});

		findViewById(R.id.btnPopupWindow).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LayoutInflater li = LayoutInflater.from(AlertDialogTest.this);
				final View view = li.inflate(R.layout.custom_popwindow, null);
				final PopupWindow popWin = new PopupWindow(view, 300, 200, true);
				popWin.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
				popWin.setOutsideTouchable(true); // 设置是否允许在外点击使其消失
				view.findViewById(R.id.btnOK).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(AlertDialogTest.this, "OK", Toast.LENGTH_SHORT).show();
						popWin.dismiss();
					}
				});
				view.findViewById(R.id.btnCancel).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						Toast.makeText(AlertDialogTest.this, "Cancel", Toast.LENGTH_SHORT).show();
						popWin.dismiss();
					}
				});
				// popWin.showAtLocation(AlertDialogTest.this.findViewById(R.id.btnPopupWindow),
				// Gravity.LEFT, 50, 0);
				popWin.showAsDropDown(AlertDialogTest.this.findViewById(R.id.btnPopupWindow), 50, 0);
				Toast.makeText(AlertDialogTest.this, "PopupWindow可以设置位置", Toast.LENGTH_SHORT).show();
			}
		});

		findViewById(R.id.btnSelect).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final CharSequence[] citys = new String[5];
				citys[0] = "北京";
				citys[1] = "上海";
				citys[2] = "广州";
				citys[3] = "天津";
				citys[4] = "重庆";
				AlertDialog ad = new AlertDialog.Builder(AlertDialogTest.this).setIcon(android.R.drawable.alert_dark_frame).setTitle("请选择").setItems(citys, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						Toast.makeText(AlertDialogTest.this, citys[which], Toast.LENGTH_SHORT).show();
					}
				}).create();
				ad.show();
			}
		});

		findViewById(R.id.btnCustomDialog).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// final Dialog ad = new Dialog(AlertDialogTest.this);
				final Dialog ad = new AlertDialog.Builder(AlertDialogTest.this).create();
				ad.show();
				ad.setContentView(R.layout.custom_alertdialog);
				ad.getWindow().findViewById(R.id.btnShowResult).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						RatingBar rb = (RatingBar) ad.getWindow().findViewById(R.id.ratingBar1);
						Toast.makeText(AlertDialogTest.this, "" + rb.getRating(), Toast.LENGTH_SHORT).show();
					}
				});
				ad.getWindow().findViewById(R.id.btnCloseDialog).setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						ad.dismiss();
					}
				});
			}
		});

		findViewById(R.id.btnCustomDialog2).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LayoutInflater li = LayoutInflater.from(AlertDialogTest.this);
				final View view = li.inflate(R.layout.custom_alertdialog2, null);
				AlertDialog.Builder builder = new AlertDialog.Builder(AlertDialogTest.this);
				builder.setView(view);
				AlertDialog ad = builder.create();
				ad.setTitle("请填写配置信息");
				ad.setButton(AlertDialog.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						String msg = "";
						EditText ipText = (EditText) view.findViewById(R.id.ipText);
						msg += ipText.getText() + ":";
						EditText portText = (EditText) view.findViewById(R.id.portText);
						msg += portText.getText();
						Toast.makeText(AlertDialogTest.this, msg, Toast.LENGTH_LONG).show();
					}
				});
				ad.setButton(AlertDialog.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {

					}
				});
				ad.show();
			}
		});
	}
}
