package com.tyl;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.tyl.activity.AllPackageInfo;
import com.tyl.activity.BaseOperateActivity;
import com.tyl.activity.BluetoothActivity;
import com.tyl.activity.BroadcastTestActivity;
import com.tyl.activity.CommandActivity;
import com.tyl.activity.DownloadActivity;
import com.tyl.activity.GalleryTestActivity;
import com.tyl.activity.HandlerTest;
import com.tyl.activity.I18nActivity;
import com.tyl.activity.JsonActivity;
import com.tyl.activity.LayoutTestActivity;
import com.tyl.activity.LoadingActivity;
import com.tyl.activity.MyAnimation;
import com.tyl.activity.MyAsyncTaskActivity;
import com.tyl.activity.OscilloscopeTest;
import com.tyl.activity.ProgressbarActivity;
import com.tyl.activity.ReadSignActivity;
import com.tyl.activity.RotationTest;
import com.tyl.activity.SensorManagerTest;
import com.tyl.activity.ServiceTestActivity;
import com.tyl.activity.SpinnerCityActivity;
import com.tyl.activity.StyleTestActivity;
import com.tyl.activity.SystemInfoActivity;
import com.tyl.activity.TableActivity;
import com.tyl.activity.TreeActivity;
import com.tyl.activity.TreeActivity2;
import com.tyl.activity.UsersListActivity;
import com.tyl.commom.ArrayUtil;
import com.tyl.commom.StartNewActivity;
import com.tyl.commom.StringUtil;
import com.tyl.customView.CustomView;
import com.tyl.db.DataBaseIntend;
import com.tyl.gesture.GestureTest;
import com.tyl.ioc.RoboguiceTest;
import com.tyl.ioc.afinal.AfinalDemo;
import com.tyl.media.MediaActivity;
import com.tyl.menu.CommonMenu;
import com.tyl.menu.TabMenuTest;
import com.tyl.net.HttpClientActivity;
import com.tyl.net.URLConnectionActivity;
import com.tyl.orm.OrmanTestActivity;
import com.tyl.pageTurn.PageTurnActivity;
import com.tyl.pic.PictureShowActivity;
import com.tyl.pic.PictureShowActivity2;
import com.tyl.preference.PreferenceTestActivity;
import com.tyl.surfaceView.SurfaceViewTest;
import com.tyl.tab.ActivityGroupDemo;
import com.tyl.tab.TabHostTest;
import com.tyl.thread.MyThreadActivity;
import com.tyl.touch.TouchActivity;
import com.tyl.touch.TouchTest;
import com.tyl.touch.TouchTest2;
import com.tyl.webView.WebViewTest;

public class FirstApp extends Activity {

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 去标题，必须在setContentView之前
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 去掉通知栏
		// getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN);

		setContentView(R.layout.main);

		// 基本操作
		findViewById(R.id.btnBaseOperate).setOnClickListener(new StartNewActivity(this, BaseOperateActivity.class));

		// 系统信息
		findViewById(R.id.btnSystemInfo).setOnClickListener(new StartNewActivity(this, SystemInfoActivity.class));

		// spinner下拉选择操作
		findViewById(R.id.btnToSpinner).setOnClickListener(new StartNewActivity(this, SpinnerCityActivity.class));

		// 服务相关
		findViewById(R.id.btnAboutService).setOnClickListener(new StartNewActivity(this, ServiceTestActivity.class));

		// 转向数据库操作
		findViewById(R.id.btnToDatabase).setOnClickListener(new StartNewActivity(this, DataBaseIntend.class));

		// 列表页面
		findViewById(R.id.btnListActivity).setOnClickListener(new StartNewActivity(this, UsersListActivity.class));

		// 列表页面2
		findViewById(R.id.btnListActivity2).setOnClickListener(new StartNewActivity(this, TableActivity.class));

		// 树形页面
		findViewById(R.id.btnTreeActivity).setOnClickListener(new StartNewActivity(this, TreeActivity.class));

		// 树形页面2
		findViewById(R.id.btnTreeActivity2).setOnClickListener(new StartNewActivity(this, TreeActivity2.class));

		// webView测试
		findViewById(R.id.btnWebView).setOnClickListener(new StartNewActivity(this, WebViewTest.class));

		// 所有安装的程序
		findViewById(R.id.btnAllApp).setOnClickListener(new StartNewActivity(this, AllPackageInfo.class));

		// 自定义view
		findViewById(R.id.btnCustomView).setOnClickListener(new StartNewActivity(this, CustomView.class));

		// handler的使用
		findViewById(R.id.btnHandler).setOnClickListener(new StartNewActivity(this, HandlerTest.class));

		// 传感器使用
		findViewById(R.id.btnSensor).setOnClickListener(new StartNewActivity(this, SensorManagerTest.class));

		// 动画使用
		findViewById(R.id.btnAnim).setOnClickListener(new StartNewActivity(this, MyAnimation.class));

		// 广播的使用
		findViewById(R.id.btnBroadcast).setOnClickListener(new StartNewActivity(this, BroadcastTestActivity.class));

		// 进度条使用
		findViewById(R.id.btnprogress).setOnClickListener(new StartNewActivity(this, ProgressbarActivity.class));

		// 样式
		findViewById(R.id.btnStyle).setOnClickListener(new StartNewActivity(this, StyleTestActivity.class));

		// 图片切换查看
		findViewById(R.id.btnGallery).setOnClickListener(new StartNewActivity(this, GalleryTestActivity.class));

		// 大图片处理
		findViewById(R.id.btnPicTest).setOnClickListener(new StartNewActivity(this, PictureShowActivity.class));

		// 大图片处理2
		findViewById(R.id.btnPicTest2).setOnClickListener(new StartNewActivity(this, PictureShowActivity2.class));

		// ActivityGroup + GridView 实现Tab分页
		findViewById(R.id.btnGridViewTab).setOnClickListener(new StartNewActivity(this, ActivityGroupDemo.class));

		// btnTabHost
		findViewById(R.id.btnTabHost).setOnClickListener(new StartNewActivity(this, TabHostTest.class));

		// SurfaceViewTest
		findViewById(R.id.btnSurfaceView).setOnClickListener(new StartNewActivity(this, SurfaceViewTest.class));

		// TabMenu
		findViewById(R.id.btnTabMenu).setOnClickListener(new StartNewActivity(this, TabMenuTest.class));

		// 普通Menu
		findViewById(R.id.btnCommonMenu).setOnClickListener(new StartNewActivity(this, CommonMenu.class));

		// 模拟信号示波器
		findViewById(R.id.btnOscilloscope).setOnClickListener(new StartNewActivity(this, OscilloscopeTest.class));

		// 首选项框架
		findViewById(R.id.btnPreference).setOnClickListener(new StartNewActivity(this, PreferenceTestActivity.class));

		// 触摸操作
		findViewById(R.id.btnTouch).setOnClickListener(new StartNewActivity(this, TouchActivity.class));

		// 多媒体
		findViewById(R.id.btnMedia).setOnClickListener(new StartNewActivity(this, MediaActivity.class));

		// 触摸记录
		findViewById(R.id.btnTouchRecord).setOnClickListener(new StartNewActivity(this, TouchTest.class));

		// 触摸记录2
		findViewById(R.id.btnTouchRecord2).setOnClickListener(new StartNewActivity(this, TouchTest2.class));

		// 旋转
		findViewById(R.id.btnRotation).setOnClickListener(new StartNewActivity(this, RotationTest.class));

		// 读取程序签名信息
		findViewById(R.id.btnReadSign).setOnClickListener(new StartNewActivity(this, ReadSignActivity.class));

		// HttpClient使用
		findViewById(R.id.btnHttpClient).setOnClickListener(new StartNewActivity(this, HttpClientActivity.class));

		// URLConnection使用
		findViewById(R.id.btnURLConnection).setOnClickListener(new StartNewActivity(this, URLConnectionActivity.class));

		// 蓝牙使用
		findViewById(R.id.btnBluetooth).setOnClickListener(new StartNewActivity(this, BluetoothActivity.class));

		// 布局测试
		findViewById(R.id.btnLayoutTest).setOnClickListener(new StartNewActivity(this, LayoutTestActivity.class));

		// 异步任务
		findViewById(R.id.btnAsyncTaskTest).setOnClickListener(new StartNewActivity(this, MyAsyncTaskActivity.class));

		// 国际化
		findViewById(R.id.btni18n).setOnClickListener(new StartNewActivity(this, I18nActivity.class));

		// 执行cmd
		findViewById(R.id.btnCommand).setOnClickListener(new StartNewActivity(this, CommandActivity.class));

		// 手势
		findViewById(R.id.btnGesture).setOnClickListener(new StartNewActivity(this, GestureTest.class));

		// loading动画
		findViewById(R.id.btnLoading).setOnClickListener(new StartNewActivity(this, LoadingActivity.class));

		// json测试
		findViewById(R.id.btnJson).setOnClickListener(new StartNewActivity(this, JsonActivity.class));

		// 发送widget更新通知
		findViewById(R.id.btnSendWidgetUpdate).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent("com.tyl.MyWidget.UPDATE_WIDGET");
				SharedPreferences sharedPreferences = FirstApp.this.getSharedPreferences("MyWidget", Context.MODE_PRIVATE);
				String widgetId = sharedPreferences.getString("widgetId", "");
				if (StringUtil.isNotBlank(widgetId)) {
					intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ArrayUtil.stringToArray(widgetId));
					sendBroadcast(intent);
					Toast.makeText(FirstApp.this, "已发送更新广播", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(FirstApp.this, "widget不存在", Toast.LENGTH_SHORT).show();
				}
			}
		});
		// 电子书翻页
		findViewById(R.id.btnPageTurn).setOnClickListener(new StartNewActivity(this, PageTurnActivity.class));
		// 线程测试
		findViewById(R.id.btnThread).setOnClickListener(new StartNewActivity(this, MyThreadActivity.class));
		// 下载测试
		findViewById(R.id.btnDownload).setOnClickListener(new StartNewActivity(this, DownloadActivity.class));

		// MyCrashHandler crashHandler = MyCrashHandler.getInstance();
		// crashHandler.init(this);
		findViewById(R.id.btnTestCrash).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				ImageView im = (ImageView) findViewById(R.id.btnTestCrash);
				im.setBackgroundColor(0);
			}
		});

		// 依赖注入
		findViewById(R.id.btnIoc).setOnClickListener(new StartNewActivity(this, RoboguiceTest.class));
		// afinal框架
		findViewById(R.id.btnAfinal).setOnClickListener(new StartNewActivity(this, AfinalDemo.class));
		// Orman
		findViewById(R.id.btnOrman).setOnClickListener(new StartNewActivity(this, OrmanTestActivity.class));
	}

	private long existTime = 0;

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
			if ((System.currentTimeMillis() - existTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
				existTime = System.currentTimeMillis();
			} else {
				finish();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}