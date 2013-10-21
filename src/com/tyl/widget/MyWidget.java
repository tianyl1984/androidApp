package com.tyl.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.tyl.R;
import com.tyl.commom.DateUtil;

public class MyWidget extends AppWidgetProvider {

	private static final String tag = "MyWidget";

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
		super.onUpdate(context, appWidgetManager, appWidgetIds);
		for (int i = 0; i < appWidgetIds.length; i++) {
			RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget);
			views.setTextViewText(R.id.widgetView, "WidgetUpdate[onUpdate]:" + appWidgetIds[i] + ":" + DateUtil.getDateSecond());

			// Intent intent = new Intent();
			// intent.setClass(context, MyWidgetConfig.class);
			// intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
			// appWidgetIds[i]);
			// PendingIntent pendingIntent = PendingIntent.getActivity(context,
			// 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			// views.setOnClickPendingIntent(R.id.widgetImage, pendingIntent);

			appWidgetManager.updateAppWidget(appWidgetIds[i], views);
			addWidgetId(context, appWidgetIds);
		}
	}

	private void addWidgetId(Context context, int[] appWidgetIds) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("MyWidget", Context.MODE_PRIVATE);
		String widgetId = sharedPreferences.getString("widgetId", "");
		for (int id : appWidgetIds) {
			if (!widgetId.contains(id + "")) {
				widgetId += id + ":";
			}
		}
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("widgetId", widgetId);
		editor.commit();
	}

	private void delWidgetId(Context context, int[] appWidgetIds) {
		SharedPreferences sharedPreferences = context.getSharedPreferences("MyWidget", Context.MODE_PRIVATE);
		String widgetId = sharedPreferences.getString("widgetId", "");
		for (int id : appWidgetIds) {
			if (widgetId.contains(id + "")) {
				widgetId = widgetId.replace(id + ":", "");
			}
		}
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.putString("widgetId", widgetId);
		editor.commit();
	}

	@Override
	public void onDeleted(Context context, int[] appWidgetIds) {
		super.onDeleted(context, appWidgetIds);
		Log.v(tag, "onDeleted");
		delWidgetId(context, appWidgetIds);
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		super.onReceive(context, intent);
		Bundle extras = intent.getExtras();
		if (extras != null) {
			int[] appWidgetIds = extras.getIntArray(AppWidgetManager.EXTRA_APPWIDGET_IDS);
			AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
			if (appWidgetIds != null && appWidgetIds.length > 0) {
				for (int i = 0; i < appWidgetIds.length; i++) {
					RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget);
					views.setTextViewText(R.id.widgetView, "WidgetUpdate[onReceive]:" + appWidgetIds[i] + ":" + DateUtil.getDateSecond());

					// Intent intent2 = new Intent();
					// intent2.setClass(context, MyWidgetConfig.class);
					// intent2.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
					// appWidgetIds[i]);
					// PendingIntent pendingIntent =
					// PendingIntent.getActivity(context, 0, intent2,
					// PendingIntent.FLAG_CANCEL_CURRENT);
					// views.setOnClickPendingIntent(R.id.widgetImage,
					// pendingIntent);

					appWidgetManager.updateAppWidget(appWidgetIds[i], views);
				}
			}
		}
	}

}
