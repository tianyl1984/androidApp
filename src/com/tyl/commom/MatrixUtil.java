package com.tyl.commom;

import android.graphics.Matrix;
import android.graphics.PointF;

public class MatrixUtil {

	public static final float getScale(Matrix matrix) {
		float[] values = new float[9];
		matrix.getValues(values);
		return values[Matrix.MSCALE_X];
	}

	public static PointF getCenterPoint(Matrix matrix, Integer picWidth, Integer picHeigth) {
		float[] values = new float[9];
		matrix.getValues(values);
		float scale = values[Matrix.MSCALE_X];
		float x = values[Matrix.MTRANS_X];// 左上角x坐标
		float y = values[Matrix.MTRANS_Y];// 左上角y坐标
		return new PointF(0.5f * picWidth * scale + x, 0.5f * picHeigth * scale + y);
	}

	public static float getRightDX(Matrix matrix, Integer picWidth, Integer picHeigth) {
		float[] values = new float[9];
		matrix.getValues(values);
		float scale = values[Matrix.MSCALE_X];
		return picWidth * scale + values[Matrix.MTRANS_X];
	}

	public static float getLeftDX(Matrix matrix, Integer picWidth, Integer picHeigth) {
		float[] values = new float[9];
		matrix.getValues(values);
		float scale = values[Matrix.MSCALE_X];
		float x = values[Matrix.MTRANS_X];// 左上角x坐标
		return picWidth * scale - x;
	}

	public static float getDownDY(Matrix matrix, Integer picWidth, Integer picHeigth) {
		float[] values = new float[9];
		matrix.getValues(values);
		float scale = values[Matrix.MSCALE_Y];
		float y = values[Matrix.MTRANS_Y];// 左上角y坐标
		return picHeigth * scale + y;
	}

	public static float getUpDY(Matrix matrix, Integer picWidth, Integer picHeigth) {
		float[] values = new float[9];
		matrix.getValues(values);
		// float scale = values[Matrix.MSCALE_Y];
		float y = values[Matrix.MTRANS_Y];// 左上角y坐标
		return picHeigth - y;
	}
}
