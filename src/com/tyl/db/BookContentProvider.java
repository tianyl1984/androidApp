package com.tyl.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.os.Environment;

public class BookContentProvider extends ContentProvider {

	private SQLiteOpenHelper dbHelper;

	private final static String DATABASE_NAME = Environment.getExternalStorageDirectory().getPath() + "/MyBook.db";

	private final static int DATABASE_VERSION = 1;

	private final static String TABLE_NAME = "book";

	private final static String BOOK_ID = "_id";

	public final static String BOOK_NAME = "name";

	public final static String BOOK_AUTHOR = "author";

	private static final String AUTHORITY = "com.tyl.mybook";

	public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + TABLE_NAME);

	private static final int ALL_MESSAGES = 1;

	private static final int SPECIFIC_MESSAGE = 2;

	private static final UriMatcher URI_MATCHER;
	static {
		URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		URI_MATCHER.addURI(AUTHORITY, TABLE_NAME, ALL_MESSAGES);
		URI_MATCHER.addURI(AUTHORITY, TABLE_NAME + "/#", SPECIFIC_MESSAGE);
	}

	@Override
	public boolean onCreate() {
		dbHelper = new SQLiteOpenHelper(this.getContext(), DATABASE_NAME, null, DATABASE_VERSION) {

			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
				String sql = "drop table if exists " + TABLE_NAME;
				db.execSQL(sql);
				onCreate(db);
			}

			@Override
			public void onCreate(SQLiteDatabase db) {
				String sql = "create table " + TABLE_NAME + "(" + BOOK_ID + " integer primary key autoincrement," + BOOK_NAME + " text," + BOOK_AUTHOR + " text);";
				db.execSQL(sql);
			}
		};
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder sqlBuilder = new SQLiteQueryBuilder();
		sqlBuilder.setTables(TABLE_NAME);
		if (URI_MATCHER.match(uri) == SPECIFIC_MESSAGE) {
			sqlBuilder.appendWhere(BOOK_ID + "=" + uri.getLastPathSegment());
		}
		Cursor c = sqlBuilder.query(dbHelper.getReadableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
		return c;
	}

	@Override
	public String getType(Uri uri) {
		switch (URI_MATCHER.match(uri)) {
		case ALL_MESSAGES:
			return "vnd.android.cursor.dir/" + TABLE_NAME;
		case SPECIFIC_MESSAGE:
			return "vnd.android.cursor.item/" + TABLE_NAME;
		default:
			throw new IllegalArgumentException("不支持的URI" + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long rowId = -1;
		rowId = dbHelper.getWritableDatabase().insert(TABLE_NAME, BOOK_NAME, values);
		this.getContext().getContentResolver().notifyChange(CONTENT_URI, null);
		return Uri.withAppendedPath(CONTENT_URI, rowId + "");
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count = dbHelper.getWritableDatabase().delete(TABLE_NAME, selection, selectionArgs);

		// 通知所有监听器
		getContext().getContentResolver().notifyChange(uri, null);

		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int count = dbHelper.getWritableDatabase().update(TABLE_NAME, values, selection, selectionArgs);
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

}
