/**
 *@Copyright:Copyright (c) 2008 - 2100
 *@Company:SJS
 */
package com.lpf.mysuperdemo.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * @Title:
 * @Description:
 * @Author:liupf5
 * @Since:2015-6-30
 * @Version:1.1.0
 */
public class DbImpl {

	public boolean insertData(SQLiteDatabase db, String name, String age) {

		try {
			db.execSQL("insert into user values(null,?,?)", new String[] {
					name, age });
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteData(SQLiteDatabase db, String name) {

		try {
			db.execSQL("delete from user where name =?",new String[]{name});
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public ArrayList<Map<String, String>> convertCursorToList(Cursor cursor) {

		ArrayList<Map<String, String>> result = new ArrayList<Map<String, String>>();
		while (cursor.moveToNext()) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("name", cursor.getString(1));
			map.put("age", cursor.getString(2));
			result.add(map);
		}

		return result;
	}
}
