package com.tyl.activity;

import com.tyl.R;
import com.tyl.R.id;
import com.tyl.R.layout;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SensorManagerTest extends Activity {

	private static final String tag = "SensorManagerTest";

	private SensorManager manager;

	private TextView sensorView1;

	private TextView sensorView2;

	private TextView sensorView3;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sensor_layout);

		sensorView1 = (TextView) findViewById(R.id.sensorView1);
		sensorView2 = (TextView) findViewById(R.id.sensorView2);
		sensorView3 = (TextView) findViewById(R.id.sensorView3);

		manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	}

	@Override
	protected void onResume() {
		super.onResume();
		manager.registerListener(sel, manager.getSensorList(Sensor.TYPE_ORIENTATION).get(0), SensorManager.SENSOR_DELAY_NORMAL);
		manager.registerListener(sel, manager.getSensorList(Sensor.TYPE_LIGHT).get(0), SensorManager.SENSOR_DELAY_NORMAL);
		Log.v(tag, "注册监听器....");
	}

	@Override
	protected void onPause() {
		super.onPause();
		manager.unregisterListener(sel);
		Log.v(tag, "取消监听器注册....");
	}

	private SensorEventListener sel = new SensorEventListener() {

		public void onSensorChanged(SensorEvent event) {
			float[] values = event.values;
			String result = "";
			for (Float f : values) {
				result += "" + f.intValue() + ":";
			}
			result = result.substring(0, result.length() - 1);
			if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
				sensorView3.setText(result);
			} else {
				sensorView1.setText(result);
			}

			Log.v(tag, result);
		}

		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			sensorView2.setText("传感器： " + sensor.getName() + " 精确发生变化。当前值为：" + accuracy);
			Log.v(tag, "传感器： " + sensor.getName() + " 精确发生变化。当前值为：" + accuracy);
		}
	};
}
