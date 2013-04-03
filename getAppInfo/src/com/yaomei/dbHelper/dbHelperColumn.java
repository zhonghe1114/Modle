package com.yaomei.dbHelper;

public class dbHelperColumn {

	public static final String TAG = "SQLITE";
	public static final String DATABASENAME = "tv.db";
	public static final String TABLE_NAME = "tv_table";
	public static final int DATABASE_VERSION = 1;
	public static final String ACTIVITY_ID = "_id";
	public static final String ACTIVITY_NAME = "name";
	public static final String ACTIVITY_PACKAGENAME = "packagename";
	public static final String ACTIVITY_CATEGORY = "category";
	public static final String ACTIVITY_DESCRIPTION = "description";

	/**
	 * ��ѯ��������
	 */
	public static final String[] SQLITE_ALL_PROJECTION = { ACTIVITY_ID,
			ACTIVITY_NAME, ACTIVITY_PACKAGENAME, ACTIVITY_CATEGORY,
			ACTIVITY_DESCRIPTION };

	/**
	 * ��ѯ�����Ͷ�Ӧ��ID
	 */
	public static final String[] SQLITE_PACKAGENAME_PROJECTION = { ACTIVITY_ID,
			ACTIVITY_NAME, ACTIVITY_PACKAGENAME };

	/**
	 * ��ѯ����ID
	 */
	public static final String[] SQLITE_QUERY_CATEGORY_PROJECTION = {
			ACTIVITY_ID, ACTIVITY_CATEGORY };

	public static final int PAGESIZE = 15;
}
