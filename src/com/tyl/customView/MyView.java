package com.tyl.customView;

import com.tyl.R;
import com.tyl.R.styleable;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class MyView extends View {

	private Paint paint;

	public MyView(Context context) {
		super(context);
		paint = new Paint();
	}

	public MyView(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
		paint = new Paint();
		TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.MyView);
		int textColor = typedArray.getColor(R.styleable.MyView_textColor, 0xFFFFFF);
		float textSize = typedArray.getDimension(R.styleable.MyView_textSize, 12f);
		paint.setColor(textColor);
		paint.setTextSize(textSize);
		typedArray.recycle();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		paint.setStyle(Style.FILL);

		canvas.drawRect(new Rect(10, 20, 30, 40), paint);

		paint.setColor(Color.GREEN);
		canvas.drawText("Hello", 12, 22, paint);
	}
}
