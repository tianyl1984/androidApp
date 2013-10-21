package com.tyl.pic;

import android.app.Activity;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.tyl.R;

public class PictureShowActivity2 extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.picture_show2);
		LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
		LinearLayout.LayoutParams parm = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
		ViewArea viewArea = new ViewArea(PictureShowActivity2.this, R.drawable.ebook4);
		layout.addView(viewArea, parm);
	}
}
