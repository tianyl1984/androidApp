package com.tyl.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class BookDB extends SQLiteOpenHelper {

	private final static String DATABASE_NAME = Environment.getExternalStorageDirectory().getPath() + "/MyBook.db";

	private final static int DATABASE_VERSION = 1;

	private final static String TABLE_NAME = "book";

	private final static String BOOK_ID = "_id";

	private final static String BOOK_NAME = "name";

	private final static String BOOK_AUTHOR = "author";

	public BookDB(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String sql = "create table " + TABLE_NAME + "(" + BOOK_ID + " integer primary key autoincrement," + BOOK_NAME + " text," + BOOK_AUTHOR + " text);";
		db.execSQL(sql);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "drop table if exists " + TABLE_NAME;
		db.execSQL(sql);
		onCreate(db);
	}

	public Cursor select() {
		return this.getWritableDatabase().query(TABLE_NAME, null, null, null, null, null, null);
	}

	public long insert(String name, String author) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(BOOK_NAME, name);
		cv.put(BOOK_AUTHOR, author);
		return db.insert(TABLE_NAME, null, cv);
	}

	public void delete(int id) {
		this.getWritableDatabase().delete(TABLE_NAME, BOOK_ID + " = ? ", new String[] { id + "" });
	}

	public void update(String name, String author, int id) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put(BOOK_NAME, name);
		cv.put(BOOK_AUTHOR, author);
		db.update(TABLE_NAME, cv, BOOK_ID + " = ? ", new String[] { id + "" });
	}
}
