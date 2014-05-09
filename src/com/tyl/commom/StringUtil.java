package com.tyl.commom;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class StringUtil {

	public static boolean isBlank(String str) {
		return str == null || str.trim().length() == 0;
	}

	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}

	public static final String getRandomStr() {
		return getRandomStr(5);
	}

	public static String getRandomStr(int count) {
		char A = 'A';
		char z = 'z';
		String result = "";
		for (int i = 0; i < count; i++) {
			Double d = Math.random() * (z - A) + A;
			char aa = (char) d.intValue();
			result += String.valueOf(aa);
		}
		return result;
	}

	public static String toString(InputStream in, String charset) throws Exception {
		if (in == null) {
			return "";
		}
		if (in.available() <= 0) {
			return "";
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(in, charset));
		String result = "";
		String temp = null;
		while ((temp = br.readLine()) != null) {
			result += temp;
		}
		return result;
	}

	public static String readOneLine(InputStream in, String charset) throws Exception {
		if (in == null) {
			return "";
		}
		if (in.available() <= 0) {
			return "";
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(in, charset));
		return br.readLine();
	}
}
