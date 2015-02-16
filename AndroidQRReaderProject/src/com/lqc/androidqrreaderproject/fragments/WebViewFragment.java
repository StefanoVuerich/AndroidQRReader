package com.lqc.androidqrreaderproject.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.lqc.androidqrreaderproject.R;

public class WebViewFragment extends Fragment {

	public static final String _TAG = "WebViewFragment";
	private static final String URL_TAG = "UrlTag";
	private WebView webView;
	private Activity activity;

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

		webView.getSettings().setJavaScriptEnabled(true);

		webView.setWebChromeClient(new WebChromeClient() {

			public void onProgressChanged(WebView view, int progress) {
				// Activities and WebViews measure progress with different
				// scales.
				// The progress meter will automatically disappear when we reach
				// 100%
				activity.setProgress(progress * 1000);
				if(progress == 1000) {
					activity.setProgressBarIndeterminateVisibility(false);
					activity.setProgressBarVisibility(false);
				}
			}

		});
		webView.setWebViewClient(new WebViewClient() {
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				Toast.makeText(activity, "Oh no! " + description,
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				Log.v("jaja", "page started");
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Log.v("jaja", "page loading");
				return super.shouldOverrideUrlLoading(view, url);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				Log.v("jaja", "page finished");
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
			webView.restoreState(savedInstanceState);
		}

		return rootView;
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
