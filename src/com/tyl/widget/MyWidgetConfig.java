package com.tyl.widget;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RemoteViews;
import android.widget.Spinner;
import android.widget.TextView;

import com.tyl.R;
import com.tyl.commom.DateUtil;

public class MyWidgetConfig extends Activity {

	private static final String tag = "MyWidgetConfig";

	int myWidgetId;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setResult(RESULT_CANCELED);

		setContentView(R.layout.my_widget_config);

		Log.v(tag, "MyWidgetConfig");

		Intent intent = getIntent();
		if (intent.getExtras() != null) {

			myWidgetId = intent.getExtras().getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
		}

		if (myWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
			finish();
		}
		final TextView showText = (TextView) findViewById(R.id.showText);
		final List<String> colors = new ArrayList<String>();
		colors.add("红色");
		colors.add("蓝色");
		colors.add("绿色");
		colors.add("黄色");
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, colors);
		// 下拉样式
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		Spinner colorSpinner = (Spinner) findViewById(R.id.colorSpinner);
		colorSpinner.setAdapter(adapter);

		colorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				showText.setText(colors.get(position) + ":" + myWidgetId);
				// showText.setBackgroundColor(Color.BLACK);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});

		findViewById(R.id.okButton).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(MyWidgetConfig.this);
				RemoteViews views = new RemoteViews(MyWidgetConfig.this.getPackageName(), R.layout.my_widget);

				int color = Color.GRAY;
				if (showText.getText().equals("红色" + ":" + myWidgetId)) {
					color = Color.RED;
				}
				if (showText.getText().equals("蓝色" + ":" + myWidgetId)) {
					color = Color.BLUE;
				}
				if (showText.getText().equals("绿色" + ":" + myWidgetId)) {
					color = Color.GREEN;
				}
				if (showText.getText().equals("黄色" + ":" + myWidgetId)) {
					color = Color.YELLOW;
				}

				views.setInt(R.id.widgetLayout, "setBackgroundColor", color);
				Intent intent = new Intent();
				intent.setClass(MyWidgetConfig.this, MyWidgetConfig.class);
				intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, myWidgetId);
				PendingIntent pendingIntent = PendingIntent.getActivity(MyWidgetConfig.this, myWidgetId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
				views.setOnClickPendingIntent(R.id.widgetImage, pendingIntent);
				views.setTextViewText(R.id.widgetView, "WidgetUpdate[Config]:" + myWidgetId + ":" + DateUtil.getDateSecond());

				appWidgetManager.updateAppWidget(myWidgetId, views);
				Intent rtn = new Intent();
				rtn.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, myWidgetId);
				setResult(RESULT_OK, rtn);
				finish();
			}
		});

	}
}
