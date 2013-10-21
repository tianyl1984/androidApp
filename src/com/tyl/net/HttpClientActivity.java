package com.tyl.net;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tyl.R;

public class HttpClientActivity extends Activity {

	TextView resultTextView = null;
	EditText userNameEditText = null;
	EditText emailEditText = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.httpclient_test);
		resultTextView = (TextView) findViewById(R.id.resultTextView);
		userNameEditText = (EditText) findViewById(R.id.userNameEditText);
		emailEditText = (EditText) findViewById(R.id.emailEditText);
		findViewById(R.id.sendBtn).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				try {
					// 初始化HttpClient对象
					HttpClient httpClient = new DefaultHttpClient();
					// 创建post连接
					HttpPost post = new HttpPost("http://192.168.1.122:9000/myWebApp/appServlet");
					// 构造提交的数据
					List<NameValuePair> requestData = new ArrayList<NameValuePair>();
					requestData.add(new BasicNameValuePair("userName", userNameEditText.getText().toString().trim()));
					requestData.add(new BasicNameValuePair("email", emailEditText.getText().toString().trim()));
					// 对发送的数据编码
					HttpEntity httpEntity = new UrlEncodedFormEntity(requestData, HTTP.UTF_8);
					post.setEntity(httpEntity);
					post.setHeader("header001", "header001Value");
					// 发送请求， 读取返回数据
					HttpResponse response = httpClient.execute(post);
					if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
						String responseMsg = EntityUtils.toString(response.getEntity());
						resultTextView.setText(responseMsg);
					} else {
						Toast.makeText(getBaseContext(), "状态错误：" + response.getStatusLine().getStatusCode(), Toast.LENGTH_LONG).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
					Toast.makeText(getBaseContext(), "出错了：" + e.getMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});
	}
}
