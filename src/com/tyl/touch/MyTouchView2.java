package com.tyl.touch;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MyTouchView2 extends SurfaceView implements SurfaceHolder.Callback, Runnable {

	/** 每50帧刷新一次屏幕 **/
	public static final int TIME_IN_FRAME = 50;

	/** 画笔 **/
	Paint mPaint = null;
	Paint mTextPaint = null;
	SurfaceHolder mSurfaceHolder = null;

	/** 控制更新循环 **/
	boolean mRunning = false;

	/** 画布 **/
	Canvas mCanvas = null;

	/** 控制循环 **/
	boolean mIsRunning = false;

	/** 曲线方向 **/
	private Path mPath;

	private float mposX, mposY;

	public MyTouchView2(Context context) {
		super(context);
		init();
	}

	public MyTouchView2(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		init();
	}

	private void init() {
		/** 设置当前View拥有控制焦点 **/
		this.setFocusable(true);
		/** 设置当前View拥有触摸事件 **/
		this.setFocusableInTouchMode(true);
		/** 拿到SurfaceHolder对象 **/
		mSurfaceHolder = this.getHolder();
		/** 将mSurfaceHolder添加到Callback回调函数中 **/
		mSurfaceHolder.addCallback(this);
		/** 创建画布 **/
		mCanvas = new Canvas();
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
		/** 创建文字画笔 **/
		mTextPaint = new Paint();
		/** 设置颜色 **/
		mTextPaint.setColor(Color.BLACK);
		/** 设置文字大小 **/
		mTextPaint.setTextSize(15);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		/** 拿到触摸的状态 **/
		int action = event.getAction();
		float x = event.getX();
		float y = event.getY();
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
			mPath.reset();
			break;
		}
		// 记录当前触摸X Y坐标
		mposX = x;
		mposY = y;
		return true;
	}

	private void Draw() {
		/** 清空画布 **/
		mCanvas.drawColor(Color.WHITE);
		/** 绘制曲线 **/
		mCanvas.drawPath(mPath, mPaint);

		/** 记录当前触点位置 **/
		mCanvas.drawText("当前触笔 X：" + mposX, 0, 20, mTextPaint);
		mCanvas.drawText("当前触笔 Y：" + mposY, 0, 40, mTextPaint);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		/** 开始游戏主循环线程 **/
		mIsRunning = true;
		new Thread(this).start();
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		mIsRunning = false;
	}

	@Override
	public void run() {
		while (mIsRunning) {

			/** 取得更新游戏之前的时间 **/
			long startTime = System.currentTimeMillis();

			/** 在这里加上线程安全锁 **/
			synchronized (mSurfaceHolder) {
				/** 拿到当前画布 然后锁定 **/
				mCanvas = mSurfaceHolder.lockCanvas();
				Draw();
				/** 绘制结束后解锁显示在屏幕上 **/
				mSurfaceHolder.unlockCanvasAndPost(mCanvas);
			}

			/** 取得更新游戏结束的时间 **/
			long endTime = System.currentTimeMillis();

			/** 计算出游戏一次更新的毫秒数 **/
			int diffTime = (int) (endTime - startTime);

			/** 确保每次更新时间为50帧 **/
			while (diffTime <= TIME_IN_FRAME) {
				diffTime = (int) (System.currentTimeMillis() - startTime);
				/** 线程等待 **/
				Thread.yield();
			}

		}

	}
}
