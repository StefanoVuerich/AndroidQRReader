package com.lqc.androidqrreaderproject.utilities;

import android.content.Context;
import android.content.SharedPreferences;

import com.lqc.androidqrreaderproject.configurationstorage.ConfigurationStorage;

public class SharedPreferencesHelper {

	private SharedPreferences sharedPreferences;

	public SharedPreferencesHelper(Context context) {

		this.sharedPreferences = context.getSharedPreferences(
				ConfigurationStorage.getInstance().STORAGE, 0);
	}

	public String[] getDeviceInfo() {

		if (sharedPreferences != null) {
			return new String[] {
					sharedPreferences.getString(ConfigurationStorage.CINEMA_ID,
							""),
					sharedPreferences.getString(ConfigurationStorage.DEVICE_ID,
							""),
					sharedPreferences.getString(
							ConfigurationStorage.DEVICE_PASSWORD, "") };
		}
		return null;
	}
}
