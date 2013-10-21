package com.tyl.activity;

import java.lang.reflect.Field;
import java.util.ArrayList;

import com.tyl.R;
import com.tyl.R.drawable;
import com.tyl.R.id;
import com.tyl.R.layout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

public class GalleryTestActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gallery_test);
		Gallery g = (Gallery) findViewById(R.id.gallery);
		g.setAdapter(new ImageAdapter(this));
	}

	private class ImageAdapter extends BaseAdapter {

		private Context context;

		private ArrayList<Integer> imgList = new ArrayList<Integer>();
		private ArrayList<Object> imgSizes = new ArrayList<Object>();

		public ImageAdapter(Context context) {
			this.context = context;
			// 用反射机制来获取资源中的图片ID和尺寸
			Field[] fields = R.drawable.class.getDeclaredFields();
			try {
				for (Field field : fields) {
					if (field.getName().startsWith("pic")) {
						int index = field.getInt(R.drawable.class);
						// 保存图片ID
						imgList.add(index);
						// 保存图片大小
						int size[] = new int[2];
						Bitmap bmImg = BitmapFactory.decodeResource(getResources(), index);
						size[0] = bmImg.getWidth();
						size[1] = bmImg.getHeight();
						imgSizes.add(size);
					}
				}
			} catch (Exception e) {
			}
		}

		public int getCount() {
			return imgList.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView i = new ImageView(context);
			// 从imgList取得图片ID
			i.setImageResource(imgList.get(position).intValue());
			i.setScaleType(ImageView.ScaleType.FIT_XY);
			// 从imgSizes取得图片大小
			int size[] = new int[2];
			size = (int[]) imgSizes.get(position);
			i.setLayoutParams(new Gallery.LayoutParams(size[0], size[1]));
			return i;
		}

	}
}
