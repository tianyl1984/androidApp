package com.tyl.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.tyl.R;
import com.tyl.commom.AlertDialogUtil;
import com.tyl.commom.Count;
import com.tyl.commom.MyNetUtil;
import com.tyl.commom.StringUtil;

public class URLConnectionActivity extends Activity {

	EditText userNameEditText;
	EditText emailEditText;
	TextView resultTextView;

	String sessionId;

	final Count count = new Count();

	private static final int MSG_REFLUSH = 0;

	private Handler handler = new Handler(new Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_REFLUSH:
				resultTextView.setText("成功:" + count.getSuccessCount() + ",失败:" + count.getFailCount() + ",总次数:" + count.getSumCount());
				break;

			default:
				break;
			}
			return false;
		}

	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Debug.startMethodTracing("URLConnectionActivity");
		// android2.3 新特性：检测当前线程是否有相关操作
		// StrictMode.setThreadPolicy(new
		// StrictMode.ThreadPolicy.Builder().detectDiskReads().detectDiskWrites().detectNetwork().penaltyLog().build());
		// StrictMode.setVmPolicy(new
		// StrictMode.VmPolicy.Builder().detectLeakedSqlLiteObjects().penaltyLog().build());
		super.onCreate(savedInstanceState);
		setContentView(R.layout.urlconnection_test);
		userNameEditText = (EditText) findViewById(R.id.userNameEditText);
		emailEditText = (EditText) findViewById(R.id.emailEditText);
		resultTextView = (TextView) findViewById(R.id.resultTextView);
		findViewById(R.id.sendBtn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				resultTextView.setText("");
				try {
					// POST请求
					HttpURLConnection conn = (HttpURLConnection) (new URL("http://192.168.1.122:9090/myWebApp/appServlet").openConnection());
					conn.setDoOutput(true);
					conn.setDoInput(true);
					// POST必须大写
					conn.setRequestMethod("POST");
					conn.setUseCaches(false);
					// 仅对当前请求自动重定向
					conn.setInstanceFollowRedirects(true);
					// header 设置编码
					conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
					conn.setRequestProperty("header1", "header1Value");
					if (StringUtil.isNotBlank(sessionId)) {// 设置cookie
						conn.setRequestProperty("Cookie", sessionId);
					}
					// 连接
					conn.connect();
					String content = "userName=" + URLEncoder.encode(userNameEditText.getText().toString().trim(), "utf-8");
					content += "&email=" + URLEncoder.encode(emailEditText.getText().toString().trim(), "utf-8");
					DataOutputStream out = new DataOutputStream(conn.getOutputStream());
					out.writeBytes(content);
					out.flush();
					out.close();
					BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
					String result = "";
					String temp = null;
					result += "content:\n";
					String json = "";
					while ((temp = reader.readLine()) != null) {
						result += temp + "\n";
						json += temp;
					}
					Map<String, List<String>> headerMap = conn.getHeaderFields();
					Iterator<String> it = headerMap.keySet().iterator();
					result += "header:\n";
					while (it.hasNext()) {
						String key = it.next();
						String value = "";
						for (String str : headerMap.get(key)) {
							value += str + ";";
						}
						result += key + ":" + value + "\n";
					}
					if (headerMap.containsKey("set-cookie")) {
						String cookies = conn.getHeaderField("set-cookie");
						sessionId = cookies.substring(0, cookies.indexOf(";"));
					}
					result += "sessionId:" + sessionId + "\n";
					result += "ResponseCode:" + conn.getResponseCode() + "\n";
					result += "ResponseMessage:" + conn.getResponseMessage() + "\n";
					resultTextView.setText(result);
					System.out.println(result);
					reader.close();
					conn.disconnect();
					JSONObject resultJson = new JSONObject(json);
					Iterator<String> itJson = resultJson.keys();
					String msg = "";
					while (itJson.hasNext()) {
						String key = itJson.next();
						if (!resultJson.isNull(key)) {
							String val = resultJson.getString(key);
							msg += key + ":" + val + "\n";
						}
					}
					AlertDialogUtil.showMessage(msg, "JSON数据", URLConnectionActivity.this);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		findViewById(R.id.sendBtn2).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable() {

					@Override
					public void run() {
						Looper.prepare();
						int count = 1000;
						while (count > 0) {
							handler.sendEmptyMessage(MSG_REFLUSH);
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							count--;
						}
						Looper.loop();
					}
				}).start();
				for (int i = 0; i < 10; i++) {
					new Thread(new Runnable() {

						@Override
						public void run() {
							Looper.prepare();
							for (int j = 0; j < 100; j++) {
								try {
									// String result =
									// NetUtil.getUrlResponse("http://192.168.1.122:9000/dc-base/base/module/homepage/webos/index.jsp");
									String result = MyNetUtil.getUrlResponse("http://www.baidu.com/");
									Log.v("MyAndroidApp", result);
									count.addSuccessCount();
								} catch (IOException e) {
									e.printStackTrace();
									count.addFailCount();
								}
							}
							Looper.loop();
						}
					}).start();
				}
			}
		});
	}

	@Override
	protected void onStop() {
		super.onStop();
		// Debug.stopMethodTracing();
	}
}
