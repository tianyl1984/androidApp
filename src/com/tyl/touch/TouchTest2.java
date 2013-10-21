package com.tyl.touch;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.tyl.R;
import com.tyl.commom.Util;

public class TouchTest2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(new MyTouchView2(this));
		setContentView(R.layout.touch);
		final File saveFile = new File(Util.getSdkPath() + "touch.png");
		final MyTouchView touchView = (MyTouchView) findViewById(R.id.touchView);
		if (saveFile.exists()) {
			// touchView.setBackgroundDrawable(Drawable.createFromPath(saveFile.getAbsolutePath()));
		} else {
			// touchView.setBackgroundResource(R.drawable.a);
		}
		touchView.setBackgroundResource(R.drawable.note_bg);
		findViewById(R.id.saveBtn).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				FileOutputStream fos = null;
				Bitmap bitmap = null;
				try {
					bitmap = touchView.getBitmap();
					if (!saveFile.getParentFile().exists()) {
						saveFile.getParentFile().mkdirs();
					}
					fos = new FileOutputStream(saveFile);
					bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (fos != null) {
						try {
							fos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
				Toast.makeText(getApplicationContext(), "完成!", Toast.LENGTH_SHORT).show();
			}
		});
		findViewById(R.id.loadBtn).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (saveFile.exists()) {
					touchView.setExistBitmap(BitmapFactory.decodeFile(saveFile.getAbsolutePath()));
					// touchView.setBgBitMap(BitmapFactory.decodeFile(saveFile.getAbsolutePath()));
				} else {
					// touchView.setBgBitMap(BitmapFactory.decodeResource(getResources(),
					// R.drawable.note_bg));
				}
				Toast.makeText(getApplicationContext(), "完成!", Toast.LENGTH_SHORT).show();
			}
		});
		findViewById(R.id.deleteBtn).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (saveFile.exists()) {
					saveFile.delete();
				}
			}
		});
	}

}
