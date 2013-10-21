package com.tyl.orm;

import org.orman.dbms.Database;
import org.orman.dbms.sqliteandroid.SQLiteAndroid;
import org.orman.mapper.MappingSession;
import org.orman.util.logging.AndroidLogger;
import org.orman.util.logging.Log;

import android.app.Application;
import android.os.Environment;

public class OrmanApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		Database db = new SQLiteAndroid(this, Environment.getExternalStorageDirectory().getPath() + "/orman.db", 1);
		MappingSession.registerDatabase(db);
		MappingSession.registerEntity(FooOrman.class);
		MappingSession.registerEntity(ZooOrman.class);
		MappingSession.start();
		AndroidLogger logger = new AndroidLogger("orman test");
		Log.setLogger(logger);
	}
}
