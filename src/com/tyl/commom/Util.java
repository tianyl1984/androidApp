package com.tyl.commom;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Environment;

public class Util {

	public static String getPicSavePath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath() + "/The American Pageant/";
	}

	public static String getSdkPath() {
		return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
	}

	public static void downloadFile(String url, String saveFile) throws Exception {
		downloadFile(url, saveFile, new DownloadCallBack() {

			@Override
			public void download(long sum, long finished, File file) {
			}

			@Override
			public void downloadFinish(File file) {
			}
		});
	}

	public static void downloadFile(String url, String saveFile, DownloadCallBack callBack) throws Exception {
		BufferedInputStream bis = null;
		FileOutputStream fos = null;
		try {
			File newFile = new File(saveFile);
			if (!newFile.getParentFile().exists()) {
				newFile.getParentFile().mkdirs();
			}
			fos = new FileOutputStream(newFile);
			HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
			if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new Exception("文件下载错误！");
			}
			InputStream is = con.getInputStream();
			bis = new BufferedInputStream(is);
			byte[] b = new byte[1024];
			int length = 0;
			int sum = con.getContentLength();
			int finished = 0;
			while ((length = bis.read(b)) != -1) {
				fos.write(b, 0, length);
				finished += length;
				callBack.download(sum, finished, newFile);
			}
			fos.flush();
			callBack.downloadFinish(newFile);
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("文件下载错误！");
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
				}
			}
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
				}
			}
		}
	}

	public static Long getFileLength(String url) throws Exception {
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
			throw new Exception("文件下载错误！");
		}
		int sum = con.getContentLength();
		con.disconnect();
		return Long.valueOf(sum);
	}
}
