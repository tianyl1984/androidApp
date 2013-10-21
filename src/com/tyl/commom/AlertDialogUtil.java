package com.tyl.commom;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class AlertDialogUtil {

	public static void showMessage(String message, String title, Context context) {
		AlertDialog ad = new AlertDialog.Builder(context).setTitle(title).setMessage(message).create();
		ad.setButton(AlertDialog.BUTTON_NEUTRAL, "关闭", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		ad.show();
	}
}
