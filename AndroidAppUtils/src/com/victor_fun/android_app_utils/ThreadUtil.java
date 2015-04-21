package com.victor_fun.android_app_utils;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ThreadUtil {
	static final int BUFFER_SIZE = 1024;
	
	public static byte[] downloadImage(String url) {
		BufferedInputStream bis = null;
		ByteArrayOutputStream baos = null;
		try {
			URL u = new URL(url);
			HttpURLConnection httpConnection = (HttpURLConnection) u.openConnection();
			httpConnection.setConnectTimeout(5000);
			bis = new BufferedInputStream(httpConnection.getInputStream());
			baos = new ByteArrayOutputStream();
			
			byte[] buffer = new byte[BUFFER_SIZE];
			int len = 0;
			while (true) {
				len = bis.read(buffer, 0, BUFFER_SIZE);
				if (len == -1)
					break;
				
				baos.write(buffer, 0, len);
			}
			return baos.toByteArray();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				try {
					baos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
