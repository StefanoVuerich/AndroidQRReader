package com.lqc.androidqrreaderproject.fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.lqc.androidqrreaderproject.R;

public class TopSearchActivityWebView extends Fragment {

	public static final String _TAG = TopSearchActivityWebView.class
			.getSimpleName();
	private static final String URL_TAG = "UrlTag";
	private WebView webView;
	private ProgressBar progressBar;

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
				TopSearchActivityWebView.this.progressBar
						.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				TopSearchActivityWebView.this.progressBar
						.setVisibility(View.GONE);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if (url.startsWith("http")) {
					if (url.contains("ebay")) {

						loadBottomWebVew("http://www.cineworld.co.uk/");

						return true;
					}
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

	private void loadBottomWebVew(String url) {
		getFragmentManager()
		.beginTransaction()
		.replace(R.id.BottomSearchActivityWebView, BottomSearchActivityWebView.get(url), BottomSearchActivityWebView._TAG)
		.commit();
		
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

	public static TopSearchActivityWebView get(String url) {

		TopSearchActivityWebView fragment = new TopSearchActivityWebView();
		Bundle vBundle = new Bundle();
		vBundle.putString(URL_TAG, url);
		fragment.setArguments(vBundle);
		return fragment;
	}
}
