package com.lqc.androidqrreaderproject.contentprovider;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

public class QRReaderContentProvider extends ContentProvider {

	public static final String AUTHORITY = "com.lqc.androidqrreaderproject.contentprovider.qrreadercontentprovider";
	public static final String URLS_PATH = UrlsHelper.TABLE_NAME;
	public static final Uri URLS_URI = Uri.parse(ContentResolver.SCHEME_CONTENT
			+ "://" + AUTHORITY + "/" + URLS_PATH);

	private final static int FULL_URLS_TABLE = 0;
	private final static int SINGLE_URL = 1;
	private final static UriMatcher uriMatcher = new UriMatcher(
			UriMatcher.NO_MATCH);
	static {
		uriMatcher.addURI(AUTHORITY, URLS_PATH, FULL_URLS_TABLE);
		uriMatcher.addURI(AUTHORITY, URLS_PATH + "/#", SINGLE_URL);
	}

	private DBHelper dbHelper;

	@Override
	public boolean onCreate() {
		dbHelper = new DBHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		switch (uriMatcher.match(uri)) {
		case FULL_URLS_TABLE:
			queryBuilder.setTables(UrlsHelper.TABLE_NAME);
			break;
		case SINGLE_URL:
			queryBuilder.setTables(UrlsHelper.TABLE_NAME);
			queryBuilder.appendWhere(UrlsHelper._ID + "="
					+ uri.getLastPathSegment());
			break;
		}
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		Cursor cursor = queryBuilder.query(database, projection, selection,
				selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}

	public static final String MIME_TYPE_URLS = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/urls";
	public static final String MIME_TYPE_URL = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/url";

	@Override
	public String getType(Uri uri) {
		String result = "";
		switch (uriMatcher.match(uri)) {
		case FULL_URLS_TABLE:
			result = MIME_TYPE_URLS;
			break;
		case SINGLE_URL:
			result = MIME_TYPE_URL;
			break;
		}
		return result;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		if (uriMatcher.match(uri) == FULL_URLS_TABLE) {
			SQLiteDatabase database = dbHelper.getReadableDatabase();
			long result = database.insert(UrlsHelper.TABLE_NAME, null, values);

			// notify insert
			getContext().getContentResolver().notifyChange(uri, null);

			return uri.parse(URLS_URI + "/" + result);
		} else
			return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		SQLiteDatabase database = dbHelper.getWritableDatabase();
		int result = 0;

		if (uriMatcher.match(uri) == FULL_URLS_TABLE) {
			result = database.delete(UrlsHelper.TABLE_NAME, selection,
					selectionArgs);
		} else if (uriMatcher.match(uri) == SINGLE_URL) {
			String vTmp = UrlsHelper._ID + "=" + uri.getLastPathSegment();
			result = database.delete(UrlsHelper.TABLE_NAME, selection + " AND"
					+ vTmp, selectionArgs);
		}
		if (result > 0) {
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return result;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {

		int result = 0;

		SQLiteDatabase database = dbHelper.getWritableDatabase();

		if (uriMatcher.match(uri) == FULL_URLS_TABLE) {
			result = database.update(UrlsHelper.TABLE_NAME, values, selection,
					selectionArgs);
		} 
		else if (uriMatcher.match(uri) == SINGLE_URL) 
		{
			String vTmp = UrlsHelper._ID + "=" + uri.getLastPathSegment();
			result = database.update(UrlsHelper.TABLE_NAME, values, selection + " AND " + vTmp, selectionArgs);
		} 
		if(result > 0)
		{
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return result;
	}

}
