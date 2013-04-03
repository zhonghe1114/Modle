package com.yaomei.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

 public DataBaseHelper(Context context) {
  super(context, dbHelperColumn.DATABASENAME, null,
				dbHelperColumn.DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table " + dbHelperColumn.TABLE_NAME + " ("
				+ dbHelperColumn.ACTIVITY_ID + " INTEGER PRIMARY KEY, "
				+ dbHelperColumn.ACTIVITY_NAME + " varchar(20),"
				+ dbHelperColumn.ACTIVITY_PACKAGENAME + " varchar(20),"
				+ dbHelperColumn.ACTIVITY_CATEGORY + " INTEGER,"
				+ dbHelperColumn.ACTIVITY_DESCRIPTION + "  varchar(50));");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		String sql = "DROP TABLE IF EXISTS  " + dbHelperColumn.TABLE_NAME + "";
		db.execSQL(sql);
		onCreate(db);
	}
}
