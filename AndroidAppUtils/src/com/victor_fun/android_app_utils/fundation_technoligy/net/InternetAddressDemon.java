package com.victor_fun.android_app_utils.fundation_technoligy.net;

import java.net.InetAddress;
import java.net.UnknownHostException;

import android.util.Log;

public class InternetAddressDemon {
	static final String LOG_TAG = InternetAddressDemon.class.getSimpleName();
	
	public String getHostAddressByName(String hostName) throws UnknownHostException {
		InetAddress address = InetAddress.getByName(hostName);
		Log.d(LOG_TAG, address.toString());
		return address.getHostAddress();
	}
}
