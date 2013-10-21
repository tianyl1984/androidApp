package com.tyl.activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.tyl.R;

public class SpinnerCityActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spinner_city);

		Spinner spinnerCity = (Spinner) findViewById(R.id.spinnerCity);
		final TextView showText = (TextView) findViewById(R.id.showText);
		final List<String> cities = new ArrayList<String>();
		cities.add("北京");
		cities.add("上海");
		cities.add("南京");
		cities.add("广州");
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, cities);
		// 下拉样式
		// adapter.setDropDownViewResource(R.layout.myspinner_dropdown);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerCity.setAdapter(adapter);
		// 设置动画
		final Animation animation = AnimationUtils.loadAnimation(this, R.anim.my_anim);
		spinnerCity.setAnimation(animation);
		spinnerCity.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				showText.setText("你选择的是：" + cities.get(position));
				parent.setVisibility(View.VISIBLE);
			}

			public void onNothingSelected(AdapterView<?> parent) {
				showText.setText("未选择");
			}

		});
		spinnerCity.setOnTouchListener(new OnTouchListener() {

			public boolean onTouch(View v, MotionEvent event) {
				v.startAnimation(animation);
				v.setVisibility(View.INVISIBLE);
				return false;
			}
		});
		spinnerCity.setOnFocusChangeListener(new OnFocusChangeListener() {

			public void onFocusChange(View v, boolean hasFocus) {

			}
		});

		final EditText newCityText = (EditText) findViewById(R.id.newCityText);
		findViewById(R.id.btnAddCity).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (!newCityText.getText().toString().equals("")) {
					adapter.add(newCityText.getText().toString());
					newCityText.setText("");
				}
			}
		});
	}
}
