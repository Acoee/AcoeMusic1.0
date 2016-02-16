package com.app.music.db;

public class DB {
	public static final String DATABASENAME = "framework.db"; // 数据库名称
	/**
	 *1.0.0 -> 100
	 */
	public static final int DATABASE_VERSION = 100; //当前数据库版本
	
	/**
	 * 所有表信息
	 * @author Acoe
	 * @date 2015-8-20
	 * @version V1.0.0
	 */
	public final class TABLES {
		/** XX表 */
		public final class ACOE_TABLE {
			public static final String TABLENAME = ""; // 表名
			/** 表字段 */
			public final class FIELDS {
				public static final String ID = "id";
			}
			/** 该表的SQL语句 */
			public final class SQL {
				// 创建该表的SQL语句
				public static final String CREATE_TABLE = "create table if not exists "
						+ TABLENAME + "()";
				// 删除该表的SQL语句
				public static final String DROP_TABLE = "drop table if exists " + TABLENAME;
			}
		}
	}
}
