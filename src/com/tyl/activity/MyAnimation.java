package com.tyl.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.CycleInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;

import com.tyl.R;

public class MyAnimation extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.anim_layout);

		findViewById(R.id.btnTest01).setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				Animation anim = AnimationUtils.loadAnimation(MyAnimation.this, R.anim.my_rotate_anim);
				v.startAnimation(anim);
			}
		});

		findViewById(R.id.btnTest02).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Animation anim = AnimationUtils.loadAnimation(MyAnimation.this, R.anim.my_alpha_anim);
				v.startAnimation(anim);
			}
		});

		findViewById(R.id.btnTest03).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Animation anim = AnimationUtils.loadAnimation(MyAnimation.this, R.anim.my_scale_anim);
				v.startAnimation(anim);
			}
		});

		findViewById(R.id.btnTest04).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Animation anim = AnimationUtils.loadAnimation(MyAnimation.this, R.anim.my_translate_anim);
				v.startAnimation(anim);
			}
		});

		findViewById(R.id.btnTest05).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AnimationSet animationSet = new AnimationSet(true);
				Animation myAnimation_rotate = new RotateAnimation(0, 300);
				myAnimation_rotate.setDuration(2000);
				Animation myAnimation_translate = new TranslateAnimation(50, 90, 10, 70);
				myAnimation_translate.setDuration(3000);
				animationSet.addAnimation(myAnimation_rotate);
				animationSet.addAnimation(myAnimation_translate);
				v.startAnimation(animationSet);
			}
		});

		findViewById(R.id.btnTest06).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				AnimationSet animationSet = new AnimationSet(true);
				Animation t1 = new TranslateAnimation(0, 90, 0, 0);
				t1.setDuration(1000);
				Interpolator i = new CycleInterpolator(7);
				animationSet.addAnimation(t1);
				animationSet.setInterpolator(i);
				v.startAnimation(animationSet);
			}
		});
	}
}
