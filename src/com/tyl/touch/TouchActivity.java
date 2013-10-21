package com.tyl.touch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import com.tyl.R;

public class TouchActivity extends Activity {

	private static final String tag = "touch";

	private VelocityTracker vt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.touch_layout);

		findViewById(R.id.layoutId).setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Log.v(tag, "<<<<<<<<<<<<<start>>>>>>>>>>>>>");
				Log.v(tag, "action:" + event.getAction());
				Log.v(tag, "deviceId:" + event.getDeviceId());
				Log.v(tag, "downTime:" + event.getDownTime());
				Log.v(tag, "edgeFlags:" + event.getEdgeFlags());
				Log.v(tag, "EventTime:" + event.getEventTime());
				Log.v(tag, "HistorySize:" + event.getHistorySize());
				Log.v(tag, "MetaState:" + event.getMetaState());
				Log.v(tag, "PointerCount:" + event.getPointerCount());
				Log.v(tag, "Pressure:" + event.getPressure());
				Log.v(tag, "RawX:" + event.getRawX());
				Log.v(tag, "RawY:" + event.getRawY());
				Log.v(tag, "Size:" + event.getSize());
				Log.v(tag, "x:" + event.getX());
				Log.v(tag, "y:" + event.getY());
				Log.v(tag, "XPrecision:" + event.getXPrecision());
				Log.v(tag, "YPrecision:" + event.getYPrecision());
				Log.v(tag, "<<<<<<<<<<<<<end>>>>>>>>>>>>>");

				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					if (vt == null) {
						vt = VelocityTracker.obtain();
					} else {
						vt.clear();
					}
					vt.addMovement(event);
					break;
				case MotionEvent.ACTION_MOVE:
					vt.addMovement(event);
					break;
				case MotionEvent.ACTION_UP:
					vt.addMovement(event);
					vt.computeCurrentVelocity(1000);
					Toast.makeText(TouchActivity.this, "移动x：" + vt.getXVelocity() + "---" + "移动y：" + vt.getYVelocity(), Toast.LENGTH_LONG).show();
					break;
				}

				return true;
			}
		});
	}
}
