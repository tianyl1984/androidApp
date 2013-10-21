package com.tyl.activity;

import java.util.ArrayList;

import com.tyl.R;
import com.tyl.R.id;
import com.tyl.R.layout;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ZoomControls;

public class OscilloscopeTest extends Activity {
	Button btnStart, btnExit;
	SurfaceView sfv;
	ZoomControls zctlX, zctlY;

	ClsOscilloscope clsOscilloscope = new ClsOscilloscope();

	static final int frequency = 8000;// 分辨率
	static final int channelConfiguration = AudioFormat.CHANNEL_CONFIGURATION_MONO;
	static final int audioEncoding = AudioFormat.ENCODING_PCM_16BIT;
	static final int xMax = 16;// X轴缩小比例最大值,X轴数据量巨大，容易产生刷新延时
	static final int xMin = 8;// X轴缩小比例最小值
	static final int yMax = 10;// Y轴缩小比例最大值
	static final int yMin = 1;// Y轴缩小比例最小值

	int recBufSize;// 录音最小buffer大小
	AudioRecord audioRecord;
	Paint mPaint;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.oscilloscope_test);
		// 录音组件
		recBufSize = AudioRecord.getMinBufferSize(frequency, channelConfiguration, audioEncoding);
		audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, frequency, channelConfiguration, audioEncoding, recBufSize);
		// 按键
		btnStart = (Button) this.findViewById(R.id.btnStart);
		btnStart.setOnClickListener(new ClickEvent());
		btnExit = (Button) this.findViewById(R.id.btnExit);
		btnExit.setOnClickListener(new ClickEvent());
		// 画板和画笔
		sfv = (SurfaceView) this.findViewById(R.id.SurfaceView01);
		sfv.setOnTouchListener(new TouchEvent());
		mPaint = new Paint();
		mPaint.setColor(Color.GREEN);// 画笔为绿色
		mPaint.setStrokeWidth(1);// 设置画笔粗细
		// 示波器类库
		clsOscilloscope.initOscilloscope(xMax / 2, yMax / 2, sfv.getHeight() / 2);

		// 缩放控件，X轴的数据缩小的比率高些
		zctlX = (ZoomControls) this.findViewById(R.id.zctlX);
		zctlX.setOnZoomInClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (clsOscilloscope.rateX > xMin)
					clsOscilloscope.rateX--;
				setTitle("X轴缩小" + String.valueOf(clsOscilloscope.rateX) + "倍" + "," + "Y轴缩小" + String.valueOf(clsOscilloscope.rateY) + "倍");
			}
		});
		zctlX.setOnZoomOutClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (clsOscilloscope.rateX < xMax)
					clsOscilloscope.rateX++;
				setTitle("X轴缩小" + String.valueOf(clsOscilloscope.rateX) + "倍" + "," + "Y轴缩小" + String.valueOf(clsOscilloscope.rateY) + "倍");
			}
		});
		zctlY = (ZoomControls) this.findViewById(R.id.zctlY);
		zctlY.setOnZoomInClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (clsOscilloscope.rateY > yMin)
					clsOscilloscope.rateY--;
				setTitle("X轴缩小" + String.valueOf(clsOscilloscope.rateX) + "倍" + "," + "Y轴缩小" + String.valueOf(clsOscilloscope.rateY) + "倍");
			}
		});

		zctlY.setOnZoomOutClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (clsOscilloscope.rateY < yMax)
					clsOscilloscope.rateY++;
				setTitle("X轴缩小" + String.valueOf(clsOscilloscope.rateX) + "倍" + "," + "Y轴缩小" + String.valueOf(clsOscilloscope.rateY) + "倍");
			}
		});
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// android.os.Process.killProcess(android.os.Process.myPid());
	}

	/**
	 * 按键事件处理
	 * 
	 * @author GV
	 * 
	 */
	class ClickEvent implements View.OnClickListener {
		@Override
		public void onClick(View v) {
			if (v == btnStart) {
				clsOscilloscope.baseLine = sfv.getHeight() / 2;
				clsOscilloscope.Start(audioRecord, recBufSize, sfv, mPaint);
			} else if (v == btnExit) {
				clsOscilloscope.Stop();
			}
		}
	}

	/**
	 * 触摸屏动态设置波形图基线
	 * 
	 * @author GV
	 * 
	 */
	class TouchEvent implements OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			clsOscilloscope.baseLine = (int) event.getY();
			return true;
		}

	}
}

class ClsOscilloscope {
	private ArrayList<short[]> inBuf = new ArrayList<short[]>();
	private boolean isRecording = false;// 线程控制标记
	/**
	 * X轴缩小的比例
	 */
	public int rateX = 4;
	/**
	 * Y轴缩小的比例
	 */
	public int rateY = 4;
	/**
	 * Y轴基线
	 */
	public int baseLine = 0;

	/**
	 * 初始化
	 */
	public void initOscilloscope(int rateX, int rateY, int baseLine) {
		this.rateX = rateX;
		this.rateY = rateY;
		this.baseLine = baseLine;
	}

	/**
	 * 开始
	 * 
	 * @param recBufSize
	 *            AudioRecord的MinBufferSize
	 */
	public void Start(AudioRecord audioRecord, int recBufSize, SurfaceView sfv, Paint mPaint) {
		isRecording = true;
		new RecordThread(audioRecord, recBufSize).start();// 开始录制线程
		new DrawThread(sfv, mPaint).start();// 开始绘制线程
	}

	/**
	 * 停止
	 */
	public void Stop() {
		isRecording = false;
		inBuf.clear();// 清除
	}

	/**
	 * 负责从MIC保存数据到inBuf
	 * 
	 * @author GV
	 * 
	 */
	class RecordThread extends Thread {
		private int recBufSize;
		private AudioRecord audioRecord;

		public RecordThread(AudioRecord audioRecord, int recBufSize) {
			this.audioRecord = audioRecord;
			this.recBufSize = recBufSize;
		}

		public void run() {
			try {
				short[] buffer = new short[recBufSize];
				audioRecord.startRecording();// 开始录制
				while (isRecording) {
					// 从MIC保存数据到缓冲区
					int bufferReadResult = audioRecord.read(buffer, 0, recBufSize);
					short[] tmpBuf = new short[bufferReadResult / rateX];
					for (int i = 0, ii = 0; i < tmpBuf.length; i++, ii = i * rateX) {
						tmpBuf[i] = buffer[ii];
					}
					synchronized (inBuf) {//
						inBuf.add(tmpBuf);// 添加数据
					}
				}
				audioRecord.stop();
			} catch (Throwable t) {
			}
		}
	};

	/**
	 * 负责绘制inBuf中的数据
	 * 
	 * @author GV
	 * 
	 */
	class DrawThread extends Thread {
		private int oldX = 0;// 上次绘制的X坐标
		private int oldY = 0;// 上次绘制的Y坐标
		private SurfaceView sfv;// 画板
		private int X_index = 0;// 当前画图所在屏幕X轴的坐标
		private Paint mPaint;// 画笔

		public DrawThread(SurfaceView sfv, Paint mPaint) {
			this.sfv = sfv;
			this.mPaint = mPaint;
		}

		public void run() {
			while (isRecording) {
				ArrayList<short[]> buf = new ArrayList<short[]>();
				synchronized (inBuf) {
					if (inBuf.size() == 0)
						continue;
					buf = (ArrayList<short[]>) inBuf.clone();// 保存
					inBuf.clear();// 清除
				}
				for (int i = 0; i < buf.size(); i++) {
					short[] tmpBuf = buf.get(i);
					SimpleDraw(X_index, tmpBuf, rateY, baseLine);// 把缓冲区数据画出来
					X_index = X_index + tmpBuf.length;
					if (X_index > sfv.getWidth()) {
						X_index = 0;
					}
				}
			}
		}

		/**
		 * 绘制指定区域
		 * 
		 * @param start
		 *            X轴开始的位置(全屏)
		 * @param buffer
		 *            缓冲区
		 * @param rate
		 *            Y轴数据缩小的比例
		 * @param baseLine
		 *            Y轴基线
		 */
		void SimpleDraw(int start, short[] buffer, int rate, int baseLine) {
			if (start == 0)
				oldX = 0;
			Canvas canvas = sfv.getHolder().lockCanvas(new Rect(start, 0, start + buffer.length, sfv.getHeight()));// 关键:获取画布
			canvas.drawColor(Color.BLACK);// 清除背景
			int y;
			for (int i = 0; i < buffer.length; i++) {// 有多少画多少
				int x = i + start;
				y = buffer[i] / rate + baseLine;// 调节缩小比例，调节基准线
				canvas.drawLine(oldX, oldY, x, y, mPaint);
				oldX = x;
				oldY = y;
			}
			sfv.getHolder().unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像
		}
	}
}