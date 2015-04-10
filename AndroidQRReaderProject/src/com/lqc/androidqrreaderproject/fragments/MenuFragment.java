package com.lqc.androidqrreaderproject.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.zxing.client.android.CaptureActivity;
import com.lqc.androidqrreaderproject.R;
import com.lqc.androidqrreaderproject.activities.SearchActivity;
import com.lqc.androidqrreaderproject.activities.SettingsActivity;

public class MenuFragment extends Fragment {

	public static final String _TAG = MenuFragment.class.getSimpleName();
	private ImageView settings, homeUp;
	private Button search, scan;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater
				.inflate(R.layout.menu_layout, container, false);

		settings = (ImageView) rootView.findViewById(R.id.settings);
		settings.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						SettingsActivity.class);
				startActivity(intent);

			}
		});

		search = (Button) rootView.findViewById(R.id.searchButton);
		search.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity()
						.getApplicationContext(), SearchActivity.class);
				startActivity(intent);

			}
		});

		scan = (Button) rootView.findViewById(R.id.scanButton);
		scan.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity()
						.getApplicationContext(), CaptureActivity.class);
				startActivity(intent);

			}
		});
		
		homeUp = (ImageView) rootView.findViewById(R.id.homeUp);
		homeUp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getActivity().finish();

			}
		});

		String currentActivity = getActivity().getClass().getSimpleName();
		if (currentActivity.equals(CaptureActivity.class.getSimpleName())) {
			scan.setVisibility(View.GONE);
			homeUp.setVisibility(View.GONE);
		} else if (currentActivity.equals(SearchActivity.class.getSimpleName())) {
			search.setVisibility(View.GONE);
			homeUp.setVisibility(View.GONE);
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)scan.getLayoutParams();
			params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			scan.setLayoutParams(params);
		} else if(currentActivity.equals(SettingsActivity.class.getSimpleName())) {
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)scan.getLayoutParams();
			params.addRule(RelativeLayout.LEFT_OF, R.id.searchButton);
			params.setMargins(0, 0, 0, 0);
			scan.setLayoutParams(params);
			settings.setVisibility(View.GONE);
		}

		return rootView;
	}

	public static MenuFragment get() {
		return new MenuFragment();
	}

}
