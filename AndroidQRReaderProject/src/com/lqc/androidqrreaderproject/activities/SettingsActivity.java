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
import com.lqc.androidqrreaderproject.fragments.MenuFragment;
import com.lqc.androidqrreaderproject.login.LoginHandler;
import com.lqc.androidqrreaderproject.utilities.FullScreenHelper;

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

		cinemaID = (EditText) findViewById(R.id.cinemaIDEditText);
		deviceID = (EditText) findViewById(R.id.deviceIDEditText);
		devicePassword = (EditText) findViewById(R.id.devicePasswordEditText);

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
							((TextView) findViewById(R.id.feedbackDataSaved))
									.setVisibility(View.VISIBLE);
							// check if preferences file exist

							// if file not exist create file

							// save data update data on file

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
