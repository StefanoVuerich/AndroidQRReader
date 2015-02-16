package com.lqc.androidqrreaderproject.contentprovider;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private final static String DB_NAME = "qrreaderurls.db";
	private final static int DB_VERSION = 1;

	public DBHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(UrlsHelper.CREATE_QUERY);
		createSampleData(db);
	}

	private void createSampleData(SQLiteDatabase db) {
		ContentValues values;
		for(int i = 1; i <= 100; i++) {
			values = new ContentValues();
			values.put(UrlsHelper.URL, "www.google" + i + ".it");
		db.insert(UrlsHelper.TABLE_NAME, null, values);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// da implementare
	}

}
