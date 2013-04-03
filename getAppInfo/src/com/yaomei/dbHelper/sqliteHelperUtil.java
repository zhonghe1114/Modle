package com.yaomei.dbHelper;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import com.yaomei.model.AppsModel;

public class sqliteHelperUtil {

	private Context sqliteContext;
	private DataBaseHelper dbHelper;
	private SQLiteDatabase mSqliteDB = null;

	public sqliteHelperUtil(Context context) {
		this.sqliteContext = context;
	}

	/**
	 * �����ݿ�
	 */
	private void open() {
		dbHelper = new DataBaseHelper(sqliteContext);
		mSqliteDB = dbHelper.getWritableDatabase();
	}

	/**
	 * �ر����ݿ�
	 */
	private void close() {
		mSqliteDB.close();
	}

	/**
	 * ��ͷ��ת����byte[]�Ա��ܽ�ͼƬ�浽���ݿ�
	 * 
	 * @param drawalbe
	 * @return
	 */
	public byte[] getBitmapByte(Drawable drawalbe) {
		BitmapDrawable db = (BitmapDrawable) drawalbe;
		Bitmap bitmap = db.getBitmap();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
		try {
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();

		}
		return out.toByteArray();
	}

	/**
	 * ��ѯ����
	 * 
	 * @return
	 */
	public Cursor queryAllData() {
		open();
		Cursor cursor = mSqliteDB.query(dbHelperColumn.TABLE_NAME,
				dbHelperColumn.SQLITE_ALL_PROJECTION, null, null, null, null,
				null);
		close();
		return cursor;
	}

	/**
	 * ͨ��ҳ����ҳ
	 * 
	 * @param pageID
	 * @return
	 */
	public Cursor queryPageById(int pageID) {
		open();
		String sql = "select * from " + dbHelperColumn.TABLE_NAME + " Limit "
				+ String.valueOf(dbHelperColumn.PAGESIZE) + " offset "
				+ String.valueOf(pageID * dbHelperColumn.PAGESIZE) + " ;";
		// System.out.println(sql);
		Cursor cursor = mSqliteDB.rawQuery(sql, null);

		cursor.getCount();
		close();
		return cursor;
	}

	/**
	 * ����Ӧ�ó�������
	 * 
	 * @param model
	 */
	public long insertAppInfo(AppsModel model) {
		open();
		ContentValues content = new ContentValues();
		content.put(dbHelperColumn.ACTIVITY_NAME, model.getActivity_Name()
				.toString());
		content.put(dbHelperColumn.ACTIVITY_PACKAGENAME, model
				.getPackage_Name());
		content.put(dbHelperColumn.ACTIVITY_CATEGORY, model.getCategory());
		content
				.put(dbHelperColumn.ACTIVITY_DESCRIPTION, model
						.getDescription());

		long i = mSqliteDB.insert(dbHelperColumn.TABLE_NAME, null, content);
		close();
		return i;
	}

	/**
	 * ͨ�������޸�Ӧ�ó�������ID
	 * 
	 * @param categoryId
	 * @param packageName
	 */
	public int updateAPPCategory(int categoryId, String Name) {
		open();
		ContentValues content = new ContentValues();
		content.put(dbHelperColumn.ACTIVITY_CATEGORY, categoryId);
		int i = mSqliteDB.update(dbHelperColumn.TABLE_NAME, content,
				dbHelperColumn.ACTIVITY_NAME + " =?", new String[] { Name });
		close();
		return i;
	}

	/**
	 * ͨ������ɾ����Ӧ��Ӧ�ó�������
	 * 
	 * @param packageName
	 * @return
	 */
	public int deleteAppByPackageName(String Name) {
		open();
		int i = mSqliteDB.delete(dbHelperColumn.TABLE_NAME,
				dbHelperColumn.ACTIVITY_NAME + " =?", new String[] { Name });
		close();
		return i;
	}

	/**
	 * �ж����ݿ����Ƿ���ڴ�����
	 * 
	 * @param packageName
	 * @return
	 */
	public boolean isExistsByPackageName(String Name) {
		open();
		boolean isExist = true;
		Cursor cursor = mSqliteDB.query(dbHelperColumn.TABLE_NAME,
				dbHelperColumn.SQLITE_PACKAGENAME_PROJECTION,
				dbHelperColumn.ACTIVITY_NAME + " =?", new String[] { Name },
				null, null, null);

		if (!cursor.moveToFirst()) {
			isExist = false;
		}
		cursor.close();
		close();
		return isExist;
	}

	/**
	 * �����������ݿ����д��ڵİ���
	 * 
	 * @return
	 */
	public String[] getAllName() {
		open();
		Cursor cursor = mSqliteDB.query(dbHelperColumn.TABLE_NAME,
				new String[] { dbHelperColumn.ACTIVITY_NAME }, null, null,
				null, null, null);
		// cursor.moveToLast();
		if (cursor.moveToFirst()) {
			System.out.println(cursor.getCount());
			int count = (int) cursor.getCount();
			String[] Names = new String[count];
			for (int i = 0; i < count; i++) {
				Names[i] = String.valueOf(cursor
						.getColumnIndex(dbHelperColumn.ACTIVITY_NAME));
			}
			cursor.close();
			close();
			return Names;
		} else {
			cursor.close();
			close();
			return null;
		}

	}

	/**
	 * ��÷����ID
	 * 
	 * @param packageName
	 * @return
	 */
	public String[] getCategoryAndId(String Name) {
		open();
		Cursor cursor = mSqliteDB
				.query(dbHelperColumn.TABLE_NAME,
						dbHelperColumn.SQLITE_QUERY_CATEGORY_PROJECTION,
						dbHelperColumn.ACTIVITY_NAME + " =?",
						new String[] { dbHelperColumn.ACTIVITY_NAME }, null,
						null, null);
		String[] categoryAndId = new String[cursor.getColumnCount()];
		try {
			for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
					.moveToNext()) {
				categoryAndId[0] = String.valueOf(cursor
						.getColumnIndex(dbHelperColumn.ACTIVITY_ID));
				categoryAndId[1] = String.valueOf(cursor
						.getColumnIndex(dbHelperColumn.ACTIVITY_CATEGORY));
			}
		} finally {
			cursor.close();
		}
		close();
		return categoryAndId;
	}

	/**
	 * ����Ӧ�õ�����
	 * 
	 * @return
	 */
	public int getAppsCount() {
		open();
		int i;
		Cursor curosor = mSqliteDB.query(dbHelperColumn.TABLE_NAME,
				new String[] { dbHelperColumn.ACTIVITY_PACKAGENAME }, null,
				null, null, null, null);
		try {
			curosor.moveToLast();
			i = (int) curosor.getCount();
		} finally {
			curosor.close();
		}
		close();
		return i;
	}

	/**
	 * ����ܷ�ҳ��
	 * 
	 * @return
	 */
	public List<HashMap<String, String>> getPage() {

		int count = getAppsCount();
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

		int page = (int) (Math
				.ceil((double) ((double) count / (double) dbHelperColumn.PAGESIZE)));

		int pageNum = page; // count % 10 == 0 ? page : page + 1; // ȡ��ҳ��

		for (int i = 0; i < pageNum; i++) {
			HashMap<String, String> map = new HashMap<String, String>();
			if (i <= 8) {
				map.put("num", " " + String.valueOf(i + 1) + " ");
			} else {
				map.put("num", String.valueOf(i + 1));
			}
			// map.put("num", String.valueOf(10));
			list.add(map);
		}
		return list;

	}

	/**
	 * �õ��洢�����ݿ��е�ͷ��
	 * 
	 * @param temp
	 * @return
	 */
	public Bitmap getBitmapFromByte(byte[] temp) {

		Bitmap bitmap = BitmapFactory.decodeByteArray(temp, 0, temp.length);
		return bitmap;

	}

}
