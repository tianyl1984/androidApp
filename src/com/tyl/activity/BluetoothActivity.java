package com.tyl.activity;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.tyl.R;

public class BluetoothActivity extends Activity {

	TextView resultTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bluetooth);
		resultTextView = (TextView) findViewById(R.id.resultTextView);
		findViewById(R.id.btnBluetoothInfo).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
				String msg = "";
				if (adapter.isEnabled()) {
					String address = adapter.getAddress();
					msg += "address:" + address + "\n";
					String name = adapter.getName();
					msg += "name:" + name + "\n";
					String scanMode = "";
					switch (adapter.getScanMode()) {
					case BluetoothAdapter.SCAN_MODE_NONE:
						scanMode = "SCAN_MODE_NONE";
						break;
					case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
						scanMode = "SCAN_MODE_CONNECTABLE";
						break;
					case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
						scanMode = "SCAN_MODE_CONNECTABLE_DISCOVERABLE";
						break;
					default:
						scanMode = "scanMode未知";
						break;
					}
					msg += "scanMode:" + scanMode + "\n";
					String state = "";
					switch (adapter.getState()) {
					case BluetoothAdapter.STATE_OFF:
						state = "STATE_OFF";
						break;
					case BluetoothAdapter.STATE_ON:
						state = "STATE_ON";
						break;
					case BluetoothAdapter.STATE_TURNING_OFF:
						state = "STATE_TURNING_OFF";
						break;
					case BluetoothAdapter.STATE_TURNING_ON:
						state = "STATE_TURNING_ON";
						break;
					default:
						state = "state未知";
						break;
					}
					msg += "state:" + state + "\n";
					msg += "BondedDevices:" + adapter.getBondedDevices().size() + "\n";
					Toast.makeText(BluetoothActivity.this, msg, Toast.LENGTH_LONG).show();
				} else {
					msg = "蓝牙关闭";
					Toast.makeText(BluetoothActivity.this, msg, Toast.LENGTH_LONG).show();
					Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
					BluetoothActivity.this.startActivityForResult(intent, REQUEST_ENABLE);
				}
			}
		});

		findViewById(R.id.btnRequestBluetoothDiscover).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
				intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 500);
				intent.putExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE);
				BluetoothActivity.this.startActivityForResult(intent, REQUEST_DISCOVERABLE);
			}
		});

		findViewById(R.id.btnDisableBluetooth).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
				adapter.disable();
			}
		});

		final Button btnScanBluetooth = (Button) findViewById(R.id.btnScanBluetooth);
		btnScanBluetooth.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
				if (!adapter.isEnabled()) {
					Toast.makeText(BluetoothActivity.this, "蓝牙没开", Toast.LENGTH_SHORT).show();
					return;
				}
				if (btnScanBluetooth.getText().toString().equals("开始扫描")) {
					adapter.startDiscovery();
				}
				if (btnScanBluetooth.getText().toString().equals("结束扫描")) {
					adapter.cancelDiscovery();
				}
			}
		});

		findViewById(R.id.btnStartServer).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
				if (!adapter.isEnabled()) {
					Toast.makeText(BluetoothActivity.this, "蓝牙没开", Toast.LENGTH_SHORT).show();
					return;
				}
				try {
					final BluetoothServerSocket bss = adapter.listenUsingRfcommWithServiceRecord("bluetoothserver", uuid);
					new Thread(new Runnable() {
						@Override
						public void run() {
							Looper.prepare();
							try {
								handler.sendMessage(Message.obtain(handler, MSG_WHAT_TOAST, "等待客户端连接"));
								BluetoothSocket bs = bss.accept();
								BluetoothDevice bd = bs.getRemoteDevice();
								handler.sendMessage(Message.obtain(handler, MSG_WHAT_TOAST, bd.getName() + "已连接"));
								while (true) {
									PrintWriter pw = new PrintWriter(bs.getOutputStream());
									String msg = "哈哈" + new Date().getTime();
									pw.print(msg);
									pw.flush();
									handler.sendMessage(Message.obtain(handler, MSG_WHAT_TOAST, "发送：" + msg));
									Thread.sleep(2000);
								}
							} catch (Exception e) {
								e.printStackTrace();
								handler.sendMessage(Message.obtain(handler, MSG_WHAT_TOAST, "出错：" + e.getMessage()));
							}
							Looper.loop();
						}
					}).start();
				} catch (Exception e) {
					e.printStackTrace();
					handler.sendMessage(Message.obtain(handler, MSG_WHAT_TOAST, "出错：" + e.getMessage()));
				}

			}
		});

		findViewById(R.id.btnConnectToServer).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
				if (!adapter.isEnabled()) {
					Toast.makeText(BluetoothActivity.this, "蓝牙没开", Toast.LENGTH_SHORT).show();
					return;
				}
				Set<BluetoothDevice> allDevice = adapter.getBondedDevices();
				for (final BluetoothDevice bd : allDevice) {
					if (bd.getName().trim().toLowerCase().equals(((EditText) findViewById(R.id.nameText)).getText().toString().trim().toLowerCase())) {
						new Thread(new Runnable() {

							@Override
							public void run() {
								Looper.prepare();
								handler.sendMessage(Message.obtain(handler, MSG_WHAT_TOAST, "查找到设备"));
								try {
									BluetoothSocket bs = bd.createRfcommSocketToServiceRecord(uuid);
									bs.connect();
									InputStream is = bs.getInputStream();
									byte[] b = new byte[1024];
									int bytes = -1;
									while ((bytes = is.read(b)) != -1) {
										byte[] data = new byte[bytes];
										for (int i = 0; i < bytes; i++) {
											data[i] = b[i];
										}
										String info = new String(data);
										handler.sendMessage(Message.obtain(handler, MSG_WHAT_TOAST, "接收到消息：" + info));
									}
								} catch (IOException e) {
									e.printStackTrace();
									handler.sendMessage(Message.obtain(handler, MSG_WHAT_TOAST, "出错：" + e.getMessage()));
								}
								Looper.loop();
							}
						}).start();
					}
				}
			}
		});

		registerReceiver(bluetoothState, new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED));
		registerReceiver(bluetoothScanState, new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED));
		registerReceiver(bluetoothDiscoveryStarted, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED));
		registerReceiver(bluetoothDiscoveryFinished, new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED));
		registerReceiver(bluetoothDiscoveryDevice, new IntentFilter(BluetoothDevice.ACTION_FOUND));
	}

	private Handler handler = new Handler(new Handler.Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_WHAT_TOAST:
				resultTextView.setText(msg.obj.toString() + "\n" + resultTextView.getText());
				break;

			default:
				break;
			}
			return false;
		}
	});

	private static final int MSG_WHAT_TOAST = 0;

	private static final int REQUEST_ENABLE = 0;

	private static final int REQUEST_DISCOVERABLE = 1;

	private static final UUID uuid = UUID.fromString("12345678-1234-1234-1234-012345678999");

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case REQUEST_ENABLE:
			if (resultCode == RESULT_OK) {
				Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "" + resultCode, Toast.LENGTH_SHORT).show();
			}
			break;
		case REQUEST_DISCOVERABLE:
			if (resultCode == RESULT_OK) {
				Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
			} else if (resultCode == RESULT_CANCELED) {
				Toast.makeText(this, "Cancel", Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, "" + resultCode, Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			break;
		}
	}

	BroadcastReceiver bluetoothState = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			int preState = intent.getIntExtra(BluetoothAdapter.EXTRA_PREVIOUS_STATE, -1);
			int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
			String msg = "";
			switch (state) {
			case BluetoothAdapter.STATE_OFF:
				msg = "关闭";
				break;
			case BluetoothAdapter.STATE_ON:
				msg = "打开";
				break;
			case BluetoothAdapter.STATE_TURNING_OFF:
				msg = "正在关闭...";
				break;
			case BluetoothAdapter.STATE_TURNING_ON:
				msg = "正在打开...";
				break;
			default:
				msg = "未知状态";
				break;
			}
			Toast.makeText(BluetoothActivity.this, msg + ":preState[" + preState + "]", Toast.LENGTH_SHORT).show();
		}
	};

	BroadcastReceiver bluetoothScanState = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			int mode = intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, -1);
			String msg = "";
			switch (mode) {
			case BluetoothAdapter.SCAN_MODE_NONE:
				msg = "可发现关闭";
				break;
			case BluetoothAdapter.SCAN_MODE_CONNECTABLE:
				msg = "只可被匹配成功过的发现";
				break;
			case BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE:
				msg = "可被所有扫描发现";
				break;
			default:
				msg = "发现状态未知";
				break;
			}
			Toast.makeText(BluetoothActivity.this, msg, Toast.LENGTH_SHORT).show();
		}

	};

	BroadcastReceiver bluetoothDiscoveryStarted = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(BluetoothActivity.this, "开始扫描...", Toast.LENGTH_SHORT).show();
			((Button) findViewById(R.id.btnScanBluetooth)).setText("结束扫描");
		}
	};
	BroadcastReceiver bluetoothDiscoveryFinished = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(BluetoothActivity.this, "扫描完成", Toast.LENGTH_SHORT).show();
			((Button) findViewById(R.id.btnScanBluetooth)).setText("开始扫描");
		}
	};
	BroadcastReceiver bluetoothDiscoveryDevice = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			BluetoothDevice bd = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
			Toast.makeText(BluetoothActivity.this, "发现设备：" + bd.getName(), Toast.LENGTH_SHORT).show();
		}
	};

	protected void onStop() {
		super.onStop();
		unregisterReceiver(bluetoothState);
		unregisterReceiver(bluetoothScanState);
		unregisterReceiver(bluetoothDiscoveryStarted);
		unregisterReceiver(bluetoothDiscoveryFinished);
		unregisterReceiver(bluetoothDiscoveryDevice);
	};
}
