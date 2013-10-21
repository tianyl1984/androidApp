package com.tyl.gesture;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;
import android.widget.Toast;

import com.tyl.R;

public class GestureTest extends Activity {

	private static final String TAG = "GestureTest";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gesture_test);
		TextView textView1 = (TextView) findViewById(R.id.textView1);
		textView1.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return myGestureDetector.onTouchEvent(event);
			}
		});
		textView1.setLongClickable(true);
	}

	private GestureDetector myGestureDetector = new GestureDetector(new OnGestureListener() {

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			log("onSingleTapUp");
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			log("onShowPress");
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			log("onScroll");
			return false;
		}

		@Override
		public void onLongPress(MotionEvent e) {
			log("onLongPress");
		}

		// 参数解释：
		// e1：第1个ACTION_DOWN MotionEvent
		// e2：最后一个ACTION_MOVE MotionEvent
		// velocityX：X轴上的移动速度，像素/秒
		// velocityY：Y轴上的移动速度，像素/秒
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			log("onFling");
			if (e1.getX() - e2.getX() > 300 && Math.abs(velocityX) > 100) {
				// Fling left
				Toast.makeText(GestureTest.this, "Fling Left", Toast.LENGTH_SHORT).show();
			} else if (e2.getX() - e1.getX() > 300 && Math.abs(velocityX) > 100) {
				// Fling right
				Toast.makeText(GestureTest.this, "Fling Right", Toast.LENGTH_SHORT).show();
			}
			return false;
		}

		@Override
		public boolean onDown(MotionEvent e) {
			log("onDown");
			return false;
		}
	});

	private static final void log(String str) {
		Log.v(TAG, str);
	}
}
