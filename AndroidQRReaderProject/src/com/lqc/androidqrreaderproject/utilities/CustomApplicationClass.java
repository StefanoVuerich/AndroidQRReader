package com.lqc.androidqrreaderproject.utilities;

import android.app.Application;
import android.util.Log;

import com.lqc.androidqrreaderproject.configurationstorage.ConfigurationStorage;

public class CustomApplicationClass extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		ConfigurationStorage.getInstance()
		.initConfigurationStorage(getApplicationContext());
	}
}
