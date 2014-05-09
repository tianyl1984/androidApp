package com.tyl.activity;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.tyl.R;
import com.tyl.commom.StringUtil;

public class CommandServerActivity extends Activity {

	private Button serverBtn;

	private TextView resultTextView;

	private ServerSocket serverSocket;

	private boolean stopServer = false;

	private Process process;

	private InputStream processInput;

	private InputStream processInputError;

	private OutputStream processOutput;

	private static final int Add_MSG = 0;
	private static final int TOAST_MSG = 1;

	private Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case Add_MSG:
				resultTextView.setText(msg.obj.toString() + "\n" + resultTextView.getText());
				break;
			case TOAST_MSG:
				Toast.makeText(getApplicationContext(), msg.obj.toString(), Toast.LENGTH_LONG).show();
				break;
			default:
				break;
			}
			return false;
		}
	});

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.command_server);
		serverBtn = (Button) findViewById(R.id.serverBtn);
		resultTextView = (TextView) findViewById(R.id.resultTextView);
		resetBtn();
		serverBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				resetServer();
			}
		});

		try {
			process = Runtime.getRuntime().exec("su");
			processInput = process.getInputStream();
			processInputError = process.getErrorStream();
			processOutput = process.getOutputStream();
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "获取root权限出错", Toast.LENGTH_LONG).show();
		}
	}

	protected void resetServer() {
		if (serverSocket == null) {
			try {
				serverSocket = new ServerSocket(9999);
				new Thread(new Runnable() {
					@Override
					public void run() {
						while (!stopServer) {
							Socket client = null;
							// Looper.prepare();
							try {
								client = serverSocket.accept();
								InputStream in = client.getInputStream();
								String commandStr = StringUtil.readOneLine(in, "utf-8");
								commandStr += "\n";
								processOutput.write(commandStr.getBytes());
								processOutput.flush();
								String result = StringUtil.toString(processInput, "utf-8");
								result += StringUtil.toString(processInputError, "utf-8");
								handler.sendMessage(Message.obtain(handler, Add_MSG, result));
								PrintWriter pr = new PrintWriter(client.getOutputStream());
								pr.println(result);
								pr.flush();
							} catch (Exception e) {
								e.printStackTrace();
								handler.sendMessage(Message.obtain(handler, TOAST_MSG, "出错:" + e.getMessage()));
							} finally {
								if (client != null) {
									try {
										client.close();
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							}
							// Looper.loop();
						}
					}
				}).start();

			} catch (Exception e) {
				e.printStackTrace();
				serverSocket = null;
			}
		} else {
			try {
				stopServer = true;
				serverSocket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			serverSocket = null;
		}
		resetBtn();
	}

	private void resetBtn() {
		if (serverSocket == null) {
			serverBtn.setText("启动");
		} else {
			serverBtn.setText("关闭");
		}
	}

}
