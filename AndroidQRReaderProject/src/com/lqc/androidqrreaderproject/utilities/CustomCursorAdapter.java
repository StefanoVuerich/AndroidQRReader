package com.lqc.androidqrreaderproject.utilities;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lqc.androidqrreaderproject.R;
import com.lqc.androidqrreaderproject.contentprovider.UrlsHelper;

public class CustomCursorAdapter extends CursorAdapter {

	public CustomCursorAdapter(Context context, Cursor c) {
		super(context, c, 0);
	}

	private class ViewHolder {
		public TextView url;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {

		LayoutInflater mInflater = LayoutInflater.from(context);

		View single_url_view = mInflater.inflate(R.layout.single_url_layout, null);
		
		ViewHolder vHolder = new ViewHolder();
		vHolder.url = (TextView)single_url_view.findViewById(R.id.singleUrlTextView);

		single_url_view.setTag(vHolder);
		
		return single_url_view;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		
		int vUrlColumnIndex = cursor.getColumnIndex(UrlsHelper.URL);
		
		ViewHolder vHolder = (ViewHolder)view.getTag();
		
		vHolder.url.setText(cursor.getString(vUrlColumnIndex));
	}
}
