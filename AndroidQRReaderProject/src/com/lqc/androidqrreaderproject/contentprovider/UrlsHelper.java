package com.lqc.androidqrreaderproject.contentprovider;

import android.provider.BaseColumns;

public class UrlsHelper implements BaseColumns {

	public static final String TABLE_NAME = "scannedurls";
	public static final String URL = "url";
	
	public static final String CREATE_QUERY = "CREATE TABLE " + TABLE_NAME + "("
												+ _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
												+ URL + " TEXT NOT NULL )";
}
