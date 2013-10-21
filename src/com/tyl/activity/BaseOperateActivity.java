package com.tyl.activity;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.tyl.R;
import com.tyl.alert.AlertDialogTest;
import com.tyl.commom.StartNewActivity;

public class BaseOperateActivity extends Activity {

	@Override
	protected void onStart() {
		super.onStart();
		setContentView(R.layout.baseoperate);
		// 日期选择
		final TextView viewSelDate = (TextView) findViewById(R.id.viewSelDate);
		findViewById(R.id.btnDatePicker).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Calendar c = Calendar.getInstance();
				int year = c.get(Calendar.YEAR);
				int month = c.get(Calendar.MONTH);
				int day = c.get(Calendar.DAY_OF_MONTH);

				DatePickerDialog dpd = new DatePickerDialog(BaseOperateActivity.this, new OnDateSetListener() {

					public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
						viewSelDate.setText("" + year + "年" + (monthOfYear + 1) + "月" + dayOfMonth + "日");
					}
				}, year, month, day);
				dpd.show();
			}
		});
		// 输入自动提示
		((AutoCompleteTextView) findViewById(R.id.actv)).setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, new String[] { "a", "ab", "abc", "abd", "asd", "abe", "abs", "ab汉字", "a bsss" }));
		// Intent切换，带回结果
		findViewById(R.id.btnChangeIntendForResult).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(BaseOperateActivity.this, IntentDemoActivity.class);
				// onActivityResult方法接收值
				startActivityForResult(intent, 110);
			}
		});

		// 弹出框测试
		findViewById(R.id.btnAlertDialog).setOnClickListener(new StartNewActivity(this, AlertDialogTest.class));

		// 测试background高度
		findViewById(R.id.btnHeightTest).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Button btnTestTarget = (Button) findViewById(R.id.btnTestTarget);
				ImageButton imgBtnTestTarget = (ImageButton) findViewById(R.id.imgBtnTestTarget);
				int h = btnTestTarget.getMeasuredHeight();
				Toast.makeText(BaseOperateActivity.this, "MeasuredHeight:" + h + ",Height:" + btnTestTarget.getHeight() + ";MeasuredHeight:" + imgBtnTestTarget.getMeasuredHeight() + ",Height:" + imgBtnTestTarget.getHeight() + ";", Toast.LENGTH_SHORT).show();
			}
		});

		// 悬浮视图
		findViewById(R.id.btnTopView).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// 在Application的生命周期内创建
				WindowManager wm = (WindowManager) getApplicationContext().getSystemService(WINDOW_SERVICE);
				WindowManager.LayoutParams params = new WindowManager.LayoutParams();
				// 不要获取焦点，防止阻挡后边的点击操作
				params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
				// 需要加入权限
				params.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
				params.width = WindowManager.LayoutParams.WRAP_CONTENT;
				params.height = WindowManager.LayoutParams.WRAP_CONTENT;
				TextView tv = new TextView(BaseOperateActivity.this);
				tv.setText("悬浮的视图");
				wm.addView(tv, params);
				Toast.makeText(BaseOperateActivity.this, "完成", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 110) {
			String result = data.getExtras().getString("result");
			new AlertDialog.Builder(BaseOperateActivity.this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Info").setMessage("返回值：" + result + "，返回代码：" + resultCode).show();
		}
	}
}
