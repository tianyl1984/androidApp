package com.tyl.touch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.content.Intent;
import android.gesture.GestureOverlayView;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import com.tyl.R;

public class TouchTest extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.touch_test);
	}

	public void saveTouch(View v) {
		FileOutputStream fos = null;
		try {
			GestureOverlayView touchView = (GestureOverlayView) findViewById(R.id.touchView);
			Bitmap bitmap = touchView.getGesture().toBitmap(touchView.getMeasuredWidth(), touchView.getMeasuredHeight(), 0, touchView.getGestureColor());
			if (bitmap == null) {
				Toast.makeText(this, "bitmap == null", Toast.LENGTH_SHORT).show();
			}
			fos = new FileOutputStream(new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/touchtest.jpg"));
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fos);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
			Toast.makeText(this, "已保存", Toast.LENGTH_SHORT).show();
		}
	}

	public void showTouch(View v) {
		File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/touchtest.jpg");
		if (!f.exists()) {
			Toast.makeText(this, "文件不存在", Toast.LENGTH_SHORT).show();
		}
		Intent intent = new Intent();
		intent.setDataAndType(Uri.parse("file://" + f.getAbsolutePath()), "image/*");
		startActivity(intent);
	}
}
