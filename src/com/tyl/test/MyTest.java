package com.tyl.test;

import android.test.AndroidTestCase;
import android.util.Log;

public class MyTest extends AndroidTestCase {

	public static final String tag = "TestTag";

	public void testM1() {
		Log.v(tag, "aaa");
		Log.v(tag, "bbb");
		Log.v(tag, "ccc");
		Log.v(tag, "中文测试！");
	}

	public void testM2() {
		assertEquals("aaa", "aaa");
	}

	public void m3() {
		assertEquals("aaaa", "sdf");
	}
}
