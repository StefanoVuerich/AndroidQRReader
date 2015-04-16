package com.lqc.androidqrreaderproject.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.lqc.androidqrreaderproject.R;
import com.lqc.androidqrreaderproject.configurationstorage.ConfigurationStorage;
import com.lqc.androidqrreaderproject.fragments.MenuFragment;
import com.lqc.androidqrreaderproject.login.LoginHandler;
import com.lqc.androidqrreaderproject.utilities.FullScreenHelper;
import com.lqc.androidqrreaderproject.utilities.SharedPreferencesHelper;

public class SettingsActivity extends Activity {

	private EditText cinemaID, deviceID, devicePassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.settings_activity_layout);

		getFragmentManager()
				.beginTransaction()
				.replace(R.id.menuContainer, MenuFragment.get(),
						MenuFragment._TAG).commit();

		
		SharedPreferencesHelper sharedPref = new SharedPreferencesHelper(this);
		String[] deviceInfo = sharedPref.getDeviceInfo();
		
		cinemaID = (EditText) findViewById(R.id.cinemaIDEditText);
		cinemaID.setText(deviceInfo[0]);
		
		deviceID = (EditText) findViewById(R.id.deviceIDEditText);
		deviceID.setText(deviceInfo[1]);
		
		devicePassword = (EditText) findViewById(R.id.devicePasswordEditText);
		devicePassword.setText(deviceInfo[2]);

		final List<TextView> feedbacksList = new ArrayList<TextView>();
		feedbacksList.add((TextView) findViewById(R.id.feedbackCinemaID));
		feedbacksList.add((TextView) findViewById(R.id.feedbackDeviceID));
		feedbacksList.add((TextView) findViewById(R.id.feedbackDevicePassword));

		((Button) findViewById(R.id.saveButton))
				.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View v) {

						for (TextView txtView : feedbacksList) {
							((View) txtView).setVisibility(View.GONE);
						}

						List<Integer> errorsIndexes = LoginHandler.get()
								.register(
										new String[] {
												cinemaID.getText().toString(),
												deviceID.getText().toString(),
												devicePassword.getText()
														.toString() });

						if (errorsIndexes != null) {
							for (Integer index : errorsIndexes) {
								((View) feedbacksList.get(index))
										.setVisibility(View.VISIBLE);
							}
						} else {
							ConfigurationStorage.getInstance().updateAll(
									SettingsActivity.this,
									cinemaID.getText().toString(),
									deviceID.getText().toString(),
									devicePassword.getText().toString());

							((TextView) findViewById(R.id.feedbackDataSaved))
									.setVisibility(View.VISIBLE);

							new Thread(new Runnable() {

								@Override
								public void run() {
									try {
										Thread.sleep(1000);
									} catch (InterruptedException e) {
										e.printStackTrace();
									}
									SettingsActivity.this.finish();
								}
							}).start();
						}
					}
				});
	}

	@Override
	protected void onResume() {
		super.onResume();
		FullScreenHelper.get().enableFullScreenMode(this);
	}
}
