package com.lqc.androidqrreaderproject.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;

import com.lqc.androidqrreaderproject.R;
import com.lqc.androidqrreaderproject.fragments.MenuFragment;
import com.lqc.androidqrreaderproject.fragments.TopSearchActivityWebView;
import com.lqc.androidqrreaderproject.utilities.FullScreenHelper;

public class SearchActivity extends Activity {

	private final static String URL = "http://www.google.it";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.search_activity_layout);
		
		FragmentTransaction ft = getFragmentManager()
		.beginTransaction();
		ft.replace(R.id.menuContainer, MenuFragment.get(),
				MenuFragment._TAG);
		ft.replace(R.id.topSearchActivityWebView, TopSearchActivityWebView.get(URL),
				TopSearchActivityWebView._TAG);
		ft.commit();
	}

	@Override
	protected void onResume() {
		super.onResume();
		FullScreenHelper.get().enableFullScreenMode(this);
	}
}
