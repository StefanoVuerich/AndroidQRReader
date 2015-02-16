package com.lqc.androidqrreaderproject.activities;

import android.app.Fragment;
import android.content.ContentValues;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;

import com.google.zxing.client.android.CaptureActivity;
import com.lqc.androidqrreaderproject.R;
import com.lqc.androidqrreaderproject.contentprovider.DBHelper;
import com.lqc.androidqrreaderproject.contentprovider.QRReaderContentProvider;
import com.lqc.androidqrreaderproject.contentprovider.UrlsHelper;
import com.lqc.androidqrreaderproject.fragments.ConfirmURLDeleteFragment.IActionSelected;
import com.lqc.androidqrreaderproject.fragments.HistoryListFragment;
import com.lqc.androidqrreaderproject.fragments.WebViewFragment;

public class ResultsActivity extends ActionBarActivity implements
		IActionSelected {

	private static final String CURRENT_URL = "CurrentURL";
	private static final String CURRENT_ACTIVE_FRAGMENT = "CurrentShownFragment";

	private String scanResult;
	private String fragmentFlag;
	private FrameLayout fragmentContainer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		requestWindowFeature(Window.FEATURE_PROGRESS);

		super.onCreate(savedInstanceState);

		setContentView(R.layout.result_activity_layout);

		setProgressBarIndeterminateVisibility(true);
		setProgressBarVisibility(true);

		setActionBar();

		fragmentContainer = (FrameLayout) findViewById(R.id.fragmentsContainer);

		if (savedInstanceState != null) {
			scanResult = savedInstanceState.getString(CURRENT_URL);
			fragmentFlag = savedInstanceState
					.getString(CURRENT_ACTIVE_FRAGMENT);
			if (fragmentFlag == WebViewFragment._TAG) {
				showWebViewFragment("");
			} else {
				showHistoryListFragment();
			}
		} else {
			initDB();

			scanResult = getIntent().getStringExtra(
					CaptureActivity.RESULT_VALUES_TAG);

			if (scanResult != null && !scanResult.equals("")) {
				ContentValues values = new ContentValues();
				values.put(UrlsHelper.URL, scanResult);
				getContentResolver().insert(QRReaderContentProvider.URLS_URI,
						values);
				if (fragmentContainer != null)
					showWebViewFragment(scanResult);
			}
		}

	}

	private void setActionBar() {
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setLogo(R.drawable.lqc_logo_lo);
		actionBar.setDisplayUseLogoEnabled(true);
		actionBar.setDisplayShowHomeEnabled(true);
		actionBar.setTitle("LQC");
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		Bundle vBundle = new Bundle();
		vBundle.putString(CURRENT_ACTIVE_FRAGMENT, fragmentFlag);
		vBundle.putString(CURRENT_URL, scanResult);
		outState.putAll(vBundle);
	}

	private void initDB() {
		DBHelper vDBHelper = new DBHelper(ResultsActivity.this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		int activeFragmentID = ((ViewGroup) fragmentContainer).getChildAt(0)
				.getId();
		View currentView;
		View x;
		String flag;
		if((x = findViewById(R.id.webViewContainer)) != null) {
			currentView = x;
			flag = WebViewFragment._TAG;
		} else {
			currentView = findViewById(R.id.historyListContainer);
			flag = HistoryListFragment._TAG;
		}
		
		switch (item.getItemId()) {
		case R.id.action_web_site:
			if (!flag.equals(WebViewFragment._TAG)) {
				showWebViewFragment(scanResult);
			}
			return true;
		case R.id.action_collection:
			if (!flag.equals(HistoryListFragment._TAG)) {
				showHistoryListFragment();
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void showHistoryListFragment() {
		Fragment historyListFragment = HistoryListFragment.get();
		getFragmentManager()
				.beginTransaction()
				.setCustomAnimations(R.anim.left_enter, R.anim.left_exit)
				.replace(R.id.fragmentsContainer, historyListFragment,
						HistoryListFragment._TAG)
				.addToBackStack("HistoryFragmentTransaction").commit();
		fragmentFlag = HistoryListFragment._TAG;
	}

	private void showWebViewFragment(String url) {
		Fragment webViewFragment;
		if (!url.equals("")) {
			webViewFragment = WebViewFragment.get(url);

		} else {
			webViewFragment = getFragmentManager().findFragmentByTag(
					WebViewFragment._TAG);
		}
		getFragmentManager()
				.beginTransaction()
				.setCustomAnimations(R.anim.right_enter, R.anim.right_exit)
				.replace(R.id.fragmentsContainer, webViewFragment,
						WebViewFragment._TAG)
				.addToBackStack("WebViewFragmentTransaction").commit();
		fragmentFlag = WebViewFragment._TAG;
	}

	@Override
	public void OnDeleteConfirmed(long id) {
		getContentResolver().delete(QRReaderContentProvider.URLS_URI,
				UrlsHelper._ID + "=" + id, null);
	}

	@Override
	public void OnDeleteCancelled() {
		// Do Nothing
	}
}
