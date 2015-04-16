package com.lqc.androidqrreaderproject.configurationstorage;

import java.io.File;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

public class ConfigurationStorage {

	public final String STORAGE = "qr_reader_app_storage";
	public static final String CINEMA_ID = "CinemaID";
	public static final String DEVICE_ID = "DeviceID";
	public static final String DEVICE_PASSWORD = "DevicePassword";
	public static final String STORAGE_FILE_NAME = "qr_reader_app_storage.xml";
	private static ConfigurationStorage mInstance;

	private ConfigurationStorage() {
	}

	public static ConfigurationStorage getInstance() {
		if (mInstance == null) {
			mInstance = new ConfigurationStorage();
		}
		return mInstance;
	}
	
	public void updateAll(Context context, String cinemaID, String deviceID, String password) {
		updateCinemaID(context, cinemaID);
		updateDeviceID(context, deviceID);
		updateDevicePassword(context, password);
	}

	private void updateCinemaID(Context context, String cinemaID) {
		SharedPreferences settings = context.getSharedPreferences(STORAGE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(CINEMA_ID, cinemaID);
		editor.commit();
	}
	
	private void updateDeviceID(Context context, String deviceID) {
		SharedPreferences settings = context.getSharedPreferences(STORAGE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(DEVICE_ID, deviceID);
		editor.commit();
	}
	
	private void updateDevicePassword(Context context, String password) {
		SharedPreferences settings = context.getSharedPreferences(STORAGE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(DEVICE_PASSWORD, password);
		editor.commit();
	}
	
	public void init(Context context) {
		SharedPreferences settings = context.getSharedPreferences(STORAGE, 0);
		SharedPreferences.Editor editor = settings.edit();
		editor.putString(CINEMA_ID, "cinemaid");
		editor.putString(DEVICE_ID, "deviceid");
		editor.putString(DEVICE_PASSWORD, "password");
		editor.commit();
	}
	
	public void initConfigurationStorage(Context context) {
		String filename = ConfigurationStorage.STORAGE_FILE_NAME;
		String filePath = Environment.getDataDirectory().getAbsolutePath()
				+ "/data/" + context.getPackageName() + "/shared_prefs/" + filename;
		File file = new File(filePath);

		if (!file.exists()) {
			init(context);
		}
	}
}
