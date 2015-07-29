package com.lpf.mysuperdemo.downloadfile;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * SQLite管理器，实现创建数据库和表，但版本变化时实现对表的数据库表的操作
 *@author Administrator
 */

public class DBOpenHelper extends SQLiteOpenHelper
{
	private static final String DBName="eric.db";
	private static final int VERSION=1;
	
	/**
	 * 通过构造方法
	 * @param context应用程序的上下文对象
	 */
	public DBOpenHelper(Context context)
	{
		super(context, DBName, null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		// TODO 自动生成的方法存根 
		db.execSQL("CREATE TABLE IF NOT EXISTS filedownlog (id integer primary key autoincrement,"
				+ "downpath varchar(100),threadid INTEGER,downlength INTEGER)");
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		// TODO 自动生成的方法存根
		db.execSQL("DROP TABLE IF EXISTS filedownlog");
		onCreate(db);
	}
	
	
}
