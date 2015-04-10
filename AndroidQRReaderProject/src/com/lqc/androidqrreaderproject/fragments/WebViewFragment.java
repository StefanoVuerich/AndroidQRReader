package com.lqc.androidqrreaderproject.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.google.zxing.client.android.CaptureActivity;
import com.lqc.androidqrreaderproject.R;
import com.lqc.androidqrreaderproject.soundmanager.Player;

public class WebViewFragment extends Fragment {

	public static final String _TAG = WebViewFragment.class.getSimpleName();
	private static final String URL_TAG = "UrlTag";
	private WebView webView;
	private Activity activity;
	private ProgressBar progressBar;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		this.activity = activity;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.webview_fragment_layout,
				container, false);

		webView = (WebView) rootView.findViewById(R.id.webView);
		progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

		webView.getSettings().setJavaScriptEnabled(true);

		webView.setWebChromeClient(new WebChromeClient() {

			public void onProgressChanged(WebView view, int progress) {
				setProgress(progress);
			}

		});
		webView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				WebViewFragment.this.progressBar.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				WebViewFragment.this.progressBar.setVisibility(View.GONE);
			}
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.startsWith("http")) {
					if (url.contains("valid=true")) {
						Log.v("jajaja", "ticekt is valid");
						Player.get(getActivity()).playTicketIsValidSound();
						
					} else {
						Log.v("jajaja", "ticekt is NOT valid");
						Player.get(getActivity()).playTicketIsNotValidSound();
					}
					updateCaptureView();
					Log.v("jajaja", "want to update view");
				}
				return false;
			}

			

		});
		if (savedInstanceState == null) {
			Bundle bundle = getArguments();
			if (bundle != null) {
				String url = bundle.getString(URL_TAG);
				if (url != null) {
					webView.loadUrl(url);
				}
			}
		} else {
			Bundle mBundle = savedInstanceState;
			webView.restoreState(savedInstanceState);
		}

		return rootView;
	}
	
	private void updateCaptureView() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Log.v("jajaja","run thread");
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				((CaptureActivity)getActivity()).updateActivity();
				Log.v("jajaja","on pause and on resume");
			}
		}).run();
	}
	
	private void setProgress(int progress) {
		this.progressBar.setProgress(progress);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		if (webView != null) {
			webView.saveState(outState);
		}
	}

	public static WebViewFragment get(String url) {

		WebViewFragment fragment = new WebViewFragment();
		Bundle vBundle = new Bundle();
		vBundle.putString(URL_TAG, url);
		fragment.setArguments(vBundle);
		return fragment;
	}
}
