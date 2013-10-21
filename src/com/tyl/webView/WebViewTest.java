package com.tyl.webView;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;

import com.tyl.R;

public class WebViewTest extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.webview_test);
		WebView webView = (WebView) findViewById(R.id.webView);
		webView.getSettings().setJavaScriptEnabled(true);
		webView.addJavascriptInterface(new JavaScriptTest(webView, new Handler()), "jsTest");
//		String url = "file:///android_asset/a.html";
		String url = "http://192.168.30.97:8082/dc-notice/no/notice!aaa.action?sys_auto_authenticate=true&dataSourceName=dataSource2&sys_username=administrator&sys_password=000000";
		webView.loadUrl(url);
	}

}
