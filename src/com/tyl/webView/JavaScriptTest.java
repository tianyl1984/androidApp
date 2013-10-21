package com.tyl.webView;

import android.os.Handler;
import android.webkit.WebView;

public class JavaScriptTest {

	private WebView webView;

	private Handler handler;

	public JavaScriptTest(WebView webView, Handler handler) {
		this.webView = webView;
		this.handler = handler;
	}

	public void addRowFromJava() {
		handler.post(new Runnable() {

			public void run() {
				webView.loadUrl("javascript:addRow('[{id:\"aaa\",name:\"bbb\",tel:\"123123\"},{id:\"sss\",name:\"eee\",tel:\"342312\"}]')");
			}
		});
	}
}
