package com.tyl.touch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class MyTouchView extends View {

	/** 画笔 **/
	Paint mPaint = null;

	/** 曲线方向 **/
	private Path mPath;

	private float mposX, mposY;

	private Bitmap existBitmap;

	public MyTouchView(Context context) {
		super(context);
		init();
	}

	public MyTouchView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		init();
	}

	private void init() {
		/** 设置当前View拥有控制焦点 **/
		this.setFocusable(true);
		/** 设置当前View拥有触摸事件 **/
		this.setFocusableInTouchMode(true);
		/** 创建曲线画笔 **/
		mPaint = new Paint();
		mPaint.setColor(Color.BLACK);
		/** 设置画笔抗锯齿 **/
		mPaint.setAntiAlias(true);
		/** 画笔的类型 **/
		mPaint.setStyle(Paint.Style.STROKE);
		/** 设置画笔变为圆滑状 **/
		mPaint.setStrokeCap(Paint.Cap.ROUND);
		/** 设置线的宽度 **/
		mPaint.setStrokeWidth(5);
		/** 创建路径对象 **/
		mPath = new Path();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		/** 拿到触摸的状态 **/
		int action = event.getAction();
		Float x = event.getX();
		Float y = event.getY();
		switch (action) {
		// 触摸按下的事件
		case MotionEvent.ACTION_DOWN:
			/** 设置曲线轨迹起点 X Y坐标 **/
			mPath.moveTo(x, y);
			break;
		// 触摸移动的事件
		case MotionEvent.ACTION_MOVE:
			/** 设置曲线轨迹 **/
			// 参数1 起始点X坐标
			// 参数2 起始点Y坐标
			// 参数3 结束点X坐标
			// 参数4 结束点Y坐标
			mPath.quadTo(mposX, mposY, x, y);
			break;
		// 触摸抬起的事件
		case MotionEvent.ACTION_UP:
			/** 按键抬起后清空路径轨迹 **/
			// mPath.reset();
			break;
		}
		// 记录当前触摸X Y坐标
		mposX = x;
		mposY = y;
		// invalidate();
		invalidate(x.intValue() - 50, y.intValue() - 50, x.intValue() + 50, y.intValue() + 50);
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (getBackground() != null) {
			getBackground().draw(canvas);
		} else {
			canvas.drawColor(Color.WHITE);
		}
		if (existBitmap != null) {
			canvas.drawBitmap(existBitmap, 0, 0, null);
		}
		canvas.drawPath(mPath, mPaint);
	}

	public Bitmap getBitmap() {
		Bitmap result = Bitmap.createBitmap(this.getMeasuredWidth(), this.getMeasuredHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(result);
		Paint tempPaint = new Paint();
		tempPaint.setColor(Color.BLACK);
		tempPaint.setAntiAlias(true);
		tempPaint.setStyle(Paint.Style.STROKE);
		tempPaint.setStrokeCap(Paint.Cap.ROUND);
		tempPaint.setStrokeWidth(5);
		// tempPaint.setAlpha(50);
		canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
		if (existBitmap != null) {
			canvas.drawBitmap(existBitmap, 0, 0, null);
		}
		canvas.drawPath(mPath, tempPaint);
		// Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
		// R.drawable.maintop2);
		// canvas.drawBitmap(bitmap, 0, 0, null);
		return result;
	}

	public Bitmap getExistBitmap() {
		return existBitmap;
	}

	public void setExistBitmap(Bitmap existBitmap) {
		this.existBitmap = existBitmap;
		invalidate();
	}

}
