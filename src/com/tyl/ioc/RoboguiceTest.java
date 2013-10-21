package com.tyl.ioc;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.inject.Inject;
import com.tyl.R;

public class RoboguiceTest extends RoboActivity {

	@InjectView(R.id.exeBtn)
	Button exeBtn;

	@InjectView(R.id.wifiBtn)
	Button wifiBtn;

	@InjectResource(R.string.app_name)
	String msg;

	@Inject
	IGreetingService greetingService;

	@Inject
	WifiManager wifiManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ioc_test);
		exeBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "注入的内容：" + msg + ",Greeting:" + greetingService.getGreeting(), Toast.LENGTH_SHORT).show();
			}
		});
		wifiBtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Toast.makeText(getApplicationContext(), "wifi状态：" + wifiManager.getWifiState(), Toast.LENGTH_SHORT).show();
			}
		});
	}
}
