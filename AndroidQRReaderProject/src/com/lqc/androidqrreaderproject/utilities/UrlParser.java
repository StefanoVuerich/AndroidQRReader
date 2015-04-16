package com.lqc.androidqrreaderproject.utilities;

import android.content.Context;

public class UrlParser {

	private static final String SLASH = "/";
	private static final String DEVICEPASSWORD = "devicepassword";
	private static final String DEVICEID = "deviceid";
	private static final String CINEMAID = "cinemaid";
	private static final String QUESTION_MARK = "?";
	private static final String AND = "&";
	private static final String EQUAL = "=";
	
	public static String parseUrl(Context context, String url) {

		StringBuilder tmp = new StringBuilder(url);

		if (url.endsWith(SLASH)) {

		} else {
			tmp.append(SLASH);
		}

		String[] deviceInfo = new SharedPreferencesHelper(context).getDeviceInfo();
		tmp.append(QUESTION_MARK)
		.append(CINEMAID).append(EQUAL).append(deviceInfo[0])
		.append(AND).append(DEVICEID).append(EQUAL).append(deviceInfo[1])
		.append(AND).append(DEVICEPASSWORD).append(EQUAL).append(deviceInfo[2])
		.append(SLASH);
		
		return tmp.toString();
	}
}
