package com.lqc.androidqrreaderproject.activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;

import com.lqc.androidqrreaderproject.R;
import com.lqc.androidqrreaderproject.fragments.MenuFragment;
import com.lqc.androidqrreaderproject.fragments.TopSearchActivityWebView;
import com.lqc.androidqrreaderproject.utilities.FullScreenHelper;

public class SearchActivity extends Activity {

	private final static String URL = "http://www.google.it";
	private InputMethodManager inputMethodManager;
	private WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.search_activity_layout);
		
		webView = (WebView) findViewById(R.id.webView);
		
		new MyResultReceiver(new Handler());
		
		inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		
		FragmentTransaction ft = getFragmentManager()
		.beginTransaction();
		ft.replace(R.id.menuContainer, MenuFragment.get(),
				MenuFragment._TAG);
		ft.replace(R.id.topSearchActivityWebView, TopSearchActivityWebView.get(URL),
				TopSearchActivityWebView._TAG);
		ft.commit();
		
		final View activityRootView = findViewById(R.id.searchContainer);
	    activityRootView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
	        @Override
	        public void onGlobalLayout() {
	            Rect r = new Rect();
	            //r will be populated with the coordinates of your view that area still visible.
	            activityRootView.getWindowVisibleDisplayFrame(r);

	            int heightDiff = activityRootView.getRootView().getHeight() - (r.bottom - r.top);

	            Log.v("jajaja" ,

	                            String.valueOf(activityRootView.getRootView().getHeight()) +
	                            " - " +
	                            String.valueOf(r.bottom - r.top) +
	                            " = " +
	                            String.valueOf(heightDiff));

	        }
	    });

	}

	@Override
	protected void onResume() {
		super.onResume();
		FullScreenHelper.get().enableFullScreenMode(this);
	}
	
	private class MyResultReceiver extends ResultReceiver {


	    public MyResultReceiver(Handler handler) {
	        super(handler);
	        // TODO Auto-generated constructor stub
	    }


	    @Override
	    protected void onReceiveResult(int resultCode, Bundle resultData) {

	        Log.v("jajaja","event received");
	    }

	}
}
