package com.victor_fun.android_app_utils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ThreadUtil {
	public void downloadImage(String url) {
		try {
			URL u = new URL(url);
			HttpURLConnection httpConnection = (HttpURLConnection) u.openConnection();
			httpConnection.setConnectTimeout(5000);
			InputStream is = httpConnection.getInputStream();
			ByteBufferInputStream bbis = new ByteB
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
