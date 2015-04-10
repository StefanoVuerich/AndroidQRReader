package com.lqc.androidqrreaderproject.fragments;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.lqc.androidqrreaderproject.R;
import com.lqc.androidqrreaderproject.contentprovider.QRReaderContentProvider;
import com.lqc.androidqrreaderproject.contentprovider.UrlsHelper;
import com.lqc.androidqrreaderproject.utilities.CustomCursorAdapter;

public class HistoryListFragment extends Fragment implements OnItemClickListener, OnItemLongClickListener, LoaderManager.LoaderCallbacks<Cursor> {
	
	public static final String _TAG = "HistoryListFragment";
	private static final int ITEMS_LOADER_ID = 0;
	
	private CustomCursorAdapter customCursorAdapter;
	private ListView urlListView;
	private Button clearDataButton;
	
	public static HistoryListFragment get() {
		return new HistoryListFragment();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.history_list_fragment_layout,
				container, false);
		
		urlListView = (ListView)rootView.findViewById(R.id.urlListView);
		
		customCursorAdapter = new CustomCursorAdapter(getActivity(), null);
		
		urlListView.setAdapter(customCursorAdapter);
		urlListView.setOnItemClickListener(this);
		urlListView.setOnItemLongClickListener(this);
		
		clearDataButton = (Button)rootView.findViewById(R.id.clearDataFromDBButton);
		clearDataButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ConfirmURLDeleteFragment confirmDialofFragment = ConfirmURLDeleteFragment.getInstance(ConfirmURLDeleteFragment.ALL_TAG);
				confirmDialofFragment.show(getFragmentManager(), ConfirmURLDeleteFragment._TAG);
			}
		});
		
		getLoaderManager().initLoader(ITEMS_LOADER_ID, null, this);
		
		return rootView;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		
		TextView currentView = (TextView) ((LinearLayout) view).getChildAt(0);
		
		getFragmentManager()
		.beginTransaction()
		.replace(R.id.fragmentsContainer, WebViewFragment.get(currentView.getText().toString()), WebViewFragment._TAG)
		.commit();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> parent, View view,
			int position, long id) {
		
		TextView currentView = (TextView) ((LinearLayout) view).getChildAt(0);
		
		ConfirmURLDeleteFragment confirmDialofFragment = ConfirmURLDeleteFragment.getInstance(id, currentView.getText().toString());
		confirmDialofFragment.show(getFragmentManager(), ConfirmURLDeleteFragment._TAG);
		return true;
	}
	
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		CursorLoader vLoader = new CursorLoader(getActivity(), QRReaderContentProvider.URLS_URI, null, null, null, UrlsHelper._ID + " DESC");
		return vLoader;
	}

	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		customCursorAdapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		customCursorAdapter.swapCursor(null);
	}

}
