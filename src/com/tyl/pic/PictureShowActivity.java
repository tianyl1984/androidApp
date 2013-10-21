package com.tyl.pic;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tyl.R;
import com.tyl.commom.MatrixUtil;
import com.tyl.commom.ProgressThreadWrap;
import com.tyl.commom.RunnableWrap;
import com.tyl.commom.Util;

public class PictureShowActivity extends Activity {

	ImageView image = null;

	Bitmap bitmap = null;

	Drawable drawable = null;

	TextView picInfoTextView = null;

	TextView resultView = null;

	private Integer picWidth;

	private Integer picHeigth;

	private final static int[] picIds = new int[] { R.drawable.small00, R.drawable.small01, R.drawable.small02, R.drawable.small03, R.drawable.small04, R.drawable.ebook1, R.drawable.ebook2, R.drawable.ebook3 };

	private int curPicIndex = 0;

	private static final Integer MSG_SHOW = 1;

	private static final Integer MSG_RELAESE = 2;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == MSG_SHOW) {
				if (bitmap != null) {
					Long t1 = System.currentTimeMillis();
					int height = bitmap.getHeight();
					int width = bitmap.getWidth();
					image.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
					image.setImageBitmap(bitmap);
					picWidth = width;
					picHeigth = height;
					matrix.reset();
					setFixSize();
					picInfoTextView.setText(picWidth + "X" + picHeigth);
					Log.v("image耗时：", (System.currentTimeMillis() - t1) + "");
				}
				if (drawable != null) {
					image.setImageDrawable(drawable);
				}
			}
			if (msg.what == MSG_RELAESE) {
				release();
			}
		};
	};

	private void setFixSize() {
		WindowManager wm = (WindowManager) PictureShowActivity.this.getSystemService(Context.WINDOW_SERVICE);
		Integer displayWidth = wm.getDefaultDisplay().getWidth();// 屏幕宽度
		// Integer displayHeight = wm.getDefaultDisplay().getHeight();
		float b = displayWidth.floatValue() / picWidth.floatValue();
		matrix.postScale(b, b);
		image.setImageMatrix(matrix);
	}

	private void setOriginalSize() {
		matrix.reset();
		float b = 1.0f;
		matrix.postScale(b, b);
		image.setImageMatrix(matrix);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.picture_show);
		image = (ImageView) findViewById(R.id.image);

		picInfoTextView = (TextView) findViewById(R.id.picInfoTextView);
		resultView = (TextView) findViewById(R.id.resultView);

		findViewById(R.id.btnOriginalSize).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setOriginalSize();
			}
		});

		findViewById(R.id.btnLoadPic2).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				new ProgressThreadWrap(PictureShowActivity.this, new RunnableWrap() {
					@Override
					public void run(ProgressDialog progressDialog) {

						handler.sendEmptyMessage(MSG_RELAESE);

						Long t1 = System.currentTimeMillis();
						BitmapFactory.Options opts = new BitmapFactory.Options();
						opts.inJustDecodeBounds = true;
						BitmapFactory.decodeResource(getResources(), R.drawable.ebook4, opts);

						// opts.inSampleSize =
						// BitmapUtil.computeSampleSize(opts, -1, 1024 * 768);
						// opts.inJustDecodeBounds = false;

						// opts.inJustDecodeBounds = false;
						// opts.inDither = false;
						// opts.inPurgeable = true;
						// opts.inTempStorage = new byte[1024 * 1024 * 30];

						// drawable = Drawable.createFromPath(Util.getSdkPath()
						// + "download/AP CHEMISTRY-0001.jpg");

						BufferedOutputStream out = null;
						InputStream in = null;
						try {
							// Rect rect = new Rect(0, 0, 1000, 1000);
							// bitmap = BitmapFactory.decodeFileDescriptor(new
							// FileInputStream(Util.getSdkPath() +
							// "download/AP CHEMISTRY-0002.jpg").getFD(), rect,
							// opts);
							in = new BufferedInputStream(new FileInputStream(Util.getSdkPath() + "download/AP CHEMISTRY-0001.jpg"));
							final ByteArrayOutputStream dataStream = new ByteArrayOutputStream();
							out = new BufferedOutputStream(dataStream, 1024 * 4);
							byte[] b = new byte[1024 * 4];
							int read;
							while ((read = in.read(b)) != -1) {
								out.write(b, 0, read);
							}
							out.flush();
							final byte[] data = dataStream.toByteArray();
							bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} finally {
							if (in != null) {
								try {
									in.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
							if (out != null) {
								try {
									out.close();
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}

						// bitmap = BitmapFactory.decodeResource(getResources(),
						// R.drawable.ebook4, opts);

						Log.v("bitmap耗时：", (System.currentTimeMillis() - t1) + "");
						Message msg = new Message();
						msg.what = MSG_SHOW;
						handler.sendMessage(msg);
						progressDialog.dismiss();
					}
				}).start();
			}
		});

		findViewById(R.id.btnLoadPic).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final int showPicId = picIds[curPicIndex];
				curPicIndex = (curPicIndex + 1) % picIds.length;

				new ProgressThreadWrap(PictureShowActivity.this, new RunnableWrap() {
					@Override
					public void run(ProgressDialog progressDialog) {
						Long t1 = System.currentTimeMillis();
						bitmap = BitmapFactory.decodeResource(getResources(), showPicId);
						Log.v("bitmap耗时：", (System.currentTimeMillis() - t1) + "");
						Message msg = new Message();
						msg.what = MSG_SHOW;
						handler.sendMessage(msg);
						progressDialog.dismiss();
					}
				}).start();
			}
		});

		image.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				ImageView view = (ImageView) v;
				switch (event.getAction() & MotionEvent.ACTION_MASK) {
				// 单指按下
				case MotionEvent.ACTION_DOWN:
					savedMatrix.set(matrix);
					start.set(event.getX(), event.getY());
					mode = DRAG;
					break;
				// 双指按下
				case MotionEvent.ACTION_POINTER_DOWN:
					oldDist = spacing(event);
					if (oldDist > 10f) {
						savedMatrix.set(matrix);
						midPoint(mid, event);
						mode = ZOOM;
					}
					break;
				// 最后一个手指离开屏幕
				case MotionEvent.ACTION_UP:
					// 一个手指离开，但还有一个在屏幕上
				case MotionEvent.ACTION_POINTER_UP:
					mode = NONE;
					break;
				// 手指移动
				case MotionEvent.ACTION_MOVE:
					if (mode == DRAG) {
						float dx = event.getX() - start.x;
						float dy = event.getY() - start.y;
						Matrix temp = new Matrix();
						temp.set(savedMatrix);
						temp.postTranslate(dx, dy);
						if (dx < 0) {// 向左移动
							float dx1 = MatrixUtil.getRightDX(temp, 1024, 728);
							if (dx1 <= 50) {
								return true;
							}
						} else {// 向右移动
							float dx1 = MatrixUtil.getLeftDX(temp, 1024, 728);
							if (dx1 <= 50) {
								return true;
							}
						}
						// if (dy < 0) {// 向上移动
						// float dy1 = MatrixUtil.getDownDY(temp, 1024, 728);
						// if (dy1 <= 50) {
						// return true;
						// }
						// } else {// 向下移动
						// float dy1 = MatrixUtil.getUpDY(temp, 1024, 728);
						// if (dy1 <= 50) {
						// return true;
						// }
						// }
						matrix.set(savedMatrix);
						matrix.postTranslate(dx, dy);
					} else if (mode == ZOOM) {
						float newDist = spacing(event);
						float newscale = newDist / oldDist;

						float scale = MatrixUtil.getScale(matrix);
						if (scale <= 0.5 && newscale < 1.0f) {// 禁止缩小超过1/2
							return true;
						}
						if (scale >= 3 && newscale > 1.0f) {// 禁止放大超过3倍
							return true;
						}

						if (newDist > 10f) {
							matrix.set(savedMatrix);
							matrix.postScale(newscale, newscale, mid.x, mid.y);
						}
					}
					break;
				}
				view.setImageMatrix(matrix);
				return true;
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		release();
	}

	private void release() {
		if (image != null) {
			image.setImageBitmap(null);
		}
		if (drawable != null) {
			drawable = null;
			System.gc();
		}
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
		}
	}

	private Matrix matrix = new Matrix();
	private Matrix savedMatrix = new Matrix();

	private static final int NONE = 0;
	private static final int DRAG = 1;
	private static final int ZOOM = 2;
	private int mode = NONE;

	// Remember some things for zooming
	private PointF start = new PointF();
	private PointF mid = new PointF();
	private float oldDist = 1f;

	/** Determine the space between the first two fingers */
	private float spacing(MotionEvent event) {
		float x = event.getX(0) - event.getX(1);
		float y = event.getY(0) - event.getY(1);
		return FloatMath.sqrt(x * x + y * y);
	}

	/** Calculate the mid point of the first two fingers */
	private void midPoint(PointF point, MotionEvent event) {
		float x = event.getX(0) + event.getX(1);
		float y = event.getY(0) + event.getY(1);
		point.set(x / 2, y / 2);
	}
}
