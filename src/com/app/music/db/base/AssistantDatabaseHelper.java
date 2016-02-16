package com.app.music.db.base;

import com.app.music.app.AppContext;
import com.app.music.db.DB;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AssistantDatabaseHelper extends SQLiteOpenHelper {
	private static SQLiteDatabase database;

	public AssistantDatabaseHelper() {
		super(AppContext.appContext, DB.DATABASENAME, null, DB.DATABASE_VERSION);
	}

	/**
	 * 实例化SQLiteDatabase
	 */
	public static void initSQLiteDatabase() {
		if (null == database) {
			try {
				database = new AssistantDatabaseHelper().getWritableDatabase();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 关闭Helper实例
	 */
	public static void destoryInstance() {
		if (database != null) {
			database.close();
		}
	}
	
	/**
	 * 释放关闭SQLiteDatabase
	 */
	public static void releaseSQLiteDatabase() {
		if (null != database) {
			database.close();
		}
	}
	
	/**
	 * database getter方法
	 * @return
	 */
	public static SQLiteDatabase getDatabase() {
		if (null == database) {
			initSQLiteDatabase();
		}
		return database;
	}
	
	/**
	 * 数据库表创建
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// 例 db.execSQL(DB.TABLES.MESSAGE.SQL.CREATE_TABLE); // 聊天消息表
	}

	/**
	 * 更新数据库表
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		onCreate(db);
	}

	/**
	 * 数据库表回滚
	 * @param db
	 * @param oldVersion
	 * @param newVersion
	 */
	public void onRollBack(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	/**
	 * 执行sql语句
	 * @param sql
	 * @throws Exception
	 */
	public static void exeSql(String sql) throws Exception {
		getDatabase().execSQL(sql);
	}
	
	/**
	 * 更新表记录
	 * @param tableName 表名
	 * @param colName 字段名
	 * @param colValues 字段值
	 * @param whereClause 条件字段名
	 * @param whereValue 条件值
	 * @throws Exception
	 */
	public static void updateDiary(String tableName, String[] colName,
			String[] colValues, String whereClause,
			String whereValue) throws Exception {
		ContentValues args = new ContentValues();
		for (int i = 0; i < colName.length; i++) {
			args.put(colName[i], colValues[i]);
		}
		getDatabase().update(tableName, args, whereClause + "='" + whereValue + "'", null);
	} 
	
	/**
	 * 插入数据
	 * @param tableName
	 * @param colName
	 * @param colValues
	 * @throws Exception
	 */
	public static void createRow(String tableName, String[] colName,
			String[] colValues) throws Exception {
		ContentValues args = new ContentValues();
		for (int i = 0; i < colName.length; i++) {
			args.put(colName[i], colValues[i] == null ? "" : colValues[i]);
		}
		getDatabase().insert(tableName, null, args);
	}
	
	/**
	 * 删除记录
	 * @param tableName
	 * @param whereClause
	 * @param whereValue
	 * @throws Exception
	 */
	public static void deleteDiary(String tableName, String whereClause,
			String whereValue) throws Exception {
		getDatabase().delete(tableName, whereClause + "='" + whereValue + "'", null);
	}
	
	public static Cursor query(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having, String orderBy) {
		return getDatabase().query(table, columns, selection, selectionArgs, 
				groupBy, having, orderBy);
	}
	
	public static void beginTransaction() {
		getDatabase().beginTransaction();
	}
	
	public static void endTransaction() {
		getDatabase().endTransaction();
	}
	
	public static void setTransactionSuccessful() {
		getDatabase().setTransactionSuccessful();
	}
	
	/**
	 * 执行查询sql
	 * @param sql
	 * @return
	 */
	public static Cursor getSomeData(String sql) {
		Cursor cursor = null;
		try {
			cursor = getDatabase().rawQuery(sql, null);
			if (cursor != null && cursor.getCount() > 0) {
				cursor.moveToFirst();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cursor;
	}
}
