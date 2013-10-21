package com.tyl.customView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

public class Dot extends View {
	private static final float RADIUS = 30;
	private float x = 30;
	private float y = 30;
	private float initX;
	private float initY;
	private float offsetX;
	private float offsetY;
	private Paint backgroundPaint;
	private Paint myPaint;

	public Dot(Context context, AttributeSet attrs) {
		super(context, attrs);

		backgroundPaint = new Paint();
		backgroundPaint.setColor(Color.BLUE);

		myPaint = new Paint();
		myPaint.setColor(Color.WHITE);
		myPaint.setAntiAlias(true);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			initX = x;
			initY = y;
			offsetX = event.getX();
			offsetY = event.getY();
			break;
		case MotionEvent.ACTION_MOVE:
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			x = initX + event.getX() - offsetX;
			y = initY + event.getY() - offsetY;
			break;
		default:
			break;
		}
		event.recycle();
		return true;
	}

	@Override
	public void draw(Canvas canvas) {
		int width = canvas.getWidth();
		int height = canvas.getHeight();
		// canvas.drawRect(1.4f * x, y, RADIUS * 2, RADIUS * 2,
		// backgroundPaint);
		canvas.drawCircle(x, y, RADIUS, myPaint);
		invalidate();
	}
}
