/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;


/**
 *@Title:
 *@Description:
 *@Author:liupf5
 *@Since:2015-6-30
 *@Version:1.1.0
 */
public class MySQLiteOpenHelper extends SQLiteOpenHelper {
	
	final String CREATE_TABLE_SQL = "create table user(id integer primary key autoincrement ,"+
                     " name varchar(20),"+
                     " age varchar(10))";
	
	private static SQLiteDatabase mDB;
	private static MySQLiteOpenHelper mHelper;
	
	private static final String TABLE_NAME = "user";

	/**
	 * @param context
	 * @param name
	 * @param factory
	 * @param version
	 */
	public MySQLiteOpenHelper(Context context, String name, int version) {
		super(context, name, null, version);
	}
	
	public static MySQLiteOpenHelper getInstance(Context context,String name, int version){
		if(mHelper == null){
			mHelper = new MySQLiteOpenHelper(context, name, version);
		}
		return mHelper;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_SQL);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	
	private SQLiteDatabase openReadable(){
		if(mDB != null){
			if(!mDB.isOpen()){
				mDB = this.getReadableDatabase();
			}else{
				return mDB;
			}
		}else{
			mDB = this.getReadableDatabase();
		}
		return mDB;
	}
	
	private SQLiteDatabase openWritable(){
		if(mDB!=null){
			if(!mDB.isOpen()){
				mDB = this.getWritableDatabase();
			}else{
				return mDB;
			}
		}else{
			mDB = this.getWritableDatabase();
		}
		return mDB;
	}

	public long insertOrUpdate(String selection, String[] selectionArgs, ContentValues values){
		openWritable();
		Cursor cursor = null;
		try{
			cursor = mDB.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
			long rowId = 0;
			if(cursor != null && cursor.getCount() > 0 ){
				//update
				rowId = mDB.update(TABLE_NAME, values, selection, selectionArgs);
				return 1;//修改
			}else{
				//insert
				rowId = mDB.insert(TABLE_NAME, null, values);
				return 2;//新增
			}
//			return rowId;
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}finally{
			cursor.close();
			mDB.close();
		}
	}

	private Cursor query(String sql, String[] selectionArgs){
		openReadable();
		Cursor cursor = null;
		cursor = mDB.rawQuery(sql, selectionArgs);
		return cursor;
	}
	
	public ArrayList<Map<String,String>> queryUsers(String sql, String[] selectionArgs){
		ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();
		Cursor cursor = query(sql,selectionArgs);
		try{
			if(cursor!=null && cursor.getCount() > 0){
				while(cursor.moveToNext()){
					Map<String, String> map = new HashMap<String, String>();
					map.put("name", cursor.getString(1));
					map.put("age", cursor.getString(2));
					result.add(map);
				}
			}
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}finally{
			cursor.close();
			mDB.close();
		}
	}
	
	public long delete(String name){
		openWritable();
		try{
			return mDB.delete(TABLE_NAME, "name = ?", new String[]{name});
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}finally{
			mDB.close();
		}
	}
	
	public long deleteAll(){
		openWritable();
		try{
			return mDB.delete(TABLE_NAME, "", null);
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}finally{
			mDB.close();
		}
	}
	
}
